package de.syntax_institut.jetpack.a04_05_online_shopper.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import coil.compose.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.state.*
import de.syntax_institut.jetpack.a04_05_online_shopper.viewModel.*

@Composable
fun ProductDetailScreen(
  productId: Int,
  viewModel: StoreViewModel
) {
  val productsState by viewModel.products.collectAsState()
  val product = (productsState as? UiState.Success)?.data?.firstOrNull { it.id == productId }

  if (product == null) {
    Text("Produkt nicht gefunden", modifier = Modifier.padding(16.dp))
  } else {
    Column(Modifier.padding(16.dp)) {
      AsyncImage(
        model = product.image,
        contentDescription = product.title,
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
      )
      Spacer(Modifier.height(16.dp))
      Text(product.title, style = MaterialTheme.typography.headlineSmall)
      Spacer(Modifier.height(8.dp))
      Text("Preis: ${product.price} €", style = MaterialTheme.typography.headlineMedium)
      Spacer(Modifier.height(8.dp))
      Text(product.description, style = MaterialTheme.typography.bodyLarge)
    }
  }
}
