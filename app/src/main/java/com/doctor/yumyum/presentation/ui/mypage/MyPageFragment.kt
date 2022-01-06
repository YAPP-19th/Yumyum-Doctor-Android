package com.doctor.yumyum.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.common.utils.gradePoint
import com.doctor.yumyum.databinding.FragmentMyPageBinding
import com.doctor.yumyum.presentation.ui.mypage.myinfo.MyInfoActivity
import com.doctor.yumyum.presentation.ui.taste.TasteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MyPageViewModel::class.java]
    }

    private val gradeBadge: Map<String, Int> by lazy {
        mapOf(
            getString(R.string.grade_student) to R.drawable.ic_badge_student,
            getString(R.string.grade_bachelor) to R.drawable.ic_badge_bachelor,
            getString(R.string.grade_master) to R.drawable.ic_badge_master,
            getString(R.string.grade_expert) to R.drawable.ic_badge_expert,
            getString(R.string.grade_professor) to R.drawable.ic_badge_professor,
        )
    }
    private val nextGrade: Map<String, String> by lazy {
        mapOf(
            getString(R.string.grade_student) to getString(R.string.grade_bachelor),
            getString(R.string.grade_bachelor) to getString(R.string.grade_master),
            getString(R.string.grade_master) to getString(R.string.grade_expert),
            getString(R.string.grade_expert) to getString(R.string.grade_professor),
            getString(R.string.grade_professor) to getString(R.string.grade_professor),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserInfo()
        }

        viewModel.gradeUp.observe(viewLifecycleOwner) { grade ->
            if (grade.isNotBlank()) {
                activity?.let {
                    GradeUpDialog(grade).show(
                        it.supportFragmentManager,
                        "GradeUpDialog"
                    )
                }
            }
        }

        viewModel.grade.observe(viewLifecycleOwner) {
            gradeBadge[it]?.let { badge -> binding.myPageIvBadge.setImageResource(badge) }
            nextGrade[it]?.let { point -> binding.myPageTvNextLevel.text = point }
        }

        viewModel.point.observe(viewLifecycleOwner) { point ->
            binding.myPageGraphOrange.apply {
                gradePoint[nextGrade[viewModel.grade.value]]?.let { nextPoint ->
                    if (nextPoint != 0) {
                        this.layoutParams.width =
                            binding.myPageGraphWhite.width * point / nextPoint
                    }
                }
            }
        }

        binding.apply {
            myPageIbInfo.setOnClickListener {
                activity?.let { activity ->
                    LevelInfoDialog().show(
                        activity.supportFragmentManager,
                        "LogoutDialog"
                    )
                }
            }
            myPageTvTasteSetting.setOnClickListener {
                val tasteIntent = Intent(context, TasteActivity::class.java)
                tasteIntent.putExtra(getString(R.string.taste_mode), true)
                startActivity(tasteIntent)
            }
            myPageTvMyInfo.setOnClickListener {
                startActivity(Intent(context, MyInfoActivity::class.java))
                myPageTvLogout.setOnClickListener {
                    activity?.let { activity ->
                        LogoutDialog(viewModel).show(
                            activity.supportFragmentManager,
                            "LogoutDialog"
                        )
                    }
                }
            }
        }
    }
}