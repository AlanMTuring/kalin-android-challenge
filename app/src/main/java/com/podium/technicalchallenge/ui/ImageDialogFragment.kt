package com.podium.technicalchallenge.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.podium.technicalchallenge.databinding.LayoutImageDialogBinding

class ImageDialogFragment : DialogFragment() {

    companion object {
        const val ARG_IMAGE_URL = "arg:iamgeUrl"
    }

    lateinit var binding: LayoutImageDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = android.app.AlertDialog.Builder(it)
            val inflater = it.layoutInflater

            binding = LayoutImageDialogBinding.inflate(inflater)
            val imageUrl = arguments?.getString(ARG_IMAGE_URL)
            binding.imageUrl = imageUrl
            builder.setView(binding.root)
            builder.create()
        }
    }

}