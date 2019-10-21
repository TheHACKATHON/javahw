package yevseienko;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer<T>{
    public boolean serialize(String path, ArrayList<T> obj){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(obj);
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ArrayList<T> deserialize(String path){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)))
        {
            ArrayList<T> obj =(ArrayList<T>)ois.readObject();
            return obj;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
