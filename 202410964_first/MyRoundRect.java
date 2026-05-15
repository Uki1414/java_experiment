import java.awt.*;

public class MyRoundRect extends MyDrawing{
  public MyRoundRect( int xpt, int ypt ){
    super(xpt, ypt);
  }

  public MyRoundRect( int xpt, int ypt, int wpt, int hpt ){
    super(xpt, ypt, wpt, hpt);
  }

  public MyRoundRect( int xpt, int ypt, int wpt, int hpt, Color lColor, Color fColor, int lWidth ){
    super(xpt, ypt, wpt, hpt, lColor, fColor, lWidth);
  }


  public void draw( Graphics g ){
    int x = getX();
    int y = getY();
    int w = getW();
    int h = getH();
    int arc = Math.min(w, h) / 20;

    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(getLineWidth()));
    g2.setColor(getFillColor());
    g2.fillRoundRect(x, y, w, h, arc, arc);
    g2.setColor(getLineColor());
    g2.drawRoundRect(x, y, w, h, arc, arc);
  }
}