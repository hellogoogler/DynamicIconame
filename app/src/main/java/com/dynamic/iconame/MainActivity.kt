package com.dynamic.iconame

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val ICONAME_DEFAULT = 0
    private val ICONAME_ALIAS = 1
    private val ICONAME_ALIAS2 = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv_default).setOnClickListener { iconame(ICONAME_DEFAULT) }
        findViewById<View>(R.id.tv_alias).setOnClickListener { iconame(ICONAME_ALIAS) }
        findViewById<View>(R.id.tv_alias2).setOnClickListener { iconame(ICONAME_ALIAS2) }
    }

    private fun showIconame(context: Context, num: Int): Boolean {
        try {
            if (Build.VERSION.SDK.toInt() >= 29) return false
            val defaultName = ComponentName(context, "com.dynamic.iconame.MainActivity")
            val alias = ComponentName(context, "com.dynamic.iconame.AliasActivity")
            val alias2 = ComponentName(context, "com.dynamic.iconame.Alias2Activity")
            val pm = context.packageManager
            if (num == ICONAME_DEFAULT) {
                if (enableComponent(pm, defaultName)) {
                    disableComponent(pm, alias)
                    disableComponent(pm, alias2)
                } else return false
            } else if (num == ICONAME_ALIAS) {
                if (enableComponent(pm, alias)) {
                    disableComponent(pm, defaultName)
                    disableComponent(pm, alias2)
                } else return false
            } else if (num == ICONAME_ALIAS2) {
                if (enableComponent(pm, alias2)) {
                    disableComponent(pm, defaultName)
                    disableComponent(pm, alias)
                } else return false
            } else {
                if (enableComponent(pm, defaultName)) {
                    disableComponent(pm, alias)
                    disableComponent(pm, alias2)
                } else return false
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun enableComponent(pm: PackageManager, componentName: ComponentName?): Boolean {
        return try {
            if (componentName == null) {
                return false
            }
            if (pm.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                return false
            }
            pm.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }
    }

    private fun disableComponent(pm: PackageManager, componentName: ComponentName?): Boolean {
        return try {
            if (componentName == null) {
                return false
            }
            if (pm.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                return false
            }
            pm.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }
    }
    private fun iconame(iconId: Int): Boolean {
        Toast.makeText(this, "10秒内手机桌面的APP图标和名称将发生变化!", Toast.LENGTH_LONG).show()
        val ret: Boolean
        ret = iconId >= 0 && showIconame(this, iconId)
        return ret
    }
}