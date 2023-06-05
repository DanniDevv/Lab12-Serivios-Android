package com.miempresa.serviciowebv4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnIngresar.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = "http://172.23.4.132:3000/usuarios?"
            url = url + "usuario=" + usuario + "&clave=" + clave

            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        Toast.makeText(
                            applicationContext,
                            "Validacion de usuario: " + valor.getString("usuario") +
                                    "con clave: " + valor.getString("clave") + "correo",
                            Toast.LENGTH_LONG
                        ).show()
                    }catch (e: JSONException){
                        Toast.makeText(
                            applicationContext,
                            "Error en las credenciales proporcionadas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
        btnRegistrarse.setOnClickListener {
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()

            // Verificar si los campos están vacíos
            if (usuario.isEmpty() || clave.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Por favor, complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://172.23.4.132:3000/usuarios"

            val jsonBody = JSONObject()
            jsonBody.put("usuario", usuario)
            jsonBody.put("clave", clave)

            val stringRequest = JsonObjectRequest(Request.Method.POST, url, jsonBody,
                Response.Listener { response ->
                    try {
                        Toast.makeText(
                            applicationContext,
                            "Registro exitoso",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error en el registro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Error en el registro",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            queue.add(stringRequest)
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }
}