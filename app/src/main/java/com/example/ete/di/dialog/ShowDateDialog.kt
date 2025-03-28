package com.example.ete.di.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ete.theme.black
import com.example.ete.theme.gray
import com.example.ete.theme.white
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDateDialog(selectedDate: Long, onDateSelect: (Long?, String) -> Unit = { _, _ -> }) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )


    var formattedDate by remember {
        mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedDate)))
    }


    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { newDate ->
            formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(newDate))
        }
    }

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = white
        ),
        onDismissRequest = {
            onDateSelect(null,"")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelect(datePickerState.selectedDateMillis,formattedDate)
                }
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.bodyLarge,
                    color = black
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDateSelect(null,formattedDate) }
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyLarge,
                    color = black
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            headline = {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(24.dp)
                )
            },
            modifier = Modifier.background(Color.Transparent),
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                headlineContentColor = Color.Black,
                subheadContentColor = Color.Black,
                selectedDayContainerColor = Color.Black,
                selectedDayContentColor = Color.White,
                todayDateBorderColor = Color.Black,
                todayContentColor = Color.Black,
                dayContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                dividerColor = gray,
                yearContentColor = Color.Black,
                selectedYearContainerColor = Color.Black,
                selectedYearContentColor = Color.White,
                currentYearContentColor = Color.Black,
            )
        )
    }
}
