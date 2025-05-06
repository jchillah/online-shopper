package de.syntax_institut.jetpack.a04_05_online_shopper.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import de.syntax_institut.jetpack.a04_05_online_shopper.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(
    categories: List<String>,
    selectedCategory: String?,
    limitText: String,
    onCategorySelected: (String?) -> Unit,
    onLimitChanged: (String) -> Unit,
    onApplyFilter: () -> Unit,
    onResetFilter: () -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Purple80),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Dropdown für Kategorien
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(8.dp),
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                value = selectedCategory ?: "Alle",
                onValueChange = {},
                readOnly = true,
                label = { Text("Kategorie") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Purple40,
                    unfocusedIndicatorColor = Purple80
                ),
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Alle") },
                    onClick = {
                        onCategorySelected(null)
                        dropdownExpanded = false
                    }
                )
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            onCategorySelected(category)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        // TextField für das Limit
        TextField(
            modifier = Modifier
                .padding(8.dp),
            value = limitText,
            onValueChange = { if (it.all { char -> char.isDigit() }) onLimitChanged(it) },
            label = { Text("Limit") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Purple40,
                unfocusedIndicatorColor = Purple80
            )
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Button für das Anwenden des Filters
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .padding(8.dp),
                onClick = onApplyFilter,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                )
            ) {
                Text("Anwenden")
            }

            // Button zum Zurücksetzen des Filters
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .padding(8.dp),
                onClick = onResetFilter,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink80,
                    contentColor = Color.White
                )
            ) {
                Text("Zurücksetzen")
            }
        }
    }
}
