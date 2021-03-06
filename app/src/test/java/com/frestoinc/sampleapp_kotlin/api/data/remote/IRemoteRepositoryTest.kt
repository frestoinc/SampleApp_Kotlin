package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.domain.extension.baseURL
import com.frestoinc.sampleapp_kotlin.api.domain.extension.retrofitField
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
class IRemoteRepositoryTest {

    @Test
    @Throws(IOException::class)
    fun testApi() {
        val conn = URL(baseURL + retrofitField).openConnection()
        val inputStream = conn.getInputStream()
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8) as Reader)
            .use { reader ->
                var s: String?
                while (reader.readLine().also { s = it } != null) {
                    sb.append(s)
                }
            }
        assert(sb.isNotEmpty())
    }
}