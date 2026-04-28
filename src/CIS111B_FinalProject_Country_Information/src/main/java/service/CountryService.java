package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Country;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountryService {

    private static final String BASE_URL =
            "https://restcountries.com/v3.1";

    // First call - main country info
    private static final String FIELDS_MAIN =
            "name,capital,region,population,area,flags,languages,currencies,timezones,cca2";

    // Second call - extra info
    private static final String FIELDS_EXTRA =
            "name,maps,idd,cca2";

    private final Gson gson = new Gson();

    // Fetches all countries or filters by region
    public List<Country> fetchCountries(String region)
            throws Exception {

        // First API call - main fields
        String endpoint;
        if (region == null || region.equals("All")) {
            endpoint = BASE_URL + "/all?fields=" + FIELDS_MAIN;
        } else {
            endpoint = BASE_URL + "/region/"
                    + region.toLowerCase()
                    + "?fields=" + FIELDS_MAIN;
        }

        String json = makeRequest(endpoint);
        Type listType =
                new TypeToken<List<Country>>() {}.getType();
        List<Country> countries =
                gson.fromJson(json, listType);

        if (countries == null) return new ArrayList<>();

        // Second API call - extra fields
        String extraEndpoint;
        if (region == null || region.equals("All")) {
            extraEndpoint = BASE_URL + "/all?fields=" + FIELDS_EXTRA;
        } else {
            extraEndpoint = BASE_URL + "/region/"
                    + region.toLowerCase()
                    + "?fields=" + FIELDS_EXTRA;
        }

        String extraJson = makeRequest(extraEndpoint);
        Type extraListType =
                new TypeToken<List<Country>>() {}.getType();
        List<Country> extraCountries =
                gson.fromJson(extraJson, extraListType);

        // Merge extra data into main countries list
        if (extraCountries != null) {
            for (Country country : countries) {
                for (Country extra : extraCountries) {
                    if (country.getCountryCode()
                            .equals(extra.getCountryCode())) {
                        country.setMaps(extra.getMapsObject());
                        country.setIdd(extra.getIddObject());
                        break;
                    }
                }
            }
        }

        countries.sort(
                Comparator.comparing(Country::getCommonName)
        );
        return countries;
    }

    // Searches for a country by name
    public List<Country> searchByName(String name)
            throws Exception {

        String endpoint = BASE_URL + "/name/"
                + name.trim().replace(" ", "%20")
                + "?fields=" + FIELDS_MAIN;

        String json = makeRequest(endpoint);
        Type listType =
                new TypeToken<List<Country>>() {}.getType();
        List<Country> results =
                gson.fromJson(json, listType);

        if (results == null) return new ArrayList<>();
        results.sort(
                Comparator.comparing(Country::getCommonName)
        );
        return results;
    }

    // Makes the HTTP request to the API
    private String makeRequest(String urlString)
            throws Exception {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty(
                    "Accept", "application/json"
            );

            int responseCode =
                    connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception(
                        "API returned HTTP " + responseCode
                );
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()
                    )
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();

        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}