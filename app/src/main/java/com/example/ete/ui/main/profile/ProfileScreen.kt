package com.example.ete.ui.main.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ete.R
import com.example.ete.data.Constant.PostType.BOOKMARK
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.bean.post.PostBean
import com.example.ete.data.remote.helper.Status
import com.example.ete.di.MyApplication
import com.example.ete.theme.black
import com.example.ete.theme.gray
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_12
import com.example.ete.ui.main.MainActivityVM
import com.example.ete.ui.main.profile.edit.EditProfileActivity
import com.example.ete.ui.main.profile.setting.SettingActivity
import com.example.ete.ui.view.HeaderView
import com.example.ete.ui.view.ProfileMedia
import com.example.ete.ui.view.ProfileTabs
import com.example.ete.ui.view.shimmer.ShimmerPostMedia
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    ProfileScreen(rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavController) {
    val vm: MainActivityVM = hiltViewModel()
    val obrGetPost = vm.obrGetUserPost.observeAsState()

    //Pagination
    var isLoadingPage = false
    var isLastPageData = false
    var page = 1
    var isRefreshing by remember { mutableStateOf(false) }

    var showShimmer by remember { mutableStateOf(false) }

    val userBean = MyApplication.instance?.getUserData()
    var isWishList = false
    var selectedTypeId = 0L
    val context = LocalContext.current

    val listOfTabs = remember { mutableStateListOf<DropDownBean>() }
    val listOfPost = remember { mutableStateListOf<PostBean>() }

    LaunchedEffect(Unit) {
        if (listOfTabs.isEmpty()) {
            listOfTabs.add(DropDownBean(id = 0L, title = "", isSelected = true))
            listOfTabs.add(DropDownBean(BOOKMARK, title = context.getString(R.string.bookmark)))
            MyApplication.instance?.getDropDownList()?.postType?.forEach {
                listOfTabs.add(it)
            }
        }
    }

    //Reset page
    fun resetPage() {
        page = 1
        isLastPageData = false
        isLoadingPage = false
    }

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
                        .padding(horizontal = 3.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            context.startActivity(Intent(context, EditProfileActivity::class.java))
                        },
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(grayV2_12, shape = RoundedCornerShape(5.dp))
                    .align(Alignment.BottomCenter)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(listOfTabs) { data ->
                    ProfileTabs(data, onClick = { dropDownBean ->
                        listOfTabs.replaceAll { tab ->
                            if (tab.title == dropDownBean.title) {
                                dropDownBean
                            } else {
                                tab.copy(isSelected = false)
                            }
                        }

                        selectedTypeId = dropDownBean.id
                        isWishList = dropDownBean.id == BOOKMARK
                        resetPage()
                        vm.callGetUserPostApi(page, isWishList, selectedTypeId)
                    })
                }
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                resetPage()
                vm.callGetUserPostApi(page, isWishList, selectedTypeId)
            }
        ) {
            if (showShimmer && page == 1 && !isRefreshing) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(bottom = 40.dp)
                ) {
                    items(30) {
                        ShimmerPostMedia()
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(bottom = 40.dp)
                ) {
                    items(listOfPost) { post ->
                        ProfileMedia(
                            post,
                            isMultiPost = post.postImageVideo.size > 1,
                            isVideo = post.postImageVideo.any { it.isImage }.not()
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.callGetUserPostApi(page, isWishList, selectedTypeId)
    }

    when (obrGetPost.value?.status) {
        Status.LOADING -> {
            isLoadingPage = true
            showShimmer = page == 1 && !isRefreshing
        }

        Status.SUCCESS -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            isLastPageData = obrGetPost.value?.data?.meta?.currentPage == obrGetPost.value?.data?.meta?.totalPages

            if (page == 1) {
                listOfPost.clear()
            }

            listOfPost.addAll(obrGetPost.value?.data?.data ?: arrayListOf())
        }

        Status.WARN -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetPost.value?.message.orEmpty(), CookieBarType.WARNING)
        }

        Status.ERROR -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetPost.value?.message.orEmpty(), CookieBarType.ERROR)
        }

        else -> {}
    }
}