package com.example.chatbox.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import com.example.chatbox.R
import com.example.chatbox.ui.theme.PurpleBg
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.disableNextButtonBg
import com.example.chatbox.ui.theme.green
import com.example.chatbox.ui.theme.grey_10
import com.example.chatbox.ui.theme.nextButtonBg
import com.example.chatbox.ui.theme.typography
import kotlin.math.roundToInt

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


@Composable
fun LoginSignUpButton(modifier: Modifier = Modifier,btnText:String,isEnable: Boolean,onClick: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "")
    val offsetX by transition.animateFloat(
        initialValue = -2f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "arrow animation"
    )
    Button(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(17.dp),
                width = 1.dp,
                color = White
            )
            .background(
                brush = if(isEnable) nextButtonBg else disableNextButtonBg,
                shape = RoundedCornerShape(17.dp)

            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick,
        enabled = isEnable
    ) {
        Text(
            text = btnText,
            style = typography.titleMedium.copy(
                fontSize = 28.sp,
                color = White
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_forward_arrow),
            contentDescription = "Moving Arrow",
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .offset { if(isEnable)IntOffset(offsetX.roundToInt(), 0) else Offset.Zero.round() }
        )
    }
}