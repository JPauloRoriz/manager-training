package com.example.managertraining.presentation.viewmodel.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.EmailOrPasswordInvalidException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.usecase.login.contract.LoginUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginEvent
import com.example.managertraining.presentation.viewmodel.login.model.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validationLoginUseCase: LoginUseCase,
    private val context: Context
) : ViewModel() {
    val stateLiveData = MutableLiveData(LoginState())
    val eventLiveData = SingleLiveEvent<LoginEvent>()


    fun tapOnRegister() {
        eventLiveData.value = LoginEvent.GoToRegister
    }

    suspend fun tapOnLogin(email: String, password: String) {
        viewModelScope.launch{
            stateLiveData.setLoadingLogin(true)
            validationLoginUseCase.invoke(email, password)
                .onSuccess { user ->
                    stateLiveData.setLoadingLogin(false)
                    stateLiveData.setMessageErrorLogin("")
                    eventLiveData.value = LoginEvent.SuccessLogin(user)

                }.onFailure { error ->
                    when (error) {
                        is EmailOrPasswordInvalidException -> {
                            stateLiveData.setMessageErrorLogin(context.getString(R.string.email_or_password_invald))
                        }
                        is NoConnectionInternetException -> {
                            stateLiveData.setMessageErrorLogin(context.getString(R.string.not_internet))
                        }
                        is DefaultException -> {
                            stateLiveData.setMessageErrorLogin(context.getString(R.string.login_not_realized))
                        }
                        else -> {
                            stateLiveData.setMessageErrorLogin(error.message.toString())
                        }
                    }

                }
        }
    }

    private fun MutableLiveData<LoginState>.setLoadingLogin(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }

    private fun MutableLiveData<LoginState>.setMessageErrorLogin(message: String) {
        this.value = this.value?.copy(isLoading = false, messageError = message)
    }


}