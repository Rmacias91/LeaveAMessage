package com.example.richie.leaveamessage.core.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by richard.macias on 10/14/18
 * Modernizing Medicine
 **/
@Entity(tableName = "messages")
data class MessageEntity(@PrimaryKey(autoGenerate = true) var id: Long?,
                         @ColumnInfo(name = "message") var message: String,
                         @ColumnInfo(name = "date") var date: String,
                         @ColumnInfo(name = "lat") var lat: String,
                         @ColumnInfo(name = "lon") var lon: String)
{
    constructor():this(null,"","","","")
}