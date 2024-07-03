package org.william.database;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConvertJavaToJson {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerDetails customerDetails = mapper.readValue(new File("D:\\William's programming projects\\sdet-architect-essentials-demo\\src\\main\\resources\\jsonFiles\\customerDetails0.json"),CustomerDetails.class);
        System.out.println(customerDetails.getCourseName());
    }
}
