package com.example.chatbox.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatbox.ui.theme.PurpleBg
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.green
import com.example.chatbox.ui.theme.grey_10
import com.example.chatbox.ui.theme.typography

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    isEnable: Boolean,
    modifier: Modifier

) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = grey_10,
            containerColor = White
        ),
        enabled = isEnable,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text,
            style = typography.bodyMedium.copy(
                color = PurpleBg
            ),
            modifier = Modifier.padding(vertical = 10.dp)

        )
    }
}