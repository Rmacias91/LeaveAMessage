const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const db = require('./config/database');
const mysql = require('mysql');
const messageRoute = require('./routes/messageRoute');
const AWS = require('aws-sdk');

AWS.config.region = process.env.REGION;

//init app to express constructor
const app = express();



//Middleware for CORS
app.use(cors());

//Middleware for bodyparsing using both json and urlencoding
app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());

//Show Invalid page it / is requested
app.get('/', (req,res) => {
    res.send("Welcome to the Homepage of the API");
})

app.use('/message',messageRoute);

var port = process.env.PORT || 3000;
db.connect(function(err){
    if(!err) {
        console.log("Database is connected ... ");    
    //Listen to port 3000 or whatever 8081
    var server = app.listen(port, function () {
        console.log('Server running at http://127.0.0.1:' + port + '/');
    });
    

    } else {
        throw err;   
    }
    });






