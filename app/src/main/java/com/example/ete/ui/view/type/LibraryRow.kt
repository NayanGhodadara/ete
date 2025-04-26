package com.example.ete.ui.view.type

import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import coil.compose.rememberAsyncImagePainter
import com.example.ete.R
import com.example.ete.data.bean.library.LibraryBean
import com.example.ete.theme.black
import com.example.ete.theme.grayV2_10
import com.example.ete.theme.grayV2_12

@Composable
@Preview(showBackground = true)
fun TypeLibrary(
    libraryBean: LibraryBean? = null,
    onClick: () -> Unit = {},
) {
    val isPreview = LocalInspectionMode.current
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .height(70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {
        Image(
            painter = if (isPreview) {
                painterResource(R.drawable.bg_flower)
            } else {
                rememberAsyncImagePainter(
                    model = libraryBean?.images?.first()?.imageUrl,
                )
            },
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .height(IntrinsicSize.Min)
                .padding(start = 8.dp)
                .background(grayV2_10, shape = RoundedCornerShape(10.dp))
                .border(1.dp, grayV2_12, shape = RoundedCornerShape(10.dp))
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = libraryBean?.title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp),
            )

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .padding(horizontal = 8.dp),
                factory = { context ->
                    TextView(context).apply {
                        text = Html.fromHtml(libraryBean?.description.orEmpty(), Html.FROM_HTML_MODE_COMPACT)
                        textSize = 12f
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        setTextColor(ContextCompat.getColor(context, R.color.gray2))
                        typeface = ResourcesCompat.getFont(context, R.font.nunito_sans_regular)
                    }
                }
            )
        }
    }
}