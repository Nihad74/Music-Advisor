package org.example;

public class Authenticator  {
        private final String auth_link = "https://accounts.spotify.com/authorize?client_id=607396c3701b4c6197129bcabb2205b2&redirect_uri=http://localhost:8080&response_type=code";
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

        public String getAuth_link() {
            return auth_link;
        }
}
