package guy.sawyer.com.entershopper.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import guy.sawyer.com.entershopper.ui.utils.AnimationProgressDialog

open class BaseActivity : AppCompatActivity() {

    private val progressLock = Any()
    private var animationProgressDialog: AnimationProgressDialog? = null
    private val FRAGMENT_COMMIT_TAG = "ProgressIndicatorDialogFragment"

    fun showProgressDialog() {
        synchronized(progressLock) {
            try {
                if (animationProgressDialog == null) {
                    animationProgressDialog = AnimationProgressDialog().newInstance()
                    animationProgressDialog?.isCancelable = false
                }
                animationProgressDialog?.show(supportFragmentManager, FRAGMENT_COMMIT_TAG)
            } catch (e: NullPointerException) {
                Log.d("Error", e.message)
            } catch (e: IllegalStateException) {
                Log.d("Error", e.message)
            }
        }
    }

    fun dismissProgressDialog() {
        synchronized(progressLock) {
            try {
                val progressIndicator =
                    supportFragmentManager.findFragmentByTag(FRAGMENT_COMMIT_TAG)
                if (progressIndicator != null) {
                    val animationProgressDialog =
                        progressIndicator as AnimationProgressDialog?
                    animationProgressDialog!!.dismiss()
                }
                if (animationProgressDialog != null) {
                    animationProgressDialog?.dismiss()
                    animationProgressDialog = null
                } else {
                    Log.d("x-e", "dismiss lottie is null")
                }
            } catch (e: NullPointerException) {
                Log.d("Error", e.message)
            } catch (e: IllegalStateException) {
                Log.d("Error", e.message)
            }

        }
    }
}