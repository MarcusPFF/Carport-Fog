package app.persistence.documentCreation;

import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RafterCalculator;


public class CarportSvg {
    private int width;
    private int height;
    private Svg carportSvg;
    private RafterCalculator rafterCalculator = new RafterCalculator();
    private PoleCalculator poleCalculator = new PoleCalculator();

    //TODO det her kan vi ændre
    private int rafterThickness;
    private int beamThickness;
    private float rafterSpacing;
    private final float poleSize = 10f;
    private boolean hasShed = false;


    public CarportSvg(int width, int height) {
        this.width = width;
        this.height = height;

        int numberOfRafters = rafterCalculator.rafterAmountForRoofCalculator(width);
        rafterSpacing = (float) width / (numberOfRafters - 1);
        beamThickness = (int) Math.ceil(rafterCalculator.totalRafterWidthInMmCalculator(width)/10);
        rafterThickness = (int) Math.ceil(rafterCalculator.totalRafterWidthInMmCalculator(height)/10);

        int extraMargin = 60; //Dette sørger for at der er plads
        carportSvg = new Svg(0, 0, "0 0 " + (width + extraMargin) + " " + (height + extraMargin),
                (width + extraMargin) + "px", (height + extraMargin) + "px");

        addOuterFrame(); //Viser hvor taget ligger
        addShedWithPoles();
        addPoles(); //Pæle der løfter carporten op
        addBeams(); //De to bjælker der ligger på langs i top og bund
        addRafters(); //Spær der ligger henover beams
        addDimensions();
    }

    private void addOuterFrame() {
        carportSvg.addRectangle(0, 0, height, width, "stroke-width:1px; stroke: #000000; fill: #ffffff");
    }

    private void addBeams() {
        float beamIndent = 30f;
        carportSvg.addRectangle(0, beamIndent, beamThickness, width, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        carportSvg.addRectangle(0, height - beamIndent - beamThickness, beamThickness, width, "stroke-width:1px; stroke: #000000; fill: #ffffff");
    }

    private void addRafters() {
        for (float x = 0; x <= width; x += rafterSpacing) {
            carportSvg.addRectangle(x, 0, height, rafterThickness, "stroke:#000000; fill:#ffffff");
        }
    }

    private void addPlumbersTape(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        String dottedStyle = "stroke:black; stroke-width:2; stroke-dasharray:1,5; stroke-linecap:round;";
        carportSvg.addLine(x1, y1, x2, y2, dottedStyle);
        carportSvg.addLine(x3, y3, x4, y4, dottedStyle);
    }

    private void addShedWithPoles() {
        hasShed = true;
        int shedHeight = 200;
        int shedWidth = 300;
        float xStart = 20f;
        float yStart = 30f;
        float xEnd = xStart + shedHeight;
        float yEnd = yStart + shedWidth;
        String shedStyling = "stroke:#8B4513; fill:#deb887; stroke-width:2px; fill-opacity:0.6;";
        String poleStyling = "stroke:#000000; fill:#000000";
        //Tilføjer skuret
        carportSvg.addRectangle(xStart, yStart, shedWidth, shedHeight, shedStyling);

        //Tilføjer pæle
        carportSvg.addRectangle(xEnd - poleSize / 2, yStart - poleSize / 2, poleSize, poleSize, poleStyling); // Top-right
        carportSvg.addRectangle(xStart - poleSize / 2, yEnd - poleSize / 2, poleSize, poleSize, poleStyling); // Bottom-left
        carportSvg.addRectangle(xEnd - poleSize / 2, yEnd - poleSize / 2, poleSize, poleSize, poleStyling);   // Bottom-right
    }

    private void addPoles() {
        int polesX = poleCalculator.poleAmountXCalculator(height);
        int polesY = poleCalculator.poleAmountYCalculator(width);

        float xStart = 20f; // Første pæl placeret 20 cm inde
        float xEnd = width - 110f; // Sidste pæl placeret 110cm fra enden
        float xSpacing = (xEnd - xStart) / (polesX - 1); // Mellemrum når pladsen udenfor er trukket fra

        String poleStyling = "stroke:#000000; fill:#000000";
        float yTop = 30f; // 30cm fra toppen
        float yBottom = height - 30f; // 30cm fra bunden

        // Koordinater til hjørnepæle til båndet
        float topLeftX = 0, topLeftY = 0;
        float topRightX = 0, topRightY = 0;
        float bottomLeftX = 0, bottomLeftY = 0;
        float bottomRightX = 0, bottomRightY = 0;
        float midLeftX = 0, midLeftY = 0;
        float midRightX = 0, midRightY = 0;

        for (int i = 0; i < polesX; i++) {
            float x = xStart + i * xSpacing;

            if (polesY >= 1) {
                carportSvg.addRectangle(x - poleSize / 2, yTop - poleSize / 2, poleSize, poleSize, poleStyling);

                // Gem koordinat til første og sidste pæl på øverste bjælke
                if (i == 0) {
                    topLeftX = x;
                    topLeftY = yTop;
                } else if (i == polesX - 1) {
                    topRightX = x;
                    topRightY = yTop;
                }
                if (i == polesX / 2) {
                    midLeftX = x;
                    midLeftY = yTop;
                }
            }

            if (polesY >= 2) {
                carportSvg.addRectangle(x - poleSize / 2, yBottom - poleSize / 2, poleSize, poleSize, poleStyling);

                // Gem koordinat til første og sidste pæl på nederste bjælke
                if (i == 0) {
                    bottomLeftX = x;
                    bottomLeftY = yBottom;
                } else if (i == polesX - 1) {
                    bottomRightX = x;
                    bottomRightY = yBottom;
                }
                if (i == polesX / 2) {
                    midRightX = x;
                    midRightY = yBottom;
                }
            }
        }
        // Båndet der skal ligge på kryds
        if (polesY >= 2) {
            if (hasShed) {
                addPlumbersTape(
                        midLeftX, midLeftY, bottomRightX, bottomRightY,   // midt-top til højre bund
                        midRightX, midRightY, topRightX, topRightY);        // midt-bund til højre top);
            } else {
                addPlumbersTape(
                        topLeftX, topLeftY, bottomRightX, bottomRightY,   // Venstre top til højre bund
                        bottomLeftX, bottomLeftY, topRightX, topRightY);    // Venstre bund til højre top
            }
        }
    }

    private void addDimensions() {
        // Bredde af carport
        float yForWidthArrow = height + 55f;
        carportSvg.addDimensionArrow(0, yForWidthArrow, width, yForWidthArrow, width + " cm", false);

        // Højde af carport
        float xForHeightArrow = width + 50f;
        carportSvg.addDimensionArrow(xForHeightArrow, 0, xForHeightArrow, height, height + " cm", true);

        // Spærafstande
        int numberOfRafters = rafterCalculator.rafterAmountForRoofCalculator(width);
        float spacing = (float) width / (numberOfRafters - 1);
        float yBellowRafter = height + 25f;

        for (int i = 0; i < numberOfRafters - 1; i++) {
            float x1 = i * spacing;
            float x2 = (i + 1) * spacing;
            String label = Math.round(x2 - x1) + " cm";

            carportSvg.addDimensionArrow(x1, yBellowRafter, x2, yBellowRafter, label, false);
        }
    }


    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
