package com.learning.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.learning.littlelemon.repository.MenuRepository
import com.learning.littlelemon.repository.convertToMenuItemEntity
import com.learning.littlelemon.ui.theme.LittleLemonTheme
import com.learning.littlelemon.utils.MenuNetwork
import com.learning.littlelemon.utils.NavigationComposable
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val menuRepository : MenuRepository by inject()
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private suspend fun fetchMenu(): MenuNetwork {
        var todayMenu = MenuNetwork(emptyList())

        try {
            withContext(IO) {
                val serverUrl = resources.getString(R.string.server_url)
                val response: HttpResponse = client.get(serverUrl)
                val rawResponse = response.bodyAsText()
                todayMenu = Json.decodeFromString(MenuNetwork.serializer(), rawResponse)
                if (todayMenu.menu.isNotEmpty()) {
                    println("Delete old menu...")
                    menuRepository.deleteAll()
                }
                todayMenu.menu.forEach {
                    menuRepository.insertMenuItem(item = convertToMenuItemEntity(it))
                }
            }
        } catch (ex: Exception) {
            println("Error fetching menu - ${ex.message}")
        }
        return todayMenu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val menus = fetchMenu()
            println("Menu = $menus")
            setContent {
                LittleLemonContent()
            }
        }
    }
}

@Composable
fun LittleLemonContent() {
    LittleLemonTheme {
        val navController = rememberNavController()
        NavigationComposable(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun LittleLemonPreview() {
    LittleLemonContent()
}
