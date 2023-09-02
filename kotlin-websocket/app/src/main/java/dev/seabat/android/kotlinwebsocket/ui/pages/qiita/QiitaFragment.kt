package dev.seabat.android.kotlinwebsocket.ui.pages.qiita

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageQiitaBinding
import dev.seabat.android.kotlinwebsocket.ui.dialog.showSimpleErrorDialog
import dev.seabat.android.kotlinwebsocket.utils.convertToJapaneseCalender
import dev.seabat.android.kotlinwebsocket.utils.getDateFromBundle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class QiitaFragment : Fragment(R.layout.page_qiita) {
    companion object {
        val TAG: String = QiitaFragment::class.java.simpleName
    }

    private var binding: PageQiitaBinding? = null
    private val viewModel: QiitaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageQiitaBinding.bind(view)
        initView()
        initToolBar()
        initObserver()
        return
    }

    private fun initView() {
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = QiitaArticleListAdapter(onListItemClick = this@QiitaFragment.onListItemClick)
        }
    }

    private fun initToolBar() {
        //NOT: タブ画面には「戻る」ボタンが要らないので以下をコメントアウトする
//        // 戻るボタン
//        this.findNavController().let {
//            val appBarConfig = AppBarConfiguration(it.graph)
//            binding?.toolbar?.setupWithNavController(it, appBarConfig)
//        }

        // タイトル
        binding?.toolbar?.title = getString(R.string.qiita_title_recently)

        // NOTE: フラグメントが所有するアプリバーは onCreateOptionsMenu ではなく
        //       ここで onViewCreated 等で inflate する
        //       ref. https://developer.android.com/guide/fragments/appbar?hl=ja#fragment-inflate
        binding?.toolbar?.inflateMenu(R.menu.qiita)
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    val action = QiitaFragmentDirections.actionToQiitaSearch()
                    this@QiitaFragment.findNavController().navigate(action)
                    true
                }

                else -> false
            }
        }
    }

    private fun initObserver() {
        viewModel.articles.observe(viewLifecycleOwner) {
            (binding?.recyclerview?.adapter as QiitaArticleListAdapter)?.updateArticleList(it)
            if (it.size > 0 && it[0].totalCount != null) {
                Toast.makeText(
                    requireActivity(),
                    "${it[0].totalCount.toString()}件ヒットしました。",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.progressVisible.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressbar?.visibility = View.VISIBLE
            } else {
                binding?.progressbar?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TAG) {
                            android.util.Log.d(
                                "Hello",
                                "Error dialog closed(${bundle.getString("errorMessage")})"
                            )
                            viewModel.clearError()
                        }
                    }
                )
            }
        }

        // 検索日でタイトルを更新
        viewModel.searchDate.observe(viewLifecycleOwner) {
            binding?.toolbar?.title = getString(R.string.qiita_title_posted_date) + convertToJapaneseCalender(it) + "〜"
        }

        // 検索画面から検索日を受け取る
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("searchParam")
            ?.observe(viewLifecycleOwner) {
                viewModel.loadQiitaArticles(getDateFromBundle(it, "start"))
            }
    }

    private val onListItemClick: (title: String, htmlUrl: String) -> Unit =
        { title, htmlUrl ->
            // Qiita 詳細画面に遷移する
            val action = QiitaFragmentDirections.actionToQiitaDetail().apply {
                this.title = title
                this.url = htmlUrl
            }
            this.findNavController().navigate(action)
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