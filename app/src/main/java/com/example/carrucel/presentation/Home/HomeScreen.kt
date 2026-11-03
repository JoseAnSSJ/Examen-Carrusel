package com.example.carrucel.presentation.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.example.carrucel.R
import com.example.carrucel.core.Resource
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.utils.GenreEnum
import com.example.carrucel.utils.RatingBar
import com.example.carrucel.utils.ResourceContent

@Composable
fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val featured by viewModel.featured.collectAsStateWithLifecycle()
    val topRated by viewModel.topRated.collectAsStateWithLifecycle()
    val newItems by viewModel.newItems.collectAsStateWithLifecycle()
    val genreMovies by viewModel.genreMovies.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()

    val mainListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = Color.White)
    ) {
        Text(
            text = stringResource(R.string.title_movies),
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(
                    Alignment.CenterHorizontally
                ),
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            state = mainListState,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                ResourceContent(
                    resource = featured,
                    onRetry = viewModel::loadFeatured,
                ) { movies ->
                    MainCarousel(
                        movies = movies.take(5), onMovieClick = onMovieClick
                    )
                }
            }

            item {
                MovieSection(
                    title = stringResource(R.string.subtitle_new),
                    resource = newItems,
                    onRetry = viewModel::loadNew,
                    onMovieClick = onMovieClick
                )
            }

            item {
                MovieSection(
                    title = stringResource(R.string.subtitle_popular),
                    resource = topRated,
                    onRetry = viewModel::loadTopRated,
                    onMovieClick = onMovieClick
                )
            }

            item {
                GenreSection(
                    selectedGenre = selectedGenre,
                    movies = genreMovies,
                    onGenreClick = viewModel::loadGenre,
                    onRetry = { viewModel.loadGenre(selectedGenre) },
                    onMovieClick = onMovieClick
                )
            }
        }
    }


}


@Composable
fun MainCarousel(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { movies.size })

    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 12.dp
        ) { page ->
            val movie = movies[page]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onMovieClick(movie.id) }) {
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
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = movie.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(times = movies.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(all = 3.dp)
                        .size(if (selected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }
    }
}

@Composable
fun MovieSection(
    title: String, resource: Resource<List<Movie>>, onRetry: () -> Unit, onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ResourceContent(
            resource = resource, onRetry = onRetry
        ) { movies -> MovieRow(movies = movies, onMovieClick = onMovieClick) }
    }
}

@Composable
fun MovieRow(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    val listState = rememberLazyListState()
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .clip(shape = RoundedCornerShape(percent = 5))
            .background(color = Color.LightGray),
        shadowElevation = 10.dp,
    ) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies, key = { it.id }) {
                MovieCard(
                    movie = it, onClick = { onMovieClick(it.id) })
            }
        }
    }

}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .width(130.dp)
            .height(260.dp)
            .clickable(onClick = onClick)
    ) {
        SubcomposeAsyncImage(
            model = movie.imageUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
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
                .height(190.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            alignment = Alignment.TopCenter
        )
        Text(
            text = movie.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp),
        )
        RatingBar(size = 15.dp, rating = movie.rating)
    }

}

@Composable
fun GenreSection(
    selectedGenre: GenreEnum,
    movies: Resource<List<Movie>>,
    onGenreClick: (GenreEnum) -> Unit,
    onRetry: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.subtitle_genre),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(GenreEnum.entries) { genre ->
                FilterChip(
                    selected = genre == selectedGenre,
                    onClick = { onGenreClick(genre) },
                    label = { Text(text = genre.spanish) })
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        ResourceContent(
            resource = movies,
            onRetry = onRetry,
        ) { list -> MovieRow(list, onMovieClick) }
    }
}