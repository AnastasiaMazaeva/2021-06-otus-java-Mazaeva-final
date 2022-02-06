package homework.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table("dat_user")
public class User {
    @Id
    private Long id;
    private String login;
    private String password;
    private Boolean isActive = true;
    private Role role = Role.ROLE_USER;
    private UUID token = UUID.randomUUID();
    @MappedCollection(idColumn = "owner", keyColumn = "owner")
    private Set<Document> documents = new HashSet<>();
    @MappedCollection(idColumn = "receiver", keyColumn = "receiver")
    private Set<Permission> permissions = new HashSet<>();

    @PersistenceConstructor
    public User(Long id, String login, String password, Boolean isActive, Role role, UUID token, Set<Document> documents, Set<Permission> permissions) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isActive = isActive;
        this.role = role;
        this.token = token;
        this.documents = documents;
        this.permissions = permissions;
    }

    public User() {

    }

}
