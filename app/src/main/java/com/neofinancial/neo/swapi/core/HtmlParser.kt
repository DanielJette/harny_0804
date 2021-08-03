package com.neofinancial.neo.swapi.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.htmlparser.jericho.Element
import net.htmlparser.jericho.Source
import okhttp3.OkHttpClient
import okhttp3.Request

@Suppress("BlockingMethodInNonBlockingContext")
class HtmlParser {

    private var body: String? = null

    fun get(url: String): HtmlParser {
        runBlocking {
            val job = GlobalScope.launch {
                internalGet(url)
            }
            job.join()
        }
        return this
    }

    private suspend fun internalGet(url: String) = withContext(Dispatchers.IO) {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            body = if (response.isSuccessful) response.body()?.string() else "<html/>"
        }
    }

    fun getMetaProperties(): Map<String, String> {
        val source = Source(body)
        val elements: List<Element> = source.getAllElements("meta")
        return elements
            .filterNot { it.getAttributeValue("property") == null }
            .associate {
                val key = it.getAttributeValue("property")
                val value = it.getAttributeValue("content")
                key to value
            }
    }
}
