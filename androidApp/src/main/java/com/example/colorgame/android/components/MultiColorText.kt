package com.example.colorgame.android.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit


@Composable
fun MultiColorText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterColors: List<Color> = emptyList(),
    defaultColor: Color = Color.Unspecified,
    colorForIndex: ((index: Int, char: Char) -> Color)? = null,
    style: TextStyle = TextStyle.Default
) {
    require(letterColors.isNotEmpty() || colorForIndex != null) {
        "You must provide either letterColors or a colorForIndex lambda"
    }

    val annotated = buildAnnotatedString {
        text.forEachIndexed { i, c ->
            val spanColor = when {
                colorForIndex != null ->
                    colorForIndex(i, c)
                letterColors.isNotEmpty() ->
                    letterColors[i % letterColors.size]
                else ->
                    defaultColor
            }
            withStyle(
                SpanStyle(
                color = if (spanColor != Color.Unspecified) spanColor else defaultColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontStyle = fontStyle,
                fontFamily = fontFamily
            )
            ) {
                append(c)
            }
        }
    }

    Text(
        text = annotated,
        modifier = modifier,
        style = style
    )
}
