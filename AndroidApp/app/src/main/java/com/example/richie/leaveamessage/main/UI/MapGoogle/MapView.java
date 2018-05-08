package com.example.richie.leaveamessage.main.UI.MapGoogle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.models.Message;
import com.example.richie.leaveamessage.main.UI.MessageList.ListView;
import com.example.richie.leaveamessage.main.UI.ReadMessage.ReadMessageView;
import com.example.richie.leaveamessage.main.UI.SignIn.SignInView;
import com.example.richie.leaveamessage.main.UI.WriteMessage.WriteMessageView;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by Richie on 3/28/2018.
 */

public class MapView extends AppCompatActivity implements
        OnMapReadyCallback,
        //GoogleMap.OnInfoWindowClickListener,
        MapContract.ViewMap,
        ClusterManager.OnClusterItemInfoWindowClickListener<Message>{
    //TODO Any Logic with Data Lets Refactor to MVP
    private static final String TAG = MapView.class.getSimpleName();
    public static final String LAST_KNOWN_LAT = "lastLat";
    public static final String LAST_KNOWN_LON = "lastLon";
    private static final int RESULT_CODE = 24;
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private FloatingActionButton mLeaveMessageBut;
    private ClusterManager<Message> mClusterManager;


    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location My Old School and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(41.952564, -87.6542044);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates;


    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_update";
    private static final int REQUEST_CHECK_SETTINGS = 2200;
    private LocationRequest mLocationRequest;
    private boolean mCheckCoarse = false;
    private boolean mCheckFine = false;
    private MapContract.PresenterMap mPresenter;

    private GoogleSignInClient mGoogleSignInClient;
    private Message clickedClusterItem;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
            mRequestingLocationUpdates = savedInstanceState.getBoolean(
                    KEY_REQUESTING_LOCATION_UPDATES);

        }

        setContentView(R.layout.map_view_layout);

        mLeaveMessageBut = findViewById(R.id.message_map_but);
        mLeaveMessageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLastKnownLocation!=null){
                    Intent intent = new Intent(MapView.this, WriteMessageView.class);
                    double lat = mLastKnownLocation.getLatitude();
                    double lon = mLastKnownLocation.getLongitude();
                    intent.putExtra(LAST_KNOWN_LAT,lat);
                    intent.putExtra(LAST_KNOWN_LON,lon);
                    startActivityForResult(intent,RESULT_CODE);
                }
            }
        });

        // Construct a GeoDataClient
        GeoDataClient geoDC = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        PlaceDetectionClient placeDC = Places.getPlaceDetectionClient(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mPresenter = new MapPresenter(this, getContentResolver());

        setUpLocationCallback();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Used for Sign out.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            outState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES,
                    mRequestingLocationUpdates);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.list_view:
                Intent intent = new Intent(this, ListView.class);
                startActivity(intent);
                return true;
            case R.id.sign_out:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if (loggedIn) {
            LoginManager.getInstance().logOut();
        }
        Intent i = new Intent(this, SignInView.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMap!=null){
            mMap.clear();
            addItems();
        }
        if (!mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        getLocationPermission();
        if (!mLocationPermissionGranted) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);
        mRequestingLocationUpdates = true;


    }


    private void setUpLocationCallback() {

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);


                Location lastLocation = locationResult.getLastLocation();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastLocation.getLatitude(),
                                lastLocation.getLongitude()), DEFAULT_ZOOM));

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        mRequestingLocationUpdates = false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Later Store the markers in a list of Markers. Case we need it. its here.
//        Integer index = 0;
//        for (Message message : mPresenter.getData()) {
//            Marker marker = mMap.addMarker(new MarkerOptions()
//                    .position(message.getPosition())
//                    .title(message.getTitle())
//                    .snippet(message.getMessage())
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.message)));
//            marker.setTag(index);
//            index++;
//
//        }
        //mMap.setOnInfoWindowClickListener(this);
        //Remove those Buttons
        mMap.getUiSettings().setMapToolbarEnabled(false);


        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) findViewById(R.id.map), false);
//
//                TextView title = (infoWindow.findViewById(R.id.title));
//                title.setText(marker.getTitle());
//
//                TextView snippet = (infoWindow.findViewById(R.id.snippet));
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });

        getLocationPermission();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        //Set up Location Settings for Location updates
        changeLocationSettings();

        //SetUp Location Updates
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        setUpClusterer();

    }


    private void changeLocationSettings() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(50000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //noinspection MissingPermission

                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                        mLocationCallback, null);

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapView.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCheckFine = true;
                }
            }
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCheckCoarse = true;
                }
            }
        }
        if (mCheckFine && mCheckCoarse) {
            mLocationPermissionGranted = true;
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void setUpClusterer() {
        // Position the map.
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Message>() {
                    @Override
                    public boolean onClusterItemClick(Message message) {
                        clickedClusterItem = message;
                        return false;
                    }
                });

        // Add cluster items (markers) to the cluster manager.
        addItems();
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new MyCustomAdapterForItems());
    }

    private void addItems() {
        mClusterManager.clearItems();
        mClusterManager.addItems(mPresenter.getData());
        mClusterManager.cluster();
    }


//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        Intent intent = new Intent(this, ReadMessageView.class);
//        Integer index = (Integer) marker.getTag();
//        intent.putExtra(ReadMessageView.POSITION_EXTRA, index);
//        startActivity(intent);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                setUpClusterer();
            }
        }
    }

    @Override
    public void onClusterItemInfoWindowClick(Message message) {
        //Cluster item InfoWindow clicked, set title as action
        Intent intent = new Intent(this, ReadMessageView.class);
        Integer index = (Integer) message.getId();
        //TODO fix ReadView for an ID instead of tag maybe
        intent.putExtra(ReadMessageView.POSITION_EXTRA, index);
        startActivity(intent);

    }

    public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyCustomAdapterForItems() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.custom_info_contents, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {

            TextView tvTitle = myContentsView.findViewById(R.id.title);
            TextView tvSnippet = myContentsView.findViewById(R.id.snippet);

            tvTitle.setText(clickedClusterItem.getTitle());
            tvSnippet.setText(clickedClusterItem.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }



}
