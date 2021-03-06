package com.ccjeng.iwish.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccjeng.iwish.R;
import com.ccjeng.iwish.model.Story;
import com.ccjeng.iwish.view.adapter.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by andycheng on 2016/7/1.
 */
public class StoryItemListAdapter extends RecyclerView.Adapter<StoryItemListAdapter.CustomViewHolder>
        implements ItemTouchHelperAdapter {

    private final static String TAG = StoryItemListAdapter.class.getSimpleName();

    private OnItemClickListener onItemClickListener;

    private List<Story> mStories;
    private int fontSize;

    public StoryItemListAdapter(List<Story> stories, int fontSize) {
        this.mStories = stories;
        this.fontSize = fontSize;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_main, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        String title = mStories.get(position).getName().split("\r\n|\r|\n")[0];

        holder.tvName.setText(title);
        holder.tvAddition.setText(mStories.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mStories, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mStories, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        //categories.remove(position);
        notifyItemRemoved(position);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final View view;
        public final TextView tvName;
        public final TextView tvAddition;


        public CustomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            view = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize/2);
            tvName.setGravity(Gravity.START);
            tvName.setMinLines(1);
            tvAddition = (TextView) itemView.findViewById(R.id.tv_addition);
            tvAddition.setVisibility(View.VISIBLE);

        }


        @Override
        public void onClick(View v) {
            Story story = mStories.get(getAdapterPosition());
            onItemClickListener.onItemClick(getAdapterPosition(), story.getId(), story.getName());
        }


    }

    public interface OnItemClickListener{
        void onItemClick(int position, String id, String name);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
