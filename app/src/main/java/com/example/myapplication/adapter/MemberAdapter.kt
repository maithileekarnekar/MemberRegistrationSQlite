package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.MemberViewBinding
import com.example.myapplication.models.Member

class MemberAdapter(
    private val memberList: ArrayList<Member>
) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(private val binding: MemberViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val txtMemberName = binding.txtMemberName
        val txtMobileNumber = binding.txtMobileNumber
        val txtMemberRole = binding.txtMemberRole

    }

    override fun getItemCount() = memberList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = MemberViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder:MemberViewHolder, position: Int) {
        val member = memberList[position]
        holder.txtMemberName.text = member.name
        holder.txtMobileNumber.text = member.mobileNumber
        holder.txtMemberRole.text = member.role

    }
}