/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package umfst.oop.kurzusprojekt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author edite
 */
public class KurzusProjekt extends JFrame{

    // GUI Komponensek
    private JTabbedPane tabbedPane;
    
    // Listák fül
    private JTextArea listDisplayArea;
    
    // Jelmez kiadás fül
    private JList<Clothes> costumeList;
    private JList<Dancer> dancerList;
    private DefaultListModel<Clothes> costumeListModel;
    private DefaultListModel<Dancer> dancerListModel;

    /**
     * A GUI fő konstruktora.
     */
    public KurzusProjekt() {
        // Ablak alapbeállításai
        setTitle("Néptánc Projekt Menedzser");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Középre igazítás

        // Adatok betöltése indításkor
        try {
            TancProjekt.loadDataFromJson();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hiba a data.json betöltésekor: " + e.getMessage(), "Betöltési Hiba", JOptionPane.ERROR_MESSAGE);
        }

        // Fő füles elrendezés
        tabbedPane = new JTabbedPane();

        // 1. Fül: Adatkezelés (Hozzáadás)
        tabbedPane.addTab("Kezelés", createManagementPanel());

        // 2. Fül: Listázás
        tabbedPane.addTab("Listák", createListPanel());

        // 3. Fül: Jelmez Kiadás
        tabbedPane.addTab("Jelmez Kiadás", createAssignmentPanel());

        // Hozzáadjuk a füleket az ablakhoz
        add(tabbedPane);
        
        // Frissítjük a listákat a GUI-n
        refreshGuiLists();
    }

    /**
     * Létrehozza az "Kezelés" fület gombokkal.
     */
    private JPanel createManagementPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10)); // Egy oszlopos rács
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Belső margó

        JButton addDancerBtn = new JButton("Táncos hozzáadása...");
        addDancerBtn.addActionListener(e -> onAddDancer());
        panel.add(addDancerBtn);

        JButton addDanceBtn = new JButton("Tánc hozzáadása...");
        addDanceBtn.addActionListener(e -> onAddDance());
        panel.add(addDanceBtn);

        JButton addCostumeBtn = new JButton("Jelmez hozzáadása...");
        addCostumeBtn.addActionListener(e -> onAddCostume());
        panel.add(addCostumeBtn);

        JButton addEventBtn = new JButton("Esemény hozzáadása...");
        addEventBtn.addActionListener(e -> onAddEvent());
        panel.add(addEventBtn);

        JButton addChoreographerBtn = new JButton("Koreográfus hozzáadása...");
        addChoreographerBtn.addActionListener(e -> onAddChoreographer());
        panel.add(addChoreographerBtn);

        JButton addStaffBtn = new JButton("Stábtag hozzáadása...");
        addStaffBtn.addActionListener(e -> onAddStaff());
        panel.add(addStaffBtn);
        
        panel.add(new JSeparator()); // Elválasztó
        
        JButton saveBtn = new JButton("Mentés (JSON)");
        saveBtn.addActionListener(e -> {
            TancProjekt.saveDataToJson();
            JOptionPane.showMessageDialog(this, "Adatok sikeresen mentve a data.json fájlba!");
        });
        panel.add(saveBtn);

        return panel;
    }

    /**
     * Létrehozza a "Listák" fület.
     */
    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        listDisplayArea = new JTextArea();
        listDisplayArea.setEditable(false);
        listDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listDisplayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Gomb panel a listázáshoz
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton listDancersBtn = new JButton("Táncosok");
        listDancersBtn.addActionListener(e -> listDancers());
        buttonPanel.add(listDancersBtn);

        JButton listDancesBtn = new JButton("Táncok");
        listDancesBtn.addActionListener(e -> listDances());
        buttonPanel.add(listDancesBtn);
        
        JButton listCostumesBtn = new JButton("Jelmezek");
        listCostumesBtn.addActionListener(e -> listCostumes());
        buttonPanel.add(listCostumesBtn);
        
        JButton listEventsBtn = new JButton("Események");
        listEventsBtn.addActionListener(e -> listEvents());
        buttonPanel.add(listEventsBtn);
        
        JButton listAllPeopleBtn = new JButton("Minden Személy (Poly)");
        listAllPeopleBtn.addActionListener(e -> listAllPeople());
        buttonPanel.add(listAllPeopleBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Létrehozza a "Jelmez Kiadás" fület.
     */
    private JPanel createAssignmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Listák modellek
        costumeListModel = new DefaultListModel<>();
        dancerListModel = new DefaultListModel<>();

        // Listák
        costumeList = new JList<>(costumeListModel);
        dancerList = new JList<>(dancerListModel);

        costumeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dancerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Görgethetővé tesszük őket
        JScrollPane costumeScroll = new JScrollPane(costumeList);
        costumeScroll.setBorder(BorderFactory.createTitledBorder("Válassz Jelmezt"));
        
        JScrollPane dancerScroll = new JScrollPane(dancerList);
        dancerScroll.setBorder(BorderFactory.createTitledBorder("Válassz Táncost"));

        // Egy osztott panelre tesszük a két listát
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, costumeScroll, dancerScroll);
        splitPane.setResizeWeight(0.5); // Egyenlő arányban ossza el a helyet
        panel.add(splitPane, BorderLayout.CENTER);

        // Gomb panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton assignButton = new JButton("Hozzárendelés");
        assignButton.addActionListener(e -> onAssignCostume());
        
        JButton refreshButton = new JButton("Listák frissítése");
        refreshButton.addActionListener(e -> refreshGuiLists());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(assignButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    // --- Akciókezelő Metódusok (Adatbevitel) ---

    private void onAddDancer() {
        // Létrehozunk egy panelt a dialógusablakhoz
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(20, 10, 80, 1));
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"leader", "member", "beginner", "expert"});
        JSpinner dancesSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));

        formPanel.add(new JLabel("Név:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Kor:"));
        formPanel.add(ageSpinner);
        formPanel.add(new JLabel("Szerepkör:"));
        formPanel.add(roleCombo);
        formPanel.add(new JLabel("Ismert táncok:"));
        formPanel.add(dancesSpinner);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Táncos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = (Integer) ageSpinner.getValue();
                String role = (String) roleCombo.getSelectedItem();
                int dances = (Integer) dancesSpinner.getValue();
                
                if(name.isEmpty()) throw new Exception("A név nem lehet üres!");

                // Meghívjuk a logikai osztályt
                TancProjekt.createDancer(name, age, role, dances);
                JOptionPane.showMessageDialog(this, "Táncos hozzáadva!");
                refreshGuiLists(); // Frissítjük a listákat
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void onAddDance() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JTextField regionField = new JTextField(15);
        JSpinner lengthSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));

        formPanel.add(new JLabel("Tánc Neve:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Tájegység:"));
        formPanel.add(regionField);
        formPanel.add(new JLabel("Hossz (perc):"));
        formPanel.add(lengthSpinner);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Tánc", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String name = nameField.getText();
                String region = regionField.getText();
                int length = (Integer) lengthSpinner.getValue();
                if(name.isEmpty() || region.isEmpty()) throw new Exception("A mezők nem lehetnek üresek!");
                
                TancProjekt.createDance(name, region, length);
                JOptionPane.showMessageDialog(this, "Tánc hozzáadva!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void onAddCostume() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JComboBox<Size> sizeCombo = new JComboBox<>(Size.values());

        formPanel.add(new JLabel("Jelmez Neve:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Méret:"));
        formPanel.add(sizeCombo);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Jelmez", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String name = nameField.getText();
                Size size = (Size) sizeCombo.getSelectedItem();
                if(name.isEmpty()) throw new Exception("A név nem lehet üres!");

                TancProjekt.createCostume(name, size);
                JOptionPane.showMessageDialog(this, "Jelmez hozzáadva!");
                refreshGuiLists();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void onAddEvent() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JTextField placeField = new JTextField(15);
        JTextField dateField = new JTextField("2025-12-31");

        formPanel.add(new JLabel("Esemény Neve:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Helyszín:"));
        formPanel.add(placeField);
        formPanel.add(new JLabel("Dátum (ÉÉÉÉ-HH-NN):"));
        formPanel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Esemény", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String name = nameField.getText();
                String place = placeField.getText();
                Date date = Date.valueOf(dateField.getText()); // Vigyázat, ez hibát dobhat!
                
                if(name.isEmpty() || place.isEmpty()) throw new Exception("A mezők nem lehetnek üresek!");

                TancProjekt.createEvent(name, date, place);
                JOptionPane.showMessageDialog(this, "Esemény hozzáadva!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba (pl. rossz dátum formátum): " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void onAddChoreographer() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(30, 18, 80, 1));
        JSpinner expSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 60, 1));

        formPanel.add(new JLabel("Név:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Kor:"));
        formPanel.add(ageSpinner);
        formPanel.add(new JLabel("Tapasztalat (év):"));
        formPanel.add(expSpinner);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Koreográfus", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String name = nameField.getText();
                int age = (Integer) ageSpinner.getValue();
                int exp = (Integer) expSpinner.getValue();
                if(name.isEmpty()) throw new Exception("A név nem lehet üres!");

                TancProjekt.createChoreographer(name, age, exp);
                JOptionPane.showMessageDialog(this, "Koreográfus hozzáadva!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void onAddStaff() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(15);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 80, 1));
        JTextField deptField = new JTextField("Logisztika");

        formPanel.add(new JLabel("Név:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Kor:"));
        formPanel.add(ageSpinner);
        formPanel.add(new JLabel("Részleg:"));
        formPanel.add(deptField);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Új Stábtag", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
             try {
                String name = nameField.getText();
                int age = (Integer) ageSpinner.getValue();
                String dept = deptField.getText();
                if(name.isEmpty() || dept.isEmpty()) throw new Exception("A mezők nem lehetnek üresek!");

                TancProjekt.createStaff(name, age, dept);
                JOptionPane.showMessageDialog(this, "Stábtag hozzáadva!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- Akciókezelő Metódusok (Listázás) ---

    private void listDancers() {
        StringBuilder sb = new StringBuilder("--- Táncosok Listája ---\n");
        for (Dancer d : TancProjekt.getDancers()) {
            sb.append(d.toString()).append("\n");
        }
        listDisplayArea.setText(sb.toString());
    }
    
    private void listDances() {
        StringBuilder sb = new StringBuilder("--- Táncok Listája ---\n");
        for (Dance d : TancProjekt.getDances()) {
            sb.append(d.toString()).append("\n");
        }
        listDisplayArea.setText(sb.toString());
    }

    private void listCostumes() {
        StringBuilder sb = new StringBuilder("--- Jelmezek Listája ---\n");
        for (Clothes c : TancProjekt.getCostumes()) {
            sb.append(c.toString()).append("\n");
        }
        listDisplayArea.setText(sb.toString());
    }
    
    private void listEvents() {
        StringBuilder sb = new StringBuilder("--- Események Listája ---\n");
        for (Event e : TancProjekt.getEvents()) {
            sb.append(e.toString()).append("\n");
        }
        listDisplayArea.setText(sb.toString());
    }
    
    private void listAllPeople() {
        listDisplayArea.setText(TancProjekt.getAllPeopleIntros());
    }

    // --- Akciókezelő Metódusok (Hozzárendelés) ---
    
    /**
     * Frissíti a GUI-n lévő listákat (Jelmez kiadás fül)
     */
    private void refreshGuiLists() {
        // Jelmezek frissítése
        costumeListModel.clear();
        for (Clothes c : TancProjekt.getCostumes()) {
            if(c.getAssignedTo() == null) { // Csak a szabad jelmezeket listázzuk
                costumeListModel.addElement(c);
            }
        }

        // Táncosok frissítése
        dancerListModel.clear();
        for (Dancer d : TancProjekt.getDancers()) {
            dancerListModel.addElement(d);
        }
    }
    
    private void onAssignCostume() {
        Clothes selectedCostume = costumeList.getSelectedValue();
        Dancer selectedDancer = dancerList.getSelectedValue();

        if (selectedCostume == null || selectedDancer == null) {
            JOptionPane.showMessageDialog(this, "Válassz ki egy jelmezt ÉS egy táncost!", "Hiba", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Logika hívása
            TancProjekt.assignCostumeLogic(selectedCostume, selectedDancer);
            JOptionPane.showMessageDialog(this, "Sikeres hozzárendelés:\n" + selectedCostume.getName() + " -> " + selectedDancer.getName());
            
            // Frissítjük a GUI listákat
            refreshGuiLists();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * A program belépési pontja.
     */
    public static void main(String[] args) {
        // A Swing alkalmazásokat az Event Dispatch Thread-en (EDT) illik indítani.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KurzusProjekt().setVisible(true);
            }
        });
    }
}
