package de.syntax_institut.jetpack.a04_05_online_shopper.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
        colors = CardDefaults.cardColors(containerColor = Pink80),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Purple40)
        ) {
            SubcomposeAsyncImage(
                model = product.image,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }

                    is AsyncImagePainter.State.Error -> Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Fehler beim Laden des Bildes",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    else -> SubcomposeAsyncImageContent()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
