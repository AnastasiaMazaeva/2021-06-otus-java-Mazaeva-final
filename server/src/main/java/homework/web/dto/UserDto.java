package homework.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Set<DocumentDto> documents;
    private Set<PermissionDto> permissions;
}
