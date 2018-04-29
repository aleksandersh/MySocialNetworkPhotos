package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.authorization.AuthorizationActivity
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.FriendsFragment

class ContentActivity : AppCompatActivity(), ContentView {

    private lateinit var presenter: ContentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val appComponent = Tree.applicationComponent.provide(ContentView.TAG)
        val contentComponent = Tree.applicationComponent.contentComponent.provide(ContentView.TAG)

        val presenterProvider = appComponent.presenterProvider
        val presenterFactory = contentComponent.presenterFactory

        presenter = presenterProvider.provide(this, ContentView.TAG, presenterFactory)

        presenter.viewState.screen.subscribe(this, ::onChangeScreen)

        presenter.onActivityCreated()
    }

    private fun onChangeScreen(screen: Screen) {
        when (screen) {
            Screen.AUTHORIZATION -> showAuthorizationScreen()
            Screen.FRIENDS -> showFriendsScreen()
        }
    }

    private fun showAuthorizationScreen() {
        val intent = Intent(applicationContext, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showFriendsScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_content_layout_fragment_container, FriendsFragment())
            .commit()
    }
}
