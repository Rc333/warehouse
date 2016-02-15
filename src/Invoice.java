
import java.util.*;
import java.io.*;

public class Invoice implements Serializable {

    String client;
    String product;
    Double price;
    Date date;
    Double sum;

    private List invoice = new LinkedList();

    public Invoice(String product, String client, Integer quantity, Double price, Date date) {
        this.client = client;
        this.product = product;
        this.price = price;
        this.date = date;
        this.sum = price * quantity;

        invoice.add("Customer: " + client + " Product: " + product + " price: " + price + " quantity: " + quantity + "grand_total " + sum);

    }

    public Iterator getInvoice() {
        return invoice.iterator();

    }

}
