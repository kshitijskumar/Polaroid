package com.example.polaroid.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ResourceFetcherImpl : ResourceFetcher {

    override suspend fun fetchResource(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val bitmapResult = kotlin.runCatching {
                    val urlObject = URL(url)

                    val connection = urlObject.openConnection() as HttpURLConnection
                    val bitmap = BitmapFactory.decodeStream(connection.inputStream)
                    connection.disconnect()
                    bitmap
                }
                bitmapResult.getOrThrow()
            } catch (e: MalformedURLException) {
                Log.d("Polaroid", "malformed url: $e")
                null
            } catch (e: IOException) {
                Log.d("Polaroid", "issue opening connection to server: $e")
                null
            } catch (e: Exception) {
                Log.d("Polaroid", "something went wrong: $e")
                null
            }
        }
    }
}