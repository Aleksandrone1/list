package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.JOptionPane.showMessageDialog;

public class Autorization {
    @FXML
    public Button vxod;
    @FXML
    private TextArea login;
    @FXML
    private CheckBox rob;
    @FXML
    private PasswordField pass;
    int totalAttempts = 4;
    int totalAttempts1 = 4;
    int totalAttempts2 = 4;
    public static String s9;

    TextArea textArea = new TextArea();
    private  static  final String sqlStr = "select * from  users where login_user= ? and pass=? and id_roles=1";

    private  static  final String sqlStr1 = "select * from users where login_user= ? and pass=? and id_roles=2";
    private  static  final String sqlStr3 = "select * from users where login_user= ?   and id_roles=1";
    @FXML
    void initialize() {

        vxod.setOnAction(event -> {
            s9=login.getText();
            app11();

         app1();
        });
    }
    private void app11 () {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(sqlStr)) {
            String password = pass.getText();
            String name = login.getText();
            int count1 = 0;
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                count1++;
            }
            if (totalAttempts != 0) {
                if (count1 >=1) {
                    Tovar tovar=new Tovar();
                    Stage stage = new Stage();
                    tovar.start(stage);
                    stage.showAndWait();
                } else {
                    totalAttempts--;
                    System.out.println(totalAttempts);
                }
                if (totalAttempts == 0) {
                    showMessageDialog(null, "Ограничение на 30 секунд");
                    vxod.setVisible(false);
                    int delay = 30 * 1000;
                    final Timer timer = new Timer();
                    final TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Toolkit.getDefaultToolkit().beep();
                            vxod.setVisible(true);
                            timer.cancel();
                            timer.purge();
                            totalAttempts += 3;
                        }
                    };
                    timer.schedule(task, delay);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void app1 () {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(sqlStr1)) {
            String password = pass.getText();
            String name = login.getText();
            int count1 = 0;
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                count1++;
            }
            if (totalAttempts1 != 0) {
                if (count1 >= 1) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/Zakaz.fxml"));
                    fxmlLoader.load();
                    Parent parent = fxmlLoader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();
                    System.out.println("Работает");
                    System.out.println("Login Correct!");
                } else {
                    totalAttempts1--;
                    System.out.println(totalAttempts1);
                }
                if (totalAttempts1 <= 0) {
                    showMessageDialog(null, "Ограничение на 30 секунд");
                    vxod.setVisible(false);
                    int delay = 30 * 1000;
                    final Timer timer = new Timer();
                    final TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Toolkit.getDefaultToolkit().beep();
                            vxod.setVisible(true);
                            timer.cancel();
                            timer.purge();
                            totalAttempts1 += 3;
                        }
                    };
                    timer.schedule(task, delay);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean app5 (String name, String password){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(sqlStr)) {
            int count1 = 0;
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                count1++;
            }
            if (count1 >= 1) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/Zakaz.fxml"));
                fxmlLoader.load();
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.showAndWait();
                System.out.println("Работает");
                System.out.println("Login Correct!");
            } else {
                totalAttempts2--;
                System.out.println(totalAttempts2);
            }
            if (totalAttempts2 <= 0) {
                showMessageDialog(null, "Ограничение на 30 секунд");
                vxod.setVisible(false);
                int delay = 30 * 1000;
                final Timer timer = new Timer();
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Toolkit.getDefaultToolkit().beep();
                        vxod.setVisible(true);
                        timer.cancel();
                        timer.purge();
                        totalAttempts2 += 3;
                    }
                };
                timer.schedule(task, delay);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void constz(Label name) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(sqlStr3)) {
            preparedStatement.setString(1, s9);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String s = resultSet.getString("login_user");
                name.setText(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
