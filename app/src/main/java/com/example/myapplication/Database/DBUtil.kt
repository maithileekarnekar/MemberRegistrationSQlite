package com.example.myapplication.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.models.Member

object DBUtil {

    fun updateMember(
        context: Context,
        member: Member
    ){
        val db = getDataBase(context)

        val values = ContentValues()
        values.put("id", member.id)
        values.put("name",member.name)
        values.put("mobileNumber",member.mobileNumber)
        values.put("role",member.role)
        values.put("subscriptionAmt", member.subscriptionAmt)


        db!!.update(
            "medicines",
            values,
            "id = ?",
            arrayOf("${member.id}")
        )


        db.close()
    }
    fun addMember(
        context: Context,
        member: Member,
        id: Int,
    ) {
        val db = getDataBase(context)
        val values= ContentValues()
        values.put("id", member.id)
        values.put("name",member.name)
        values.put("mobileNumber",member.mobileNumber)
        values.put("role",member.role)
        values.put("subscriptionAmt", member.subscriptionAmt)



        db!!.insert("members",null,values)
        db!!.close()

    }
    fun getMembers(
        context: Context
    ) : ArrayList<Member> {
        val db = getDataBase(context)
        val cursor = db!!.query(
            "members",
            null,
            null,
            null,
            null,
            null,
            "id"
        )
        val member = ArrayList<Member>()

        while (cursor.moveToNext()) {
            member.add(
                Member(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
                )
            )
        }
        db.close()
        return member
    }
    fun deleteMembers(
        context: Context,
        id: Int
    ){
        val db = getDataBase(context)

        db!!.delete(
            "members",
            "id = ?",
            arrayOf("$id")
        )
        db.close()
    }
    private fun getDataBase(context: Context): SQLiteDatabase? {

        return MemberDBHelper(
            context,
            "db_members",
            null,
            1
        ).writableDatabase

    }
}