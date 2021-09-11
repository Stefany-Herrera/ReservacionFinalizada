package com.example.reservacionesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
    }

    fun startAplication(view: android.view.View) {
        var usuario = findViewById<EditText>(R.id.editUsuario).text.toString();
        var contraseña = findViewById<EditText>(R.id.editContraseña).text.toString();
        if (usuario == "karen" && contraseña == "1234") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            val toast = Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG)
            toast.show()

        } else {
            val toast = Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG)
            toast.show()
        }
    }
}