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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailTxt

class login : AppCompatActivity() {

    inner class Usuario{
        var success:String=""
        var token:String=""
        var name:String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnlogin.setOnClickListener {
            var pd=ProgressDialog(this)
            pd.setMessage("Please wait...")
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.show()


        var rq: RequestQueue=Volley.newRequestQueue(this)
        var sr=object:StringRequest(Request.Method.POST,Appinfo.web+"public/login",
            Response.Listener {response ->
                pd.hide()
                println(response)
                var g=Gson()
                var respuesta=g.fromJson(response,Usuario().javaClass)
                println(respuesta.success)
                println(respuesta.token)
                println(respuesta.name)


                if (respuesta.success=="true"){
                    Appinfo.email=emailTxt.text.toString()
                    Appinfo.password=passwordTxt.text.toString()
                    var i = Intent(this, home::class.java)
                    startActivity(i)
                    finish()
                }
                else Toast.makeText(this,"Login Failed!",Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                pd.hide()
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

            })
        {
            override fun getParams(): MutableMap<String, String> {
                var map= HashMap<String,String>()
                map.put("email",emailTxt.text.toString())
                map.put("password",passwordTxt.text.toString())

                return map
            }
        }

        rq.add(sr)
        }


        btnBack.setOnClickListener {  val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()}

    }
}
