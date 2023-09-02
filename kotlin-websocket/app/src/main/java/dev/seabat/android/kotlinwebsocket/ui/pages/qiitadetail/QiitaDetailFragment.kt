package dev.seabat.android.kotlinwebsocket.ui.pages.qiitadetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageQiitaDetailBinding

@AndroidEntryPoint
class QiitaDetailFragment : Fragment(R.layout.page_qiita_detail) {
    companion object {
        val TAG: String = QiitaDetailFragment::class.java.simpleName
    }

    private var binding: PageQiitaDetailBinding? = null
    private val viewModel: QiitaDetailViewModel by viewModels()
    val args: QiitaDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageQiitaDetailBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
        val url = args.url
        val qiitaWebView = binding?.webview
        qiitaWebView?.loadUrl(url)
    }

    private fun initToolBar() {
        // 戻るボタン
        this.findNavController().let {
            val appBarConfig = AppBarConfiguration(it.graph)
            binding?.toolbar?.setupWithNavController(it, appBarConfig)
        }

        // タイトル
        val title = args.title
        binding?.toolbar?.title = title
    }

    private fun initObserver() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}