package com.kuyodynamics.commcaresurveymanager.viewmodels.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuyodynamics.commcaresurveymanager.R
import com.kuyodynamics.commcaresurveymanager.domain.LoggedInUser
import com.kuyodynamics.commcaresurveymanager.repository.LoginRepo
import com.kuyodynamics.commcaresurveymanager.ui.login.LoggedInUserView
import com.kuyodynamics.commcaresurveymanager.ui.login.LoginFormState
import com.kuyodynamics.commcaresurveymanager.ui.login.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _user = MutableLiveData<LoggedInUser>()

    fun login(username: String, password: String, domainName: String) = viewModelScope.launch {
        // can be launched in a separate asynchronous job
        loginRepo.login(username, password, domainName)

        // Since this is LiveData, we can do it like this
        LoginResult(success = _user.value?.let { LoggedInUserView(displayName = it.firstName) }).also {

            _loginResult.value = it
        }

//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}