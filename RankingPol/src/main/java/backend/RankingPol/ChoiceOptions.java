package backend.RankingPol;

public class ChoiceOptions {
    private String type;
    private int id;
    private String question;

    // Konstruktor
    public ChoiceOptions(String type, int id, String question) {
        this.type = type;
        this.id = id;
        this.question = question;
    }

    // Getter i Setter dla pola 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter i Setter dla pola 'question'
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
