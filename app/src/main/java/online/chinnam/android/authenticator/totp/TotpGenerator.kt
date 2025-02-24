package online.chinnam.android.authenticator.totp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import online.chinnam.android.authenticator.entity.TotpEntity
import online.chinnam.android.authenticator.iface.ILogger
import org.apache.commons.codec.binary.Base32
import java.time.Instant
import java.util.Date
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs

class TotpGenerator(
    private val secretKey: String,
    private val interval: Long = 30,
    private val digits: Int = 6,
    private val algorithm: String = "Sha1",
    private val timeOffset: Long = 0,
    private val onOtpChange: ((String) -> Unit)? = null
) : ILogger {

    private var secretKeyBytes = Base32.builder().get().decode(secretKey)
    private var otpGenerationTimer: Timer? = null
    private var progressTimer: Timer? = null

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private fun run() = runBlocking {

        launch(Dispatchers.IO) {
            if (secretKeyBytes.isEmpty()) return@launch
            publishState(getOtp())
            val currentSeconds = System.currentTimeMillis() / 1000
            val remaining = abs(interval - currentSeconds) % interval
            val delay = interval - remaining
            log("Setting Delay: $delay for otp generation")
            progressTimer = fixedRateTimer(period = (interval*1000), initialDelay = (delay*1000)) {
                publishState(getOtp())
            }
        }
    }

    fun init() {
        run()
    }


    private fun getPercentageCompleted(): Float {
        val seconds = Instant.now().epochSecond % interval
        val remaining = interval - seconds
        val percentage  = 1 - (remaining.toFloat() / interval.toFloat())
        return percentage
    }

    /**
     * Generate a TOTP based on the provided secret, time, and optional parameters.
     */
    private fun getOtp(): String {
        try {
            val otp = TOTP.generateTOTP(
                secretKey = secretKeyBytes,
                currentTimeMillis = System.currentTimeMillis(),
                algorithm = algorithm,
                timeStepSeconds = interval,
                codeDigits = digits
            )
            return otp
        } catch (e: Exception) {
            log("Failed to generate OTP", e)
            return "000 000"
        }
    }

    /**
     * Stop the OTP generation
     */
    fun stopOtpGeneration() {
        otpGenerationTimer?.cancel()
    }



    private fun publishState(newState: State){
        _state.value = newState
    }

    private fun publishState(otp:String){
        publishState(State(otp))
    }


    data class State(
        val otp: String = "000 000",
    )


    companion object {

        fun from(entity: TotpEntity): TotpGenerator {
            return TotpGenerator(
                secretKey = entity.secret,
                interval = entity.period.toLong(),
                digits = entity.digits,
                algorithm = entity.algorithm,
                timeOffset = 0
            )
        }

    }

}