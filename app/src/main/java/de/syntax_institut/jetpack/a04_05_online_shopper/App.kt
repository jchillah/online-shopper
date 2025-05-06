package de.syntax_institut.jetpack.a04_05_online_shopper

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.screens.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme {
        val viewModel: StoreViewModel = viewModel()
        var filterVisible by remember { mutableStateOf(false) }

        Scaffold(
            contentColor = Purple40,
            containerColor = Purple80,
            topBar = {
                TopAppBar(
                    colors = TopAppBarColors(
                        containerColor = Purple80,
                        scrolledContainerColor = Purple40,
                        navigationIconContentColor = Purple40,
                        titleContentColor = Purple40,
                        actionIconContentColor = Purple40
                    ),
                    title = { Text("Sylando") },
                    actions = {
                        IconButton(onClick = { filterVisible = !filterVisible }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Filter toggeln"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                StoreScreen(
                    viewModel = viewModel,
                    filterVisible = filterVisible,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
