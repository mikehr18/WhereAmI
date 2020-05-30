package com.example.whereami

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_registro.*

class registro : AppCompatActivity() {


    inner class Usuario{
        var status:String=""
        var token:String=""
        var name:String=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnUnirse.setOnClickListener {


            if (passwordTxt.text.toString()==password2.text.toString()){
            var pd= ProgressDialog(this)
            pd.setMessage("Please wait...")
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.show()


            var rq: RequestQueue = Volley.newRequestQueue(this)
            var sr=object: StringRequest(
                Request.Method.POST,Appinfo.web+"public/addUser",
                Response.Listener { response ->
                    pd.hide()
                    println(response)
                    var g= Gson()
                    var respuesta=g.fromJson(response,Usuario().javaClass)
                    println(respuesta.status)
                    println(respuesta.token)
                    println(respuesta.name)


                    if (respuesta.status=="created"){
                        Appinfo.fullname=name.text.toString()
                        Appinfo.cellphone=telefono.text.toString()
                        Appinfo.email=emailTxt.text.toString()
                        Appinfo.password=passwordTxt.text.toString()
                        var i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        finish()
                        Toast.makeText(this,"REGISTRO REALIZADO",Toast.LENGTH_LONG).show()
                    }
                    else Toast.makeText(this,"Registro NO REALIZADO",Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    pd.hide()
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
            {
                override fun getParams(): MutableMap<String, String> {
                    var map= HashMap<String,String>()
                    map.put("fullName",name.text.toString())
                    map.put("cellphone",telefono.text.toString())
                    map.put("email",emailTxt.text.toString())
                    map.put("password",passwordTxt.text.toString())

                    return map
                }
            }

            rq.add(sr)
        }
            else{
                Toast.makeText(this,"Las contraseñas no coinciden!",Toast.LENGTH_LONG).show()
            }

        }

    }
}
