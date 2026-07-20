package com.zhanan.variablemanage;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class VariableChangeEvent extends Event {
    private static final HandlerList handlers = HandlerList.getHandlerList();

    private final String key;
    private final String oldValue;
    private final String newValue;

    public VariableChangeEvent(String key, String oldValue, String newValue) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
