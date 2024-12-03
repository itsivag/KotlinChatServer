package com.itsivag.kotlin_chat_server.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Message @JsonCreator constructor(
    @JsonProperty("content") val content: String,
    @JsonProperty("time") val time: String
)