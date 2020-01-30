package com.example.mytask;

import android.content.Context;

public class ContactModel {

    Context context;


    private String name, number;
    private int imge;



    public ContactModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setImge(int imge) {
        this.imge = imge;
    }

    public int getImge() {
        return imge;
    }
}
