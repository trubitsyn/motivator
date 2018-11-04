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

package org.trubitsyn.motivator.view.intro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import org.trubitsyn.motivator.Motivator;
import org.trubitsyn.motivator.R;
import org.trubitsyn.motivator.model.Preferences;

public class IntroFragment extends Fragment {
    @BindView(R.id.fromHour) EditText fromHour;
    @BindView(R.id.tillHour) EditText tillHour;
    private Unbinder unbinder;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void applySettings(Context context) {
        Preferences preferences = Motivator.get(context).getPreferences();
        int from = Integer.valueOf(fromHour.getText().toString());
        int to = Integer.valueOf(tillHour.getText().toString());
        preferences.setIgnoreFromHour(from);
        preferences.setIgnoreToHour(to);
    }
}
