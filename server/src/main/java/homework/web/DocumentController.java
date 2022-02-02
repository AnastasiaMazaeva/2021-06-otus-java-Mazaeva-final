package homework.web;

import homework.domain.model.Document;
import homework.service.interfaces.DocumentService;
import homework.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping(value = "download/{documentId}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void download(@PathVariable Long documentId, HttpServletResponse response) {
        Document document = documentService.findById(documentId);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + document.getName();
        response.setHeader(headerKey, headerValue);
        //response.setContentType("application/octet-stream");
        StreamUtils.copy(documentService.download(documentId), response);
    }

    @DeleteMapping(value = "delete/{documentId}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public String delete(@PathVariable Long documentId) {
        documentService.delete(documentId);
        return "home";
    }
}
