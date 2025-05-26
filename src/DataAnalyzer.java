package First;

import java.util.*;

/**
 * Utility class for analyzing war data across all regions.
 */
public class DataAnalyzer {

    // Returns the region with the highest number of martyrs
    public static String getMostAffectedRegion(ArrayList<RegionData> dataList) {
        Map<String, Integer> regionTotals = new HashMap<>();

        for (RegionData data : dataList) {
            String region = data.getRegion();
            int martyrs = data.getWarStats().getMartyrs();
            regionTotals.put(region, regionTotals.getOrDefault(region, 0) + martyrs);
        }

        return regionTotals.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
    }

    // Returns the region with the least total martyrs
    public static String getLeastAffectedRegion(ArrayList<RegionData> dataList) {
        Map<String, Integer> regionTotals = new HashMap<>();

        for (RegionData data : dataList) {
            String region = data.getRegion();
            int martyrs = data.getWarStats().getMartyrs();
            regionTotals.put(region, regionTotals.getOrDefault(region, 0) + martyrs);
        }

        return regionTotals.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
    }

    // Sort victims by age descending (oldest first)
    public static List<WarVictim> sortVictimsByAge(ArrayList<RegionData> dataList) {
        List<WarVictim> all = new ArrayList<>();
        for (RegionData r : dataList) {
            all.addAll(r.getVictims());
        }

        all.sort((v1, v2) -> Integer.compare(v2.getAge(), v1.getAge()));
        return all;
    }

    // Get total martyrs count per region
    public static Map<String, Integer> countAllMartyrs(ArrayList<RegionData> dataList) {
        Map<String, Integer> regionTotals = new HashMap<>();
        for (RegionData data : dataList) {
            String region = data.getRegion();
            int martyrs = data.getWarStats().getMartyrs();
            regionTotals.put(region, regionTotals.getOrDefault(region, 0) + martyrs);
        }
        return regionTotals;
    }

    // Summary of border status per region
    public static Map<String, Set<String>> getBorderStatusSummary(ArrayList<RegionData> dataList) {
        Map<String, Set<String>> result = new HashMap<>();
        for (RegionData data : dataList) {
            String region = data.getRegion();
            String status = data.getBorderStatus().getStatus();

            result.putIfAbsent(region, new HashSet<>());
            result.get(region).add(status);
        }
        return result;
    }
}
