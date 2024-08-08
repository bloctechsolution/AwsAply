package com.awsamplifyotp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify


class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_otp)

    }
    fun validateOtp(view: View?) {
        val bundle = intent.extras
        val user = bundle!!.getString("USERNAME")
        val otp = findViewById<View>(R.id.txtOtp) as EditText
        Amplify.Auth.confirmSignUp(
            user!!,
            otp.text.toString(),
            { result: AuthSignUpResult ->
                runOnUiThread {
                    if (result.isSignUpComplete){
                        Toast.makeText(this, "Confirm signUp succeeded", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "Confirm sign up not complete", Toast.LENGTH_SHORT).show()
                    }
                    Log.i(
                        "AuthQuickstart",
                        if (result.isSignUpComplete) "Confirm signUp succeeded" else "Confirm sign up not complete"
                    )
                }

            },
            { error: AuthException ->
                runOnUiThread {
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }


                Log.e(
                    "AuthQuickstart",
                    error.toString()
                )
            }
        )
    }
}