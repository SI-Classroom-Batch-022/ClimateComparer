package com.example.climatecomparer.ui.detailmain.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.climatecomparer.ui.detailmain.screen.ForecastPeriod

@Composable
fun ForecastPeriodSwitcher(
    selectedPeriod: ForecastPeriod,
    onPeriodSelected: (ForecastPeriod) -> Unit
) {
    Column {
        Text(
            text = "Vorhersage",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Companion.Medium,
            color = Color.Companion.White
        )

        Spacer(modifier = Modifier.Companion.height(8.dp))

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ForecastPeriod.values().forEach { period ->
                FilterChip(
                    onClick = { onPeriodSelected(period) },
                    label = {
                        Text(
                            text = period.displayName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    selected = selectedPeriod == period,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.Companion.White.copy(alpha = 0.2f),
                        labelColor = Color.Companion.White,
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        selectedLabelColor = Color.Companion.White
                    ),
                    modifier = Modifier.Companion.weight(1f)
                )
            }
        }
    }
}