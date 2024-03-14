package backend.RankingPol;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {

        Map<String, String> credentials = checkCredentialsInDatabase(loginData.get("mail"), loginData.get("password"));
        if (!credentials.isEmpty()) {
            return new ResponseEntity<>(credentials, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, String> checkCredentialsInDatabase(String username, String password) {
        Map<String, String> result = new HashMap<>();
        String sql = "SELECT user_type, email, password FROM user WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result.put("user_type", resultSet.getString("user_type"));
                    result.put("email", resultSet.getString("email"));
                    result.put("password", resultSet.getString("password"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
