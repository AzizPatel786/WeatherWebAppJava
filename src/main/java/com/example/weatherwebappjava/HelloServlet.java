package com.example.weatherwebappjava;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // API Setup
        String myApiKey = "d89da33fb3f74f43e37a2f1755a7d546";
        String city = request.getParameter("city"); // Get the city from input
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + myApiKey;
        try {
            // Integrate API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Establishing connection
            connection.setRequestMethod("GET"); // Get the data with the connection

            // Read data from network
            InputStream inputStream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            // Store the data that is read
            StringBuilder responseContent = new StringBuilder();

            // Scan the data from the reader line by line
            Scanner scanner = new Scanner(streamReader);
            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }
            scanner.close();

            // Parse weather data from string to JSON
            Gson gson = new Gson(); // Allows the JSON data into tree model
            JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);

            // Extracting Date and Time
            long dateTimeStamp = jsonObject.get("dt").getAsLong() * 1000; //ms to sec
            String date = new Date(dateTimeStamp).toString();
            // Extracting temperature
            double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble(); // In the main object there is another temp obj
            int temperatureCelsius = (int) (temperatureKelvin - 273.15);
            // Extracting Humidity
            int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
            // Extracting Wind Speed
            double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
            // Extracting Weather Condition
            String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").toString();
            // Set the data as request attributes (for sending to the jsp page)
            request.setAttribute("date", date);
            request.setAttribute("city", city);
            request.setAttribute("temperature", temperatureCelsius);
            request.setAttribute("weatherCondition", weatherCondition);
            request.setAttribute("humidity", humidity);
            request.setAttribute("windSpeed", windSpeed);
            request.setAttribute("weatherData", responseContent.toString());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Forward the request to the weather.jsp page for rendering
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

//    private static InputStreamReader getInputStreamReader(String city, String myApiKey) throws IOException {
//        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + myApiKey;
//
//        // Integrate API
//        URL url = new URL(apiUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Establishing connection
//        connection.setRequestMethod("GET"); // Get the data with the connection
//
//        // Read data from network
//        InputStream inputStream = connection.getInputStream();
//        connection.disconnect();
//        return new InputStreamReader(inputStream);
//    }

    public void destroy() {
    }
}