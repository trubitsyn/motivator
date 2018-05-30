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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.trubitsyn.motivator.R;
import io.github.trubitsyn.motivator.model.Task;

public class TaskActivity extends AppCompatActivity {
    public static final String INTENT_PARCELABLE_TASK = "task";
    @BindView(R.id.titleInput) TextInputEditText titleInput;
    @BindView(R.id.frequencySelector) AppCompatSpinner frequencySelector;
    private Task task;
    private boolean editMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        Task task = getIntent().getParcelableExtra(INTENT_PARCELABLE_TASK);

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.frequency_values, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySelector.setAdapter(spinnerAdapter);

        if (task != null) {
            this.task = task;
            titleInput.setText(task.getTitle());
            frequencySelector.setSelection(task.getFrequency());
            editMode = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.apply:
                saveTaskInIntent();
                finish();
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTaskInIntent() {
        String title = titleInput.getText().toString();
        Intent intent = new Intent();

        if (editMode) {
            task.setTitle(title);
            task.setFrequency(frequencySelector.getSelectedItemPosition());
        } else {
            task = new Task(title, new Date().getTime(), frequencySelector.getSelectedItemPosition(), true);
        }

        intent.putExtra(INTENT_PARCELABLE_TASK, task);
        setResult(RESULT_OK, intent);
    }
}
