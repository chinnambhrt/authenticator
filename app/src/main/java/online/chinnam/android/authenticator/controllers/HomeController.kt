package online.chinnam.android.authenticator.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

class HomeController(private val application: Application) : AndroidViewModel(application), IController, ILogger{

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    private val state = _state.asStateFlow()

    private val repository = TotpRepository(application)

    override fun getState(): StateFlow<State> {
        return state
    }

    /**
     * Fetches the list of totp from the database
     */
    fun fetchTotp(){

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.all()
            _state.value = _state.value.copy(totpList = list, isLoading = false)
            log("Fetched ${list.size} totp from the database")
        }
    }

    /**
     * Add a new account
     */
    fun addNewAccount(){
        log("Add new account")
    }


    /**
     * State of the home screen
     */
    data class State(
        val selectedTotp: TotpEntity? = null,
        val isLoading: Boolean = false,
        val totpList: List<TotpEntity> = emptyList()
    ): IState
}