package com.example.myapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Database.DBUtil
import com.example.myapplication.R
import com.example.myapplication.adapter.MemberAdapter
import com.example.myapplication.databinding.RegistrationDetailsFragmentBinding
import com.example.myapplication.models.Member

class RegistrationDetailsFragment : Fragment() {
    private lateinit var binding: RegistrationDetailsFragmentBinding
    private var memberList: ArrayList<Member> = ArrayList()
    private lateinit var memberAdapter: MemberAdapter

    interface OnMemberAddedListener {
        fun onMemberAdded(member: Member)
        fun getExistingMember(): Member?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationDetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        initData()
        initViews()
        setupListeners()

        return view
    }

    private fun setupListeners() {
        binding.btnAddMember.setOnClickListener {
            val addMemberDetailsFragment = MemberRegistrationFragment()
            addFragment(addMemberDetailsFragment)

            addMemberDetailsFragment.onMemberAddedListener =
                object : MemberRegistrationFragment.OnMemberAddedListener {
                    override fun onMemberAdded(member: Member) {
                        memberList.add(member)
                        DBUtil.addMember(requireContext(), member, memberList.size - 1)
                        memberAdapter.notifyItemInserted(memberList.size - 1)
                        removeFragment(addMemberDetailsFragment)
                    }

                    override fun getExistingMember(): Member? {
                        return null
                    }
                }
        }
    }

    private fun addFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    private fun removeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        parentFragmentManager.popBackStack()
    }

    private fun mt(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    private fun initData() {
        memberList.addAll(DBUtil.getMembers(requireContext()))
    }

    private fun initViews() {
        binding.memberRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        memberAdapter = MemberAdapter(memberList)
        binding.memberRecycler.adapter = memberAdapter
    }
}
