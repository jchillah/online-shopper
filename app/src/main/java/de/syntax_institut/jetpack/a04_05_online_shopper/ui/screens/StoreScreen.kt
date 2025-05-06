package de.syntax_institut.jetpack.a04_05_online_shopper.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.components.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.state.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    viewModel: StoreViewModel,
    filterVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val productState by viewModel.products.collectAsState()
    val categoriesState by viewModel.categories.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var limitText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Purple80),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (filterVisible) {
            val categories = when (val state = categoriesState) {
                is UiState.Success -> state.data
                else -> emptyList()
            }

            FilterItem(
                categories = categories,
                selectedCategory = selectedCategory,
                limitText = limitText,
                onCategorySelected = {
                    selectedCategory = it
                    viewModel.loadProducts(limit = limitText.toIntOrNull(), category = it)
                },
                onLimitChanged = { limitText = it },
                onApplyFilter = {
                    viewModel.loadProducts(
                        limit = limitText.toIntOrNull(),
                        category = selectedCategory
                    )
                },
                onResetFilter = {
                    // Zurücksetzen der Filter
                    selectedCategory = null
                    limitText = ""
                    viewModel.loadProducts() // Lädt die Produkte ohne Filter
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = productState) {
            is UiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }

            is UiState.Success -> ProductList(products = state.data)
            is UiState.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.loadProducts(
                            limit = limitText.toIntOrNull(),
                            category = selectedCategory
                        )
                    }) {
                        Text("Erneut versuchen")
                    }
                }
            }
        }
    }
}
