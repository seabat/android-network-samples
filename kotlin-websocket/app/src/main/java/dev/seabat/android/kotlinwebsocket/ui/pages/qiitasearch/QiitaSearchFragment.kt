package dev.seabat.android.kotlinwebsocket.ui.pages.qiitasearch

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.text.DateFormat
import android.icu.text.DateFormatSymbols
import android.icu.util.JapaneseCalendar
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.PageQiitaSearchBinding
import dev.seabat.android.kotlinwebsocket.ui.dialog.showSimpleErrorDialog
import dev.seabat.android.kotlinwebsocket.ui.pages.top.TopFragment
import dev.seabat.android.kotlinwebsocket.utils.convertToJapaneseCalender
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class QiitaSearchFragment : BottomSheetDialogFragment(R.layout.page_qiita_search) {
    private val viewModel: QiitaSearchViewModel by viewModels()
    private var binding: PageQiitaSearchBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener { it ->
                val bottomSheetDialog = (it as? BottomSheetDialog) ?: return@setOnShowListener
                val bottomSheet =
                    bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                        ?: return@setOnShowListener

                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }

                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams

                //WARNING: 以下のコードでは layoutParams が更新されない！
//                bottomSheet.layoutParams.apply {
//                    this.height = WindowManager.LayoutParams.MATCH_PARENT
//                }

                this@QiitaSearchFragment.view?.let {
                    initView(it)
                    initObserver(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageQiitaSearchBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initView(view: View) {
        PageQiitaSearchBinding.bind(view).let {
            // CLOSEボタン
            it.textClose.setOnClickListener {
                goBack()
            }
            // 開始日ボタン
            it.buttonStartDate.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener() { _, year, month, dayOfMonth ->
                        viewModel.setStartDate("${year}-${month + 1}-${dayOfMonth}")
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            // 検索ボタン
            it.buttonSearch.setOnClickListener {
                goBackWithValue()
            }
        }
    }

    private fun initObserver(view: View) {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TopFragment.TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TopFragment.TAG) {
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

        viewModel.startDate.observe(viewLifecycleOwner) {
            binding?.textStartDate?.text = convertToJapaneseCalender(it)
        }
    }

    private fun goBackWithValue() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "searchParam", bundleOf("start" to viewModel.startDate.value)
        )

        goBack()
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    /**
     * 上部の角を丸くしたデザインを適用する
     *
     * ref. https://note.com/yasukotelin/n/nb1c877358d4a
     */
    override fun getTheme(): Int {
        return R.style.QiitaSearchBottomSheetDialogTheme
    }
}