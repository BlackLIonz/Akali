package com.enchantme.akali.core.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "rss", strict = false)
public class Feed implements Serializable {

    //region Variables
    @Element(name = "channel")
    private Channel channel;
    //endregion

    //region Constructors

    public Feed() {
    }

    //endregion

    //region Public Methods

    public Channel getChannel() {
        return channel;
    }

    public Feed(Channel channel) {
        this.channel = channel;
    }

    //endregion
}