package backend.RankingPol;

public class RankingRequest {
    private String[] alternatives;
    private String[] criteria;
    private String scale;
    private String questionOrder;
    private String rankingName;

    // Getters and setters

    public String[] getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(String[] alternatives) {
        this.alternatives = alternatives;
    }

    public String[] getCriteria() {
        return criteria;
    }

    public void setCriteria(String[] criteria) {
        this.criteria = criteria;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(String questionOrder) {
        this.questionOrder = questionOrder;
    }

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }
}
