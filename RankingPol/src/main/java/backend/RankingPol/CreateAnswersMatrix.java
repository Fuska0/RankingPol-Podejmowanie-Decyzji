package backend.RankingPol;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAnswersMatrix {
    private int surveyId;
    public CreateAnswersMatrix(int surveyId){
        this.surveyId = surveyId;
    }

    public Map<Integer,Integer> getAlternatives(){
        Map<Integer, Integer> result = new HashMap<>();
        String sql = "SELECT id FROM Alternatives WHERE survey_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(surveyId));

            int i = 0;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.put(resultSet.getInt("id"), i);
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public Map<Integer,Integer> getCriterions(){
        Map<Integer, Integer> result = new HashMap<>();
        String sql = "SELECT id FROM Criterions WHERE SurveyId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(surveyId));

            int i = 0;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())  {
                    result.put(resultSet.getInt("id"), i);
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    public double[][] createMatrix(String type){
        Map<Integer, Integer> answers;
        if(Objects.equals(type, "a")) answers = getAlternatives();
        else answers = getCriterions();

        int n = answers.size();
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               result[i][j] = 1.0;
            }
        }

        String sql = "SELECT FirstId,SecondId,Scale FROM Answers WHERE SurveyID = ? and Type = ? ";
        int first = 0;
        int second = 0;
        int scale = 0;
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, surveyId);
            preparedStatement.setString(2, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if(answers.containsKey(resultSet.getInt("FirstId")) &&
                            answers.containsKey(resultSet.getInt("SecondId"))) {

                        first = answers.get(resultSet.getInt("FirstId"));
                        second = answers.get(resultSet.getInt("SecondId"));
                        scale = resultSet.getInt("Scale");

                        result[first][second] *= scale;

                        result[second][first] *= (double) 1/scale;

                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int maxUsersCount = findMaxUser(type);
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++ ){
                result[i][j] = Math.pow(result[i][j], 1.0 / maxUsersCount);
                BigDecimal bigDecimal = new BigDecimal(result[i][j]);
                bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                result[i][j] = bigDecimal.doubleValue();
            }
        }

        return result;
    }

    public int findMaxUser(String type){
        int maxUsersCount = 1;
        String sql = "SELECT MAX(UsersCount) AS MaxUsersCount FROM Answers WHERE SurveyID = ? and Type = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, surveyId);
            preparedStatement.setString(2, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxUsersCount = resultSet.getInt("MaxUsersCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxUsersCount;
    }

    public Map<Integer,String> getAlternativesNames(){
        Map<Integer, String> result = new HashMap<>();
        String sql = "SELECT id,text FROM Alternatives WHERE survey_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(surveyId));

            int i = 0;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.put(resultSet.getInt("id"), resultSet.getString("text"));
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<Integer,String> getCriterionsNames(){
        Map<Integer, String> result = new HashMap<>();
        String sql = "SELECT id, text FROM Criterions WHERE SurveyId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(surveyId));

            int i = 0;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.put(resultSet.getInt("id"), resultSet.getString("text"));
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
