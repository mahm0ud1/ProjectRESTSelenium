package com.mahmoud.ui.pages.htmltables;

public class HTMLTablesPage {
    private Actions actions;
    private Verify verify;

    private static HTMLTablesPage htmlTablesPage;

    public HTMLTablesPage() {
        this.actions = new Actions();
        this.verify = new Verify();
    }

    private static HTMLTablesPage getInstance() {
        if(htmlTablesPage == null)
            htmlTablesPage = new HTMLTablesPage();
        return htmlTablesPage;
    }

    public static Actions getActions() {
        return getInstance().actions;
    }

    public static Verify getVerify() {
        return getInstance().verify;
    }
}
