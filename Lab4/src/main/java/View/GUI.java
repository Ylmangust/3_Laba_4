/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Component;
import Model.Customer;
import Model.Sale;
import Model.Wand;
import Controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Регина
 */
public class GUI extends JFrame {

    private Controller controller;

    public GUI(Controller ctrl) {
        this.controller = ctrl;
        JFrame frame = new JFrame("Волшебные палочки Оливандера");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 200);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        JButton supplyBtn = new JButton("Совершить поставку");
        JButton addWandBtn = new JButton("Создать палочку");
        JButton checkBtn = new JButton("Состояние склада");
        supplyBtn.setAlignmentX(CENTER_ALIGNMENT);
        addWandBtn.setAlignmentX(CENTER_ALIGNMENT);
        checkBtn.setAlignmentX(CENTER_ALIGNMENT);

        supplyBtn.addActionListener((e) -> {
            newSupply(controller.getStorage());
        });

        addWandBtn.addActionListener((e) -> {
            if (controller.getCompsAvaliable().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Склад пуст, необходимы поставки!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                addWand(controller.getCompsAvaliable());
            }
        });

        checkBtn.addActionListener((e) -> {
            storageInfo(controller.getStorage());

        });

        storagePanel.add(checkBtn);
        storagePanel.add(Box.createVerticalGlue());
        storagePanel.add(supplyBtn);
        storagePanel.add(Box.createVerticalGlue());
        storagePanel.add(addWandBtn);
        storagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        JButton sellBtn = new JButton("Продать палочку");
        JButton salesHistoryBtn = new JButton("История продаж");
        JButton clearBtn = new JButton("Очистить все данные");
        sellBtn.setAlignmentX(CENTER_ALIGNMENT);
        salesHistoryBtn.setAlignmentX(CENTER_ALIGNMENT);
        clearBtn.setAlignmentX(CENTER_ALIGNMENT);

        shopPanel.add(sellBtn);
        shopPanel.add(Box.createVerticalGlue());
        shopPanel.add(salesHistoryBtn);
        shopPanel.add(Box.createVerticalGlue());
        shopPanel.add(clearBtn);
        shopPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        splitPane.setLeftComponent(storagePanel);
        splitPane.setRightComponent(shopPanel);

        sellBtn.addActionListener((e) -> {
            List<Wand> wands = controller.getWandsAvaliable();
            if ((wands == null) || wands.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Палочек на складе нет, сначала создайте их!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                newPersonFrame();
            }
        });

        salesHistoryBtn.addActionListener((e) -> {
            List<Sale> sales = controller.getSalesHistory();
            if (sales == null || sales.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Зарегистрированных продаж нет!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                showSalesHistory(sales);
            }
        });

        clearBtn.addActionListener((e) -> {
            int choice = JOptionPane.showConfirmDialog(null, "Действие невозможно отменить. Вы уверены?", null, JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                controller.clear();
            }
        }
        );

        splitPane.setDividerLocation(225);
        frame.add(splitPane);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void sellWand(List<Wand> wands, List<Customer> clients) {
        if (clients != null) {
            JFrame frame = new JFrame("Продажа палочки");
            frame.setSize(450, 200);

            Object[] customers = clients.toArray(new Customer[0]);
            Object[] wandList = wands.toArray(new Wand[0]);

            JComboBox customerOptions = new JComboBox(customers);
            JLabel customerLabel = new JLabel("Покупатель: ");
            JPanel peoplePanel = new JPanel();
            peoplePanel.setLayout(new GridLayout(0, 2));
            peoplePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            peoplePanel.add(customerLabel);
            peoplePanel.add(customerOptions);

            JComboBox wandOptions = new JComboBox(wandList);
            JLabel wandLabel = new JLabel("Палочка: ");
            JPanel wandPanel = new JPanel();
            wandPanel.setLayout(new GridLayout(0, 2));
            wandPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            wandPanel.add(wandLabel);
            wandPanel.add(wandOptions);

            JPanel btnPanel = new JPanel();
            JButton okBtn = new JButton("Продать");
            okBtn.addActionListener((e) -> {
                Customer customer = (Customer) customerOptions.getSelectedItem();
                Wand wand = (Wand) wandOptions.getSelectedItem();
                LocalDate date = LocalDate.now();
                controller.addNewSale(customer.getID(), wand.getId(), date);
                JOptionPane.showMessageDialog(null, "Факт продажи успешно зафиксирован!", null, JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            });
            btnPanel.add(okBtn);
            btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.add(peoplePanel);
            centerPanel.add(wandPanel);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(btnPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Список клиентов пуст!", null, JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addWand(List<Component> comps) {
        JFrame frame = new JFrame("Добавление новой палочки");
        frame.setSize(350, 200);

        JPanel panelWood = new JPanel();
        panelWood.setLayout(new GridLayout(0, 2));
        panelWood.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelCore = new JPanel();
        panelCore.setLayout(new GridLayout(0, 2));
        panelCore.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        Map<Integer, Component> core = new HashMap<>();
        Map<Integer, Component> wood = new HashMap<>();

        for (int i = 0; i < comps.size(); i++) {
            if (comps.get(i).getId() < 25) {
                wood.put(comps.get(i).getId(), comps.get(i));
            } else {
                core.put(comps.get(i).getId(), comps.get(i));
            }
        }

        if (!wood.isEmpty() && !core.isEmpty()) {
            Object[] woodOptions = wood.values().toArray(new Component[0]);
            Object[] coreOptions = core.values().toArray(new Component[0]);

            JComboBox woodValues = new JComboBox(woodOptions);
            JLabel woodLabel = new JLabel("Корпус: ");

            JComboBox coreValues = new JComboBox(coreOptions);
            JLabel coreLabel = new JLabel("Сердцевины: ");

            panelWood.add(woodLabel);
            panelWood.add(woodValues);
            panelCore.add(coreLabel);
            panelCore.add(coreValues);

            JPanel btnPanel = new JPanel();
            JButton okBtn = new JButton("Сохранить");
            okBtn.addActionListener((e) -> {
                Component selectedCore = (Component) coreValues.getSelectedItem();
                Component selectedWood = (Component) woodValues.getSelectedItem();
                if (selectedCore != null && selectedWood != null) {
                    controller.addNewWand(selectedWood, selectedCore);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать оба компонента!", null, JOptionPane.WARNING_MESSAGE);
                }
            });
            btnPanel.add(okBtn);
            btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            centerPanel.add(panelWood);
            centerPanel.add(panelCore);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(btnPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Недостаточно компонентов, пополните склад!", null, JOptionPane.WARNING_MESSAGE);
        }

    }

    private void newPersonFrame() {
        int choice = JOptionPane.showConfirmDialog(null, "Клиент совершает покупку впервые?", null, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JFrame frame = new JFrame("Внесение данных о новом клиенте");
            frame.setSize(350, 200);
            JPanel namePanel = new JPanel();
            namePanel.setLayout(new GridLayout(0, 2));
            namePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            JLabel nameLabel = new JLabel("Полное имя: ");
            JTextField nameValue = new JTextField(20);
            nameValue.setBorder(new LineBorder(Color.BLACK));
            namePanel.add(nameLabel);
            namePanel.add(nameValue);

            JPanel addressPanel = new JPanel();
            addressPanel.setLayout(new GridLayout(0, 2));
            addressPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            JLabel addressLabel = new JLabel("Адрес: ");
            JTextField addressValue = new JTextField(20);
            addressValue.setBorder(new LineBorder(Color.BLACK));
            addressPanel.add(addressLabel);
            addressPanel.add(addressValue);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.add(namePanel);
            centerPanel.add(addressPanel);

            JPanel btnPanel = new JPanel();
            btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JButton okBtn = new JButton("Сохранить");
            okBtn.addActionListener((e) -> {
                controller.addNewPerson(nameValue.getText(), addressValue.getText());
                frame.dispose();
                sellWand(controller.getWandsAvaliable(), controller.getCustomers());
            });
            btnPanel.add(okBtn);

            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(btnPanel, BorderLayout.SOUTH);
            pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } else {
            sellWand(controller.getWandsAvaliable(), controller.getCustomers());
        }
    }

    /* private void showWands(List<Wand> wands) {
        JFrame frame = new JFrame("Доступные палочки");
        frame.setSize(450, 200);

        String[] columnNames = {"ID", "Древесина", "Сердцевина"};
        Object[][] data = new Object[wands.size()][3];
        for (int i = 0; i < wands.size(); i++) {
            Wand w = wands.get(i);
            data[i][0] = w.getId();
            data[i][1] = WandsComponents.getComponent(w.getWoodId());
            data[i][2] = WandsComponents.getComponent(w.getCoreId());
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }*/
    private void newSupply(List<Component> storage) {
        JFrame frame = new JFrame("Выбор компонентов для поставки");
        frame.setSize(600, 200);

        Object[][] data = new Object[storage.size()][4];
        String[] columnNames = {"Выбрать", "ID", "Компонент", "Количество на складе"};
        for (int i = 0; i < storage.size(); i++) {
            Component component = storage.get(i);
            data[i][0] = Boolean.FALSE;
            data[i][1] = component.getId();
            data[i][2] = component.getName();
            data[i][3] = component.getQuantity();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Integer.class;
                }
                return null;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Редактируем только чекбоксы
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(1).setPreferredWidth(5);
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        model.addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                int selectedCount = 0;
                int lastRowChanged = e.getFirstRow();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
                        selectedCount++;
                    }
                }
                if (selectedCount > 15) {
                    model.setValueAt(Boolean.FALSE, lastRowChanged, 0);
                    JOptionPane.showMessageDialog(frame, "Можно выбрать не более 15 компонентов!", null, JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        JPanel datePanel = new JPanel(new GridLayout(0, 2));
        datePanel.add(new JLabel("Дата поставки:"));
        JTextField dateValue = new JTextField("2025-12-05");
        datePanel.add(dateValue);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton selectBtn = new JButton("Подтвердить поставку");
        selectBtn.addActionListener(e -> {
            LocalDate date = null;
            try {
                date = LocalDate.parse(dateValue.getText(), formatter);
                List<Integer> selectedID = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    Boolean checked = (Boolean) model.getValueAt(i, 0);
                    if (Boolean.TRUE.equals(checked)) {
                        Integer id = (Integer) model.getValueAt(i, 1);
                        selectedID.add(id);
                    }
                }
                if (!selectedID.isEmpty()) {
                    controller.addNewSupply(date, selectedID);
                    JOptionPane.showMessageDialog(frame, "Поставка прошла успешно, компоненты доставлены на склад.", null, JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Компоненты не были выбраны, поставка не реализована.", null, JOptionPane.WARNING_MESSAGE);
                }

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Дата не соотвествует формату ГГГГ-ММ-ДД", null, JOptionPane.WARNING_MESSAGE);
            }

        });
        buttonPanel.add(selectBtn);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(datePanel);
        southPanel.add(buttonPanel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 180));
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void storageInfo(List<Component> components) {
        JFrame frame = new JFrame("Состояние склада");
        frame.setSize(600, 400);

        String[] columnNames = {"ID", "Компонент", "Количество на складе"};
        Object[][] data = new Object[components.size()][3];
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            data[i][0] = component.getId();
            data[i][1] = component.getName();
            data[i][2] = component.getQuantity();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void showSalesHistory(List<Sale> sales) {
        JFrame frame = new JFrame("История продаж");
        frame.setSize(700, 400);

        String[] columnNames = {"ID", "Покупатель", "Палочка", "Дата продажи"};
        Object[][] data = new Object[sales.size()][4];
        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            data[i][0] = sale.getSaleId();
            data[i][1] = sale.getCustName();
            data[i][2] = sale.getWandName();
            data[i][3] = sale.getDate();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane);
        //pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
