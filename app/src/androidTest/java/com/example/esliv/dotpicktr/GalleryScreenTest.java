package com.example.esliv.dotpicktr;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.esliv.dotpicktr.activities.GalleryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by esliv on 23/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class GalleryScreenTest {

    @Rule
    public ActivityTestRule<GalleryActivity> mGalleryActivityActivityTestRule = new ActivityTestRule<GalleryActivity>(GalleryActivity.class);


    @Test
    public void clickAddBoardButton_opensAddBoardUi() throws Exception {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.select_dialog_listview)).check(matches(isDisplayed()));
    }

    @Test
    public void click16x16Board_opensBoardUi()throws Exception {
        onView(withId(R.id.fab)).perform(click());
        onData(hasToString(startsWith("16"))).perform(click());
        onView(withId(R.id.board)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFirstItemInGallery_opensBoardUI()throws Exception {
        onView(withId(R.id.gallery)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click())
        );
        onView(withId(R.id.board)).check(matches(isDisplayed()));

    }


}
