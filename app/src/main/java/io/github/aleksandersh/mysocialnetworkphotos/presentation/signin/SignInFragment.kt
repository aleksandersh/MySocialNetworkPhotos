package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.extensions.isDisplayed
import io.github.aleksandersh.simplemvp.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), SignInView {

    private lateinit var presenter: SignInPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SignInPresenter(ResourceManager(requireContext()), this)

        getAppCompatActivity().setSupportActionBar(fragment_sign_in_toolbar)

        fragment_sign_in_button_login.setOnClickListener { onClickLogIn() }

        fragment_sign_in_edit_text_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                onClickLogIn()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        val viewModel: SignInViewModel =
            ViewModelProvider().getViewModel(this, "1", SignInViewModelFactory())
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

    private fun getAppCompatActivity(): AppCompatActivity {
        return requireActivity() as AppCompatActivity
    }
}