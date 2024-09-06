package CarRental;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class Car 
{
    //variables(data members)
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;
    
    //constructor
    public Car(String carId, String brand,String model,double basePricePerDay)
    {
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;
    }

    //getMethods
    public String getcarId()
    {
        return carId;  
    }

    public String getbrand()
    {
        return brand;
    }

    public String getmodel()
    {
        return model;
    }

    //calculate price
    public double calculaterPrice(int rentalDay)
    {
        return basePricePerDay*rentalDay;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void rent()//if car on rent availability is show false
    {
        isAvailable=false;
    }

    public void returncar() //if car is in showroom availability is show true


    {
        isAvailable=true;
    }

}

// now we create class customer
class Customer
{
    //data members
    private String customerId;
    private String name;

    //constructor
    public Customer(String customerId,String name)
    {
        this.customerId=customerId;
        this.name=name;
    }

    //getMethods
    public String getcustomerId()
    {
        return customerId;
    }

    public String getname()
    {
        return name;
    }

}

// now we create class rental
class Rental
{
    //data members
    private Car car;
    private Customer customer;
    private int days;

    //constructor
    public Rental(Car car,Customer customer,int days)
    {
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    //getMethods
    public Car getcar()
    {
        return car;
    }

    public Customer getcustomer()
    {
        return customer;
    }

    public int getdays()
    {
        return days;
    }
  
}

// now we create class carrentalSystem
class CarRentalSystem 
{
    //data members
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private Scanner scanner;

    //constructor
    public CarRentalSystem()
    {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    //member function
    public void addCar(Car car)
    {
        cars.add(car);
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public void rental(Car car,Customer customer,int days)
    {
        if(car.isAvailable())
        {
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else
        {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car)
    {
        
        car.returncar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals)
        {
            if(rental.getcar() == car)
            {
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null)
        {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully.");
        }
        else
        {
            System.out.println("Car was not rented.");
        }
    }

    public void menu()
    {
        try (Scanner scanner = new Scanner(System.in)) {
            while(true)
            {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); //consume newline

                if(choice == 1)
                {
                    System.out.println("\n== Rent a Car==\n");
                    System.out.println("Enter your Name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("\nAvailable Cars:");
                    for(Car car : cars)
                    {
                        if(car.isAvailable())
                        {
                            System.out.println(car.getcarId()+"-"+car.getbrand()+""+car.getmodel());
                        }
                    }

                    System.out.println("\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();

                    System.out.println("Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine();

                    Customer newCustomer = new Customer("cus"+(customers.size()+1),customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null;
                    for (Car car : cars)
                    {
                       if(car.getcarId().equals(carId) && car.isAvailable())
                       {
                        selectedCar = car;
                        break;
                       } 
                    }

                    if(selectedCar != null)
                    {
                        double totalPrice = selectedCar.calculaterPrice(rentalDays);
                        System.out.println("\n== Rental Information ==\n");
                        System.out.println("Customer ID: "+newCustomer.getcustomerId());
                        System.out.println("Customer Name: "+newCustomer.getname());
                        System.out.println("Car: "+selectedCar.getbrand()+""+selectedCar.getmodel());
                        System.out.println("Rental Days: "+rentalDays);
                        System.out.printf("Total Price: $%.2f%n",totalPrice);

                        System.out.println("\nConfirm rental (Yes/No): ");
                        String confirm = scanner.nextLine();

                        if(confirm.equalsIgnoreCase("Yes"))
                        {
                            rental(selectedCar,newCustomer,rentalDays);
                            System.out.println("\nCar rented successfully.");
                        }
                        else
                        {
                            System.out.println("\nRental canceled.");
                        }
                    }
                    else
                    {
                        System.out.println("\nInvalid car selection or car not available for rent.");
                    }
                }
                else if(choice == 2)
                {
                    System.out.println("\n== Renturn a Car ==\n");
                    System.out.println("Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();

                    Car carToReturn = null;
                    for(Car car : cars)
                    {
                        if(car.getcarId().equals(carId) && !car.isAvailable())
                        {
                            carToReturn = car;
                            break;
                        }
                    }

                    if(carToReturn != null)
                    {
                        Customer customer = null;
                        for(Rental rental : rentals)
                        {
                            if(rental.getcar() == carToReturn)
                            {
                                customer = rental.getcustomer();
                                break;
                            }
                        }

                        if(customer != null)
                        {
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by "+customer.getname());
                        }
                        else
                        {
                            System.out.println("Car was not rented or rental information is missing.");
                        }
                    }
                    else
                    {
                        System.out.println("Ivalid car ID or car is not rented.");
                    }
                }
                else if(choice == 3)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid choice. Please entera valid option.");
                }
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");

    }

   
}

//now we create a main class
 class Main
{
   
    public static void main(String[]args)
    {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 80.0);//Different base price per day
        Car car2 = new Car("C002", "Honda", "Accord", 100.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 180.);
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();

    }
 
}
