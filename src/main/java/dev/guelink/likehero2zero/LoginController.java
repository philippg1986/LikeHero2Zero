package dev.guelink.likehero2zero;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    MainApp mainApp;

    @Inject
    CurrentUser currentUser;

    private static final String salt = "wosfowhuzsid4wySfocytqossiDzaj9zahdy";
    private String lastName;
    private String password;
    private String failureMessage;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public void checkLogin() {
        if(!currentUser.isValid()) {
            failureMessage = "please login first";
            FacesContext fc = FacesContext.getCurrentInstance();
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "/login.xhtml?faces-redirect=true");
        }
    }

    public String logout() {
        currentUser.reset();
        return "/index.xhtml?faces-redirect=true";
    }

    public void postValidateUser(ComponentSystemEvent event) {
        System.out.println("postValidateUser");
        UIInput temp = (UIInput) event.getComponent();
        this.lastName = (String) temp.getValue();
    }

    public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        //tempUsername = "scientist1";
        mainApp.validateUsernameAndPassword(currentUser, lastName, password, salt);
        System.out.println("validate Login for " + lastName + " Result: " + currentUser.isValid());
        if (!currentUser.isValid()) {
            System.out.println("validate user failed");
            throw new ValidatorException(new FacesMessage("Invalid username or password"));
        }
    }

    public String login() {
        if (currentUser.isScientist()) {
            this.failureMessage = "";
            return "backend?faces-redirect=true";
        } else {
            System.out.println("No scientist");
            this.failureMessage = "Invalid username or password";
            return "";
        }
    }
}
