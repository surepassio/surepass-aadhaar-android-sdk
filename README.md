# SurePass Aadhaar Android SDK
Surepass's identity verification SDK using Aadhaar for Android devices
## Table of contents
* [Overview](#overview)
* [Screenshots](#screenshots)
* [Getting started](#getting-started)
* [Adding the SDK dependency](#adding-the-sdk-dependency)
* [Creating the SDKController](#creating-the-sdkcontroller)
* [Instantiating the client](#instantiating-the-client)
* [Starting the flow](#starting-the-flow)
* [Handling callbacks](#handling-callbacks)
* [Customising SDK](#customising-sdk)
* [Going live](#going-live)
* [Getting notified about releases](#getting-notified-about-releases)
* [Support](#support)
## Overview
This SDK provides a drop-in set of screens and tools for Android applications to allow Verification of Aadhaar card via a no. of methods for the purpose of identity verification. The SDK offers a number of benefits to help you create the best onboarding/identity verification experience for your customers:
- Carefully designed UI to guide your customers through the entire Aadhaar verification process
- Modular design to help you seamlessly integrate the Aadhaar verification process into your application flow
- Advanced api to ensure the process of Aadhaar verification to be simple, guaranteeing the best success rate
- Direct get data to the integrating app/module, to simplify integration.

# Screenshots
![](https://i.imgur.com/adfBHoe.jpg)
## Getting started
The SDK supports API level 19 and above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).
Our configuration is currently set to the following:
- `minSdkVersion = 19`
- `compileSdkVersion = 28`
- `targetSdkVersion = 28`
- `Android Support Library = 28.1.0`
- `Kotlin = 1.3+`

### Adding the SDK dependency
You can integrate it like this:
In your project gradle file:
```gradle
repositories {
  maven {
            url "https://dl.bintray.com/surepassio/aadhaar-android-sdk"
        }
}
```
and , in your app level gradle file:
```
dependencies {
     implementation 'io.surepass.aadhaarandroidsdk:sdk:1.0.0'
}
```
and, inside the `android` block of your build.gradle, add these lines:
```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```
### Creating the SDKController
You must create a SurePass **SDKController** before you start the flow.
For creating a **SDKController**, the minimum requirement is to have the **API Token** .
Also, You've to pass your Organisation's Name inside the SDKController's Constructor
```Kotlin
val mySDKController : SDKController = SDKController(authorizationKey = "YOUR_API_TOKEN", companyName = "ORGANISATION's NAME")
```
just replace `YOUR_API_TOKEN` with your Token
and `ORGANISATION's NAME` with your organisation's name.

### Instantiating the client
To use the SDK, you can Directly call the Main UI by Passing the Activity **`SurePassActivity`** inside an Intent and passing the **`SDKController`** object as Extra with the name: `"sdkController"`
```kotlin
val intent = Intent(this, SurePassActivity::class.java)
intent.putExtra("sdkController", mySDKController)
```
### Starting the flow
If you want to modify the flow, you can pass the activity name(the one which you want to open) as an Extra to the intent:
```kotlin
intent.putExtra("activityName", AppConstants.AADHAAR_HOME_ACTIVITY)     //keep the tags for extras same as shown, these are important for the flow
```

Intent should be triggered with **startActivityForResult(intent, REQUEST_DATA)**, where ** REQUEST_DATA** is the pre-defined Request Code.
```kotlin
// start the flow. REQUEST_DATA should be your request code (customise as needed)
startActivityForResult(this,         /*must be an activity*/
                             REQUEST_DATA);         /*this request code will be important for you on onActivityResult() to identity the callback*/
```
Congratulations! You have successfully started the flow. Carry on reading the next sections to learn how to:
- Handle callbacks
- Customise the SDK
- Create checks

## Handling callbacks

To receive the result from the flow, you should override the method `onActivityResult`. In this method, the result is received in the variable named `data`. Now, firstly you have to create an Object of the class `ApiResult` inside your calling Activity :
```kotlin
private lateinit var apiResult: APIModel.APIResult<APIModel.User>
```

then, you can receive the data and save it to the `ApiResult` object inside your `onActivityResult` method:

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_DATA -> if (resultCode==1){
                if (data != null) {
                    apiResult = data.extras.getSerializable("result") as APIModel.APIResult<APIModel.User>
                }
            }
        }
    }
```


When the user has successfully completed the flow, thhe `ApiResult` object contains information about the User.
With the `ApiResult` object, you can get any infromation about the user that the **Aadhaar** provides. Here's the list of all the information you can get from the `ApiResult` object:


`apiResult.data.full_name` : Full name  
`apiResult.data.dob` : Date of Birth  
`apiResult.data.gender` : Gender  
`apiResult.data.address.full_address` : Full Address  
`apiResult.data.address.house` : House No.  
`apiResult.data.address.street` : Street  
`apiResult.data.address.po` : Post Office  
`apiResult.data.address.subdist` : Sub-District  
`apiResult.data.address.dist` : District  
`apiResult.data.address.state` : State  
`apiResult.data.address.country` : Country  
`apiResult.data.profile_image`  : Profile image URL



## Customising SDK

### 1. Flow customisation

You can customize the flow of the SDK by calling different activities instead of the `AadhaarHomeActivity`.
You can pass the respective Activities as an extra inside the launcher intent while [starting the flow](#starting-the-flow) for different tasks and flows:

* E-Aadhaar (open a webView and let the user do the verification manually): `AppConstants.WEB_VIEW_ACTIVITY`
* E-Aadhaar(let the User upload the Pdf version of the Aadhaar): `AppConstants.VERIFICATION_FROM_FILE_ACTIVITY`
* E-Aadhaar(present the user with a form to let the app do the verification automatically): `AppConstants.DO_IT_FOR_ME_ACTIVITY`
* XML Aadhar(open a webView and let the user do the verification manually): `AppConstants.WEB_VIEW_ACTIVITY_FOR_XML`
* XML Aadhaar(let the User upload the zip file of the Aadhaar XML): `AppConstants.OFFLINE_XML_ACTIVITY`
* XML Aadhaar(present the user with a form to let the app do the verification automatically): `AppConstants.DO_IT_FOR_ME_ACTIVITY`

Each of the above Activities return the `APIResult` class' object which you can receive in the same way as described [above](#handling-callbacks)




### 2. Theme customisation

In order to enhance the user experience on the transition between your application and the SDK, you can provide some customisation by defining certain colors inside your own `colors.xml` file:

`ColorPrimary`: Defines the background color of the `Toolbar` which guides the user through the flow

`ColorPrimaryDark`: Defines the color of the status bar above the `Toolbar`

`TextColorPrimary`: Defines the color of the title on the `Toolbar`

`headerTextColor`: Defines the color of the subtitle on the `Toolbar`

`buttonTextColor`: Defines the color of the text inside the primary action buttons

## Going live

Once you are happy with your integration and are ready to go live, please contact [techsupport@surepass.io](mailto:techsupport@surepass.io) to obtain live version of the mobile SDK token. We will have to replace the sandbox tokens in your code with the live tokens.


## Getting notified about releases

In case you want to get notified about our releases, feel free to access our [Bintray page](https://bintray.com/beta/#/surepassio/aadhaar-android-sdk/io.surepass.aadhaarandroidsdk?tab=overview) and click the `Watch` button.


### Support

Please open an issue through [GitHub](https://github.com/surepassio/surepass-aadhaar-android-sdk/issues). Please be as detailed as you can. Remember **not** to submit your token in the issue. Also check the closed issues to check whether it has been previously raised and answered.

If you have any issues that contain sensitive information please send us an email with the ISSUE: at the start of the subject to [techsupport@surepass.io](mailto:techsupport@surepass.io).
