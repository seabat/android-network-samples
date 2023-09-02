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
import dev.seabat.android.kotlinwebsocket.databinding.PageTopBinding
import dev.seabat.android.kotlinwebsocket.ui.dialog.showSimpleErrorDialog

@AndroidEntryPoint
class ServerFragment : Fragment(R.layout.page_top) {
    companion object {
        val TAG: String = ServerFragment::class.java.simpleName
    }

    private var binding: PageTopBinding? = null
    private val viewModel: ServerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageTopBinding.bind(view)
        initView()
        initObserver()
        viewModel.loadRepositories()
        return
    }

    private fun initView() {
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = RepositoryListAdapter(onListItemClick = {_, _ -> })
        }

        binding?.search?.setOnCloseListener {
            binding?.search?.visibility = View.GONE
            binding?.toolbar?.visibility = View.VISIBLE
            true
        }

        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadRepositories(query)
                binding?.search?.visibility = View.GONE
                binding?.toolbar?.visibility = View.VISIBLE
                return false
            }
        })
    }

    private fun initObserver() {
        viewModel.repositories.observe(viewLifecycleOwner) {
            (binding?.recyclerview?.adapter as RepositoryListAdapter)?.updateRepositoryList(it)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}