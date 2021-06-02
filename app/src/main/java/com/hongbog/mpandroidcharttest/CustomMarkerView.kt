package com.hongbog.mpandroidcharttest

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.custom_marker_view.view.*

class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e is CandleEntry) {
            val ce: CandleEntry = e
            marker_txt.text = ce.high.toString()
        } else {
            marker_txt.text = e?.y.toString()
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}