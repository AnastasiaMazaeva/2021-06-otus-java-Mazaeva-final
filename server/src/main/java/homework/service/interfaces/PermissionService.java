package homework.service.interfaces;

import homework.domain.projection.PermissionRepresentation;

import java.util.List;

public interface PermissionService {

    void permit(Long documentId, Long receiverId);

    List<PermissionRepresentation> findByUser(Long userId);
}
