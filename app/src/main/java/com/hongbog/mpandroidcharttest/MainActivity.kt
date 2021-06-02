package com.hongbog.mpandroidcharttest

import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.hongbog.mpandroidcharttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val menuItems = listOf<String>("주", "월")
    private val lineDataSetParamMap = HashMap<Code.Organ, LineDataSetParam>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpView()
        setUpObserver()
        setUpData()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    private fun setUpView() {
        binding.organChipGrp.setOnCheckedChangeListener{ grp, checkId ->
            if (!binding.dropdownMenu.editText?.text.isNullOrEmpty()) {
                val selMenuItem: String = binding.dropdownMenu.editText?.text.toString()
                setGraphData(selMenuItem, checkId)
            }
        }

        val adapter = ArrayAdapter(this, R.layout.dropdown_menu, menuItems)
        binding.menuFiled.setAdapter(adapter)
        binding.dropdownMenu.editText?.doOnTextChanged { item, _, _, _ ->
            if (item != null) {
                val selChipItem: Int = binding.organChipGrp.checkedChipId
                setGraphData(item.toString(), selChipItem)
            }
        }
        binding.menuFiled.setText(adapter.getItem(0).toString(), false)
    }

    private fun setUpObserver() {
        viewModel.histDataRst.observe(this, Observer { histDataRst ->
            if (histDataRst != null) {
                lineDataSetParamMap[Code.Organ.BR] = LineDataSetParam("뇌", 2f, 6f,
                    ContextCompat.getColor(this, R.color.yellow),
                    ContextCompat.getColor(this, R.color.yellow),
                    ContextCompat.getColor(this, R.color.yellow))
                lineDataSetParamMap[Code.Organ.LG] = LineDataSetParam("폐", 2f, 6f,
                    ContextCompat.getColor(this, R.color.purple_200),
                    ContextCompat.getColor(this, R.color.purple_200),
                    ContextCompat.getColor(this, R.color.purple_200))
                lineDataSetParamMap[Code.Organ.LV] = LineDataSetParam("간", 2f, 6f,
                    ContextCompat.getColor(this, R.color.teal_700),
                    ContextCompat.getColor(this, R.color.teal_700),
                    ContextCompat.getColor(this, R.color.teal_700))
                lineDataSetParamMap[Code.Organ.KDN] = LineDataSetParam("신장", 2f, 6f,
                    ContextCompat.getColor(this, R.color.purple_700),
                    ContextCompat.getColor(this, R.color.purple_700),
                    ContextCompat.getColor(this, R.color.purple_700))

                binding.totalChip.isChecked = true
            }
        })
    }

    private fun setUpData() {
        viewModel.setUpData()
    }

    private fun setGraphData(selMenuItem: String, selChipItem: Int) {
        if (viewModel.histDataRst.value.isNullOrEmpty()) return

        binding.lineChart.invalidate()
        binding.lineChart.clear()

        val entryMap = HashMap<Code.Organ, ArrayList<Entry>>()
        val xAxisValue = ArrayList<String>()
        for (idx: Int in viewModel.histDataRst.value!!.indices) {
            val histData = viewModel.histDataRst.value!![idx]
            if ((TextUtils.equals(selMenuItem, menuItems[0]) && !TextUtils.equals(histData.statType, "w")) ||
                (TextUtils.equals(selMenuItem, menuItems[1]) && !TextUtils.equals(histData.statType, "m"))) {
                continue
            }

            when(selChipItem) {
                binding.totalChip.id -> {
                    if (!entryMap.containsKey(Code.Organ.BR)) entryMap.put(Code.Organ.BR, ArrayList<Entry>())
                    if (!entryMap.containsKey(Code.Organ.LG)) entryMap.put(Code.Organ.LG, ArrayList<Entry>())
                    if (!entryMap.containsKey(Code.Organ.LV)) entryMap.put(Code.Organ.LV, ArrayList<Entry>())
                    if (!entryMap.containsKey(Code.Organ.KDN)) entryMap.put(Code.Organ.KDN, ArrayList<Entry>())

                    entryMap[Code.Organ.BR]!!.add(Entry(idx.toFloat(), histData.brGr.toFloat()))
                    entryMap[Code.Organ.LG]!!.add(Entry(idx.toFloat(), histData.lgGr.toFloat()))
                    entryMap[Code.Organ.LV]!!.add(Entry(idx.toFloat(), histData.lvGr.toFloat()))
                    entryMap[Code.Organ.KDN]!!.add(Entry(idx.toFloat(), histData.kdnGr.toFloat()))
                }
                binding.brainChip.id -> {
                    if (!entryMap.containsKey(Code.Organ.BR)) entryMap.put(Code.Organ.BR, ArrayList<Entry>())
                    entryMap[Code.Organ.BR]!!.add(Entry(idx.toFloat(), histData.brGr.toFloat()))
                }
                binding.lungChip.id -> {
                    if (!entryMap.containsKey(Code.Organ.LG)) entryMap.put(Code.Organ.LG, ArrayList<Entry>())
                    entryMap[Code.Organ.LG]!!.add(Entry(idx.toFloat(), histData.lgGr.toFloat()))
                }
                binding.liverChip.id -> {
                    if (!entryMap.containsKey(Code.Organ.LV)) entryMap.put(Code.Organ.LV, ArrayList<Entry>())
                    entryMap[Code.Organ.LV]!!.add(Entry(idx.toFloat(), histData.lvGr.toFloat()))
                }
                binding.kidneyChip.id -> {
                    if (!entryMap.containsKey(Code.Organ.KDN)) entryMap.put(Code.Organ.KDN, ArrayList<Entry>())
                    entryMap[Code.Organ.KDN]!!.add(Entry(idx.toFloat(), histData.kdnGr.toFloat()))
                }
            }
            xAxisValue.add(histData.usrAnalDt)
        }

        val lineDataSetList = ArrayList<LineDataSet>()
        for ((key, value) in entryMap) {
            lineDataSetList.add(getLineDataSet(value, lineDataSetParamMap[key]!!))
        }

        val lineData: LineData? = LineData()
        for (lineDataSet: LineDataSet in lineDataSetList) {
            lineData?.addDataSet(lineDataSet)
        }
        lineData?.apply {
            setValueTextSize(12f)
            setValueTextColor(ContextCompat.getColor(this@MainActivity, R.color.teal_200))
            setValueFormatter(GraphYAxisValueFormatter())
        }

        setUpChart(xAxisValue)
        binding.lineChart.data = lineData
        binding.lineChart.marker = CustomMarkerView(this, R.layout.custom_marker_view)
    }

    private fun getLineDataSet(
        entry: ArrayList<Entry>,
        lineDataSetParam: LineDataSetParam
    ): LineDataSet {
        return LineDataSet(entry, lineDataSetParam.label).apply {
            lineWidth = lineDataSetParam.lineWidth
            circleRadius = lineDataSetParam.circleRadius
            setCircleColor(lineDataSetParam.circleColor)
            circleHoleColor = lineDataSetParam.circleHoleColor
            color = lineDataSetParam.color
        }
    }

    private fun setUpChart(xAxisValue: ArrayList<String>) {
        binding.lineChart.apply {
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textSize = 16f
                textColor = ContextCompat.getColor(this@MainActivity, R.color.teal_200)
                setDrawGridLines(false)
                isGranularityEnabled = true
                labelCount = xAxisValue.size
                valueFormatter = GraphXAxisLabelFormatter(xAxisValue)
            }
            axisRight.isEnabled = false
            axisLeft.apply {
                axisMinimum = 0f
                axisMaximum = 5f
                textSize = 16f
                textColor = ContextCompat.getColor(this@MainActivity, R.color.teal_200)
            }
            legend.apply {
                textSize = 16f
                textColor = ContextCompat.getColor(this@MainActivity, R.color.teal_200)
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
        }
    }
}