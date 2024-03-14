package backend.RankingPol;

public class Answer {

    int selected;
    int notSelected;
    int val;
    String type;

    public Answer(int selected, int notSelected, int val, String type) {
        this.selected = selected;
        this.notSelected = notSelected;
        this.val = val;
        this.type = type;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getNotSelected() {
        return notSelected;
    }

    public void setNotSelected(int notSelected) {
        this.notSelected = notSelected;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
