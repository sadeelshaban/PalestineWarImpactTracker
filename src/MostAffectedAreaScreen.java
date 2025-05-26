package First;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Displays statistical analysis like most and least affected regions based on war data.
 */
public class MostAffectedAreaScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea resultArea;

    public MostAffectedAreaScreen() {
        super("War Impact Summary");

        JLabel titleLabel = new JLabel("War impact summary");
        titleLabel.setFont(UiTheme.SUBTITLE);
        titleLabel.setForeground(UiTheme.INK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(22, 24, 12, 24));

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        UiTheme.styleField(resultArea);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(196, 186, 166)));

        JButton analyzeButton = UiTheme.button("Run analysis", UiTheme.Kind.GOLD);
        analyzeButton.addActionListener(e -> runAnalysis());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomPanel.setBackground(UiTheme.PAGE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(14, 24, 18, 24));
        bottomPanel.add(analyzeButton);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UiTheme.PAGE);
        center.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        center.add(scrollPane, BorderLayout.CENTER);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(UiTheme.PAGE);
        page.add(titleLabel, BorderLayout.NORTH);
        page.add(center, BorderLayout.CENTER);
        page.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(page);

        setSize(680, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void runAnalysis() {
        ArrayList<RegionData> data = MainScreen.regionDatabase;

        String mostAffected = DataAnalyzer.getMostAffectedRegion(data);
        String leastAffected = DataAnalyzer.getLeastAffectedRegion(data);
        Map<String, Integer> martyrsPerRegion = DataAnalyzer.countAllMartyrs(data);
        Map<String, Set<String>> borderSummary = DataAnalyzer.getBorderStatusSummary(data);

        StringBuilder sb = new StringBuilder();
        sb.append("🔴 Most Affected Region: ").append(mostAffected).append("\n");
        sb.append("🟢 Least Affected Region: ").append(leastAffected).append("\n\n");

        sb.append("📊 Total Martyrs Per Region:\n");
        for (Map.Entry<String, Integer> entry : martyrsPerRegion.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" martyrs\n");
        }

        sb.append("\n🧱 Border Status Summary:\n");
        for (Map.Entry<String, Set<String>> entry : borderSummary.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        resultArea.setText(sb.toString());
    }
}
