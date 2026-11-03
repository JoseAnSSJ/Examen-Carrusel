package com.example.carrucel.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.carrucel.R
import com.example.carrucel.core.Resource
import com.example.carrucel.presentation.BaseViewModel

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
    resource: Resource<T>, onRetry: () -> Unit, loading: @Composable () -> Unit = {
        Loading()
    }, content: @Composable (T) -> Unit
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


@Composable
fun VideoPlayer(
    url: String, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var position by rememberSaveable(url) { mutableLongStateOf(0L) }
    var playWhenReady by rememberSaveable(url) { mutableStateOf(false) }
    var error by remember(url) { mutableStateOf<String?>(null) }

    val player = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            seekTo(position)
            this.playWhenReady = playWhenReady
            prepare()
        }
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlayerError(e: PlaybackException) {
                error = context.getString(R.string.error_play_video)
            }
        }
        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }

    DisposableEffect(lifecycleOwner, player) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                position = player.currentPosition
                playWhenReady = player.playWhenReady
                player.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player.release()
        }
    }

    Box(modifier = modifier.background(Color.Black)) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = true
                }
            }, modifier = Modifier.fillMaxSize()
        )

        error?.let {
            Text(
                text = it, color = Color.White, modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
@Composable
fun Pull2Refresh(
    viewModel: BaseViewModel,
    content: @Composable BoxScope.() -> Unit
) {
    val isRefresh by viewModel.isRefresh.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefresh,
        onRefresh = viewModel::refresh,
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}