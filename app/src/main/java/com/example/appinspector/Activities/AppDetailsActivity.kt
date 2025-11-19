package com.example.appinspector.Activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appinspector.R

class AppDetailsActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var txtPkg: TextView
    private lateinit var txtVersion: TextView
    private lateinit var txtChecksum: TextView
    private lateinit var btnOpen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)

        txtName = findViewById(R.id.txtName)
        txtPkg = findViewById(R.id.txtPackage)
        txtVersion = findViewById(R.id.txtVersion)
        txtChecksum = findViewById(R.id.txtChecksum)
        btnOpen = findViewById(R.id.btnOpen)

        val packageName = intent.getStringExtra("packageName") ?: return

        showAppInfo(packageName)
    }

    private fun showAppInfo(pkg: String) {
        try {
            val pm = packageManager
            val info = pm.getPackageInfo(pkg, 0)

            val name = info.applicationInfo?.loadLabel(pm).toString()
            val version = info.versionName ?: "N/A"
            val apkPath = info.applicationInfo?.sourceDir

            txtName.text = Html.fromHtml("<b>Имя:</b> $name")
            txtPkg.text = Html.fromHtml("<b>Пакет:</b> $pkg")
            txtVersion.text = Html.fromHtml("<b>Версия:</b> $version")

            val checksum = ApkChecksum.calculateSHA1(apkPath)
            txtChecksum.text = Html.fromHtml("<b>Checksum:</b> $checksum")

            btnOpen.setOnClickListener {
                val launchIntent = pm.getLaunchIntentForPackage(pkg)
                if (launchIntent != null) {
                    startActivity(launchIntent)
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            finish() // Закрываем активность, если пакет не найден
        }
    }
}