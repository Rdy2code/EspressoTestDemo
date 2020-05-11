package com.sqisland.android.espresso.cat_names

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IdeasActivityTest {
    @Rule @JvmField
    //Use this three-argument constructor to prevent the activity from being launched before the test
    var activityRule = ActivityTestRule(IdeasActivity::class.java, true, false)

    //Check IdeasActivity text display when no intent is received from MainActivity
    @Test
    fun noTheme() {
        activityRule.launchActivity(null)
        onView(withId(R.id.theme)).check(matches(withText(R.string.missing_theme)))
    }

    //Test when IdeasActivity receives an intent with string extra
    @Test
    fun intentThemePunny() {
        //Need context to get string resources
        //Set up the intent with extra as usual
        //Launch the activity with the intent. The activity unpacks the intent extra and sets the display
        //Check the text in the view in the UI
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val theme = context.getString(R.string.theme_punny)
        val intent = Intent()
        intent.putExtra(IdeasActivity.KEY_THEME, theme)
        activityRule.launchActivity(intent)
        onView(withId(R.id.theme)).check(matches(withText(theme)))
    }

    //Check display when an unknown theme is passed in the intent
    @Test
    fun unKnownTheme() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val theme = "Unknown"
        val intent = Intent()
        intent.putExtra(IdeasActivity.KEY_THEME, theme)
        activityRule.launchActivity(intent)
        val message = context.getString(R.string.unknown_theme, theme)
        onView(withId(R.id.theme)).check(matches(withText(message)))
    }
}