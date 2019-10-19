package com.example.friends

import androidx.lifecycle.Observer
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Friend(
    @PrimaryKey(autoGenerate = true)
    val temanId : Int? = null,
    val nama : String,
    val jenisKelamin : String,
    val email : String,
    val telp : String,
    val alamat : String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null
)