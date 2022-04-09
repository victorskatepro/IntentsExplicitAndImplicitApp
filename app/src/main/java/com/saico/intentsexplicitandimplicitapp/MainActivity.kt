package com.saico.intentsexplicitandimplicitapp

import android.R.attr
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import android.R.attr.data
import android.app.Activity
import android.widget.Button
import android.widget.EditText


const val ACTIVITY_A_REQUEST = 991
const val ACTIVITY_B_REQUEST = 992


class MainActivity : AppCompatActivity() {

    private lateinit var edtCode: EditText
    private lateinit var btnImplicit: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCode = findViewById(R.id.edtCodeInput)
        btnImplicit = findViewById(R.id.btnExplicit)

        edtCode.setText("TEXTO DIFERENTE")

        btnImplicit.setOnClickListener {}
    }

    fun sendExplicit(view: android.view.View) {

        val code = edtCodeInput.text.toString()
        val fullName = edtFullNameInput.text.toString()
        val amount = edtAmountInput.text.toString()

        validateInputFields(code, fullName, amount)
        goDetailActivity(code, fullName, amount)
    }

    fun sendImplicit(view: android.view.View) {
        val code = edtCodeInput.text.toString()
        val fullName = edtFullNameInput.text.toString()
        val amount = edtAmountInput.text.toString()

        validateInputFields(code, fullName, amount)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, "code: $code\nfullname: $fullName\namount: $amount")
        intent.type = "text/plain"
        startActivity(intent)

    }

    private fun goDetailActivity(code: String, fullName: String, amount: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PARAMETER_EXTRA_CODE, code)
        intent.putExtra(PARAMETER_EXTRA_FULL_NAME, fullName)
        intent.putExtra(PARAMETER_EXTRA_AMOUNT, amount)
        startActivity(intent)
    }


    private fun validateInputFields(code: String, fullName: String, amount: String) {
        if (code.isBlank() && fullName.isBlank() && amount.isBlank()) {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show()
            return
        }
    }

    fun callActivityA(view: android.view.View) {
        Log.d(TAG, "callActivityA")
        startActivityForResult(Intent(this, PantallaAActivity::class.java), ACTIVITY_A_REQUEST)
    }

    fun callActivityB(view: android.view.View) {
        Log.d(TAG, "callActivityB")
        startActivityForResult(Intent(this, PantallaBActivity::class.java), ACTIVITY_B_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "requestCode:$requestCode")
        Log.d(TAG, "resultCode:$resultCode")
        Log.d(TAG, "data:" + attr.data)

        when (requestCode) {
            ACTIVITY_A_REQUEST -> Toast.makeText(this, "Regresamos del Activity A", Toast.LENGTH_SHORT).show()
            ACTIVITY_B_REQUEST -> {
                Log.d(TAG, "Regresamos del Activity B")
                if (resultCode === RESULT_OK) {
                    val valor: String = data?.extras?.getString("valor").toString()
                    Toast.makeText(this, "valor: $valor", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "valor: $valor")
                }
            }
        }

    }
}