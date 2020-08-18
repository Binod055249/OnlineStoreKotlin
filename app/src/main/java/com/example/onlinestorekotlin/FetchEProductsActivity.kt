package com.example.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_e_products.*

class FetchEProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_e_products)

        var productsList=ArrayList<EProducts>()

        val selectBrand:String= intent.getStringExtra("BRAND")
        txtBrandName.text="Products of $selectBrand"
      val productsUrl =  "http://onlinestoreappkotlin.epizy.com/OnlineStoreApp/fetch_eproducts.php?brand=$selectBrand"
      val requestQ= Volley.newRequestQueue(this@FetchEProductsActivity)
       val jsonAR=JsonArrayRequest(Request.Method.GET,productsUrl,null,
       Response.Listener { response ->

           for(productJOIndex in 0.until(response.length())) {
               productsList.add(EProducts(response.getJSONObject(productJOIndex).getInt("id"),
               response.getJSONObject(productJOIndex).getString("name"),
               response.getJSONObject(productJOIndex).getInt("price"),
               response.getJSONObject(productJOIndex).getString("picture")))
           }
           val pAdapter=EProductAdapter(this@FetchEProductsActivity,productsList)
           productsRV.layoutManager=LinearLayoutManager(this@FetchEProductsActivity)
           productsRV.adapter=pAdapter
       },Response.ErrorListener { error ->
         val alertDialog=AlertDialog.Builder(this@FetchEProductsActivity)
               alertDialog.setTitle("Error")
               alertDialog.setMessage(error.message)
               alertDialog.create().show()
           })
        requestQ.add(jsonAR)
    }
}