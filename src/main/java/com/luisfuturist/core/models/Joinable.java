package com.luisfuturist.core.models;

public interface Joinable {
    
    public void onJoin(User user);
    public void onLeave(User user);

}
