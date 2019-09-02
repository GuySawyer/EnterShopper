package guy.sawyer.com.entershopper.ui.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import guy.sawyer.com.entershopper.R

class AnimationProgressDialog: DialogFragment() {

    fun newInstance(): AnimationProgressDialog {
        return AnimationProgressDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.ProgressIndicatorStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_indicator_activity, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val dialogWindow = dialog.window
        dialogWindow?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}