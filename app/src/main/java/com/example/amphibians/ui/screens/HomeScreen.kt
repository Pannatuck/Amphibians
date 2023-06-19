package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.AmphibianCardItem
import com.example.amphibians.ui.screens.AmphibianViewModel.AmphibianUiState

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState, modifier: Modifier = Modifier
){
    when (amphibianUiState){
        is AmphibianUiState.Success -> AmphibianScrollList(amphibians = amphibianUiState.amphibians, modifier = modifier)
        is AmphibianUiState.Error -> ErrorScreen(Modifier.fillMaxSize())
        is AmphibianUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())
    }
}

@Composable
fun AmphibianScrollList(
    amphibians: List<AmphibianCardItem>,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(amphibians.size) {amphibian ->
            AmphibianInfoCard(
                amphibian = amphibians[amphibian],
                modifier = modifier
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun AmphibianInfoCard(
    amphibian: AmphibianCardItem, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "${amphibian.name} (${amphibian.type})",
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(amphibian.img_src)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.amphibian_photo),
            error = painterResource(id = R.drawable.ic_connection_error),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
            )
        Text(text = amphibian.description, modifier)
    }
}

@Preview
@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading",
        modifier = modifier.size(200.dp)
    )

}

@Preview
@Composable
fun ErrorScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_image),
            contentDescription = "Error"
        )
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.padding(16.dp)
        )
    }
}
