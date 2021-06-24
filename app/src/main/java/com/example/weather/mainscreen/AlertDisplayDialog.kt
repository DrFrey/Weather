package com.example.weather.mainscreen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weather.data.Alert
import com.example.weather.databinding.AlertDialogFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlertDisplayDialog : DialogFragment() {

    private lateinit var binding: AlertDialogFragmentBinding
    private lateinit var adapter: AlertAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alerts = arguments?.getParcelableArrayList<Alert>(ALERTS_LIST)

        binding = AlertDialogFragmentBinding.inflate(LayoutInflater.from(context))
        adapter = AlertAdapter()
        adapter.submitList(alerts)
        binding.alertViewPager.adapter = adapter
        TabLayoutMediator(binding.alertTabLayout, binding.alertViewPager) { _, _ -> }.attach()

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    companion object {
        const val ALERTS_LIST = "ALERTS_LIST"
        fun newInstance(alerts: ArrayList<Alert>) : AlertDisplayDialog {
            val dialog = AlertDisplayDialog()
            val args = Bundle()
            args.putParcelableArrayList(ALERTS_LIST, alerts)
            dialog.arguments = args
            return dialog
        }

    }
}