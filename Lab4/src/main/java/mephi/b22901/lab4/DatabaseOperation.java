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
import javax.swing.JOptionPane;

/**
 *
 * @author Регина
 */
public class DatabaseOperation {

    private final String url = "jdbc:postgresql://aws-0-eu-north-1.pooler.supabase.com:5432/postgres";
    private final String user = "postgres.enjmuwzyvkbkogqnbspm";
    private final String password = "@Mechanic%2925";
    private String[] tables = {"supplies", "sales", "customers", "wands", "storage"};
    private String[] fKeys = {"id_supply", "id_sale", "id_client", "id_wand", "id_component"};
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
                readCustomers();
                readWands();
                readSales();
                readSupplies();
                readStorage();
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            System.exit(0);
        }
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

    private void readCustomers() {
        if (!tableIsEmpty("customers")) {
            if (customers != null) {
                customers.clear();
            }
            customers = new ArrayList<>();
            String query = "SELECT id_client, full_name, address FROM customers";
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
    }

    private void readWands() {
        if (!tableIsEmpty("wands")) {
            if (wands != null) {
                wands.clear();
            }
            wands = new ArrayList<>();
            String queryWands = "SELECT "
                    + "    w.id_wand, "
                    + "    w.id_wood, "
                    + "    wood.name AS wood_name, "
                    + "w.id_core, "
                    + "core.name AS core_name, "
                    + "w.is_sold "
                    + "FROM wands w "
                    + "JOIN storage wood ON w.id_wood = wood.id_component "
                    + "JOIN storage core ON w.id_core = core.id_component;";
            try (PreparedStatement statement = connection.prepareStatement(queryWands); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_wand");
                    int wood = resultSet.getInt("id_wood");
                    int core = resultSet.getInt("id_core");
                    String woodName = resultSet.getString("wood_name");
                    String coreName = resultSet.getString("core_name");
                    boolean isSold = resultSet.getBoolean("is_sold");
                    wands.add(new Wand(id, wood, core, isSold, woodName, coreName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void readSales() {
        if (!tableIsEmpty("sales")) {
            if (sales != null) {
                sales.clear();
            }
            sales = new ArrayList<>();
            String query = "SELECT "
                    + "s.id_sale, "
                    + "c.full_name AS customer_name, "
                    + "w.id_wand, "
                    + "(wood.name || '+' || core.name) AS wand_name, "
                    + "s.date "
                    + "FROM sales s "
                    + "JOIN customers c ON s.id_customer = c.id_client "
                    + "JOIN wands w ON s.id_wand = w.id_wand "
                    + "JOIN storage wood ON w.id_wood = wood.id_component "
                    + "JOIN storage core ON w.id_core = core.id_component "
                    + "ORDER BY s.id_sale ASC;";

            try (PreparedStatement querySales = connection.prepareStatement(query); ResultSet resultSet = querySales.executeQuery()) {
                while (resultSet.next()) {
                    int saleId = resultSet.getInt("id_sale");
                    String customerName = resultSet.getString("customer_name");
                    String wandName = resultSet.getString("wand_name");
                    Date saleDate = resultSet.getDate("date");
                    sales.add(new Sale(saleId, customerName, wandName, saleDate.toString()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void readSupplies() {
        if (!tableIsEmpty("supplies")) {
            if (supplies != null) {
                supplies.clear();
            }
            supplies = new ArrayList<>();
            String querySupply = "SELECT id_supply, date, components FROM supplies";
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

    private void readStorage() {
        if (!tableIsEmpty("storage")) {
            if (storage != null) {
                storage.clear();
            }
            storage = new ArrayList<>();
            String queryStore = "SELECT id_component, name, quantity FROM storage ORDER BY id_component ASC";
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
        String query = "INSERT INTO customers(full_name,  address) VALUES (?,?)";
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
        readCustomers();
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

    public void addNewWand(Component wood, Component core) {
        String query = "INSERT INTO wands(id_wood, id_core, is_sold) VALUES (?,?, false)";
        String updateStorage = "UPDATE storage SET quantity = quantity - 1 WHERE id_component = ?";
        PreparedStatement updateStatement = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            updateStatement = connection.prepareStatement(updateStorage);
            statement.setInt(1, wood.getId());
            statement.setInt(2, core.getId());
            statement.executeUpdate();
            updateStatement.setInt(1, wood.getId());
            updateStatement.executeUpdate();
            updateStatement.setInt(1, core.getId());
            updateStatement.executeUpdate();
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
        readWands();
        readStorage();
    }

    public void addNewSale(int customerID, int wandID, LocalDate date) {
        String saleQuery = "INSERT INTO sales(id_customer, id_wand, date) VALUES (?,?,?)";
        String updateWands = "UPDATE wands SET is_sold = TRUE WHERE id_wand = ?";
        PreparedStatement statement = null;
        PreparedStatement updateStatement = null;
        try {
            statement = connection.prepareStatement(saleQuery);
            updateStatement = connection.prepareStatement(updateWands);
            statement.setInt(1, customerID);
            statement.setInt(2, wandID);
            statement.setDate(3, java.sql.Date.valueOf(date));
            statement.executeUpdate();
            updateStatement.setInt(1, wandID);
            updateStatement.executeUpdate();
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
        readSales();
        readWands();
    }

    public void clearData() {
        for (int i = 0; i < 5; i++) {
            if (!tableIsEmpty(tables[i]) && (i != 4)) {
                try {
                    String queryTable = "TRUNCATE " + tables[i] + " CASCADE";
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
                    ex.printStackTrace();
                }

            } else {
                try {
                    String queryTable = "UPDATE storage SET quantity = 0";
                    PreparedStatement statement = connection.prepareStatement(queryTable);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        readCustomers();
        readSales();
        readStorage();
        readSupplies();
        readWands();
    }

    public List<Customer> getCustomers() {
        return customers;
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

    public List<Component> getComponentsAvaliable() {
        List<Component> avaliable = null;
        if (storage != null) {
            avaliable = new ArrayList<>();
            for (Component comp : storage) {
                if (comp.getQuantity() != 0) {
                    avaliable.add(comp);
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

}
