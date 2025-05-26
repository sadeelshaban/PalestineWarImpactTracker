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
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2023, 2000, 2100, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(editor);

        JButton addButton = new JButton("Add Person");
        addButton.setBackground(new Color(60, 100, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setFocusPainted(false);

        // Layout
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 15);

        JLabel idLabel = new JLabel("National ID:");
        idLabel.setFont(labelFont);
        panel.add(idLabel);
        panel.add(idField);

        panel.add(new JLabel("Type:")).setFont(labelFont);
        panel.add(typeBox);

        panel.add(new JLabel("Name:")).setFont(labelFont);
        panel.add(nameField);

        panel.add(new JLabel("Age:")).setFont(labelFont);
        panel.add(ageField);

        panel.add(new JLabel("Cause of Death:")).setFont(labelFont);
        panel.add(causeField);

        panel.add(new JLabel("Date of Death (DD-MM-YYYY):")).setFont(labelFont);
        panel.add(deathDateField);

        panel.add(new JLabel("Region:")).setFont(labelFont);
        panel.add(regionBox);

        panel.add(new JLabel("Month:")).setFont(labelFont);
        panel.add(monthBox);

        panel.add(new JLabel("Year:")).setFont(labelFont);
        panel.add(yearSpinner);

        panel.add(new JLabel());
        panel.add(addButton);

        // Action
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            String name = nameField.getText().trim();
            String cause = causeField.getText().trim();
            String date = deathDateField.getText().trim();
            String region = (String) regionBox.getSelectedItem();
            String month = (String) monthBox.getSelectedItem();
            int year = (int) yearSpinner.getValue();

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

        // Display
        add(panel);
        setSize(500, 520);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
