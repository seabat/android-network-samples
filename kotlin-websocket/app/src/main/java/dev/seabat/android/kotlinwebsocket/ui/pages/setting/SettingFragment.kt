package dev.seabat.android.kotlinwebsocket.ui.pages.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.seabat.android.kotlinwebsocket.BuildConfig
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageSettingBinding

class SettingFragment : Fragment(R.layout.page_setting) {
    companion object {
        val TAG: String = SettingFragment::class.java.simpleName
    }

    private var binding: PageSettingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageSettingBinding.bind(view)
        initToolBar()
        initView()
        return
    }

    private fun initToolBar() {
        // 戻るボタン
        findNavController().let {
            val appBarConfig = AppBarConfiguration(it.graph)
            binding?.toolbar?.setupWithNavController(it, appBarConfig)
        }
    }

    private fun initView() {
        binding?.textVersionValue?.text = BuildConfig.VERSION_NAME
    }
}