package dev.seabat.android.kotlinwebsocket.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import dev.seabat.android.kotlinwebsocket.ui.dialog.SimpleErrorDialogFragment.Companion.ARG_MESSAGE
import dev.seabat.android.kotlinwebsocket.ui.dialog.SimpleErrorDialogFragment.Companion.ARG_POSITIVE_BUTTON_TEXT
import dev.seabat.android.kotlinwebsocket.ui.dialog.SimpleErrorDialogFragment.Companion.ARG_REQUEST_BUNDLE
import dev.seabat.android.kotlinwebsocket.ui.dialog.SimpleErrorDialogFragment.Companion.ARG_REQUEST_KEY
import dev.seabat.android.kotlinwebsocket.ui.dialog.SimpleErrorDialogFragment.Companion.ARG_TITLE

fun Activity.showSimpleErrorDialog(
    title: String? = null,
    message: String,
    requestKey: String,
    requestBundle: Bundle,
    onClickCallback: (key: String, bundle: Bundle) -> Unit
) {
    val dialogFragment = SimpleErrorDialogFragment().apply {
        arguments = Bundle().apply {
            putString(ARG_TITLE, title)
            putString(ARG_MESSAGE, message)
            putString(ARG_POSITIVE_BUTTON_TEXT, "CLOSE")
            putString(ARG_REQUEST_KEY, requestKey)
            putBundle(ARG_REQUEST_BUNDLE, requestBundle)
        }
    }

    val fragmentTransaction = (this as AppCompatActivity).supportFragmentManager.beginTransaction()
    (this as AppCompatActivity).supportFragmentManager.setFragmentResultListener(
        requestKey,
        this
    ) { key, bundle ->
        onClickCallback(key, bundle)
    }
    dialogFragment.show(fragmentTransaction, SimpleErrorDialogFragment.TAG)
}

fun Fragment.showSimpleErrorDialog(
    title: String? = null,
    message: String,
    requestKey: String,
    requestBundle: Bundle,
    onClickCallback: (key: String, bundle: Bundle) -> Unit
) {
    val dialogFragment = SimpleErrorDialogFragment().apply {
        arguments = Bundle().apply {
            putString(ARG_TITLE, title)
            putString(ARG_MESSAGE, message)
            putString(ARG_POSITIVE_BUTTON_TEXT, "CLOSE")
            putString(ARG_REQUEST_KEY, requestKey)
            putBundle(ARG_REQUEST_BUNDLE, requestBundle)
        }
    }

    val fragmentTransaction = childFragmentManager.beginTransaction()
    childFragmentManager.setFragmentResultListener(requestKey, this) { key, bundle ->
        onClickCallback(key, bundle)
    }
    dialogFragment.show(fragmentTransaction, SimpleErrorDialogFragment.TAG)
}

class SimpleErrorDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = SimpleErrorDialogFragment::class.java.simpleName
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_MESSAGE = "ARG_MESSAGE"
        const val ARG_POSITIVE_BUTTON_TEXT = "ARG_POSITIVE_BUTTON_TEXT"
        const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
        const val ARG_REQUEST_BUNDLE = "ARG_REQUEST_BUNDLE"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(ARG_TITLE)
        val message = arguments?.getString(ARG_MESSAGE)
        val positiveButtonText = arguments?.getString(ARG_POSITIVE_BUTTON_TEXT)
        val requestKey = arguments?.getString(ARG_REQUEST_KEY)
        var requestBundle = arguments?.getBundle(ARG_REQUEST_BUNDLE)
        val builder = AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
        }

        title?.let {
            builder.setTitle(it)
        }
        message?.let {
            builder.setMessage(it)
        }
        positiveButtonText?.let {
            builder.setPositiveButton(it) { _, _ ->
                if (requestKey != null && requestBundle != null) {
                    setFragmentResult(requestKey, requestBundle)
                }

            }
        }
        return builder.create()
    }
}