package homework.domain.projection;

import lombok.Value;

@Value
public class PermissionRepresentation {

    Long documentId;
    String documentName;
    String ownerName;
    String documentDescription;
}
