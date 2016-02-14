import java.util.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.math.BigDecimal;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_ISSUED = 2;
    public static final int PRODUCT_HAS_HOLD = 3;
    public static final int PRODUCT_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_HOLD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_CLIENT = 9;
    private ProductList ProductList;
    private ClientList clientList;
    private static Warehouse warehouse;
    private List transactions = new LinkedList(); //product transaction
    private List invoice = new LinkedList();
    protected Double sumProduct = 0.0;

    private Warehouse() {
        ProductList = ProductList.instance();
        clientList = ClientList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance(); // instantiate all singletons
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Product addProduct(String name, String manufacturer, String id, Double price, Integer quantity) {
        Product product = new Product(name, manufacturer, id, price,quantity );
        if (ProductList.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    public Client addClient(String name, String address, String phone) {
        Client client = new Client(name, address, phone);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }

    public Iterator getProducts() {
        return ProductList.getProducts();
    }

    public Iterator getClients() {
        return clientList.getClients();
    }

    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIdServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return ProductList + "\n" + clientList;
    }

    public Client testClient(String clientID) {
        ClientList clientlist = ClientList.instance();
        Client client = clientlist.checkClient(clientID);
        try {
            if (client != null) {
                //System.out.println("user " + client.getName() + " exists");
                return client;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Product testProduct(String productID) {
        ProductList catlist = ProductList.instance();
        Product product = catlist.checkProduct(productID);
        try {
            if (product != null) {
                //System.out.println("user " + client.getName() + " exists");
                return product;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean insertTransaction(Client client, Product product, Date date) {
        try {
            transactions.add(product.getProductName() + " sold on " + date + " to Client: "+client.getName() +" at price "+ product.getPrice());
            return TRUE;
        } catch (Exception e) {
            return FALSE;
        }
    }
    
        public void decQuantity(Product product){
        product.quantity-=1;
    }

    public List getTransactions() {
    return transactions;
  }

    public boolean insertInvoice(Product product, Client client,Date date){
        invoice.add(product.getProductName() + " sold on " + date + " to Client: "+client.getName() +" at price "+ product.getPrice());
  return TRUE;  
    }
    
    public List getInvoice() {
    return invoice;
    }
    
    public Double getSumProduct(){
    return sumProduct;
    }
    
    public void flushInvoice(){
    invoice.clear();
    }
    
    
    public Product issueBook(String memberID, String bookID) {

	Product product = ProductList.search(bookID);
	if(product == null) {	
	return(null);
	}

	if(product.getBorrower() != null){
            System.out.println("Book issued to "+book.getBorrower().getName()+". Book can't be issued !!!");
		return null;
	}
	Member member = memberList.search(memberID);
	if(member == null) {
		return null;
	}
	if(!(book.issue(member) && member.issue(book) )){
		return null;
	}

	return book;
	
}
    
}

