package homework.db.service;

import homework.domain.projection.PermissionRepresentation;

import java.util.List;

public interface DbServicePermission {

    void save(Long documentId, Long receiverId);

    List<PermissionRepresentation> findByUser(Long userId);
}
