package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.authorization.AuthorizationActivity
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.FriendsFragment
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isGone
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.setTextOrHide
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.layout_zero_screen.*

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

        val viewState = presenter.viewState
        viewState.screen.subscribe(this, ::onChangeScreen)
        viewState.contentScreen.subscribe(this, ::showContent)
        viewState.zeroScreenData.subscribe(this, ::showZeroScreenData)

        presenter.onActivityCreated()

        layout_zero_screen_button.setOnClickListener { presenter.onClickRetry() }
    }

    private fun onChangeScreen(screen: Screen) {
        when (screen) {
            Screen.AUTHORIZATION -> showAuthorizationScreen()
            Screen.FRIENDS -> showFriendsScreen()
        }
    }

    private fun showContent(show: Boolean) {
        activity_content_layout_fragment_container.isGone = !show
        activity_content_layout_zero_screen.isGone = show
    }

    private fun showZeroScreenData(data: ZeroScreenData) {
        layout_zero_screen_text_view_title.text = data.title
        layout_zero_screen_text_view_subtitle.setTextOrHide(data.subtitle)
        layout_zero_screen_button.isGone = !data.retry
        layout_zero_screen_progressbar.isGone = !data.progress
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
