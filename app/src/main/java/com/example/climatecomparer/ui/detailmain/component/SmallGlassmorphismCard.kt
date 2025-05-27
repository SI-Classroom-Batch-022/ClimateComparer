package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SmallGlassmorphismCard(
    modifier: Modifier = Modifier.Companion,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.Companion.linearGradient(
                    colors = listOf(
                        Color.Companion.Black.copy(alpha = 0.3f),
                        Color.Companion.White.copy(alpha = 0.2f)
                    )
                )
            )
    ) {
        // Blur-Hintergrund-Effekt
        Box(
            modifier = Modifier.Companion
                .matchParentSize()
                .background(
                    Color.Companion.White.copy(alpha = 0.15f),
                    androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
        )

        // Inhalt
        Box(modifier = Modifier.Companion.padding(1.dp)) {
            content()
        }
    }
}