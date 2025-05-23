package app.persistence.documentCreation;

public class Svg {
    private static final String SVG_TEMPLATE = "<svg version=\"1.1\"\n" +
            "     x=\"%f\" y=\"%f\"\n" +
            "     viewBox=\"%s\"  width=\"%s\" \n" +
            "     height=\"%s\" preserveAspectRatio=\"xMinYMin\">";

    private static final String SVG_ARROW_DEFS = "<defs>\n" +
            "        <marker id=\"beginArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"0\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker id=\"endArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"12\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";

    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%f\" y=\"%f\" height=\"%f\" width=\"%f\" style=\"%s\" />";

    private static final String SVG_LINE_TEMPLATE = "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"%s\" />";

    private static final String SVG_TEXT_TEMPLATE = "<text x=\"%d\" y=\"%d\" transform=\"rotate(%d, %d, %d)\" style=\"text-anchor: middle; font-size: 14px\">%s</text>";

    private static final String SVG_DIM_LINE_TEMPLATE = "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke: #000000; stroke-width: 1px; marker-start: url(#beginArrow); marker-end: url(#endArrow);\" />";

    private static final String SVG_TICK_TEMPLATE = "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke: #000000; stroke-width: 1px;\" />";


    private StringBuilder svg = new StringBuilder();

    public Svg(float x, float y, String viewBox, String width, String height) {
        svg.append(String.format(SVG_TEMPLATE, x, y, viewBox, width, height));
        svg.append(SVG_ARROW_DEFS);
    }

    public void addRectangle(float x, float y, double height, double width, String style) {
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, height, width, style));
    }

    public void addLine(float x1, float y1, float x2, float y2, String style) {
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    public void addText(int x, int y, int rotation, String text) {
        svg.append(String.format(SVG_TEXT_TEMPLATE, x, y, rotation, x, y, text));
    }

    public void addDimensionArrow(float x1, float y1, float x2, float y2, String label, boolean vertical) {
        svg.append(String.format(SVG_DIM_LINE_TEMPLATE, x1, y1, x2, y2));

        float tickSize = 5;

        if (vertical) {
            svg.append(String.format(SVG_TICK_TEMPLATE, x1 - tickSize, y1, x1 + tickSize, y1));
            svg.append(String.format(SVG_TICK_TEMPLATE, x2 - tickSize, y2, x2 + tickSize, y2));
        } else {
            svg.append(String.format(SVG_TICK_TEMPLATE, x1, y1 - tickSize, x1, y1 + tickSize));
            svg.append(String.format(SVG_TICK_TEMPLATE, x2, y2 - tickSize, x2, y2 + tickSize));
        }

        int textX = Math.round((x1 + x2) / 2);
        int textY = Math.round((y1 + y2) / 2);

        //Kigger lige om pilen står horizontalt eller vertikalt
        if (vertical) {
            addText(textX, textY, -90, label);
        } else {
            addText(textX, textY - 10, 0, label);
        }
    }

    @Override
    public String toString() {
        return svg.append("</svg>").toString();
    }
}
