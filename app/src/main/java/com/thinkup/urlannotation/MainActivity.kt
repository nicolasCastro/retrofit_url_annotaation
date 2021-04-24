package com.thinkup.urlannotation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thinkup.services.ServiceOne
import com.thinkup.services.ServiceTwo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create different services
        ServiceFactory().createInstance(ServiceOne::class.java)
        ServiceFactory().createInstance(ServiceTwo::class.java)
    }
}