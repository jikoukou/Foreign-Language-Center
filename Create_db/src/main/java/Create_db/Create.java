
package Create_db;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Indexes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Create {
    
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017); // H mongo akouei sto port 27017
        DB database = client.getDB("Project_db"); // Efoson den yparxei tha dhmiourghthei h vash
        char array[]; // Apothikefsh tou trexontos string
        StringBuilder help_string = new StringBuilder();
        BasicDBObject document = new BasicDBObject();
        
        short i, counter = 0, total_written = 0; // Poia h the tou comma sto string (to i), total_written: Poses eggrafes exoun ginei
        array = new char[512];
        
        // Dhmioyrgia twn aparaithtwn collections (grammes 18-20)
        database.createCollection("students", null);
        database.createCollection("classes", null);
        database.createCollection("instructors", null);
        
        // curr_col: H vash poy exetazetai anapasa stigmh
        DBCollection curr_coll = database.getCollection("students"); 
        
        try(BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Δημήτρης Κουκ\\Desktop\\Students.csv"))) {
            while(true) {
                i = 0;
                if(in.read(array) == -1 || total_written == 360) // An teleiwsei to diavasma tou arxeiou kanei eksodo
                    break;
                help_string.append(array); // To help_string apothikevei to meros tou arxeioy
                
                while(true) {
                    if(i == help_string.length() || total_written == 360)
                        break;
                    
                    if(help_string.charAt(i) == '\n') {
                        if(counter == 8) {
                            total_written++;
                            document.put("Signed_in", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            i = 0;
                            counter = 0;
                            curr_coll.insert(document);
                            document = new BasicDBObject();
                            continue;
                        }
                    }
                    
                    if(help_string.charAt(i) == ',') {
                        if(counter == 0) {
                            document.put("Full_name", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                        
                        if(counter == 1) {
                            document.put("Phone", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }

                        if(counter == 2) {
                            document.put("Address", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        } 
                        
                        if(counter == 3) {
                            document.put("Email", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       if(counter == 4) {
                            document.put("Class", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 5) {
                            document.put("Bank_account", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 6) {
                            document.put("Last_paid", Integer.valueOf(help_string.substring(0, i))); // H timh apothikevetai se int gia th fash twn erwthmatwn
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 7) {
                            document.put("Age", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                        
                    }
                    i++;
                }
                
            }
        } catch(IOException ex1) {
            System.exit(1);
        }
        total_written = 0;
        help_string.delete(0, help_string.length());
        array = new char[512];
        curr_coll = database.getCollection("instructors");
        try(BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Δημήτρης Κουκ\\Desktop\\Instructors.csv"))) {
            counter = 0;
            document = new BasicDBObject();
            
            while(true) {
                i = 0;
                if(in.read(array) == -1 || total_written == 9) // An teleiwsei to diavasma tou arxeiou kanei eksodo
                    break;
                help_string.append(array); // To help_string apothikevei to meros tou arxeioy
                
                while(true) {
                    if(i == help_string.length() || total_written == 9)
                        break;
                    
                    if(help_string.charAt(i) == '\n') {
                        total_written++;
                        if(counter == 9) {
                            document.put("Recruitment", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            i = 0;
                            counter = 0;
                            curr_coll.insert(document);
                            document = new BasicDBObject();
                            continue;
                        }
                    }
                    
                    if(help_string.charAt(i) == ',') {
                        if(counter == 0) {
                            document.put("Full_name", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                        
                        if(counter == 1) {
                            document.put("Phone", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }

                        if(counter == 2) {
                            document.put("Address", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        } 
                        
                        if(counter == 3) {
                            document.put("Email", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       if(counter == 4) {
                            document.put("Class1", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 5) {
                            document.put("Class2", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 6) {
                            document.put("Bank_account", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 7) {
                            document.put("Salary", Integer.valueOf(help_string.substring(0, i)));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       
                       if(counter == 8) {
                            document.put("Last_paid", Integer.valueOf(help_string.substring(0, i)));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                    }
                    i++;
                }
                
            }
        } catch(IOException ex2) {
            System.exit(1);
        }
        
        help_string.delete(0, help_string.length());
        array = new char[512];
        curr_coll = database.getCollection("classes");
        total_written = 0;
        
        try(BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Δημήτρης Κουκ\\Desktop\\Classes.csv"))) {
            counter = 0;
            document = new BasicDBObject();
            
            while(true) {
                i = 0;
                if(in.read(array) == -1 || total_written == 18) // An teleiwsei to diavasma tou arxeiou kanei eksodo
                    break;
                help_string.append(array); // To help_string apothikevei to meros tou arxeioy
                
                while(true) {
                    if(i == help_string.length() || total_written == 18)
                        break;
                    
                    if(help_string.charAt(i) == '\n') {
                        total_written++;
                        if(counter == 5) {
                            document.put("Total_money", Integer.valueOf(help_string.substring(0, i)));
                            help_string.delete(0, i+1);
                            i = 0;
                            counter = 0;
                            curr_coll.insert(document);
                            document = new BasicDBObject();
                            continue;
                        }
                    }
                    
                    if(help_string.charAt(i) == ',') {
                        if(counter == 0) {
                            document.put("Full_name", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                        
                        if(counter == 1) {
                            document.put("Day1", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }

                        if(counter == 2) {
                            document.put("Hours1", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        } 
                        
                        if(counter == 3) {
                            document.put("Day2", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                       if(counter == 4) {
                            document.put("Hours2", help_string.substring(0, i));
                            help_string.delete(0, i+1);
                            counter++;
                            i = 0;
                            continue;
                        }
                    }
                    i++;
                }
                
                
            }
            
                // Index gia tous mathites pou xrwstane kata ayksousa seira 
                
                database.getCollection("students").createIndex("Last_paid");
                database.getCollection("students").createIndex("Full_name");
                // Index gia tous kathigites poy exei arghsei h plhrwmh tous
                database.getCollection("instructors").createIndex("Last_paid");
                database.getCollection("students").createIndex("Full_name");
        } catch(IOException ex3) {
            System.exit(1);
        }
    }
}
