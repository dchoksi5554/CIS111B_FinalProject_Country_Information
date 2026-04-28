package model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Country {

    // Nested class for the name object in JSON
    public static class Name {
        private String common;
        private String official;

        public String getCommon()   { return common; }
        public String getOfficial() { return official; }
    }

    // Nested class for the flags object in JSON
    public static class Flags {
        private String png;

        public String getPng() { return png; }
    }

    // Nested class for each currency in JSON
    public static class Currency {
        private String name;
        private String symbol;

        public String getName()   { return name; }
        public String getSymbol() { return symbol; }

        @Override
        public String toString() {
            return name + " (" + symbol + ")";
        }
    }

    // Nested class for maps in JSON
    public static class Maps {
        private String googleMaps;

        public String getGoogleMaps() { return googleMaps; }
    }

    // Nested class for dial code in JSON
    public static class Idd {
        private String root;
        private List<String> suffixes;

        public String getFullDialCode() {
            if (root == null) return "N/A";
            if (suffixes != null && !suffixes.isEmpty()
                    && suffixes.size() == 1) {
                return root + suffixes.get(0);
            }
            return root;
        }
    }

    // ── Main fields ──
    private Name name;
    private List<String> capital;
    private String region;
    private String subregion;
    private long population;
    private double area;
    private Flags flags;
    private Map<String, String> languages;
    private Map<String, Currency> currencies;
    private Maps maps;
    private Idd idd;
    private List<String> timezones;

    @SerializedName("cca2")
    private String countryCode;

    // ── Getters ──

    public String getCommonName() {
        return name != null ? name.getCommon() : "N/A";
    }

    public String getOfficialName() {
        return name != null ? name.getOfficial() : "N/A";
    }

    public String getCapital() {
        if (capital != null && !capital.isEmpty())
            return capital.get(0);
        return "N/A";
    }

    public String getRegion() {
        return region != null ? region : "N/A";
    }

    public long getPopulation() { return population; }

    public String getFormattedPopulation() {
        if (population >= 1_000_000_000) {
            return String.format("%.2fB",
                    population / 1_000_000_000.0);
        } else if (population >= 1_000_000) {
            return String.format("%.2fM",
                    population / 1_000_000.0);
        } else if (population >= 1_000) {
            return String.format("%.1fK",
                    population / 1_000.0);
        }
        return String.valueOf(population);
    }

    public String getFormattedArea() {
        return String.format("%,.0f km²", area);
    }

    public String getFlagUrl() {
        return flags != null ? flags.getPng() : "";
    }

    public String getLanguages() {
        if (languages == null || languages.isEmpty())
            return "N/A";
        return String.join(", ", languages.values());
    }

    public String getCurrencies() {
        if (currencies == null || currencies.isEmpty())
            return "N/A";
        StringBuilder sb = new StringBuilder();
        for (Currency c : currencies.values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.toString());
        }
        return sb.toString();
    }

    public String getDialCode() {
        return idd != null ? idd.getFullDialCode() : "N/A";
    }

    public String getTimezones() {
        if (timezones == null || timezones.isEmpty())
            return "N/A";
        return timezones.get(0);
    }

    public String getAnthemSearchUrl() {
        return "https://www.youtube.com/results?search_query="
                + getCommonName().replace(" ", "+")
                + "+national+anthem";
    }

    public String getGoogleMapsUrl() {
        return maps != null ? maps.getGoogleMaps() : "";
    }

    public String getCountryCode() {
        return countryCode != null ? countryCode : "";
    }

    @Override
    public String toString() {
        return getCommonName();
    }

    public void setMaps(Maps maps) {
        this.maps = maps;
    }

    public void setIdd(Idd idd) {
        this.idd = idd;
    }


    public Maps getMapsObject() {
        return maps;
    }

    public Idd getIddObject() {
        return idd;
    }

}

