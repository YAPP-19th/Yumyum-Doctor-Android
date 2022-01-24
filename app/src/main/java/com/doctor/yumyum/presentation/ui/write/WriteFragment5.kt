package com.doctor.yumyum.presentation.ui.write


import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
import androidx.lifecycle.lifecycleScope
import com.doctor.yumyum.R
import com.doctor.yumyum.common.base.BaseFragment
import com.doctor.yumyum.databinding.FragmentWriteFifthBinding
import com.doctor.yumyum.presentation.ui.write.viewmodel.WriteViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * 레시피 작성하기 5
 * - 사진(필수), 텍스트(필수아님)로 리뷰 작성하기
 * - 비공개 여부 결정
 */

class WriteFragment5 : BaseFragment<FragmentWriteFifthBinding>(R.layout.fragment_write_fifth) {
    private lateinit var reviewImageLauncher: ActivityResultLauncher<Intent>
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGalleryListener()
            }
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
            WriteDialog {
                showLoading()
                lifecycleScope.launch {
                    val postRecipe = lifecycleScope.async {
                        writeViewModel.postRecipe()
                    }
                    postRecipe.await()
                    activity?.finish()
                }

            }.show(parentFragmentManager, "WriteDialog")

        }
        writeViewModel.fifthOnFinish.observe(viewLifecycleOwner) {
            if (it) {
                binding.writeBtnFinish.isEnabled = true
                binding.writeBtnFinish.background = resources.getDrawable(R.drawable.bg_btn_main)
            } else {
                binding.writeBtnFinish.isEnabled = false
                binding.writeBtnFinish.background = resources.getDrawable(R.drawable.bg_btn_sub)
            }
        }

        writeViewModel.privateMode.observe(viewLifecycleOwner) {
            if (it) {
                binding.writeFifthSwRecipePrivate.setTrackResource(R.drawable.recipe_private_sw_seleted_track)
            } else {
                binding.writeFifthSwRecipePrivate.setTrackResource(R.drawable.recipe_private_sw_track)
            }
        }

        writeViewModel.errorState.observe(viewLifecycleOwner) { resId ->
            showToast(getString(resId))
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
                binding.writeFifthTvCount.text = binding.writeFifthTvCount.context.getString(
                    R.string.write_tv_text_count,
                    s.toString().length
                )
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
                        return@registerForActivityResult
                    } else {
                        it.data
                    }

                    val images =
                        writeViewModel.reviewImageList.value?.toMutableList() ?: arrayListOf()
                    intentResult?.clipData?.apply {
                        for (i in 0 until this.itemCount) {
                            val uri = this.getItemAt(i).uri
                            val path = uriToPath(requireContext(), uri)
                            images.add(Pair(uri, path))
                            if (images.size == 3) break
                        }
                    } ?: intentResult?.data?.let {
                        images.add(Pair(it, uriToPath(requireContext(), it)))
                    }
                    Log.d("img", images.toString())
                    writeViewModel.setReviewImageList(images)
                }
            }

    }

    fun openGalleryListener() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                WRITE_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                reviewImageLauncher.launch(intent)
            }
            shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
            -> {
                showDialogToGetPermission()
            }
            else -> {
                requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.img_permission_dialog_title))
            .setMessage(resources.getString(R.string.img_permission_dialog_message))

        builder.setPositiveButton(resources.getString(R.string.img_permission_dialog_ok)) { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", "com.doctor.yumyum", null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton(resources.getString(R.string.img_permission_dialog_dismiss)) { dialogInterface, i ->
            // ignore
        }
        val dialog = builder.create()
        dialog.show()
    }

}
