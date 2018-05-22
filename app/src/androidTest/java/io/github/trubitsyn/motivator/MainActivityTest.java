package io.github.trubitsyn.motivator;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.trubitsyn.motivator.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addButtonDisplayed() {
        onView(withId(R.id.add)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyStateVisible() {
        onView(withId(R.id.emptyState)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void recyclerViewInvisible() {
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())));
    }

    private void createTask(String text) {
        onView(withText(text)).check(doesNotExist());
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.titleInput)).perform(typeText(text));
        onView(withId(R.id.apply)).perform(click());
    }

    @Test
    public void newTaskBecomesVisibleInRecyclerView() {
        String text = "Sample user task";
        createTask(text);
        onView(withText(text)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void twoAndMoreTasksAreVisibleInRecyclerView() {
        String task1 = "Task 1";
        String task2 = "Task 2";
        createTask(task1);
        createTask(task2);

        onView(withText(task1)).check(matches(isCompletelyDisplayed()));
        onView(withText(task2)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipingLeftWithTaskVisibleShowsOptions() {
        createTask("Sample user task");

        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.optionsView)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipingRightWithTaskVisibleDoesNotShowOptions() {
        createTask("Sample user task");

        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void swipingRightWithOptionsVisibleShowsTask() {
        createTask("Sample user task");

        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.optionsView)).check(matches(isDisplayed()));
        onView(withId(R.id.taskView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.taskView)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipingLeftWithOptionsVisibleDoesNotShowTask() {
        createTask("Sample user task");

        onView(withId(R.id.optionsView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.optionsView)).check(matches(isDisplayed()));
        onView(withId(R.id.taskView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.optionsView)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.taskView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void editButtonDisplayedInOptionsView() {
        createTask("Sample user task");

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.edit)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickingEditButtonOpensTaskEditScreen() {
        createTask("Sample user task");

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.edit)).perform(click());
        onView(withId(R.id.activity_task)).check(matches(isDisplayed()));
    }

    @Test
    public void deleteButtonDisplayedInOptionsView() {
        createTask("Sample user task");

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.delete)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void clickingDeleteButtonRemovesTaskFromRecyclerView() {
        String text = "Sample user task";
        createTask(text);

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.delete)).perform(click());
        onView(withText(text)).check(matches(not(isDisplayed())));
    }

    @Test
    public void daysActiveDisplayedInTaskView() {
        createTask("Sample user task");
        onView(withId(R.id.daysActive)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void taskIconDisplayedInTaskView() {
        createTask("Sample user task");
        onView(withId(R.id.icon)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void optionsTipDisplayedInTaskView() {
        createTask("Sample user task");
        onView(withId(R.id.options)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void backTipDisplayedInOptionsView() {
        createTask("Sample user task");
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.back)).check(matches(isCompletelyDisplayed()));
    }
}
