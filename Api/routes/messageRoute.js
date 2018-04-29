//need Router
const express = require('express');
const router = express.Router();
const message = require('../models/messageModel');


router.get('/',(req,res) => {
    message.getAllMessages((err,messages) => {
        if(err){
            res.json({success:false,message: `Failed
            to load all messages. Error: ${err}` });
        }
        else{
            res.write(JSON.stringify({success:true,
            messages:messages},null,2));
            res.end();
        }
    });   
});

router.get('/:id',(req,res) => {
    let id = req.params.id;
    console.log("I GOT CALLED id is "+ id);
    message.getMessageById(id,(err,message) => {
        if(err){
            res.json({success:false,message: `Failed
            to load message by ID. Error: ${err}` });
        }
        else if(message!=""){
            console.log("Message is route is: " + message);
            res.write(JSON.stringify({success:true,
            message:message},null,2));
            res.end();
        }
        else{
            res.json({success:false,message:"ID: "+ id + "does not exist"});
        }
    });
});

router.post('/',(req,res,next) => {
    let Lat = req.body.Lat;
    let Lon = req.body.Lon;
    let Message = req.body.Message;
    let datePosted = req.body.Date;
    message.addMessage(Lat,Lon,Message,datePosted,(err,messageRes) =>{
        if(err){
            res.json({success: false, message: `Failed
            to create a new Message. Error: ${err}`});
        }
        else{
            console.log("MessageResponse in addMessage "+messageRes);
            console.log("Response id is:" + res);
            res.json({success:true, message:"Added Message", insertId:messageRes});
        }
    });
});

//DELETE HTTP method to /blog. Here, we pass in a params which is the object id.
router.delete('/:id', (req,res,next)=> {
    let id = req.params.id;
    message.deleteMessageByID(id,(err,message) => {
        if(err){
            res.json({success: false, message: `Failed
            to delete the message. Error: ${err}`});
        }
        else if (message){
            res.json({success:true,message:
            "Deleted Successfully"});
        }
        else 
        res.json({success:false});
    });

});

//Put (Update) method to /blog. 
router.put('/:id',(req,res,next)=>{
    let id = req.params.id;
    let newMessage = req.body.Message;
    console.log(newMessage)
    console.log(id)
    message.updateMessageById(id,newMessage,(err,list)=>{
        if(err){
            res.json({success: false, message: `Failed to update the  Message. Error: ${err}`});
        }
        //Do I check for list??
        else
        res.json({success:true, message:`Updated Id ${id} Successfully.`});
    })
})




//finished export router
module.exports = router;