package com.example.ete.data.bean.auth

import java.io.Serializable


data class AuthBean(
    var provideType: String = "",
    var token: String = "",
    var countryCode: String = "",
    var fullName: String = "",
    var phone: String = "",
    var email: String = "",
    var profilePic: String = "",
    var otp: String = "",
    var successMessage: String = "",
    var orderId: String = ""
): Serializable
