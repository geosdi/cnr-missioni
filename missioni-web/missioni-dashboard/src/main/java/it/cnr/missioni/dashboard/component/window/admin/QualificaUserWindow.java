package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.QualificaUserAction;
import it.cnr.missioni.dashboard.component.form.qualificaUser.IQualificaUserForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.QualificaUser;

public class QualificaUserWindow extends IWindow.AbstractWindow<QualificaUser, QualificaUserWindow> {

    /**
     *
     */

    public static final String ID = "qualificaUserwindow";
    /**
     *
     */
    private static final long serialVersionUID = -5429194287713891357L;
    private IQualificaUserForm qualificaUserForm;

    protected QualificaUserWindow() {
    }

    public static QualificaUserWindow getQualificaUserWindow() {
        return new QualificaUserWindow();
    }

    public QualificaUserWindow build() {
        setId(ID);
        buildWindow();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    protected HorizontalLayout buildTab() {
//        this.qualificaUserForm = new QualificaUserForm(qualificaUser,true,true,modifica);
        this.qualificaUserForm = IQualificaUserForm.QualificaUserForm.getQualificaUserForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        HorizontalLayout tab = super.buildTab("Qualifica", FontAwesome.GRADUATION_CAP, this.qualificaUserForm);
        return tab;
    }

    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -4972204396092590093L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    QualificaUser qualificaUser = qualificaUserForm.validate();
                    DashboardEventBus.post(new QualificaUserAction(qualificaUser, modifica));
                    close();
                } catch (InvalidValueException | CommitException e) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                            Type.ERROR_MESSAGE);
                }
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public QualificaUserWindow self() {
        return this;
    }

}
