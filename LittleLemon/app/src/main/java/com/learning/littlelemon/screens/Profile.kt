package com.learning.littlelemon.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.learning.littlelemon.R
import com.learning.littlelemon.ui.theme.DefaultButtonColor
import com.learning.littlelemon.ui.theme.HighlightDark
import com.learning.littlelemon.ui.theme.LittleLemonTheme
import com.learning.littlelemon.ui.theme.Typography
import com.learning.littlelemon.utils.PreferencesManager

@Composable
fun ProfileForm(navHostController: NavHostController) {
    LittleLemonTheme {
        val context = LocalContext.current
        val preferencesManager = remember { PreferencesManager(context) }
        val sharedFirstname = remember { mutableStateOf(preferencesManager.getData("firstname", "")) }
        val sharedLastname = remember { mutableStateOf(preferencesManager.getData("lastname", "")) }
        val sharedEmail = remember { mutableStateOf(preferencesManager.getData("email", "")) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.profile_info),
                    color = HighlightDark,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 30.dp),
                )
                OutlinedTextField(
                    value = sharedFirstname.value,
                    onValueChange = { /* Do nothing */ },
                    readOnly = false,
                    enabled = false,
                    label = {
                        Text(
                            text = "First name",
                            style = Typography.bodySmall,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                )
                OutlinedTextField(
                    value = sharedLastname.value,
                    onValueChange = { /* Do nothing */ },
                    readOnly = false,
                    enabled = false,
                    label = {
                        Text(
                            text = "Last name",
                            style = Typography.bodySmall
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                )
                OutlinedTextField(
                    value = sharedEmail.value,
                    onValueChange = { /* Do nothing */ },
                    readOnly = false,
                    enabled = false,
                    label = {
                        Text(
                            text = "Email",
                            style = Typography.bodySmall
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                )
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = DefaultButtonColor,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    preferencesManager.saveData("firstname", "")
                    preferencesManager.saveData("lastname", "")
                    preferencesManager.saveData("email", "")
                    navHostController.navigate(com.learning.littlelemon.utils.Onboarding.route) {
                        popUpTo(0)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.profile_logout_button),
                    color = HighlightDark,
                    style = Typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun Profile(innerPadding: PaddingValues, navHostController: NavHostController) {
    LittleLemonTheme {
        Column(modifier = Modifier.padding(innerPadding)) {
            TopAppBar()
            ProfileForm(navHostController)
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight")
@Composable
fun ProfileScreenLightModePreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Profile(innerPadding, rememberNavController())
    }
}
