package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class insertTovar {
    @FXML
    private TextField name;
    @FXML
    private TextField articul;
    @FXML
    private TextField kolichestvo;

    public TextField put;
    @FXML
    public TextField price;
    @FXML
    public Button save;
    @FXML
    public Button save1;
    @FXML
    void initialize() {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (
                    ClassNotFoundException e) {
                e.printStackTrace();
            }
            save.setOnAction(event -> {
                String namet = name.getText();
                double pr = Double.parseDouble(price.getText());
                String ar = articul.getText();
                String photo = put.getText();
                File file=new File(photo);
                int quantity = Integer.parseInt(kolichestvo.getText());String s=
                     " INSERT INTO [dbo].[tovar] ([name_t],[prise],[articul],[photo],[quantity]) VALUES (?,?,?,?,?)" ;
                try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb", "sa", "1111");
                     InputStream is = new FileInputStream(file);
                     PreparedStatement preparedStatement = connection1.prepareStatement(s)){
                    preparedStatement.setString(1, namet);
                    preparedStatement.setDouble(2, pr);
                    preparedStatement.setString(3, ar);
                    preparedStatement.setBinaryStream(4, (InputStream) is,(int) file.length());
                    preparedStatement.setInt(5, quantity);
                    preparedStatement.execute();
                    is.close();
                    connection1.close();
                    preparedStatement.close();
                    Stage stage = (Stage) save.getScene().getWindow();
                    stage.close();
                }
                catch (SQLException  | IOException e) {
                    System.out.println(e);
                }
            });

        save1.setOnAction(event -> {

        });
    }

}
