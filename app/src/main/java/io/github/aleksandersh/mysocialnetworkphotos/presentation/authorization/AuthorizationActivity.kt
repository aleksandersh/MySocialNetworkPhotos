package io.github.aleksandersh.mysocialnetworkphotos.presentation.authorization

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.presentation.signin.SignInFragment

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        supportFragmentManager.run {
            if (findFragmentById(R.id.activity_authorization_fragment_container) == null) {
                beginTransaction()
                    .replace(R.id.activity_authorization_fragment_container, SignInFragment())
                    .commit()
            }
        }
    }
}
