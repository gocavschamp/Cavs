package com.example.myapp.homepage.homedemo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapp.R
import com.nucarf.base.utils.LogUtils
//import io.flutter.embedding.android.FlutterFragment
//import io.flutter.view.FlutterView
import kotlinx.android.synthetic.main.activity_flutter_test.*


class FlutterTestActivity : FragmentActivity() {
    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
//    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_flutter_test)
        val fragmentManager: FragmentManager = supportFragmentManager
//        flutterFragment = fragmentManager
//                .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
//        if (flutterFragment == null) {
//            var flutterFragment = FlutterFragment.createDefault()
//            fragmentManager
//                    .beginTransaction()
//                    .add(
//                            R.id.rl_content,
//                            flutterFragment,
//                            TAG_FLUTTER_FRAGMENT
//                    )
//                    .commit()
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        flutterFragment?.onRequestPermissionsResult(
//                requestCode,
//                permissions,
//                grantResults
//        );
    }
    override fun onPostResume() {
        super.onPostResume()
//        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
//        flutterFragment?.onNewIntent(intent)

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
//        flutterFragment?.onTrimMemory(level)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
//        flutterFragment?.onUserLeaveHint()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        flutterFragment?.onBackPressed()
    }
    override fun onResume() {
        super.onResume()
//        flutterFragment?.onResume()
    }

    override fun onPause() {
        super.onPause()
//        flutterFragment?.onPause()

    }

    override fun onStart() {
        super.onStart()
//        flutterFragment?.onStart()
    }
    override fun onStop() {
        super.onStop()
//        flutterFragment?.onStop()

    }
}