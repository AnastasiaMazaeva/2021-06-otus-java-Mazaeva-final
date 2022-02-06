package homework.db.service;

import homework.db.sessionmanager.TransactionManager;
import homework.domain.model.Permission;
import homework.domain.projection.PermissionRepresentation;
import homework.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbServicePermissionImpl implements DbServicePermission {

    private final TransactionManager transactionManager;
    private final PermissionRepository permissionRepository;

    @Override
    public void save(Long documentId, Long receiverId) {
        transactionManager.callInTransaction(() -> {
            Optional<Permission> permission = permissionRepository.findPermissionWithUserAndDocument(receiverId, documentId);
            if (permission.isEmpty()) {
                permissionRepository.save(documentId, receiverId);
            }
        });
    }

    @Override
    public List<PermissionRepresentation> findByUser(Long userId) {
        return permissionRepository.findPermissionsWithUserId(userId);
    }
}
