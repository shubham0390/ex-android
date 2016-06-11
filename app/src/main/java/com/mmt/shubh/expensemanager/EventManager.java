package com.mmt.shubh.expensemanager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by subhamtyagi on 4/3/16.
 */
public class EventManager {

    private static EventManager eventManager = new EventManager();

    private Bus mainBus = new Bus(ThreadEnforcer.MAIN);

    private Bus anyBus = new Bus(ThreadEnforcer.ANY);

    private EventManager() {
    }

    public static void registerMain(Object object) {
        eventManager.mainBus.register(object);
    }

    public static void unRegisterMain(Object object) {
        eventManager.mainBus.unregister(object);
    }

    public static void registerAny(Object object) {
        eventManager.anyBus.register(object);
    }

    public static void unRegisterAny(Object object) {
        eventManager.anyBus.unregister(object);
    }

    public static Bus getMainBus() {
        return eventManager.mainBus;
    }

    public static Bus getAnyBus() {
        return eventManager.anyBus;
    }
}
