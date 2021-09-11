package com.example.reservacionesapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReservacionesDao {
    @Query("SELECT * FROM reservaciones")
    fun getAll(): LiveData<List<Reservacion>>

    @Query("SELECT * FROM reservaciones WHERE idReservacion = :id")
    fun get(id: Int): LiveData<Reservacion>

    @Insert
    fun insertAll(vararg reservaciones: Reservacion): List<Long>

    @Update
    fun update(reservacion: Reservacion)

    @Delete
    fun delete(reservacion: Reservacion)
}