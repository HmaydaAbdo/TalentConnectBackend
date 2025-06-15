package staport.rh.TalentConnectBackend.initializers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import staport.rh.TalentConnectBackend.security.entities.Role;
import staport.rh.TalentConnectBackend.security.entities.User;
import staport.rh.TalentConnectBackend.security.enums.RoleName;
import staport.rh.TalentConnectBackend.security.repositories.RoleRepository;
import staport.rh.TalentConnectBackend.security.repositories.UserRepository;

import java.util.List;

@Configuration
public class RolesAndUsersInitializer {

        @Bean
        CommandLineRunner userAndRolesInitializer(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
            return args -> {
                // Initialize Roles
                Role userRole = roleRepo.findByRoleName(RoleName.USER).orElseGet(() -> roleRepo.save(new Role(RoleName.USER)));
                Role adminRole = roleRepo.findByRoleName(RoleName.ADMIN).orElseGet(() -> roleRepo.save(new Role(RoleName.ADMIN)));
                Role superAdminRole = roleRepo.findByRoleName(RoleName.SUPER_ADMIN).orElseGet(() -> roleRepo.save(new Role(RoleName.SUPER_ADMIN)));

                // Initialize Users if they don't exist
                if (userRepo.findByUsername("rania").isEmpty()) {
                    userRepo.save(new User("rania", encoder.encode("12345"), true, List.of(userRole)));
                }
                if (userRepo.findByUsername("salhi").isEmpty()) {
                    userRepo.save(new User("salhi", encoder.encode("12345"), true, List.of(userRole)));
                }
                if (userRepo.findByUsername("ighmer").isEmpty()) {
                    userRepo.save(new User("ighmer", encoder.encode("12345"), true, List.of(userRole, adminRole)));
                }
                if (userRepo.findByUsername("hmayda").isEmpty()) {
                    userRepo.save(new User("hmayda", encoder.encode("12345"), true, List.of(userRole, adminRole, superAdminRole)));
                }
                if (userRepo.findByUsername("ismail").isEmpty()) {
                    userRepo.save(new User("ismail", encoder.encode("12345"), true, List.of(userRole, adminRole, superAdminRole)));
                }
            };
        }
}
