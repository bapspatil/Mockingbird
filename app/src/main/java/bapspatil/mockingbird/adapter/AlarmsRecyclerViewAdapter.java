package bapspatil.mockingbird.adapter;
/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.AlarmItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;

public class AlarmsRecyclerViewAdapter extends io.realm.RealmRecyclerViewAdapter<AlarmItem, AlarmsRecyclerViewAdapter.AlarmViewHolder> {
    private AlarmDeletedInterface listener;

    public AlarmsRecyclerViewAdapter(@Nullable OrderedRealmCollection<AlarmItem> data, boolean autoUpdate, boolean updateOnModification, AlarmDeletedInterface listener) {
        super(data, autoUpdate, updateOnModification);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmItem alarmItem = getItem(position);
        if (alarmItem != null) {
            holder.timeTextView.setText(alarmItem.getFriendlyTimeSet());
            holder.deleteImageView.setOnClickListener(v -> listener.alarmDeleted(alarmItem));
        }
    }

    public interface AlarmDeletedInterface {
        void alarmDeleted(AlarmItem alarmItem);
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeTextView)
        TextView timeTextView;
        @BindView(R.id.deleteImageView)
        ImageView deleteImageView;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
