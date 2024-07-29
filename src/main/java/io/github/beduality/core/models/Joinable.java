package io.github.beduality.core.models;

public interface Joinable {
    
    public void onJoin(User user);
    public void onLeave(User user);

}
