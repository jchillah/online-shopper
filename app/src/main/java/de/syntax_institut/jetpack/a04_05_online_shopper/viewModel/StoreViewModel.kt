package de.syntax_institut.jetpack.a04_05_online_shopper.viewModel

import androidx.lifecycle.*
import de.syntax_institut.jetpack.a04_05_online_shopper.api.*
import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.state.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.*
import java.io.*

class StoreViewModel : ViewModel() {
    private val api = FakeStoreAPI.retrofitService

    private val _products = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val products: StateFlow<UiState<List<Product>>> = _products

    private val _categories = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<String>>> = _categories

    init {
        loadProducts(reloadCategories = true)
    }

    fun loadProducts(
        limit: Int? = null,
        category: String? = null,
        reloadCategories: Boolean = false
    ) {
        viewModelScope.launch {
            _products.value = UiState.Loading
            if (reloadCategories) {
                _categories.value = UiState.Loading
            }
            delay(3000)
            try {
                if (reloadCategories) {
                    val catResp = api.getCategories()
                    _categories.value = if (catResp.isSuccessful) {
                        UiState.Success(catResp.body().orEmpty())
                    } else {
                        UiState.Error("Kategorien konnten nicht geladen werden.")
                    }
                }

                val resp = when {
                    category != null -> api.getProductsByCategory(category)
                    limit != null -> api.getProductsWithLimit(limit)
                    else -> api.getProducts()
                }

                if (resp.isSuccessful) {
                    val list = resp.body().orEmpty()
                    val filtered = when {
                        category != null && limit != null ->
                            list.filter { it.category == category }.take(limit)

                        else -> list
                    }
                    _products.value = UiState.Success(filtered)
                } else {
                    _products.value = UiState.Error(
                        if (resp.code() in 500..599)
                            "Serverfehler – bitte später erneut versuchen."
                        else
                            "Verbindungsfehler – prüfe deine Internetverbindung."
                    )
                }
            } catch (_: IOException) {
                _products.value = UiState.Error("Keine Netzwerkverbindung.")
            } catch (e: HttpException) {
                _products.value = UiState.Error("HTTP-Fehler ${e.code()}.")
            } catch (_: Exception) {
                _products.value = UiState.Error("Unerwarteter Fehler.")
            }
        }
    }
}
