package com.example.ete.ui.main.profile

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ete.R
import com.example.ete.di.MyApplication
import com.example.ete.theme.black
import com.example.ete.theme.gray
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_12
import com.example.ete.ui.main.profile.setting.SettingActivity
import com.example.ete.ui.view.HeaderView


@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    ProfileScreen(rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavController) {
    val userBean = MyApplication.instance?.getUserData()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        HeaderView(
            title = stringResource(R.string.profile),
            isHelpShow = true,
            onHelpClick = {},
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(start = 24.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(userBean?.profilePic.orEmpty())
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .error(R.drawable.ic_profile_placeholder)
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
            )

            Column {
                Row {
                    Text(
                        text = userBean?.name.orEmpty(),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        color = black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .padding(horizontal = 24.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .padding(start = 24.dp)
                ) {
                    Column() {
                        Text(
                            text = userBean?.getPostCountString().orEmpty(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            color = black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.posts),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            color = grayV2,
                            modifier = Modifier
                                .padding(top = 2.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                    ) {
                        Text(
                            text = userBean?.getFollowerCountString().orEmpty(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            color = black,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = stringResource(R.string.followers),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            maxLines = 1,
                            color = grayV2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 2.dp)
                        )
                    }

                    Column(
                    ) {
                        Text(
                            text = userBean?.getFollowingCountString().orEmpty(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            color = black,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = stringResource(R.string.following),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            color = grayV2,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        Text(
            text = userBean?.getUsernameString().orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = grayV2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .padding(horizontal = 24.dp)
        )

        if (userBean?.bio.orEmpty().isNotEmpty())
            Text(
                text = userBean?.bio.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                color = grayV2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(horizontal = 24.dp)
            )

        if (userBean?.link.orEmpty().isNotEmpty())
            Text(
                text = userBean?.link.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(horizontal = 24.dp)
            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 7.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 14.sp,
                    color = black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(14.dp, spotColor = grayV2_12, shape = RoundedCornerShape(6.dp))
                        .background(gray, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, grayV2_12, shape = RoundedCornerShape(6.dp))
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 3.dp),
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 7.dp)
            ) {
                Text(
                    text = stringResource(R.string.share_profile),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 14.sp,
                    color = black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(14.dp, spotColor = grayV2_12, shape = RoundedCornerShape(6.dp))
                        .background(gray, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, grayV2_12, shape = RoundedCornerShape(6.dp))
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 3.dp),
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 7.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        val intent = Intent(context, SettingActivity::class.java)
                        context.startActivity(intent)
                    }
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 14.sp,
                    color = black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(14.dp, spotColor = grayV2_12, shape = RoundedCornerShape(6.dp))
                        .background(gray, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, grayV2_12, shape = RoundedCornerShape(6.dp))
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 3.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}