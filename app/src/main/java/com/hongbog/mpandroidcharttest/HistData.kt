package com.hongbog.mpandroidcharttest

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistData constructor(
    val statType: String,
    val rnum: Int,
    val usrAnalDt: String,
    val irisAge: Double,
    val stsGr: Double,
    val cholGr: Double,
    val stmGr: Double,
    val brGr: Double,
    val lgGr: Double,
    val lvGr: Double,
    val kdnGr: Double
): Parcelable