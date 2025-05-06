package de.syntax_institut.jetpack.a04_05_online_shopper.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import coil.compose.*
import de.syntax_institut.jetpack.a04_05_online_shopper.data.model.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Pink80
        ),
        elevation = cardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Purple40)
        ) {

            AsyncImage(
                model = product.image,
                contentDescription = "Produktbild",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Titel des Produkts
            Text(
                product.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            // Kategorie des Produkts
            Text(
                text = "Kategorie: ${product.category}",
                style = MaterialTheme.typography.bodySmall,
                color = Purple80
            )

            // Preis des Produkts
            Text(
                "Preis: ${product.price} €",
                style = MaterialTheme.typography.bodyMedium,
                color = Pink80
            )
        }
    }
}

@Preview()
@Composable
fun ProductCardPreview() {
    ProductCard(
        product = Product(
            id = 0,
            title = "title",
            price = 13.99,
            description = "description",
            category = "category",
            image = "image"
        )
    )
}
