package com.awsamplifyotp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        try {
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }
    fun registerUser(view: View?) {
        val password = findViewById<View>(R.id.txtPassword) as EditText
        val email = findViewById<View>(R.id.txtEmail) as EditText
        try {
            Amplify.Auth.signUp(
                email.text.toString(),
                password.text.toString(),
                AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email.text.toString()).build(),
                { result: AuthSignUpResult? ->
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Signup successful",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val i = Intent(this, OtpActivity::class.java)
                        val b = Bundle()
                        b.putString("USERNAME", email.text.toString())
                        i.putExtras(b)
                        startActivity(i)
                    }
                },
                { error: AuthException? ->
                    runOnUiThread {
                        if (error is AuthException.UsernameExistsException) {
                            Toast.makeText(
                                applicationContext,
                                "Signup failed because username exists.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Signup failed for a different reason.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("test", "error in main ", e)
        }
    }
}