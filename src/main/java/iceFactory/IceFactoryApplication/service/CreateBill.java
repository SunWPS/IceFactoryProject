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
import iceFactory.IceFactoryApplication.model.CustomerOrder;
import iceFactory.IceFactoryApplication.model.OrderItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class CreateBill {

    public static void createBill(CustomerOrder order, float total, String date, String billId,Stage stage) throws IOException {
        String thai = "fonts/tahoma.ttf";
        PdfFont thfont = PdfFontFactory.createFont(thai, PdfEncodings.IDENTITY_H, true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Bill");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName(order.getOrderId().toString());
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
        table.addCell(new Cell().add("Bill")
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(20f)
                .setBorder(Border.NO_BORDER)
        );
        table.addCell(new Cell().add("โรงงานน้ำแข็ง\nถนนแอปปี้แลนด์\nกรุงเทพฯ\n10240")
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(9f)
                .setBorder(Border.NO_BORDER)
                .setMarginRight(10f)
        );

        float[] colwitdh = {80, 200, 40, 100};
        Table customerInfomation = new Table(colwitdh);

        customerInfomation.addCell(new Cell(0,4)
                .add("ข้อมูลลูกค้า")
                .setBorder(Border.NO_BORDER)
        );

        customerInfomation.addCell(new Cell().add("ชื่อ:").setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add(order.getCustomerName()).setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add("หมายเลขออเดอร์:").setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add(order.getOrderId().toString()).setBorder(Border.NO_BORDER).setFontSize(9f));

        customerInfomation.addCell(new Cell().add("หมายเลขบิล:").setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add(billId).setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add("วันที่พิมพ์:").setBorder(Border.NO_BORDER).setFontSize(9f));
        customerInfomation.addCell(new Cell().add(date).setBorder(Border.NO_BORDER).setFontSize(9f));

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(customerInfomation);
        document.add(new Paragraph("\n"));
        document.add(orderList(order, total));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("เซนต์: ___________________________").setTextAlignment(TextAlignment.RIGHT).setFontSize(9f));

        document.close();
    }

    private static Table orderList(CustomerOrder order, float total){
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

        for(OrderItem item : order.getOrderItemList()){
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

}
