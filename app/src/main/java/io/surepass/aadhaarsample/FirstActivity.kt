package io.surepass.aadhaarsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.surepass.aadhaarandroidsdk.Activities.OfflineXMLActivity
import io.surepass.aadhaarandroidsdk.Activities.QRScanActivity
import io.surepass.aadhaarandroidsdk.Activities.SurePassActivity
import io.surepass.aadhaarandroidsdk.Activities.VerificationFromFileActivity
import io.surepass.aadhaarandroidsdk.models.APIModel
import io.surepass.aadhaarandroidsdk.models.AppConstants
import io.surepass.aadhaarandroidsdk.models.SDKController
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    private var REQUEST_DATA = 21
    private lateinit var sdkController: SDKController
    private lateinit var apiResult: APIModel.APIResult<APIModel.User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        sdkController = SDKController(authorizationKey = "[YOUR API TOKEN]", companyName = "XYZ Pvt. Ltd.")
        sdkController.environment = AppConstants.ENVIRONMENT_SANDBOX
        setonclickListeners()
    }
    private fun setonclickListeners() {
        button.setOnClickListener {
            val intent = Intent(this, SurePassActivity::class.java)
            intent.putExtra("activityName", AppConstants.AADHAAR_HOME_ACTIVITY)
            intent.putExtra("sdkController", sdkController)
            startActivityForResult(intent, REQUEST_DATA)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_DATA -> if (resultCode==1){
                if (data != null) {
                    apiResult = data.extras.getSerializable("result") as APIModel.APIResult<APIModel.User>
                    val launcherIntent = Intent(this, MainActivity::class.java)
                    launcherIntent.putExtra("result", apiResult)
                    startActivity(launcherIntent)
                }
            }
        }
    }
}
