package me.androidbox.customedittext

import ru.whalemare.rxvalidator.ValidateRule

class NotEmptyRule : ValidateRule {
    override fun errorMessage(): String {
        return "Should not be empty"
    }

    override fun validate(data: String?): Boolean {
        return !data.isNullOrEmpty()
    }
}
