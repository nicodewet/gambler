package com.thorgil.gambler.entrypoints.rest

import com.fasterxml.jackson.annotation.JsonInclude

/**
 *
 * Following Google JSON Style Guide
 *
 * https://google.github.io/styleguide/jsoncstyleguide.xml
 *
 * Using @JsonInclude(JsonInclude.Include.NON_EMPTY) as per Google JSON Style Guide rule: Consider removing empty or
 * null values.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class UserDto(val cardNumber: String, val points: Int) {
}