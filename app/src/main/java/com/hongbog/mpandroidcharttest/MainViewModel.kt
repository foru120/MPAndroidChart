package com.hongbog.mpandroidcharttest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val histDataRst: MutableLiveData<ArrayList<HistData>> = MutableLiveData()

    fun setUpData() {
        val histData1: HistData = HistData(
            "w", 1, "202113", 80.00, 1.88, 1.03, 2.30, 1.61, 1.32, 1.00, 1.91
        )
        val histData2: HistData = HistData(
            "w", 2, "202114", 77.80, 1.75, 1.10, 2.35, 1.65, 1.30, 1.05, 1.95
        )
        val histData3: HistData = HistData(
            "w", 3, "202115", 80.00, 2.00, 1.00, 2.43, 1.71, 1.29, 1.00, 2.00
        )
        val histData4: HistData = HistData(
            "w", 4, "202116", 78.00, 2.14, 1.05, 2.48, 1.81, 1.25, 1.05, 2.03
        )
        val histData5: HistData = HistData(
            "w", 5, "202117", 81.00, 2.22, 0.95, 2.65, 1.86, 1.20, 1.00, 2.10
        )
        val histData6: HistData = HistData(
            "m", 1, "20213", 82.20, 1.85, 1.00, 2.37, 1.59, 1.25, 1.00, 1.96
        )
        val histData7: HistData = HistData(
            "m", 2, "20214", 74.80, 1.83, 1.09, 2.34, 1.69, 1.36, 1.03, 1.91
        )
        val histData8: HistData = HistData(
            "m", 3, "20215", 78.80, 1.80, 1.14, 2.35, 1.73, 1.40, 1.02, 1.95
        )
        val histData9: HistData = HistData(
            "m", 4, "20216", 72.80, 1.83, 1.20, 2.37, 1.64, 1.44, 1.00, 1.93
        )
        val histData10: HistData = HistData(
            "m", 5, "20217", 83.80, 1.84, 1.21, 2.33, 1.66, 1.50, 1.00, 1.94
        )

        val histDataList: ArrayList<HistData> = ArrayList()
        histDataList.add(histData1)
        histDataList.add(histData2)
        histDataList.add(histData3)
        histDataList.add(histData4)
        histDataList.add(histData5)
        histDataList.add(histData6)
        histDataList.add(histData7)
        histDataList.add(histData8)
        histDataList.add(histData9)
        histDataList.add(histData10)

        this.histDataRst.value = histDataList
    }
}