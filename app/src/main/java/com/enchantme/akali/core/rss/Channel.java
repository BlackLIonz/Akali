package com.enchantme.akali.core.rss;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel", strict = false)
public class Channel implements Serializable {


    //region Variables

    @ElementList(inline = true, name="item")
    private List<FeedItem> feedItems;

    //endregion

    //region Constructors

    public Channel() {
    }

    //endregion

    //region Public Methods

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public Channel(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    //endregion
}