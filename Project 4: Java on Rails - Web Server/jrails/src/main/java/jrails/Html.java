package jrails;

public class Html {
    private String message = "";

    public String toString() {
        //what happens if the html tag is empty
        return message;
    }

    public Html seq(Html h) {
        Html newHtml = new Html();
        newHtml.message = this.message + h.toString();
        return newHtml;
    }

    public Html br() {
        Html newHtml = new Html();
        newHtml.message = this.message + "<br/>";
        return newHtml;
    }

    public Html t(Object o) {
        Html newHtml = new Html();
        newHtml.message = o.toString();         // Use o.toString() to get the text for this
        return newHtml;
    }

    public Html p(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<p>" + child.toString() + "</p>";
        return newHtml;
    }

    public Html div(Html child) {
        Html newHtml = new Html();
        newHtml.message = message + "<div>" + child.toString() + "</div>";
        return newHtml;
    }

    public Html strong(Html child) {
        Html newHtml = new Html();
        newHtml.message = message + "<strong>" + child.toString() + "</strong>";
        return newHtml;
    }

    public Html h1(Html child) {
        Html newHtml = new Html();
        newHtml.message = message + "<h1>" + child.toString() + "</h1>";
        return newHtml;
    }

    public Html tr(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<tr>" + child.toString() + "</tr>";
        return newHtml;
    }

    public Html th(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<th>" + child.toString() + "</th>";
        return newHtml;
    }

    public Html td(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<td>" + child.toString() + "</td>";
        return newHtml;
    }

    public Html table(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<table>" + child.toString() + "</table>";
        return newHtml;
    }

    public Html thead(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<thead>" + child.toString() + "</thead>";
        return newHtml;
    }

    public Html tbody(Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<tbody>" + child.toString() + "</tbody>";
        return newHtml;
    }

    public Html textarea(String name, Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<textarea name=" +  '"' + name + '"' + ">" + child.toString() +  "</textarea>";
        return newHtml;
    }

    public Html link_to(String text, String url) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<a href=" + '"' + url + '"' + ">" + text + "</a>";
        return newHtml;
    }

    public Html form(String action, Html child) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<form action=" + '"' + action + '"' + " accept-charset=" + '"' + "UTF-8" + '"' + " method=" + '"' + "post" + '"' + ">"+ child.toString() +"</form>";
        return newHtml;
    }

    public Html submit(String value) {
        Html newHtml = new Html();
        newHtml.message = this.message + "<input type=" + '"' + "submit"  + '"' + " value="  + '"' + value  + '"' + "/>";
        return newHtml;
    }
}