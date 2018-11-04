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

package org.trubitsyn.motivator.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import org.trubitsyn.motivator.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.daysActive) TextView daysActive;
    @BindView(R.id.options) ImageView options;
    @BindView(R.id.taskView) View taskView;
    @BindView(R.id.optionsView) View optionsView;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.edit) ImageView edit;
    @BindView(R.id.resume) ImageView resume;
    @BindView(R.id.pause) ImageView pause;
    @BindView(R.id.delete) ImageView delete;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
