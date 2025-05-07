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
            .fillMaxSize()
            .padding(16.dp)
            .background(Purple80)
    ) {
        if (filterVisible) {
            val cats = when (val s = categoriesState) {
                is UiState.Success -> s.data
                else -> emptyList()
            }
            FilterItem(
                categories = cats,
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
                    selectedCategory = null
                    limitText = ""
                    viewModel.loadProducts() // ohne Filter
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        when (val state = productState) {
            UiState.Loading -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is UiState.Success -> ProductList(products = state.data)
            is UiState.Error -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        // Retry: immer auch Kategorien nachladen
                        viewModel.loadProducts(
                            limit = limitText.toIntOrNull(),
                            category = selectedCategory
                        )
                        viewModel.loadCategories()
                    }) {
                        Text("Erneut versuchen")
                    }
                }
            }
        }
    }
}
