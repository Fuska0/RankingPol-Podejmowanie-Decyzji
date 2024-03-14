package backend.RankingPol;

public class LoginData {
    private String mail;
    private String password;


    // Konstruktor
    public LoginData() {
    }

    // Getter i Setter dla pola 'username'
    public String getMail() {
        return mail;
    }

    public void setUsername(String username) {
        this.mail = username;
    }

    // Getter i Setter dla pola 'password'
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
