package com.gamechange.assignment.data

import org.threeten.bp.ZonedDateTime

data class CurrentIssues(
    val currentIssues: List<Issue>
){
    val zonedDateTime: ZonedDateTime
        get() {
            return ZonedDateTime.now()
        }
}