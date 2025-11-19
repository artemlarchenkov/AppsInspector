package com.example.appinspector.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.AppInspector.Adapter.AppsAdapter
import com.example.AppInspector.Model.InstalledApp
import com.example.appinspector.R

class AppListActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AppsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)

        recycler = findViewById(R.id.recyclerApps)
        recycler.layoutManager = LinearLayoutManager(this)

        val apps = loadInstalledApps()
        adapter = AppsAdapter(apps) { app ->
            val intent = Intent(this, AppDetailsActivity::class.java)
            intent.putExtra("packageName", app.packageName)
            startActivity(intent)
        }

        recycler.adapter = adapter
    }
    private fun loadInstalledApps(): List<InstalledApp> {
        val pm = packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        return apps.mapNotNull { appInfo ->
            try {
                InstalledApp(
                    appName = pm.getApplicationLabel(appInfo).toString(),
                    packageName = appInfo.packageName,
                    versionName = pm.getPackageInfo(appInfo.packageName, 0).versionName ?: "N/A",
                    icon = pm.getApplicationIcon(appInfo.packageName),
                    apkPath = appInfo.sourceDir ?: ""
                )
            } catch (e: Exception) {
                null
            }
        }.sortedBy { it.appName.lowercase() }
    }
}
