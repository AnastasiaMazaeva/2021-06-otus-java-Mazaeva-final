package homework.domain.repository;

import homework.domain.model.Document;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Modifying
    @Query("delete from permission where document = :id; delete from document where id = :id")
    void delete(@Param("id") Long id);
}
