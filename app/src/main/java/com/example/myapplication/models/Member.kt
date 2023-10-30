package com.example.myapplication.models

data class Member(
    val id: Int,
    val name: String,
    val mobileNumber: String,
    val role: String,
    val subscriptionAmt: Int = 0
)