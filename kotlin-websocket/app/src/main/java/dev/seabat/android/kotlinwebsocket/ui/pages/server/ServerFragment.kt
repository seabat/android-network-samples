package dev.seabat.android.kotlinwebsocket.ui.pages.server

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageServerBinding
import dev.seabat.android.kotlinwebsocket.ui.dialog.showSimpleErrorDialog

@AndroidEntryPoint
class ServerFragment : Fragment(R.layout.page_server) {
    companion object {
        val TAG: String = ServerFragment::class.java.simpleName
    }

    private var binding: PageServerBinding? = null
    private val viewModel: ServerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageServerBinding.bind(view)
        initView()
        initObserver()
        viewModel.loadRepositories()
        return
    }

    private fun initView() {
    }

    private fun initObserver() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}