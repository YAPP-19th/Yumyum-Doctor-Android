package com.doctor.yumyum.presentation.ui.write


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFifthBinding
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel

/**
 * 레시피 작성하기 5
 * - 사진(필수), 텍스트(필수아님)로 리뷰 작성하기
 * - 비공개 여부 결정
 */

class WriteFragment5 : BaseFragment<FragmentWriteFifthBinding>(R.layout.fragment_write_fifth) {
    private lateinit var reviewImageLauncher: ActivityResultLauncher<Intent>
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if(isGranted){openGalleryListener()}
        }
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
            WriteDialog(writeViewModel).show(parentFragmentManager, "WriteDialog")
        }
    }

    private fun initBinding() {
        binding.viewModel = writeViewModel
        binding.fragment = this
    }

    private fun changeReview() {
        binding.writeFifthEtReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.writeFifthTvCount.text = binding.writeFifthTvCount.context.getString(R.string.write_tv_text_count,s.toString().length)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun openGallery() {
        reviewImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val intentResult = if (it.data == null) {
                        //어떤 이미지도 선택하지 않은 경우 예외 처리 필요
                        return@registerForActivityResult
                    } else {
                        it.data
                    }

                    //이미지를 여러장 선택한 경우
                    val images =
                        writeViewModel.reviewImageList.value?.toMutableList() ?: arrayListOf()
                    intentResult?.clipData?.apply {
                        for (i in 0 until this.itemCount) {
                            val uri = this.getItemAt(i).uri
                            val path = uriToPath(requireContext(), uri)
                            images.add(Pair(uri, path))
                            if (images.size == 3) break
                        }
                    }
                    writeViewModel.setReviewImageList(images)
                    Log.d("imgsize", writeViewModel.reviewImageList.value?.size.toString())
                }
            }

    }

    fun openGalleryListener() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                READ_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                reviewImageLauncher.launch(intent)
            }
            shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
            -> {
                showDialogToGetPermission()
            }
            else -> {
                requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            }
        }
    }

    @SuppressLint("Range")
    fun uriToPath(context: Context, uri: Uri): String {
        val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null) ?: return ""
        cursor.moveToNext()
        return cursor.getString(cursor.getColumnIndex("_data"))
    }

    private fun showDialogToGetPermission() {
        //TODO 디자인이 나오면 삭제 예정, 삭제예정이여서 하드코딩했습니다.
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permission request")
            .setMessage("냠냠박사는 이미지 접근 허용이 필요합니다. \n일부 권한을 부여하려면 설정으로 이동해야 합니다.")

        builder.setPositiveButton("OK") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", "com.doctor.yumyum", null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton("Later") { dialogInterface, i ->
            // ignore
        }
        val dialog = builder.create()
        dialog.show()
    }

}