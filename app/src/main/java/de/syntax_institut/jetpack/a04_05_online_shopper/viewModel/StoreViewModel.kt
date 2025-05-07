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
        loadProducts()
        loadCategories()
    }

    fun loadProducts(limit: Int? = null, category: String? = null) {
        viewModelScope.launch {
            _products.value = UiState.Loading
            delay(3_000)

            try {
                val resp = when {
                    category != null -> api.getProductsByCategory(category)
                    limit != null -> api.getProductsWithLimit(limit)
                    else -> api.getProducts()
                }

                if (resp.isSuccessful) {
                    val list = resp.body().orEmpty()
                    val filtered = if (category != null && limit != null)
                        list.filter { it.category == category }.take(limit)
                    else list
                    _products.value = UiState.Success(filtered)
                } else {
                    _products.value = when (resp.code()) {
                        in 500..599 -> UiState.Error("Oops, der Server hat gerade Probleme. Bitte später erneut versuchen.")
                        404 -> UiState.Error("Die angeforderten Produkte wurden nicht gefunden.")
                        else -> UiState.Error("Fehler ${resp.code()}: Bitte überprüfe deine Verbindung.")
                    }
                }
            } catch (_: IOException) {
                _products.value =
                    UiState.Error("Keine Netzwerkverbindung. Bitte prüfe deine Internet-Einstellungen.")
            } catch (e: HttpException) {
                _products.value =
                    UiState.Error("Serverfehler (${e.code()}). Versuche es später erneut.")
            } catch (e: Exception) {
                _products.value = UiState.Error("Unerwarteter Fehler: ${e.localizedMessage}")
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = UiState.Loading
            delay(3_000)

            try {
                val resp = api.getCategories()
                if (resp.isSuccessful) {
                    _categories.value = UiState.Success(resp.body().orEmpty())
                } else {
                    _categories.value =
                        UiState.Error("Fehler ${resp.code()} beim Laden der Kategorien.")
                }
            } catch (e: Exception) {
                _categories.value =
                    UiState.Error("Konnte Kategorien nicht laden: ${e.localizedMessage}")
            }
        }
    }
}
