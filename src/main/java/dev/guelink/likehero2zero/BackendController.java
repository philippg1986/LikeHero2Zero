package dev.guelink.likehero2zero;

import guelink.dev.likeherotozero.DBUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class BackendController implements Serializable {

    @Inject
    MainApp mainApp;


    private List<Emission> emissions;

    public BackendController() {
    }

    public String addEmission(Emission emission) throws Exception {

        System.out.println("BackendController addEmission: " + emission.toString());

        DBUtil dbUtil = new DBUtil();
        dbUtil.addEmission(emission);

        mainApp.getEmissions();

        return "/backend.xhtml?faces-redirect=true";
    }
}
