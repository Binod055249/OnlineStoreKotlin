package com.example.onlinestorekotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        if(Person.email!=null){
//            finish()
//            intentToHomeScreen()
//        }

        activity_sign_up_btnSignUP.setOnClickListener{

            if((edtSignUpPassword.text.toString()).equals(edtSignUPConfirmPassword.text.toString())){

                val signUpUrl:String="http://onlinestoreappkotlin.epizy.com/OnlineStoreApp/join_new_user.php?email=" +
                        "$edtSignUpEmail.text.toString()"+
                        "&username="+edtSignUpUsername.text.toString()+
                        "&password="+edtSignUpPassword.text.toString()
                val requestQ:RequestQueue= Volley.newRequestQueue(this@SignUpActivity)
                val stringRequest= StringRequest(Request.Method.GET,signUpUrl,
                Response.Listener { response ->

                    if(response.equals("A user with email address already exists")) {
                       alertDialog("Message",response)
                    }else{
                        Person.email=edtSignUpEmail.text.toString()
                        Toast.makeText(this@SignUpActivity,response,Toast.LENGTH_SHORT).show()
                        intentToHomeScreen()
                    }

                },Response.ErrorListener {error ->

                       alertDialog("Error",error.message.toString())
                    })
                 requestQ.add(stringRequest)
            }else{
                alertDialog("Alert","Password Mismatch")
            }
        }
        activity_sign_up_btnLogin.setOnClickListener {
            finish()
        }
    }
    fun intentToHomeScreen(){
        var homeIntent= Intent(this@SignUpActivity,HomeScreen::class.java)
       startActivity(homeIntent)
    }
    fun alertDialog(title:String,message: String){
        var alertDialog=AlertDialog.Builder(this@SignUpActivity)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.create().show()
    }
}