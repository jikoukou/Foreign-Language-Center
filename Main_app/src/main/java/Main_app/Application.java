/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_app;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Application {

    
}

// Kathe klash ylopoiei ena query. H search_by_name psaxnei instructors/students vash onomatos
class search_by_name {
    BasicDBObject searchQuery;
    ArrayList<String> values; // Sth values tha apothikeftoun ola ta eggrafa poy mas aforoun
    
    
    search_by_name(String Full_name, String search, DBCollection collection) {
        DBObject curr_obj;
        StringBuilder output;
        
        values = new ArrayList<>();
        output = new StringBuilder();
        searchQuery = new BasicDBObject();
        searchQuery.put(Full_name, search);
        
        
        if(search.charAt(0) == '*' && search.length() == 1) {
            DBCursor iterDoc = collection.find().sort(new BasicDBObject("Full_name", 1)); // Evresh olwn twn documents
            
            while (iterDoc.hasNext()) {
             curr_obj = iterDoc.next();
                
             
                output.append(curr_obj.get("Full_name"));
                output.append(",");
                if(curr_obj.containsField("Day1")) {
                    // Se afto to if mpainoume an psaxnoume gia taksh
                    output.append(curr_obj.get("Day1"));
                    output.append(",");
                    output.append(curr_obj.get("Hours1"));
                    output.append(",");
                    output.append(curr_obj.get("Day2"));
                    output.append(",");
                    output.append(curr_obj.get("Hours2"));
                    output.append(",");
                    output.append(curr_obj.get("Total_money"));
                }
                else {
                    output.append(curr_obj.get("Phone"));
                    output.append(",");
                    output.append(curr_obj.get("Address"));
                    output.append(",");
                    output.append(curr_obj.get("Email"));
                    output.append(",");
                    output.append(curr_obj.get("Bank_account"));
                    output.append(",");

                    // Mexri th grammh 70 koina stoixeia metaksy student kai instructor

                    if(curr_obj.containsField("Class")) { // Shmainei einai mathiths
                        output.append(curr_obj.get("Class"));
                        output.append(",");
                        output.append(curr_obj.get("Last_paid"));
                        output.append(",");
                        output.append(curr_obj.get("Age"));
                        output.append(",");
                        output.append(curr_obj.get("Signed_in"));
                    }

                    else { // Einai kathigiths
                        output.append(curr_obj.get("Class1"));
                        output.append(",");
                        output.append(curr_obj.get("Class2"));
                        output.append(",");
                        output.append(curr_obj.get("Salary"));
                        output.append(",");
                        output.append(curr_obj.get("Last_paid"));
                        output.append(",");
                        output.append(curr_obj.get("Recruitment"));
                    }
                }
                output.append("\n");
                values.add(output.toString());
                output.delete(0, output.length());
            }
        }
        else {
            DBCursor cursor = collection.find(searchQuery).sort(new BasicDBObject("Full_name", 1));
            while(cursor.hasNext()) {
                curr_obj = cursor.next();
                output.append(curr_obj.get("Full_name"));
                output.append(",");
                output.append(curr_obj.get("Phone"));
                output.append(",");
                output.append(curr_obj.get("Address"));
                output.append(",");
                output.append(curr_obj.get("Email"));
                output.append(",");
                output.append(curr_obj.get("Bank_account"));
                output.append(",");
                
                // Mexri th grammh 70 koina stoixeia metaksy student kai instructor
                
                if(curr_obj.containsField("Class")) { // Shmainei einai mathiths
				
                    output.append(curr_obj.get("Class"));
                    output.append(",");
                    output.append(curr_obj.get("Last_paid"));
                    output.append(",");
                    output.append(curr_obj.get("Age"));
                    output.append(",");
                    output.append(curr_obj.get("Signed_in"));
                }
                
                else { // Einai kathigiths
					
                    output.append(curr_obj.get("Class1"));
                    output.append(",");
                    output.append(curr_obj.get("Class2"));
                    output.append(",");
                    output.append(curr_obj.get("Salary"));
                    output.append(",");
                    output.append(curr_obj.get("Last_paid"));
                    output.append(",");
                    output.append(curr_obj.get("Recruitment"));
                }
                
                output.append("\n");
                values.add(output.toString());
                output.delete(0, output.length());
            }
        }
    }
}

/* Afth h klassh vriskei mathites poy xrwstane h  kathigites stous opoious xrwstaei to frontisthrio
Thorw ws mathites pou xrwstane aftous pou exoun dwsei ta xrhmata se prohgoumeno mhna
*/

class owes {
    StringBuilder output;
    ArrayList<String> values; // Sth values tha apothikeftoun ola ta eggrafa poy mas aforoun
    
    owes(String column, DBCollection coll) {
        DBObject curr_obj;
        BasicDBObject searchQuery;
        
        output = new StringBuilder();
        values = new ArrayList<>();
        
        searchQuery = new BasicDBObject();
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        
        // Grammes 51-54: Lhpsh tou trexontos mhna
        searchQuery.put(column, new BasicDBObject("$lt", month+1)); // Me ton telesth lt ginetai evresh gia mhnes prin apo ton pepto
        
        DBCursor cursor = coll.find(searchQuery).sort(new BasicDBObject("Last_paid", 1)); // Ston pinaka emfanizontai panta prwtoi aftoi poy exoun kathisterhsei pio poly th dosh
        while(cursor.hasNext()) {
            curr_obj = cursor.next();
                output.append(curr_obj.get("Full_name"));
                output.append(",");
                output.append(curr_obj.get("Phone"));
                output.append(",");
                output.append(curr_obj.get("Address"));
                output.append(",");
                output.append(curr_obj.get("Email"));
                output.append(",");
                output.append(curr_obj.get("Bank_account"));
                output.append(",");
                
                // Mexri th grammh 70 koina stoixeia metaksy student kai instructor
                
                if(curr_obj.containsField("Class")) { // Shmainei einai mathiths
                    output.append(curr_obj.get("Class"));
                    output.append(",");
                    output.append(curr_obj.get("Last_paid"));
                    output.append(",");
                    output.append(curr_obj.get("Age"));
                    output.append(",");
                    output.append(curr_obj.get("Signed_in"));
                }
                
                else { // Einai kathigiths
                    output.append(curr_obj.get("Class1"));
                    output.append(",");
                    output.append(curr_obj.get("Class2"));
                    output.append(",");
                    output.append(curr_obj.get("Salary"));
                    output.append(",");
                    output.append(curr_obj.get("Last_paid"));
                    output.append(",");
                    output.append(curr_obj.get("Recruitment"));
                }
                
                output.append("\n");
                values.add(output.toString());
                
                output.delete(0, output.length());
        }
        
        
    }
}

class program {
    
    StringBuilder output;
    
    program() {
        output = new StringBuilder();
    }
    
    void find_query(String language_class, DBCollection coll) {
        BasicDBObject searchQuery = new BasicDBObject();
        
        if(language_class.length() != 3) {
            System.out.println("Wrong input");
            return;
        }
        
        if(language_class.charAt(0) != 'A' && language_class.charAt(0) != 'B' && language_class.charAt(0) != 'C') {
            System.out.println("Wrong input");
            return;
        }
        
        if(language_class.charAt(1) != '1' && language_class.charAt(1) != '2') {
            System.out.println("Wrong input");
            return;
        }
        
        if(language_class.charAt(2) != 'G' && language_class.charAt(2) != 'F' && language_class.charAt(2) != 'E') {
            System.out.println("Wrong input");
            return;
        }
        
        
        searchQuery.put("Class", language_class);
        DBCursor cursor = coll.find(searchQuery);
        while(cursor.hasNext()) {
            output.append(cursor.next());
        }
    }
}

// Update document
class update_document {
    BasicDBObject query;
    
    /*
    Me to query ginetai h anazhthsh tou paliou eggrafou. 
    
    Gia ton kataskevasth:
    coll: H syllogh pou ginetai h allagh
    type: poio pedio theloume na ginei allagh
    old_value: Palia timh anazhthshs
    new_value: Kainourgia timh pros antikatastash
    */
    update_document(DBCollection coll, String type, String old_value, String new_value, DBObject curr_obj) {
        BasicDBObject new_document = new BasicDBObject();
        BasicDBObject update = new BasicDBObject();
        
        
        if(!type.equals("Full_name"))
            query = new BasicDBObject().append(type, old_value).append("Full_name", (String)curr_obj.get("Full_name")); // Anazhthsh tou eggrafou pou psaxnoume
        else
                query = new BasicDBObject().append(type, old_value);
        new_document.put(type, new_value);
        update.put("$set", new_document);
        
        coll.update(query, update);
    }
}

// Delete_document
class delete_document {
    BasicDBObject query;
    
    /*
    Type: Με vash poia timh tha ginei diagrafh
    value: H idia h timh
    */
    delete_document(DBCollection coll, String type, String value) {
        query = new BasicDBObject();
        
        query.put(type, value);
        coll.remove(query); // Diagrafh tou antikeimenou
    }
}

