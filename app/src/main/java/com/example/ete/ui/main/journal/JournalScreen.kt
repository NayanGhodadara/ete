package com.example.ete.ui.main.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ete.theme.red


@Preview(showSystemUi = true)
@Composable
fun JournalPreview() {
    JournalScreen()
}

@Composable
fun JournalScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Journal")
    }
}