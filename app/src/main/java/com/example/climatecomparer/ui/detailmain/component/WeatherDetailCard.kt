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
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    valueColor: Color = Color.White,
    windDirection: Double? = null // Neuer Parameter für Windrichtung
) {
    SmallGlassmorphismCard(
        modifier = modifier
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Spezielle Behandlung für Windrichtung
            if (windDirection != null && title.contains("Windrichtung", ignoreCase = true)) {
                WindDirectionCompass(
                    windDirection = windDirection,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                maxLines = 1
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = valueColor,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

// Alternative Version für eine dedizierte WindDirectionCard
@Composable
fun WindDirectionCard(
    windDirection: Double,
    windSpeed: Double,
    modifier: Modifier = Modifier
) {
    SmallGlassmorphismCard(
        modifier = modifier
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EnhancedWindDirectionCompass(
                windDirection = windDirection,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Windrichtung",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.8f),
                maxLines = 1
            )

            Text(
                text = "${windDirection.toInt()}°",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Text(
                text = getWindDirection(windDirection),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

// Hilfsfunktion für Windrichtung (falls nicht bereits vorhanden)
private fun getWindDirection(degrees: Double): String {
    return when ((degrees / 45).toInt() % 8) {
        0 -> "N"
        1 -> "NO"
        2 -> "O"
        3 -> "SO"
        4 -> "S"
        5 -> "SW"
        6 -> "W"
        7 -> "NW"
        else -> "N"
    }
}