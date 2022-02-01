package homework.service.interfaces;

import homework.domain.model.Document;
import homework.domain.model.User;
import homework.domain.projection.PermissionRepresentation;

import java.util.List;

public interface PermissionService {

    void permit(Long documentId, Long receiverId);
    void retrievePermission(Document document, User receiver);
    void retrieveAllPermissions(User owner, User receiver);

    List<PermissionRepresentation> findByUser(Long userId);
}
