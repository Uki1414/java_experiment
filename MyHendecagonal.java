import java.awt.*;

public class MyHendecagonal extends MyDrawing{
  public MyHendecagonal( int xpt, int ypt ){
    super(xpt, ypt);
  }

  public MyHendecagonal( int xpt, int ypt, int wpt, int hpt ){
    super(xpt, ypt, wpt, hpt);
  }

  public MyHendecagonal( int xpt, int ypt, int wpt, int hpt, Color lColor, Color fColor, int lWidth ){
    super(xpt, ypt, wpt, hpt, lColor, fColor, lWidth);
  }


  public void draw( Graphics g ){
    int x = getX();
    int y = getY();
    int w = getW();
    int h = getH();

    Graphics2D g2 = (Graphics2D) g;

    int nPoints = 11;
    int[] xPoints = new int[nPoints];
    int[] yPoints = new int[nPoints];

    double cx = x + w / 2.0;
    double cy = y + h / 2.0; 
    double rx = w / 2.0;    
    double ry = h / 2.0;     

    for (int i = 0; i < nPoints; i++) {
      double angle = startAngle + (2 * Math.PI * i / nPoints);
      
      xPoints[i] = (int) Math.round(cx + rx * Math.cos(angle));
      yPoints[i] = (int) Math.round(cy + ry * Math.sin(angle));
    }

    Polygon polygon = new Polygon(xPoints, yPoints, nPoints);

    g2.setStroke(new BasicStroke(getLineWidth()));
    g2.setColor(getFillColor());
    g2.fillPolygon(polygon);
    g2.setColor(getLineColor());
    g2.drawPolygon(polygon);
  }
}