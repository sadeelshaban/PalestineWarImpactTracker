package First;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This screen allows the user to add a person (Martyr, Wounded, Prisoner)
 * to a specific region and time.
 */
public class AddPersonScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    public AddPersonScreen() {
        super("Add Person");

        // Input fields
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(15);
        JTextField ageField = new JTextField(5);
        JTextField causeField = new JTextField(15);
        JTextField deathDateField = new JTextField(10);

        String[] types = {"Martyr", "Wounded", "Prisoner"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JComboBox<String> regionBox = new JComboBox<>(new String[]{"Gaza", "West Bank", "East Jerusalem"});
        JComboBox<String> monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"});
        JSpinner yearSpinner = UiTheme.yearSpinner();

        JButton addButton = UiTheme.button("Add Person", UiTheme.Kind.GOLD);

        for (JComponent field : new JComponent[]{idField, nameField, ageField, causeField, deathDateField, typeBox, regionBox, monthBox}) {
            UiTheme.styleField(field);
        }

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UiTheme.PAGE);
        form.setBorder(BorderFactory.createEmptyBorder(4, 28, 8, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 8);

        String[] labels = {
                "National ID", "Type", "Name", "Age", "Cause of death",
                "Date of death", "Region", "Month", "Year"
        };
        JComponent[] fields = {
                idField, typeBox, nameField, ageField, causeField,
                deathDateField, regionBox, monthBox, yearSpinner
        };
        for (int i = 0; i < labels.length; i++) {
            JLabel label = UiTheme.label(labels[i], UiTheme.INK);
            label.setPreferredSize(new Dimension(140, 28));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            form.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            fields[i].setPreferredSize(new Dimension(320, 36));
            form.add(fields[i], gbc);
        }

        JLabel hint = new JLabel("Date format: DD-MM-YYYY");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(new Color(110, 100, 88));
        gbc.gridx = 1;
        gbc.gridy = labels.length;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 8, 8, 8);
        form.add(hint, gbc);

        gbc.gridy = labels.length + 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        form.add(Box.createVerticalGlue(), gbc);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.setBackground(UiTheme.PAGE);
        actions.setBorder(BorderFactory.createEmptyBorder(8, 28, 20, 36));
        addButton.setPreferredSize(new Dimension(160, 42));
        actions.add(addButton);

        JLabel title = new JLabel("Add a person");
        title.setFont(UiTheme.SUBTITLE);
        title.setForeground(UiTheme.INK);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UiTheme.PAGE);
        header.setBorder(BorderFactory.createEmptyBorder(22, 36, 16, 36));
        header.add(title, BorderLayout.WEST);

        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(BorderFactory.createEmptyBorder());
        formScroll.setBackground(UiTheme.PAGE);
        formScroll.getViewport().setBackground(UiTheme.PAGE);
        formScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UiTheme.PAGE);
        panel.add(header, BorderLayout.NORTH);
        panel.add(formScroll, BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);

        // Action
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            String name = nameField.getText().trim();
            String cause = causeField.getText().trim();
            String date = deathDateField.getText().trim();
            String region = (String) regionBox.getSelectedItem();
            String month = (String) monthBox.getSelectedItem();
            Integer selectedYear = UiTheme.readYear(yearSpinner, this);
            if (selectedYear == null) {
                return;
            }
            int year = selectedYear;

            // Validate empty fields
            if (id.isEmpty() || name.isEmpty() || cause.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate name (letters and spaces only)
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Name must contain letters only (no numbers or symbols).", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date format (DD-MM-YYYY)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Date must be in format DD-MM-YYYY.", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate age
            int age;
            try {
                age = Integer.parseInt(ageField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for duplicate ID
            boolean exists = false;
            for (RegionData r : MainScreen.regionDatabase) {
                for (WarVictim v : r.getVictims()) {
                    if (v.getId().equals(id)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) break;
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "This ID already exists. IDs must be unique.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find RegionData
            RegionData target = null;
            for (RegionData r : MainScreen.regionDatabase) {
                if (r.getRegion().equals(region) &&
                    r.getMonth().equals(month) &&
                    r.getYear() == year) {
                    target = r;
                    break;
                }
            }

            if (target == null) {
                JOptionPane.showMessageDialog(this, "No data found for selected region and date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add victim
            WarVictim victim = new WarVictim(id, name, age, date, cause, type);
            target.addVictim(victim);
            JOptionPane.showMessageDialog(this, type + " added successfully.");
            dispose();
        });

        setContentPane(panel);
        setSize(640, 700);
        setMinimumSize(new Dimension(560, 640));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
