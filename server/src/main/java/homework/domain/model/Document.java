package homework.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("document")
public class Document {
    @Id
    private Long id;
    private String name;
    private String description;
}
