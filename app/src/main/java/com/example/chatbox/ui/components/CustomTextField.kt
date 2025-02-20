package com.example.chatbox.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.chatbox.ui.theme.green
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.typography
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyBoardType: KeyboardType,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    icon: @Composable ()->Unit={}
) {
    OutlinedTextField(
        value =value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                style = typography.bodySmall.copy(fontSize = 14.sp)
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = green,
            unfocusedLabelColor = grey_7B,
            focusedBorderColor = green,
            unfocusedBorderColor = grey_7B,
            focusedTextColor = green,
            unfocusedTextColor = grey_7B
        ),
        textStyle = typography.bodyMedium,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
        ),
        modifier = modifier.fillMaxWidth(),
        visualTransformation =  visualTransformation,
        trailingIcon = icon
    )
}
