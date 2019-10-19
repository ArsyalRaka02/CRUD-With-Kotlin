package com.example.friends

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FriendDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: Friend)

    @Query("SELECT * from Friend")
    fun ambilSemuaTeman(): LiveData<List<Friend>>

//    @Query("SELECT * FROM Friend WHERE temanId")
//    fun ambilEditTeman(temanId: Int?): Friend?

//    @Delete
//    fun delete(friend: Friend)
//
//    @Query("UPDATE Friend SET nama=:nama, jenisKelamin=:gender, email=:email, telp=:telp, alamat=:alamat WHERE temanId=:id ")
//    fun update(id: Long, nama: String, gender: String, email:String, telp:String, alamat:String)

     @Update
     fun updateTeman(friend: Friend)
}