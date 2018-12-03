package com.enchantme.akali.core.rss;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.enchantme.akali.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    //region Variables

    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    private WebView webView;

    //endregion

    //region Constructors

    public FeedAdapter(Context context, Feed feed, WebView webView) {
        this.inflater = LayoutInflater.from(context);
        feedItems = feed.getChannel().getFeedItems();
        this.webView = webView;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        holder.feedItemTitle.setText(feedItem.getTitle());
        holder.feedItemDescription.setText(feedItem.getDescription());
        holder.feedItemPubDate.setText(feedItem.getPubDate());
        holder.link = feedItem.getLink();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Akali", "onClick: " + holder.link);
                webView.loadUrl(holder.link);
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    //endregion

    //region Classes

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView feedItemTitle;
        final TextView feedItemDescription;
        final TextView feedItemPubDate;
        String link;
        ViewHolder(View view){
            super(view);
            feedItemTitle = view.findViewById(R.id.feed_item_title);
            feedItemDescription = view.findViewById(R.id.feed_item_description);
            feedItemPubDate = view.findViewById(R.id.feed_item_pubDate);
        }
    }

    //endregion
}
