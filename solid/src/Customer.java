import java.util.UUID;

public class Customer {

    private final UUID customerUUID = UUID.randomUUID();
    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public UUID getCustomerUUID() {
        return customerUUID;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
