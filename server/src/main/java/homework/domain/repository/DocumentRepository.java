package homework.domain.repository;

import homework.domain.model.Document;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Query("select d.* from document d inner join permission on permission.document = d.id and permission.id = :permissionId")
    Document findByPermissionId(@Param("permissionId") Long permissionId);

}
