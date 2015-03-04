package com.bluehub.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Standalone utility to test PDF stamping.
 */
public class PDFStampTester {

    private static void testPDF() throws IOException, DocumentException {
        final String name = "Joe Bloe Snaggletooth, III";
        final String dob = "01/01/1950";
        final String address = "1001 Somewhere St., Austin, TX 77777";

        final PdfReader pdfReader = new PdfReader(("target/ROOT/HIPAAForm.pdf"));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("/Temp/play.pdf"));
        PdfContentByte content = pdfStamper.getUnderContent(1);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final String date = dateFormat.format(new Date());

        BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

        // Add the name to the first line
        content.beginText();
        content.setFontAndSize(font, 12);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, 65, 674, 0);

        // Identifying info
        int x = 120;
        int y = 575;
        content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Name:", x-10, y, 0);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, x, y, 0);

        content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "DOB:", x-10, y-15, 0);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, dob, x, y - 15, 0);

        content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Address:", x-10, y-30, 0);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, address, x, y - 30, 0);

        // Add the Signature and date
        content.setFontAndSize(font, 16);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, 60f, 80f, 0);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT, date, 380f, 80f, 0);
        content.endText();

        pdfStamper.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        testPDF();
    }
}
