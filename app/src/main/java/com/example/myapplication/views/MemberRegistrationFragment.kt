package com.example.myapplication.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.MemberRegistrationFragmentBinding
import com.example.myapplication.models.Member

class MemberRegistrationFragment : Fragment() {
    private lateinit var binding: MemberRegistrationFragmentBinding

    interface OnMemberAddedListener {
        fun onMemberAdded(member: Member)
        fun getExistingMember(): Member?
    }

    var onMemberAddedListener: OnMemberAddedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MemberRegistrationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        bindData()
        setupListeners()

        return view
    }

    private fun setupListeners() {
        binding.btnSubmit.setOnClickListener {
            val member = Member(
                1,
                binding.edtMemberName.text.toString(),
                binding.edtMemberMobileNumber.text.toString(),
                getSelectedRole()
            )
            onMemberAddedListener?.onMemberAdded(member)
            removeCurrentFragment()
        }
    }

    private fun removeCurrentFragment() {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    private fun bindData() {
        onMemberAddedListener?.let { listener ->
            val existingMember = listener.getExistingMember()
            if (existingMember != null) {
                binding.edtMemberName.setText(existingMember.name)
                binding.edtMemberMobileNumber.setText(existingMember.mobileNumber)
                setRoleSelection(existingMember.role)
            }
        }
    }

    private fun setRoleSelection(role: String) {
        when (role) {
            "Member" -> binding.btnMember.isChecked = true
            "Secretary" -> binding.btnSecretary.isChecked = true
        }
    }

    private fun getSelectedRole(): String {
        return if (binding.btnSecretary.isChecked) "Secretary" else "Member"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
        }
    }
}
