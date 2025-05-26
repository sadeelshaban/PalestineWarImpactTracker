package First;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.Year;

/**
 * Shared fonts, colors, and control styling.
 * Screens that use the photo keep that image; this only styles controls.
 */
public final class UiTheme {
    private UiTheme() {}

    public static final Color GOLD = new Color(212, 168, 67);
    public static final Color GOLD_SOFT = new Color(245, 215, 110);
    public static final Color INK = new Color(28, 28, 28);
    public static final Color DANGER = new Color(140, 42, 42);
    public static final Color DANGER_HOVER = new Color(174, 56, 56);
    public static final Color PAGE = new Color(247, 244, 238);
    public static final Color CARD = new Color(0, 0, 0, 158);

    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font SUBTITLE = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font LABEL = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    public static final int FIRST_YEAR = 1948;

    public enum Kind { DARK, GOLD, DANGER }

    public static void install() {
        UIManager.put("OptionPane.messageFont", BODY);
        UIManager.put("OptionPane.buttonFont", BUTTON);
        UIManager.put("Label.font", BODY);
        UIManager.put("Button.font", BUTTON);
        UIManager.put("TextField.font", FIELD);
        UIManager.put("ComboBox.font", FIELD);
        UIManager.put("Spinner.font", FIELD);
    }

    public static JButton button(String text, Kind kind) {
        JButton button = new JButton(text);
        applyButton(button, kind);
        return button;
    }

    public static void applyButton(JButton button, Kind kind) {
        Color background = switch (kind) {
            case GOLD -> GOLD;
            case DANGER -> DANGER;
            default -> INK;
        };
        Color foreground = kind == Kind.GOLD ? INK : Color.WHITE;
        Color hover = switch (kind) {
            case GOLD -> GOLD_SOFT;
            case DANGER -> DANGER_HOVER;
            default -> new Color(54, 54, 54);
        };

        button.putClientProperty("bg", background);
        button.putClientProperty("fg", foreground);
        button.putClientProperty("hover", hover);
        button.setFont(BUTTON);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setUI(new BasicButtonUI());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        paint(button, false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    paint(button, true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                paint(button, false);
            }
        });
        button.addPropertyChangeListener("enabled", evt -> paint(button, false));
    }

    private static void paint(JButton button, boolean hover) {
        if (!button.isEnabled()) {
            button.setBackground(new Color(186, 186, 186));
            button.setForeground(Color.WHITE);
            return;
        }
        Color background = hover
                ? (Color) button.getClientProperty("hover")
                : (Color) button.getClientProperty("bg");
        button.setBackground(background);
        button.setForeground((Color) button.getClientProperty("fg"));
    }

    public static void styleField(JComponent field) {
        field.setFont(FIELD);
        field.setBackground(Color.WHITE);
        field.setForeground(INK);
        if (field instanceof JTextField textField) {
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(196, 186, 166)),
                    BorderFactory.createEmptyBorder(7, 10, 7, 10)
            ));
        }
        if (field instanceof JTextArea area) {
            area.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            area.setMargin(new Insets(8, 10, 8, 10));
        }
        if (field instanceof JComboBox<?> combo) {
            combo.setBackground(Color.WHITE);
            combo.setPreferredSize(new Dimension(220, 36));
        }
        if (field instanceof JSpinner spinner) {
            spinner.setFont(FIELD);
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor numberEditor) {
                JTextField text = numberEditor.getTextField();
                text.setFont(FIELD);
                text.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            }
        }
    }

    public static JLabel label(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL);
        label.setForeground(color);
        return label;
    }

    public static int currentYear() {
        return Year.now().getValue();
    }

    public static JSpinner yearSpinner() {
        int now = currentYear();
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(2023, FIRST_YEAR, now, 1));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        styleField(spinner);
        return spinner;
    }

    /** Reads the typed year. Shows an error when it is before 1948 or after the current year. */
    public static Integer readYear(JSpinner spinner, Component parent) {
        try {
            spinner.commitEdit();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Please enter a valid year.",
                    "Invalid Year",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int year = ((Number) spinner.getValue()).intValue();
        int now = currentYear();
        if (year < FIRST_YEAR || year > now) {
            JOptionPane.showMessageDialog(parent,
                    "Year must be from " + FIRST_YEAR + " to " + now + ".\nYears before " + FIRST_YEAR + " are not allowed.",
                    "Invalid Year",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return year;
    }

    public static JPanel overlayCard() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 168, 67), 1),
                BorderFactory.createEmptyBorder(18, 24, 18, 24)
        ));
        return panel;
    }
}
