package com.example.reservacionesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_nuevo_reservacion.*
import kotlinx.android.synthetic.main.activity_reservacion.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservacionActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var reservacion: Reservacion
    private lateinit var reservacionLiveData: LiveData<Reservacion>
    private val EDIT_ACTIVITY = 49

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservacion)

        database = AppDatabase.getDatabase(this)

        val idReservacion = intent.getIntExtra("id", 0)
        val imageUri = ImagenController.getImageUri(this, idReservacion.toLong())
        imagen.setImageURI(imageUri)

        reservacionLiveData = database.reservaciones().get(idReservacion)

        reservacionLiveData.observe(this, Observer {
            reservacion = it

            nombre_reservacion.text = reservacion.nombre
            apellido_reservacion.text= reservacion.apellido
            numero_reservacion.text = reservacion.telefono
            email_reservacion.text = reservacion.email
            tipo_reservacion.text = reservacion.tipo
            precio_reservacion.text = reservacion.precio
            descripcion_reservacion.text = reservacion.descripcion
            //et_habitacion.text = reservacion.habitacion
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reservacion_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> {
                val intent = Intent(this, NuevoReservacionActivity::class.java)
                intent.putExtra("reservacion", reservacion)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }

            R.id.delete_item -> {
                reservacionLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.reservaciones().delete(reservacion)
                    ImagenController.deleteImage(this@ReservacionActivity, reservacion.idReservacion.toLong() )
                    this@ReservacionActivity.finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imagen.setImageURI((data!!.data))
            }
        }
    }
}