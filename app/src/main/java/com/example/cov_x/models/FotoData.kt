package com.example.cov_x.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FotoDataRequest(
        var files: File
): Parcelable


data class PrediksiResponse(
        var data: List<ListFotoPrediksiResponse>
)

@Parcelize
data class ListFotoPrediksiResponse(
        var filename: String = "",
        var filepath: String = "",
        var prediction: String = "",
        var status: String = "",
        var created_at: String = ""
): Parcelable

data class HapusFotoRequest(
        var filename: String = ""
)