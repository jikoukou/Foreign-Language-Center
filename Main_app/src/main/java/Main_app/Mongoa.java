package Main_app;

import static Main_app.Mongoa.cursor;
import static Main_app.Mongoa.db;
import static Main_app.Mongoa.table;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Mongoa {
    static JTable table;
    
    static MongoClient mongoClient;
    static DBCursor cursor;
    static DB db;
    public static void main(String[] args) {
        
        mongoClient = new MongoClient( "localhost" , 27017 );
        db = mongoClient.getDB( "Project_db" );
        
        MenuExample menuExample = new MenuExample();
        
    }

    static ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                
                DBCollection coll = db.getCollection("students");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class", "Last Month paid", "Age", "Signed in"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                search_by_name fors = new search_by_name("Full_name", "*", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                table.setModel(model);
            
        }
    }; 
    
}

class MenuExample  
{  
    int counter;
    DBObject curr_obj;
            
    search_by_name for_search;
        
          JMenu menu, menu_students, i2, i3;  
          JMenuItem  i1, i4, i5, i6, i7, i8, i9, i10;  
          MenuExample(){  
             
          JFrame f= new JFrame("Actions"); 
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("Menu");  
          menu_students=new JMenu("Students"); 
          
          i1 = new JMenuItem("Show all"); 
          i5 = new JMenuItem("Owes");
          i6 = new JMenuItem("Add student");
          i7 = new JMenuItem("Show all");
          i8 = new JMenuItem("Not paid");
          i9 = new JMenuItem("Add instructor");
          i10 = new JMenuItem("Show all");
          menu_students.add(i1); menu_students.add(i5); menu_students.add(i6);
          
          
          i2=new JMenu("Instructors"); i7 = new JMenuItem("Show all");   i2.add(i7); i2.add(i8); i2.add(i9);
          i3=new JMenu("Classes");  i3.add(i10);
          i4=new JMenuItem("Program");  
          
           menu.add(menu_students);
          menu.add(i2); menu.add(i3);  menu.add(i4);
          mb.add(menu);  
          f.setJMenuBar(mb);  
          f.setSize(600,600);  
          f.setLayout(null);  
          f.setVisible(true);  
          
          // Mexri th grammh 96 dhmiourgeitai to anagkaio menu
          i1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1300, 600);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("students");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class", "Last Month paid", "Age", "Signed in"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                
                search_by_name fors = new search_by_name("Full_name", "*", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                table.setModel(model);
                table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu delete_menu = new JPopupMenu();
                JMenuItem delete = new JMenuItem("delete");
                JMenuItem show_program = new JMenuItem("show program");
                delete_menu.add(delete); delete_menu.add(show_program);
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    // Row index: Mas deixnei se poia grammh vriskomaste. Me deksi click erxetai diagrafh mathith
                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                        delete_menu.show(e.getComponent(),e.getX(), e.getY());
                        delete.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to delete
                                delete_document fordelete = new delete_document(coll, "Full_name", (String)table.getValueAt(rowindex, 0));
                                 DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Meta th diagrafh o pinakas prepei na ksanaemfanistei
                            for_search = new search_by_name("Full_name", "*", coll);
                            for(int i = 0; i < for_search.values.size(); i++) {
                                model.addRow(for_search.values.get(i).split(","));
                            }
                        table.setModel(model);
                            }
                        });
                        
                        show_program.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to delete
                                DBCollection coll = db.getCollection("classes");
                cursor = coll.find();

                String[] columnNames = {"", "14:00", "15:15","16:00","17:15", "18:00", "19:15","20:00","21:15", "21:00", "22:15"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                
                for_search = new search_by_name("Full_name", "*", db.getCollection("classes"));
                String[] pos;
                
                
                for(int i = 0; i < 5; i++) {
                    pos = new String[11];
                
                for(int j = 0; j < for_search.values.size(); j++) {
                    
                    
                    if(i == 0)
                        pos[0] = "Monday";
                    if(i == 1)
                        pos[0] = "Tuesday";
                    if(i == 2)
                        pos[0] = "Wednsday";
                    if(i == 3)
                        pos[0] = "Thursday";
                    if(i == 4)
                        pos[0] = "Friday";
                    
                    if(for_search.values.get(j).split(",")[2].equals("14:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[1] == null) {
                                pos[1] = for_search.values.get(j).split(",")[0];
                                pos[2] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[1] = pos[1] + "-"+for_search.values.get(j).split(",")[0];
                                pos[2] = pos[2] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                        
                    }
                    
                    if(for_search.values.get(j).split(",")[2].equals("16:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[3] == null) {
                                pos[3] = for_search.values.get(j).split(",")[0];
                                pos[4] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[3] = pos[3] + "-"+for_search.values.get(j).split(",")[0];
                                pos[4] = pos[4] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[2].equals("18:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[5] == null) {
                                pos[5] = for_search.values.get(j).split(",")[0];
                                pos[6] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[5] = pos[5] + "-"+for_search.values.get(j).split(",")[0];
                                pos[6] = pos[6] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[2].equals("20:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[7] == null) {
                                pos[7] = for_search.values.get(j).split(",")[0];
                                pos[8] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[7] = pos[7] + "-"+for_search.values.get(j).split(",")[0];
                                pos[8] = pos[8] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[2].equals("21:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[9] == null) {
                                pos[9] = for_search.values.get(j).split(",")[0];
                                pos[10] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[9] = pos[9] + "-"+for_search.values.get(j).split(",")[0];
                                pos[10] = pos[10] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                    
                        if(for_search.values.get(j).split(",")[4].equals("14:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[1] == null) {
                                pos[1] = for_search.values.get(j).split(",")[0];
                                pos[2] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[1] = pos[1] + "-"+for_search.values.get(j).split(",")[0];
                                pos[2] = pos[2] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                        
                    }
                    
                    if(for_search.values.get(j).split(",")[4].equals("16:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[3] == null) {
                                pos[3] = for_search.values.get(j).split(",")[0];
                                pos[4] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[3] = pos[3] + "-"+for_search.values.get(j).split(",")[0];
                                pos[4] = pos[4] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("18:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[5] == null) {
                                pos[5] = for_search.values.get(j).split(",")[0];
                                pos[6] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[5] = pos[5] + "-"+for_search.values.get(j).split(",")[0];
                                pos[6] = pos[6] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("20:00") && for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5))) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[7] == null) {
                                pos[7] = for_search.values.get(j).split(",")[0];
                                pos[8] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[7] = pos[7] + "-"+for_search.values.get(j).split(",")[0];
                                pos[8] = pos[8] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("21:00") &&  for_search.values.get(j).split(",")[0].equals((String)table.getValueAt(rowindex, 5)) ) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[9] == null) {
                                pos[9] = for_search.values.get(j).split(",")[0];
                                pos[10] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[9] = pos[9] + "-"+for_search.values.get(j).split(",")[0];
                                pos[10] = pos[10] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                    
                    
                }
                
                model.addRow(pos);
                
                }
                
                table.setModel(model);
                JOptionPane.showMessageDialog(null, panel);
                
                 
                }
                        });
                    }
                }
            }
        }); 
                table.getModel().addTableModelListener(new TableModelListener() {
                    

                    @Override // SHmainei oti anoixteytike allagh se kapoio keli
                    public void tableChanged(TableModelEvent e) {
                        // Me tis methodous getSelectedColumns() kai getSelectedRows tha vrethei h kathe allagh
                        String type = null;
                        
                        if(table.getSelectedColumn() == 0) {
                                type = "Full_name";
                            }
                            
                            if(table.getSelectedColumn() == 1) {
                                type = "Phone";
                            }
                            
                            if(table.getSelectedColumn() == 2) {
                                type = "Address";
                            }
                            
                            if(table.getSelectedColumn() == 3) {
                                type = "Email";
                            }
                            
                            if(table.getSelectedColumn() == 4) {
                                type = "Bank_account";
                            }
                            
                            if(table.getSelectedColumn() == 5) {
                                type = "Class";
                            }
                            
                            if(table.getSelectedColumn() == 6) {
                                type = "Last_paid";
                            }
                            
                            if(table.getSelectedColumn() == 7) {
                                type = "Age";
                            }
                            
                            if(table.getSelectedColumn() == 8) {
                                type = "Signed_in";
                            }
                            
                            
                            // Se afto to shmeio exei vrethei h allagh
                        counter = 0; 
                        curr_obj = null;
                        
                        DBCursor cursor = coll.find().sort(new BasicDBObject("Full_name", 1)); // Anazhthsh se ola ta eggrafa
                        while(cursor.hasNext()) {
                            if(counter == table.getSelectedRow()+1)
                                break;
                            
                            curr_obj = cursor.next();
                            counter++;
                        }
                        
                        // Exei vrethei to antikeimeno pou psaxnoume
                        
                        if(curr_obj == null)
                            curr_obj = cursor.next();
                        
                        Object new_val = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        update_document new_doc = new update_document(coll, type, (String)curr_obj.get(type), (String)new_val, curr_obj);
                    }
              });
                JTextField txt = new JTextField(20);
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        if(txt.getText().length() == 0)
                            for_search = new search_by_name("Full_name", "*", coll);
                        else {
                            for_search = new search_by_name("Full_name", txt.getText(), coll);
                        }
                        for(int i = 0; i < for_search.values.size(); i++) {
                            model.addRow(for_search.values.get(i).split(","));
                    }
                        table.setModel(model);
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
          
          i10.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1300, 600);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("classes");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Day 1", "Day 1 Starts", "Day 2", "Day 2 starts", "Total_money"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                
                search_by_name fors = new search_by_name("Full_name", "*", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                
                table.setModel(model);
                table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu delete_menu = new JPopupMenu();
                JMenuItem show = new JMenuItem("show students");
                
                delete_menu.add(show);
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    // Row index: Mas deixnei se poia grammh vriskomaste. Me deksi click erxetai diagrafh mathith
                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                        delete_menu.show(e.getComponent(),e.getX(), e.getY());
                        show.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to show students
                               
                               search_by_name for_s = new search_by_name("Class", String.valueOf(table.getValueAt(rowindex, 0)), db.getCollection("students"));
                               
                               if(for_s.values.size() == 0) {
                                   JOptionPane.showMessageDialog(new JFrame(), "No students assigned in this class");
                                   return;
                               }
                                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class", "Last Month paid", "Age", "Signed in"};
                                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                               for(int i = 0; i < fors.values.size(); i++) {
                                    model.addRow(for_s.values.get(i).split(","));
                                }
                               table.setModel(model);
                               table.setEnabled(false);
                            }
                        });
                        
                    }
                }
            }
        }); 
                table.getModel().addTableModelListener(new TableModelListener() {
                    
                    public void tableChanged(TableModelEvent e) {
                        // Me tis methodous getSelectedColumns() kai getSelectedRows tha vrethei h kathe allagh
                        String type = null;
                        
                        if(table.getSelectedColumn() == 0) {
                                type = "Full_name";
                            }
                            
                            if(table.getSelectedColumn() == 1) {
                                type = "Day1";
                            }
                            
                            if(table.getSelectedColumn() == 2) {
                                type = "Hours1";
                            }
                            
                            if(table.getSelectedColumn() == 3) {
                                type = "Day2";
                            }
                            
                            if(table.getSelectedColumn() == 4) {
                                type = "Hours2";
                            }
                            
                            if(table.getSelectedColumn() == 5) {
                                type = "Total_money";
                            }
                            
                            
                            
                            // Se afto to shmeio exei vrethei h allagh
                        counter = 0; 
                        curr_obj = null;
                        
                        DBCursor cursor = coll.find().sort(new BasicDBObject("Full_name", 1)); // Anazhthsh se ola ta eggrafa
                        while(cursor.hasNext()) {
                            if(counter == table.getSelectedRow()+1)
                                break;
                            
                            curr_obj = cursor.next();
                            counter++;
                        }
                        
                        // Exei vrethei to antikeimeno pou psaxnoume
                        
                        if(curr_obj == null)
                            curr_obj = cursor.next();
                        
                        Object new_val = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        update_document new_doc = new update_document(coll, type, (String)curr_obj.get(type), (String)new_val, curr_obj);
                    }
              });
                JTextField txt = new JTextField(20);
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        if(txt.getText().length() == 0)
                            for_search = new search_by_name("Full_name", "*", coll);
                        else {
                            for_search = new search_by_name("Full_name", txt.getText(), coll);
                        }
                        for(int i = 0; i < for_search.values.size(); i++) {
                            model.addRow(for_search.values.get(i).split(","));
                    }
                        table.setModel(model);
                        table.setEnabled(true);
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
          
           i7.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1300, 600);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("instructors");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class1", "Class2", "Salary", "Last_paid", "Recruitment_day"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                
                search_by_name fors = new search_by_name("Full_name", "*", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                table.setModel(model);
                table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu delete_menu = new JPopupMenu();
                JMenuItem delete = new JMenuItem("delete");
                delete_menu.add(delete);
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    // Row index: Mas deixnei se poia grammh vriskomaste. Me deksi click erxetai diagrafh mathith
                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                        delete_menu.show(e.getComponent(),e.getX(), e.getY());
                        delete.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to delete
                                delete_document fordelete = new delete_document(coll, "Full_name", (String)table.getValueAt(rowindex, 0));
                                 DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Meta th diagrafh o pinakas prepei na ksanaemfanistei
                            for_search = new search_by_name("Full_name", "*", coll);
                            for(int i = 0; i < for_search.values.size(); i++) {
                                model.addRow(for_search.values.get(i).split(","));
                            }
                        table.setModel(model);
                            }
                        });
                    }
                }
            }
        }); 
                table.getModel().addTableModelListener(new TableModelListener() {
                    

                    @Override // SHmainei oti anoixteytike allagh se kapoio keli
                    public void tableChanged(TableModelEvent e) {
                        // Me tis methodous getSelectedColumns() kai getSelectedRows tha vrethei h kathe allagh
                        String type = null;
                        
                        if(table.getSelectedColumn() == 0) {
                                type = "Full_name";
                            }
                            
                            if(table.getSelectedColumn() == 1) {
                                type = "Phone";
                            }
                            
                            if(table.getSelectedColumn() == 2) {
                                type = "Address";
                            }
                            
                            if(table.getSelectedColumn() == 3) {
                                type = "Email";
                            }
                            
                            if(table.getSelectedColumn() == 4) {
                                type = "IBAN";
                            }
                            
                            if(table.getSelectedColumn() == 5) {
                                type = "Class1";
                            }
                            
                            if(table.getSelectedColumn() == 6) {
                                type = "Class2";
                            }
                            
                            if(table.getSelectedColumn() == 7) {
                                type = "Salary";
                            }
                            
                            if(table.getSelectedColumn() == 8) {
                                type = "Last_paid";
                            }
                            
                            if(table.getSelectedColumn() == 8) {
                                type = "Recruitment";
                            }
                            
                            
                            // Se afto to shmeio exei vrethei h allagh
                        counter = 0; 
                        curr_obj = null;
                        
                        DBCursor cursor = coll.find(); // Anazhthsh se ola ta eggrafa
                        while(cursor.hasNext()) {
                            if(counter == table.getSelectedRow()+1)
                                break;
                            
                            curr_obj = cursor.next();
                            counter++;
                        }
                        
                        // Exei vrethei to antikeimeno pou psaxnoume
                        
                        if(curr_obj == null)
                            curr_obj = cursor.next();
                        curr_obj = cursor.next();
                        Object new_val = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        update_document new_doc = new update_document(coll, type, (String)curr_obj.get(type), (String)new_val, curr_obj);
                    }
              });
                JTextField txt = new JTextField(20);
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        if(txt.getText().length() == 0)
                            for_search = new search_by_name("Full_name", "*", coll);
                        else {
                            for_search = new search_by_name("Full_name", txt.getText(), coll);
                        }
                        for(int i = 0; i < for_search.values.size(); i++) {
                            model.addRow(for_search.values.get(i).split(","));
                    }
                        table.setModel(model);
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
          
          i4.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1300, 600);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("classes");
                cursor = coll.find();

                String[] columnNames = {"", "14:00", "15:15","16:00","17:15", "18:00", "19:15","20:00","21:15", "21:00", "22:15"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                table.setEnabled(false); // Den mporoume na to epeksergastoume apo ekei
                
                for_search = new search_by_name("Full_name", "*", db.getCollection("classes"));
                String[] pos;
                
                
                
                for(int i = 0; i < 5; i++) {
                    pos = new String[11];
                
                for(int j = 0; j < for_search.values.size(); j++) {
                    
                    
                    if(i == 0)
                        pos[0] = "Monday";
                    if(i == 1)
                        pos[0] = "Tuesday";
                    if(i == 2)
                        pos[0] = "Wednsday";
                    if(i == 3)
                        pos[0] = "Thursday";
                    if(i == 4)
                        pos[0] = "Friday";
                    
                    
                    if(for_search.values.get(j).split(",")[2].equals("14:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[1] == null) {
                                pos[1] = for_search.values.get(j).split(",")[0];
                                pos[2] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[1] = pos[1] + "-"+for_search.values.get(j).split(",")[0];
                                pos[2] = pos[2] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                        
                    }
                    
                    if(for_search.values.get(j).split(",")[2].equals("16:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[3] == null) {
                                pos[3] = for_search.values.get(j).split(",")[0];
                                pos[4] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[3] = pos[3] + "-"+for_search.values.get(j).split(",")[0];
                                pos[4] = pos[4] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[2].equals("18:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[5] == null) {
                                pos[5] = for_search.values.get(j).split(",")[0];
                                pos[6] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[5] = pos[5] + "-"+for_search.values.get(j).split(",")[0];
                                pos[6] = pos[6] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[2].equals("20:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[7] == null) {
                                pos[7] = for_search.values.get(j).split(",")[0];
                                pos[8] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[7] = pos[7] + "-"+for_search.values.get(j).split(",")[0];
                                pos[8] = pos[8] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                    
                        if(for_search.values.get(j).split(",")[2].equals("21:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[9] == null) {
                                pos[9] = for_search.values.get(j).split(",")[0];
                                pos[10] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[9] = pos[9] + "-"+for_search.values.get(j).split(",")[0];
                                pos[10] = pos[10] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("14:00")) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[1] == null) {
                                pos[1] = for_search.values.get(j).split(",")[0];
                                pos[2] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[1] = pos[1] + "-"+for_search.values.get(j).split(",")[0];
                                pos[2] = pos[2] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                        
                    }
                    
                    if(for_search.values.get(j).split(",")[4].equals("16:00")) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[3] == null) {
                                pos[3] = for_search.values.get(j).split(",")[0];
                                pos[4] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[3] = pos[3] + "-"+for_search.values.get(j).split(",")[0];
                                pos[4] = pos[4] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("18:00")) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[5] == null) {
                                pos[5] = for_search.values.get(j).split(",")[0];
                                pos[6] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[5] = pos[5] + "-"+for_search.values.get(j).split(",")[0];
                                pos[6] = pos[6] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("20:00")) {
                        if(for_search.values.get(j).split(",")[3].equals(String.valueOf(i+1))) {
                            if(pos[7] == null) {
                                pos[7] = for_search.values.get(j).split(",")[0];
                                pos[8] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[7] = pos[7] + "-"+for_search.values.get(j).split(",")[0];
                                pos[8] = pos[8] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                        
                        if(for_search.values.get(j).split(",")[4].equals("21:00")) {
                        if(for_search.values.get(j).split(",")[1].equals(String.valueOf(i+1))) {
                            if(pos[9] == null) {
                                pos[9] = for_search.values.get(j).split(",")[0];
                                pos[10] = for_search.values.get(j).split(",")[0];
                            }
                            else {
                                pos[9] = pos[9] + "-"+for_search.values.get(j).split(",")[0];
                                pos[10] = pos[10] + "-"+for_search.values.get(j).split(",")[0];
                            }
                        }
                    }
                    
                    
                }
                model.addRow(pos);
                
                }
                table.setModel(model);
                panel.add(new JScrollPane(Mongoa.table));
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
          // Ean patithei h deyterh epilogh tote ginetai anazhthsh me vash th synarthsh 
          // owes, gia to poioi mathites xrwstane 
         i5.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1100, 500);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("students");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class", "Last Month paid", "Age", "Signed in"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                owes fors = new owes("Last_paid", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                
                table.setModel(model);
                table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu delete_menu = new JPopupMenu();
                JMenuItem delete = new JMenuItem("delete");
                delete_menu.add(delete);
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    // Row index: Mas deixnei se poia grammh vriskomaste. Me deksi click erxetai diagrafh mathith
                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                        delete_menu.show(e.getComponent(),e.getX(), e.getY());
                        delete.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to delete
                                delete_document fordelete = new delete_document(coll, "Full_name", (String)table.getValueAt(rowindex, 0));
                                 DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Meta th diagrafh o pinakas prepei na ksanaemfanistei
                            owes fors = new owes("Last_paid", coll);
                            for(int i = 0; i < fors.values.size(); i++) {
                                model.addRow(fors.values.get(i).split(","));
                            }
                        table.setModel(model);
                            }
                        });
                    }
                }
            }
        }); 
                
                table.getModel().addTableModelListener(new TableModelListener() {

                    @Override // SHmainei oti anoixteytike allagh se kapoio keli
                    public void tableChanged(TableModelEvent e) {
                        // Me tis methodous getSelectedColumns() kai getSelectedRows tha vrethei h kathe allagh
                        String type = null;
                        
                        if(table.getSelectedColumn() == 0) {
                                type = "Full_name";
                            }
                            
                            if(table.getSelectedColumn() == 1) {
                                type = "Phone";
                            }
                            
                            if(table.getSelectedColumn() == 2) {
                                type = "Address";
                            }
                            
                            if(table.getSelectedColumn() == 3) {
                                type = "Email";
                            }
                            
                            if(table.getSelectedColumn() == 4) {
                                type = "Bank_account";
                            }
                            
                            if(table.getSelectedColumn() == 5) {
                                type = "Class";
                            }
                            
                            if(table.getSelectedColumn() == 6) {
                                type = "Last_paid";
                            }
                            
                            if(table.getSelectedColumn() == 7) {
                                type = "Age";
                            }
                            
                            if(table.getSelectedColumn() == 8) {
                                type = "Signed_in";
                            }
                            
                            
                            // Se afto to shmeio exei vrethei h allagh
                        counter = 0; 
                        curr_obj = null;
                        
                        DBCursor cursor = coll.find(); // Anazhthsh se ola ta eggrafa
                        while(cursor.hasNext()) {
                            if(counter == table.getSelectedRow()+1)
                                break;
                            
                            curr_obj = cursor.next();
                            counter++;
                        }
                        
                        // Exei vrethei to antikeimeno pou psaxnoume
                        
                        if(curr_obj == null)
                            return;
                        
                        Object new_val = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        update_document new_doc = new update_document(coll, type, (String)curr_obj.get(type), (String)new_val, curr_obj);
                    }
              });
                JTextField txt = new JTextField(20);
                
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        if(txt.getText().length() == 0)
                            for_search = new search_by_name("Full_name", "*", coll);
                        else {
                            for_search = new search_by_name("Full_name", txt.getText(), coll);
                        }
                        for(int i = 0; i < for_search.values.size(); i++) {
                            model.addRow(for_search.values.get(i).split(","));
                    }
                        table.setModel(model);
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
         
         i9.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1100, 500);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class1", "Class2", "Salary", "Last month paid", "Started working"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", ""});
                table.setModel(model);
                
                JButton txt = new JButton("Insert");
                BasicDBObject obj = new BasicDBObject(); // To object tha prosthethei sth vash mas
                int curr_obj_int; // Edw tha apothikeftoun oi ints tou sunolou
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i <= 9; i++) {
                            String curr_string = (String)table.getValueAt(0, i); // Pernoume auta poy eishxthisan apo ton xrhsth
                            
                            if(curr_string.length() == 0) {
                                JOptionPane.showMessageDialog(new JFrame(), "Please fill all values", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                i = -1;
                                return;
                            }
                            
                            if(i == 0) {
                                obj.put("Full_name", curr_string);
                            }
                            
                            if(i == 1) {
                                obj.put("Phone", curr_string);
                            }
                            
                            if(i == 2) {
                                obj.put("Address", curr_string);
                            }
                            
                            if(i == 3) {
                                obj.put("Email", curr_string);
                            }
                            
                            if(i == 4) {
                                obj.put("Bank_account", curr_string);
                            }
                            
                            if(i == 5) {
                                obj.put("Class1", curr_string);
                            }
                            
                            if(i == 6) {
                                obj.put("Class2",curr_string);
                                
                            }
                            
                            if(i == 7) {
                                try {
                                   obj.put("Salary", Short.valueOf(curr_string));
                                } catch(NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Not valid salary", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                    i = -1;
                                    return;
                                }
                                
                            }
                            
                            if(i == 8) {
                                try {
                                   obj.put("Last_paid", Short.valueOf(curr_string));
                                } catch(NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Not valid month", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                    i = -1;
                                    return;
                                }
                                
                            }
                            
                            if(i == 9) {
                                System.out.println("In");
                                obj.put("Recruitment", curr_string);
                            }
                            
                            
                        }
                        
                        Mongoa.db.getCollection("instructors").save(obj);
                        
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                 
            }
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
         
       i6.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1100, 500);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class", "Last Month paid", "Age", "Signed in"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                model.addRow(new Object[]{"", "", "", "", "", "", "", "", ""});
                table.setModel(model);
                
                JButton txt = new JButton("Insert");
                BasicDBObject obj = new BasicDBObject(); // To object tha prosthethei sth vash mas
                int curr_obj_int; // Edw tha apothikeftoun oi ints tou sunolou
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i <= 8; i++) {
                            String curr_string = (String)table.getValueAt(0, i); // Pernoume auta poy eishxthisan apo ton xrhsth
                            
                            if(curr_string.length() == 0) {
                                JOptionPane.showMessageDialog(new JFrame(), "Please fill all values", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                i = -1;
                                return;
                            }
                            
                            if(i == 0) {
                                obj.put("Full_name", curr_string);
                            }
                            
                            if(i == 1) {
                                obj.put("Phone", curr_string);
                            }
                            
                            if(i == 2) {
                                obj.put("Address", curr_string);
                            }
                            
                            if(i == 3) {
                                obj.put("Email", curr_string);
                            }
                            
                            if(i == 4) {
                                obj.put("Bank_account", curr_string);
                            }
                            
                            if(i == 5) {
                                obj.put("Class", curr_string);
                            }
                            
                            if(i == 6) {
                                try {
                                    obj.put("Last_paid", Short.valueOf(curr_string));
                                }
                                 catch(NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Month is not a valid number", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                    i = -1;
                                    return;
                                }
                            }
                            
                            if(i == 7) {
                                try {
                                   obj.put("Age", Integer.valueOf(curr_string));
                                } catch(NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Age is not a number", "Dialog",JOptionPane.ERROR_MESSAGE); // Shmainei den exoun symplhrwthei oles oi times
                                    i = -1;
                                    return;
                                }
                                
                            }
                            
                            if(i == 8) {
                                obj.put("Signed_in", curr_string);
                            }
                        }
                        
                        Mongoa.db.getCollection("students").save(obj);
                        
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                 
            }
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
       
       i8.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Runnable runnable = new Runnable() {
            public void run() {
                Mongoa.table = new JTable(){
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(1100, 500);
                    }
                };
                table.setRowHeight(25);
                
                JPanel panel = new JPanel(new BorderLayout());
                
                DBCollection coll = db.getCollection("instructors");
                cursor = coll.find();

                String[] columnNames = {"Full name", "Phone", "Address", "Email", "IBAN", "Class1", "Class2", "Salary", "Last month paid", "Started working"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                owes fors = new owes("Last_paid", coll);
                for(int i = 0; i < fors.values.size(); i++) {
                    model.addRow(fors.values.get(i).split(","));
                }
                
                
                table.setModel(model);
                table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu delete_menu = new JPopupMenu();
                JMenuItem delete = new JMenuItem("delete");
                delete_menu.add(delete);
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    // Row index: Mas deixnei se poia grammh vriskomaste. Me deksi click erxetai diagrafh mathith
                    int rowindex = table.getSelectedRow();
                    if (rowindex < 0)
                        return;
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                        delete_menu.show(e.getComponent(),e.getX(), e.getY());
                        delete.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent actionEvent) { // Shmainei oti patithke to delete
                                delete_document fordelete = new delete_document(coll, "Full_name", (String)table.getValueAt(rowindex, 0));
                                 DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Meta th diagrafh o pinakas prepei na ksanaemfanistei
                            owes fors = new owes("Last_paid", coll);
                            for(int i = 0; i < fors.values.size(); i++) {
                                model.addRow(fors.values.get(i).split(","));
                            }
                        table.setModel(model);
                            }
                        });
                    }
                }
            }
        }); 
                
                table.getModel().addTableModelListener(new TableModelListener() {

                    @Override // SHmainei oti anoixteytike allagh se kapoio keli
                    public void tableChanged(TableModelEvent e) {
                        // Me tis methodous getSelectedColumns() kai getSelectedRows tha vrethei h kathe allagh
                        String type = null;
                        
                        if(table.getSelectedColumn() == 0) {
                                type = "Full_name";
                            }
                            
                            if(table.getSelectedColumn() == 1) {
                                type = "Phone";
                            }
                            
                            if(table.getSelectedColumn() == 2) {
                                type = "Address";
                            }
                            
                            if(table.getSelectedColumn() == 3) {
                                type = "Email";
                            }
                            
                            if(table.getSelectedColumn() == 4) {
                                type = "Bank_account";
                            }
                            
                            if(table.getSelectedColumn() == 5) {
                                type = "Class1";
                            }
                            
                            if(table.getSelectedColumn() == 6) {
                                type = "Class2";
                            }
                            
                            if(table.getSelectedColumn() == 7) {
                                type = "Salary";
                            }
                            
                            if(table.getSelectedColumn() == 8) {
                                type = "Last_paid";
                            }
                            
                            if(table.getSelectedColumn() == 9) {
                                type = "Recruitment";
                            }
                            
                            
                            // Se afto to shmeio exei vrethei h allagh
                        counter = 0; 
                        curr_obj = null;
                        
                        DBCursor cursor = coll.find(); // Anazhthsh se ola ta eggrafa
                        while(cursor.hasNext()) {
                            if(counter == table.getSelectedRow()+1)
                                break;
                            
                            curr_obj = cursor.next();
                            counter++;
                        }
                        
                        // Exei vrethei to antikeimeno pou psaxnoume
                        
                        if(curr_obj == null)
                            curr_obj = cursor.next();
                        
                        Object new_val = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        update_document new_doc = new update_document(coll, type, (String)curr_obj.get(type), (String)new_val, curr_obj);
                    }
              });
                JTextField txt = new JTextField(20);
                
                Action action = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        if(txt.getText().length() == 0)
                            for_search = new search_by_name("Full_name", "*", coll);
                        else {
                            for_search = new search_by_name("Full_name", txt.getText(), coll);
                        }
                        for(int i = 0; i < for_search.values.size(); i++) {
                            model.addRow(for_search.values.get(i).split(","));
                    }
                        table.setModel(model);
                    }
                }; {
                
                }
                
                txt.addActionListener(action);
                panel.add(new JScrollPane(Mongoa.table));
                panel.add(txt, BorderLayout.PAGE_END);
                JOptionPane.showMessageDialog(null, panel);
                
                 
            }
            
        };
            SwingUtilities.invokeLater(runnable);
        }
      });
    }
          
}

