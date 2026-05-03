package com.example.PFE.config;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class PdfFooter extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfPTable footer = new PdfPTable(1);
        footer.setTotalWidth(500);
        footer.setLockedWidth(true);

        PdfPCell cell = new PdfPCell(new Phrase(
                "📞 Tel: +216 12 345 678   |   📧 Email: contact@cettex.tn   |   🌐 www.cettex.tn",
                new Font(Font.HELVETICA, 9)
        ));

        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        footer.addCell(cell);

        footer.writeSelectedRows(
                0, -1,
                50,
                30,
                writer.getDirectContent()
        );
    }
}