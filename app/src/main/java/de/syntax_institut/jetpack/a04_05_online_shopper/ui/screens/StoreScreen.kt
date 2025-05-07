package de.syntax_institut.jetpack.a04_05_online_shopper.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.components.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.state.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    viewModel: StoreViewModel,
    filterVisible: Boolean,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    val productState by viewModel.products.collectAsState()
    val categoriesState by viewModel.categories.collectAsState()

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var limitText by remember { mutableStateOf("") }

    Column(
        modifier
            .padding(16.dp)
            .fillMaxWidth()
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
                    viewModel.loadProducts(limitText.toIntOrNull(), it, reloadCategories = false)
                },
                onLimitChanged = { limitText = it },
                onApplyFilter = {
                    viewModel.loadProducts(
                        limitText.toIntOrNull(),
                        selectedCategory,
                        reloadCategories = false
                    )
                },
                onResetFilter = {
                    selectedCategory = null
                    limitText = ""
                    viewModel.loadProducts(reloadCategories = true)
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        when (val state = productState) {
            UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is UiState.Success -> ProductList(
                products = state.data,
                onItemClick = onProductClick
            )

            is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.loadProducts(reloadCategories = true)
                    }) {
                        Text("Erneut versuchen")
                    }
                }
            }
        }
    }
}
