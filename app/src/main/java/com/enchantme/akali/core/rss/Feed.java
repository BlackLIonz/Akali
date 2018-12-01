package com.enchantme.akali.core.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "rss", strict = false)
public class Feed implements Serializable {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public Feed() {
    }

    public Feed(Channel channel) {
        this.channel = channel;
    }
}