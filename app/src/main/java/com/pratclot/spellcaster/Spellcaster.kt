package com.pratclot.spellcaster

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

const val TAG = "Spellcaster Accessibility Service"

val BANNED = arrayOf("dirty", "word", "not allowed")
const val SAFE_WORD = "saint"

class Spellcaster : AccessibilityService() {
    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        event?.source?.text?.toString()?.let { originalText ->
            var safeText: String = originalText

            BANNED.map {
                safeText = safeText.replace(it, SAFE_WORD)
            }

            val arguments = Bundle()
            arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                safeText
            )
            event.source.apply {
                performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
            }
        }
    }
}
