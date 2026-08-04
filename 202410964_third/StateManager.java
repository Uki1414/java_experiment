import java.awt.*;

public class StateManager {

    private State currentState;
    private MyCanvas canvas;
    private boolean dashed = false;
    private boolean shadow = false;
    private Color currentFillColor = Color.white;
    private Color currentLineColor = Color.black;
    private int currentLineWidth = 1;

    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void addDrawing(MyDrawing d) {
        d.setDashed(this.dashed);
        d.setShadow(this.shadow);
        d.setFillColor(this.currentFillColor);
        d.setLineColor(this.currentLineColor);
        d.setLineWidth(this.currentLineWidth);
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

        MyDrawing d = mediator().getSelectedDrawings();
        if (d != null) {
            d.setDashed(dashed);
            setRepaint();
        }
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;

        MyDrawing d = mediator().getSelectedDrawings();
        if (d != null) {
            d.setShadow(shadow);
            setRepaint();
        }
    }

    public void setFillColor(Color color) {
        this.currentFillColor = color;
        MyDrawing d = mediator().getSelectedDrawings();
        if (d != null) {
            d.setFillColor(color);
            setRepaint();
        }   
    }

    public void setLineColor(Color color) {
        this.currentLineColor = color;
        MyDrawing d = mediator().getSelectedDrawings();
        if (d != null) {
            d.setLineColor(color);
            setRepaint();
        }
    }

    public void setLineWidth(int width) {
        this.currentLineWidth = width;
        MyDrawing d = mediator().getSelectedDrawings();
        if (d != null) {
            d.setLineWidth(width);
            setRepaint();
        }
    }

    public void setSelected(int x, int y) {
        mediator().setSelected(x, y);
    }

    public void move(int dx, int dy){
        mediator().move(dx, dy);
    }

    public Mediator mediator(){
        return canvas.getMediator();
    }

    public void deleteSelected(){
        MyDrawing d = mediator().getSelectedDrawings();
        if(d != null){
            mediator().removeDrawing(d);
            mediator().setSelectedDrawings(null);
            setRepaint();
        }
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
}
