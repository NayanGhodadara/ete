package com.example.ete.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ete.data.remote.helper.Status
import com.example.ete.theme.black
import com.example.ete.theme.white
import com.example.ete.ui.main.MainActivityVM
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.post.PostView
import com.example.ete.ui.view.shimmer.ShimmerPost
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.progress.AnimatedCircularProgress
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen()
}

@Composable
fun HomeScreen() {

    val vm: MainActivityVM = hiltViewModel()

    //Pagination
    var isLastPageData by remember { mutableStateOf(false) }
    var isLoadingPage by remember { mutableStateOf(false) }
    var page by remember { mutableIntStateOf(1) }

    val obrGetPost = vm.obrGetPost.value

    var isRefreshing by remember { mutableStateOf(false) }
    var showShimmer by remember { mutableStateOf(false) }

    //Reset page
    fun resetPage() {
        page = 1
        isLastPageData = false
        isLoadingPage = false
    }

    LaunchedEffect(Unit) {
        if (vm.listOfPost.isEmpty()) {
            vm.callGetPostApi(page)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Status bar spacer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) {
                    WindowInsets.systemBars.getTop(this).toDp()
                })
                .background(white)
        )

        // Custom header
        HeaderView(
            canShowHomeHeader = true
        )

        // Make SwipeRefresh fill the remaining space
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                if (!isLoadingPage) {
                    isRefreshing = true
                    resetPage()
                    vm.callGetPostApi(page)
                }
            },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    scale = true,
                    contentColor = black
                )
            },
            modifier = Modifier.weight(1f)
        ) {

            LazyColumn(
                contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (showShimmer) {
                    item {
                        ShimmerPost()
                        ShimmerPost()
                        ShimmerPost()
                    }
                }

                itemsIndexed(vm.listOfPost) { index, postBean ->
                    PostView(
                        postBean,
                        onOptionClick = {
                            vm.listOfPost[index] = postBean.copy(showPopup = postBean.showPopup.not())
                        },
                        onUpdate = { updatedData ->
                            val index = vm.listOfPost.indexOfFirst { it.id == updatedData.id }
                            if (index != -1) {
                                vm.listOfPost[index] = updatedData
                            }
                        }
                    )

                    // Trigger pagination when user scrolls to last item
                    if (index == vm.listOfPost.lastIndex
                        && !isLoadingPage
                        && isLastPageData.not()
                    ) {
                        LaunchedEffect(Unit) {
                            page++
                            vm.callGetPostApi(page)
                        }
                    }
                }

                if (isLoadingPage && showShimmer.not() && isRefreshing.not()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedCircularProgress()
                        }
                    }
                }
            }
        }
    }

    when (obrGetPost.status) {
        Status.LOADING -> {
            isLoadingPage = true
            showShimmer = page == 1 && !isRefreshing && vm.listOfPost.isEmpty()
        }

        Status.SUCCESS -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            isLastPageData = obrGetPost.data?.meta?.currentPage == obrGetPost.data?.meta?.totalPages

            if (page == 1) {
                vm.listOfPost.clear()
            }

            vm.listOfPost.addAll(obrGetPost.data?.data ?: arrayListOf())
        }

        Status.WARN -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetPost.message.orEmpty(), CookieBarType.WARNING)
        }

        Status.ERROR -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetPost.message.orEmpty(), CookieBarType.ERROR)
        }

        else -> {}
    }
}