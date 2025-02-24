package online.chinnam.android.authenticator.totp

import java.nio.ByteBuffer
import java.security.Key
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow


object TOTP {

    private const val DEFAULT_TIME_STEP_SECONDS = 30L // Time step (RFC 6238)
    private const val DEFAULT_CODE_DIGITS = 6        // Number of digits in OTP
    private const val HMAC_ALGORITHM = "HmacSHA1"    // HMAC algorithm (can be SHA256, SHA512 as well)

    private val SUPPORTED_ALGORITHMS = listOf(
        "HmacSHA1",
        "HmacSHA256",
        "HmacSHA512"
    )

    /**
     * Generate a TOTP based on the provided secret, time, and optional parameters.
     *
     * @param secretKey The shared secret key (Base32-encoded or plain UTF-8 bytes).
     * @param currentTimeMillis Current system time in milliseconds.
     * @param timeStepSeconds The time step duration in seconds (default is 30 seconds).
     * @param codeDigits The number of digits in the OTP (default is 6).
     * @return The generated TOTP.
     */
    fun generateTOTP(
        secretKey: ByteArray,
        currentTimeMillis: Long,
        algorithm: String = HMAC_ALGORITHM,
        timeStepSeconds: Long = DEFAULT_TIME_STEP_SECONDS,
        codeDigits: Int = DEFAULT_CODE_DIGITS
    ): String {

        if(algorithm !in SUPPORTED_ALGORITHMS) {
            throw IllegalArgumentException("Unsupported algorithm: $algorithm")
        }

        // Calculate the time step count
        val timeStepCount = currentTimeMillis / (timeStepSeconds * 1000)

        // Convert time step count to byte array (8 bytes, big-endian)
        val timeStepBytes = ByteBuffer.allocate(8).putLong(timeStepCount).array()

        // Generate HMAC hash
        val hmacHash = hmac(secretKey, timeStepBytes, algorithm)

        // Extract a dynamic binary code from the HMAC hash
        val binaryCode = truncate(hmacHash)

        // Compute the OTP
        val otp = binaryCode % 10.0.pow(codeDigits).toInt()

        // Format OTP with leading zeros
        return otp.toString().padStart(codeDigits, '0')
    }

    /**
     * Computes the HMAC-SHA1 hash of the given key and message.
     *
     * @param key The secret key (as byte array).
     * @param data The data to hash (as byte array).
     * @return The HMAC-SHA1 hash.
     */
    private fun hmacSha1(key: ByteArray, data: ByteArray): ByteArray {
        val mac: Mac = Mac.getInstance(HMAC_ALGORITHM)
        val secretKeySpec: Key = SecretKeySpec(key, HMAC_ALGORITHM)
        mac.init(secretKeySpec)
        return mac.doFinal(data)
    }


    private fun hmac(key: ByteArray, data: ByteArray, algorithm: String = HMAC_ALGORITHM): ByteArray {
        val mac: Mac = Mac.getInstance(algorithm)
        val secretKeySpec: Key = SecretKeySpec(key, algorithm)
        mac.init(secretKeySpec)
        return mac.doFinal(data)
    }

    /**
     * Extracts a dynamic binary code from the HMAC hash (per RFC 4226).
     *
     * @param hmacHash The HMAC hash (as byte array).
     * @return The dynamic binary code.
     */
    private fun truncate(hmacHash: ByteArray): Int {
        // Extract the lower 4 bits of the last byte as the offset
        val offset = hmacHash[hmacHash.size - 1].toInt() and 0x0F

        // Extract 4 bytes starting from the offset
        val binaryCode = (hmacHash[offset].toInt() and 0x7F shl 24) or
                (hmacHash[offset + 1].toInt() and 0xFF shl 16) or
                (hmacHash[offset + 2].toInt() and 0xFF shl 8) or
                (hmacHash[offset + 3].toInt() and 0xFF)

        return binaryCode
    }
}