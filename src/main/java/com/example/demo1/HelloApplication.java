package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class HelloApplication extends Application {
    private static ArrayList<Product> products = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        Button button = new Button("Downland the file ");
        root.getChildren().add(button);
        button.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel файлы (*.xls, *.xlsx)", "*.xls", "*.xlsx"));
            File file = fileChooser.showOpenDialog(stage);
            try {
                readExcelFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

    }
    public static void readExcelFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            int id = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            double price = row.getCell(2).getNumericCellValue();
            int quantity = (int) row.getCell(3).getNumericCellValue();
            double totalPrice = row.getCell(4).getNumericCellValue();

            LocalDate date = row.getCell(5).getDateCellValue()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Product product = new Product(id, name, price, quantity, totalPrice, date);
            products.add(product);
        }
        System.out.println(products);
        System.out.println(products.size());
        System.out.println(LocalDate.now());
        System.out.println(products.get(3).price());
        System.out.println(products.get(3).name());
        System.out.println(products.get(3).totalPrice());
        System.out.println(products.get(3).date());







    }

    public static void main(String[] args) {
        launch();
    }
}