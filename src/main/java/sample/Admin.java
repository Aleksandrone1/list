package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin {
    @FXML
    private TextField login;
    @FXML
    private TextField pass;
    @FXML
    private TextField rol;
    @FXML
    private Button save;

    @FXML
    void initialize() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        save.setOnAction(event -> {
            String s = ("INSERT INTO users (id_roles,login_user,pass) values(?,?,?)");
            int i = Integer.parseInt(rol.getText());
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb", "sa", "1111");
                 PreparedStatement preparedStatement = connection1.prepareStatement(s)) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, login.getText());
                preparedStatement.setString(3, pass.getText());
                preparedStatement.execute();
                connection1.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        });
    }
}
