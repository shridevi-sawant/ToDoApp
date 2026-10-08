package com.pes.todoapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread

class BackupService : Service() {

    companion object {
        const val ACTION_BACKUP_DONE = "com.pes.todoapp.action.backup_done"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // executed only once in the lifecycle of service
    override fun onCreate() {
        super.onCreate()
        Log.d("BackupService", "onCreate called")
        Toast.makeText(this, "onCreate called",
            Toast.LENGTH_LONG).show()
    }

    // executed multiple times as many times startService() called
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BackupService", "onStartCommand called")
        Toast.makeText(this, "onStartCommand called",
            Toast.LENGTH_LONG).show()

        // perform long running task - new thread
        thread {
            // executes on newly created thread
            // do the long running task here...
            Log.d("BackupService", "Task started")
            Thread.sleep(5000) // simulates task takes 5 sec
            Log.d("BackupService", "Task completed")

            // send broadcast for event - backup completed..
            val broadcastIntent = Intent(ACTION_BACKUP_DONE)
            broadcastIntent.setPackage("com.pes.todoapp")

            sendBroadcast(broadcastIntent)
            Log.d("BackupService", "Broadcast sent")

            // stop service as task got over
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("BackupService", "onDestroy called")
        Toast.makeText(this, "onDestroy called",
            Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
}