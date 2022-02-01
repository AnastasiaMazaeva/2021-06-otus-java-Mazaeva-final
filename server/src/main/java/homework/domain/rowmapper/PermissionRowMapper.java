package homework.domain.rowmapper;

import homework.domain.projection.PermissionRepresentation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionRowMapper implements RowMapper<PermissionRepresentation> {
    @Override
    public PermissionRepresentation mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new PermissionRepresentation(rs.getLong("documentId"), rs.getString("documentName"), rs.getString("ownerName"), rs.getString("documentDescription"));
    }
}
