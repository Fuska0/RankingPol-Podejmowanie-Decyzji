package backend.RankingPol;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationData registrationData) {
        // Użyj RegistrationDAO do zapisu danych rejestracyjnych do bazy danych
        if (RegistrationDAO.isEmailUnique(registrationData.getEmail())) {
            // Jeśli email jest unikalny, wykonaj rejestrację
            boolean registrationSuccess = RegistrationDAO.registerUser(registrationData);

            if (registrationSuccess) {
                return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration failed!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Jeśli email już istnieje, zwróć informację o niepowodzeniu rejestracji
            return new ResponseEntity<>("Email already exists. Registration failed!", HttpStatus.BAD_REQUEST);
        }
    }
}
