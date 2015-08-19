package pro.enikeev.imitation;

import javafx.scene.control.TextField;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class FloatTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        super.replaceText(start, end, text);
        try {
            float value = Float.parseFloat(super.getText());
        } catch (NumberFormatException exception) {
            super.undo();
        }
    }

    @Override
    public void replaceSelection(String text) {
        super.replaceSelection(text);
        try {
            float value = Float.parseFloat(super.getText());
        } catch (NumberFormatException exception) {
            super.undo();
        }
    }

    public Float getValue() {
        return Float.parseFloat(super.getText());
    }

}