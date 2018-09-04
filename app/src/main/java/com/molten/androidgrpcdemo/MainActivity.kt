package com.molten.androidgrpcdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "http://elyeproject.x10host.com/experiment/protobuf"
        doAsync {
            val person = getPerson(url)
            println(person)
        }
    }

    private fun getPerson(url: String): Person {
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        return Person.parseFrom(response.body()!!.byteStream())
    }
}
