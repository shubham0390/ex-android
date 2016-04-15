package com.mmt.shubh.expensemanager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by subhamtyagi on 4/3/16.
 */
public class EventManager {

    private EventManager eventManager =  new EventManager();

    private Bus mainBus = new Bus(ThreadEnforcer.MAIN);

    private Bus anyBus = new Bus(ThreadEnforcer.ANY);

    private EventManager() {
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void registerMain(Object object){
        mainBus.register(object);
    }

    public void unRegisterMain(Object object){
        mainBus.unregister(object);
    }

    public void registerAny(Object object){
        anyBus.register(object);
    }

    public void unRegisterAny(Object object){
        anyBus.unregister(object);
    }

    public Bus getMainBus(){
        return mainBus;
    }

    public Bus getAnyBus(){
        return anyBus;
    }
}
