package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDetailCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier.Companion,
    subtitle: String? = null,
    valueColor: Color = Color.Companion.White
) {
    SmallGlassmorphismCard(
        modifier = modifier
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier.Companion
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.Companion.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Companion.Center,
                color = Color.Companion.White.copy(alpha = 0.8f),
                maxLines = 1
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Companion.Bold,
                color = valueColor,
                textAlign = TextAlign.Companion.Center,
                maxLines = 1
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Companion.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Companion.Center,
                    maxLines = 1
                )
            }
        }
    }
}