package pro.enikeev.imitation;

import javafx.scene.control.TextField;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class IntegerTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return ("".equals(text) || text.matches("[0-9]"));
    }

    public Integer getValue() {
        return Integer.parseInt(super.getText());
    }
}