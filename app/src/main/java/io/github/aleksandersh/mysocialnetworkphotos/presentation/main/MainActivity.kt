package io.github.aleksandersh.mysocialnetworkphotos.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.presentation.authorization.AuthorizationActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(applicationContext, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
