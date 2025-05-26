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

        JLabel titleLabel = new JLabel("War Impact Analysis Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JButton analyzeButton = new JButton("Run Analysis");
        analyzeButton.setBackground(new Color(60, 60, 60));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        analyzeButton.addActionListener(e -> runAnalysis());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(analyzeButton);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(650, 550);
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
