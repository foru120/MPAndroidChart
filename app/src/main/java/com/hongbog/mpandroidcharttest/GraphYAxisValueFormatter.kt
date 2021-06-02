package com.hongbog.mpandroidcharttest

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat

class GraphYAxisValueFormatter: ValueFormatter() {
    private var format: DecimalFormat? = null

    init {
        format = DecimalFormat("###,###,##0.0")
    }

    override fun getFormattedValue(
        value: Float,
        entry: Entry?,
        dataSetIndex: Int,
        viewPortHandler: ViewPortHandler?
    ): String {
        return format!!.format(value)
    }
}