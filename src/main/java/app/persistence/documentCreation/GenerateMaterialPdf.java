package app.persistence.documentCreation;

import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

//ADVARSEL:
// Denne kode har været med meget hjælp af AI, da vi ønskede at sende et attachment PDF med på vores mail som var vores stykliste.
// Dette tænke vi var en pænere løsning, end bare at sende styklisten i selve mailen.
// Vi anvender et maven lib pdfbox

public class GenerateMaterialPdf {
    private static OfferMapper offerMapper = new OfferMapper();

    public byte[] generateMaterialPdf(List<MountForCalculator> mounts, List<RoofForCalculator> roofs, List<ScrewForCalculator> screws, List<WoodForCalculator> woods) throws IOException, DatabaseException {

        try (PDDocument document = new PDDocument()) {
            float yStart = 700;
            yStart = writeSection(document, "Materialeliste", null, yStart, true);
            yStart = writeSection(document, "Beslag", mounts, yStart, false);
            yStart = writeSection(document, "Tagplader", roofs, yStart, false);
            yStart = writeSection(document, "Skruer", screws, yStart, false);
            yStart = writeSection(document, "Træ", woods, yStart, false);

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                document.save(baos);
                return baos.toByteArray();
            }
        }
    }

    private float writeSection(PDDocument doc, String title, List<?> items, float y, boolean isTitleOnly) throws IOException, DatabaseException {
        PDPage page = new PDPage();
        doc.addPage(page);
        try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
            cs.setFont(PDType1Font.HELVETICA_BOLD, isTitleOnly ? 16 : 12);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText(isTitleOnly ? title : title + ":");
            cs.endText();
            y -= isTitleOnly ? 30 : 20;

            if (!isTitleOnly && items != null) {
                String[] headers = {"Navn", "Antal", "Beskrivelse"};
                float startX = 50;
                float[] widths = {180, 60, 260};
                float rowH = 18;

                cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
                float textY = y - 12;
                float x = startX;
                for (int i = 0; i < headers.length; i++) {
                    cs.beginText();
                    cs.newLineAtOffset(x + 2, textY);
                    cs.showText(headers[i]);
                    cs.endText();
                    x += widths[i];
                }
                y -= rowH;

                cs.setFont(PDType1Font.HELVETICA, 9);
                for (Object item : items) {
                    String[] cols = formatItem(item);
                    if (cols == null) continue;

                    if (y < 50) break;

                    x = startX;
                    for (int i = 0; i < cols.length; i++) {
                        cs.beginText();
                        cs.newLineAtOffset(x + 2, y - 12);
                        cs.showText(cols[i]);
                        cs.endText();
                        x += widths[i];
                    }
                    y -= rowH;
                }
            }
        }
        return 700;
    }

    private String[] formatItem(Object item) throws DatabaseException {
        ConnectionPool connectionPool = app.persistence.controller.RoutingController.getConnectionPool();
        if (item instanceof MountForCalculator m) {
            return new String[]{m.getName(), m.getAmount() + " stk.", m.getDescription()};
        } else if (item instanceof ScrewForCalculator s) {
            return new String[]{s.getName(), s.getAmount() + " bokse", s.getDescription()};
        } else if (item instanceof RoofForCalculator r) {
            return new String[]{r.getName(), r.getAmount() + " stk.", r.getDescription()};
        } else if (item instanceof WoodForCalculator w) {
            String dims = offerMapper.getWoodWidthFromWoodDimensionId(connectionPool, w.getWoodDimensionId()) + "mm X " + offerMapper.getWoodHeightFromWoodDimensionId(connectionPool, w.getWoodDimensionId()) + "mm X " + offerMapper.getWoodLengthFromWoodDimensionId(connectionPool, w.getWoodDimensionId()) + "cm.";
            return new String[]{w.getName() + " " + dims, String.valueOf(w.getAmount()), w.getDescription()};
        }
        return null;
    }
}
