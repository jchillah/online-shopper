package de.syntax_institut.jetpack.a04_05_online_shopper

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}