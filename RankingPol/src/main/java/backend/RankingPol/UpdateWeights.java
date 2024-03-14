package backend.RankingPol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class UpdateWeights {

    private int surveyId;
    private String scaleType;
    public UpdateWeights(int surveyId, String scaleType){
        this.surveyId = surveyId;
        this.scaleType = scaleType;
    }

    public void UpdateNewsWeights(){
        CreateAnswersMatrix createAnswersMatrix = new CreateAnswersMatrix(surveyId);
        double[][] mAlternatives = createAnswersMatrix.createMatrix("a");
        double[][] mCriterions = createAnswersMatrix.createMatrix("c");
        Map<Integer,String> alternativesMap = createAnswersMatrix.getAlternativesNames();
        Map<Integer,String> criterionsMap = createAnswersMatrix.getCriterionsNames();
        EVMethod evMethod = new EVMethod();
        double[] alternativesWeights = evMethod.calculateEvmWeights(mAlternatives,scaleType);
        double[] criterionsWeights = evMethod.calculateEvmWeights(mCriterions,scaleType);
        List<Weights> res = new ArrayList<>();
        int id = findMaxId() + 1;
        int i = 0;
        for (String name : alternativesMap.values()) {
            res.add(new Weights(id,alternativesWeights[i],name,"a",surveyId));
            id++;
            i++;
        }
        i = 0;
        for (String name : criterionsMap.values()) {
            res.add(new Weights(id,criterionsWeights[i],name,"c",surveyId));
            id++;
            i++;
        }

        deleteOldWeights();
        insertWeights(res);
    }

    public int findMaxId(){
        int maxId = 0;
        String sql = "SELECT MAX(id) AS MaxId FROM Weights";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxId = resultSet.getInt("MaxId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    public void deleteOldWeights() {
        String sql = "DELETE FROM Weights WHERE SurveyID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, surveyId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insertWeights(List<Weights> weights) {

        for(int i = 0; i < weights.size(); i++){
            String sql = "INSERT INTO Weights (id, result, name, type, surveyId) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, weights.get(i).getId());
                preparedStatement.setDouble(2, weights.get(i).getResult());
                preparedStatement.setString(3, weights.get(i).getName());
                preparedStatement.setString(4, weights.get(i).getType());
                preparedStatement.setInt(5, weights.get(i).getSurveyId());

                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
