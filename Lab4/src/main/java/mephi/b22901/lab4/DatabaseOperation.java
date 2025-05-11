/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Регина
 */
public class DatabaseOperation {

    private final String url = "jdbc:postgresql://localhost:5432/OlivanderShop";
    private final String user = "postgres";
    private final String password = "123";
    private String[] tables = {"customers", "wands", "storage", "supplies", "sales"};
    private String[] fKeys = {"id_client", "id_wand", "id_component", "id_supply", "id_sale"};
    private Connection connection = null;
    private List<Customer> customers = null;
    private List<Wand> wands = null;
    private List<Component> storage = null;
    private List<Sale> sales = null;
    private List<Supply> supplies = null;

    public DatabaseOperation() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                JOptionPane.showMessageDialog(null, "Вы успешно подключились к базе данных!", null, JOptionPane.INFORMATION_MESSAGE);
                if (tablesAreEmpty()) {
                    JOptionPane.showMessageDialog(null, "Все таблицы пусты!", null, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    readFromDatabase();
                    readStorage();
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    private boolean tablesAreEmpty() {
        boolean allTablesEmpty = true;
        try {
            for (String tableName : tables) {
                String query = "SELECT COUNT(*) AS row_count FROM " + tableName;
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet countResultSet = statement.executeQuery();
                if (countResultSet.next()) {
                    int rowCount = countResultSet.getInt("row_count");
                    if (rowCount > 0) {
                        allTablesEmpty = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTablesEmpty;
    }

    private boolean tableIsEmpty(String table) {
        boolean tablesEmpty = true;
        try {
            String query = "SELECT COUNT(*) AS row_count FROM " + table;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet countResultSet = statement.executeQuery();
            if (countResultSet.next()) {
                int rowCount = countResultSet.getInt("row_count");
                if (rowCount > 0) {
                    tablesEmpty = false;
                }

            }
        } catch (Exception e) {

        }
        return tablesEmpty;
    }

    private void readFromDatabase() {
        if (!tableIsEmpty(tables[0])) {
            customers = new ArrayList<>();
            String query = "SELECT id_client, full_name, address FROM " + tables[0];
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_client");
                    String name = resultSet.getString("full_name");
                    String address = resultSet.getString("address");
                    customers.add(new Customer(id, name, address));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!tableIsEmpty(tables[1])) {
            wands = new ArrayList<>();
            String queryWands = "SELECT id_wand, id_wood, id_core, is_sold FROM " + tables[1];
            try (PreparedStatement statement = connection.prepareStatement(queryWands); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_wand");
                    int wood = resultSet.getInt("id_wood");
                    int core = resultSet.getInt("id_core");
                    boolean isSold = resultSet.getBoolean("is_sold");
                    System.out.println(new Wand(id, wood, core, isSold).toString());
                    wands.add(new Wand(id, wood, core, isSold));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
       

        if (!tableIsEmpty(tables[4])) {
            sales = new ArrayList<>();
            String querySales = "SELECT id_sale, id_customer, id_палочки, date  FROM " + tables[4];
            try (PreparedStatement statement = connection.prepareStatement(querySales); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int saleId = resultSet.getInt("id_sale");
                    int custId = resultSet.getInt("id_customer");
                    int wandId = resultSet.getInt("id_wand");
                    String date = resultSet.getString("date");
                    sales.add(new Sale(saleId, custId, wandId, date));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!tableIsEmpty(tables[3])) {
            supplies = new ArrayList<>();
            String querySupply = "SELECT id_supply, date, components FROM " + tables[3];
            try (PreparedStatement statement = connection.prepareStatement(querySupply); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_supply");
                    String date = resultSet.getString("date");
                    int component = resultSet.getInt("components");
                    supplies.add(new Supply(id, date, component));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void readStorage(){
        if (!tableIsEmpty(tables[2])) {
            if (storage != null){
                storage.clear();
            }
            storage = new ArrayList<>();
            String queryStore = "SELECT id_component, name, quantity FROM " + tables[2];
            try (PreparedStatement statement = connection.prepareStatement(queryStore); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_component");
                    String name = resultSet.getString("name");
                    int quantity = resultSet.getInt("quantity");
                    storage.add(new Component(id, name, quantity));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPerson(String name, String address) {
        String query = "INSERT INTO customers(full_name, address) VALUES (?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNewSupply(LocalDate date, List<Integer> id) {
        String query = "INSERT INTO supplies(date, components) VALUES (?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(date));
            for (int i = 0; i < id.size(); i++) {
                statement.setInt(2, id.get(i));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        readStorage();
    }

    private void endSession() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Successfull close connection!");
            }
        } catch (SQLException e) {
            System.out.println("Close connection error: " + e.getMessage());
        }
    }

    public void clearData() {
        for (int i = 0; i < 5; i++) {
            if (!tablesAreEmpty() && (i != 2)) {
                try {
                    String queryTable = "DELETE FROM " + tables[i];
                    PreparedStatement statement = connection.prepareStatement(queryTable);
                    statement.executeUpdate();
                    try (PreparedStatement ps = connection.prepareStatement("SELECT pg_get_serial_sequence(?, ?)")) {
                        ps.setString(1, tables[i]);
                        ps.setString(2, fKeys[i]);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String seqName = rs.getString(1);
                            PreparedStatement alter = connection.prepareStatement("ALTER SEQUENCE " + seqName + " RESTART WITH 1");
                            alter.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseOperation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Wand> getWands() {
        return wands;
    }

    public List<Wand> getWandsAvaliable() {
        List<Wand> avaliable = null;
        if (wands != null) {
            avaliable = new ArrayList<>();
            for (Wand wand : wands) {
                if (wand.getIsSold() == false) {
                    avaliable.add(wand);
                }
            }
        }
        return avaliable;
    }

    public List<Component> getStorage() {
        return storage;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

}
