import java.util.Enumeration;
import java.util.Vector;

public class Mediator {
  Vector<MyDrawing> drawings;
  MyCanvas canvas;
  MyDrawing selectedDrawing = null;

  public Mediator(MyCanvas canvas){
    this.canvas = canvas;
    drawings = new Vector<MyDrawing>();
  }

  public Enumeration<MyDrawing> drawingsElements(){
    return drawings.elements();
  }

  public void addDrawing(MyDrawing d){
    drawings.add(d);
    setSelectedDrawing(d);
  }

  public void removeDrawing(MyDrawing d){
    drawings.remove(d);
  }

  public MyDrawing getSelectedDrawing(){
    return selectedDrawing;
  }

  public void repaint(){
    canvas.repaint();
  }

  public void setSelected(int x, int y){
  }
}
  