package com.jing91.pawfit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jing91.pawfit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetTopAppBar(
    title: String,
    showLogo: Boolean = true
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showLogo) {
                    Image(
                        painter = painterResource(id = R.drawable.pawfit_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}
