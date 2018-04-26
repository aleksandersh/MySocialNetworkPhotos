package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.presentation.content.ContentActivity
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isGone
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.setTextOrHide
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.layout_zero_screen.*

class SignInFragment : Fragment(), SignInView {

    private lateinit var presenter: SignInPresenter
    private lateinit var viewState: SignInViewState

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val appComponent = Tree.applicationComponent.provide(SignInView.TAG)
        val authComponent = Tree.applicationComponent.authorizationComponent.provide(SignInView.TAG)

        val presenterProvider = appComponent.presenterProvider
        val presenterFactory = authComponent.signInPresenterFactory

        presenter = presenterProvider.provide(this, SignInView.TAG, presenterFactory)
        viewState = presenter.viewState

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_sign_in_webview.webViewClient = AuthWebViewClient()

        viewState.complete.subscribe(this, ::showContentActivity)
        viewState.progress.subscribe(this, ::showProgress)
        viewState.cancel.subscribe(this, ::cancelActivity)
        viewState.zeroScreenData.subscribe(this, ::showZeroScreenData)
        viewState.contentScreen.subscribe(this, ::showContent)
        viewState.loadUrl.subscribe(this, ::loadUrl)

        layout_zero_screen_button.setOnClickListener { presenter.onClickRetry() }
    }

    private fun showContentActivity(show: Boolean) {
        if (show) {
            val newIntent = Intent(requireContext(), ContentActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun showProgress(show: Boolean) {
        fragment_sign_in_progressbar.isGone = !show
    }

    private fun cancelActivity(cancel: Boolean) {
        if (cancel) {
            requireActivity().finish()
        }
    }

    private fun showContent(show: Boolean) {
        fragment_sign_in_webview.isGone = !show
        fragment_sign_in_layout_zero_screen.isGone = show
    }

    private fun showZeroScreenData(data: ZeroScreenData) {
        layout_zero_screen_text_view_title.text = data.title
        layout_zero_screen_text_view_subtitle.setTextOrHide(data.subtitle)
        layout_zero_screen_button.isGone = !data.retry
        layout_zero_screen_progressbar.isGone = !data.progress
    }

    private fun loadUrl(url: String) {
        fragment_sign_in_webview.loadUrl(url)
    }

    private inner class AuthWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return presenter.onUrlLoading(url)
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            presenter.onReceivedError(error)
        }
    }
}