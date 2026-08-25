package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class CustomerModelTest {

    static class TestCustomerView extends CustomerView {
        @Override
        public void update(String imageName, String searchResult, String trolley, String receipt) {
            // Do nothing for unit testing
        }
    }
    @Test
    void makeOrganisedTrolley() {
        CustomerModel cm = new CustomerModel();
        Product p = new Product("0001", "Tv", "0001.jpg", 12.01, 100);
        cm.setTheProduct(p);
        cm.makeOrganisedTrolley();
        cm.makeOrganisedTrolley();
        cm.makeOrganisedTrolley();
        ArrayList<Product> tro = cm.getTrolley();
        assertEquals(1, tro.size());
        assertEquals(3, tro.get(0).getOrderedQuantity());

    }


    @Test
    void trolleyShouldBeSortedByProductId() {
        CustomerModel cm = new CustomerModel();

        Product p3 = new Product("0003", "Watch", "0003.jpg", 20.00, 100);
        Product p1 = new Product("0001", "TV", "0001.jpg", 200.00, 100);
        Product p2 = new Product("0002", "Radio", "0002.jpg", 30.00, 100);

        cm.setTheProduct(p3);
        cm.makeOrganisedTrolley();

        cm.setTheProduct(p1);
        cm.makeOrganisedTrolley();

        cm.setTheProduct(p2);
        cm.makeOrganisedTrolley();

        ArrayList<Product> trolley = cm.getTrolley();

        assertEquals("0001", trolley.get(0).getProductId());
        assertEquals("0002", trolley.get(1).getProductId());
        assertEquals("0003", trolley.get(2).getProductId());
    }
    @Test
    void removeProductFromTrolley() {
        CustomerModel cm = new CustomerModel();
        cm.cusView = new TestCustomerView();

        Product p1 = new Product(
                "0001", "TV", "0001.jpg", 200.00, 100
        );

        Product p2 = new Product(
                "0002", "Radio", "0002.jpg", 30.00, 100
        );

        cm.setTheProduct(p1);
        cm.makeOrganisedTrolley();

        cm.setTheProduct(p2);
        cm.makeOrganisedTrolley();

        assertEquals(2, cm.getTrolley().size());

        cm.removeFromTrolley("0001");

        assertEquals(1, cm.getTrolley().size());
        assertEquals("0002", cm.getTrolley().get(0).getProductId());
    }
    @Test
    void updateProductQuantity() {
        CustomerModel cm = new CustomerModel();
        cm.cusView = new TestCustomerView();

        Product p = new Product(
                "0001", "TV", "0001.jpg", 200.00, 100
        );

        cm.setTheProduct(p);
        cm.makeOrganisedTrolley();

        cm.updateProductQuantity("0001", 5);

        assertEquals(5, cm.getTrolley().get(0).getOrderedQuantity());
    }
    @Test
    void zeroQuantityShouldRemoveProduct() {
        CustomerModel cm = new CustomerModel();
        cm.cusView = new TestCustomerView();

        Product p = new Product(
                "0001", "TV", "0001.jpg", 200.00, 100
        );

        cm.setTheProduct(p);
        cm.makeOrganisedTrolley();

        assertEquals(1, cm.getTrolley().size());

        cm.updateProductQuantity("0001", 0);

        assertEquals(0, cm.getTrolley().size());
    }
    @Test
    void quantityShouldNotExceedStock() {
        CustomerModel cm = new CustomerModel();
        cm.cusView = new TestCustomerView();

        Product p = new Product(
                "0001", "TV", "0001.jpg", 200.00, 5
        );

        cm.setTheProduct(p);
        cm.makeOrganisedTrolley();

        cm.updateProductQuantity("0001", 10);

        assertEquals(1, cm.getTrolley().get(0).getOrderedQuantity());
    }
    @Test
    void quantityEqualToStockShouldBeAllowed() {
        CustomerModel cm = new CustomerModel();
        cm.cusView = new TestCustomerView();

        Product p = new Product(
                "0001", "TV", "0001.jpg", 200.00, 5
        );

        cm.setTheProduct(p);
        cm.makeOrganisedTrolley();

        cm.updateProductQuantity("0001", 5);

        assertEquals(5, cm.getTrolley().get(0).getOrderedQuantity());
    }
    @Test
    void differentProductsShouldRemainSeparate() {
        CustomerModel cm = new CustomerModel();

        Product p1 = new Product(
                "0001", "TV", "0001.jpg", 200.00, 10
        );

        Product p2 = new Product(
                "0002", "Radio", "0002.jpg", 30.00, 10
        );

        cm.setTheProduct(p1);
        cm.makeOrganisedTrolley();

        cm.setTheProduct(p2);
        cm.makeOrganisedTrolley();

        ArrayList<Product> trolley = cm.getTrolley();

        assertEquals(2, trolley.size());
        assertEquals("0001", trolley.get(0).getProductId());
        assertEquals("0002", trolley.get(1).getProductId());
    }
}
//J unit testing implemented