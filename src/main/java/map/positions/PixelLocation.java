package map.positions;

import org.apache.commons.lang3.StringUtils;

public class PixelLocation {
    protected double x;
    protected double y;

    public PixelLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PixelLocation(String x, String y) {
        this(Double.parseDouble(x), Double.parseDouble(y));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString(int tabLength) {

        return StringUtils.repeat(" ", 4 * (tabLength + 1)) + "x = " + Position.getPrintableNumber(getX()) + "\n" +
                StringUtils.repeat(" ", 4 * (tabLength + 1)) + "y = " + Position.getPrintableNumber(getY()) + "\n" +
                StringUtils.repeat(" ", 4 * tabLength) + "}\n";
    }

    @Override
    public String toString() {
        return toString(0);
    }
}
