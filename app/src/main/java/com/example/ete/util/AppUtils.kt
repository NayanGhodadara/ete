package com.example.ete.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.ete.data.Constant.PrefsKeys.AWS_TOKEN
import com.example.ete.data.Constant.PrefsKeys.IDENTITY_ID
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.aws.AWSBean
import com.example.ete.data.bean.country.CountryBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.ApiCallback
import com.example.ete.di.MyApplication
import com.example.ete.util.prefs.Prefs
import retrofit2.Response
import java.io.File
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object AppUtils {


    fun checkLocationPermission(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    //Check GPS
    fun isGPS(context: Context): Boolean {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    fun copyImageToAppStorage(context: Context, uri: Uri): String? {
        val appDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ETE")
        if (!appDir.exists()) appDir.mkdirs()  // Create directory if it doesn't exist

        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val newFile = File(appDir, fileName)

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                newFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            newFile.absolutePath  // Return the new absolute path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createImageUri(context: Context): Uri {
        val appSpecificDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ETE")

        if (!appSpecificDir.exists()) {
            appSpecificDir.mkdirs()
        }

        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val imageFile = File(appSpecificDir, fileName)

        // Return FileProvider URI
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider", // Must match your Manifest
            imageFile
        )
    }

    fun getDefaultCountry(context: Context): CountryBean {
        return try {
            val tm = context.getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
            val countryCodeValue = tm.networkCountryIso
            MyApplication.instance?.getCountryList()?.first { it.nameCode.lowercase() == countryCodeValue.lowercase() } ?: CountryBean()
        } catch (e: Exception) {
            e.printStackTrace()
            CountryBean("in", "91", "India")
        }
    }

    //Get device id
    @SuppressLint("HardwareIds")
    fun getDeviceId(c: Context): String {
        return Settings.Secure.getString(c.contentResolver, Settings.Secure.ANDROID_ID)
    }

    //Country list
    fun getCountryListWithCode(): MutableList<CountryBean> {
        val countries: MutableList<CountryBean> = ArrayList()
        countries.add(CountryBean("ad", "376", "Andorra"))
        countries.add(CountryBean("ae", "971", "United Arab Emirates (UAE)"))
        countries.add(CountryBean("af", "93", "Afghanistan"))
        countries.add(CountryBean("ag", "1", "Antigua and Barbuda"))
        countries.add(CountryBean("ai", "1", "Anguilla"))
        countries.add(CountryBean("al", "355", "Albania"))
        countries.add(CountryBean("am", "374", "Armenia"))
        countries.add(CountryBean("ao", "244", "Angola"))
        countries.add(CountryBean("aq", "672", "Antarctica"))
        countries.add(CountryBean("ar", "54", "Argentina"))
        countries.add(CountryBean("as", "1", "American Samoa"))
        countries.add(CountryBean("at", "43", "Austria"))
        countries.add(CountryBean("au", "61", "Australia"))
        countries.add(CountryBean("aw", "297", "Aruba"))
        countries.add(CountryBean("ax", "358", "Åland Islands"))
        countries.add(CountryBean("az", "994", "Azerbaijan"))
        countries.add(CountryBean("ba", "387", "Bosnia And Herzegovina"))
        countries.add(CountryBean("bb", "1", "Barbados"))
        countries.add(CountryBean("bd", "880", "Bangladesh"))
        countries.add(CountryBean("be", "32", "Belgium"))
        countries.add(CountryBean("bf", "226", "Burkina Faso"))
        countries.add(CountryBean("bg", "359", "Bulgaria"))
        countries.add(CountryBean("bh", "973", "Bahrain"))
        countries.add(CountryBean("bi", "257", "Burundi"))
        countries.add(CountryBean("bj", "229", "Benin"))
        countries.add(CountryBean("bl", "590", "Saint Barthélemy"))
        countries.add(CountryBean("bm", "1", "Bermuda"))
        countries.add(CountryBean("bn", "673", "Brunei Darussalam"))
        countries.add(CountryBean("bo", "591", "Bolivia, Plurinational State Of"))
        countries.add(CountryBean("br", "55", "Brazil"))
        countries.add(CountryBean("bs", "1", "Bahamas"))
        countries.add(CountryBean("bt", "975", "Bhutan"))
        countries.add(CountryBean("bw", "267", "Botswana"))
        countries.add(CountryBean("by", "375", "Belarus"))
        countries.add(CountryBean("bz", "501", "Belize"))
        countries.add(CountryBean("ca", "1", "Canada"))
        countries.add(CountryBean("cc", "61", "Cocos (keeling) Islands"))
        countries.add(CountryBean("cd", "243", "Congo, The Democratic Republic Of The"))
        countries.add(CountryBean("cf", "236", "Central African Republic"))
        countries.add(CountryBean("cg", "242", "Congo"))
        countries.add(CountryBean("ch", "41", "Switzerland"))
        countries.add(CountryBean("ci", "225", "Côte D'ivoire"))
        countries.add(CountryBean("ck", "682", "Cook Islands"))
        countries.add(CountryBean("cl", "56", "Chile"))
        countries.add(CountryBean("cm", "237", "Cameroon"))
        countries.add(CountryBean("cn", "86", "China"))
        countries.add(CountryBean("co", "57", "Colombia"))
        countries.add(CountryBean("cr", "506", "Costa Rica"))
        countries.add(CountryBean("cu", "53", "Cuba"))
        countries.add(CountryBean("cv", "238", "Cape Verde"))
        countries.add(CountryBean("cw", "599", "Curaçao"))
        countries.add(CountryBean("cx", "61", "Christmas Island"))
        countries.add(CountryBean("cy", "357", "Cyprus"))
        countries.add(CountryBean("cz", "420", "Czech Republic"))
        countries.add(CountryBean("de", "49", "Germany"))
        countries.add(CountryBean("dj", "253", "Djibouti"))
        countries.add(CountryBean("dk", "45", "Denmark"))
        countries.add(CountryBean("dm", "1", "Dominica"))
        countries.add(CountryBean("do", "1", "Dominican Republic"))
        countries.add(CountryBean("dz", "213", "Algeria"))
        countries.add(CountryBean("ec", "593", "Ecuador"))
        countries.add(CountryBean("ee", "372", "Estonia"))
        countries.add(CountryBean("eg", "20", "Egypt"))
        countries.add(CountryBean("er", "291", "Eritrea"))
        countries.add(CountryBean("es", "34", "Spain"))
        countries.add(CountryBean("et", "251", "Ethiopia"))
        countries.add(CountryBean("fi", "358", "Finland"))
        countries.add(CountryBean("fj", "679", "Fiji"))
        countries.add(CountryBean("fk", "500", "Falkland Islands (malvinas)"))
        countries.add(CountryBean("fm", "691", "Micronesia, Federated States Of"))
        countries.add(CountryBean("fo", "298", "Faroe Islands"))
        countries.add(CountryBean("fr", "33", "France"))
        countries.add(CountryBean("ga", "241", "Gabon"))
        countries.add(CountryBean("gb", "44", "United Kingdom"))
        countries.add(CountryBean("gd", "1", "Grenada"))
        countries.add(CountryBean("ge", "995", "Georgia"))
        countries.add(CountryBean("gf", "594", "French Guyana"))
        countries.add(CountryBean("gh", "233", "Ghana"))
        countries.add(CountryBean("gi", "350", "Gibraltar"))
        countries.add(CountryBean("gl", "299", "Greenland"))
        countries.add(CountryBean("gm", "220", "Gambia"))
        countries.add(CountryBean("gn", "224", "Guinea"))
        countries.add(CountryBean("gp", "450", "Guadeloupe"))
        countries.add(CountryBean("gq", "240", "Equatorial Guinea"))
        countries.add(CountryBean("gr", "30", "Greece"))
        countries.add(CountryBean("gt", "502", "Guatemala"))
        countries.add(CountryBean("gu", "1", "Guam"))
        countries.add(CountryBean("gw", "245", "Guinea-bissau"))
        countries.add(CountryBean("gy", "592", "Guyana"))
        countries.add(CountryBean("hk", "852", "Hong Kong"))
        countries.add(CountryBean("hn", "504", "Honduras"))
        countries.add(CountryBean("hr", "385", "Croatia"))
        countries.add(CountryBean("ht", "509", "Haiti"))
        countries.add(CountryBean("hu", "36", "Hungary"))
        countries.add(CountryBean("id", "62", "Indonesia"))
        countries.add(CountryBean("ie", "353", "Ireland"))
        countries.add(CountryBean("il", "972", "Israel"))
        countries.add(CountryBean("im", "44", "Isle Of Man"))
        countries.add(CountryBean("is", "354", "Iceland"))
        countries.add(CountryBean("in", "91", "India"))
        countries.add(CountryBean("io", "246", "British Indian Ocean Territory"))
        countries.add(CountryBean("iq", "964", "Iraq"))
        countries.add(CountryBean("ir", "98", "Iran, Islamic Republic Of"))
        countries.add(CountryBean("it", "39", "Italy"))
        countries.add(CountryBean("je", "44", "Jersey "))
        countries.add(CountryBean("jm", "1", "Jamaica"))
        countries.add(CountryBean("jo", "962", "Jordan"))
        countries.add(CountryBean("jp", "81", "Japan"))
        countries.add(CountryBean("ke", "254", "Kenya"))
        countries.add(CountryBean("kg", "996", "Kyrgyzstan"))
        countries.add(CountryBean("kh", "855", "Cambodia"))
        countries.add(CountryBean("ki", "686", "Kiribati"))
        countries.add(CountryBean("km", "269", "Comoros"))
        countries.add(CountryBean("kn", "1", "Saint Kitts and Nevis"))
        countries.add(CountryBean("kp", "850", "North Korea"))
        countries.add(CountryBean("kr", "82", "South Korea"))
        countries.add(CountryBean("kw", "965", "Kuwait"))
        countries.add(CountryBean("ky", "1", "Cayman Islands"))
        countries.add(CountryBean("kz", "7", "Kazakhstan"))
        countries.add(CountryBean("la", "856", "Lao People's Democratic Republic"))
        countries.add(CountryBean("lb", "961", "Lebanon"))
        countries.add(CountryBean("lc", "1", "Saint Lucia"))
        countries.add(CountryBean("li", "423", "Liechtenstein"))
        countries.add(CountryBean("lk", "94", "Sri Lanka"))
        countries.add(CountryBean("lr", "231", "Liberia"))
        countries.add(CountryBean("ls", "266", "Lesotho"))
        countries.add(CountryBean("lt", "370", "Lithuania"))
        countries.add(CountryBean("lu", "352", "Luxembourg"))
        countries.add(CountryBean("lv", "371", "Latvia"))
        countries.add(CountryBean("ly", "218", "Libya"))
        countries.add(CountryBean("ma", "212", "Morocco"))
        countries.add(CountryBean("mc", "377", "Monaco"))
        countries.add(CountryBean("md", "373", "Moldova, Republic Of"))
        countries.add(CountryBean("me", "382", "Montenegro"))
        countries.add(CountryBean("mf", "590", "Saint Martin"))
        countries.add(CountryBean("mg", "261", "Madagascar"))
        countries.add(CountryBean("mh", "692", "Marshall Islands"))
        countries.add(CountryBean("mk", "389", "Macedonia (FYROM)"))
        countries.add(CountryBean("ml", "223", "Mali"))
        countries.add(CountryBean("mm", "95", "Myanmar"))
        countries.add(CountryBean("mn", "976", "Mongolia"))
        countries.add(CountryBean("mo", "853", "Macau"))
        countries.add(CountryBean("mp", "1", "Northern Mariana Islands"))
        countries.add(CountryBean("mq", "596", "Martinique"))
        countries.add(CountryBean("mr", "222", "Mauritania"))
        countries.add(CountryBean("ms", "1", "Montserrat"))
        countries.add(CountryBean("mt", "356", "Malta"))
        countries.add(CountryBean("mu", "230", "Mauritius"))
        countries.add(CountryBean("mv", "960", "Maldives"))
        countries.add(CountryBean("mw", "265", "Malawi"))
        countries.add(CountryBean("mx", "52", "Mexico"))
        countries.add(CountryBean("my", "60", "Malaysia"))
        countries.add(CountryBean("mz", "258", "Mozambique"))
        countries.add(CountryBean("na", "264", "Namibia"))
        countries.add(CountryBean("nc", "687", "New Caledonia"))
        countries.add(CountryBean("ne", "227", "Niger"))
        countries.add(CountryBean("nf", "672", "Norfolk Islands"))
        countries.add(CountryBean("ng", "234", "Nigeria"))
        countries.add(CountryBean("ni", "505", "Nicaragua"))
        countries.add(CountryBean("nl", "31", "Netherlands"))
        countries.add(CountryBean("no", "47", "Norway"))
        countries.add(CountryBean("np", "977", "Nepal"))
        countries.add(CountryBean("nr", "674", "Nauru"))
        countries.add(CountryBean("nu", "683", "Niue"))
        countries.add(CountryBean("nz", "64", "New Zealand"))
        countries.add(CountryBean("om", "968", "Oman"))
        countries.add(CountryBean("pa", "507", "Panama"))
        countries.add(CountryBean("pe", "51", "Peru"))
        countries.add(CountryBean("pf", "689", "French Polynesia"))
        countries.add(CountryBean("pg", "675", "Papua New Guinea"))
        countries.add(CountryBean("ph", "63", "Philippines"))
        countries.add(CountryBean("pk", "92", "Pakistan"))
        countries.add(CountryBean("pl", "48", "Poland"))
        countries.add(CountryBean("pm", "508", "Saint Pierre And Miquelon"))
        countries.add(CountryBean("pn", "870", "Pitcairn Islands"))
        countries.add(CountryBean("pr", "1", "Puerto Rico"))
        countries.add(CountryBean("ps", "970", "Palestine"))
        countries.add(CountryBean("pt", "351", "Portugal"))
        countries.add(CountryBean("pw", "680", "Palau"))
        countries.add(CountryBean("py", "595", "Paraguay"))
        countries.add(CountryBean("qa", "974", "Qatar"))
        countries.add(CountryBean("re", "262", "Réunion"))
        countries.add(CountryBean("ro", "40", "Romania"))
        countries.add(CountryBean("rs", "381", "Serbia"))
        countries.add(CountryBean("ru", "7", "Russian Federation"))
        countries.add(CountryBean("rw", "250", "Rwanda"))
        countries.add(CountryBean("sa", "966", "Saudi Arabia"))
        countries.add(CountryBean("sb", "677", "Solomon Islands"))
        countries.add(CountryBean("sc", "248", "Seychelles"))
        countries.add(CountryBean("sd", "249", "Sudan"))
        countries.add(CountryBean("se", "46", "Sweden"))
        countries.add(CountryBean("sg", "65", "Singapore"))
        countries.add(CountryBean("sh", "290", "Saint Helena, Ascension And Tristan Da Cunha"))
        countries.add(CountryBean("si", "386", "Slovenia"))
        countries.add(CountryBean("sk", "421", "Slovakia"))
        countries.add(CountryBean("sl", "232", "Sierra Leone"))
        countries.add(CountryBean("sm", "378", "San Marino"))
        countries.add(CountryBean("sn", "221", "Senegal"))
        countries.add(CountryBean("so", "252", "Somalia"))
        countries.add(CountryBean("sr", "597", "Suriname"))
        countries.add(CountryBean("ss", "211", "South Sudan"))
        countries.add(CountryBean("st", "239", "Sao Tome And Principe"))
        countries.add(CountryBean("sv", "503", "El Salvador"))
        countries.add(CountryBean("sx", "1", "Sint Maarten"))
        countries.add(CountryBean("sy", "963", "Syrian Arab Republic"))
        countries.add(CountryBean("sz", "268", "Swaziland"))
        countries.add(CountryBean("tc", "1", "Turks and Caicos Islands"))
        countries.add(CountryBean("td", "235", "Chad"))
        countries.add(CountryBean("tg", "228", "Togo"))
        countries.add(CountryBean("th", "66", "Thailand"))
        countries.add(CountryBean("tj", "992", "Tajikistan"))
        countries.add(CountryBean("tk", "690", "Tokelau"))
        countries.add(CountryBean("tl", "670", "Timor-leste"))
        countries.add(CountryBean("tm", "993", "Turkmenistan"))
        countries.add(CountryBean("tn", "216", "Tunisia"))
        countries.add(CountryBean("to", "676", "Tonga"))
        countries.add(CountryBean("tr", "90", "Turkey"))
        countries.add(CountryBean("tt", "1", "Trinidad &amp; Tobago"))
        countries.add(CountryBean("tv", "688", "Tuvalu"))
        countries.add(CountryBean("tw", "886", "Taiwan"))
        countries.add(CountryBean("tz", "255", "Tanzania, United Republic Of"))
        countries.add(CountryBean("ua", "380", "Ukraine"))
        countries.add(CountryBean("ug", "256", "Uganda"))
        countries.add(CountryBean("us", "1", "United States"))
        countries.add(CountryBean("uy", "598", "Uruguay"))
        countries.add(CountryBean("uz", "998", "Uzbekistan"))
        countries.add(CountryBean("va", "379", "Holy See (vatican City State)"))
        countries.add(CountryBean("vc", "1", "Saint Vincent &amp; The Grenadines"))
        countries.add(CountryBean("ve", "58", "Venezuela, Bolivarian Republic Of"))
        countries.add(CountryBean("vg", "1", "British Virgin Islands"))
        countries.add(CountryBean("vi", "1", "US Virgin Islands"))
        countries.add(CountryBean("vn", "84", "Vietnam"))
        countries.add(CountryBean("vu", "678", "Vanuatu"))
        countries.add(CountryBean("wf", "681", "Wallis And Futuna"))
        countries.add(CountryBean("ws", "685", "Samoa"))
        countries.add(CountryBean("xk", "383", "Kosovo"))
        countries.add(CountryBean("ye", "967", "Yemen"))
        countries.add(CountryBean("yt", "262", "Mayotte"))
        countries.add(CountryBean("za", "27", "South Africa"))
        countries.add(CountryBean("zm", "260", "Zambia"))
        countries.add(CountryBean("zw", "263", "Zimbabwe"))
        return countries
    }

    //Get count in format
    fun getCounterString(count: Long): String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val value = floor(log10(count.toDouble())).toInt()
        val base = value / 3

        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(count / 10.0.pow((base * 3).toDouble())) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(count)
        }
    }

    //Call AWS token API
    fun callAWSTokenAPI(apiRepoImpl: ApiRepositoryImpl, callback: (Boolean) -> Unit) {
        try {

            apiRepoImpl.awsToken(object : ApiCallback<Response<ApiResponse<AWSBean>>>() {
                override fun onLoading() {

                }

                override fun onSuccess(response: Response<ApiResponse<AWSBean>>) {
                    Prefs.putString(IDENTITY_ID, response.body()?.data?.identityId)
                    Prefs.putString(AWS_TOKEN, response.body()?.data?.token)

                    callback(true)
                }

                override fun onFailed(message: String) {
                    callback(false)
                }

                override fun onErrorThrow(exception: Exception) {
                    callback(false)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            callback(false)
        }
    }
}