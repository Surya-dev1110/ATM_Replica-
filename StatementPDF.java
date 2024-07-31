package ATM_PROJECT;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.*;
import java.awt.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class StatementPDF 
{
    //Creating Encrypted ATM Statement PDF
    public static void createPDF(ResultSet rs,String username,String cardnum,String mailid,Long bal) throws Exception 
    {
        //Creating document with size
        Document document = new Document(PageSize.A4);

        try {
            // Create a PdfWriter instance to write the PDF file
            String fileLocation = "D:\\project\\"+username+"_Statement.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileLocation));
            //bank logo image
            String imagepath = "C:\\Users\\ELCOT\\OneDrive\\Pictures\\Screenshots\\Screenshot (1).png";
            Image image = Image.getInstance(imagepath);
            image.scaleToFit(200,150);
            image.setAlignment(Element.ALIGN_MIDDLE);
             //watermark
             MyPageEventHelper eventhelper = new MyPageEventHelper();

             writer.setPageEvent(eventhelper);
             
             //Encrypting PDF
            String userpassword = cardnum;
            String ownerpassword = "jdbc@123";

            int permisssions = PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_MODIFY_ANNOTATIONS | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_ASSEMBLY;

            writer.setEncryption(userpassword.getBytes(),ownerpassword.getBytes(), permisssions,PdfWriter.STANDARD_ENCRYPTION_40);
            // Open the document for writing
            document.open();
            document.addCreationDate();//ading creation date
            document.add(image);//  adding bank logo
            //adding title
            Paragraph p = new Paragraph("\t\t\tSTATEMENT OF JDBC ATM",getColoredFont(Color.BLUE,Font.UNDERLINE));
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            p.setAlignment(Element.RECTANGLE);
            document.add(p);
            //adding user detail
            document.add(new Paragraph("Name : "+username+"\nAccount Number : "+cardnum+"\nAccount Status : Active \nMail Id : "+mailid ));
            document.add(new Paragraph("CURRENT BALANCE : "+String.valueOf(bal),getColoredFont(Color.BLACK , Font.BOLD)));
            document.add(new Paragraph(" \n \n "));

            // Create a table with 3 columns
            PdfPTable table = new PdfPTable(3);
            
            //creating cell explicitly
            PdfPCell cell1 = new PdfPCell(new Paragraph("TRANSACTION DATE"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("WITHDRAWALS"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("DEPOSIT"));
            

            // adding Backgroundcolour for Each cells
            cell1.setBackgroundColor(Color.lightGray);
            cell2.setBackgroundColor(Color.lightGray);
            cell3.setBackgroundColor(Color.lightGray);
            
            

            table.setTotalWidth(table.getTotalWidth());
            // Add headers to the table
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            
            

            // Adding rows to the table
            while (rs.next()) 
            {
               
                
                table.addCell(String.valueOf(rs.getDate(2)));
                String status = rs.getString(3);
                if(status.equalsIgnoreCase("Dr"))
                {
                    table.addCell(new Paragraph(String.valueOf(rs.getLong(4)),getColoredFont(Color.RED,Font.COURIER)));
                    table.addCell("-");
                }
                else
                {
                    table.addCell("-");
                    table.addCell(new Paragraph(String.valueOf(rs.getLong(4)),getColoredFont(Color.green,Font.COURIER)));
                }
                // Add the table to the document
                document.add(table);
                table.flushContent();
                table.deleteBodyRows();
                
                
            }

            

            // Close the document
            document.close();

            System.out.println("Table created successfully.");
           //Sending PDF to the user mail
            Javasendmail.sendMailAttach(mailid,"Bank_Statement", "Dear "+username+"\n\tHere is your Bank Account ATM transaction statement.\n\nYour file is Password Protected. Your Password is your *Card Number.\n\nKindly find the attachement !! \n\n To Know more : https://jdbc.tiiny.site/ "+"\n\n\nRegards\nTeam JDBC bank ! ", fileLocation);

        } catch (DocumentException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // Adding colour and Font style for text
    public static Font getColoredFont(Color color, int fnt) {
        Font font = new Font();
        font.setStyle(fnt);
        font.setColor(color);
        return font;
    }

}

// This class is for adding water mark on every pages

class MyPageEventHelper extends PdfPageEventHelper {

    @Override
    public void onEndPage(com.lowagie.text.pdf.PdfWriter writer, com.lowagie.text.Document document) {
        try {
            String imagepath = "C:\\Users\\ELCOT\\OneDrive\\Pictures\\Screenshots\\Screenshot (1).png";
            Image image = Image.getInstance(imagepath);
            float opacity = 0.3f; //Opacity of the watermark image
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(opacity);

            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.saveState();
            canvas.setGState(gs);
            image.setAbsolutePosition(40, 260);// position of image
            image.scaleToFit(500, 500);// size of image

            canvas.addImage(image);
            canvas.restoreState();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
