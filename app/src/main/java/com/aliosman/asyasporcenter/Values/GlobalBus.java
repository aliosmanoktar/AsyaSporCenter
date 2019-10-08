package com.aliosman.asyasporcenter.Values;

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {
    private static EventBus eventBus;

    public static EventBus getBus() {
        if (eventBus == null)
            eventBus = EventBus.getDefault();
        return eventBus;
    }
}
