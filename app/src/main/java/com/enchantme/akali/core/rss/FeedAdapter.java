package com.enchantme.akali.core.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enchantme.akali.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    //region Variables

    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    //endregion

    //region Constructors

    public FeedAdapter(Context context, Feed feed) {
        this.inflater = LayoutInflater.from(context);
        feedItems = feed.getChannel().getFeedItems();
    }

    //endregion

    //region Public Methods

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        holder.feedItemTitle.setText(feedItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    //endregion

    //region Classes

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView feedItemTitle;
        ViewHolder(View view){
            super(view);
            feedItemTitle = view.findViewById(R.id.feed_item_title);
        }
    }

    //endregion
}
