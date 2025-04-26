package com.example.ete.ui.view.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ete.data.bean.post.PostMediaBean
import com.example.ete.R

@Composable
@Preview(showSystemUi = true)
fun UploadMedia(
    postMediaBean: PostMediaBean? = null,
    isVideo: Boolean = false,
    onClick: (PostMediaBean) -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = colorResource(R.color.black_50), shape = RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = postMediaBean?.fileUrl
            ),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    postMediaBean?.let { onClick(it) }
                },
            contentScale = ContentScale.Crop
        )

        if (isVideo) {
            Image(
                painter = painterResource(R.drawable.ic_video),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}