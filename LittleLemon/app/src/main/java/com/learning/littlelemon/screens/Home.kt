package com.learning.littlelemon.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.learning.littlelemon.R
import com.learning.littlelemon.TopAppBar
import com.learning.littlelemon.repository.MenuItemEntity
import com.learning.littlelemon.ui.theme.DefaultButtonColor
import com.learning.littlelemon.ui.theme.GreenMain
import com.learning.littlelemon.ui.theme.HighlightDark
import com.learning.littlelemon.ui.theme.HighlightLight
import com.learning.littlelemon.ui.theme.LittleLemonTheme
import com.learning.littlelemon.ui.theme.Typography
import com.learning.littlelemon.ui.theme.YellowMain
import com.learning.littlelemon.viewmodel.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(innerPadding: PaddingValues, navHostController: NavHostController) {
    LittleLemonTheme {
        val viewModel: MenuViewModel = koinViewModel()
        viewModel.getMenuItems()
        val menu by viewModel.menuItemsFlow.collectAsState()

        Column(modifier = Modifier.padding(innerPadding)) {
            TopAppBar()
            HeroBanner()
            HomeForm(navHostController, menu)
        }
    }
}

@Composable
fun HeroBanner() {
    LittleLemonTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = GreenMain)
        ) {
            Text(
                text = stringResource(R.string.home_title),
                color = YellowMain,
                style = Typography.headlineLarge,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = stringResource(R.string.home_city),
                color = HighlightLight,
                style = Typography.headlineMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.home_description),
                    color = HighlightLight,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = stringResource(id = R.string.onboarding_image_desc),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

        }
    }
}

@Composable
fun HomeForm(navHostController: NavHostController, menuItems: List<MenuItemEntity>) {
    LittleLemonTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_info),
                color = HighlightDark,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 30.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f),
                contentPadding = PaddingValues(0.dp)
            ) {
                menuItems.forEach { menuItem ->
                    item {
                        MenuItem(menuItem)
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 1.dp
                        )
                    }
                }
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = DefaultButtonColor,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    navHostController.navigate(com.learning.littlelemon.utils.Profile.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.home_profile_button),
                    color = HighlightDark,
                    style = Typography.bodyMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemEntity) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = menuItem.title,
                color = HighlightDark,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = menuItem.description,
                color = HighlightDark,
                style = Typography.bodyMedium,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "$${menuItem.price}",
                color = HighlightDark,
                style = Typography.bodyMedium,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        GlideImage(
            contentDescription = stringResource(id = R.string.onboarding_image_desc),
            contentScale = ContentScale.Crop,
            model = menuItem.image,
            modifier = Modifier
                .size(120.dp)
                .padding(10.dp)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight")
@Composable
fun HomeScreenLightModePreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Home(innerPadding, rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    val menuItem: MenuItemEntity = MenuItemEntity(
        id = 1,
        title = "Greek Salad",
        description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago...",
        price = "$12.99",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
    )
    MenuItem(menuItem)
}
