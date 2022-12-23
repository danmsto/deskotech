package com.example.security;


import com.example.security.domain.Desk;
import com.example.security.domain.User;
import com.example.security.domain.dto.UserRoles;
import com.example.security.properties.AdminProperties;
import com.example.security.properties.DeskProperties;
import com.example.security.properties.RsaKeyProperties;
import com.example.security.repository.BookingRepository;
import com.example.security.repository.DeskRepository;
import com.example.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableConfigurationProperties({RsaKeyProperties.class, AdminProperties.class, DeskProperties.class})
@SpringBootApplication
public class DeskBookerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DeskBookerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, DeskRepository deskRepository, PasswordEncoder encoder, AdminProperties adminProperties, DeskProperties deskProperties) {
        return args -> {
            User admin = new User();
            admin.setUsername(adminProperties.getUsername());
            admin.setPassword(encoder.encode(adminProperties.getPassword()));
            admin.setRoles(UserRoles.ROLE_ADMIN + "," + UserRoles.ROLE_USER);
            admin.setFirstName("Mr");
            admin.setSurname("Admin");
            admin.setVerified(true);
            admin.setVerified(true);
            userRepository.save(admin);

            for (int i = deskProperties.getMinDeskNumber(); i <= deskProperties.getMaxDeskNumber(); i++) {
                deskRepository.save(new Desk(i));
            }

        };
    }

}
