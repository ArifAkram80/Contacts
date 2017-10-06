package com.example.arif.contacts;

/**
 * Created by Arif on 9/18/2017.
 */

public class myDataRetriver {
    private String Name;
    private String Mob;
    private String Email;
    private byte[] image;

    myDataRetriver(String Name, byte[] image)
    {
        this.Name = Name;
        this.image = image;
    }
    myDataRetriver(byte[] image)
    {
        this.image = image;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String mob) {
        Mob = mob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
