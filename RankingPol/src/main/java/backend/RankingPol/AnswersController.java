package backend.RankingPol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnswersController {

    @PostMapping("/submitResponse")
    public ResponseEntity<String> submitResponse(@RequestBody Map<String, Object> request) {
        Integer selectedSurveyId = (Integer) request.get("id");
        String username = (String) request.get("username");
        Object selectedAnswersObj = request.get("answers");
        int maxId = findMaxId() + 1;
        int maxUser = findMaxUser( selectedSurveyId)+ 1;
        if (selectedAnswersObj instanceof List) {
            ArrayList<Object> selectedAnswers = (ArrayList<Object>) selectedAnswersObj;
            int listSize = selectedAnswers.size();
            for(int i = 0; i < listSize; i++){
                Map<String, Object> tmp = (Map<String, Object>) selectedAnswers.get(i);
                int firstId = (Integer) tmp.get("selectedOption");
                int secondId =  (Integer) tmp.get("notSelected");
                int scale = (Integer) tmp.get("sliderValue");
                String type = (String) tmp.get("type");

                insertAnswer(firstId,secondId,scale,type,selectedSurveyId, maxId, maxUser,username);
                maxId++;

            }
        }

		UpdateWeights updateWeights = new UpdateWeights(selectedSurveyId,findScale(selectedSurveyId));
		updateWeights.UpdateNewsWeights();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public int findMaxId(){
        int maxID = 0;
        String sql = "SELECT MAX(ID) AS MaxID FROM Answers";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxID = resultSet.getInt("MaxID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxID;
    }
    public int findMaxUser(int surveyId){
        int maxUsersCount = 0;
        String sql = "SELECT MAX(UsersCount) AS MaxUsersCount FROM Answers WHERE SurveyID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(surveyId));

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

    private void insertAnswer(int firstId, int secondId, int scale, String type, int surveyId, int newId, int newUsersCount, String username) {
        String sql = "INSERT INTO Answers (ID,FirstId,SecondId,Scale,SurveyID,UsersCount,Type, username) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newId);
            preparedStatement.setInt(2, firstId);
            preparedStatement.setInt(3, secondId);
            preparedStatement.setInt(4, scale);
            preparedStatement.setInt(5,surveyId);
            preparedStatement.setInt(6,newUsersCount);
            preparedStatement.setString(7, type);
            preparedStatement.setString(8, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public String findScale(int surveyId){
        String order = "";
        String sql = "SELECT Scale FROM Rankings Where SurveyID = ?;";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, surveyId);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = resultSet.getString("Scale");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
