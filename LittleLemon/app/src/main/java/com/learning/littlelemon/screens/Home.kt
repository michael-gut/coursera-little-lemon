package com.learning.littlelemon.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.learning.littlelemon.R
import com.learning.littlelemon.repository.MenuItemEntity
import com.learning.littlelemon.ui.theme.DefaultButtonColor
import com.learning.littlelemon.ui.theme.GreenMain
import com.learning.littlelemon.ui.theme.HighlightDark
import com.learning.littlelemon.ui.theme.HighlightLight
import com.learning.littlelemon.ui.theme.LightButtonColor
import com.learning.littlelemon.ui.theme.LittleLemonTheme
import com.learning.littlelemon.ui.theme.Typography
import com.learning.littlelemon.ui.theme.YellowMain
import com.learning.littlelemon.viewmodel.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(innerPadding: PaddingValues, navHostController: NavHostController) {
    LittleLemonTheme {
        val viewModel: MenuViewModel = koinViewModel()
        viewModel.getMenuCategories()
        viewModel.getMenuItems("")

        Column(modifier = Modifier.padding(innerPadding)) {
            TopAppBar()
            HeroBanner()
            HomeForm(navHostController)
        }
    }
}

@Composable
fun HeroBanner() {
    var searchPhrase by remember { mutableStateOf("") }
    val viewModel: MenuViewModel = koinViewModel()

    LittleLemonTheme {
        val focusManager = LocalFocusManager.current

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
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                value = searchPhrase,
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.home_search),
                        color = HighlightDark,
                        style = Typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                onValueChange = {
                    searchPhrase = it
                    viewModel.setSearchPhrase(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
            )
        }
    }
}

@Composable
fun HomeForm(navHostController: NavHostController) {
    val viewModel: MenuViewModel = koinViewModel()
    val menuItems by viewModel.menuItemsFlow.collectAsState()
    val categories by viewModel.menuCategoryFlow.collectAsState()

    LittleLemonTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.home_info),
                color = HighlightDark,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                categories.forEach { category ->
                    item {
                        CategoryButton(
                            category = category.capitalize(),
                            action = {
                                viewModel.getMenuItemsForCategory(category)
                            }
                        )
                    }
                }
                item {
                    CategoryButton(
                        category = "All",
                        action = {
                            viewModel.getMenuItems("")
                        }
                    )
                }
            }
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
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
                item {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
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

@Composable
fun CategoryButton(category: String, action: () -> Unit) {
    TextButton(
        modifier = Modifier
            .height(40.dp)
            .padding(end = 20.dp),
        colors = LightButtonColor,
        shape = RoundedCornerShape(15.dp),
        onClick = {
            action()
        }
    ) {
        Text(
            text = category,
            color = HighlightDark,
            style = Typography.bodySmall,
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
        price = "12",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
    )
    MenuItem(menuItem)
}
