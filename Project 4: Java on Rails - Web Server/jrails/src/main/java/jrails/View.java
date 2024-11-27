package jrails;

public class View {
    public static Html empty() {
        Html newHtml = new Html();
        return newHtml.t("");
    }

    public static Html br() {
        Html newHtml = new Html();
        return newHtml.br();
    }

    public static Html t(Object o) {
        Html newHtml = new Html();
        return newHtml.t(o);
    }
    public static Html p(Html child) {
        Html newHtml = new Html();
        return newHtml.p(child);
    }

    public static Html div(Html child) {
        Html newHtml = new Html();
        return newHtml.div(child);
    }

    public static Html strong(Html child) {
        Html newHtml = new Html();
        return newHtml.strong(child);
    }

    public static Html h1(Html child) {
        Html newHtml = new Html();
        return newHtml.h1(child);
    }

    public static Html tr(Html child) {
        Html newHtml = new Html();
        return newHtml.tr(child);
    }

    public static Html th(Html child) {
        Html newHtml = new Html();
        return newHtml.th(child);
    }

    public static Html td(Html child) {
        Html newHtml = new Html();
        return newHtml.td(child);
    }

    public static Html table(Html child) {
        Html newHtml = new Html();
        return newHtml.table(child);
    }

    public static Html thead(Html child) {
        Html newHtml = new Html();
        return newHtml.thead(child);
    }

    public static Html tbody(Html child) {
        Html newHtml = new Html();
        return newHtml.tbody(child);
    }

    public static Html textarea(String name, Html child) {
        Html newHtml = new Html();
        return newHtml.textarea(name, child);
    }

    public static Html link_to(String text, String url) {
        Html newHtml = new Html();
        return newHtml.link_to(text, url);
    }

    public static Html form(String action, Html child) {
        Html newHtml = new Html();
        return newHtml.form(action, child);
    }

    public static Html submit(String value) {
        Html newHtml = new Html();
        return newHtml.submit(value);
    }
}