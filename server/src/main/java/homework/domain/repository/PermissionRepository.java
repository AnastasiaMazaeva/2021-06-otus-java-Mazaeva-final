package homework.domain.repository;

import homework.domain.model.Permission;
import homework.domain.projection.PermissionRepresentation;
import homework.domain.rowmapper.PermissionRowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    @Modifying
    @Query("insert into permission(document, receiver) values (:documentId, :receiverId)")
    void save(@Param("documentId") Long documentId, @Param("receiverId") Long receiverId);

    @Query(value = "select p.document as documentId, d.name as documentName, d.description as documentDescription, u.login as ownerName "
            + "from permission p "
            + "inner join document d on p.document = d.id "
            + "inner join dat_user u on d.owner = u.id "
            + "where p.receiver = :userId ", rowMapperClass = PermissionRowMapper.class)
    List<PermissionRepresentation> findPermissionsWithUserId(@Param("userId") Long userId);
}
