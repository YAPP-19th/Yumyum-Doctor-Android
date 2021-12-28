package com.doctor.yumyum.presentation.ui.write

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFifthBinding
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 레시피 작성하기 5
 * - 사진(필수), 텍스트(필수아님)로 리뷰 작성하기
 * - 비공개 여부 결정
 */

class WriteFragment5 : BaseFragment<FragmentWriteFifthBinding>(R.layout.fragment_write_fifth) {
    private lateinit var reviewImageLauncher: ActivityResultLauncher<Intent>
    private val writeViewModel: WriteViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteViewModel() as T
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        changeReview()
        openGallery()

        binding.writeBtnFinish.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                postRecipe()
            }
        }
    }

    private fun initBinding() {
        binding.viewModel = writeViewModel
    }

    private fun changeReview() {
        binding.writeFifthEtReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.writeFifthTvCount.text = "${s.toString().length}/110"
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun openGallery() {
        reviewImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val intentResult = if (it.data == null) {
                    //어떤 이미지도 선택하지 않은 경우 예외 처리 필요
                    return@registerForActivityResult
                } else {
                    it.data
                }

                //이미지를 여러장 선택한 경우
                val images = writeViewModel.reviewImageList.value?.toMutableList() ?: arrayListOf()
                intentResult?.clipData?.apply {
                    for (i in 0 until this.itemCount) {
                        val uri = this.getItemAt(i).uri
                        val path = uriToPath(requireContext(), uri)
                        images.add(Pair(uri,path))
                        Log.d("Fragment",images.toString())
                        if (images.size == 3) break
                    }
                }
                writeViewModel.setReviewImageList(images)
            }
        }

        binding.writeFifthIbImage1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            reviewImageLauncher.launch(intent)
        }
    }

    @SuppressLint("Range")
    fun uriToPath(context: Context, uri: Uri): String {
        val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null) ?: return ""
        cursor.moveToNext()
        return cursor.getString(cursor.getColumnIndex("_data"))
    }

    private suspend fun postRecipe() {
        writeViewModel.postRecipe()
    }
}