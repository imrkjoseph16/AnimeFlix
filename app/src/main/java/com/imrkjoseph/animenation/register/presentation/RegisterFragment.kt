package com.imrkjoseph.animenation.register.presentation

import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.util.Default.Companion.EMAIL_VERIFICATION_MSG
import com.imrkjoseph.animenation.app.util.showFancyToast
import com.imrkjoseph.animenation.databinding.FragmentRegisterBinding
import com.imrkjoseph.animenation.register.data.UserDetails
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(bindingInflater = FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated() {
        super.onViewCreated()
        binding.apply {
            configureViews()
            setupObserver()
        }
    }

    private fun FragmentRegisterBinding.configureViews() {
        back.setOnClickListener { goToPreviousScreen() }
        signInUser.setOnClickListener { goToPreviousScreen() }
        signUpUser.setOnClickListener {
            validateFields(
                listOf(
                    inputFirstName,
                    inputLastName,
                    inputEmail,
                    inputPhoneNumber,
                    inputPassword
                )
            ).also { valid ->
                if (valid) submitForm(
                    UserDetails(
                        firstName = inputFirstName.text.toString(),
                        lastName = inputLastName.text.toString(),
                        email = inputEmail.text.toString(),
                        phoneNumber = inputPhoneNumber.text.toString(),
                        password = inputPassword.text.toString())
                )
            }
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            registerState.observe(viewLifecycleOwner) { state ->
                when(state) {
                    is SaveFireStoreDetailsSuccess -> binding.updateUIState(showLoading = false).also { goToPreviousScreen() }
                    is ShowRegisterLoading -> binding.updateUIState(showLoading = true)
                    is ShowRegisterDismissLoading -> binding.updateUIState(showLoading = false)
                    is EmailVerificationSuccess -> getAppActivity().showFancyToast(EMAIL_VERIFICATION_MSG, duration = FancyToast.LENGTH_LONG)
                    is ShowRegisterError -> state.throwable.message?.let {
                        getAppActivity().showFancyToast(state.throwable.message.toString(), FancyToast.ERROR)
                    }
                }
            }
        }
    }

    private fun validateFields(listOfEditText: List<EditText>) =
        validationUtil.validateFields(
            listOfEditText
        )

    private fun submitForm(userDetails: UserDetails) = viewModel.registerCredentials(userDetails)

    private fun goToPreviousScreen() = findNavController().popBackStack()

    private fun FragmentRegisterBinding.updateUIState(showLoading: Boolean) = loadingWidget.apply { isShowLoading = showLoading }
}