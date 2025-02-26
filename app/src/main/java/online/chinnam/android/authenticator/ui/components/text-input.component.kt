package online.chinnam.android.authenticator.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextInputComponent(
    title: String,
    placeholder: String = title,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    numericInput: Boolean = false
) {

    var inputValue by remember { mutableStateOf(value) }

    OutlinedTextField(
        label = { Text(title) },
        placeholder = { Text(placeholder) },
        value = inputValue,
        onValueChange = {
            inputValue = it
            onChange(it)
        },
        modifier = modifier.padding(8.dp, 5.dp),
        keyboardOptions = if (numericInput) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
    )

}