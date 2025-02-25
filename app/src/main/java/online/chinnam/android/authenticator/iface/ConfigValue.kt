package online.chinnam.android.authenticator.iface

class ConfigValue<T>(private var value: T) {

    fun get(): T = value

    fun set(newValue: T) {
        value = newValue
    }

    override fun toString(): String {
        return "ConfigValue(value=$value)"
    }
}