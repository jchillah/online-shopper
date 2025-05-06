package de.syntax_institut.jetpack.a04_05_online_shopper.viewModel

import androidx.lifecycle.*
import de.syntax_institut.jetpack.a04_05_online_shopper.api.*
import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.state.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class StoreViewModel : ViewModel() {
    private val api = FakeStoreAPI.retrofitService

    val products = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    private val _categories = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<String>>> = _categories

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts(limit: Int? = null, category: String? = null) {
        viewModelScope.launch {
            products.value = UiState.Loading
            try {
                val fetchedProducts = when {
                    category != null -> api.getProductsByCategory(category)
                    limit != null -> api.getProductsWithLimit(limit)
                    else -> api.getProducts()
                }
                val limitedProducts = if (limit != null && category != null) {
                    fetchedProducts.take(limit)
                } else {
                    fetchedProducts
                }
                products.value = UiState.Success(limitedProducts)
            } catch (e: Exception) {
                products.value =
                    UiState.Error("Fehler beim Laden der Produkte: ${e.localizedMessage}")
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = UiState.Loading
            try {
                val fetchedCategories = api.getCategories()
                _categories.value = UiState.Success(fetchedCategories)
            } catch (e: Exception) {
                _categories.value =
                    UiState.Error("Fehler beim Laden der Kategorien: ${e.localizedMessage}")
            }
        }
    }
}
