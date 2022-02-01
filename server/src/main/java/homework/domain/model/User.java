package homework.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@Table("dat_user")
public class User {
    @Id
    private Long id;
    private String login;
    private String password;
    private Boolean isActive = true;
    private Boolean isOpen = true;
    private Role role;
    @MappedCollection(idColumn = "owner", keyColumn = "owner")
    private Set<Document> documents = new HashSet<>();
    @MappedCollection(idColumn = "receiver", keyColumn = "receiver")
    private Set<Permission> permissions = new HashSet<>();

    @PersistenceConstructor
    public User(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public User(String login, String password, Role role) {

        this(null, login, password, role);
    }
}
