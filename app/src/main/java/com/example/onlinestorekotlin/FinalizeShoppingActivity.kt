package com.example.onlinestorekotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.math.BigDecimal


class FinalizeShoppingActivity : AppCompatActivity() {

    var ttPrice:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        var calculateUrl="http://192.168.42.102/OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        var requestQ=Volley.newRequestQueue(this)
        var stringRequest=StringRequest(Request.Method.GET,calculateUrl,
        Response.Listener { response ->
          btnPaymentProcessing.text="Pay $$response via Paypal"
            ttPrice=response.toLong()

        },Response.ErrorListener { error ->
                var alertDialog=AlertDialog.Builder(this)
                alertDialog.setTitle("Error!!")
                alertDialog.setMessage(error.message)
                alertDialog.create().show()
            })
        requestQ.add(stringRequest)

        var payPalConfig:PayPalConfiguration=PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(MyPaypal.clientId)
        var ppService= Intent(this, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfig)
          startService(ppService)

        btnPaymentProcessing.setOnClickListener {

            var ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice),
                "USD","online Store Kotlin",PayPalPayment.PAYMENT_INTENT_SALE)
            var paypalPaymentIntent=Intent(this,PaymentActivity ::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,ppProcessing)
            startActivityForResult(paypalPaymentIntent,1000)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1000){

            if(resultCode== Activity.RESULT_OK){
                var intent=Intent(this,ThankYouActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"sorry! something went wrong try again ",Toast.LENGTH_SHORT).show()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {

        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }
}