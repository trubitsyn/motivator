package io.github.trubitsyn.motivator;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.trubitsyn.motivator.view.TaskActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TaskActivityTest {

    @Rule
    public ActivityTestRule<TaskActivity> activityTestRule = new ActivityTestRule<>(TaskActivity.class);

    @Test
    public void applyButtonVisible() {
        onView(withId(R.id.apply)).check(matches(isDisplayed()));
    }

    @Test
    public void applyButtonClickable() {
        onView(withId(R.id.apply)).check(matches(isClickable()));
    }

    @Test
    public void titleEditTextVisible() {
        onView(withId(R.id.titleInput)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void titleEditTextIsEmpty() {
        onView(withId(R.id.titleInput)).check(matches(ViewMatchers.withText(Matchers.isEmptyString())));
    }

    @Test
    public void titleEditTextAcceptsInput() {
        String text = "Task title";
        onView(withId(R.id.titleInput)).perform(ViewActions.typeText(text)).check(matches(ViewMatchers.withText(text)));
    }

    @Test
    public void clickingBackActionBarButtonBringsBackTasksView() {
        onView(withId(android.R.id.home)).perform(click());
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
    }
}
