import java.awt.*;
import java.util.Vector;
import java.util.Enumeration;

public class StateManager {

    private State currentState;
    private MyCanvas canvas;
    private MyTextBox activeTextBox;
    private boolean dashed = false;
    private boolean shadow = false;
    private Color currentFillColor = Color.white;
    private Color currentLineColor = Color.black;
    private int currentLineWidth = 1;
    private String currentFontName = Font.DIALOG;
    private int currentFontSize = 12;
    private boolean currentBold = false;
    private boolean currentItalic = false;
    private boolean currentUnderline = false;

    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setState(State state) {
        this.currentState = state;
        if (!(state instanceof TextButton)) {
            clearActiveTextBox();
        }
    }

    public void addDrawing(MyDrawing d) {
        d.setDashed(this.dashed);
        d.setShadow(this.shadow);
        d.setFillColor(this.currentFillColor);
        d.setLineColor(this.currentLineColor);
        d.setLineWidth(this.currentLineWidth);
        if (d instanceof MyTextBox) {
            MyTextBox textBox = (MyTextBox)d;
            textBox.setFontName(this.currentFontName);
            textBox.setFontSize(this.currentFontSize);
            textBox.setBold(this.currentBold);
            textBox.setItalic(this.currentItalic);
            textBox.setUnderline(this.currentUnderline);
        }
        mediator().addDrawing(d);
        mediator().repaint();
    }

    public MyCanvas getCanvas() {
        return canvas;
    }

    public void mouseDown(int x, int y) {
        if (currentState != null) {
            currentState.mouseDown(x, y);
        }
    }

    public void mouseUp(int x, int y) {
        if (currentState != null) {
            currentState.mouseUp(x, y);
        }
    }

    public void mouseDrag(int x, int y) {
        if (currentState != null) {
            currentState.mouseDrag(x, y);
        }
    }

    public void setRepaint() {
        canvas.repaint();
    }

    public void removeDrawing(MyDrawing d) {
        mediator().removeDrawing(d);
        mediator().repaint();
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            d.setDashed(dashed);
        }
        setRepaint();
      
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            d.setShadow(shadow);
        }
        setRepaint();
    }

    public void setFillColor(Color color) {
        this.currentFillColor = color;
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            d.setFillColor(color);
        }
        setRepaint();
    }

    public void setLineColor(Color color) {
        this.currentLineColor = color;
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            d.setLineColor(color);
        }
        setRepaint();
    }

    public void setLineWidth(int width) {
        this.currentLineWidth = width;
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            d.setLineWidth(width);
        }
        setRepaint();
    }

    public void setFontName(String fontName) {
        this.currentFontName = fontName;
        if (activeTextBox != null) {
            activeTextBox.setFontName(fontName);
        } else {
            for (MyDrawing d : mediator().getSelectedDrawings()) {
                if (d instanceof MyTextBox) {
                    ((MyTextBox)d).setFontName(fontName);
                }
            }
        }
        setRepaint();
    }

    public void setFontSize(int fontSize) {
        this.currentFontSize = fontSize;
        if (activeTextBox != null) {
            activeTextBox.setFontSize(fontSize);
        } else {
            for (MyDrawing d : mediator().getSelectedDrawings()) {
                if (d instanceof MyTextBox) {
                    ((MyTextBox)d).setFontSize(fontSize);
                }
            }
        }
        setRepaint();
    }

    public void setBold(boolean bold) {
        this.currentBold = bold;
        if (activeTextBox != null) {
            activeTextBox.setBold(bold);
        } else {
            for (MyDrawing d : mediator().getSelectedDrawings()) {
                if (d instanceof MyTextBox) {
                    ((MyTextBox)d).setBold(bold);
                }
            }
        }
        setRepaint();
    }

    public void setItalic(boolean italic) {
        this.currentItalic = italic;
        if (activeTextBox != null) {
            activeTextBox.setItalic(italic);
        } else {
            for (MyDrawing d : mediator().getSelectedDrawings()) {
                if (d instanceof MyTextBox) {
                    ((MyTextBox)d).setItalic(italic);
                }
            }
        }
        setRepaint();
    }

    public void setUnderline(boolean underline) {
        this.currentUnderline = underline;
        if (activeTextBox != null) {
            activeTextBox.setUnderline(underline);
        } else {
            for (MyDrawing d : mediator().getSelectedDrawings()) {
                if (d instanceof MyTextBox) {
                    ((MyTextBox)d).setUnderline(underline);
                }
            }
        }
        setRepaint();
    }

    public void setActiveTextBox(MyTextBox textBox) {
        activeTextBox = textBox;
    }

    public void clearActiveTextBox() {
        activeTextBox = null;
    }

    public Font getCurrentTextFont() {
        int style = Font.PLAIN;
        if (currentBold) style |= Font.BOLD;
        if (currentItalic) style |= Font.ITALIC;
        return new Font(currentFontName, style, currentFontSize);
    }

    public void setSelected(int x, int y) {
        mediator().setSelected(x, y);
    }

    public MyTextBox getTextBoxAt(int x, int y) {
        return mediator().getTextBoxAt(x, y);
    }

    public void move(int dx, int dy){
        mediator().move(dx, dy);
    }

    public void bringToFront(){
        mediator().bringToFront();
    }

    public void sendToBack(){
        mediator().sendToBack();
    }

    public Mediator mediator(){
        return canvas.getMediator();
    }

    public void deleteSelected(){
        for (MyDrawing d : mediator().getSelectedDrawings()) {
            mediator().removeDrawing(d);
        }
        mediator().clearSelection(); 
        setRepaint();
    }

    public void copy() {
        mediator().copy();
    }

    public void cut() {
        mediator().cut();
    }

    public void paste() {
        mediator().paste();
    }

    public Vector<MyDrawing> getSelectedDrawings() {
        return mediator().getSelectedDrawings();
    }

    public Enumeration<MyDrawing> drawingsElements() {
        return mediator().drawingsElements();
    }

    public void clearSelection() {
        mediator().clearSelection();
    }
}
