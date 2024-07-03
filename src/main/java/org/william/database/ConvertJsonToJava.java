package org.william.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ConvertJsonToJava {
    static ArrayList<CustomerDetails> customerDetailsArrayList = new ArrayList<>();
    static JSONArray jsonArray = new JSONArray();
    static JSONObject jsonObject = new JSONObject();
    static Gson gson = new Gson();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection mySqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/business", "root", "root");
        Statement statement = mySqlConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");
        CustomerDetails customerDetails = null;
        while (resultSet.next()) {
            customerDetails = new CustomerDetails();
            customerDetails.setCourseName(resultSet.getString("courseName"));
            customerDetails.setPurchaseDate(String.valueOf(resultSet.getDate("PurchasedDate")));
            customerDetails.setAmount(resultSet.getInt("Amount"));
            customerDetails.setLocation(resultSet.getString("Location"));
            customerDetailsArrayList.add(customerDetails);
        }
        for(int index = 0; index < customerDetailsArrayList.size(); index++) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("D:\\William's programming projects\\sdet-architect-essentials-demo\\src\\main\\resources\\jsonFiles\\customerDetails"+index+".json"), customerDetailsArrayList.get(index));
            String gsonObject = gson.toJson(customerDetailsArrayList.get(index));
            jsonArray.add(gsonObject);
        }
        jsonObject.put("data", jsonArray);
        String jsonEscapedCharacters = StringEscapeUtils.unescapeJava(jsonObject.toJSONString());
        String jsonEscapedOpenQuotes = jsonEscapedCharacters.replace("\"{\"", "{\"");
        String jsonEscapedClosedQuotes = jsonEscapedOpenQuotes.replace("\"}\"", "\"}");
        System.out.println(jsonEscapedClosedQuotes);
        mySqlConnection.close();
    }
}