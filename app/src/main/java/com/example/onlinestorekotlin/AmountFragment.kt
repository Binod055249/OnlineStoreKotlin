package com.example.onlinestorekotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class AmountFragment : android.app.DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var fragmentView = inflater.inflate(R.layout.fragment_amount, container, false)

        var edtEnterAmount=fragmentView.findViewById<EditText>(R.id.edtEnterAmount)
        var btnAddToCart=fragmentView.findViewById<ImageButton>(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {
           var pToUrl= "http://192.168.42.102/OnlineStoreApp/insert_temporary_order.php?" +
                   "email=${Person.email}&product_id=${Person.addToCartProductId}&amount=${edtEnterAmount.text.toString()}"
            var requestQ=Volley.newRequestQueue(activity)
            var stringRequest=StringRequest(Request.Method.GET,pToUrl,
            Response.Listener { response ->

                var intent= Intent(activity,CartProductsActivity::class.java)
                startActivity(intent)

            },Response.ErrorListener { error ->
                    var alertDialog=AlertDialog.Builder(activity)
                    alertDialog.setTitle("Error!!")
                    alertDialog.setMessage(error.message)
                    alertDialog.create().show()

                })
            requestQ.add(stringRequest)
        }

        return fragmentView

    }

}
