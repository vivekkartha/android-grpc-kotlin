package com.molten.androidgrpcdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import tutorial.Dataformat

class MainActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "http://elyeproject.x10host.com/experiment/protobuf"
        getPerson(url)
    }

    private fun getPerson(url: String) {
        Single.create<Dataformat.Person> { personApi(url, it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { t1, _ -> println(t1) }
    }

    private fun personApi(url: String, it: SingleEmitter<Dataformat.Person>) {
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        it.onSuccess(Dataformat.Person.parseFrom(response.body()!!.byteStream()))
    }
}
