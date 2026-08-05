import java.awt.event.*;
import javax.swing.*;

public class TextButton extends JButton implements State {

    private StateManager stateManager;
    private int x, y;
    private JTextField textField;
    private MyTextBox editingTextBox;

    public TextButton(StateManager stateManager) {
        super("Text");
        this.stateManager = stateManager;
        addActionListener(new TextListener());
    }

    public void mouseDown(int x, int y) {
        finishInput();

        editingTextBox = stateManager.getTextBoxAt(x, y);
        if (editingTextBox != null) {
            stateManager.setActiveTextBox(editingTextBox);
            stateManager.setSelected(x, y);
            this.x = editingTextBox.getX();
            this.y = editingTextBox.getY();
            editingTextBox.setEditing(true);
        } else {
            stateManager.clearActiveTextBox();
            this.x = x;
            this.y = y;
        }

        textField = new JTextField();
        int width = 200;
        if (editingTextBox != null) {
            textField.setText(editingTextBox.getText());
            textField.setForeground(editingTextBox.getLineColor());
            textField.setFont(editingTextBox.getTextFont());
            width = Math.max(width, editingTextBox.getW() + 20);
        } else {
            textField.setFont(stateManager.getCurrentTextFont());
        }
        int height = textField.getPreferredSize().height;
        textField.setBounds(this.x, this.y, width, height);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finishInput();
            }
        });

        textField.getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        textField.getActionMap().put("cancel", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cancelInput();
            }
        });

        textField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                finishInput();
            }
        });

        MyCanvas canvas = stateManager.getCanvas();
        canvas.add(textField);
        canvas.revalidate();
        canvas.repaint();

        JTextField input = textField;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (input == textField) {
                    input.requestFocusInWindow();
                    input.setCaretPosition(input.getDocument().getLength());
                }
            }
        });
    }

    public void mouseDrag(int x, int y) {
    }

    public void mouseUp(int x, int y) {
    }

    public void notify(String text) {
        if (!text.isEmpty()) {
            stateManager.addDrawing(new MyTextBox(text, x, y));
        }
    }

    private void finishInput() {
        if (textField == null) return;

        String text = textField.getText();
        removeTextField();

        if (editingTextBox != null) {
            MyTextBox target = editingTextBox;
            editingTextBox = null;
            if (text.isEmpty()) {
                stateManager.clearActiveTextBox();
                stateManager.removeDrawing(target);
                stateManager.getSelectedDrawings().remove(target);
            } else {
                target.setText(text);
                target.setEditing(false);
                target.setSelected(false);
                stateManager.getSelectedDrawings().remove(target);
                stateManager.setRepaint();
            }
        } else {
            notify(text);
        }
    }

    private void cancelInput() {
        if (textField == null) return;
        removeTextField();
        if (editingTextBox != null) {
            editingTextBox.setEditing(false);
            editingTextBox.setSelected(false);
            stateManager.getSelectedDrawings().remove(editingTextBox);
            stateManager.clearActiveTextBox();
            editingTextBox = null;
            stateManager.setRepaint();
        }
    }

    private void removeTextField() {
        MyCanvas canvas = stateManager.getCanvas();
        canvas.remove(textField);
        textField = null;
        canvas.revalidate();
        canvas.repaint();
    }

    class TextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(TextButton.this);
        }
    }
}
