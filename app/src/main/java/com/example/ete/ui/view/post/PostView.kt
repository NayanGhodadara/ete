package com.example.ete.ui.view.post

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ete.R
import com.example.ete.data.bean.post.PostBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.grayV3
import com.example.ete.ui.view.like.LikeButtonWithFlyingHearts
import com.example.ete.util.span.CustomBuildAnnotatedString

@Composable
@Preview(showBackground = true)
fun PostViewPreview() {
    PostView(
        null,
        onOptionClick = {},
    )
}

@Composable
fun PostView(
    postBean: PostBean? = null,
    onOptionClick: () -> Unit = {},
    onUpdate: (PostBean) -> Unit = {},
) {
    val isPreview = LocalInspectionMode.current

    var anchorBounds by remember { mutableStateOf(Rect(0f, 0f, 0f, 0f)) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DropdownMenu(
            expanded = postBean?.showPopup == true,
            onDismissRequest = { onOptionClick() },
            modifier = Modifier
                .background(Color.White),
            offset = DpOffset(
                x = anchorBounds.left.dp,
                y = anchorBounds.bottom.dp + 70.dp
            )
        ) {
            // Block item
            Text(
                text = "Block",
                color = Color.Red,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Handle block
                        onOptionClick()
                    }
                    .padding(16.dp)
            )

            Divider()

            // Report item
            Text(
                text = "Report",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clickable {
                        onOptionClick()
                    }
                    .padding(16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
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
                    .size(45.dp)
                    .clip(CircleShape),
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
                    .onGloballyPositioned { coordinates ->
                        anchorBounds = coordinates.boundsInParent()
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onOptionClick()
                    },
                contentScale = ContentScale.Crop
            )
        }

        val pagerState = rememberPagerState(initialPage = 0, pageCount = {
            postBean?.postImageVideo?.size ?: 0
        })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .aspectRatio((postBean?.width?.toFloat() ?: 1f) / (postBean?.height?.toFloat() ?: 1f))
                .background(grayV2)
        ) { pageIndex ->
            val isPreview = LocalInspectionMode.current

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (postBean?.postImageVideo[pageIndex]?.isImage == true) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = if (isPreview) painterResource(R.drawable.ic_profile_placeholder)
                            else postBean.postImageVideo[pageIndex].fileUrl.orEmpty(),
                        ),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(grayV2),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = if (isPreview) painterResource(R.drawable.ic_profile_placeholder)
                            else postBean?.postImageVideo[pageIndex]?.thumbnailUrl.orEmpty(),
                        ),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(grayV2),
                        contentScale = ContentScale.Crop
                    )
                }
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

                //Like
                LikeButtonWithFlyingHearts(
                    isLiked = postBean?.isPostLiked == true,
                    onLikeClick = {
                        postBean?.copy(
                            isPostLiked = postBean.isPostLiked.not(),
                            postLikeCount = if (postBean.isPostLiked == true) {
                                postBean.postLikeCount - 1
                            } else {
                                postBean.postLikeCount + 1
                            }
                        )?.let { onUpdate(it) }
                    }
                )

                Text(
                    text = if (isPreview) "102" else postBean?.getLikeCount().orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = grayV3,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )

                //comment
                Image(
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(25.dp)
                )

                Text(
                    text = if (isPreview) "10" else postBean?.getCommentCount().orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = grayV3,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )

                //Share
                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(25.dp),
                )
            }

            //Library and watchlist
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
                    colorFilter = ColorFilter.tint(grayV2),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(25.dp)
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
                        .size(25.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            postBean?.copy(isPostInWishlist = postBean.isPostInWishlist.not())?.let { onUpdate(it) }
                        }
                )
            }
        }

        if (postBean?.caption.isNullOrEmpty().not()) {
            Text(
                text = if (isPreview)
                    buildAnnotatedString { append("1234") }
                else
                    CustomBuildAnnotatedString.instance
                        .setCompleteString("${postBean.user.userName} ${postBean.caption}")
                        .setString(postBean.user.userName.orEmpty())
                        .setFontStyle(FontFamily(Font(R.font.nunito_sans_bold)), black)
                        .build(),
                style = MaterialTheme.typography.bodyLarge,
                color = grayV3,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 14.dp)
            )
        }

        if (postBean?.commentedUser != null) {
            Text(
                text = if (isPreview)
                    buildAnnotatedString { append("1234") }
                else
                    CustomBuildAnnotatedString.instance
                        .setCompleteString("${postBean.commentedUser?.user?.userName} ${postBean.commentedUser?.comment.orEmpty()}")
                        .setString(postBean.commentedUser?.user?.userName.orEmpty())
                        .setFontStyle(FontFamily(Font(R.font.nunito_sans_bold)), black)
                        .build(),
                style = MaterialTheme.typography.bodyLarge,
                color = grayV3,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .padding(horizontal = 14.dp)
            )
        }

        Text(
            text = stringResource(R.string.view_all_comment_count).format(postBean?.postCommentCount),
            style = MaterialTheme.typography.bodyLarge,
            color = grayV2,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .padding(horizontal = 14.dp)
        )

        Text(
            text = if (isPreview) "1 month ago" else postBean?.getCreatedTimeText(LocalContext.current).orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = grayV2,
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
                .padding(horizontal = 14.dp)
        )

        Spacer(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(grayV2_12)
        )
    }
}