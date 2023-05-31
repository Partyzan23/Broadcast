package com.gmail.pashkovich.al.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
//    private val receiver = MyReceiver()

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }
    private val receiver = object : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "loaded") {
            val percent = intent.getIntExtra("percent", 0)
            progressBar.setProgress(percent, true)
        }
    }
}
    private var count = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            Intent(MyReceiver.ACTION_CLICKED).apply {
                putExtra(MyReceiver.EXTRA_COUNT_CLICK, count++)
                localBroadcastManager.sendBroadcast(this)
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(MyReceiver.ACTION_CLICKED)
            addAction("loaded")
        }
        localBroadcastManager.registerReceiver(receiver, intentFilter)
        progressBar = findViewById(R.id.progressBar)
        startService(MyService.newIntent(this))
    }

    override fun onDestroy() {
        localBroadcastManager.unregisterReceiver(receiver)
        super.onDestroy()
    }

}