import java.util.Enumeration;
import java.util.Vector;

public class Mediator {
  Vector<MyDrawing> drawings;
  MyCanvas canvas;
  MyDrawing selectedDrawings = null;
  MyDrawing buffer = null;

  public Mediator(MyCanvas canvas){
    this.canvas = canvas;
    drawings = new Vector<MyDrawing>();
  }

  public Enumeration<MyDrawing> drawingsElements(){
    return drawings.elements();
  }

  public void addDrawing(MyDrawing d){
    drawings.add(d);
    setSelectedDrawings(d);
  }

  public void removeDrawing(MyDrawing d){
    drawings.remove(d);
  }

  public MyDrawing getSelectedDrawings(){
    return selectedDrawings;
  }

  public void repaint(){
    canvas.repaint();
  }

  public void setSelected(int x, int y){
    for(MyDrawing d : drawings){
      d.setSelected(false);
    }
    selectedDrawings = null;

    for(int i = drawings.size() - 1; i >= 0; i--){
      MyDrawing d = drawings.get(i);
      if(d.contains(x, y)) {
        setSelectedDrawings(d);
        d.setSelected(true);
        break;
      }
    }
    canvas.repaint();
  }

  public void setSelectedDrawings(MyDrawing d){
    selectedDrawings = d;
  }

  public void move(int dx, int dy){
    if(selectedDrawings != null){
      selectedDrawings.move(dx, dy);
    }
  }

  public void clearBuffer() {
        buffer = null;
    }

    public void copy() {
        if (selectedDrawings != null) {
            clearBuffer();
            buffer = selectedDrawings.clone();
        }
    }

    public void cut() {
        if (selectedDrawings != null) {
            clearBuffer();
            buffer = selectedDrawings.clone();
            removeDrawing(selectedDrawings);
            setSelectedDrawings(null);
            repaint();
        }
    }

    public void paste() {
        if (buffer != null) {
            MyDrawing clone = buffer.clone();
            clone.setLocation(clone.getX() + 20, clone.getY() + 20);
            
            buffer = clone;
            
            addDrawing(clone);
            repaint();
        }
    }
}
  