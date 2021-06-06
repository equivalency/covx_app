package com.example.cov_x.models

data class FotoDataRequest(
        var id: Int = 0,
        var files: Array<String>? = null
)

data class FotoDataResponse(
        var filename: String = "",
        var prediction: String = "",
        var status: String = ""
)