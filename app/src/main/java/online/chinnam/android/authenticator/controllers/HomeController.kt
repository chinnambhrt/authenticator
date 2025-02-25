package online.chinnam.android.authenticator.controllers

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import online.chinnam.android.authenticator.activity.SettingsActivity
import online.chinnam.android.authenticator.entity.TotpEntity
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.iface.ILogger
import online.chinnam.android.authenticator.iface.IState
import online.chinnam.android.authenticator.repository.TotpRepository
import online.chinnam.android.authenticator.totp.TotpGenerator
import online.chinnam.android.authenticator.totp.TotpTimer

class HomeController(private val application: Application) : AndroidViewModel(application),
    IController, ILogger {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    private val state = _state.asStateFlow()

    private val repository = TotpRepository(application)

    private val timerMap = mutableMapOf<Int,TotpTimer>()

    private val generatorMap = mutableMapOf<Int, TotpGenerator>()

    override fun getState(): StateFlow<State> {
        return state
    }

    /**
     * Fetches the list of totp from the database
     */
    fun fetchTotp() {

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
    fun addNewAccount() {
        log("Add new account")
        scanQrCode()
    }


    /**
     * Start the camera when the user clicks on the QR code icon
     * when the MFA filter is selected
     */
    private fun scanQrCode() {

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(application, options)

        scanner.startScan().addOnSuccessListener {
            val rawString = it.rawValue
            log("Barcode value: $rawString")
            if (rawString != null) {
                log("Barcode value: $rawString")
                val entity = TotpEntity.fromUrl(rawString)
                saveEntity(entity)
            }
        }.addOnCanceledListener {
            log("Barcode scanning cancelled")
            Toast.makeText(
                application,
                "Cancelled",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            log("Barcode scanning failed, error: ${it.message}")
            Toast.makeText(
                application,
                "Failed: ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    /**
     * Save the entity to the database
     */
    private fun saveEntity(entity: TotpEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(entity)
            fetchTotp()
        }
    }


    fun getTotpTimer(period: Int): TotpTimer {
        return timerMap.getOrPut(period) {
            TotpTimer(period)
        }
    }

    fun getGenerator(t: TotpEntity): TotpGenerator{
        if(t.id == null){
            return TotpGenerator("INVALIDSECRET",30)
        }
        return generatorMap.getOrPut(t.id){
            TotpGenerator.from(t)
        }
    }

    fun onSettingsClicked(){
        log("Settings clicked")
        val intent = Intent(application, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }


    /**
     * State of the home screen
     */
    data class State(
        val selectedTotp: TotpEntity? = null,
        val isLoading: Boolean = false,
        val totpList: List<TotpEntity> = emptyList()
    ) : IState
}