package online.chinnam.android.authenticator.iface

import kotlinx.coroutines.flow.StateFlow

fun interface IController {
    fun getState(): StateFlow<IState>
}