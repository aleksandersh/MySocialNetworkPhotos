package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.presentation.content.ContentActivity
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isDisplayed
import kotlinx.android.synthetic.main.fragment_sign_in.*

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

        getAppCompatActivity().setSupportActionBar(fragment_sign_in_toolbar)

        viewState.complete.subscribe(this, ::showContentActivity)
        viewState.progress.subscribe(this, ::showProgress)
        viewState.error.subscribe(this, ::showError)

        fragment_sign_in_button_login.setOnClickListener { onClickLogIn() }

        fragment_sign_in_edit_text_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                onClickLogIn()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun showLoading(loading: Boolean) {
        fragment_sign_in_progressbar.isDisplayed = loading
        fragment_sign_in_button_login.isEnabled = !loading
        fragment_sign_in_edit_text_login.isEnabled = !loading
        fragment_sign_in_edit_text_password.isEnabled = !loading
    }

    private fun onClickLogIn() {
        val login = fragment_sign_in_edit_text_login.text
        val password = fragment_sign_in_edit_text_password.text
        presenter.onClickLogIn(login, password)
    }

    private fun showContentActivity(show: Boolean) {
        if (show) {
            val newIntent = Intent(requireContext(), ContentActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun showProgress(show: Boolean) {
        fragment_sign_in_progressbar.isDisplayed = show
    }

    private fun showError(error: String) {
        if (error.isNotEmpty()) {
            fragment_sign_in_input_layout_password.error = error
        } else {
            fragment_sign_in_input_layout_password.error = null
        }
    }

    private fun getAppCompatActivity(): AppCompatActivity {
        return requireActivity() as AppCompatActivity
    }
}