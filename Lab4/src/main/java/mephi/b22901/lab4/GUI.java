/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
        JButton checkBtn = new JButton("Проверить склад");
        JButton addWandBtn = new JButton("Создать палочку");
        JButton avaliableWandsBtn = new JButton("Доступные палочки");
        checkBtn.setAlignmentX(CENTER_ALIGNMENT);
        addWandBtn.setAlignmentX(CENTER_ALIGNMENT);
        avaliableWandsBtn.setAlignmentX(CENTER_ALIGNMENT);

        storagePanel.add(checkBtn);
        storagePanel.add(Box.createVerticalGlue());
        storagePanel.add(addWandBtn);
        storagePanel.add(Box.createVerticalGlue());
        storagePanel.add(avaliableWandsBtn);
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

        checkBtn.addActionListener((e) -> {
            newSupply(controller.getStorage());
            if (controller.getStorage() == null) {
                JOptionPane.showMessageDialog(null, "Склад пуст, необходимы поставки!", null, JOptionPane.WARNING_MESSAGE);
            }
        });

        addWandBtn.addActionListener((e) -> {
            if (controller.getStorage() == null) {
                JOptionPane.showMessageDialog(null, "Склад пуст, необходимы поставки!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                addWand();
            }
        });

        avaliableWandsBtn.addActionListener((e) -> {
            List<Wand> wands = controller.getWandsAvaliable();
            if ((wands == null) || wands.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Доступных на складе нет, сначала создайте их!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                showWands(wands);
            }
        });

        sellBtn.addActionListener((e) -> {
            List<Wand> wands = controller.getWandsAvaliable();
            if ((wands == null) || wands.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Палочек на складе нет, сначала создайте их!", null, JOptionPane.WARNING_MESSAGE);
            } else {
                sellWand();
            }
        });

        clearBtn.addActionListener((e) -> {
            controller.clear();
        });

        splitPane.setDividerLocation(225);
        frame.add(splitPane);
        pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void sellWand() {
        int choice = JOptionPane.showConfirmDialog(null, "Клиент совершает покупку впервые?", null, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JFrame frame = new JFrame("Внесение данных о новом клиенте");
            frame.setSize(350, 200);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 0));

            JLabel nameLabel = new JLabel("Полное имя: ");
            JTextField nameValue = new JTextField(20);
            nameValue.setBorder(new LineBorder(Color.BLACK));
            panel.add(nameLabel);
            panel.add(nameValue);

            JLabel addressLabel = new JLabel("Адрес: ");
            JTextField addressValue = new JTextField(20);
            addressValue.setBorder(new LineBorder(Color.BLACK));
            panel.add(addressLabel);
            panel.add(addressValue);

            panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

            JPanel btnPanel = new JPanel();
            JButton okBtn = new JButton("Сохранить");
            okBtn.addActionListener((e) -> {
                controller.addNewPerson(nameValue.getText(), addressValue.getText());
                frame.dispose();
            });
            btnPanel.add(okBtn);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(btnPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        }

    }

    private void addWand() {
        JFrame frame = new JFrame("Добавление новой палочки");
        frame.setSize(350, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));
        String[] woodOptions = {};
        JComboBox woodValues = new JComboBox(woodOptions);
        JLabel woodLabel = new JLabel("Корпус: ");

        String[] coreOptions = {};
        JComboBox coreValues = new JComboBox(coreOptions);
        JLabel coreLabel = new JLabel("Сердцевины: ");

        panel.add(woodLabel);
        panel.add(woodValues);
        panel.add(coreLabel);
        panel.add(coreValues);

        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("Сохранить");
        okBtn.addActionListener((e) -> {
            controller.addNewWand();
            frame.dispose();
        });
        btnPanel.add(okBtn);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void showWands(List<Wand> wands) {
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
    }

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

        // Кастомный TableModel чтобы первый столбец был Boolean
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
        JButton selectBtn = new JButton("Получить выбранные ID");
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
                controller.addNewSupply(date, selectedID);
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "Поставка прошла успешно, компоненты доставлены на склад.", null, JOptionPane.INFORMATION_MESSAGE);
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
}
