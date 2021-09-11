package com.example.reservacionesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_reservacion.view.*

class ReservacionesAdapter(private val mContext: Context, private val listaReservaciones: List<Reservacion>) : ArrayAdapter<Reservacion>(mContext, 0, listaReservaciones) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_reservacion, parent, false)

        val reservacion = listaReservaciones[position]

        layout.nombre_res.text = reservacion.nombre
        layout.apellido_res.text = reservacion.apellido
        layout.numero_res.text = reservacion.telefono
        layout.correo_res.text = reservacion.email
        val imageUri = ImagenController.getImageUri(mContext, reservacion.idReservacion.toLong())
        layout.imageView.setImageURI(imageUri)

        return layout
    }

}