/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author N0741707
 */
public class Functions {

    public static JSONObject getIdentifier() throws NullPointerException {

        InputStream databaseFile = Functions.class.getResourceAsStream("../identifier.json");
        JSONTokener databaseTokener = new JSONTokener(databaseFile);
        JSONObject databaseRoot = new JSONObject(databaseTokener);

        return databaseRoot;
    }

    public static void registerStation() throws SocketException, IOException {
        JSONObject json = new JSONObject();
        Date date = new Date();
        JSONObject identifier = getIdentifier();

        if (identifier.getString("stationId").isEmpty()) {

            json.put("stationId", UUID.randomUUID().toString());

            FileWriter file = new FileWriter("C:\\Users\\Callum\\Documents\\WeatherStation\\src\\identifier.json");
            try {
                file.write(json.toString());
            } catch (IOException e) {
            } finally {
                file.flush();
                file.close();
            }
        } else {
            json.put("stationId", UUID.randomUUID().toString());
        }
        json.put("lastContact", String.valueOf(date.getTime()));
        json.put("method", "registerStation");
        try {
            Reciever.sendData(json);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void updateStationValues() throws SocketException, IOException {
        JSONObject json = new JSONObject();
        Date date = new Date();
        JSONObject identifier = getIdentifier();

        json.put("stationId", identifier.getString("stationId"));
        json.put("lastContact", date.getTime());
        json.put("method", "updateStationValues");
        try {
            Reciever.sendData(json);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}