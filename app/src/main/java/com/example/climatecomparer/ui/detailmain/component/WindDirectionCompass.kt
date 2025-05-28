package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun WindDirectionCompass(
    windDirection: Double,
    modifier: Modifier = Modifier,
    compassColor: Color = Color.White.copy(alpha = 0.3f),
    arrowColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Kompass-Hintergrund (Kreis)
        Canvas(
            modifier = Modifier.size(24.dp)
        ) {
            drawCompassBackground(compassColor)
        }

        // Rotierender Pfeil
        Icon(
            imageVector = Icons.Default.Navigation,
            contentDescription = "Windrichtung",
            modifier = Modifier
                .size(16.dp)
                .rotate(windDirection.toFloat()),
            tint = arrowColor
        )
    }
}

private fun DrawScope.drawCompassBackground(color: Color) {
    // Äußerer Kreis
    drawCircle(
        color = color,
        radius = size.minDimension / 2,
        style = Stroke(width = 1.dp.toPx())
    )

    // Innerer Punkt (Zentrum)
    drawCircle(
        color = color,
        radius = 1.dp.toPx()
    )
}

@Composable
fun EnhancedWindDirectionCompass(
    windDirection: Double,
    modifier: Modifier = Modifier,
    compassColor: Color = Color.White.copy(alpha = 0.3f),
    arrowColor: Color = MaterialTheme.colorScheme.primary,
    showCardinalPoints: Boolean = true
) {
    Box(
        modifier = modifier.size(32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Erweiterter Kompass-Hintergrund
        Canvas(
            modifier = Modifier.size(32.dp)
        ) {
            drawEnhancedCompassBackground(compassColor, showCardinalPoints)
        }

        // Rotierender Pfeil
        Icon(
            imageVector = Icons.Default.Navigation,
            contentDescription = "Windrichtung $windDirection°",
            modifier = Modifier
                .size(20.dp)
                .rotate(windDirection.toFloat()),
            tint = arrowColor
        )
    }
}

private fun DrawScope.drawEnhancedCompassBackground(
    color: Color,
    showCardinalPoints: Boolean
) {
    val radius = size.minDimension / 2
    val center = androidx.compose.ui.geometry.Offset(
        size.width / 2,
        size.height / 2
    )

    // Äußerer Kreis
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = 1.5.dp.toPx())
    )

    if (showCardinalPoints) {
        // Himmelsrichtungsmarkierungen
        val markLength = 4.dp.toPx()
        val directions = listOf(0f, 90f, 180f, 270f) // N, O, S, W

        directions.forEach { angle ->
            val angleRad = Math.toRadians(angle.toDouble())
            val outerX = center.x + (radius - markLength) * kotlin.math.cos(angleRad).toFloat()
            val outerY = center.y + (radius - markLength) * kotlin.math.sin(angleRad).toFloat()
            val innerX = center.x + radius * kotlin.math.cos(angleRad).toFloat()
            val innerY = center.y + radius * kotlin.math.sin(angleRad).toFloat()

            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(outerX, outerY),
                end = androidx.compose.ui.geometry.Offset(innerX, innerY),
                strokeWidth = 1.dp.toPx()
            )
        }
    }

    // Zentrum
    drawCircle(
        color = color,
        radius = 1.5.dp.toPx(),
        center = center
    )
}