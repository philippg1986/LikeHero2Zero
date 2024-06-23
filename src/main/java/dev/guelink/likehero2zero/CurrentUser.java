package dev.guelink.likehero2zero;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class CurrentUser implements Serializable {

    boolean scientist;

    void reset() {
        scientist = false;
    }

    public boolean isScientist() {
        return scientist;
    }

    public boolean isValid() {
        return isScientist();
    }

    public void setScientist(boolean scientist) {
        this.scientist = scientist;
    }
}
