package com.molten.androidgrpcdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.grpc.okhttp.OkHttpChannelBuilder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Change to your local server's IP
        val url = "localhost"
        val emptyRequest: Empty = Empty.newBuilder().build()
        val okHttpChannel = OkHttpChannelBuilder.forAddress(url, 8080)
                .usePlaintext()
                .build()
        val personStub = PersonServiceGrpc.newBlockingStub(okHttpChannel)
        doAsync {
            val person = personStub.getPerson(emptyRequest)
            uiThread { println(person) }
        }
    }
}
