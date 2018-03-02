const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const db = require('./config/database');
const mysql = require('mysql');
const messageRoute = require('./routes/messageRoute');



//init app to express constructor
const app = express();

//Declare the port
const port = 3000;

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


// db.connect(function(err){
//     if(!err) {
//         console.log("Database is connected ... ");    
//     //Listen to port 3000
//     app.listen(port, () => {
//         console.log(`Starting the server at port ${port}`);
//     });

//     } else {
//         throw err;   
//     }
//     });

    app.listen(port, () => {
        console.log(`Starting the server at port ${port}`);
    });




