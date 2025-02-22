package com.example.chatbox.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.chatbox.ui.theme.PurpleBg
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
    icon: @Composable () -> Unit = {},
    leadingIcon: Int,
    isEnable:Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                style = typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = PurpleBg
                    )
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = PurpleBg,
            unfocusedLabelColor = grey_7B,
            focusedBorderColor = PurpleBg,
            unfocusedBorderColor = grey_7B,
            focusedTextColor = PurpleBg,
            unfocusedTextColor = grey_7B
        ),
        textStyle = typography.bodyMedium.copy(
            color = PurpleBg
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
        ),
        modifier = modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = icon,
        leadingIcon = {
            Image(
                painter = painterResource(leadingIcon), contentDescription = ""
            )
        },
        enabled = isEnable
    )
}
