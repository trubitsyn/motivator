package io.github.trubitsyn.motivator.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.trubitsyn.motivator.R;

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
