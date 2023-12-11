package jrails;


import books.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model();
    }

    @Test
    public void testSave() {
        // Adding the first book to the database
        Book b1 = new Book();
        b1.title = "Lord of The Rings";
        b1.author = "J.R.R. , Tolkien";
        b1.num_copies = 50;
        b1.save();


        // Adding the second book and changing a field
        Book b2 = new Book();
        b2.title = "1984";
        b2.author = "George Orwell";
        b2.num_copies = 12;
        b2.save();
        b2.author = "J.K Rowling";
        b2.save();


        // Adding a book with empty string field
        Book b3 = new Book();
        b3.title = "Malibu Rising";
        b3.author = "";
        b3.num_copies = 42;
        b3.save();

        // Adding a book with null field
        Book b4 = new Book();
        b4.author = "Ahmet Ümit";
        b4.num_copies = 3;
        b4.save();
        b4.destroy();

        Book book42 = Model.find(Book.class, b1.id());
        assert(book42.title.equals(b1.title));
        assert(book42.author.equals(b1.author));
        assert(book42.num_copies == b1.num_copies);

        List<Book> books = Model.all(Book.class);
        for (Book b : books) {
            System.out.println(b.title);
            System.out.println(b.author);
            System.out.println(b.num_copies);
        }



    }

    @Test
    public void id() {
        assertThat(model.id(), notNullValue());
    }

    @After
    public void tearDown() throws Exception{
    }
}