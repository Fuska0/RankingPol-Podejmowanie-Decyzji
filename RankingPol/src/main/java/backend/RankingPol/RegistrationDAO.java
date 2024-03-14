package backend.RankingPol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO {

    public static boolean registerUser(RegistrationData registrationData) {
        // Sprawdź, czy email jest unikalny
        if (isEmailUnique(registrationData.getEmail())) {
            // Jeśli email jest unikalny, wykonaj rejestrację
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO user (email, password, user_type) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, registrationData.getEmail());
                    preparedStatement.setString(2, registrationData.getPassword());
                    preparedStatement.setString(3, registrationData.getUserType());

                    int affectedRows = preparedStatement.executeUpdate();
                    return affectedRows > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Jeśli email nie jest unikalny, zwróć false
        return false;
    }

    public static boolean isEmailUnique(String email) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 0; // Jeśli count == 0, to email jest unikalny
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // W przypadku błędu zwracamy false
        return false;
    }
}
