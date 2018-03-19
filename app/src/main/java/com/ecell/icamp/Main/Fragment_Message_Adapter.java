package com.ecell.icamp.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecell.icamp.R;

import java.util.List;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Fragment_Message_Adapter extends RecyclerView.Adapter<Fragment_Message_Adapter.ViewHolder> {

    private LinearLayout co_card;
    private TextView co_title, co_message, co_time;
    private List<String> li_id, li_title, li_message, li_time, li_target;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            co_card = (LinearLayout)view.findViewById(R.id.message_card);
            co_title = (TextView)view.findViewById(R.id.msg_title);
            co_message = (TextView)view.findViewById(R.id.msg_message);
            co_time = (TextView) view.findViewById(R.id.msg_time);
        }
    }

    public Fragment_Message_Adapter(List<String> li_id, List<String> li_title, List<String> li_message, List<String> li_time, List<String> li_target) {
        this.li_id = li_id;
        this.li_title = li_title;
        this.li_message = li_message;
        this.li_time = li_time;
        this.li_target = li_target;
    }

    @Override
    public Fragment_Message_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        co_title.setText(li_title.get(position));
        co_message.setText(li_message.get(position));
        co_time.setText(li_time.get(position));
    }

    @Override
    public int getItemCount() {
        return li_id.size();
       // return 3;
    }
}
