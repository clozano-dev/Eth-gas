package com.clozanodev.ethereumgastracker.ui.screen

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import com.clozanodev.ethereumgastracker.util.SharedPreferencesManager
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModelFactory

@Composable
fun NotificationsScreen(
    viewModel: GasViewModel = viewModel(
        factory = GasViewModelFactory(
            application = LocalContext.current.applicationContext as Application,
            repository = GasRepository()
        )
    )
) {

    var lowerLimit by remember { mutableStateOf("") }
    var upperLimit by remember { mutableStateOf("") }
    val context = LocalContext.current

    val savedLowerLimit by viewModel.lowerLimit.observeAsState()
    val savedUpperLimit by viewModel.upperLimit.observeAsState()

    Surface(
        color = Color(0xFFE0E2EE),
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.notifications),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = lowerLimit,
                    onValueChange = { lowerLimit = it },
                    label = { Text(text = stringResource(R.string.lower_limit)) },
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Button(
                    onClick = {
                        val lowerLimitValue = lowerLimit.toIntOrNull() ?: 0
                        SharedPreferencesManager.saveLowerLimit(context, lowerLimitValue)
                        viewModel.saveLowerLimit(lowerLimitValue)
                        hideSoftKeyboard(context)
                    }, modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = stringResource(R.string.set))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = upperLimit,
                    onValueChange = { upperLimit = it },
                    label = { Text(text = stringResource(R.string.upper_limit)) },
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Button(
                    onClick = {
                        val upperLimitValue = upperLimit.toIntOrNull() ?: 0
                        SharedPreferencesManager.saveUpperLimit(context, upperLimitValue)
                        viewModel.saveUpperLimit(upperLimitValue)
                        hideSoftKeyboard(context)
                    }, modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = stringResource(R.string.set))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(text = "Lower limit selected: ${if (savedLowerLimit != 0) savedLowerLimit.toString() + " gwei" else "Select lower limit"}")
            Text(text = "Upper limit selected: ${if (savedUpperLimit != 0) savedUpperLimit.toString() + " gwei" else "Select upper limit"}")
        }
    }

}

fun hideSoftKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        (context as Activity).currentFocus?.windowToken, 0
    )
}