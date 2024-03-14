package backend.RankingPol;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class SelectedSurveyController {
    private Integer id;

    @PostMapping("/selected-survey")
    public ResponseEntity<String> selectedSurvey(@RequestBody SelectedSurveyRequest request) {
        int selectedSurveyId = request.getSurveyId();
        // Tutaj możesz dodać logikę obsługi wybranego survey
        id = selectedSurveyId;
        // Na razie zwracamy prostą odpowiedź, ale to można dostosować do własnych potrzeb
        return new ResponseEntity<>("Selected survey with ID: " + selectedSurveyId, HttpStatus.OK);
    }

    @PostMapping("/survey-questions")
    public ResponseEntity<List<ChoiceOptions>> getSurveyQuestions(@RequestBody Map<String, Integer> requestBody) {
        int surveyId = requestBody.get("survey_id");

        List<ChoiceOptions> surveyQuestions = new ArrayList<>();
        String order = findOrder(surveyId);
        int flag;
        if(Objects.equals(order, "CriteriaFirst")) flag = 1;
        else if (Objects.equals(order, "AlternativesFirst")) flag = 0;
        else {
            Random random = new Random();
            flag = random.nextInt(2);
        }

        if (flag == 0){
            getAlternatives(surveyId,surveyQuestions);
            getCriterions(surveyId,surveyQuestions);
        }
        else {
            getCriterions(surveyId,surveyQuestions);
            getAlternatives(surveyId,surveyQuestions);
        }

        return new ResponseEntity<>(surveyQuestions, HttpStatus.OK);
    }

    public String findOrder(int surveyId){
        String order = "";
        String sql = "SELECT QuestionOrder FROM Rankings Where SurveyID = ?;";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             preparedStatement.setInt(1, surveyId);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = resultSet.getString("QuestionOrder");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void getAlternatives(int surveyId, List<ChoiceOptions> res){

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id, text FROM Alternatives WHERE survey_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, surveyId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int aternativeId = resultSet.getInt("id");
                        String aternativesText = resultSet.getString("text");
                        ChoiceOptions questions = new ChoiceOptions("a", aternativeId, aternativesText);
                        res.add(questions);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getCriterions(int surveyId,List<ChoiceOptions> res){

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query =  "SELECT id, text FROM Criterions WHERE SurveyId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, surveyId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int criterionId = resultSet.getInt("id");
                        String criterionText = resultSet.getString("text");
                        ChoiceOptions questions = new ChoiceOptions("c", criterionId, criterionText);
                        res.add(questions);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
