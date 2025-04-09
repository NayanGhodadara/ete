package com.example.ete.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ete.R
import com.example.ete.data.bean.post.PostBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV3
import com.example.ete.util.span.CustomBuildAnnotatedString

@Composable
@Preview(showBackground = true)
fun PostViewPreview() {
    PostView(
        null,
        onOptionClick = {}
    )
}

@Composable
fun PostView(
    postBean: PostBean? = null,
    onOptionClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = postBean?.user?.profilePic.orEmpty(),
                    error = painterResource(R.drawable.ic_profile_placeholder),
                    placeholder = painterResource(R.drawable.ic_profile_placeholder),
                ),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(45.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = if (isPreview)
                        stringResource(R.string.are_you_sure_you_want_to_logout_your_account)
                    else
                        postBean?.user?.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    color = black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = if (isPreview)
                        stringResource(R.string.are_you_sure_you_want_to_logout_your_account)
                    else
                        postBean?.address.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = grayV2,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_option),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(27.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onOptionClick()
                    },
                contentScale = ContentScale.Crop
            )
        }

        LazyRow(
            modifier = Modifier
                .aspectRatio((postBean?.width?.toFloat() ?: 1f) / (postBean?.height?.toFloat() ?: 1f))
        ) {
            items(postBean?.postImageVideo ?: arrayListOf()) { postMedia ->
                PostMedia(postMedia)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(
                        if (postBean?.isPostLiked == true) R.drawable.ic_like
                        else R.drawable.ic_unlike
                    ),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = if (isPreview) "102" else postBean?.getLikeCount().orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = grayV3,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(20.dp)
                )

                Text(
                    text = if (isPreview) "10" else postBean?.getCommentCount().orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = grayV3,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(20.dp),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_library),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(20.dp)
                )

                Image(
                    painter = painterResource(
                        if (postBean?.isPostInWishlist == true)
                            R.drawable.ic_save
                        else
                            R.drawable.ic_unsave
                    ),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

        Text(
            text = if (isPreview)
                buildAnnotatedString { append("1234") }
            else
                CustomBuildAnnotatedString.instance
                    .setCompleteString("${postBean?.user?.userName} ${postBean?.caption}")
                    .setFontFamily(postBean?.user?.userName.orEmpty(), MaterialTheme.typography.headlineSmall),
            style = MaterialTheme.typography.bodyLarge,
            color = grayV3,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp)
                .padding(horizontal = 14.dp)
        )

        Text(
            text = if (isPreview)
                buildAnnotatedString { append("1234") }
            else
                CustomBuildAnnotatedString.instance
                    .setCompleteString("${postBean?.commentedUser?.user?.userName} ${postBean?.commentedUser?.comment.orEmpty()}")
                    .setFontFamily(postBean?.commentedUser?.user?.userName.orEmpty(), MaterialTheme.typography.headlineSmall),
            style = MaterialTheme.typography.bodyLarge,
            color = grayV3,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, top = 4.dp)
                .padding(horizontal = 14.dp)
        )
    }
}