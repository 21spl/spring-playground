package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {
    

    public static void createSchema(Connection conn) throws Exception{
        
        // DDL - Data Definition Language
        String ddl = """
                create table item(
                    id bigint primary key,
                    name varchar(255),
                    price double
                )
                """;
        
        Statement stmt = conn.createStatement();
        stmt.execute(ddl);
        System.out.println("Table ITEM Created");
        
    }


    public static void insertItems(Connection conn, Long id, String name, double price) throws Exception{

        // DML 
        String dml = "insert into item(id, name, price) values (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(dml);

        ps.setLong(1, id);
        ps.setString(2, name);
        ps.setDouble(3, price);
        ps.executeUpdate();

        System.out.println("Inserted 1 item");

        ps.close();
    }

    public static List<Item> findAllItems(Connection conn) throws Exception{

        // DQL
        String dql = "Select id, name, price from item";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(dql);

        List<Item> items = new ArrayList<>();

        // Manual mapping
        while(rs.next()){
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            items.add(new Item(id, name, price));
        }

        rs.close();
        stmt.close();
        return items;
    }

    public static void main(String[] args)throws Exception{
        
        Connection conn = DriverManager.getConnection(
            "jdbc:h2:mem:testdb",
            "sa",
            ""
        );

        // create the schema
        createSchema(conn);

        // insert item 1
        insertItems(conn, 1L, "Vintage Camera", 420.00);
        
        // insert item 2
        insertItems(conn, 2L, "Mechanical Keyboard", 100.00);

        // find all items
        List<Item> items = findAllItems(conn);

        // print the items
        System.out.println(items);
    }
}
