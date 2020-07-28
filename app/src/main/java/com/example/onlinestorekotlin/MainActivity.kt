package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Person.email!=null){
            intentToHomeScreen()
        }

        activity_main_btnSignUp.setOnClickListener {
            var intent: Intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
            activity_main_btnLogin.setOnClickListener{

                val loginUrl:String= "http://192.168.42.102/OnlineStoreApp/login_app_user.php?email="+
                        edtLoginEmail.text.toString()+"&password="+edtLoginPassword.text.toString()
                val requestQ=Volley.newRequestQueue(this@MainActivity)
                val stringRequest=StringRequest(Request.Method.GET,loginUrl,
                Response.Listener { response ->
                    if(response.equals("there are no users exist")){
                        alertDialog("Message",response)
                    }else{
                        Person.email=edtLoginEmail.text.toString()
                        Toast.makeText(this@MainActivity,response,Toast.LENGTH_SHORT).show()
                           intentToHomeScreen()
                    }
                },Response.ErrorListener { error ->
                        alertDialog("Error",error.message.toString())
                    })
                requestQ.add(stringRequest)
            }
    }
    fun intentToHomeScreen(){
        var homeIntent= Intent(this@MainActivity,HomeScreen::class.java)
        startActivity(homeIntent)
    }
    fun alertDialog(title:String,message: String){
        var alertDialog=AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.create().show()
    }
}