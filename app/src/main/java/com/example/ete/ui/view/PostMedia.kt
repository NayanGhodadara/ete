package com.example.ete.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.ete.mobile.app.data.bean.post.PostMediaBean
import com.example.ete.R
import com.example.ete.theme.grayV2

@Composable
@Preview(showBackground = true)
fun PostMediaView() {
    PostMedia(null)
}

@Composable
fun PostMedia(
    postMediaBean: PostMediaBean? = null,
) {

    val isPreview = LocalInspectionMode.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = if (isPreview) painterResource(R.drawable.ic_profile_placeholder)
                else postMediaBean?.fileUrl.orEmpty(),
                error = painterResource(R.drawable.ic_profile_placeholder),
                placeholder = painterResource(R.drawable.ic_profile_placeholder)
            ),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .fillMaxSize()
                .background(grayV2),
            contentScale = ContentScale.Fit
        )
    }
}