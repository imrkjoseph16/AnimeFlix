package com.imrkjoseph.animenation.login.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.widget.DialogFactory
import com.imrkjoseph.animenation.app.shared.widget.DialogFactory.Companion.showCustomDialog
import com.imrkjoseph.animenation.app.util.Default.Companion.EMAIL_NOT_VERIFIED_MSG
import com.imrkjoseph.animenation.app.util.showFancyToast
import com.imrkjoseph.animenation.databinding.FragmentLoginBinding
import com.imrkjoseph.animenation.register.data.UserDetails
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(bindingInflater = FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        binding.apply {
            configureViews()
            setupObserver()
        }
    }

    private fun FragmentLoginBinding.configureViews() {
        // Check push notification permission,
        // this is only necessary for API level >= 33 (TIRAMISU),
        // due to the new Android 13 updates.
        checkNotificationPermission()
        back.setOnClickListener { goBackToPreviousScreen() }
        signInUser.setOnClickListener {
            validateFields(
                listOf(inputEmail, inputPassword)
            ).also { valid ->
                if (valid) loginViewModel.loginCredentials(
                    UserDetails(
                        email = inputEmail.text.toString(),
                        password = inputPassword.text.toString()
                    )
                )
            }
        }

        signUpUser.setOnClickListener { navigateActivityRegister() }

        // onBackPressed from activity
        onBackPressedCallBack(::goBackToPreviousScreen)
    }

    private fun validateFields(listOfEditText: List<EditText>) =
        validationUtil.validateFields(
            listOfEditText
        )

    private fun setupObserver() {
        with(loginViewModel) {
            loginState.observe(viewLifecycleOwner) { state ->
                when(state){
                    is ShowLoginSuccess -> state.handleSuccess()
                    is ShowLoginLoading -> binding.updateUIState(showLoading = true)
                    is ShowLoginDismissLoading -> binding.updateUIState(showLoading = false)
                    is ShowLoginError -> state.handleError().also { binding.updateUIState(showLoading = false) }
                }
            }
        }
    }

    private fun ShowLoginSuccess.handleSuccess() {
        if (isVerified == true) navigateActivityDashboard()
        else getAppActivity().showFancyToast(EMAIL_NOT_VERIFIED_MSG, FancyToast.INFO, duration = FancyToast.LENGTH_LONG)
    }

    private fun goBackToPreviousScreen() = findNavController().popBackStack()

    private fun ShowLoginError.handleError() = getAppActivity().showFancyToast(throwable.message.toString(), FancyToast.ERROR)

    private fun FragmentLoginBinding.updateUIState(showLoading: Boolean) = loadingWidget.apply { isShowLoading = showLoading }

    private fun navigateActivityDashboard() = findNavController().navigate(LoginFragmentDirections.actionToDashboardScreen())

    private fun navigateActivityRegister() = findNavController().navigate(LoginFragmentDirections.actionToRegisterScreen())

    private fun checkNotificationPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    /* context = */ getAppActivity(),
                    /* permission = */ Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            true
        } else false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted.not()) showPermissionDialog()
    }

    private fun showPermissionDialog() {
        showCustomDialog(
            context = getAppActivity(),
            dialogAttributes = DialogFactory.DialogAttributes(
                title = getString(R.string.dialog_permission_required_title),
                subTitle = getString(R.string.dialog_subtitle),
                primaryButtonTitle = getString(R.string.action_cancel),
                secondaryButtonTitle = getString(R.string.action_settings)
            ), secondaryButtonClicked = {
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        )
    }
}