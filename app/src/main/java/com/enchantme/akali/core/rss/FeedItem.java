package com.enchantme.akali.core.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "item", strict = false)
public class FeedItem implements Serializable {
    //region Variables

    @Element(name = "pubDate")
    private String pubDate;
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;

    //endregion

    //region Constructors

    public FeedItem() {
    }

    public FeedItem(String description, String link, String title, String pubDate) {
        this.description = description;
        this.link = link;
        this.title = title;
        this.pubDate = pubDate;
    }

    //endregion

    //region Public Methods

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //endregion
}