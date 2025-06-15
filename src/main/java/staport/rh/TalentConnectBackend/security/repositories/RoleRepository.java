package staport.rh.TalentConnectBackend.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import staport.rh.TalentConnectBackend.security.entities.Role;
import staport.rh.TalentConnectBackend.security.enums.RoleName;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

}