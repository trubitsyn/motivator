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

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.trubitsyn.motivator.view.ItemTouchHelperAdapter;
import org.trubitsyn.motivator.view.ItemTouchHelperCustomCallback;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemTouchHelperCustomCallbackTest {
    private ItemTouchHelperCustomCallback callback;
    private ItemTouchHelperAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.ViewHolder viewHolder;

    @Before
    public void setUp() {
        adapter = mock(ItemTouchHelperAdapter.class);
        viewHolder = mock(RecyclerView.ViewHolder.class);
        recyclerView = mock(RecyclerView.class);
        callback = new ItemTouchHelperCustomCallback(adapter);
    }

    @After
    public void tearDown() {
        callback = null;
        adapter = null;
    }

    @Test
    public void makeMovementFlagsPotentiallyUsesDirectionsFromAdapter() {
        int allowedDirections = 1024;
        when(adapter.allowedDirections(viewHolder)).thenReturn(allowedDirections);
        int expected = ItemTouchHelper.Callback.makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, allowedDirections);
        int actual = callback.getMovementFlags(recyclerView, viewHolder);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeMovementFlagsCallsAdapterMethod() {
        callback.getMovementFlags(recyclerView, viewHolder);
        verify(adapter).allowedDirections(viewHolder);
    }

    @Test
    public void moveNotAllowed() {
        RecyclerView.ViewHolder target = mock(RecyclerView.ViewHolder.class);
        Assert.assertFalse(callback.onMove(recyclerView, viewHolder, target));
    }

    @Test
    public void onSwipedCallsAdapterOnSwiped() {
        int direction = 1024;
        callback.onSwiped(viewHolder, direction);
        verify(adapter).onSwiped(viewHolder, direction);
    }
}
