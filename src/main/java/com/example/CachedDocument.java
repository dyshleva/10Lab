package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CachedDocument implements Document {
    private static final String DB_URL = "jdbc:sqlite:/Users/my.bd";
    private Document doc;

    public CachedDocument(Document document) {
        this.doc = document;
    }

    @Override
    public String parse() {
        String cachedResult = finder(this.getPath());
        if (cachedResult != null) {
            return cachedResult;
        }

        String result = doc.parse();
        saver(this.getPath(), result);
        return result;
    }

    private String finder(String url) {
        String st = "SELECT ... FROM your table WHERE path = ...";

        try (Connection CONN = DriverManager.getConnection(DB_URL);
                PreparedStatement PS = CONN.prepareStatement(st)) {
            PS.setString(1, url);
            try (ResultSet RS = PS.executeQuery()) {
                if (RS.next()) {
                    return RS.getString("result");
                }
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return null;
    }

    private void saver(String url, String result) {
        String st = "SELECT ... FROM your table WHERE path = ...";
        try (Connection CONN = DriverManager.getConnection(DB_URL);
                PreparedStatement PS = CONN.prepareStatement(st)) {
            PS.setString(1, url);
            PS.setString(2, result);
            PS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getgcsPath() {
        if (doc instanceof SmartDocument) {
            SmartDocument smartDoc = (SmartDocument) doc;
            return smartDoc.getgcsPath();
        }
        return null;
    }
}
