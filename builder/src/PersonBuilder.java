public class PersonBuilder {

    protected String name;
    protected String surname;
    protected Integer age;
    protected String address;


    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public PersonBuilder setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Некорректный возраст: " + age);
        }
        this.age = age;
        return this;
    }

    public PersonBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Person build() {
        if (surname == null) {
            throw new IllegalStateException("Фамилия обязательна");
        }

        if (age ==null && address == null){
            return new Person(name, surname);
        }
        if (age ==null ){
            return new Person(name, surname, address);
        }
        if ( address==null ){
            return new Person(name, surname, age);
        }
        return new Person(name, surname, age, address);

    }
}