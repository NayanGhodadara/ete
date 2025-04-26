@file:Suppress("DEPRECATION")

package com.example.ete.ui.main.create

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ete.BuildConfig
import com.example.ete.R
import com.example.ete.data.Constant.IntentObject.INTENT_MEDIA_URI
import com.example.ete.data.Constant.MainScreen.LIBRARY_TYPE
import com.example.ete.data.Constant.MainScreen.POST_TYPE
import com.example.ete.data.bean.post.PostMediaBean
import com.example.ete.di.dialog.ShowPermissionDialog
import com.example.ete.theme.black
import com.example.ete.theme.grayV2
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12
import com.example.ete.theme.red
import com.example.ete.theme.white
import com.example.ete.ui.main.MainActivityVM
import com.example.ete.ui.view.create.UploadMedia
import com.example.ete.ui.view.header.HeaderView
import com.example.ete.ui.view.location.RowLocation
import com.example.ete.util.AppUtils
import com.example.ete.util.cookie.CookieBar
import com.example.ete.util.progress.AnimatedCircularProgress
import com.example.ete.util.span.CustomBuildAnnotatedString
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
@Preview(showSystemUi = true)
fun CreatePostScreen(nav: NavController? = null) {

    //Location
    lateinit var broadcastGps: BroadcastReceiver

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val vm: MainActivityVM? = if (isPreview) {
        null
    } else {
        hiltViewModel(context as ComponentActivity)
    }

    broadcastGps = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val locationManager: LocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
            val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (intent != null) {
                val action = intent.action
                if (!TextUtils.isEmpty(action) && action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    if (isGpsEnable && isNetworkEnable) {
                        scope.launch {
                            delay(3000)
                            checkPermission(vm!!, context)
                        }
                    } else if (vm?.isGpsDialogShowing?.value?.not() == true && isGpsEnable.not()) {
                        vm.locationErrorShow.value = true
                        vm.locationError.value = ContextCompat.getString(context, R.string.on_gps_to_show_near_location)
                        showGpsDialog(context, vm)
                    }
                }
            }

        }
    }

    if (vm?.showCookies?.value == true) {
        CookieBar(
            vm.cookieBean.value.message,
            vm.cookieBean.value.type, onRemove = {
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    vm.showCookies.value = false
                }, 500)
            })
    }

    LaunchedEffect(Unit) {
        if (vm?.createPostList?.isEmpty() == true) {
            val json = nav?.currentBackStackEntry?.arguments?.getString(INTENT_MEDIA_URI)
            vm.createPostList.addAll(Gson().fromJson(json, object : TypeToken<ArrayList<String>>() {}.type) ?: arrayListOf())
        }
        checkPermission(vm!!, context)
        context.registerReceiver(broadcastGps, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    BackHandler {
        vm?.selectedType?.value = null
        vm?.selectedLibrary?.value = null
        vm?.createPostList?.clear()
        nav?.popBackStack()
    }

    DisposableEffect(Unit) {
        onDispose {
            context.unregisterReceiver(broadcastGps)
            Places.deinitialize()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineLocationGranted || coarseLocationGranted) {
            if (AppUtils.isGPS(context)) {
                vm?.placesClient = initPlaceApi(context)
                getLocation(vm!!, vm.placesClient!!, context)
                vm.locationErrorShow.value = false
            } else {
                vm?.isGpsDialogShowing?.value = true
            }
        } else {
            vm?.isPermissionShowing?.value = true
        }
    }

    if (vm?.isPermissionShowing?.value == true) {
        ShowPermissionDialog(
            title = stringResource(R.string.permission_required),
            desc = stringResource(R.string.location_access_needed_go_to_setting),
            onDismiss = {
                vm.isPermissionShowing.value = false
            },
            onConfirm = {
                val intent = Intent()
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.setData(uri)
                ContextCompat.startActivity(context, intent, null)
            },
            onCancel = {
                vm.isPermissionShowing.value = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderView(
            title = stringResource(R.string.new_post),
            isBackShow = true,
            onBackClick = {
                vm?.selectedType?.value = null
                vm?.selectedLibrary?.value = null
                nav?.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
        ) {
            LazyRow(
                modifier = Modifier
                    .aspectRatio(1f / 0.54f)
                    .padding(top = 10.dp),
                contentPadding = PaddingValues(horizontal = 19.dp)
            ) {
                items(vm?.createPostList ?: arrayListOf()) {
                    UploadMedia(
                        PostMediaBean(fileUrl = it)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
                .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                .background(color = grayV2_10, shape = RoundedCornerShape(9.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.TopStart
        ) {
            if (vm?.descField?.value?.isEmpty() == true) {
                Text(
                    text = stringResource(R.string.write_a_caption),
                    color = grayV2,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            BasicTextField(
                value = vm?.descField?.value.orEmpty(),
                onValueChange = { vm?.descField?.value = it },
                maxLines = 4,
                minLines = 4,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    nav?.navigate(POST_TYPE.name)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    if (vm?.selectedType?.value == null)
                        R.drawable.ic_radio
                    else
                        R.drawable.ic_selected_radio
                ),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = vm?.selectedType?.value?.let { type ->
                    if (type.title == stringResource(R.string.others) && !type.otherOption.isNullOrEmpty()) {
                        CustomBuildAnnotatedString().setCompleteString(type.otherOption.orEmpty())
                            .setString(type.otherOption.orEmpty())
                            .setFontStyle(FontFamily(Font(R.font.nunito_sans_bold)))
                            .build()
                    } else {
                        CustomBuildAnnotatedString().setCompleteString(type.title)
                            .setString(type.title)
                            .setFontStyle(FontFamily(Font(R.font.nunito_sans_bold)))
                            .build()
                    }
                } ?: CustomBuildAnnotatedString().getReqString(stringResource(R.string.type)),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp,
                color = black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            )

            Image(
                painter = painterResource(
                    if (vm?.selectedType?.value == null) {
                        R.drawable.ic_arrow_right
                    } else {
                        R.drawable.ic_close
                    }
                ),
                colorFilter = ColorFilter.tint(black),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        vm?.selectedType?.value = null
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 14.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    nav?.navigate(LIBRARY_TYPE.name)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = vm?.selectedLibrary?.value?.images?.firstOrNull()?.imageUrl
            Image(
                painter = if (!imageUrl.isNullOrEmpty()) {
                    rememberAsyncImagePainter(model = imageUrl)
                } else {
                    painterResource(R.drawable.ic_library)
                },
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp)
                    .clip(
                        if (vm?.selectedLibrary?.value == null) {
                            RoundedCornerShape(0.dp)
                        } else {
                            CircleShape
                        }
                    )
            )

            Text(
                text = if (vm?.selectedLibrary?.value != null) {
                    vm.selectedLibrary.value?.title.orEmpty()
                } else {
                    stringResource(R.string.link_to_library)
                },
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp,
                color = black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            )

            Image(
                painter = painterResource(
                    if (vm?.selectedLibrary?.value == null) {
                        R.drawable.ic_arrow_right
                    } else {
                        R.drawable.ic_close
                    }
                ),
                colorFilter = ColorFilter.tint(black),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        vm?.selectedLibrary?.value = null
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 14.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
//                        val intent = PostTypeActivity.newIntent(thiscom.ete.mobile.app.ui.main.post.CreatePostActivity, POST, plantTypeBean = postTypeBean)
//                        mStartForResultType.launch(intent)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = if (vm?.selectedLocation?.value.isNullOrEmpty() == true) {
                    stringResource(R.string.add_location)
                } else {
                    vm.selectedLocation.value.orEmpty()
                },
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp,
                color = black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            )

            Image(
                painter = if (vm?.selectedLocation?.value.isNullOrEmpty() == true) {
                    painterResource(R.drawable.ic_arrow_right)
                } else {
                    painterResource(R.drawable.ic_close)
                },
                colorFilter = ColorFilter.tint(black),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        vm?.selectedLocation?.value = null
                    }
            )
        }

        if (vm?.locationErrorShow?.value == true) {
            Text(
                text = vm.locationError.value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                color = red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 10.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (vm.locationError.value == context.getString(R.string.on_gps_to_show_near_location)) {
                            if (vm.isGpsDialogShowing.value.not()) {
                                showGpsDialog(context, vm)
                            }
                        } else {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    }
            )
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentPadding = PaddingValues(horizontal = 21.dp)
            ) {
                items(vm?.listOfLocation ?: arrayListOf()) { data ->
                    RowLocation(data, onClick = {
                        getPlaceInfo(vm!!, data.placeId)
                        vm.selectedLocation.value = data.getFullText(null).toString()
                    })
                }
            }
        }

        /** Create/Update **/
        Column(
            Modifier
                .weight(1f)
                .padding(bottom = 20.dp)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box {
                Text(
                    text = if (vm?.isLoading?.value == true) "" else stringResource(R.string.post),
                    style = MaterialTheme.typography.headlineSmall,
                    color = white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(black, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (vm?.isCreatePostValid(context) == true) {

                            }
                        },
                    textAlign = TextAlign.Center,
                )

                if (vm?.isLoading?.value == true) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AnimatedCircularProgress()
                    }
                }
            }
        }
    }
}

//Get device current location
private fun checkPermission(vm: MainActivityVM, context: Context) {
    when {
        AppUtils.checkLocationPermission(context) && AppUtils.isGPS(context) -> {
            vm.placesClient = initPlaceApi(context)
            vm.locationErrorShow.value = false
            getLocation(vm, vm.placesClient!!, context)
        }

        AppUtils.isGPS(context).not() -> {
            vm.locationErrorShow.value = true
            vm.locationError.value = context.getString(R.string.on_gps_to_show_near_location)
        }

        AppUtils.checkLocationPermission(context).not() -> {
            vm.locationErrorShow.value = true
            vm.locationError.value = context.getString(R.string.location_access_needed_to_show_near_location)
        }
    }
}

/**
 * Location
 * **/
//Get device current location
@SuppressLint("MissingPermission")
private fun getLocation(vm: MainActivityVM, placesClient: PlacesClient, context: Context) {
    val fuseLocationProvider = LocationServices.getFusedLocationProviderClient(context)
    fuseLocationProvider.lastLocation.addOnCompleteListener {
        try {
            vm.currentLong.doubleValue = it.result.longitude
            vm.currentLat.doubleValue = it.result.latitude
            placeApiCall(
                vm,
                placesClient,
                getSubLocalityAddress(vm.currentLat.doubleValue, vm.currentLong.doubleValue, context)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.addOnFailureListener {
    }
}

//Get subLocality  address from latitude and longitude
fun getSubLocalityAddress(latitude: Double, longitude: Double, context: Context): String {
    val geocoder = Geocoder(context)

    @Suppress("DEPRECATION")
    val addressList = geocoder.getFromLocation(latitude, longitude, 1)
    if (addressList?.isNotEmpty() == true) {
        if (addressList.first().subLocality.isNotEmpty()) {
            return addressList.first().subLocality
        }
    }
    return ""
}

//Show Permission dialog
private fun showGpsDialog(context: Context, vm: MainActivityVM) {
    vm.isGpsDialogShowing.value = true
    Handler(Looper.getMainLooper()).postDelayed({
        vm.isGpsDialogShowing.value = false
    }, 1000)

    val locationRequest: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
        .setWaitForAccurateLocation(false)
        .setMinUpdateIntervalMillis(100)
        .setMaxUpdateDelayMillis(100)
        .build()

    val locationSettingRequest: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    locationSettingRequest.setAlwaysShow(true)

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(locationSettingRequest.build())

    task.addOnFailureListener {
        if (it is ResolvableApiException) {
            try {
                val exception: ResolvableApiException = it
                exception.startResolutionForResult(context as ComponentActivity, 100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
//End ::--- Location Permission ---

/**
 * Place Api
 * **/
//Init Place Api
private fun initPlaceApi(context: Context): PlacesClient {
    if (!Places.isInitialized()) {
        Places.initialize(context, String(Base64.decode(BuildConfig.PLACE_API_KEY, Base64.DEFAULT)), Locale.getDefault())
    }
    return Places.createClient(context)
}

// Place Api Call
private fun placeApiCall(vm: MainActivityVM, placesClient: PlacesClient, searchTitle: String) {
    val token = AutocompleteSessionToken.newInstance()
    val request = FindAutocompletePredictionsRequest.builder().setSessionToken(token).setQuery(searchTitle.trim()).build()
    placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
        vm.listOfLocation.clear()
        vm.listOfLocation.addAll(response.autocompletePredictions)
    }.addOnFailureListener {
        it.printStackTrace()
    }
}

// Get place info from PlaceId
@Suppress("DEPRECATION")
private fun getPlaceInfo(vm: MainActivityVM, placeId: String) {
    var place: Place? = null
    val placeField = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
    val request = FetchPlaceRequest.newInstance(placeId, placeField)

    try {
        vm.placesClient?.fetchPlace(request)?.addOnSuccessListener { response: FetchPlaceResponse ->
            place = response.place
            vm.latitude.doubleValue = place?.latLng?.latitude ?: 0.0
            vm.longitude.doubleValue = place?.latLng?.longitude ?: 0.0
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
//End ::--- Place Api AND Location---