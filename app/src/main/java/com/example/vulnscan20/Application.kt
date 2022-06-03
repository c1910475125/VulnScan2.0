package com.example.vulnscan20

import java.io.Serializable

data class Application(val appName: String,
                       val packageName: String,
                       val version: String?,
                       val sourcedir: String,
                       val datadir: String
) : Serializable