package homework.db.service;

import homework.db.sessionmanager.TransactionManager;
import homework.domain.projection.PermissionRepresentation;
import homework.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServicePermissionImpl implements DbServicePermission {

    private final TransactionManager transactionManager;
    private final PermissionRepository permissionRepository;

    @Override
    public void save(Long documentId, Long receiverId) {
        transactionManager.callInTransaction(() ->
            permissionRepository.save(documentId, receiverId));
    }

    @Override
    public List<PermissionRepresentation> findByUser(Long userId) {
        return permissionRepository.findPermissionsWithUserId(userId);
    }
}
