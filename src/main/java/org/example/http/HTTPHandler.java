package org.example.http;


import com.fasterxml.jackson.core.type.TypeReference;
import org.example.company.Company;
import org.example.dumper.impl.JsonDumper;
import org.example.dumper.impl.XmlDumper;
import org.example.shapes.Shape;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPHandler {
    static Map<String, Map<String, Method>> endpoints = new HashMap<>();

    public HTTPHandler() {
        registerEndpoints();
    }

    public String dispatch(String request) throws InvocationTargetException, IllegalAccessException {
        String[] lines = request.split("\r\n");
        if (lines.length > 0) {
            String requested_method = lines[0].split(" ")[0];
            String url = lines[0].split(" ")[1];
            for (String path : endpoints.keySet()) {
                if (url.startsWith(path)) {
                    Map<String, Method> allowedMethods = endpoints.get(path);
                    if (allowedMethods.containsKey(requested_method)) {
                        Map<String, String> params = parseQueryParams(url);
                        return (String) allowedMethods.get(requested_method).invoke(null, params);
                    }
                }
            }
        }
        return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
    }

    @Request(method = "GET", path = "/workers")
    public static String getWorkers(Map<String, String> params) {
        String format = params.getOrDefault("format", "json");

        StringBuilder responseBody = new StringBuilder("<html><head>\r\n" + "  <meta charset=\"UTF-8\">\r\n"
                + "  <title>Workers</title>\r\n" + "</head><body>");
        switch (format) {
                case "json":
                    JsonDumper<Company> jsonDumper = new JsonDumper<>();
                    responseBody.append(jsonDumper.dump(jsonDumper.load(Path.of(".", "company.json"), Company.class)));
                    responseBody.append("</body></html>");
                    return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + responseBody.length() + "\r\n\r\n" + responseBody;

                case "xml":
                    XmlDumper<Company> xmlDumper = new XmlDumper<>();
                    responseBody.append(xmlDumper.dump(xmlDumper.load(Path.of(".", "company.xml"), Company.class)));
                    responseBody.append("</body></html>");
                    return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + responseBody.length() + "\r\n\r\n" + responseBody;
        }
        return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
    }

    @Request(method = "GET", path = "/shapes")
    public static String getShapes(Map<String, String> params) {
        String format = params.getOrDefault("format", "json");
        String shapeFilter = params.getOrDefault("filter", null);

        XmlDumper<List<Shape>> xmlDumper = new XmlDumper<>();
        List<Shape> shapes = xmlDumper.load(Path.of(".", "shapes.xml"), new TypeReference<List<Shape>>() {});
        StringBuilder responseBody = new StringBuilder("<html><head>\r\n" + "  <meta charset=\"UTF-8\">\r\n"
                + "  <title>Shapes</title>\r\n" + "</head><body>");

        List<Shape> filteredShapes = new ArrayList<>();
        for (Shape shape : shapes) {
                    if (shapeFilter == null || shape.getClass().getSimpleName().toLowerCase().contains(shapeFilter.toLowerCase())) {
                        filteredShapes.add(shape);
                    }
                }

        if (shapeFilter != null) {
            shapes = filteredShapes;
        }

        switch (format) {
            case "json":
                JsonDumper<List<Shape>> jsonDumper = new JsonDumper<>();
                responseBody.append(jsonDumper.dump(shapes));
                responseBody.append("</body></html>");
                return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + responseBody.length() + "\r\n\r\n" + responseBody;

            case "xml":
                responseBody.append(xmlDumper.dump(shapes));
                responseBody.append("</body></html>");
                return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + responseBody.length() + "\r\n\r\n" + responseBody;
        }
        return "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
    }

    private static Map<String, String> parseQueryParams(String url) {
        Map<String, String> params = new HashMap<>();
        String query = url.contains("?") ? url.substring(url.indexOf("?") + 1) : "";
        if (!query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 1) {
                    params.put(keyValue[0], URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
                } else if (keyValue.length == 1 && !keyValue[0].isEmpty()) {
                    params.put(keyValue[0], "");
                }
            }
        }
        return params;
    }

    private void registerEndpoints() {
        Method[] methods = HTTPHandler.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Request.class)) {
                String path = method.getAnnotation(Request.class).path();

                endpoints.putIfAbsent(path, new HashMap<>());

                Map<String, Method> methodMap = endpoints.get(path);
                methodMap.putIfAbsent(method.getAnnotation(Request.class).method(), method);
                endpoints.put(path, methodMap);
            }
        }
    }
}