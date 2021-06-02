package com.hongbog.mpandroidcharttest

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class GraphXAxisLabelFormatter constructor(
    private val xAxisValue: ArrayList<String>
): ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return if (xAxisValue.size > value.toInt()) xAxisValue[value.toInt()].substring(4) else ""
    }
}