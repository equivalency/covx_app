package com.example.cov_x.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FotoDataRequest(
        var files: ArrayList<ByteArray> = arrayListOf(),
): Parcelable

data class FotoDataResponse(
        var message: String = ""
)

data class ListFotoPrediksiResponse(
        var filename: String = "",
        var filepath: String = "",
        var prediction: String = "",
        var status: String = "",
        var timestamp: String = ""
)