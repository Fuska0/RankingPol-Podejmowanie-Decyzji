package backend.RankingPol;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportToJson {

    public void export() {
        List<DataObject> dataList = getData(); // Pobierz dane z bazy danych
        exportToJson(dataList, "Answers.json"); // Eksportuj dane do pliku JSON
    }

    public List<DataObject> getData() {
        List<DataObject> dataList = new ArrayList<>();
        String sql = "SELECT * FROM `Answers` WHERE 1";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    int FirstId= resultSet.getInt("FirstId");
                    int SecondId= resultSet.getInt("SecondId");
                    int Scale= resultSet.getInt("Scale");
                    int SurveyID= resultSet.getInt("SurveyID");
                    int UsersCount= resultSet.getInt("UsersCount");
                    String Type = resultSet.getString("Type");
                    String username= resultSet.getString("username");
                    DataObject dataObject = new DataObject(ID, FirstId, SecondId, Scale, SurveyID, UsersCount, Type, username);
                    dataList.add(dataObject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void exportToJson(List<DataObject> dataList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Inicjalizacja obiektu ObjectMapper z Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write("[");

            for (int i = 0; i < dataList.size()-1; i++) {
                // Konwersja obiektu do JSON
                String jsonData = objectMapper.writeValueAsString(dataList.get(i));
                // Zapisz dane do pliku, każda dana w nowej linii
                writer.write(jsonData);
                writer.write(",");
                writer.newLine();
            }
            String jsonData = objectMapper.writeValueAsString(dataList.get(dataList.size()-1));
            // Zapisz dane do pliku, każda dana w nowej linii
            writer.write(jsonData);
            writer.write("]");
            System.out.println("Dane zostały pomyślnie wyeksportowane do pliku: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
