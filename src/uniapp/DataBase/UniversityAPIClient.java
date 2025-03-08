package uniapp.DataBase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.List;
import java.util.ArrayList;
import uniapp.models.University;
import uniapp.models.Webpage;
import uniapp.models.Domain;

//Κλάση για ανάκτηση δεδομένων από το API
public class UniversityAPIClient {

    //Συνδέεται στο API της διεύθυνσης url, λαμβάνει δεδομένα και τα επιστέφει
    public static String getAPIData(String url) {
        System.out.println("🔍 Ανάκτηση δεδομένων από API: " + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseString = response.body().string();
                System.out.println("✅ Δεδομένα λήφθηκαν με επιτυχία από API.");
                System.out.println(responseString);
                return responseString;
            }
            System.out.println("⚠️ Αποτυχία λήψης δεδομένων από API.");
            return null;
        } catch (IOException e) {
            System.out.println("❌ Σφάλμα κατά τη λήψη δεδομένων από API: "
                                    + e.getMessage());
            return null;
        }
    }

    //Δημιουργεί το κατάλληλο url για αναζήτηση πανεπιστημίων 
    //  με όνομα nameString και χώρα countryString
    public static String getURL(String nameString, String countryString) {
        String url = "http://universities.hipolabs.com/search";
        if (!nameString.isBlank()) {
            url += "?name=" + nameString;
        }
        if (!countryString.isBlank()) {
            if (!nameString.isBlank()) {
                url += "&country=" + countryString;
            } else {
                url += "?country=" + countryString;
            }
        }
        System.out.println("🌍 Δημιουργήθηκε API URL: " + url);
        return url;
    }

    //Επιστρέφει λίστα με τα ονόματα πανεπιστημίων που βρίσκει 
    //στο API με αναζήτηση ονόματος nameString και χώρας countryString
    public static List<String> getNamesFromAPI(String nameString, String countryString) {
        System.out.println("🔎 Αναζήτηση πανεπιστημίων από API με όνομα: " 
                              + nameString + " και χώρα: " + countryString);
        //Παίρνει τα δεδομένα από το API και τα βάζει σε πίνακα JsonArray
        String url = getURL(nameString, countryString);
        String jsonString = getAPIData(url);
        
        //Αμυντικός προγραμματισμός
        //Ελέγχει αν ΔΕΝ επιστρέφει τίποτε
        if (jsonString == null) {
            System.out.println("⚠️ Δεν επιστράφηκαν δεδομένα από το API.");
            return new ArrayList<>();
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);

        //Παίρνει τα ονόματα πανεπιστημίων από το JsonArray και τα βάζει σε λίστα
        List<String> universityNames = new ArrayList<>();
        if (jsonArray != null && jsonArray.size() > 0) {
            for (JsonElement e : jsonArray) {
                JsonElement jsonElement = e.getAsJsonObject().get("name");
                String name = "";
                if (!jsonElement.isJsonNull()) {
                    name = jsonElement.getAsString();
                }
                if (!name.isBlank() && !universityNames.contains(name)) {
                    universityNames.add(name);
                }
            }
        }
        return universityNames;
    }

    //Επιστρέφει από το API τα δεδομένα ενός πανεπιστημίου με όνομα nameString    
    public static University getUniversityFromAPI(String nameString) {
        System.out.println("📡 Ανάκτηση στοιχείων πανεπιστημίου από API: " 
                                + nameString);
        //Παίρνει τα δεδομένα από το API και τα βάζει σε ένα JsonObject
        String url = getURL(nameString, "");
        String jsonString = getAPIData(url);
        
        //Αμυντικός προγραμματισμός
        //Ελέγχει αν ΔΕΝ επιστρέφει τίποτε
        if (jsonString == null) {
            System.out.println("⚠️ Δεν επιστράφηκαν δεδομένα από το API.");
            return null;
        }
                
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Τυπώνει στην κονσόλα το JSON
        System.out.println("📝 Ληφθέν JSON String:");
        System.out.println(jsonString);
        
        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        if (jsonArray == null || jsonArray.size() == 0) {
            System.out.println("⚠️ Δεν βρέθηκε πανεπιστήμιο με το όνομα: " 
                                    + nameString);
            return null;
        }
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

        //Παίρνει το όνομα, την περιοχή, την χώρα και τον κωδικό χώρας 
        // του πανεπιστημίου από το JSON
        JsonElement jsonElement = jsonObject.get("name");
        String name = "";
        if (!jsonElement.isJsonNull()) {
            name = jsonElement.getAsString();
        }
        jsonElement = jsonObject.get("state-province");
        String area = "";
        if (!jsonElement.isJsonNull()) {
            area = jsonElement.getAsString();
        }
        jsonElement = jsonObject.get("country");
        String country = "";
        if (!jsonElement.isJsonNull()) {
            country = jsonElement.getAsString();
        }
        jsonElement = jsonObject.get("alpha_two_code");
        String countryCode = "";
        if (!jsonElement.isJsonNull()) {
            countryCode = jsonElement.getAsString();
        }
        
        System.out.println("🏫 Πανεπιστήμιο βρέθηκε: " + name 
                                    + " (" + country + ")");
        
        //Δημιουργεί το πανεπιστήμιο με τα στοιχεία που πήρε
        University university = new University(
                            0, name, area, country, countryCode, "", 0, "");

        //Δημιουργεί αντικείμενο για τα domains 
        // και web pages του και τα προσθέτει στο πανεπιστήμιο
        JsonArray domains = jsonObject.get("domains").getAsJsonArray();
        for (JsonElement je : domains) {
            Domain domain = new Domain(0, je.getAsString());
            university.addDomain(domain);
            domain.setUniversityid(university);
        }
        JsonArray webPages = jsonObject.get("web_pages").getAsJsonArray();
        for (JsonElement je : webPages) {
            Webpage webpage = new Webpage(0, je.getAsString());
            university.addWebpage(webpage);
            webpage.setUniversityid(university);
        }

        //Επιστρέφει το πανεπιστήμιο
        System.out.println("✅ Το πανεπιστήμιο δημιουργήθηκε επιτυχώς από API δεδομένα.");
        return university;
    }
}
