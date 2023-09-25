package ru.ok.itmo.hw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract

class ResultContract : ActivityResultContract<DataInfo, DataInfo>() {

    companion object {
        const val KEY = "key"
        const val RESULT_KEY = "result_key"
    }

    override fun createIntent(context: Context, input: DataInfo): Intent = Intent(
        context,
        RequestCameraPermissionActivity::class.java,
    ).apply {
        putExtra(KEY, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): DataInfo {
        val dataInfo: DataInfo? = if (resultCode == Activity.RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(RESULT_KEY, DataInfo::class.java)
            } else {
                intent?.getParcelableExtra(RESULT_KEY)
            }
        } else null
        return dataInfo ?: throw IllegalStateException("")
    }
}