package dev.seabat.android.kotlinwebsocket.ui.pages.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageClientBinding
import java.util.Date

@AndroidEntryPoint
class ClientFragment : Fragment(R.layout.page_client) {
    companion object {
        val TAG: String = ClientFragment::class.java.simpleName
    }

    private var binding: PageClientBinding? = null
    private val viewModel: ClientViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageClientBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
    }

    private fun initToolBar() {
        //NOT: タブ画面には「戻る」ボタンが要らないので以下をコメントアウトする
//        // 戻るボタン
//        this.findNavController().let {
//            val appBarConfig = AppBarConfiguration(it.graph)
//            binding?.toolbar?.setupWithNavController(it, appBarConfig)
//        }

        // タイトル
        binding?.toolbar?.title = getString(R.string.client_title)

        // NOTE: フラグメントが所有するアプリバーは onCreateOptionsMenu ではなく
        //       ここで onViewCreated 等で inflate する
        //       ref. https://developer.android.com/guide/fragments/appbar?hl=ja#fragment-inflate
    }

    private fun initObserver() {
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadQiitaArticles(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}