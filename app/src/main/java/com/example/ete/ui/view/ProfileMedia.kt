package com.example.ete.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ete.R
import com.example.ete.data.bean.post.PostBean

@Composable
@Preview(showSystemUi = true)
fun ProfileMedia(
    postBean: PostBean? = null,
    isMultiPost: Boolean = false,
    isVideo: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.5.dp)
            .background(color = colorResource(R.color.black_50))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = postBean?.getFileUrl()
            ),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .aspectRatio(1f / 1f)
        )

        if (isMultiPost) {
            Image(
                painter = painterResource(R.drawable.ic_multi_post),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(6.dp)
            )
        }

        if (isVideo) {
            Image(
                painter = painterResource(R.drawable.ic_video),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
            )
        }
    }
}
