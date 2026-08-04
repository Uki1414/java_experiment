import java.awt.*;
import java.io.*;

public class MyHendecagonal extends MyDrawing {

    public MyHendecagonal(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyHendecagonal(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyHendecagonal(int xpt, int ypt, int wpt, int hpt, Color lColor, Color fColor, int lWidth) {
        super(xpt, ypt, wpt, hpt, lColor, fColor, lWidth);
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        if (w < 0) { x += w; w *= -1; }
        if (h < 0) { y += h; h *= -1; }

        Graphics2D g2 = (Graphics2D) g;

        int nPoints = 11;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        double cx = x + w / 2.0;
        double cy = y + h / 2.0;
        double rx = w / 2.0;
        double ry = h / 2.0;
        double startAngle = -Math.PI / 2;

        for (int i = 0; i < nPoints; i++) {
            double angle = startAngle + (2 * Math.PI * i / nPoints);
            xPoints[i] = (int) Math.round(cx + rx * Math.cos(angle));
            yPoints[i] = (int) Math.round(cy + ry * Math.sin(angle));
        }

        Polygon polygon = new Polygon(xPoints, yPoints, nPoints);

        if (isDashed()) {
            g2.setStroke(new MyDashStroke(getLineWidth()));
        } else {
            g2.setStroke(new BasicStroke(getLineWidth()));
        }

        if (getShadow()) {
            g2.setColor(Color.black);
            Polygon shadowPolygon = new Polygon(xPoints, yPoints, nPoints);
            shadowPolygon.translate(5, 5);
            g2.fillPolygon(shadowPolygon);
        }

        g2.setColor(getFillColor());
        g2.fillPolygon(polygon);
        g2.setColor(getLineColor());
        g2.drawPolygon(polygon);
        
        super.draw(g);
    }

    public boolean contains(int x, int y) {
        if(region == null) return false;
        return super.contains(x, y) || region.contains(x, y);
    }

    public void setRegion() {
        int nPoints = 11;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        int rx = getX();
        int ry = getY();
        int rw = getW();
        int rh = getH();
        
        if (rw < 0) { rx += rw; rw *= -1; }
        if (rh < 0) { ry += rh; rh *= -1; }

        double cx = rx + rw / 2.0;
        double cy = ry + rh / 2.0;
        double radX = rw / 2.0;
        double radY = rh / 2.0;
        double startAngle = -Math.PI / 2;

        for (int i = 0; i < nPoints; i++) {
            double angle = startAngle + (2 * Math.PI * i / nPoints);
            xPoints[i] = (int) Math.round(cx + radX * Math.cos(angle));
            yPoints[i] = (int) Math.round(cy + radY * Math.sin(angle));
        }

        region = new Polygon(xPoints, yPoints, nPoints);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setRegion();
    }
}
