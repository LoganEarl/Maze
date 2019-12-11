package utils;

import java.io.*;

public class ObjectPersister<E extends Serializable> {
    private File dataDirectory;

    public ObjectPersister(String dataDirectory){
        this.dataDirectory = new File(dataDirectory);
    }

    public boolean saveObject(E saveObject, String fileName){
        if(saveObject == null)
            throw new IllegalArgumentException("cannot save null objects");
        if(fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("cannot save without a file name");
        try {
            File destination = new File(dataDirectory, fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(destination));
            objectOutputStream.writeObject(saveObject);
            objectOutputStream.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public E loadObject(String fileName){
        if(fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("cannot load without a file name");
        try{
            File destination = new File(dataDirectory, fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(destination));
            //noinspection unchecked
            E obj = (E)objectInputStream.readObject();
            objectInputStream.close();
            return obj;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
