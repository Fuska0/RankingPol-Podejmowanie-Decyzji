package backend.RankingPol;

public class RegistrationData {
    private String email;
    private String password;
    private String userType;

    // Konstruktor
    public RegistrationData() {
    }

    // Getter i Setter dla pola 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter i Setter dla pola 'password'
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter i Setter dla pola 'userType'
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

