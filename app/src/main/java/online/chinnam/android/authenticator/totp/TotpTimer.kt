package online.chinnam.android.authenticator.totp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import online.chinnam.android.authenticator.iface.ILogger
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs

class TotpTimer(private val period: Int): ILogger {

    private val timer: Timer

    private val _percentage: MutableStateFlow<Float> = MutableStateFlow(0f)
    val percentage = _percentage.asStateFlow()


    init {
        val currentSeconds = System.currentTimeMillis() / 1000
        val delay = abs(period - currentSeconds) % period
        timer = fixedRateTimer(
            period = 1000,
            initialDelay = 0,
            action = { tick() })
    }


    private fun tick() {
        val currentSeconds = System.currentTimeMillis() / 1000
        val delay = abs(period - currentSeconds) % period
        val percent = (delay/period.toFloat())
        _percentage.value = percent
    }

}