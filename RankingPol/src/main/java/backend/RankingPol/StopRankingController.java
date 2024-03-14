package backend.RankingPol;
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

@RestController
@RequestMapping("/api")
public class StopRankingController {
    @PostMapping("/survey")
    public ResponseEntity<String> endSurvey(@RequestBody SurveyID surveyRequest) {
        try {
            Integer surveyId = surveyRequest.getId();

            String sql = "UPDATE Survey SET Active = 0 WHERE Id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, String.valueOf(surveyId));
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new ResponseEntity<>("Survey ended successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to end survey", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


class SurveyID {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
