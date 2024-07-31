package com.learning.littlelemon

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.learning.littlelemon.utils.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.learning.littlelemon", appContext.packageName)
    }

    @Test
    fun testFetchMenu() {
        runBlocking {
            val menuResponse = fetchMenu()
            menuResponse.menu.forEach { item ->
                println("Title: ${item.title}, Description: ${item.description}, Price: ${item.price}")
            }
        }
    }

    private suspend fun fetchMenu(): MenuNetwork {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        try {
            val response: HttpResponse = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            val rawResponse = response.bodyAsText()
            return Json.decodeFromString(MenuNetwork.serializer(), rawResponse)
        } finally {
            client.close()
        }
    }
}
