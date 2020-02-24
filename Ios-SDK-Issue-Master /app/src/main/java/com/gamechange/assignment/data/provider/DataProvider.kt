package com.gamechange.assignment.data.provider

interface DataProvider {
    fun getCommentUrl() : String
    fun setCommentURL(url: String)
    fun getIssueUrl() : String
    fun setIssueURL(url: String)
}