package com.gmail.pashkovich.al.broadcast

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread

class MyService: Service() {

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 0 .. 10) {
                Thread.sleep(1000)
                Intent("loaded").apply {
                    putExtra("percent", i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}