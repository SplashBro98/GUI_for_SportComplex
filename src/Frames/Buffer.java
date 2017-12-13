package Frames;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Buffer {
    static final String zipEntryName = "z";

    static byte[] toByteArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bufOut)) {
            oos.writeObject(obj);
            oos.flush();
            return bufOut.toByteArray();
        }
    }

    static byte[] toZipByteArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bufOut)) {
            zos.putNextEntry(new ZipEntry(zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(obj);
                oos.flush();
                zos.closeEntry();
                zos.flush();
                return bufOut.toByteArray();
            }
        }
    }

    static Object fromByteArray(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bufIn = new ByteArrayInputStream(arr);
        try  {
            ObjectInputStream ois = new ObjectInputStream(bufIn);
            return ois.readObject();
        }
        catch (Exception e){
            System.out.println(e.getClass()+ "Lalala");
            return null;
        }

    }

    static Object fromZipByteArray(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bufIn = new ByteArrayInputStream(arr);
        try (ZipInputStream zis = new ZipInputStream(bufIn)) {
            ZipEntry zen = zis.getNextEntry();
            if (zen.getName().equals(zipEntryName) == false) {
                throw new IOException("invalid block");
            }
            try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                return ois.readObject();
            }
        }
    }

    public static long writeObject(RandomAccessFile file, Serializable obj, Boolean zipped) throws IOException {
        long res = file.length();
        file.seek(res);
        byte[] arr;//= toByteArray(obj);
        if (zipped) {
            arr = toZipByteArray(obj);
            file.writeByte(1);
        } else {
            arr = toByteArray(obj);
            file.writeByte(0);
        }
        file.writeInt(arr.length);
        file.write(arr);
        file.setLength(file.getFilePointer());
        return res;
    }

    public static Object readObject(RandomAccessFile file, long pos, boolean[] isZipped) throws IOException, ClassNotFoundException {
        file.seek(pos);
        byte zipped = file.readByte();
        int len = file.readInt();
        byte[] arr = new byte[len];
        file.read(arr);
        if (isZipped != null) {
            isZipped[0] = (zipped != 0);
        }
        if (zipped == 0) {
            return fromByteArray(arr);
        } else {
            if (zipped == 1) {
                return fromZipByteArray(arr);
            } else {
                throw new IOException("Invalid format");
            }
        }
    }
}
