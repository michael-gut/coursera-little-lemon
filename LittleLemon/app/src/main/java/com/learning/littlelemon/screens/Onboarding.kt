package com.learning.littlelemon.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.learning.littlelemon.R
import com.learning.littlelemon.ui.theme.DefaultButtonColor
import com.learning.littlelemon.ui.theme.GreenMain
import com.learning.littlelemon.ui.theme.HighlightDark
import com.learning.littlelemon.ui.theme.HighlightLight
import com.learning.littlelemon.ui.theme.LittleLemonTheme
import com.learning.littlelemon.ui.theme.Typography
import com.learning.littlelemon.utils.PreferencesManager

@Composable
fun Onboarding(innerPadding: PaddingValues, navHostController: NavHostController) {
    LittleLemonTheme {
        Column(modifier = Modifier.padding(innerPadding)) {
            TopAppBar()
            TitleBar()
            OnboardingForm(navHostController)
        }
    }
}

@Composable
fun TitleBar() {
    LittleLemonTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = GreenMain)
        ) {
            Text(
                text = stringResource(R.string.onboarding_title),
                color = HighlightLight,
                style = Typography.titleLarge,
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun OnboardingForm(navHostController: NavHostController) {
    LittleLemonTheme {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        val preferencesManager = remember { PreferencesManager(context) }
        val sharedFirstname = remember { mutableStateOf(preferencesManager.getData("firstname", "")) }
        val sharedLastname = remember { mutableStateOf(preferencesManager.getData("lastname", "")) }
        val sharedEmail = remember { mutableStateOf(preferencesManager.getData("email", "")) }
        var firstname by remember { mutableStateOf(sharedFirstname.value) }
        var lastname by remember { mutableStateOf(sharedLastname.value) }
        var email by remember { mutableStateOf(sharedEmail.value) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.onboarding_info),
                    color = HighlightDark,
                    style = Typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 30.dp),
                )
                OutlinedTextField(
                    value = firstname,
                    singleLine = true,
                    onValueChange = { firstname = it },
                    label = {
                        Text(
                            text = "First name",
                            style = Typography.bodySmall,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
                OutlinedTextField(
                    value = lastname,
                    singleLine = true,
                    onValueChange = { lastname = it },
                    label = {
                        Text(
                            text = "Last name",
                            style = Typography.bodySmall
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
                OutlinedTextField(
                    value = email,
                    singleLine = true,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Email",
                            style = Typography.bodySmall
                        )
                    },
                    isError = email.isNotEmpty() && !isValidEmail(email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .focusRequester(focusRequester),
                colors = DefaultButtonColor,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (firstname.isBlank() || lastname.isBlank() || !isValidEmail(email)) {
                        Toast.makeText(context, "Registration unsuccessful. \nPlease enter all data.", Toast.LENGTH_SHORT).show()
                    } else {
                        preferencesManager.saveData("firstname", firstname)
                        preferencesManager.saveData("lastname", lastname)
                        preferencesManager.saveData("email", email)

                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(com.learning.littlelemon.utils.Home.route)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.onboarding_register_button),
                    color = HighlightDark,
                    style = Typography.bodyMedium,
                )
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return email.matches(
        Regex("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight")
@Composable
fun OnboardingLightModePreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Onboarding(innerPadding, rememberNavController())
    }
}
