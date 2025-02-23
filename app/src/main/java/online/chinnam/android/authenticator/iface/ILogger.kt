package online.chinnam.android.authenticator.iface

import android.util.Log

interface ILogger {

    /**
     * Log message
     */
    fun log(message: String) {
        Log.d(this.javaClass.simpleName, message)
    }

    /**
     * Log error message
     */
    fun log(e: Throwable){
        Log.e(this.javaClass.simpleName, e.message.toString(), e)
    }

}