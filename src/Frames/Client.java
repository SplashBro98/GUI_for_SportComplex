package Frames;

import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    public Client(String name, String surname) {
        this.name = new String(name);
        this.surname = new String(surname);
    }
}
