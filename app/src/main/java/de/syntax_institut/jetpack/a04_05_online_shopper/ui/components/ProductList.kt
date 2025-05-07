package de.syntax_institut.jetpack.a04_05_online_shopper.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*

@Composable
fun ProductList(
    products: List<Product>,
    onItemClick: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(Purple80),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(product)
                    })
        }
    }
}
