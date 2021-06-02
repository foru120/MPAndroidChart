package com.hongbog.mpandroidcharttest

class Code {
    enum class Organ constructor(
        private val value: String
    ){
        BR("BR"), LV("LV"), LG("LG"), KDN("KDN");

        override fun toString(): String {
            return this.value
        }
    }
}