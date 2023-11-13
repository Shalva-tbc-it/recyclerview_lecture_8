package com.example.recyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var firstName: String,
    var lastName: String,
    var email: String
): Parcelable

