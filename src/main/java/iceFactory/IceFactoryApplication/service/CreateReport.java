package iceFactory.IceFactoryApplication.service;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import iceFactory.IceFactoryApplication.model.OrderItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class CreateReport {

    public static void createReport(List<OrderItem> deliveryItems, List<OrderItem> pickupItems,
                                    List<OrderItem> summaryItems, float dTotalPrice,
                                    float pTotalPrice, float sTotalPrice, String date,
                                    Stage stage) throws IOException {

        String thai = "fonts/tahoma.ttf";
        PdfFont thfont = PdfFontFactory.createFont(thai, PdfEncodings.IDENTITY_H, true);

        FileChooser fileChooser = new FileChooser();;
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName("report-date-" + date.replace("/", "-"));
        File file = fileChooser.showSaveDialog(stage);
        String path = file.getAbsolutePath();

        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.setFont(thfont);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        float col = 280f;
        float[] columnWidth = {col, col};
        Table table = new Table(columnWidth);

        table.setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE);
        table.addCell(new Cell().add("รายงานประจำวันที่ " + date)
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(thfont)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(20f)
                .setBorder(Border.NO_BORDER)
        );
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();
        int value = random.nextInt(1000000+9999999) + 1000000;
        table.addCell(new Cell().add("Report ID: " + value + "\nหน้า: 1/1\n วันที่พิมพ์: " + dtf.format(now))
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(9f)
                .setBorder(Border.NO_BORDER)
                .setMarginRight(10f)
        );

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("ข้อมูลรายการสินค้า").setTextAlignment(TextAlignment.LEFT).setFontSize(13f).setBold());
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("รายการสินค้าแบบจัดส่ง").setTextAlignment(TextAlignment.LEFT).setFontSize(9f).setBold());
        document.add(createItemsList(deliveryItems, dTotalPrice));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("รายการสินค้าแบบซื้อหน้าโรงงาน").setTextAlignment(TextAlignment.LEFT).setFontSize(9f).setBold());
        document.add(createItemsList(pickupItems, pTotalPrice));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("สรุปรวม").setTextAlignment(TextAlignment.LEFT).setFontSize(9f).setBold());
        document.add(summaryItemsTable(summaryItems, sTotalPrice));

        document.close();
    }

    private static Table createItemsList(List<OrderItem> items, float total){
        float[] itemInfoColWitdh = {140, 140, 140, 140};
        Table itemInfomation = new Table(itemInfoColWitdh);

        itemInfomation.addCell(new Cell()
                .add("สินค้า")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("จำนวน")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("ราคาต่อหน่วย")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("ราคารวม")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f)
        );

        for(OrderItem item : items){
            itemInfomation.addCell(new Cell().add(item.getPName()).setFontSize(9f));
            itemInfomation.addCell(new Cell().add("" + item.getOrderQuantity()).setFontSize(9f));
            itemInfomation.addCell(new Cell().add("" + item.getPrice()).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f));
            itemInfomation.addCell(new Cell().add("" + item.getSumPrice()).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f));
        }

        itemInfomation.addCell(new Cell()
                .add("")
                .setBackgroundColor(Color.WHITE)
                .setBorder(Border.NO_BORDER).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("")
                .setBackgroundColor(Color.WHITE)
                .setBorder(Border.NO_BORDER).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("รวมราคาทั้งหมด")
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("" + total)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        return itemInfomation;
    }

    private static Table summaryItemsTable(List<OrderItem> items, float total){
        float[] itemInfoColWitdh = {140, 140, 140};
        Table itemInfomation = new Table(itemInfoColWitdh);

        itemInfomation.addCell(new Cell()
                .add("สินค้า")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("จำนวน")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("ราคารวม")
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f)
        );

        for(OrderItem item : items){
            itemInfomation.addCell(new Cell().add(item.getPName()).setFontSize(9f));
            itemInfomation.addCell(new Cell().add("" + item.getOrderQuantity()).setFontSize(9f));
            itemInfomation.addCell(new Cell().add("" + item.getPrice()).setTextAlignment(TextAlignment.RIGHT).setFontSize(9f));
        }

        itemInfomation.addCell(new Cell()
                .add("")
                .setBackgroundColor(Color.WHITE)
                .setBorder(Border.NO_BORDER).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("รวมราคาทั้งหมด")
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        itemInfomation.addCell(new Cell()
                .add("" + total)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(new DeviceRgb(29, 185, 195))
                .setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE).setFontSize(9f)
        );
        return itemInfomation;
    }

}
