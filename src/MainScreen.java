package First;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    public static ArrayList<RegionData> regionDatabase = new ArrayList<>();

    private JComboBox<String> regionComboBox;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;

    public MainScreen() {
        super("Palestine War Impact Tracker");

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setLayout(new BorderLayout());

        String[] regions = {"Gaza", "West Bank", "Jerusalem"};
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        regionComboBox = new JComboBox<>(regions);
        monthComboBox = new JComboBox<>(months);
        yearSpinner = UiTheme.yearSpinner();
        UiTheme.styleField(regionComboBox);
        UiTheme.styleField(monthComboBox);

        JButton addButton = UiTheme.button("Add War Data", UiTheme.Kind.GOLD);
        JButton viewButton = UiTheme.button("View Data", UiTheme.Kind.DARK);
        JButton addPersonButton = UiTheme.button("Add Person", UiTheme.Kind.DARK);
        JButton statsButton = UiTheme.button("Impact Summary", UiTheme.Kind.DARK);
        JButton backButton = UiTheme.button("Back", UiTheme.Kind.DARK);

         
        addButton.addActionListener(e -> {
            String region = (String) regionComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();
            Integer year = UiTheme.readYear(yearSpinner, this);
            if (year == null) {
                return;
            }
            new AddDataScreen(region, month, year);
        });

        viewButton.addActionListener(e -> new ViewDataScreen());
        addPersonButton.addActionListener(e -> new AddPersonScreen());
        statsButton.addActionListener(e -> new MostAffectedAreaScreen());
        backButton.addActionListener(e -> {
            dispose(); 
            new WelcomeScreen();   
        });

        JPanel card = UiTheme.overlayCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 6, 0);

        JLabel heading = new JLabel("Track a region", SwingConstants.CENTER);
        heading.setFont(UiTheme.TITLE);
        heading.setForeground(UiTheme.GOLD_SOFT);
        card.add(heading, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(14, 0, 6, 0);
        card.add(UiTheme.label("Region", Color.WHITE), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(regionComboBox, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 6, 12);
        card.add(UiTheme.label("Month", Color.WHITE), gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(8, 12, 6, 0);
        card.add(UiTheme.label("Year", Color.WHITE), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 16, 12);
        card.add(monthComboBox, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 12, 16, 0);
        card.add(yearSpinner, gbc);

        JButton[][] rows = {
                {addButton, viewButton},
                {addPersonButton, statsButton}
        };
        for (JButton[] row : rows) {
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.insets = new Insets(6, 0, 6, 8);
            card.add(row[0], gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(6, 8, 6, 0);
            card.add(row[1], gbc);
        }

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 0, 0, 0);
        card.add(backButton, gbc);

        JPanel formWrap = new JPanel(new GridBagLayout());
        formWrap.setOpaque(false);
        formWrap.setBorder(BorderFactory.createEmptyBorder(28, 80, 28, 80));
        GridBagConstraints wrap = new GridBagConstraints();
        wrap.fill = GridBagConstraints.HORIZONTAL;
        wrap.weightx = 1;
        formWrap.add(card, wrap);

        backgroundLabel.add(formWrap, BorderLayout.CENTER);

        setContentPane(backgroundLabel);
        setSize(1000, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
