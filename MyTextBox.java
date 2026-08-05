import java.awt.*;

public class MyTextBox extends MyDrawing {

    private String text;
    private transient boolean editing;
    private String fontName = Font.DIALOG;
    private int fontSize = 12;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    public MyTextBox(String text, int xpt, int ypt) {
        super(xpt, ypt, 0, 0);
        this.text = text;
    }

    public void draw(Graphics g) {
        if (editing) return;

        Font font = getTextFont();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        setSize(fm.stringWidth(text), fm.getHeight());

        g.setColor(getLineColor());
        int baseline = getY() + fm.getAscent();
        g.drawString(text, getX(), baseline);
        if (underline) {
            g.drawLine(getX(), baseline + 1, getX() + getW(), baseline + 1);
        }

        super.draw(g);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isUnderline() {
        return underline;
    }

    public Font getTextFont() {
        int style = Font.PLAIN;
        if (bold) style |= Font.BOLD;
        if (italic) style |= Font.ITALIC;
        return new Font(fontName, style, fontSize);
    }

    public boolean contains(int x, int y) {
        if(region == null) return false;
        return super.contains(x, y) || region.contains(x, y);
    }

    public void setRegion() {
        region = new Rectangle(getX(), getY(), getW(), getH());
    }
}
