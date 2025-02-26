package online.chinnam.android.authenticator.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import online.chinnam.android.authenticator.entity.TotpEntity
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.iface.ILogger
import online.chinnam.android.authenticator.iface.IState
import online.chinnam.android.authenticator.repository.TotpRepository
import online.chinnam.android.authenticator.utils.getAlgorithmName

class AddTotpController(private val application: Application) : AndroidViewModel(application),
    IController, ILogger {

    private val state = MutableStateFlow(State())

    val finish = MutableLiveData<Boolean>(false)

    /**
     * Get the current state of the controller
     */
    override fun getState(): StateFlow<State> {
        return state.asStateFlow()
    }

    fun update(newState: State) {
        state.value = newState
    }

    fun save() {
        try {

            validate()

            saveToRepository()

        } catch (e: Exception) {
            state.value = state.value.copy(error = e.message ?: "An error occurred")
        }
    }


    private fun saveToRepository() {

        val repository = TotpRepository(application)

        val totpEntity = TotpEntity.EMPTY.copy(
            display = state.value.name,
            accountName = state.value.name,
            issuer = state.value.issuer,
            secret = state.value.secret,
            algorithm = getAlgorithmName(state.value.algorithm),
            digits = state.value.digits,
            period = state.value.period
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(totpEntity)
            finish.postValue(true)
        }

    }

    private fun validate() {

        val s = state.value

        require(s.name.isNotEmpty() && s.name.length > 5) { "Label must be minimum 5 characters" }
        require(s.issuer.isNotEmpty() && s.issuer.length > 5) { "Issuer must be minimum 5 characters" }
        require(s.secret.isNotEmpty() && s.secret.length > 5) { "Secret must be minimum 5 characters" }
        require(s.digits in 6..8) { "Digits must be between 6 and 8" }
        require(s.period in 30..60) { "Period must be between 30 and 60" }
        require(s.algorithm.isNotEmpty()) { "Algorithm cannot be empty" }
        require(
            s.algorithm in listOf(
                "SHA1",
                "SHA256",
                "SHA512"
            )
        ) { "Invalid Algorithm. Valid algorithms are SHA1, SHA256, SHA512" }

    }


    data class State(
        val error: String = "",
        val name: String = "",
        val issuer: String = "",
        val secret: String = "",
        val algorithm: String = "SHA1",
        val digits: Int = 6,
        val period: Int = 30,
    ) : IState

}