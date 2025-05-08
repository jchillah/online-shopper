package de.syntax_institut.jetpack.a04_05_online_shopper

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.screens.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.ui.theme.onlineshopperjchillahTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    onlineshopperjchillahTheme {
        val navController = rememberNavController()
        val viewModel: StoreViewModel = viewModel()
        var filterVisible by remember { mutableStateOf(false) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sylando") },
                    navigationIcon = {
                        if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Zurück"
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentRoute == "store") {
                            IconButton(
                                onClick = { filterVisible = !filterVisible }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Filter toggeln"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple80,
                        titleContentColor = Purple40,
                        navigationIconContentColor = Purple40,
                        actionIconContentColor = Purple40
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "store"
                ) {
                    composable("store") {
                        StoreScreen(
                            viewModel = viewModel,
                            filterVisible = filterVisible,
                            modifier = Modifier.fillMaxSize(),
                            onProductClick = { product ->
                                navController.navigate("detail/${product.id}")
                            }
                        )
                    }
                    composable(
                        route = "detail/{productId}",
                        arguments = listOf(navArgument("productId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments!!.getInt("productId")
                        ProductDetailScreen(
                            productId = id,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
