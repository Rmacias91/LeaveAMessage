package com.example.richie.leaveamessage.core.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by richard.macias on 10/14/18
 * Modernizing Medicine
 **/
@Database(entities = arrayOf(MessageEntity::class),version = 1)
abstract class MessageDataBase : RoomDatabase() {

    abstract fun messageDataDao(): MessageDataDao

    companion object {
        private var INSTANCE: MessageDataBase? = null

        fun getInstance(context:Context):MessageDataBase?{
            if(INSTANCE == null){
                synchronized(MessageDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MessageDataBase::class.java,"messages.db")
                            .build()

                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}