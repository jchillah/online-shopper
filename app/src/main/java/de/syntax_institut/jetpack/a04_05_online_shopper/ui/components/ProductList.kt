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
fun ProductList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.background(Purple40),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = products,
            key = { product -> product.id },
            contentType = { product -> "product" }
        ) { product ->
            ProductCard(
                product = product
            )
        }
    }
}
