package com.adriandeseta.weatherapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.adriandeseta.weatherapp.R

private val coinyRegular = Font(R.font.coiny_regular, FontWeight.Normal)
private val figtreeRegular = Font(R.font.figtree_regular, FontWeight.Normal)

// Familia de fuentes
val coinyFontFamily = FontFamily(
    coinyRegular
)
val figtreeFontFamily = FontFamily(
    figtreeRegular
)
@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = TextStyle.Default,
    textDecoration: TextDecoration = TextDecoration.None,
    fontFamily: FontFamily
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        style = style,
        textDecoration = textDecoration
    )
}
