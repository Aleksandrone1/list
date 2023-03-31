package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
public class Controller {
    public javafx.scene.control.Button b;
    @FXML
   private TextField name;
    @FXML
    private TextField articul;
    @FXML
    private Button save;
    @FXML
    private Button  form;
    @FXML
    private Button  ar;
    @FXML
    private Button pechat;
    @FXML
    private ImageView strih;
    String imagePath = "str.png";
    String pdfPath = "mypdf.pdf";
    private static final int F = 24;
    static int[] calcDimensions(int height, int wihdt){

        return new int[0];
    }
    private static final double HEIGHT_SYMBOL = 25.93;
    private static final double HEIGHT_BAR = 22.85;
    private static final double LEFT_MARGIN = 3.63;
    private static final double RIGHT_MARGIN = 2.31;
    private static final double DOWN_EXTENSION = 1.65;
    private static final double HEIGHT_DIGITS = 2.75;
    Document document = new Document();
    public void createPDF(String imagePath, String pdfPath) {
        try {
            // создание файла PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            Image img = Image.getInstance(imagePath);
            document.add(img);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        ar.setOnAction(event -> {
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=mdb;user=sa;password=1111", "sa", "1111");
                 PreparedStatement stmt1 = connection1.prepareStatement("SELECT*FROM [dbo].[tovar] where name_t=?")) {
                String s=name.getText();
                stmt1.setString(1, s);

                ResultSet rs1 = stmt1.executeQuery();
                int row = 0;
                while (rs1.next()) {
                   String s1=rs1.getString("articul");
                   articul.setText(s1);
                    System.out.println(s1);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        form.setOnAction(event -> {
            try {
                Code128Bean code128= new Code128Bean();
                code128.setHeight(25.93);
                code128.setBarHeight(22.85*0.165);
                code128.setModuleWidth(0.15);
                code128.setFontSize(2.75);
                code128.setQuietZone(3.63);
                code128.doQuietZone(true);
                // Создаем область для отображения штрих-кода
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(700, BufferedImage.TYPE_BYTE_BINARY,false,   0);
                code128.generateBarcode(canvas,articul.getText());
                BufferedImage bufferedImage = canvas.getBufferedImage ();
                bufferedImage.getHeight();
                strih.setImage (SwingFXUtils.toFXImage (bufferedImage, null));
                WritableImage image = strih.snapshot(new SnapshotParameters(), null);
                BufferedImage bufferedImage1 = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage1, "png", new File("str.png"));;

            }    catch (Exception e) {
                    e.printStackTrace();
                }

        });
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            fileChooser.setTitle("Save PDF");
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                String pdfPath = file.getAbsolutePath();
                createPDF(imagePath, pdfPath);
            }
        });
           pechat.setOnAction(event -> {
               // создание PrinterJob
               File file = new File("file::str.png");
               javafx.scene.image.Image image;
               image=new javafx.scene.image.Image(new File("str.png").toURI().toString());
               PrinterJob printerJob = PrinterJob.createPrinterJob();

// Отображение диалога печати и проверка выбора
               if (printerJob != null && printerJob.showPrintDialog(null)) {

                   Printer printer = printerJob.getPrinter();

               // Получение объекта PageLayout из выбранного принтера
               PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

               // Задание размеров и позиции изображения на странице
               double scaleX = pageLayout.getPrintableWidth() / image.getWidth();
               double scaleY = pageLayout.getPrintableHeight() / image.getHeight();
               double scale = Math.min(scaleX, scaleY);
               double imageWidth = image.getWidth() * scale;
               double imageHeight = image.getHeight() * scale;
               double x = (pageLayout.getPrintableWidth() - imageWidth) / 2;
               double y = (pageLayout.getPrintableHeight() - imageHeight) / 2;
               // Создание нового объекта PageLayout с заданными параметрами
               PageLayout layout = printer.createPageLayout(pageLayout.getPaper(), pageLayout.getPageOrientation(),Printer.MarginType.DEFAULT);
                       Insets insets = new Insets(0, (int) x,(int) y,20);
                   printerJob.getJobSettings().setPageLayout(layout);
               boolean success = printerJob.printPage(new ImageView(image));

               // Завершение задания печати
               if (success) {
                   printerJob.endJob();
               }
           }
           });
    }

}
