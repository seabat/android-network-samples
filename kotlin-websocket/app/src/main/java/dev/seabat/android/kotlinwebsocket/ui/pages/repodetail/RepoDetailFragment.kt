package dev.seabat.android.kotlinwebsocket.ui.pages.repodetail

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
import dev.seabat.android.kotlinwebsocket.databinding.PageRepoDetailBinding

@AndroidEntryPoint
class RepoDetailFragment : Fragment(R.layout.page_repo_detail) {
    companion object {
        val TAG: String = RepoDetailFragment::class.java.simpleName
    }

    private var binding: PageRepoDetailBinding? = null
    private val viewModel: RepoDetailViewModel by viewModels()
    val args: RepoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageRepoDetailBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
        val repoUrl = args.repoUrl
        val repoWebView = binding?.webview
        repoWebView?.loadUrl(repoUrl)
    }

    private fun initToolBar() {
        // 戻るボタン
        this.findNavController().let {
            val appBarConfig = AppBarConfiguration(it.graph)
            binding?.toolbar?.setupWithNavController(it, appBarConfig)
        }

        // タイトル
        val repoName = args.repoName
        binding?.toolbar?.title = repoName
    }

    private fun initObserver() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}