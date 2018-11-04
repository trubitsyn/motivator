/*
 * Copyright (C) 2016 Nikola Trubitsyn
 *
 * This file is part of Motivator.
 *
 * Motivator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Motivator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Motivator.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.trubitsyn.motivator;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.trubitsyn.motivator.view.TaskActivity;

import org.trubitsyn.motivator.R;

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
