package iceFactory.IceFactoryApplication.model;

public class Account {
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean entryCheck(String username,String password){
        if(this.username.equals(username)) {
            if(this.password.equals(password)) return true;
            else throw new IllegalArgumentException("Wrong password.");
        }
        return false;
    }
}
