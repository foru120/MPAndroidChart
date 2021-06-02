package com.hongbog.mpandroidcharttest

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LineDataSetParam constructor(
    val label: String,
    val lineWidth: Float,
    val circleRadius: Float,
    val circleColor: Int,
    val circleHoleColor: Int,
    val color: Int
): Parcelable