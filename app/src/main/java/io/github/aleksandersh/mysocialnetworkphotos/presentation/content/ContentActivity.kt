package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.presentation.authorization.AuthorizationActivity

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val intent = Intent(applicationContext, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
