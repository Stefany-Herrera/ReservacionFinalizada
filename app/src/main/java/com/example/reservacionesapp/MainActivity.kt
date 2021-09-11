package com.example.reservacionesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaReservaciones = emptyList<Reservacion>()

        val database = AppDatabase.getDatabase(this)

        database.reservaciones().getAll().observe(this, Observer {
            listaReservaciones = it

            val adapter = ReservacionesAdapter(this, listaReservaciones)

            lista.adapter = adapter
        })

        lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReservacionActivity::class.java)
            intent.putExtra("id", listaReservaciones[position].idReservacion)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, NuevoReservacionActivity::class.java)
            startActivity(intent)
        }
    }
}