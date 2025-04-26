package com.example.ete.ui.picker


import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ete.R
import com.example.ete.data.remote.helper.Status
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.ui.main.MainActivityVM
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.shimmer.ShimmerLibrary
import com.example.ete.ui.view.type.TypeLibrary
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.cookie.CookieBarType
import com.example.ete.util.progress.AnimatedCircularProgress
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
@Preview(showSystemUi = true)
fun LibraryType(navController: NavController? = null) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val vm: MainActivityVM? = if (isPreview) {
        null
    } else {
        hiltViewModel(context as ComponentActivity)
    }

    //Pagination
    var isLastPageData by remember { mutableStateOf(false) }
    var isLoadingPage by remember { mutableStateOf(false) }
    var page by remember { mutableIntStateOf(1) }

    val obrGetLibraryList = vm?.obrGetLibraryList

    var isRefreshing by remember { mutableStateOf(false) }
    var showShimmer by remember { mutableStateOf(false) }

    //Reset page
    fun resetPage() {
        page = 1
        isLastPageData = false
        isLoadingPage = false
    }

    LaunchedEffect(vm?.fieldSearch?.value) {
        delay(500)
        vm?.callGetLibraryList(page)
    }

    LaunchedEffect(Unit) {
        vm?.listOfLibrary?.clear()
        vm?.callGetLibraryList(page)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderView(
            title = stringResource(R.string.link_to_library),
            isBackShow = true,
            onBackClick = {
                navController?.popBackStack()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
                .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                .background(color = grayV2_10, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 17.dp, vertical = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.app_name)
            )

            if (vm?.fieldSearch?.value?.isEmpty() == true) {
                Text(
                    text = stringResource(R.string.search),
                    color = grayV2,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 28.dp)
                )
            }
            BasicTextField(
                value = vm?.fieldSearch?.value.orEmpty(),
                onValueChange = { vm?.fieldSearch?.value = it },
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 28.dp),
            )
        }

        // Make SwipeRefresh fill the remaining space
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                if (!isLoadingPage) {
                    isRefreshing = true
                    resetPage()
                    vm?.callGetLibraryList(page)
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
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(vertical = 11.dp)
            ) {

                if (showShimmer) {
                    item(20) {
                        ShimmerLibrary()
                    }
                }

                itemsIndexed(vm?.listOfLibrary ?: arrayListOf()) { index, libraryBean ->

                    TypeLibrary(
                        libraryBean,
                        onClick = {
                            vm?.selectedLibrary?.value = libraryBean
                            navController?.popBackStack()
                        })

                    // Trigger pagination when user scrolls to last item
                    if (index == vm?.listOfLibrary?.lastIndex
                        && !isLoadingPage
                        && isLastPageData.not()
                    ) {
                        LaunchedEffect(Unit) {
                            page++
                            vm.callGetLibraryList(page)
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

    when (obrGetLibraryList?.value?.status) {
        Status.LOADING -> {
            isLoadingPage = true
            showShimmer = page == 1 && !isRefreshing && vm.listOfLibrary.isEmpty()
        }

        Status.SUCCESS -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            isLastPageData = obrGetLibraryList.value.data?.meta?.currentPage == obrGetLibraryList.value.data?.meta?.totalPages

            if (page == 1) {
                vm.listOfLibrary.clear()
            }

            vm.listOfLibrary.addAll(obrGetLibraryList.value.data?.data ?: arrayListOf())
        }

        Status.WARN -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetLibraryList.value.message.orEmpty(), CookieBarType.WARNING)
        }

        Status.ERROR -> {
            showShimmer = false
            isRefreshing = false
            isLoadingPage = false
            CookieBar(obrGetLibraryList.value.message.orEmpty(), CookieBarType.ERROR)
        }

        else -> {}
    }
}