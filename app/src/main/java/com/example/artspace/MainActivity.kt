package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class Art(
    @StringRes val artist: Int,
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val year: Int
) {}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var index by remember { mutableIntStateOf(0) }
    
    val art = when(index) {
        0 -> Art(
            artist = R.string.river_landscape_artist,
            image = R.drawable.river_landscape_1952_5_58,
            title = R.string.river_landscape_title,
            year = R.string.river_landscape_year
        )
        1 -> Art(
            artist = R.string.moonlit_landscape_artist,
            image = R.drawable.moonlit_landscape_with_bridge_1990_6_1,
            title = R.string.moonlit_landscape_title,
            year = R.string.moonlit_landscape_year
        )
        else -> Art(
            artist = R.string.ships_in_distress_artist,
            image = R.drawable.ships_in_distress_off_a_rocky_coast_1985_29_1,
            title = R.string.ships_in_distress_title,
            year = R.string.ships_in_distress_year
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ArtCanvas(art = art, modifier = modifier.weight(1f))
        ArtAttributions(art = art)
        Controls(
            modifier = modifier,
            onClickNext = { index = (index + 1) % 3 },
            onClickPrevious = { index = if(index > 0) (index - 1) % 3 else 2 }
        )
    }
}

@Composable
fun ArtAttributions(
    art: Art,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .background(color = colorResource(R.color.attribution_background))
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Column {
            Row { Text(
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                lineHeight = 36.sp,
                modifier = modifier,
                text = stringResource(id = art.title)
            ) }

            Spacer(modifier = modifier.height(16.dp))
            
            Row {
                Text(
                    fontWeight = FontWeight.Bold,
                    modifier = modifier,
                    text = stringResource(id = art.artist)
                )
                Text(
                    text = " (${stringResource(id = art.year)}))"
                )
            }
        }
    }
}

@Composable
fun ArtCanvas(
    art: Art,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = modifier.shadow(8.dp)
        ) {
            Image(
                contentDescription = null,
                modifier = modifier.padding(24.dp),
                painter = painterResource(id = art.image)
            )
        }
    }
}

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 8.dp),
    ) {
        Button(
            modifier = modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            onClick = onClickPrevious
        ) {
            Text(text = stringResource(R.string.previous_button_text))
        }

        Spacer(modifier = modifier.width(32.dp))

        Button(
            modifier = modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            onClick = onClickNext
        ) {
            Text(text = stringResource(R.string.next_button_text))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        MainContent()
    }
}