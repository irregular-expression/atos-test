package ru.irregularexpression.atostest.meetingrooms.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.irregularexpression.atostest.meetingrooms.R;
import ru.irregularexpression.atostest.meetingrooms.model.data.Order;

public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> data;
    private Context context;

    public OrdersAdapter(List<Order> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        RecyclerView.ViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order, parent, false);
        holder = new OrderViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;
        final Order order = data.get(i);
        orderViewHolder.user.setText(order.getUsername().split(" ")[0] + " " + order.getUsername().split(" ")[1]); //TODO: needs refactoring and clearing requirements for username format
        orderViewHolder.date.setText(order.getStringDate());
        orderViewHolder.time.setText(context.getString(R.string.order_time_template, order.getStringTimeStart(), order.getStringTimeEnd()));
    }

    public void setData(List<Order> orders) {
        this.data = orders;
        notifyDataSetChanged();
    }

    public List<Order> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView time;
        private TextView user;

        OrderViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            user = itemView.findViewById(R.id.username);
        }
    }

}
