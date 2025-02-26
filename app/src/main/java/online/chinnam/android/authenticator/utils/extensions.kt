package online.chinnam.android.authenticator.utils

/**
 * Extension function to add ellipsis to a string
 */
fun String.ellipsis(length: Int = 25): String {
    return if (this.length > length) {
        this.substring(0, length) + "..."
    } else {
        this
    }
}