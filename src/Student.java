
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.Component;
import java.sql.Connection;
import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anton
 */
public class Student extends javax.swing.JFrame {

    private Connection con;

    private Integer studentID;

    private String course;

    private String dropcourse;

    private Database db = new Database();
    
    /**
     * Creates new form Student
     */
    public Student(Connection passedcon, Integer studentID) {
        setConnection(passedcon);
        setStudentID(studentID);
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            }
        catch (Exception e) {
            e.printStackTrace();
            }
        initComponents();
        populateComboBoxes();
        addCorTableListener();
        addClassTableListener();
        corEnroll.setEnabled(false);
        dropButton.setEnabled(false);
        displayStuInfo();
        reloadClassList();
        /*coursesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int column = 0; column < coursesTable.getColumnCount(); column++) {
            TableColumn tableColumn = coursesTable.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();
            for (int row = 0; row < coursesTable.getRowCount(); row++) {
                TableCellRenderer cellRenderer = coursesTable.getCellRenderer(row, column);
                Component c = coursesTable.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + coursesTable.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth( preferredWidth );
        }
        this.revalidate();
        this.repaint();*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stuTabs = new javax.swing.JTabbedPane();
        stuInfoTab = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        majorLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        coursesScroll = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        dropButton = new javax.swing.JButton();
        courseTab = new javax.swing.JPanel();
        corIDLabel = new javax.swing.JLabel();
        corID = new javax.swing.JTextField();
        corNameLabel = new javax.swing.JLabel();
        corName = new javax.swing.JTextField();
        corTimeLabel = new javax.swing.JLabel();
        corTime = new javax.swing.JTextField();
        corRoomLabel = new javax.swing.JLabel();
        corRoom = new javax.swing.JTextField();
        corInstructorLabel = new javax.swing.JLabel();
        corInstructor = new javax.swing.JComboBox();
        corSizeLabel = new javax.swing.JLabel();
        corSize = new javax.swing.JTextField();
        corEnroll = new javax.swing.JButton();
        corSearch = new javax.swing.JButton();
        corClear = new javax.swing.JButton();
        corScroll = new javax.swing.JScrollPane();
        corTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 500));

        stuTabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stuTabsStateChanged(evt);
            }
        });

        nameLabel.setText("Name:");

        majorLabel.setText("Major:");

        levelLabel.setText("Level:");

        ageLabel.setText("Age:");

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Time", "Location", "Instructor", "Enrolled", "Maximum", "Exam 1 Score", "Exam 2 Score", "Final Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        coursesTable.getTableHeader().setReorderingAllowed(false);
        coursesScroll.setViewportView(coursesTable);
        if (coursesTable.getColumnModel().getColumnCount() > 0) {
            coursesTable.getColumnModel().getColumn(0).setMinWidth(50);
            coursesTable.getColumnModel().getColumn(1).setMinWidth(50);
            coursesTable.getColumnModel().getColumn(2).setMinWidth(50);
            coursesTable.getColumnModel().getColumn(3).setMinWidth(75);
            coursesTable.getColumnModel().getColumn(4).setMinWidth(85);
            coursesTable.getColumnModel().getColumn(5).setMinWidth(75);
            coursesTable.getColumnModel().getColumn(6).setMinWidth(85);
            coursesTable.getColumnModel().getColumn(7).setMinWidth(100);
            coursesTable.getColumnModel().getColumn(8).setMinWidth(100);
            coursesTable.getColumnModel().getColumn(9).setMinWidth(85);
        }

        dropButton.setText("Drop");
        dropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stuInfoTabLayout = new javax.swing.GroupLayout(stuInfoTab);
        stuInfoTab.setLayout(stuInfoTabLayout);
        stuInfoTabLayout.setHorizontalGroup(
            stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coursesScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
            .addGroup(stuInfoTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(levelLabel)
                    .addComponent(nameLabel))
                .addGap(272, 272, 272)
                .addGroup(stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stuInfoTabLayout.createSequentialGroup()
                        .addComponent(majorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dropButton))
                    .addComponent(ageLabel))
                .addContainerGap())
        );
        stuInfoTabLayout.setVerticalGroup(
            stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stuInfoTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(majorLabel))
                    .addComponent(dropButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stuInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel)
                    .addComponent(ageLabel))
                .addGap(18, 18, 18)
                .addComponent(coursesScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
        );

        stuTabs.addTab("Student Info", stuInfoTab);

        corIDLabel.setText("ID");

        corNameLabel.setText("Name");

        corTimeLabel.setText("Time");

        corRoomLabel.setText("Location");

        corInstructorLabel.setText("Instructor");

        corInstructor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        corSizeLabel.setText("Size Limit");

        corEnroll.setText("Enroll");
        corEnroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corEnrollActionPerformed(evt);
            }
        });

        corSearch.setText("Search");
        corSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corSearchActionPerformed(evt);
            }
        });

        corClear.setText("Clear");
        corClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corClearActionPerformed(evt);
            }
        });

        corTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Time", "Location", "Instructor", "Size Limit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        corScroll.setViewportView(corTable);

        javax.swing.GroupLayout courseTabLayout = new javax.swing.GroupLayout(courseTab);
        courseTab.setLayout(courseTabLayout);
        courseTabLayout.setHorizontalGroup(
            courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(corScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
            .addGroup(courseTabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(corName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(corTime, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corRoomLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(corRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corInstructorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(corInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(corID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                        .addComponent(corSizeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, courseTabLayout.createSequentialGroup()
                                .addComponent(corEnroll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(corSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(corClear))
                            .addComponent(corSize, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseTabLayout.setVerticalGroup(
            courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(courseTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corIDLabel)
                    .addComponent(corID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corNameLabel)
                    .addComponent(corName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corTimeLabel)
                    .addComponent(corTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corRoomLabel)
                    .addComponent(corRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corInstructorLabel)
                    .addComponent(corInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corSizeLabel)
                    .addComponent(corSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(courseTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corSearch)
                    .addComponent(corEnroll)
                    .addComponent(corClear))
                .addGap(18, 18, 18)
                .addComponent(corScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
        );

        stuTabs.addTab("Course Search", courseTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stuTabs, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stuTabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void populateComboBoxes() {
        Object[] facs = db.getFacList(con);
        for (Object fac : facs) {
            corInstructor.addItem(fac);
        }
    }

    private void clearComboBoxes() {
        while (corInstructor.getItemCount() > 1) {
            corInstructor.removeItemAt(1);
        }
    }

    private void addCorTableListener() {
        corTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        corTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    Integer selectedRow = lsm.getMinSelectionIndex();
                    Integer numcols = corTable.getColumnCount();
                    Hashtable<String,Object> colhash = new Hashtable<String, Object>();
                    for (int col=0; col < numcols; col++) {
                        String colname = corTable.getColumnName(col);
                        Object colcontent = corTable.getValueAt(selectedRow,col);
                        if (colcontent != null) {
                            colhash.put(colname, colcontent);
                        }
                    }
                    course = colhash.get("ID").toString();
                    corEnroll.setEnabled(true);
                }
            }
        });
    }

    private void addClassTableListener() {
        coursesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    Integer selectedRow = lsm.getMinSelectionIndex();
                    Integer numcols = coursesTable.getColumnCount();
                    Hashtable<String,Object> colhash = new Hashtable<String, Object>();
                    for (int col=0; col < numcols; col++) {
                        String colname = coursesTable.getColumnName(col);
                        Object colcontent = coursesTable.getValueAt(selectedRow,col);
                        if (colcontent != null) {
                            colhash.put(colname, colcontent);
                        }
                    }
                    dropcourse = colhash.get("ID").toString();
                    dropButton.setEnabled(true);
                }
            }
        });
    }

    private void reloadClassList() {
        cleartable(coursesTable);
        Object[][] stuClasses = db.getEnrolled(con,studentID);
        addtoTable(coursesTable, stuClasses);
        dropButton.setEnabled(false);
    }

    private void displayStuInfo () {
        Object[][] info = db.searchStu(con,studentID,"-1","-1","-1",-1);
        String name = info[0][1].toString();
        String major = info[0][2].toString();
        String s_level = info[0][3].toString();
        Integer age = Integer.parseInt(info[0][4].toString());
        nameLabel.setText("Name: "+name);
        majorLabel.setText("Major: "+major);
        levelLabel.setText("Level: "+s_level);
        ageLabel.setText("Age: "+age);
    }

    private void corSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corSearchActionPerformed
        String cid = "-1";
        String cname = "-1";
        String meets_at = "-1";
        String room = "-1";
        String fname = "-1";
        Integer limit = -1;
        if (!corID.getText().isEmpty()) {
            cid = corID.getText();
        }
        if (!corName.getText().isEmpty()) {
            cname = corName.getText();
        }
        if (!corTime.getText().isEmpty()) {
            meets_at = corTime.getText();
        }
        if (!corRoom.getText().isEmpty()) {
            room = corRoom.getText();
        }
        if (!corInstructor.getSelectedItem().toString().isEmpty()) {
            fname = corInstructor.getSelectedItem().toString();
        }
        if (!corSize.getText().isEmpty()) {
            limit = Integer.parseInt(corSize.getText());
        }
        // do search action
        Object[][] result = db.searchCor(con, cid, cname, meets_at, room, fname, limit);
        cleartable(corTable);
        addtoTable(corTable, result);
    }//GEN-LAST:event_corSearchActionPerformed

    private void corEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corEnrollActionPerformed
        if (course != null && !course.isEmpty() && db.isCourse(con,course)) {
            db.newEnrl(con,course,studentID,0,0,0);
        }
    }//GEN-LAST:event_corEnrollActionPerformed

    private void corClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corClearActionPerformed
        cleartable(corTable);
        clearCorForm();
        Object[][] allcor = db.searchCor(con);
        addtoTable(corTable,allcor);
        corEnroll.setEnabled(false);
    }//GEN-LAST:event_corClearActionPerformed

    private void dropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropButtonActionPerformed
        if (dropcourse != null && !dropcourse.isEmpty() && db.isCourse(con,dropcourse)) {
            db.delEnrl(con,dropcourse,studentID);
        }
        reloadClassList();
    }//GEN-LAST:event_dropButtonActionPerformed

    private void stuTabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stuTabsStateChanged
        JTabbedPane temp = (JTabbedPane) evt.getSource();
        int index = temp.getSelectedIndex();
        Database db = new Database();
        if (index == 0) {
            reloadClassList();
        }
        else if (index == 1) {
            Object[][] allcor = db.searchCor(con);
            cleartable(corTable);
            addtoTable(corTable,allcor);
            corEnroll.setEnabled(false);
        }
    }//GEN-LAST:event_stuTabsStateChanged

    private void cleartable (JTable table) {
        table.clearSelection();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int rowcount = tableModel.getRowCount();
        for (int idx = 0; idx < rowcount; idx++) {
            tableModel.removeRow(0);
        }
    }

    public void addtoTable (JTable table, Object[][] data) {
        table.clearSelection();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if (data != null) {
            for (Object[] row : data) {
                tableModel.addRow(row);
            }
        }
    }

    public void clearCorForm () {
        corID.setText("");
        corName.setText("");
        corTime.setText("");
        corRoom.setText("");
        corInstructor.setSelectedItem("");
        corSize.setText("");
    }

    public Connection getConnection () {
        return this.con;
    }

    public void setConnection (Connection passedcon) {
        this.con = passedcon;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ageLabel;
    private javax.swing.JButton corClear;
    private javax.swing.JButton corEnroll;
    private javax.swing.JTextField corID;
    private javax.swing.JLabel corIDLabel;
    private javax.swing.JComboBox corInstructor;
    private javax.swing.JLabel corInstructorLabel;
    private javax.swing.JTextField corName;
    private javax.swing.JLabel corNameLabel;
    private javax.swing.JTextField corRoom;
    private javax.swing.JLabel corRoomLabel;
    private javax.swing.JScrollPane corScroll;
    private javax.swing.JButton corSearch;
    private javax.swing.JTextField corSize;
    private javax.swing.JLabel corSizeLabel;
    private javax.swing.JTable corTable;
    private javax.swing.JTextField corTime;
    private javax.swing.JLabel corTimeLabel;
    private javax.swing.JPanel courseTab;
    private javax.swing.JScrollPane coursesScroll;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton dropButton;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JLabel majorLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel stuInfoTab;
    private javax.swing.JTabbedPane stuTabs;
    // End of variables declaration//GEN-END:variables
}
