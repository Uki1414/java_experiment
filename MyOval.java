import java.awt.*;

public class MyOval extends MyDrawing{
  public MyOval( int xpt, int ypt ){
    super(xpt, ypt);
  }

  public MyOval( int xpt, int ypt, int wpt, int hpt ){
    super(xpt, ypt, wpt, hpt);
  }

  public MyOval( int xpt, int ypt, int wpt, int hpt, Color lColor, Color fColor, int lWidth ){
    super(xpt, ypt, wpt, hpt, lColor, fColor, lWidth);
  }


  public void draw( Graphics g ){
    int x = getX();
    int y = getY();
    int w = getW();
    int h = getH();

    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(getLineWidth()));
    g2.setColor(getFillColor());
    g2.fillOval(x, y, w, h);
    g2.setColor(getLineColor());
    g2.drawOval(x, y, w, h);
  }
}