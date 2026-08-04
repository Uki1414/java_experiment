import java.awt.*;
import java.io.*;
import java.awt.geom.*;

public class MyRoundRect extends MyDrawing {

    public MyRoundRect(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyRoundRect(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyRoundRect(int xpt, int ypt, int wpt, int hpt, Color lColor, Color fColor, int lWidth) {
        super(xpt, ypt, wpt, hpt, lColor, fColor, lWidth);
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }

        int arc = Math.min(w, h) / 5;

        Graphics2D g2 = (Graphics2D) g;

        if (isDashed()) {
            g2.setStroke(new MyDashStroke(getLineWidth()));
        } else {
            g2.setStroke(new BasicStroke(getLineWidth()));
        }

        if (getShadow()) {
            g2.setColor(Color.black);
            g2.fillRoundRect(x + 5, y + 5, w, h, arc, arc);
        }

        g2.setColor(getFillColor());
        g2.fillRoundRect(x, y, w, h, arc, arc);
        g2.setColor(getLineColor());
        g2.drawRoundRect(x, y, w, h, arc, arc);

        super.draw(g);
    }

    public boolean contains(int x, int y) {
        if(region == null) return false;
        return super.contains(x, y) || region.contains(x, y);
    }

    public void setRegion() {
        region = new RoundRectangle2D.Double(getX(), getY(), getW(), getH(), getW() / 5, getH() / 5);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setRegion();
    }
}
