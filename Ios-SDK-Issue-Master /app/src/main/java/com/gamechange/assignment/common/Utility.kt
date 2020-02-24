package com.gamechange.assignment.common

import android.util.Patterns

class Utility {

    companion object {
        fun isValidEmail(s: String): Boolean
                = s.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(s).matches()

    }

}