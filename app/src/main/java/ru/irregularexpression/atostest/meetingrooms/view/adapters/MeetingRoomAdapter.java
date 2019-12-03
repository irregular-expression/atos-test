package ru.irregularexpression.atostest.meetingrooms.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.model.data.MeetingRoom;

public class MeetingRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MeetingRoom> data;
    private Context context;
    public PublishSubject<MeetingRoom> publisher;

    public MeetingRoomAdapter(List<MeetingRoom> data, Context context) {
        this.data = data;
        this.context = context;
        this.publisher = PublishSubject.create();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        RecyclerView.ViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_room, parent, false);
        holder = new RoomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final RoomViewHolder roomViewHolder = (RoomViewHolder) viewHolder;
        final MeetingRoom room = data.get(i);
        roomViewHolder.name.setText(context.getString(R.string.room_name_template, room.getName()));
        roomViewHolder.chairCount.setText(context.getString(R.string.room_chair_count_template, room.getChairsCount()));
        roomViewHolder.earliestTime.setText(room.getEarliestTime() == null ? context.getString(R.string.room_free) : room.getEarliestTime());
        roomViewHolder.projector.setVisibility(room.hasProjector() ? View.VISIBLE : View.INVISIBLE);
        roomViewHolder.board.setVisibility(room.hasBoard() ? View.VISIBLE : View.INVISIBLE);
        roomViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisher.onNext(room);
            }
        });
    }

    public void setData(List<MeetingRoom> rooms) {
        this.data = rooms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        private ImageView projector;
        private ImageView board;
        private TextView name;
        private TextView chairCount;
        private TextView earliestTime;

        RoomViewHolder(View itemView) {
            super(itemView);
            projector = itemView.findViewById(R.id.projector);
            board = itemView.findViewById(R.id.board);
            name = itemView.findViewById(R.id.name);
            chairCount = itemView.findViewById(R.id.chair_count);
            earliestTime = itemView.findViewById(R.id.earliest_time);
        }
    }

}
