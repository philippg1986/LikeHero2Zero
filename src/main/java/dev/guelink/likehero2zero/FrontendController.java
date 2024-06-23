package dev.guelink.likehero2zero;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
public class FrontendController implements Serializable {

    @Inject
    private MainApp mainApp;

    private List<Emission> emissions;
    private List<Emission> filteredEmissions;
    private List<FilterMeta> filterBy;
    private boolean globalFilterOnly;

    @PostConstruct
    public void init() throws Exception {
        globalFilterOnly = false;
        emissions = mainApp.getEmissions();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? "" : filter.toString().trim().toLowerCase();
        if(LangUtils.isBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Emission emission = (Emission) value;

        return emission.getCountry().toLowerCase().contains(filterText);
    }

    //public void toggleGlobalFilter() {
    //    setGlobalFilterOnly(!isGlobalFilterOnly());
    //}

    private int getInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public List<Emission> getEmissions() {
        return emissions;
    }

    public List<Emission> getFilteredEmissions() {
        return filteredEmissions;
    }

    public void setFilteredEmissions(List<Emission> filteredEmissions) {
        this.filteredEmissions = filteredEmissions;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }
    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

    public void reloadTable() throws Exception {
        mainApp.getEmissions();
    }
}
