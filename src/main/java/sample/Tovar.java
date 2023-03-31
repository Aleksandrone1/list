package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public  class Tovar extends Application {
    int i=0;

    GridPane gridPane1 = new GridPane();
    Button button=new Button("Получить артикул");
    ObservableList<Label> labels = FXCollections.observableArrayList();
    Pane resultsPane = new Pane();
    Autorization autorization = new Autorization();
@Override
public void start(Stage stage) throws SQLException,IOException {

    Button vozvr = new Button("Отредактировать данные");
    Button ubyv = new Button("Вставить данные");
    ObservableList<String> langs = FXCollections.observableArrayList("Сортировка по убыванию", "Сортировка по возрастанию");
    ComboBox<String> langsComboBox = new ComboBox<String>(langs);
    langsComboBox.setValue("Сортировка по убыванию");

    TextField textField = new TextField();
    GridPane gridPane1 = new GridPane();
    gridPane1.setAlignment(Pos.TOP_LEFT);
    gridPane1.setHgap(10);
    gridPane1.setVgap(10);
    gridPane1.setPadding(new Insets(25, 25, 25, 25));
    Button button1 = new Button("Получить артикул");
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        // Метод для поиска в базе данных и обновления результатов на экране
        // newValue - новое значение текстового поля
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111")) {
            int row = 0;
            // Создаем запрос к базе данных
            String query = "SELECT*FROM [dbo].[tovar] WHERE name_t LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + newValue + "%");
            // Выполняем запрос и получаем результаты
            ResultSet rs1 = pstmt.executeQuery();
            gridPane1.getChildren().clear();
            // Очищаем старые результаты поиска

            // Обрабатываем результаты поиска и отображаем их на экране
            while (rs1.next()) {
                Blob blob = rs1.getBlob("Photo");
                Image image = new Image(blob.getBinaryStream());
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setImage(image);
                String s3 = rs1.getString("Name_t");
                Double s4 = rs1.getDouble("prise");
                String s5 = rs1.getString("articul");
                String w5 = "Стоимость" + " " + s4;
                int s6 = rs1.getInt("quantity");
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul = new Label();
                articul.setText(s5);
                Label money = new Label();
                money.setText(w5);
                ImageView imageView1 = new ImageView();
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                VBox vBox = new VBox(20, nameValueLabel, articul);
                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(50, money);
                HBox hbox = new HBox(50, imageView1, hbox1, vBox);
                HBox.setHgrow(money, Priority.ALWAYS);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 0, row);
                row++;
            }
            // Закрываем соединение с базой данных
            pstmt.close();
            rs1.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (
            ClassNotFoundException e) {
        e.printStackTrace();
    }

    try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
         PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar]")) {
        ResultSet rs1 = stmt1.executeQuery();
        int row = 0;
        gridPane1.getChildren().clear();
        while (rs1.next()) {
            Blob blob = rs1.getBlob("Photo");
            Image image = new Image(blob.getBinaryStream());
            ImageView imageView = new ImageView();
            imageView.setFitWidth(150);
            imageView.setFitHeight(200);
            imageView.setImage(image);
            String s3 = rs1.getString("Name_t");
            int s4 = rs1.getInt("prise");
            String s5 = rs1.getString("articul");
            String w5 = "Стоимость:" + " " + s4;
            int s6 = rs1.getInt("quantity");
            StringBuilder stringBuilder = new StringBuilder(s3);
            Label nameValueLabel = new Label();
            nameValueLabel.setText(s3);
            Label articul = new Label();
            articul.setText(s5);
            Label money = new Label();
            money.setText(w5);
            ImageView imageView1 = new ImageView();
            imageView1.setFitWidth(100);
            imageView1.setFitHeight(130);
            imageView1.setImage(image);
            VBox vBox = new VBox(20, nameValueLabel, articul);
            Label label1 = new Label();
            label1.setText(s3);
            VBox hbox1 = new VBox(50, money);
            HBox hbox = new HBox(50, imageView1, hbox1, vBox);
            HBox.setHgrow(money, Priority.ALWAYS);
            hbox.setStyle("-fx-border-color: black;");
            gridPane1.add(hbox, 0, row);
            row++;
            // добавьте элемент в ячейку таблицы
        }
        stmt1.close();
        rs1.close();

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    button.setOnAction(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    //INSERT INTO Employees (Id, Name, Photo)
//                SELECT 1, 'Dbnz', BulkColumn
//                FROM Openrowset( Bulk 'C:\Сессия 1\Товар_import\S538J7.jpg', Single_Blob) as EmployeePicture
//
    ubyv.setOnAction(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/gridpane.fxml"));
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage2 = (Stage) ubyv.getScene().getWindow();
        stage2.close();
    });

    TextField name=new TextField();
    Label polzname=new Label();
    name.setPromptText("имя товара");
    TextField price=new TextField();
    price.setPromptText("цена товара");
    TextField articul=new TextField();
    articul.setPromptText("артикул товара");
    TextField put=new TextField();
    put.setPromptText("путь к фото");
    Button dobav=new Button("добавить данные");
    Button strih=new Button("узнать штрихкод");
    Button delete=new Button("удалить данные");
    Button updateprice=new Button("обновить цену");
    Button updatetovar=new Button("обновить имя товара");
    Button updatear=new Button("обновить артикул");
    Button otpavkaemail=new Button("отправить письмо");
    Button admin=new Button("Администратирование");
    TextField kolichestvo=new TextField();
    kolichestvo.setPromptText("количество товара");
admin.setOnAction(event -> {
    try {
        String fxmlFile = "/fxml/admin.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        Stage stage1=new Stage();
        stage1.setTitle("Отправка почты");
        stage1.setScene(new Scene(root));
        stage1.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (
            ClassNotFoundException e) {
        e.printStackTrace();
    }
    dobav.setOnAction(event -> {
        String namet = name.getText();
        double pr = Double.parseDouble(price.getText());
        String ar = articul.getText();
        String photo = put.getText();
        File file=new File(photo);
        int quantity = Integer.parseInt(kolichestvo.getText());String s=
                "INSERT INTO [dbo].[tovar] ([name_t],[prise],[articul],[photo],[quantity]) VALUES (?,?,?,?,?)" ;
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
        }
        catch (SQLException  | IOException e) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar]")) {
            ResultSet rs1 = stmt1.executeQuery();
            int row = 0;
            gridPane1.getChildren().clear();
            while (rs1.next()) {
                Blob blob = rs1.getBlob("Photo");
                Image image = new Image(blob.getBinaryStream());
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setImage(image);
                String s3 = rs1.getString("Name_t");
                int s4 = rs1.getInt("prise");
                String s5 = rs1.getString("articul");
                String w5 = "Стоимость:" + " " + s4;
                int s6 = rs1.getInt("quantity");
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);
                ImageView imageView1 = new ImageView();
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                VBox vBox = new VBox(20, nameValueLabel, articul1);
                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(50, money);
                HBox hbox = new HBox(50, imageView1, hbox1, vBox);
                HBox.setHgrow(money, Priority.ALWAYS);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 0, row);
                row++;
                // добавьте элемент в ячейку таблицы
            }
            stmt1.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    delete.setOnAction(event -> {
        String namet = name.getText();
        String s= "Delete FROM [dbo].[tovar] where name_t=?";
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setString(1, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar]")) {
            ResultSet rs1 = stmt1.executeQuery();
            int row = 0;
            gridPane1.getChildren().clear();
            while (rs1.next()) {
                Blob blob = rs1.getBlob("Photo");
                Image image = new Image(blob.getBinaryStream());
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setImage(image);
                String s3 = rs1.getString("Name_t");
                int s4 = rs1.getInt("prise");
                String s5 = rs1.getString("articul");
                String w5 = "Стоимость:" + " " + s4;
                int s6 = rs1.getInt("quantity");
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);
                ImageView imageView1 = new ImageView();
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                VBox vBox = new VBox(20, nameValueLabel, articul1);
                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(50, money);
                HBox hbox = new HBox(50, imageView1, hbox1, vBox);
                HBox.setHgrow(money, Priority.ALWAYS);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 0, row);
                row++;
                // добавьте элемент в ячейку таблицы
            }
            stmt1.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    strih.setOnAction(event -> {
        try {
        String fxmlFile = "/fxml/sample.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        Stage stage1=new Stage();
        stage1.setTitle("Штрихкод");
        stage1.setScene(new Scene(root));
        stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    otpavkaemail.setOnAction(event -> {
        try {
            String fxmlFile = "/fxml/otpavlaemail.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
            Stage stage1=new Stage();
            stage1.setTitle("Отправка почты");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    updateprice.setOnAction(event -> {
        String namet = name.getText();
        double pr = Double.parseDouble(price.getText());
        String s= "UPDATE  [dbo].[tovar] SET prise=? where name_t=?";
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setDouble(1, pr);
            preparedStatement.setString(2, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar]")) {
            ResultSet rs1 = stmt1.executeQuery();
            int row = 0;
            gridPane1.getChildren().clear();
            while (rs1.next()) {
                Blob blob = rs1.getBlob("Photo");
                Image image = new Image(blob.getBinaryStream());
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setImage(image);
                String s3 = rs1.getString("Name_t");
                int s4 = rs1.getInt("prise");
                String s5 = rs1.getString("articul");
                String w5 = "Стоимость:" + " " + s4;
                int s6 = rs1.getInt("quantity");
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);
                ImageView imageView1 = new ImageView();
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                VBox vBox = new VBox(20, nameValueLabel, articul1);
                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(50, money);
                HBox hbox = new HBox(50, imageView1, hbox1, vBox);
                HBox.setHgrow(money, Priority.ALWAYS);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 0, row);
                row++;
                // добавьте элемент в ячейку таблицы
            }
            stmt1.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    updatear.setOnAction(event -> {
        String namet = name.getText();
        String ar = articul.getText();
        String s= "UPDATE  [dbo].[tovar] SET articul=? where name_t=?";
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setString(1, ar);
            preparedStatement.setString(2, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar]")) {
            ResultSet rs1 = stmt1.executeQuery();
            int row = 0;
            gridPane1.getChildren().clear();
            while (rs1.next()) {
                Blob blob = rs1.getBlob("Photo");
                Image image = new Image(blob.getBinaryStream());
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setImage(image);
                String s3 = rs1.getString("Name_t");
                int s4 = rs1.getInt("prise");
                String s5 = rs1.getString("articul");
                String w5 = "Стоимость:" + " " + s4;
                int s6 = rs1.getInt("quantity");
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);
                ImageView imageView1 = new ImageView();
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                VBox vBox = new VBox(20, nameValueLabel, articul1);
                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(50, money);
                HBox hbox = new HBox(50, imageView1, hbox1, vBox);
                HBox.setHgrow(money, Priority.ALWAYS);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 0, row);
                row++;
                // добавьте элемент в ячейку таблицы
            }
            stmt1.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });

    autorization.constz(polzname);

    Button diagramma=new Button("Диаграмма");
    diagramma.setOnAction(event -> {
        try {
    Diagramma tovar=new Diagramma();
    Stage stage11 = new Stage();

            tovar.start(stage11);
            stage11.showAndWait();
        } catch (SQLException  | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    });
    Label label=new Label("Имя пользователя: ");
    VBox vBox =new VBox(30,name,price,articul,put,kolichestvo,dobav,delete,updateprice,updatear,strih,otpavkaemail,admin,diagramma);
    StackPane.setAlignment(vBox, Pos.TOP_RIGHT);
    StackPane.setMargin(vBox, new Insets(150, 50, 600, 500));
    HBox hBox = new HBox(50, textField);
    StackPane.setAlignment(hBox, Pos.TOP_RIGHT);
    StackPane.setMargin(hBox, new Insets(80, 300, 600, 40));
    StackPane.setAlignment(gridPane1, Pos.TOP_RIGHT);
    StackPane.setMargin(gridPane1, new Insets(110, 300, 500, 10));
    StackPane.setAlignment(polzname, Pos.TOP_LEFT);

    StackPane.setMargin(polzname, new Insets(20, 10, 500, 650));
    StackPane.setMargin(label, new Insets(20, 10, 500, 530));
    StackPane.setAlignment(label, Pos.TOP_LEFT);
    StackPane root = new StackPane(gridPane1, hBox,vBox,polzname,label);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(root);
    scrollPane.setPrefSize(300, 300); // установка начальных размеров
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // установка политики прокрутки вертикальной полосы
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // установка политики прокрутки горизонтальной полосы
    scrollPane.setVvalue(0.5); // установка начального значения полосы прокрутки по вертикали
    scrollPane.setHvalue(0.5); // установка начального значения полосы прокрутки по горизонтали
    scrollPane.setPannable(true); // установка возможности прокрутки мышью
    scrollPane.setFitToWidth(true);


    Scene scene = new Scene(scrollPane, 800, 600);
    stage.setScene(scene);
    stage.setTitle("TableView in JavaFX");
    stage.show();
}
}