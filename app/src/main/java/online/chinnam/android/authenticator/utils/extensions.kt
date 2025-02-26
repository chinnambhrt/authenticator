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

fun getAlgorithmName(algorithm: String): String {
    return when (algorithm.uppercase()) {
        "SHA1" -> "HmacSHA1"
        "SHA256" -> "HmacSHA256"
        "SHA512" -> "HmacSHA512"
        else -> algorithm
    }
}