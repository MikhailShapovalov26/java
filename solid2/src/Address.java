public class Address {
    private String country;
    private String index;
    private String city;
    private String street;

    public Address(String country, String index, String city, String street) {
        this.country = country;
        this.index = index;
        this.city = city;
        this.street = street;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public String getIndex() {
        return index;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "country='" + country + '\'' +
                ", index='" + index + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'';
    }
}
