import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class api {
    public static void main(String[] args) {
        String secretKey = "your_secret_key_here";
        List<Integer> numbers = Arrays.asList(1,1,2,2,3,3,4,4,5,5,5); // Example list of numbers

        // Calculate statistics
        int count = numbers.size();
        int minimum = Collections.min(numbers);
        int maximum = Collections.max(numbers);
        api mainObj = new api();
        double mean = mainObj.calculateMean(numbers);
        double median = mainObj.calculateMedian(numbers);
        List<Integer> mode = mainObj.calculateMode(numbers);

        // Prepare payload for POST request
        StringBuilder modeBuilder = new StringBuilder();
        for (Integer num : mode) {
            modeBuilder.append(num).append(",");
        }
        String modeString = modeBuilder.toString();
        if (modeString.endsWith(",")) {
            modeString = modeString.substring(0, modeString.length() - 1);
        }

        String payload = "{\"secret_key\":\"" + secretKey + "\"," +
                "\"count\":" + count + "," +
                "\"minimum\":" + minimum + "," +
                "\"maximum\":" + maximum + "," +
                "\"mean\":" + mean + "," +
                "\"median\":" + median + "," +
                "\"mode\":[" + modeString + "]}";

        try {
            URL url = new URL("https://postman-echo.com/post");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            System.out.println("Response Body: " + response.toString());

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     double calculateMean(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.size();
    }

    double calculateMedian(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);

        if (sortedNumbers.size() % 2 == 0) {
            int middleIndex = sortedNumbers.size() / 2;
            int median1 = sortedNumbers.get(middleIndex - 1);
            int median2 = sortedNumbers.get(middleIndex);
            return (double) (median1 + median2) / 2;
        } else {
            int middleIndex = sortedNumbers.size() / 2;
            return sortedNumbers.get(middleIndex);
        }
    }

    List<Integer> calculateMode(List<Integer> numbers) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int maxFrequency = 0;

        for (int number : numbers) {
            int frequency = frequencyMap.getOrDefault(number, 0) + 1;
            frequencyMap.put(number, frequency);
            maxFrequency = Math.max(maxFrequency, frequency);
        }

        List<Integer> modeList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modeList.add(entry.getKey());
            }
        }

        return modeList;
    }
}
