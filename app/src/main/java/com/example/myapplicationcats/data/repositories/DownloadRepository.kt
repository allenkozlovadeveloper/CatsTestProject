package com.example.myapplicationcats.data.repositories

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.myapplicationcats.utils.md5
import com.example.myapplicationcats.domain.models.CatModel

class DownloadRepository (
    private val context: Context
) {

    private val manager: DownloadManager by lazy { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() : Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStatus == -1) return false
        return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED
    }

    fun saveCat(catModel: CatModel) {
        val url = catModel.url
        val request = DownloadManager.Request(Uri.parse(url))
        val fileFormat = url.substring(url.lastIndexOf("."))

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(url.md5())
        request.setDescription("the file...")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            url.md5() + fileFormat
        )
        manager.enqueue(request)
    }
}