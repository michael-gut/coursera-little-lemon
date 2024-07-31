package com.learning.littlelemon.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color

val GreenMain = Color(0xFF495E57)
val YellowMain = Color(0xFFF4CE14)
val PinkSecond = Color(0xFFEE9972)
val PeachSecond = Color(0xFFFBDABB)
val HighlightLight = Color(0xFFEDEFEE)
val HighlightDark = Color(0xFF333333)

val DefaultButtonColor = ButtonColors(
    containerColor = YellowMain,
    contentColor = HighlightLight,
    disabledContainerColor = Color.Gray,
    disabledContentColor = Color.White
)

val LightButtonColor = ButtonColors(
    containerColor = HighlightLight,
    contentColor = GreenMain,
    disabledContainerColor = HighlightDark,
    disabledContentColor = HighlightLight
)
