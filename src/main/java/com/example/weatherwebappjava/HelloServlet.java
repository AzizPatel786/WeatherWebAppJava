package com.example.weatherwebappjava;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // API Setup
        String myApiKey = "d89da33fb3f74f43e37a2f1755a7d546";
        String city = request.getParameter("city"); // Get the city from input
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + myApiKey;

        // Integrate API
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Establishing connection
        connection.setRequestMethod("GET"); // Get the data with the connection
    }

    public void destroy() {
    }
}