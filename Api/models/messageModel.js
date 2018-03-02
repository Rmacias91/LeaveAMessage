var db = require('../config/database');

//Lat and Lon Decimal 1234.567890 Google maps uses one more decimal but it doesn't make a difference.
//Message varchar 100
//id 11 bit int
//postDate datetime ISO-8601 and the format is: YYYY-MM-DDTHH:mm:ss.sssZ
exports.addMessage = function(Lat,Lon, Message, postDate, done){
    var values = [Lat, Lon, Message, postDate];
    

    db.get().query('INSERT INTO messages (Lat,Lon,Message,Date) VALUES (?,?,?,?)',
        values, function(err,result){
            if(err) return done(err);
            else return done(null,result.id);
        })
}

exports.getAllMessages = function(done){
    db.get().query('SELECT * FROM messages', function(err, rows){
        if(err) return done(err);
        else return done(null,rows);
    });
}

exports.getMessageById = function(id,done){
    db.get().query('SELECT * FROM messages WHERE id = '+id),
        function(err,row){
        if(err) return done(err);
        else return done(null,row);
    }
}

//Gotta make an SQL SELECT Statement to look for nearby locations.
//Pull only ones in time range? Or maybe delete after time range?

//TODO Update in future to use abstract layer like Content Provider in Android
//People can SQL inject in your APi WARNING
exports.updateMessageById = function(id,newMessage,done){
    console.log("id = "+id +" message is: "+newMessage);
    query = 'UPDATE messages SET Message = \x27'+ newMessage + '\x27 WHERE id = '+id;
    db.get().query(query,
        function(err,result){
            if(err) return done(err);
            else return done(null,result);
        });
}



exports.deleteMessageByID = function(id,done){
    db.get().query('DELETE FROM messages WHERE id = '+id, function(err, rows){
        if(err) return done(err);
        else return done(null,rows);
    });
}


//We need QUERY TO SEACH FOR NEARBY LOCATIONS


