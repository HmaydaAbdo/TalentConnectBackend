package staport.rh.TalentConnectBackend.security.entities;

import jakarta.persistence.*;
import staport.rh.TalentConnectBackend.security.enums.RoleName;


@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role() {
    }

    public RoleName getRoleName() {
        return roleName;
    }
}