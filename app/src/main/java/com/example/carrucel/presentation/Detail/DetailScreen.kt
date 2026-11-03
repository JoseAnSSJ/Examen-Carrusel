package com.example.carrucel.presentation.Detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.example.carrucel.R
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.utils.RatingBar
import com.example.carrucel.utils.Pull2Refresh
import com.example.carrucel.utils.ResourceContent
import com.example.carrucel.utils.VideoPlayer
import com.example.carrucel.utils.toSpanishGenre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit, viewModel: DetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movie.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.title_detail),
                    style = MaterialTheme.typography.titleLarge
                )
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            R.string.txt_return
                        )
                    )
                }
            })
        }) { padding ->
        Pull2Refresh(
            viewModel = viewModel
        ) {
            Box(
                Modifier
                    .padding(paddingValues = padding)
                    .fillMaxSize()
            ) {
                ResourceContent(
                    resource = movie,
                    onRetry = viewModel::load,
                    loading = {
                        Loading()
                    },
                ) { m -> MovieDetailContent(m) }
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: Movie) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SubcomposeAsyncImage(
            model = movie.imageUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(
                    painterResource(R.drawable.ic_error),
                    contentDescription = stringResource(R.string.image_error),
                    modifier = Modifier.size(25.dp),
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(
                        color = Color.Red
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    RatingBar(size = 20.dp, rating = movie.rating)
                    Text(text = movie.year.toString(), style = MaterialTheme.typography.bodyLarge)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    movie.genres.forEach { genre ->
                        AssistChip(onClick = {}, label = { Text(text = genre.toSpanishGenre()) })
                    }
                }
            }

            Text(text = movie.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.trailer), style = MaterialTheme.typography.titleLarge
            )
            VideoPlayer(
                url = "https://lorem.video/720p",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
        }
    }
}

@Composable
fun Loading() {
    Box(
        Modifier.fillMaxSize(), Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp)
        )
    }

}