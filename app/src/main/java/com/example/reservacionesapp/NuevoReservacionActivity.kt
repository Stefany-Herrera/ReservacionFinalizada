package com.example.reservacionesapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nuevo_reservacion.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoReservacionActivity : AppCompatActivity() {
    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_reservacion)
        fecha_Entrada.setOnClickListener { showDatePickerDialog() }
        fecha_salida.setOnClickListener { showDatePickerDialog2() }
        var idReservacion: Int? = null

        if (intent.hasExtra("reservacion")) {
            val reservacion = intent.extras?.getSerializable("reservacion") as Reservacion

            nombre_reserva.setText(reservacion.nombre)
            apellido_reserva.setText(reservacion.apellido)
            numero_reserva.setText(reservacion.telefono)
            tipo_reserva.setText(reservacion.tipo)
            email_reserva.setText(reservacion.email)
            fecha_Entrada.setText(reservacion.fecha_entrada)
            fecha_salida.setText(reservacion.fecha_salida)
            NHabitacion.setText(reservacion.habitacion)
            precio_reserva.setText(reservacion.precio)
            descripcion_reserva.setText(reservacion.descripcion)
            idReservacion = reservacion.idReservacion
           val imageUri = ImagenController.getImageUri(this, idReservacion.toLong())
            imagenSelect_iv.setImageURI(imageUri)



        }

        val database = AppDatabase.getDatabase(this)

        save_btn.setOnClickListener {

            val nombre= nombre_reserva.text.toString()
            val apellido= apellido_reserva.text.toString()
            val telefono= numero_reserva.text.toString()
            val tipo= tipo_reserva.text.toString()
            val email= email_reserva.text.toString()
            val precio= precio_reserva.text.toString()
            val descripcion= descripcion_reserva.text.toString()
            val fechaentrada = fecha_Entrada.text.toString()
            val fechasalida = fecha_salida.text.toString()
            val Nhabitacion= NHabitacion.text.toString()

            val reservacion = Reservacion(
                nombre,
                apellido,
                telefono,
                tipo,
                email,
                precio,
                descripcion,
                fechaentrada,
                fechaentrada,
                fechasalida,
                Nhabitacion,
                 R.drawable.ic_launcher_background)

            if (idReservacion != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    reservacion.idReservacion = idReservacion

                   database.reservaciones().update(reservacion)
                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImagenController.saveImage(this@NuevoReservacionActivity, idReservacion.toLong(), it)
                    }

                    this@NuevoReservacionActivity.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                   val id = database.reservaciones().insertAll(reservacion)[0]
                    imageUri?.let {
                        ImagenController.saveImage(this@NuevoReservacionActivity, id, it)
                    }

                    this@NuevoReservacionActivity.finish()
                }
            }
        }
        imagenSelect_iv.setOnClickListener {
            ImagenController.selectPhotoFromGallery(this,SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imageUri = data!!.data
                imagenSelect_iv.setImageURI(imageUri)
            }
        }
    }
    private fun showDatePickerDialog() {
        val datePicker =
            DatePickerFragment { day, month, year -> onDateSelecter(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun showDatePickerDialog2() {

        val datePicker2 =
            DatePickerFragment { day, month, year -> onDateSelecter2(day, month, year) }
        datePicker2.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelecter(day: Int, month: Int, year: Int) {
        fecha_Entrada.setText("$day/ $month/ $year")
    }

    fun onDateSelecter2(day: Int, month: Int, year: Int) {
        fecha_salida.setText("$day/ $month/ $year")
    }
}