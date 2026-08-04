
import java.awt.*;

public class MyDrawing implements Cloneable{

    private int x, y, w, h; // X座標, Y座標, 幅, 高さ
    private Color lineColor, fillColor; // 線の色,　塗り色
    private int lineWidth; // 線の太さ
    private boolean dashed; // 線種
    private boolean shadow; // 影
    private boolean isSelected;
    protected Shape region; //包含判定用
    private final int SIZE = 7;

    public MyDrawing() {
        this(0, 0, 40, 40, Color.black, Color.white, 1);
    }

    public MyDrawing(int x, int y) {
        this(x, y, 40, 40, Color.black, Color.white, 1);
    }

    public MyDrawing(int x, int y, int w, int h) {
        this(x, y, w, h, Color.black, Color.white, 1);
    }

    public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
        this.dashed = false;
        this.shadow = false;
        this.isSelected = false;
        setRegion();
    }

    public void draw(Graphics g) {
        // 選択状態を表す四角形を描く
        if(isSelected){
            g.setColor(Color.black);
            g.fillRect(x+w/2-SIZE/2, y-SIZE/2, SIZE, SIZE);
            g.fillRect(x-SIZE/2, y+h/2-SIZE/2, SIZE, SIZE);
            g.fillRect(x+w/2-SIZE/2, y+h-SIZE/2, SIZE, SIZE);
            g.fillRect(x+w-SIZE/2, y+h/2-SIZE/2, SIZE, SIZE);
            g.fillRect(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
            g.fillRect(x+w-SIZE/2, y-SIZE/2, SIZE, SIZE);
            g.fillRect(x-SIZE/2, y+h-SIZE/2, SIZE, SIZE);
            g.fillRect(x+w-SIZE/2, y+h-SIZE/2, SIZE, SIZE);
        }
    }

    public boolean getSelected(){
        return isSelected;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean contains(int x, int y) {
        return false;
    }

    public void setRegion() {

    }

    public void move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
        setRegion();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        setRegion();
    }

    public void setLocation(Point p) {
        this.x = p.x;
        this.y = p.y;
        setRegion();
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        setRegion();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color c) {
        this.lineColor = c;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color c) {
        this.fillColor = c;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int w) {
        this.lineWidth = w;
    }

    public boolean isDashed() {
        return dashed;
    }

    public void setDashed(boolean b) {
        this.dashed = b;
    }

    public void setShadow(boolean b) {
        shadow = b;
    }

    public boolean getShadow() {
        return shadow;
    }

    public MyDrawing clone(){
        try{
            return (MyDrawing)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
            return null;
        }
    }
}
