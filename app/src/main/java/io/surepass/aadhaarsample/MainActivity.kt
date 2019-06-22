package io.surepass.aadhaarsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import io.surepass.aadhaarandroidsdk.models.APIModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var apiResult:APIModel.APIResult<APIModel.User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.hasExtra("result")){
            apiResult = intent.getSerializableExtra("result") as APIModel.APIResult<APIModel.User>
            initiateViews()
        }
        else{
            Toast.makeText(this@MainActivity, "No Result Found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initiateViews() {
        nameTextView.text = apiResult.data.full_name
        dobTextView.text = apiResult.data.dob
        if (apiResult.data.gender.equals("M")){
            genderTextView.text = "Male"
        }
        else if (apiResult.data.gender.equals("F")){
            genderTextView.text = "Female"
        }
        houseText.text = apiResult.data.address.house
        locTextView.text = apiResult.data.address.loc
        poTextView.text = apiResult.data.address.po
        districtTextView.text = apiResult.data.address.dist
        stateTextView.text = apiResult.data.address.state
        countryTextView.text = apiResult.data.address.country
        if (apiResult.data.has_image){
            Glide.with(this).load(apiResult.data.profile_image).into(profile_image)
        }
        zipcodeTextView.text= apiResult.data.zip


    }
}
