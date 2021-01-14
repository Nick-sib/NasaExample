package geekbarains.material.model.entity

import com.google.gson.annotations.SerializedName

data class PODServerResponseData(
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val mediaType: String?,
    val title: String?,
    @field:SerializedName("url")
    val singleUrl: String?,
    @field:SerializedName("hdurl")
    val hDUrl: String?
)
