package com.islavstan.firebasekotlinchat

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric


class FirebaseChatMainApp : Application() {


    companion object {
        private var sIsChatActivityOpen = false
        fun isChatActivityOpen():Boolean {
            return sIsChatActivityOpen
        }
        fun setChatActivityOpen(isChatActivityOpen: Boolean){
            sIsChatActivityOpen = isChatActivityOpen
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        // Normal app init code...
        Fabric.with(this, Crashlytics())
    }
}