package com.example.ete.ui.welcome

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.R
import com.example.ete.theme.EteTheme
import com.example.ete.theme.Typography
import com.example.ete.theme.gray

class WelcomeActivity : ComponentActivity() {

    private val vm: WelcomeActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EteTheme {
                WelcomeView()
            }
        }
    }
}

@Composable
fun WelcomeView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 67.dp)
                .size(90.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {
            Item(
                0,
                stringResource(R.string.continue_with_email),
                painterResource(R.drawable.ic_email)
            )
            Item(
                14,
                stringResource(R.string.continue_with_phone_no),
                painterResource(R.drawable.ic_phone)
            )
            Item(
                14,
                stringResource(R.string.continue_with_google), painterResource(R.drawable.ic_google)
            )
        }

    }
}

@Composable
fun Item(top: Int = 0, title: String, icon: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (top != 0) top.dp else 0.dp)
            .padding(horizontal = 24.dp)
            .border(1.dp, color = gray, shape = RoundedCornerShape(18.dp))
            .padding(vertical = 12.dp)
    ) {

        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(start = 67.dp)
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 14.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewWelcome() {
    WelcomeView()
}