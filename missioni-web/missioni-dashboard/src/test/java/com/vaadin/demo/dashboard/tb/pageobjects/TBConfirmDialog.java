package com.vaadin.demo.dashboard.tb.pageobjects;

import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.WindowElement;

import it.cnr.missioni.dashboard.view.reports.ReportsView;

public class TBConfirmDialog extends TestBenchTestCase {

    private final WindowElement scope;

    public TBConfirmDialog(WebDriver driver) {
        setDriver(driver);
        scope = $(WindowElement.class).id(ReportsView.CONFIRM_DIALOG_ID);
    }

    public void discard() {
        $(ButtonElement.class).caption("Discard Changes").first().click();
    }

}
