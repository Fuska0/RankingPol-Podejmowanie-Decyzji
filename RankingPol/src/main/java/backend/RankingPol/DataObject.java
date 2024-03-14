package backend.RankingPol;

public class DataObject {

    int ID;
    int FirstId;
    int SecondId;
    int Scale;
    int SurveyID;
    int UsersCount;
    String Type;
    String username;

    public DataObject(int ID, int firstId, int secondId, int scale, int surveyID, int usersCount, String type, String username) {
        this.ID = ID;
        FirstId = firstId;
        SecondId = secondId;
        Scale = scale;
        SurveyID = surveyID;
        UsersCount = usersCount;
        Type = type;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFirstId() {
        return FirstId;
    }

    public void setFirstId(int firstId) {
        FirstId = firstId;
    }

    public int getSecondId() {
        return SecondId;
    }

    public void setSecondId(int secondId) {
        SecondId = secondId;
    }

    public int getScale() {
        return Scale;
    }

    public void setScale(int scale) {
        Scale = scale;
    }

    public int getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(int surveyID) {
        SurveyID = surveyID;
    }

    public int getUsersCount() {
        return UsersCount;
    }

    public void setUsersCount(int usersCount) {
        UsersCount = usersCount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
