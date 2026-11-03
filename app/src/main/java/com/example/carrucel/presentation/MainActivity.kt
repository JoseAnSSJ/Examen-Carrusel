package com.example.carrucel.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.carrucel.core.Resource
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.presentation.Home.HomeViewModel
import com.example.carrucel.ui.theme.CarrucelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarrucelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: HomeViewModel = hiltViewModel()) {
    val featured by viewModel.featured.collectAsStateWithLifecycle()
    val topRated by viewModel.topRated.collectAsStateWithLifecycle()
    val newItems by viewModel.newItems.collectAsStateWithLifecycle()
    val genreMovies by viewModel.genreMovies.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DebugSection(name = "Principal", resource = featured) }
        item { DebugSection(name = "Top", resource = topRated) }
        item { DebugSection(name = "Nuevos", resource = newItems) }
        item { DebugSection(name = "Género", resource = genreMovies) }
    }
}

@Composable
fun DebugSection(name: String, resource: Resource<List<Movie>>) {
    Column {
        Text(name, style = MaterialTheme.typography.titleMedium)
        when (resource) {
            is Resource.Loading -> Text(text = "Loading...")
            is Resource.Error -> Text(text = "${resource.message}", color = Color.Red)
            is Resource.Success -> Text(
                text = "${resource.data.size} películas: " +
                        resource.data.take(3).joinToString { it.title }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarrucelTheme {
        Greeting()
    }
}


