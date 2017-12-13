package Frames;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

interface IndexInt {
    void put(String str, long in);
    long[] get(String str);
    String[] getKeys(Comparator<String> c);
    boolean contains(String str);
}

class KeyCompRev implements Comparator<String> {
    public int compare(String o1, String o2) {return o2.compareTo(o1);}
}

class KeyComp implements Comparator<String> {
    public int compare(String o1, String o2) {return o1.compareTo(o2);}
}

class IndexOne implements Serializable,IndexInt{
    HashMap<String, Long> map;
    public IndexOne() {map = new HashMap<>();}

    public void put(String str, long in) {map.put(str, new Long(in));}

    public long[] get(String str) {
        long pos = map.get(str).longValue();
        return new long[] {pos};
    }

    public String[] getKeys(Comparator<String> c) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result,c);
        return result;
    }

    public boolean contains(String str) {return map.containsKey(str);}
}
public class MyIndex implements Serializable, Closeable {
    public static long[] InsertValue(long[] arr, long value) {
        int length = (arr == null) ? 0 : arr.length;
        long[] result = new long[length + 1];
        for (int i = 0; i < length; i++)
            result[i] = arr[i];
        result[length] = value;
        return result;
    }

    IndexOne fio_client;
    IndexOne name_coach;
    IndexOne minutes;

    public void test(SportComplex sp) throws MyException {
        assert (sp != null);
        if (fio_client.contains(sp.client.getSurname()))
            throw new MyException(sp.client.getSurname());
    }

    public void put(SportComplex sp, long value) throws MyException {
        test(sp);
        fio_client.put(sp.getClient().getSurname(), value);
        name_coach.put(sp.getCoach().getSurname(), value);
        minutes.put(Integer.toString(sp.getMinutes()), value);
    }

    public MyIndex() {
        fio_client = new IndexOne();
        name_coach = new IndexOne();
        minutes = new IndexOne();
    }

    public static MyIndex load(String name) throws IOException, ClassNotFoundException {
        MyIndex obj = null;
        try {
            FileInputStream file = new FileInputStream(name);
            try (ZipInputStream zis = new ZipInputStream(file)) {
                ZipEntry zen = zis.getNextEntry();
                if (!zen.getName().equals(Buffer.zipEntryName)) {
                    throw new IOException("Invalid block format");
                }
                try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                    obj = (MyIndex) ois.readObject();
                }
            }
        } catch (FileNotFoundException e) {
            obj = new MyIndex();
        }
        if (obj != null) {
            obj.save(name);
        }
        return obj;
    }

    private transient String filename = null;

    public void save(String name) {
        filename = name;
    }

    public void saveAs(String name) throws IOException {
        FileOutputStream file = new FileOutputStream(name);
        try (ZipOutputStream zos = new ZipOutputStream(file)) {
            zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
    }

    public void close() throws IOException {
        saveAs(filename);
    };
}
