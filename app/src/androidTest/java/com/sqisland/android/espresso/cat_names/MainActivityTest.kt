package com.sqisland.android.espresso.cat_names

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    var activityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun punnyLaunchActivity() {
        onView(withId(R.id.button_punny)).perform(click())
        onView(withId(R.id.theme)).check(matches(withText(R.string.theme_punny)))
    }

    @Test
    fun punnyIntended() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val theme = context.getString(R.string.theme_punny)
        onView(withId(R.id.button_punny)).perform(click())
        Intents.intended(hasExtra(IdeasActivity.KEY_THEME, theme))

    }

    @Test
    fun punnyIntendedClass() {
        onView(withId(R.id.button_punny)).perform(click())
        Intents.intended(hasComponent(IdeasActivity::class.java.name))
        Intents.intended(IntentMatchers.hasComponent(
                ComponentNameMatchers.hasClassName(
                        "com.sqisland.android.espresso.cat_names.IdeasActivity")
        ))
    }

    //This tests that we get the result back from the IdeasActivity that we expect. This lets
    //you check the logic in startActivityForResult, allowing you to stub in an intent with the
    //extra you expect will be returned
    @Test
    fun punnyIntending() {
        val name = "Catalie Portman"
        val intent = Intent()
        intent.putExtra(IdeasActivity.KEY_NAME, name)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, intent)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val theme = context.getString(R.string.theme_punny)
        //This makes Espresso intercept the launch of the activity to check the intent
        Intents.intending(hasExtra(IdeasActivity.KEY_THEME, theme)).respondWith(result)
        onView(withId(R.id.button_punny)).perform(click())
        onView(withId(R.id.name)).check(matches(withText(name)))
    }
}