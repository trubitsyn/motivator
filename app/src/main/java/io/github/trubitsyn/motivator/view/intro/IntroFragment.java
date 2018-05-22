package io.github.trubitsyn.motivator.view.intro;

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
import io.github.trubitsyn.motivator.Motivator;
import io.github.trubitsyn.motivator.R;
import io.github.trubitsyn.motivator.model.Preferences;

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
