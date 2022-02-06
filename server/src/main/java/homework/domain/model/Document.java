package homework.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("document")
public class Document {
    @Id
    private Long id;
    private String name;
    private String description;
    @Column("owner")
    private Long ownerId;

    @PersistenceConstructor
    public Document(Long id, String name, String description, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Document(String name, String description, Long ownerId) {
        this(null, name, description, ownerId);
    }
}
