package Frames;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class SportComplex implements Serializable {
    Client client;
    Client coach;
    int minutes;
    String code;
    int tarif;

    public Client getClient() {
        return client;
    }
    public Client getCoach() {
        return coach;
    }
    public int getMinutes() {
        return minutes;
    }
    public String getCode() {
        return code;
    }
    public int getTarif() {
        return tarif;
    }

    public SportComplex(Client c1, Client c2,String s, int duration, int tarif) {

        this.client = new Client(c1.getName(),c1.getSurname());
        this.coach = new Client(c2.getName(), c2.getSurname());
        this.minutes = duration;
        this.code = new String(s+c2.getName().charAt(0) + c2.getSurname().charAt(0));
        this.tarif = tarif;


    }


    @Override
    public String toString() {
        return "SportComplex{" +
                "client = " + client.getName() + " "+client.getSurname()+
                ", coach = " + coach.getName() + " "+coach.getSurname()+
                ", minutes = " + minutes +
                ", code = " + code  +
                ", tarif = " + tarif +
                '}';
    }

    public SportComplex() {
    }

    public static SportComplex read(Scanner s, PrintStream out) throws MyException {

        System.out.println("Please, enter person name: ");
        Client first, second;
        if(s.hasNextLine() == true) {
            String buf = new String(s.nextLine());
            String[] curry = buf.split(" ");
            first = new Client(curry[0], curry[1]);

        }
        else  throw new MyException("Wrong");
        System.out.println("Please, enter coach name: ");
        if(s.hasNextLine() == true) {
            String buf2 = new String(s.nextLine());
            String[] curry2 = buf2.split(" ");
            second = new Client(curry2[0], curry2[1]);
        }
        else  throw new MyException("Wrong");
        System.out.println("Please, enter type, minutes and tarif: ");
        if(s.hasNextLine() == true) {
            String buf3 = new String(s.nextLine());
            String [] q = buf3.split(" ");
            SportComplex sp = new SportComplex(first,second,q[0],Integer.parseInt(q[1]),Integer.parseInt(q[2]));
            System.out.println("Information was appending");
            return sp;
        }

        return null;



    }
}
