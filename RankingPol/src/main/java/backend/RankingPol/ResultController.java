package backend.RankingPol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api")
public class ResultController  {
    int selectedAnswerId;
    @GetMapping("/get-results")
    public ResponseEntity<List<Survey>> getResults() {
        List<Survey> surveyList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Survey";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");

                        Survey survey = new Survey(id, name);
                        surveyList.add(survey);
                    }
                }
            }

            return ResponseEntity.ok(surveyList);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/submit-selected-answer")
    public ResponseEntity<String> submitSelectedAnswer(@RequestBody int request) {
        try {
            selectedAnswerId = request;

            return ResponseEntity.ok("Selected answer submitted successfully!");
        } catch (Exception e) {
            // W przypadku błędu zwracamy odpowiedni status HTTP i komunikat błędu
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting selected answer: " + e.getMessage());
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Scores>> getRanking(Integer resultId) {
        try {

            List<Scores> rankingList = retrieveRankingFromDatabase(resultId);

            return ResponseEntity.ok(rankingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    private List<Scores> retrieveRankingFromDatabase(int resultId) {
        List<Scores> res = new ArrayList<>();

        String sql = "SELECT name,result FROM Weights WHERE surveyId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(resultId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    res.add(new Scores(resultSet.getString("name"), resultSet.getDouble("result")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return res;
    }

}
