package com.example.cardinfo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardinfo.data.db.model.CardInfoDbModel

@Dao
interface CardInfoDao {

    @Query("SELECT * FROM card_info")
    fun getPreviousRequests(): LiveData<List<CardInfoDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCardInfo(cardInfoDbModel: CardInfoDbModel)

    @Query("DELETE FROM card_info WHERE id=:cardInfoId")
    suspend fun deleteCardInfo(cardInfoId: Long)
}