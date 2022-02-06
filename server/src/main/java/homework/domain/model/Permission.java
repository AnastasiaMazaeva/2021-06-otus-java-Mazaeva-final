package homework.domain.model;

import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("permission")
public class Permission {

    @Column("document")
    private Long document;

    @PersistenceConstructor
    public Permission(Long document) {
        this.document = document;
    }
}
