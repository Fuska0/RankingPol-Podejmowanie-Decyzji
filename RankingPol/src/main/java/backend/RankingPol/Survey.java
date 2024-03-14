package backend.RankingPol;

// Klasa reprezentująca ankietę
public class Survey {
    private int id;
    private String name;

    public Survey(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
