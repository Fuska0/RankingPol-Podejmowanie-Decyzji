package backend.RankingPol;

public class Weights {
    int id;
    double result;
    String name;
    String type;
    int surveyId;

    public Weights(int id, double result, String name, String type, int surveyId){
        this.id = id;
        this.result = result;
        this.name = name;
        this.type = type;
        this.surveyId = surveyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSurveyId() {
        return surveyId;
    }
}
