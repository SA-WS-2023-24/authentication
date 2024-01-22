//package com.htwberlin.userservice;
//
//import com.htwberlin.userservice.core.domain.model.User;
//import com.htwberlin.userservice.core.domain.model.Address;
//import com.htwberlin.userservice.core.domain.service.interfaces.IUserRepository;
//import com.htwberlin.userservice.core.domain.service.interfaces.IAddressRepository;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    private final IUserRepository repository;
//    private final IAddressRepository addresses;
//
//    public DataLoader(IUserRepository repository, IAddressRepository addresses) {
//        this.repository = repository;
//        this.addresses = addresses;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
////        Category[] categories = Category.values();
////
////        for (int i = 0; i < 20; i++) {
////            int randomIndex = new Random().nextInt(Category.values().length);
////            User user = User.builder()
////                    .id(UUID.randomUUID())
////                    .category(categories[randomIndex])
////                    .name("Dummy User" + i)
////                    .description("Dummy user description")
////                    .imageLink("http://dummyImageLinke.com")
////                    .price(new BigDecimal(250 + i))
////                    .build();
////
////            User userForAddress = this.repository.save(user);
////
////            this.repository.findAll().forEach(System.out::println);
////
////            if (new Random().nextBoolean()) {
////                int year = new Random().nextInt(2024);
////                Month[] months = Month.values();
////                Month month = months[new Random().nextInt(months.length)];
////                int day = new Random().nextInt(28) + 1;
////                int hour = new Random().nextInt(24);
////                int minute = new Random().nextInt(60);
////
////                Address address = Address.builder()
////                        .id(UUID.randomUUID())
////                        .userId(UUID.randomUUID())
////                        .content("Important address")
////                        .stars(new Random().nextInt(5))
////                        .publishedDate(LocalDateTime.of(year, month, day, hour,
////                                minute)).user(userForAddress).build();
////
////                System.out.println(address);
////
////                this.addresses.save(address);
////            }
////        }
//    }
//}
