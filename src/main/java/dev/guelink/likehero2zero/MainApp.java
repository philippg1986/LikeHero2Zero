package dev.guelink.likehero2zero;

import guelink.dev.likeherotozero.DBUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Named
public class MainApp {
    private List<Emission> emissions = new ArrayList<Emission>();
    private List<Scientist> scientists = new ArrayList<Scientist>();
    private List<Emission> lastTenEmissions = new ArrayList<Emission>();

    /*
    *   Scientists Logins:  scientist1 / science
    *                       Schmidt / password
    * */
    /*** scientist Login ohne Datenbankabfrage
    private final String[][] scientists = new String[][] {

            //user scientist1, password science
            new String[] {"scientist1",
                    "dfL2MkD058o5Y/uVS1RmPTKBj9dPNfc6O9DN3/Tf7AEk9wQsr2NNJgx5E75nHNrl44z8TFZv4SwXX3kiSsl0/Q==",
                    "scientist"},
            //new String[] {"scientist1", "scientist2", "scientist3"}
    };
    ***/

    @PostConstruct
    public void init() throws Exception {
        DBUtil dbUtil = new DBUtil();
        emissions = dbUtil.getEmissions();
        scientists = dbUtil.getScientists();
    }

    public List<Emission> getEmissions() throws Exception {
        DBUtil dbUtil = new DBUtil();
        emissions = dbUtil.getEmissions();
        return emissions;
    }

    static String hashPassword(String lastName, String pass, String salt) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digester.digest((lastName + pass + salt).getBytes(StandardCharsets.UTF_8));
            System.out.println("hashBytes: " + Arrays.toString(hashBytes));
            System.out.println("Base64" + Base64.getEncoder().encodeToString(hashBytes));
            System.out.println(lastName + pass + salt);

            return new String(Base64.getEncoder().encode(hashBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void validateUsernameAndPassword(CurrentUser currentUser, String lastName, String password, String salt) {
        String passHash = hashPassword(lastName, password, salt);
        currentUser.reset();
        System.out.println("Current user: " + currentUser);



        for (Scientist scientist : scientists) {
            System.out.println("Scientist: " + scientist);
            if(scientist.getLastName().equals(lastName)) {
                System.out.println(scientist.getLastName() + " = " + lastName);
                if(scientist.getPassword().equals(passHash)) {
                    currentUser.setScientist(true);
                    System.out.println("scientist found");
                    return;
                    }
            }
        }
    }


}
