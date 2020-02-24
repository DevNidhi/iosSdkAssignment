package com.gamechange.assignment.data.provider

import android.content.Context

const val COMMENT_URL = "COMMENT_URL"
const val ISSUE_URL = "ISSUE_URL"

class DataProviderImpl(mContext: Context) : PreferenceProvider(mContext),DataProvider {
    override fun getIssueUrl(): String {
        var issueURL = preferences.getString(ISSUE_URL,"Default")
        if(issueURL == null) {
            issueURL = ""
        }
        return issueURL
    }

    override fun setIssueURL(url: String) {
        preferences.edit().putString(ISSUE_URL,url).apply()
    }

    override fun setCommentURL(url: String) {
        preferences.edit().putString(COMMENT_URL,url).apply()
    }

    override fun getCommentUrl(): String {
        var commentUrl = preferences.getString(COMMENT_URL,"Default")
        if(commentUrl == null) {
            commentUrl = ""
        }
        return commentUrl
    }
}