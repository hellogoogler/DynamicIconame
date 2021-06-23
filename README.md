# DynamicIconame
 动态更换桌面APP图标和名称

# activity-alias
Android里为了重复使用Activity而设计的。

1. 含义和作用:

         对于activity-alias标签，它有一个属性叫android:targentActivity，这个属性就是用来为该标签设置目标Activity的，或者说它就是这个目标Activity的别名。
         activity-alias作为一个已存在Activity的别名，则应该可以通过该别名标签声明快速打开目标Activity。
         因此activity-alias可用来设置某个Activity的快捷入口，可以放在桌面上或者通过该别名被其他组件快速调起。

2. 部分属性说明如下

    1. android:enable 该属性用来决定目标Activity可否通过别名被系统实例化，默认为true。
                  需要注意的是application也有enable属性，只用当它们同时为true时，activity-alias的enable才生效。

    2. android:exported 该属性为true的话，则目标Activity可被其他应用调起，如为false则只能被应用自身调起。
                   其默认值根据activity-alias是否包含intent-filter元素决定，如果有的话，则默认为true；没有的话则为false。
                   其实也很好理解，如果有intent-filter，则目标Activity可以匹配隐式Intent，因此可被外部应用唤起；
                   如果没有intent-filter，则目标Activity要被调起的话必须知道其精确类名，因为只有应用本身才知道精确类名，所以此时默认为false。

    3. android:icon 该属性就比较好玩了，允许自定义icon，可以不同于应用本身在桌面的icon。如果需要在桌面上创建快捷入口，也许产品会要求换个不同的icon。

    4. android:label 该属性类似于android:icon，图标都换了，换个名称也合情合理吧，此属性就是为此而生的。

    5. android:name 该属性可以为任意字符串，但最好符合类名命名规范。activity元素的name属性实质上都会指向一个具体的Activity类，而activity-alias的name属性仅作为一个唯一标识而已。

    6. android:permission 该属性指明了通过别名声明调起目标Activity所必需的权限。

    7. android:targetActivity 该属性指定了目标Activity，即通过activity-alias调起的Activity是哪个，此属性其实类似于activity标签中的name属性，需要规范的Activity包名类名。


3. 注意:
    在AndroidManifest配置文件中，activity-alias标签元素必须声明在目标Acitvity对应的activity标签元素之后，否则会编译错误。

4. 使用案例:
    当在Activity的onCreate()方法里，执行getIntent().getComponent().getClassName();
    得到的可能不是这个Activity的名字，有可能是别名的名字，例如：在AndroidMenifest.xml有如下配置：

        <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity-alias
            android:name="com.dynamic.iconame.AliasActivity"
            android:enabled="false"
            android:icon="@mipmap/icon_alias"
            android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize|screenLayout"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme"
            android:windowSoftInputMode="stateAlwaysHidden|adjustPan"
            android:label="别名"
            android:targetActivity=".MainActivity">
            <meta-data
                android:name="android.notch_support"
                android:value="true" />
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
                <category android:name="android.intent.category.MULTIWINDOW_LAUNCHER" />
                <category android:name="miui.intent.category.SYSAPP_RECOMMEND" />
            </intent-filter>
        </activity-alias>

        <activity-alias
            android:name="com.dynamic.iconame.Alias2Activity"
            android:enabled="false"
            android:icon="@mipmap/icon_alias2"
            android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize|screenLayout"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme"
            android:label="别名2"
            android:windowSoftInputMode="stateAlwaysHidden|adjustPan"
            android:targetActivity=".MainActivity">
            <meta-data
                android:name="android.notch_support"
                android:value="true" />
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
                <category android:name="android.intent.category.MULTIWINDOW_LAUNCHER" />
                <category android:name="miui.intent.category.SYSAPP_RECOMMEND" />
            </intent-filter>
        </activity-alias>
    </application>
   
总结：
1.更换桌面图标不是马上生效，需要一段时间才生效（10S内）
2.当app不是默认图标时，再run 'app'，就会报错 'Session "app":Error Launching activity'
 Error while executing: am start -n "com.dynamic.iconame/com.dynamic.iconame.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
 Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.dynamic.iconame/.MainActivity }
 Error type 3
 Error: Activity class {com.dynamic.iconame/com.dynamic.iconame.MainActivity} does not exist.
 Error while Launching activity
 需要卸载当前APP，然后重新编译运行即可。