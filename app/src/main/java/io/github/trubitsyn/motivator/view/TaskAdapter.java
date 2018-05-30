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

package io.github.trubitsyn.motivator.view;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.trubitsyn.motivator.R;
import io.github.trubitsyn.motivator.model.Task;
import io.github.trubitsyn.motivator.presenter.MainPresenter;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> implements ItemTouchHelperAdapter {
    private final DatabaseConnectionCallback dbCallback;
    private final EditCallback editCallback;
    private List<Task> tasks;
    private Set<Task> tasksWithOptionsOpen;
    private MainPresenter presenter;

    public TaskAdapter(DatabaseConnectionCallback dbCallback, EditCallback editCallback, MainPresenter presenter) {
        this.tasks = new ArrayList<>();
        tasksWithOptionsOpen = new HashSet<>();
        this.dbCallback = dbCallback;
        this.editCallback = editCallback;
        this.presenter = presenter;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        final Task task = tasks.get(position);
        holder.title.setText(task.getTitle());

        if (task.isActive()) {
            holder.pause.setVisibility(View.VISIBLE);
            holder.resume.setVisibility(View.GONE);
            holder.pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSwiped(holder, ItemTouchHelper.RIGHT);
                    task.setActive(false);
                    presenter.onPauseButtonClicked(task);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        } else {
            holder.taskView.setAlpha(0.5f);
            holder.optionsView.setAlpha(0.5f);
            holder.resume.setVisibility(View.VISIBLE);
            holder.pause.setVisibility(View.GONE);
            holder.resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSwiped(holder, ItemTouchHelper.RIGHT);
                    task.setActive(true);
                    holder.taskView.setAlpha(1f);
                    holder.optionsView.setAlpha(1f);
                    presenter.onResumeButtonClicked(task);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCallback.onEdit(task);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSwiped(holder, ItemTouchHelper.RIGHT);
                tasks.remove(task);
                dbCallback.removeItem(task);
            }
        });

        Resources resources = holder.itemView.getContext().getResources();
        int daysActive = task.daysActive();
        String daysActiveText = resources.getString(R.string.days_active, daysActive, resources.getQuantityString(R.plurals.days, daysActive));
        holder.daysActive.setText(daysActiveText);

        if (tasksWithOptionsOpen.contains(task)) {
            int height = holder.taskView.getHeight();
            holder.taskView.setVisibility(View.GONE);
            holder.optionsView.setVisibility(View.VISIBLE);
            holder.optionsView.setMinimumHeight(height);
        } else {
            holder.taskView.setVisibility(View.VISIBLE);
            holder.optionsView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public int allowedDirections(RecyclerView.ViewHolder holder) {
        TaskViewHolder vh = (TaskViewHolder) holder;
        if (vh.taskView.getVisibility() == View.VISIBLE) {
            return ItemTouchHelper.LEFT;
        }
        return ItemTouchHelper.RIGHT;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
        // TODO: Cleanup
        // Task removal when calling this method is confusing, because if task object was modified,
        // behaviour would be wrong

        if (direction == ItemTouchHelper.LEFT) {
            tasksWithOptionsOpen.add(tasks.get(holder.getAdapterPosition()));
            notifyDataSetChanged();
        } else if (direction == ItemTouchHelper.RIGHT) {
            tasksWithOptionsOpen.remove(tasks.get(holder.getAdapterPosition()));
            notifyDataSetChanged();
        }
    }

    public void invalidateOptionsViews() {
        tasksWithOptionsOpen.clear();
        notifyDataSetChanged();
    }
}
