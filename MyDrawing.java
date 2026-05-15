import java.awt.*;

public class MyDrawing{
  private int x, y, w, h; // X座標, Y座標, 幅, 高さ
  private Color lineColor, fillColor; // 線の色,　塗り色
  private int lineWidth; // 線の太さ
  private boolean dashed; // 線種

  public MyDrawing(){
    this(0, 0, 40, 40, Color.black, Color.white, 1);
  }

  public MyDrawing(int x, int y){
    this(x, y, 40, 40, Color.black, Color.white, 1);
  }

  public MyDrawing(int x, int y, int w, int h){
    this(x, y, w, h, Color.black, Color.white, 1);
  }

  public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.lineColor = lineColor;
    this.fillColor = fillColor;
    this.lineWidth = lineWidth;
    this.dashed = false;
  }

  public void draw(Graphics g){

  }

  public void move( int dx, int dy ){
    x = x + dx;
    y = y + dy;
  }

  public void setLocation( int x, int y ){
    this.x = x;
    this.y = y;
  }

  public void setLocation( Point p ){
    this.x = p.x;
    this.y = p.y;
  }

  public Point getLocation(){
    return new Point(x, y);
  }

  public void setSize( int w, int h ){
    this.w = w;
    this.h = h;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public int getW(){
    return w;
  }

  public int getH(){
    return h;
  }

  public Color getLineColor(){
    return lineColor;
  }

  public void setLineColor(Color c){
    this.lineColor = c;
  }

  public Color getFillColor(){
    return fillColor;
  }

  public void setFillColor(Color c){
    this.fillColor = c;
  }

  public int getLineWidth(){
    return lineWidth;
  }

  public boolean isDashed(){
    return dashed;
  }

  public void setDashed(boolean b){
    this.dashed = b;
  }
}