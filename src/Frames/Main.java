package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import static Frames.SwingConsole.run;

public class Main extends JFrame {

    private static JMenuItem item1 = new JMenuItem("Load");
    private static JMenuItem item6 = new JMenuItem("Append");
    private static JMenuItem item2 = new JMenuItem("Sort");
    private static JMenuItem item3 = new JMenuItem("ReverseSort");
    private static JMenuItem item4 = new JMenuItem("Student");
    private static JMenuItem item5 = new JMenuItem("Find");
    private static JMenuItem item7 = new JMenuItem("Delete");
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jMenu1 = new JMenu("File");
    private JMenu jMenu2 = new JMenu("About");



    private static JTextArea area = new JTextArea(30,50);

    private AppendDialog appendDialog = new AppendDialog(null);
    private FindDialog findDialog = new FindDialog(null);
    private MyDialog myDialog = new MyDialog(null);
    private static File fileData = new File("SportComplex.dat");


    public Main() throws HeadlessException {
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                try {
                    openFile();
                    printFile();
                }
                catch (Exception ez){
                    System.out.println(ez.getMessage());
                }

            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                try{
                    printFile("fc",false);
                }
                catch (Exception ex){
                    System.out.println(ex.getClass());
                }
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                area.setText("");
                try{
                    printFile("fc",true);
                }
                catch (Exception ex){
                    System.out.println(ex.getClass());
                }
            }
        });
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myDialog.setVisible(true);
            }
        });
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findDialog.setVisible(true);
            }
        });
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                appendDialog.setVisible(true);
                area.setText("");
                try {
                    printFile();
                }
                catch (Exception ez){
                    System.out.println(ez.getMessage());
                }
            }
        });
        item7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFile();
                area.setText("");
                try {
                    printFile();
                }
                catch (Exception ez){
                    System.out.println(ez.getMessage());
                }

            }
        });

        item1.setToolTipText("You can load all objects");
        item6.setToolTipText("You can append a new SportComplex");
        item7.setToolTipText("You can delete all SportComplexes");


        jMenu1.add(item1);
        jMenu1.add(item6);
        jMenu1.add(item2);
        jMenu1.add(item3);
        jMenu1.add(item5);
        jMenu1.add(item7);
        jMenu2.add(item4);
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        setJMenuBar(jMenuBar);
        setLayout(new FlowLayout());


        add(new JScrollPane(area));
    }
    public void openFile(){
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setSelectedFile(fileData);
        if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            fileData = jfc.getSelectedFile();
        }
        try {
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        run(new Main(),600,550);
    }

    private static String encoding = "Cp866";
    private static PrintStream outStream = System.out;
    private static Scanner fin = new Scanner( System.in );

    static final String fname = "SportComplex.dat";
    static final String fnameB = "SportComplex.~dat";
    static final String iname = "SportComplex.idx";
    static final String inameB = "SportComplex.~idx";



    private static void deleteBackUp() {
        new File(fnameB).delete();
        new File(inameB).delete();
    }
    static void deleteFile() {
        deleteBackUp();
        new File(fname).delete();
        new File(iname).delete();
    }
    private static void backup() {
        deleteBackUp();
        new File(fname).renameTo(new File(fnameB));
        new File(iname).renameTo(new File(inameB));
    }
    static boolean deleteFile(String[] args) throws ClassNotFoundException, IOException, MyException {
        if (args.length != 3) {
            System.err.println("Invalid ammount of args");
            return false;
        }
        long [] pos = null;
        try{
            MyIndex idx = MyIndex.load(iname);
            IndexInt pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            if (pidx.contains(args[2]) == false) {
                System.err.println("Key not found" + args[2]);
                return false;
            }
            pos = pidx.get(args[2]);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        backup();
        Arrays.sort(pos);
        try{
            MyIndex idx = MyIndex.load(iname);
            RandomAccessFile fB = new RandomAccessFile(fnameB, "rw");
            RandomAccessFile f = new RandomAccessFile(fname, "rw");
            boolean[] wasZipped = new boolean[] {false};
            long poss;
            while((poss = fB.getFilePointer()) < fB.length()) {
                SportComplex l = (SportComplex) Buffer.readObject(fB, poss, wasZipped);
                if (Arrays.binarySearch(pos, poss) < 0) {
                    long ptr = Buffer.writeObject(f, l, wasZipped[0]);
                    idx.put(l, ptr);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    static SportComplex read_SportComplex(ArrayList<String> arr) throws MyException, MyException {
        if(arr.size() ==6){
            Client t1 = new Client(arr.get(0),arr.get(1));
            Client t2 = new Client(arr.get(2),arr.get(3));
            return new SportComplex(t1,t2,arr.get(4),Integer.parseInt(arr.get(5)),20);
        }
        return null;
    }
    static void appendFile(Boolean zipped, ArrayList<String> a) throws FileNotFoundException, IOException, ClassNotFoundException, MyException, MyException{
        int flag = 1;
        SportComplex l;
        try( MyIndex idx = MyIndex.load(iname); RandomAccessFile raf = new RandomAccessFile(fname, "rw")){

            if (( l = read_SportComplex(a))!= null ) {
                idx.test(l);
                long pos = Buffer.writeObject(raf, l, zipped);
                idx.put(l, pos);
            }
        }

    }
    private static void printRecord(RandomAccessFile raf, String key, IndexInt pidx) throws ClassNotFoundException, IOException{
        long [] poss = pidx.get(key);
        for(long pos : poss) {
            //System.out.println("******Key : " + key + " points to ");
            printRecord(raf, pos);
        }
    }
    private static void printRecord(RandomAccessFile raf, long pos) throws ClassNotFoundException, IOException {
        boolean [] wasZipped = new boolean[] {false};
        SportComplex l = (SportComplex) Buffer.readObject(raf, pos, wasZipped);
        if(l == null){
            area.append("NULL");
        }
        if (wasZipped[0] == true) {
            System.out.println(" compressed");
        }
        area.append("record in position : " + pos + " " + l +"\n");
    }
    static boolean printFile(String arg, boolean reverse) throws ClassNotFoundException, IOException{
        try( MyIndex idx = MyIndex.load(iname);
             RandomAccessFile raf = new RandomAccessFile(fname, "rw")){

            IndexInt pidx = indexByArg(arg, idx);
            if(pidx == null) {
                return false;
            }
            String[] keys = pidx.getKeys(reverse ? new KeyCompRev() : new KeyComp());
            for (String key : keys) {
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
    static void printFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        long pos;
        int rec = 0;
        try(RandomAccessFile raf = new RandomAccessFile(fname, "rw")){
            while((pos = raf.getFilePointer()) < raf.length()) {
                area.append("#" + (++rec));
                printRecord(raf, pos);
            }

        }
    }
    private static IndexInt indexByArg(String arg, MyIndex idx) {
        IndexInt pidx = null;
        if (arg.equals("fc"))
            pidx = idx.fio_client;
        else if (arg.equals("fco"))
            pidx = idx.name_coach;
        else if (arg.equals("m"))
            pidx = idx.minutes;
        else
            System.err.println("Invalid index : " + arg);
        return pidx;
    }
    static boolean findByKey(ArrayList<String> args) throws IOException, ClassNotFoundException{
        if(args.size() != 3) {
            System.err.println("Invalid num of args");
            return false;
        }
        try{
            area.setText("");
            MyIndex idx = MyIndex.load(iname);
            RandomAccessFile raf = new RandomAccessFile(fname, "rw");
            IndexInt pidx = indexByArg(args.get(2), idx);
            if (pidx.contains(args.get(0)) == false) {
                area.append("Key not found : " + args.get(0));
                return false;
            }

            printRecord(raf, args.get(0), pidx);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    static boolean findByKey(ArrayList<String> args, Comparator<String> comp) throws ClassNotFoundException, IOException{
        if (args.size() != 3) {
            System.err.println("Invalid num of args");
            return false;
        }
        try( MyIndex idx = MyIndex.load(iname); RandomAccessFile raf = new RandomAccessFile(fname, "rw")){

            area.setText("");

            if(args.get(1).equals("f")){
                findByKey(args);
                return true;
            }
            else if(args.get(1).equals("fr")){
                IndexInt pidx = indexByArg(args.get(2), idx);
                String[] keys = pidx.getKeys(comp);
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    if(args.get(2).equals("m")){
                        if(Integer.parseInt(key) < Integer.parseInt(args.get(0)))
                            break;
                    }
                    else if (key.compareTo(args.get(0)) == 1) {
                        break;
                    }
                    else if(key.equals(args.get(0)))
                        break;
                    printRecord(raf, key, pidx);
                }
            }
            else if(args.get(1).equals("fl")){
                IndexInt pidx = indexByArg(args.get(2), idx);
                String[] keys = pidx.getKeys(comp);
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    if(args.get(2).equals("m")){
                        if(Integer.parseInt(key) > Integer.parseInt(args.get(0)))
                            break;
                    }
                    else if (key.compareTo(args.get(0)) == 1) {
                        break;
                    }
                    else if(key.equals(args.get(0)))
                        break;
                    printRecord(raf, key, pidx);
                }
            }

        }
        return true;
    }


}