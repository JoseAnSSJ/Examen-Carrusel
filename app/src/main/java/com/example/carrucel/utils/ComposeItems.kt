package com.example.carrucel.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.carrucel.R
import com.example.carrucel.core.Resource

@Composable
fun RatingBar(
    size: Dp, rating: Double
) {

    Row {
        for (i in 1..5) {
            key(i, rating) {
                val difference = rating - i + 1
                val starPainter = when {
                    difference >= 1.0 -> painterResource(R.drawable.ic_star_full)
                    difference == 0.5 -> painterResource(R.drawable.ic_star_half)
                    else -> painterResource(R.drawable.ic_star_border)
                }

                Image(
                    painter = starPainter,
                    contentDescription = "Calificacion",
                    colorFilter = ColorFilter.tint(color = Color.Black),
                    modifier = Modifier.size(size)
                )
            }
        }
    }
}


@Composable
fun <T> ResourceContent(
    resource: Resource<T>,
    onRetry: () -> Unit,
    loading: @Composable () -> Unit = {
        Loading()
    },
    content: @Composable (T) -> Unit
) {
    when (resource) {
        is Resource.Loading -> loading()
        is Resource.Error -> ErrorRow(resource.message, onRetry)
        is Resource.Success -> content(resource.data)
    }
}


@Composable
fun ErrorRow(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) { Text(text = stringResource(R.string.retry)) }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp), contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}