package org.example;

public class Authenticator  {
    private boolean authenticated;

    public Authenticator(){
        authenticated = false;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
