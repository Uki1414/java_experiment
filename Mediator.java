import java.util.Enumeration;
import java.util.Vector;
import java.io.*;

public class Mediator {
  Vector<MyDrawing> drawings;
  MyCanvas canvas;
  Vector<MyDrawing> selectedDrawings = new Vector<MyDrawing>();
  Vector<MyDrawing> buffer = new Vector<MyDrawing>();


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

  public Vector<MyDrawing> getSelectedDrawings(){
    return selectedDrawings;
  }

  public void repaint(){
    canvas.repaint();
  }

  public void setSelected(int x, int y){
    boolean found = false;
    for(int i = drawings.size() - 1; i >= 0; i--){
      MyDrawing d = drawings.get(i);
      if(d.contains(x, y)) {
        if (!d.getSelected()) {
          clearSelection();
          for(MyDrawing drawing : drawings){
            drawing.setSelected(false);
          }
          d.setSelected(true);
        }
        setSelectedDrawings(d);
        found = true;
        break;
      }
    }
    if (!found) clearAllSelections();
  }

  public void setSelectedDrawings(MyDrawing d){
    selectedDrawings.clear();
    if (d != null){
      selectedDrawings.add(d);
    }
  }

  public void clearSelection(){
    selectedDrawings.clear();
    repaint();
  }

  public void clearAllSelections() {
    for (MyDrawing d : drawings) {
      d.setSelected(false);
    }
    clearSelection();
  }

  public void move(int dx, int dy){
    if(selectedDrawings != null){
      for (MyDrawing d : selectedDrawings) {
            d.move(dx, dy);
      }
    }
  }

  public void clearBuffer() {
        buffer.clear();
    }

    public void copy() {
        if (selectedDrawings != null) {
            clearBuffer();
            for (MyDrawing d : selectedDrawings) {
                buffer.add(d.clone()); 
            }
        }
    }

    public void cut() {
        if (selectedDrawings != null) {
            copy();
            for (MyDrawing d : selectedDrawings) {
                removeDrawing(d);
            }
            clearSelection();
            repaint();
        }
    }

    public void paste() {
        if (buffer != null) {
            Vector<MyDrawing> newBuffer = new Vector<MyDrawing>();
            for (MyDrawing d : buffer) {
                MyDrawing clone = d.clone();
                clone.setLocation(clone.getX() + 20, clone.getY() + 20);
                addDrawing(clone);
                newBuffer.add(clone);
            }
            buffer = newBuffer;
            repaint();
        }
    }

    public void open(File f) {
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fin);

            drawings = (Vector<MyDrawing>)in.readObject();
            for (MyDrawing d : drawings) {
                d.setRegion(); // ロードした各図形のregionを再設定
            }
            clearAllSelections();
            fin.close();
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(File f) {
        try {
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(drawings);
            out.flush();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
  