package com.example.ete.ui.main.post

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ete.R
import com.example.ete.data.Constant.IntentObject.INTENT_ID
import com.example.ete.data.Constant.IntentObject.INTENT_IS_WATCH_LIST
import com.example.ete.data.remote.helper.Status
import com.example.ete.theme.EteTheme
import com.example.ete.theme.black
import com.example.ete.theme.white
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.post.PostView
import com.example.ete.ui.view.shimmer.ShimmerPost
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.progress.AnimatedCircularProgress
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExplorePostActivity : ComponentActivity() {

    private val vm: ExplorePostActivityVM by viewModels()

    private val isWatchList by lazy {
        intent.getBooleanExtra(INTENT_IS_WATCH_LIST, false)
    }

    private val id by lazy {
        intent.getLongExtra(INTENT_ID, 0L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EteTheme {
                MainView()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun MainView() {
        //Pagination
        var isLastPageData by remember { mutableStateOf(false) }
        var isLoadingPage by remember { mutableStateOf(false) }
        var page by remember { mutableIntStateOf(1) }

        //Reset page
        fun resetPage() {
            page = 1
            isLastPageData = false
            isLoadingPage = false
        }

        val obrGetPost = vm.obrGetUserPost

        var isRefreshing by remember { mutableStateOf(false) }
        var showShimmer by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            vm.callGetUserPostApi(page, isWatchList, id)
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
                title = stringResource(R.string.post),
                isBackShow = true,
                onBackClick = { finish() }
            )

            // Make SwipeRefresh fill the remaining space
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    if (!isLoadingPage) {
                        isRefreshing = true
                        resetPage()
                        vm.callGetUserPostApi(page, isWatchList, id)
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
                    contentPadding = PaddingValues(vertical = 12.dp),
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
                                vm.callGetUserPostApi(page, isWatchList, id)
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

        when (obrGetPost.value.status) {
            Status.LOADING -> {
                isLoadingPage = true
                showShimmer = page == 1 && !isRefreshing && vm.listOfPost.isEmpty()
            }

            Status.SUCCESS -> {
                showShimmer = false
                isRefreshing = false
                isLoadingPage = false
                isLastPageData = obrGetPost.value.data?.meta?.currentPage == obrGetPost.value.data?.meta?.totalPages

                if (page == 1) {
                    vm.listOfPost.clear()
                }

                vm.listOfPost.addAll(obrGetPost.value.data?.data ?: arrayListOf())
            }

            Status.WARN -> {
                showShimmer = false
                isRefreshing = false
                isLoadingPage = false
                CookieBar(obrGetPost.value.message.orEmpty(), CookieBarType.WARNING)
            }

            Status.ERROR -> {
                showShimmer = false
                isRefreshing = false
                isLoadingPage = false
                CookieBar(obrGetPost.value.message.orEmpty(), CookieBarType.ERROR)
            }

            else -> {}
        }
    }
}
