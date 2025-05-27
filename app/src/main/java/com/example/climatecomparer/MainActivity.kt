package com.example.climatecomparer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.climatecomparer.navigation.AppNavigation
import com.example.climatecomparer.ui.theme.ClimateComparerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClimateComparerTheme {
                AppNavigation()
            }
        }
    }
}


