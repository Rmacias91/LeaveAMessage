package com.example.richie.leaveamessage.core.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by richard.macias on 10/14/18
 * Modernizing Medicine
 **/
@Dao
interface MessageDataDao {
    @Query("SELECT * from messages")
    fun getAll(): List<MessageEntity>

    @Insert(onConflict = REPLACE)
    fun insert(messageEntity: MessageEntity)

    @Query("DELETE from messages")
    fun deleteAll()

}