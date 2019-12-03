package com.ace.bi3.samplerefreshtoken.network_response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Boat(
    @SerializedName("boatId")
    val boatId: Int,
    @SerializedName("boatName")
    val boatName: String,
    @SerializedName("boatOwnerName")
    val boatOwnerName: String,
    @SerializedName("boatRegistrationNo")
    val boatRegistrationNo: String,
    @SerializedName("boatTripId")
    val boatTripId: Int,
    @SerializedName("destination")
    val destination: Any,
    @SerializedName("estimatedDepartureDate")
    val estimatedDepartureDate: Any,
    @SerializedName("estimatedReturnDate")
    val estimatedReturnDate: Any,
    @SerializedName("inspectionOffice")
    val inspectionOffice: Any,
    @SerializedName("registeredDate")
    val registeredDate: Any,
    @SerializedName("registrationNo")
    val registrationNo: String,
    @SerializedName("registrationOffice")
    val registrationOffice: Any
): Serializable