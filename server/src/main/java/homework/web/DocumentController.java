package homework.web;

import homework.domain.model.Document;
import homework.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("download/permission/{documentId}")
    public void share(@PathVariable Long documentId) {
        Document document = documentService.findByPermission(documentId);
    }
}
