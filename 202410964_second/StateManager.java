
public class StateManager {

    private State currentState;
    private MyCanvas canvas;
    private boolean dashed = false;
    private boolean shadow = false;

    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void addDrawing(MyDrawing d) {
        d.setDashed(this.dashed);
        d.setShadow(this.shadow);
        canvas.addDrawing(d);
        canvas.repaint();
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
        canvas.removeDrawing(d);
        canvas.repaint();
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}
