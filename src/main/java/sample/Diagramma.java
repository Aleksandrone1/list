package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;

public class Diagramma extends Application {
    private final ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();
    BarChart<String, Number> barChart;
    XYChart.Series<String, Number> data;
    XYChart.Series<String, Number> data1;
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar] where prise Between 213 and 1500"); PreparedStatement stmt2 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar] where prise Between 1500 and 5000")){
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Наименование товара");
            yAxis.setLabel("Цена");
            barChart = new BarChart<String,Number>(xAxis, yAxis);
            data = new XYChart.Series<String, Number>();
             data1 = new XYChart.Series<String, Number>();
            ResultSet rs1 = stmt1.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();
            int row = 0;
            while (rs2.next()) {
                String s9 = rs2.getString("Name_t");
                int s8 = rs2.getInt("prise");
                String s5 = rs2.getString("articul");
                String w5 = "Стоимость:" + " " + s8;
                int s6 = rs2.getInt("quantity");
                data1.getData().add(new XYChart.Data<>(s9, s8));
                data1.setName("Категория от 1500 до 5000");
                row++;
                // добавьте элемент в ячейку таблицы
            }
            rs2.close();
            stmt2.close();
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
                data.getData().add(new XYChart.Data<>(s3, s4));
                data.setName("Категория от 213 до 1500");
                row++;
                // добавьте элемент в ячейку таблицы
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        chartData.add(data);
        chartData.add(data1);
         barChart.setData(chartData);
        barChart.setTitle("Категория распределения товара по цене");

        Scene scene = new Scene(barChart);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}