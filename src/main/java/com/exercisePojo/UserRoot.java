package com.exercisePojo;

public class UserRoot {
    User user;
    public UserRoot(){}
    public UserRoot(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
