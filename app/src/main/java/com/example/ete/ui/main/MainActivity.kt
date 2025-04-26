package com.example.ete.ui.main

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ete.R
import com.example.ete.data.Constant.IntentObject.INTENT_MEDIA_URI
import com.example.ete.data.Constant.MainScreen.ADD
import com.example.ete.data.Constant.MainScreen.HOME
import com.example.ete.data.Constant.MainScreen.LIBRARY_TYPE
import com.example.ete.data.Constant.MainScreen.MY_JOURNAL
import com.example.ete.data.Constant.MainScreen.POST_TYPE
import com.example.ete.data.Constant.MainScreen.PROFILE
import com.example.ete.data.Constant.MainScreen.SEARCH
import com.example.ete.di.dialog.ShowImagePickerDialog
import com.example.ete.di.dialog.ShowPermissionDialog
import com.example.ete.theme.EteTheme
import com.example.ete.theme.black
import com.example.ete.theme.black_8
import com.example.ete.theme.grayV2
import com.example.ete.theme.white
import com.example.ete.ui.main.create.CreatePostScreen
import com.example.ete.ui.main.home.HomeScreen
import com.example.ete.ui.main.journal.JournalScreen
import com.example.ete.ui.main.profile.ProfileScreen
import com.example.ete.ui.main.search.SearchScreen
import com.example.ete.ui.picker.LibraryType
import com.example.ete.ui.picker.PostType
import com.example.ete.util.AppUtils.createImageUri
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var vm: MainActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider.create(this)[MainActivityVM::class]

        setContent {
            EteTheme {
                MainView()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun MainView() {
        val navController = rememberNavController()
        var selectedTab by remember { mutableStateOf(HOME) }
        var showAddPopup by remember { mutableStateOf(false) }

        val showImagePicker = remember { mutableStateOf(false) }
        val showPermissionDialog = remember { mutableStateOf(false) }

        val tempUri = remember { mutableStateOf<Uri?>(null) }

        val imageLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success && tempUri.value != null) {
                val list = arrayListOf(tempUri.value.toString())
                navController.navigate("${ADD.name}?${INTENT_MEDIA_URI}=${Gson().toJson(list)}")
            }
        }

        val isCameraPermissionGrant = ContextCompat.checkSelfPermission(
            this, CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val cameraPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val uri = createImageUri(this)
                tempUri.value = uri
                uri.let { imageLauncher.launch(it) }
            } else {
                if (showPermissionDialog.value.not()) {
                    showPermissionDialog.value = true
                }
            }
        }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uriList ->
            if (uriList.isNotEmpty()) {
                val selectedImgList = uriList.map { it.toString() }
                navController.navigate("${ADD.name}?${INTENT_MEDIA_URI}=${Gson().toJson(selectedImgList)}")
            }
        }

        val isGalleryPermissionGrant =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(this, READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }

        val galleryPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                val allGranted = permissions.all { it.value }
                if (allGranted) {
                    galleryLauncher.launch("image/*")
                } else {
                    if (showPermissionDialog.value.not()) {
                        showPermissionDialog.value = true
                    }
                }
            }
        )

        if (showPermissionDialog.value) {
            ShowPermissionDialog(
                stringResource(R.string.permission_required),
                stringResource(R.string.camera_gallery_permission),
                onCancel = {
                    showPermissionDialog.value = false
                },
                onConfirm = {
                    val intent = Intent()
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.setData(uri)
                    //ContextCompat.startActivity(context, intent, null)
                    showPermissionDialog.value = false
                },
                onDismiss = {
                    showPermissionDialog.value = false
                }
            )
        }

        LaunchedEffect(showAddPopup) {
            vm.showBlur.value = showAddPopup
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        LaunchedEffect(currentDestination) {
            when (currentDestination) {
                HOME.name -> selectedTab = HOME
                SEARCH.name -> selectedTab = SEARCH
                MY_JOURNAL.name -> selectedTab = MY_JOURNAL
                PROFILE.name -> selectedTab = PROFILE
            }
        }

        LaunchedEffect(selectedTab) {
            showAddPopup = false
            when (selectedTab) {
                HOME -> {

                }

                SEARCH -> {

                }

                ADD -> {
                    showAddPopup = true
                }

                MY_JOURNAL -> {

                }

                PROFILE -> {

                }

                else -> {}
            }
        }


        Box {
            //Show pop for create post and journal
            if (showAddPopup) {
                Column(
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxSize()
                        .padding(bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .background(black_8)
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (showImagePicker.value.not()) {
                                        showImagePicker.value = true
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_pick_image),
                                contentDescription = stringResource(R.string.app_name),
                                modifier = Modifier
                                    .size(35.dp)
                                    .background(white, shape = CircleShape)
                                    .padding(7.dp)
                            )

                            Text(
                                text = stringResource(R.string.post),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 12.sp,
                                color = black,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .background(white, shape = RoundedCornerShape(4.dp))
                                    .padding(vertical = 3.dp, horizontal = 17.dp)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(start = 24.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_journal),
                                contentDescription = stringResource(R.string.app_name),
                                colorFilter = ColorFilter.tint(black),
                                modifier = Modifier
                                    .size(35.dp)
                                    .background(white, shape = CircleShape)
                                    .padding(7.dp)
                            )

                            Text(
                                text = stringResource(R.string.journal),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 12.sp,
                                color = black,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .background(white, shape = RoundedCornerShape(4.dp))
                                    .padding(vertical = 3.dp, horizontal = 7.dp)
                            )
                        }
                    }

                    Image(
                        painter = if (showAddPopup) {
                            painterResource(R.drawable.ic_close)
                        } else {
                            painterResource(R.drawable.ic_add)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .background(
                                color = black,
                                shape = RoundedCornerShape(360.dp)
                            )
                            .padding(11.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                showAddPopup = showAddPopup.not()
                            }
                    )
                }
            }

            Scaffold(
                bottomBar = {
                    if (vm.canNavigationBarShow.value == true) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.blur(
                                if (vm.showBlur.value == true) {
                                    5.dp
                                } else {
                                    0.dp
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(white)
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                //HOME
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            navigateToTab(navController, HOME.name)
                                        }
                                ) {
                                    if (selectedTab == HOME) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(5.dp)
                                                .fillMaxWidth()
                                                .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                                .padding(horizontal = 4.dp)
                                        )
                                    }

                                    Image(
                                        painter = if (selectedTab == HOME) painterResource(R.drawable.ic_selected_home) else painterResource(R.drawable.ic_home),
                                        contentDescription = stringResource(R.string.app_name),
                                        modifier = Modifier
                                            .padding(top = if (selectedTab == HOME) 10.dp else 15.dp)
                                            .align(Alignment.CenterHorizontally),
                                    )
                                    Text(
                                        text = stringResource(R.string.home),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTab == HOME) black else grayV2,
                                        fontSize = 10.sp,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }

                                //SEARCH
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            navigateToTab(navController, SEARCH.name)
                                        }
                                ) {
                                    if (selectedTab == SEARCH) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(5.dp)
                                                .fillMaxWidth()
                                                .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                                .padding(horizontal = 4.dp)
                                        )
                                    }

                                    Image(
                                        painter = if (selectedTab == SEARCH) painterResource(R.drawable.ic_selected_search) else painterResource(R.drawable.ic_search),
                                        contentDescription = stringResource(R.string.app_name),
                                        modifier = Modifier
                                            .padding(top = if (selectedTab == SEARCH) 10.dp else 15.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    Text(
                                        text = stringResource(R.string.search),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTab == SEARCH) black else grayV2,
                                        fontSize = 10.sp,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }

                                //Add
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_add),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .background(
                                                color = black,
                                                shape = RoundedCornerShape(360.dp)
                                            )
                                            .padding(11.dp)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ) {
                                                showAddPopup = showAddPopup.not()
                                            }
                                    )
                                }


                                //MY_JOURNAL
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            navigateToTab(navController, MY_JOURNAL.name)
                                        }
                                ) {
                                    if (selectedTab == MY_JOURNAL) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(5.dp)
                                                .fillMaxWidth()
                                                .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                                .padding(horizontal = 4.dp)
                                        )
                                    }

                                    Image(
                                        painter = if (selectedTab == MY_JOURNAL) painterResource(R.drawable.ic_selected_tab_journal) else painterResource(R.drawable.ic_tab_journal),
                                        contentDescription = stringResource(R.string.app_name),
                                        modifier = Modifier
                                            .padding(top = if (selectedTab == MY_JOURNAL) 10.dp else 15.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    Text(
                                        text = stringResource(R.string.my_journal),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTab == MY_JOURNAL) black else grayV2,
                                        fontSize = 10.sp,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }

                                //PROFILE
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            navigateToTab(navController, PROFILE.name)
                                        }
                                ) {
                                    if (selectedTab == PROFILE) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(5.dp)
                                                .fillMaxWidth()
                                                .background(black, shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                                .padding(horizontal = 4.dp)
                                        )
                                    }

                                    Image(
                                        painter = if (selectedTab == PROFILE) painterResource(R.drawable.ic_selected_profile) else painterResource(R.drawable.ic_profile),
                                        contentDescription = stringResource(R.string.app_name),
                                        modifier = Modifier
                                            .padding(top = if (selectedTab == PROFILE) 10.dp else 15.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    Text(
                                        text = stringResource(R.string.profile),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTab == PROFILE) black else {
                                            grayV2
                                        },
                                        fontSize = 10.sp,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                        }
                    }
                }
            ) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(white)
                        .padding(paddingValues)
                        .blur(
                            if (vm.showBlur.value == true) {
                                5.dp
                            } else {
                                0.dp
                            }
                        )
                ) {

                    NavHost(navController, HOME.name) {
                        composable(HOME.name) {
                            vm.canNavigationBarShow.value = true
                            HomeScreen()
                        }
                        composable(SEARCH.name) {
                            vm.canNavigationBarShow.value = true
                            SearchScreen()
                        }
                        composable(MY_JOURNAL.name) {
                            vm.canNavigationBarShow.value = true
                            JournalScreen()
                        }
                        composable(PROFILE.name) {
                            vm.canNavigationBarShow.value = true
                            ProfileScreen()
                        }
                        composable(
                            "${ADD.name}?${INTENT_MEDIA_URI}={$INTENT_MEDIA_URI}",
                            arguments = listOf(
                                navArgument(INTENT_MEDIA_URI) {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) {
                            vm.canNavigationBarShow.value = false
                            CreatePostScreen(navController)
                        }
                        composable(POST_TYPE.name) {
                            PostType(navController)
                        }

                        composable(LIBRARY_TYPE.name) {
                            LibraryType(navController)
                        }
                    }

                    //Show camera and gallery permission
                    if (showPermissionDialog.value) {
                        ShowPermissionDialog(
                            stringResource(R.string.permission_required),
                            stringResource(R.string.camera_gallery_permission),
                            onCancel = {
                                showPermissionDialog.value = false
                            },
                            onConfirm = {
                                val intent = Intent()
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.setData(uri)
                                showPermissionDialog.value = false
                            },
                            onDismiss = {
                                showPermissionDialog.value = false
                            }
                        )
                    }

                    //show Dialog for pick and capture image
                    if (showImagePicker.value) {
                        showAddPopup = false
                        ShowImagePickerDialog(
                            openCamera = {
                                if (isCameraPermissionGrant) {
                                    val uri = createImageUri(this@MainActivity)
                                    tempUri.value = uri
                                    uri.let { imageLauncher.launch(it) }
                                } else {
                                    cameraPermissionLauncher.launch(CAMERA)
                                }
                                showImagePicker.value = false
                            },
                            openGallery = {
                                if (isGalleryPermissionGrant) {
                                    galleryLauncher.launch("image/*")
                                } else {
                                    galleryPermissionLauncher.launch(
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            arrayOf(
                                                READ_MEDIA_IMAGES,
                                                READ_MEDIA_VIDEO
                                            )
                                        } else {
                                            arrayOf(
                                                READ_EXTERNAL_STORAGE,
                                                WRITE_EXTERNAL_STORAGE
                                            )
                                        }
                                    )
                                }
                                showImagePicker.value = false
                            },
                            onDismiss = {
                                showImagePicker.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    fun navigateToTab(navController: NavController, name: String) {
        navController.navigate(name) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
