package backend.RankingPol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class RankingController {

    @PostMapping("/createRanking")
    public ResponseEntity<ObjectNode> createRanking(@RequestBody RankingRequest rankingRequest) {
        // Obsługa otrzymanych danych

        String[] alternatives = rankingRequest.getAlternatives();
        String[] criteria = rankingRequest.getCriteria();
        String scale = rankingRequest.getScale();
        String order = rankingRequest.getQuestionOrder();
        String name = rankingRequest.getRankingName();

        int surveyId = insertSurvey(name);
        insertRankings(surveyId, scale, order);
        insertAlternatives(surveyId, alternatives);
        insertCriterions(surveyId, criteria);


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("message", "Ranking created successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public int findMaxSurveyID(){
        int maxId = 1;
        String sql = "SELECT MAX(Id) AS MaxId FROM Survey";
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
        return maxId;
    }
    public int findMaxRankingID(){
        int maxId = 1;
        String sql = "SELECT MAX(ID) AS MaxId FROM Rankings";
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
        return maxId;
    }

    public int findMaxCriterionsID(){
        int maxId = 1;
        String sql = "SELECT MAX(id) AS MaxId FROM Criterions";
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
        return maxId;
    }

    public int findMaxAlternativesID(){
        int maxId = 1;
        String sql = "SELECT MAX(id) AS MaxId FROM Alternatives";
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
        return maxId;
    }


    private int insertSurvey(String name) {
        int maxId = findMaxSurveyID();
        int newId = maxId + 1;


        // Tworzenie zapytania SQL do dodania nowego rekordu
        String sql = "INSERT INTO Survey (Id, name, Active) VALUES (?, ?, 1)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, name);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return newId;
            } else {
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void insertRankings(int surveyId, String scale, String order) {
        int maxId = findMaxRankingID();
        int newId = maxId + 1;

        // Tworzenie zapytania SQL do dodania nowego rekordu
        String sql = "INSERT INTO Rankings (ID, SurveyID, Scale, QuestionOrder) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newId);
            preparedStatement.setInt(2, surveyId);
            preparedStatement.setString(3, scale);
            preparedStatement.setString(4, order);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertCriterions(int surveyId, String[] text) {
        int maxId = findMaxCriterionsID();
        int newId = maxId + 1;

        for(int i = 0; i < text.length; i++){
            String sql = "INSERT INTO Criterions (id, text, SurveyId) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, newId);
                preparedStatement.setString(2, text[i]);
                preparedStatement.setInt(3, surveyId);
                preparedStatement.executeUpdate();
                newId++;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void insertAlternatives(int surveyId, String[] text) {
        int maxId = findMaxAlternativesID();
        int newId = maxId + 1;

        for(int i = 0; i < text.length; i++){
            String sql = "INSERT INTO Alternatives (id, survey_id, text ) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, newId);
                preparedStatement.setString(3, text[i]);
                preparedStatement.setInt(2, surveyId);
                preparedStatement.executeUpdate();
                newId++;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}