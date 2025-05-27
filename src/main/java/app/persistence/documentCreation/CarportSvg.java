package app.persistence.documentCreation;

import app.persistence.calculator.PoleCalculator;
import app.persistence.calculator.RafterCalculator;
import io.javalin.http.Context;


public class CarportSvg {
    private int width;
    private int height;
    private Svg carportSvg;
    private RafterCalculator rafterCalculator = new RafterCalculator();
    private PoleCalculator poleCalculator = new PoleCalculator();

    private int rafterThickness;
    private int beamThickness;
    private float rafterSpacing;
    private final float poleSize = 10f;
    private boolean hasShed = false;
    private final float halfPoleSizeForCentering = poleSize / 2f;

    public CarportSvg(Context ctx, int width, int height) {
        this.width = width;
        this.height = height;

        int numberOfRafters = rafterCalculator.rafterAmountForRoofCalculator(width);
        rafterSpacing = (float) width / (numberOfRafters - 1);
        beamThickness = (int) Math.ceil(rafterCalculator.totalRafterWidthInMmCalculator(width) / 10);
        rafterThickness = (int) Math.ceil(rafterCalculator.totalRafterWidthInMmCalculator(height) / 10);

        int extraMargin = 60; //Dette sørger for at der er plads
        carportSvg = new Svg(0, 0, "0 0 " + (width + extraMargin) + " " + (height + extraMargin),
                (width + extraMargin) + "px", (height + extraMargin) + "px");

        addOuterFrame(); //Viser hvor taget ligger
        //Tjekker om der skal være skur eller ej
        this.hasShed = ctx.sessionAttribute("redskabsrumCheckbox");
        if (hasShed) {
            int shedLength = ctx.sessionAttribute("redskabsrumLength");
            int shedWidth = ctx.sessionAttribute("redskabsrumWidth");
            addShedWithPoles(shedLength, shedWidth);
        }
        addPoles(); //Pæle der løfter carporten op
        addBeams(); //De to bjælker der ligger på langs i top og bund
        addRafters(); //Spær der ligger henover beams
        addDimensions(); //Sætter dimensioner og pile op
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

    private void addShedWithPoles(int shedLength, int shedWidth) {
        float xStart = 20f;
        float yStart = 30f;
        float xEnd = xStart + shedLength;
        float yEnd = yStart + shedWidth;
        String shedStyling = "stroke:#8B4513; fill:#deb887; stroke-width:2px; fill-opacity:0.6;";
        String poleStyling = "stroke:#000000; fill:#000000";
        //Tilføjer skuret
        carportSvg.addRectangle(xStart, yStart, shedWidth, shedLength, shedStyling);

        //Tilføjer pæle
        carportSvg.addRectangle(xEnd - halfPoleSizeForCentering, yStart - halfPoleSizeForCentering, poleSize, poleSize, poleStyling); // Top-right
        carportSvg.addRectangle(xStart - halfPoleSizeForCentering, yEnd - halfPoleSizeForCentering, poleSize, poleSize, poleStyling); // Bottom-left
        carportSvg.addRectangle(xEnd - halfPoleSizeForCentering, yEnd - halfPoleSizeForCentering, poleSize, poleSize, poleStyling);   // Bottom-right
    }

    private void addPoles() {
        int poleCountAlongXAxis = poleCalculator.poleAmountXCalculator(width);
        int poleCountAlongYAxis = poleCalculator.poleAmountYCalculator(height);

        float xStart = 20f; // Første pæl placeret 20 cm inde
        float xEnd = width - 110f; // Sidste pæl placeret 110cm fra enden
        float xSpacingBetweenPoles = (xEnd - xStart) / (poleCountAlongXAxis - 1); // Mellemrum når pladsen udenfor er trukket fra

        String poleStyling = "stroke:#000000; fill:#000000";
        float spacingFromTopBorderOfSvg = 30f; // 30cm fra toppen
        float spacingFromBottomBorderOfSvg = height - 30f; // 30cm fra bunden

        // Koordinater til hjørnepæle til båndet
        float topLeftX = 0, topLeftY = 0;
        float topRightX = 0, topRightY = 0;
        float bottomLeftX = 0, bottomLeftY = 0;
        float bottomRightX = 0, bottomRightY = 0;
        float topMidX = 0, topMidY = 0;
        float bottomMidX = 0, bottomMidY = 0;

        for (int i = 0; i < poleCountAlongXAxis; i++) {
            float xCoordinateForPolePlacements = xStart + i * xSpacingBetweenPoles;

            if (poleCountAlongYAxis >= 1) {
                carportSvg.addRectangle(xCoordinateForPolePlacements - halfPoleSizeForCentering, spacingFromTopBorderOfSvg - halfPoleSizeForCentering, poleSize, poleSize, poleStyling);

                // Gem koordinat til første og sidste pæl på øverste bjælke
                if (i == 0) {
                    topLeftX = xCoordinateForPolePlacements;
                    topLeftY = spacingFromTopBorderOfSvg;
                } else if (i == poleCountAlongXAxis - 1) {
                    topRightX = xCoordinateForPolePlacements;
                    topRightY = spacingFromTopBorderOfSvg;
                }
                if (i == poleCountAlongXAxis / 2) {
                    topMidX = xCoordinateForPolePlacements;
                    topMidY = spacingFromTopBorderOfSvg;
                }
            }

            if (poleCountAlongYAxis >= 2) {
                carportSvg.addRectangle(xCoordinateForPolePlacements - halfPoleSizeForCentering, spacingFromBottomBorderOfSvg - halfPoleSizeForCentering, poleSize, poleSize, poleStyling);

                // Gem koordinat til første og sidste pæl på nederste bjælke
                if (i == 0) {
                    bottomLeftX = xCoordinateForPolePlacements;
                    bottomLeftY = spacingFromBottomBorderOfSvg;
                } else if (i == poleCountAlongXAxis - 1) {
                    bottomRightX = xCoordinateForPolePlacements;
                    bottomRightY = spacingFromBottomBorderOfSvg;
                }
                if (i == poleCountAlongXAxis / 2) {
                    bottomMidX = xCoordinateForPolePlacements;
                    bottomMidY = spacingFromBottomBorderOfSvg;
                }
            }
        }
        // Båndet der skal ligge på kryds
        if (poleCountAlongYAxis >= 2) {
            if (hasShed) {
                addPlumbersTape(
                        topMidX, topMidY, bottomRightX, bottomRightY,       // midt-top til højre bund
                        bottomMidX, bottomMidY, topRightX, topRightY);      // midt-bund til højre top);
            } else {
                addPlumbersTape(
                        topLeftX, topLeftY, bottomRightX, bottomRightY,     // Venstre top til højre bund
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
