package First;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewDataScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> regionComboBox;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;
    private JTextArea resultArea;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private RegionData currentData;

    public ViewDataScreen() {
        super("View War Data");

        String[] regions = {"Gaza", "West Bank", "East Jerusalem"};
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        regionComboBox = new JComboBox<>(regions);
        monthComboBox = new JComboBox<>(months);
        yearSpinner = UiTheme.yearSpinner();
        UiTheme.styleField(regionComboBox);
        UiTheme.styleField(monthComboBox);

        JButton viewButton = UiTheme.button("View", UiTheme.Kind.GOLD);
        editButton = UiTheme.button("Edit", UiTheme.Kind.DARK);
        deleteButton = UiTheme.button("Delete", UiTheme.Kind.DANGER);
        backButton = UiTheme.button("Back", UiTheme.Kind.DARK);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        resultArea = new JTextArea(18, 50);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        UiTheme.styleField(resultArea);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        resultArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(196, 186, 166)));

        // Actions
        viewButton.addActionListener(e -> displayData());

        editButton.addActionListener(e -> {
            if (currentData != null) {
                new AddDataScreen(currentData, () -> {
                    new AddPersonScreen();
                });
                dispose();   
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentData != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this data?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    MainScreen.regionDatabase.remove(currentData);
                    resultArea.setText("Data deleted successfully.");
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    currentData = null;
                }
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new MainScreen();
        });

        JPanel filters = new JPanel(new GridBagLayout());
        filters.setBackground(UiTheme.PAGE);
        filters.setBorder(BorderFactory.createEmptyBorder(18, 20, 8, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        filters.add(UiTheme.label("Region", UiTheme.INK), gbc);
        gbc.gridx = 1;
        filters.add(UiTheme.label("Month", UiTheme.INK), gbc);
        gbc.gridx = 2;
        filters.add(UiTheme.label("Year", UiTheme.INK), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 1;
        filters.add(regionComboBox, gbc);
        gbc.gridx = 1;
        filters.add(monthComboBox, gbc);
        gbc.gridx = 2;
        filters.add(yearSpinner, gbc);
        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        filters.add(viewButton, gbc);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setBackground(UiTheme.PAGE);
        actions.setBorder(BorderFactory.createEmptyBorder(4, 20, 14, 20));
        actions.add(editButton);
        actions.add(deleteButton);
        actions.add(backButton);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiTheme.PAGE);
        top.add(filters, BorderLayout.NORTH);
        top.add(actions, BorderLayout.SOUTH);

        JPanel page = new JPanel(new BorderLayout(0, 0));
        page.setBackground(UiTheme.PAGE);
        page.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
        page.add(top, BorderLayout.NORTH);
        page.add(scrollPane, BorderLayout.CENTER);
        setContentPane(page);

        setSize(820, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayData() {
        String region = (String) regionComboBox.getSelectedItem();
        String month = (String) monthComboBox.getSelectedItem();
        Integer selectedYear = UiTheme.readYear(yearSpinner, this);
        if (selectedYear == null) {
            return;
        }
        int year = selectedYear;

        currentData = null;

        for (RegionData data : MainScreen.regionDatabase) {
            if (data.getRegion().equals(region)
                    && data.getMonth().equals(month)
                    && data.getYear() == year) {

                currentData = data;

                StringBuilder sb = new StringBuilder();
                sb.append("Region: ").append(region).append("\n");
                sb.append("Date: ").append(month).append(" ").append(year).append("\n\n");

                WarStats stats = data.getWarStats();
                sb.append("Martyrs: ").append(stats.getMartyrs()).append("\n");
                sb.append("Wounded: ").append(stats.getWounded()).append("\n");
                sb.append("Prisoners: ").append(stats.getPrisoners()).append("\n\n");

                HealthCareImpact health = data.getHealthImpact();
                sb.append("Hospitals Destroyed: ").append(health.getHospitalsDestroyed()).append("\n");
                sb.append("Untreated Patients: ").append(health.getUntreatedPatients()).append("\n\n");

                EducationImpact edu = data.getEduImpact();
                sb.append("Schools Destroyed: ").append(edu.getSchoolsDestroyed()).append("\n");
                sb.append("Displaced Students: ").append(edu.getDisplacedStudents()).append("\n\n");

                sb.append("Border Status: ").append(data.getBorderStatus().getStatus()).append("\n\n");

                ArrayList<WarVictim> victims = data.getVictims();
                if (victims.isEmpty()) {
                    sb.append("No recorded individuals (martyrs, wounded, prisoners).\n");
                } else {
                    sb.append("Victim List:\n");
                    for (String type : new String[]{"Martyr", "Wounded", "Prisoner"}) {
                        sb.append(" - ").append(type).append("s:\n");
                        boolean found = false;
                        for (WarVictim v : victims) {
                            if (v.getType().equalsIgnoreCase(type)) {
                                sb.append("    • ").append(v.getSummary()).append("\n");
                                found = true;
                            }
                        }
                        if (!found) sb.append("    • None\n");
                        sb.append("\n");
                    }
                }

                resultArea.setText(sb.toString());
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                return;
            }
        }

        resultArea.setText("No data found for this selection.");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}
