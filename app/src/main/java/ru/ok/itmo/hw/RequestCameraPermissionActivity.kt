package ru.ok.itmo.hw

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.hw.R

class RequestCameraPermissionActivity : AppCompatActivity(R.layout.activity_request_camera_permission) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val dataInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(ResultContract.KEY, DataInfo::class.java)
        } else {
            intent?.getParcelableExtra(ResultContract.KEY)
        }
        findViewById<TextView>(R.id.tv).text = dataInfo?.name ?: "data info is null"

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(ResultContract.RESULT_KEY, DataInfo("request granted = $it"))
            })
            finish()
        }
        launcher.launch(Manifest.permission.CAMERA)
    }
}