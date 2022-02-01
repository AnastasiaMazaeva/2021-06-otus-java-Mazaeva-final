package homework.web;

import homework.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("document/{documentId}/share/{userId}")
    public void share(@PathVariable Long documentId, @PathVariable Long userId) {
        permissionService.permit(documentId, userId);
    }
}
