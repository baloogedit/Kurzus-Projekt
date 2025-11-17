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

    // GUI components
    private JTabbedPane tabbedPane;
    
    // list tab
    private JTextArea listDisplayArea;
    
    // costume assignment tab
    private JList<Clothes> costumeList;
    private JList<Dancer> dancerList;
    private DefaultListModel<Clothes> costumeListModel;
    private DefaultListModel<Dancer> dancerListModel;
    
    // dance details tab
    private JList<Dance> danceSelectorList;
    private DefaultListModel<Dance> danceSelectorListModel;
    private JTextArea danceDescribeArea;
    
    // change dancer role tab
    private JList<Dancer> dancerRoleList;
    private DefaultListModel<Dancer> dancerRoleListModel;
    private JComboBox<String> roleChangeCombo;
    private JTextField roleChangeReasonField;

    
    // GUI contructor
    public KurzusProjekt() {
        // the window basic setting
        setTitle("Néptánc Projekt Menedzser");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // align in center

        // load data when starting
        try {
            TancProjekt.loadDataFromJson();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hiba a data.json betöltésekor: " + e.getMessage(), "Betöltési Hiba", JOptionPane.ERROR_MESSAGE);
        }

        // main tab layout
        tabbedPane = new JTabbedPane();

        // 1.tab - management(add)
        tabbedPane.addTab("Kezelés", createManagementPanel());

        // 2.tab - listing
        tabbedPane.addTab("Listák", createListPanel());

        // 3.tab - costume assignment
        tabbedPane.addTab("Jelmez Kiadás", createAssignmentPanel());

        // tab 4 - dance details
        tabbedPane.addTab("Dance Details", createDescribeDancePanel());
        
        // tab 5 - change role
        tabbedPane.addTab("Change Role", createChangeRolePanel());
        
        // add tabs to window
        add(tabbedPane);
        
        // refresh the lists of GUI
        refreshGuiLists();
    }

    // create the managing tab with buttons
    private JPanel createManagementPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10)); // single-column grid layout
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // inner margin

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
        
        panel.add(new JSeparator()); 
        
        JButton saveBtn = new JButton("Mentés (JSON)");
        saveBtn.addActionListener(e -> {
            TancProjekt.saveDataToJson();
            JOptionPane.showMessageDialog(this, "Adatok sikeresen mentve a data.json fájlba!");
        });
        panel.add(saveBtn);

        return panel;
    }

    // create the listing tab
    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        listDisplayArea = new JTextArea();
        listDisplayArea.setEditable(false);
        listDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listDisplayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // button panel(row) for listing
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

    // create the costume assign tab
    private JPanel createAssignmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // list models
        costumeListModel = new DefaultListModel<>();
        dancerListModel = new DefaultListModel<>();

        // lists
        costumeList = new JList<>(costumeListModel);
        dancerList = new JList<>(dancerListModel);

        costumeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dancerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // make it scrollable
        JScrollPane costumeScroll = new JScrollPane(costumeList);
        costumeScroll.setBorder(BorderFactory.createTitledBorder("Válassz Jelmezt"));
        
        JScrollPane dancerScroll = new JScrollPane(dancerList);
        dancerScroll.setBorder(BorderFactory.createTitledBorder("Válassz Táncost"));

        // put them on a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, costumeScroll, dancerScroll);
        splitPane.setResizeWeight(0.5); // Egyenlő arányban ossza el a helyet
        panel.add(splitPane, BorderLayout.CENTER);

        // button panel
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
    
    // create dance describe tab
    private JPanel createDescribeDancePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // list model and list
        danceSelectorListModel = new DefaultListModel<>();
        danceSelectorList = new JList<>(danceSelectorListModel);
        danceSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(danceSelectorList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Select Dance"));

        // output area
        danceDescribeArea = new JTextArea();
        danceDescribeArea.setEditable(false);
        danceDescribeArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        danceDescribeArea.setWrapStyleWord(true);
        danceDescribeArea.setLineWrap(true);
        JScrollPane textScroll = new JScrollPane(danceDescribeArea);
        textScroll.setBorder(BorderFactory.createTitledBorder("Description"));

        // split pane for list and text area
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScroll, textScroll);
        splitPane.setResizeWeight(0.4); // Give 40% to the list
        panel.add(splitPane, BorderLayout.CENTER);

        // 4. button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Refresh List");
        refreshBtn.addActionListener(e -> refreshGuiLists()); // re-use the existing refresh method
        buttonPanel.add(refreshBtn);

        JButton performBtn = new JButton("Perform");
        performBtn.addActionListener(e -> onDescribeDance("perform"));
        buttonPanel.add(performBtn);

        JButton originBtn = new JButton("Show Origin");
        originBtn.addActionListener(e -> onDescribeDance("origin"));
        buttonPanel.add(originBtn);

        JButton durationBtn = new JButton("Show Duration");
        durationBtn.addActionListener(e -> onDescribeDance("duration"));
        buttonPanel.add(durationBtn);

        JButton styleBtn = new JButton("Describe Style");
        styleBtn.addActionListener(e -> onDescribeDance("style"));
        buttonPanel.add(styleBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    // create the role change tab
    private JPanel createChangeRolePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // dancer list model and list
        dancerRoleListModel = new DefaultListModel<>();
        dancerRoleList = new JList<>(dancerRoleListModel);
        dancerRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(dancerRoleList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Select Dancer"));
        
        // control panel for selection
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("New Role Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // new role label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        controlPanel.add(new JLabel("New Role:"), gbc);

        // new role combo box
        // same roles as onAddDancer method
        roleChangeCombo = new JComboBox<>(new String[]{"leader", "member", "beginner", "expert"});
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(roleChangeCombo, gbc);

        // reason label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        controlPanel.add(new JLabel("Reason (optional):"), gbc);

        // reason text field
        roleChangeReasonField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(roleChangeReasonField, gbc);
        
        // filler
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        controlPanel.add(new JPanel(), gbc);

        // split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, controlPanel);
        splitPane.setResizeWeight(0.5);
        panel.add(splitPane, BorderLayout.CENTER);
        
        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> refreshGuiLists());
        buttonPanel.add(refreshButton);
        
        JButton applyButton = new JButton("Apply Role Change");
        applyButton.addActionListener(e -> onChangeRole());
        buttonPanel.add(applyButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    
    
    
    // data input methods
    private void onAddDancer() {
        // create panel for dialog window
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

                // call for logical class(TancProjekt.java)
                TancProjekt.createDancer(name, age, role, dances);
                JOptionPane.showMessageDialog(this, "Táncos hozzáadva!");
                refreshGuiLists(); // refresh lists
            } 
            catch (InvalidRoleException ex) {
                // handling custom exception
                String msg = "Hibás szerepkör: " + ex.getInvalidRoleInput() + "\n" +
                             "Kérlek válassz egyet ezek közül: " + ex.getAllowedRoles();
                JOptionPane.showMessageDialog(this, msg, "Szerepkör Hiba", JOptionPane.WARNING_MESSAGE);
            } 
            catch (Exception ex) {
                // generic error handling - fallback
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
                Date date = Date.valueOf(dateField.getText()); // potential cause of error (catch)
                
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

    
    // listing methods
    private void listDancers() {
        StringBuilder sb = new StringBuilder("--- Táncosok Listája (Összesen: " + Dancer.getDancerCount() + ")---\n");
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

    
    // refresh gui lists
    private void refreshGuiLists() {
        
        // refresh costumes
        costumeListModel.clear();
        for (Clothes c : TancProjekt.getCostumes()) {
            if(c.getAssignedTo() == null) { // only list costumes eith no assigned Dancer
                costumeListModel.addElement(c);
            }
        }

        // refresh dancers (costume tab)
        dancerListModel.clear();
        for (Dancer d : TancProjekt.getDancers()) {
            dancerListModel.addElement(d);
        }
        
        // refresh dancers (change role tab)
        // update list, so it reflects the new role
        if (dancerRoleListModel != null) {
            dancerRoleListModel.clear();
            for (Dancer d : TancProjekt.getDancers()) {
                dancerRoleListModel.addElement(d); // show the role
            }
        }
        
        // refresh dance details list
        if (danceSelectorListModel != null) { // check if it's initialized
            danceSelectorListModel.clear();
            for (Dance d : TancProjekt.getDances()) {
                danceSelectorListModel.addElement(d);
            }
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
            // call the logic
            TancProjekt.assignCostumeLogic(selectedCostume, selectedDancer);
            JOptionPane.showMessageDialog(this, "Sikeres hozzárendelés:\n" + selectedCostume.getName() + " -> " + selectedDancer.getName());
            
            // refresh GUI lists
            refreshGuiLists();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hiba: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // handle button clicks on dance details tab
    private void onDescribeDance(String action) {
        Dance selectedDance = danceSelectorList.getSelectedValue();
        
        if (selectedDance == null) {
            JOptionPane.showMessageDialog(this, "Please select a dance from the list first!", "No Dance Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String description = "";
        switch (action) {
            case "perform":
                description = selectedDance.getPerform(); 
                break;
            case "origin":
                description = selectedDance.getOrigin();
                break;
            case "duration":
                description = selectedDance.getDuration();
                break;
            case "style":
                description = selectedDance.getStyleDescription();
                break;
        }
        
        danceDescribeArea.setText(description);
    }
    
    
    // handle button click on role change tab
    private void onChangeRole() {
        Dancer selectedDancer = dancerRoleList.getSelectedValue();
        String newRole = (String) roleChangeCombo.getSelectedItem();
        String reason = roleChangeReasonField.getText();

        if (selectedDancer == null) {
            JOptionPane.showMessageDialog(this, "Please select a dancer from the list!", "Hiba", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (newRole == null) {
            JOptionPane.showMessageDialog(this, "Please select a new role!", "Hiba", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedDancer.getRole().equalsIgnoreCase(newRole)) {
            JOptionPane.showMessageDialog(this, "The dancer already has that role.", "Információ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            // call logic method
            TancProjekt.changeDancerRoleLogic(selectedDancer, newRole, reason);
            JOptionPane.showMessageDialog(this, "Success! " + selectedDancer.getName() + "'s role has been changed to " + newRole + ".");
            
            // clear the reason field
            roleChangeReasonField.setText("");
            
            // refresh all GUI lists to show the change
            refreshGuiLists();
        }
        catch (InvalidRoleException ex) {
            // specialized handling
            System.err.println("Role mismatch! User tried: " + ex.getInvalidRoleInput());
            JOptionPane.showMessageDialog(this, 
                "Nem sikerült a szerepkör váltás.\nÉrvénytelen: " + ex.getInvalidRoleInput(), 
                "Érvénytelen Adat", 
                JOptionPane.WARNING_MESSAGE);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error changing role: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    


    /**
     * the program entry point
     */
    public static void main(String[] args) {
        // the swing applications run on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KurzusProjekt().setVisible(true);
            }
        });
    }
}
