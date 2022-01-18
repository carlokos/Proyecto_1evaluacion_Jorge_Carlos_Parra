package Modelo;

public class Usuario {
    String user, pw;

    public Usuario(String usuario, String pw){
        this.user = usuario;
        this.pw = pw;
    }

    public String getUser(){
        return user;
    }

    public String getPw() {
        return pw;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
