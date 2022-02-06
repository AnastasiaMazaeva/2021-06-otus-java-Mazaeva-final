package homework.web;

import homework.domain.model.Document;
import homework.domain.model.User;
import homework.service.interfaces.DocumentService;
import homework.service.interfaces.UserService;
import homework.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;

    @GetMapping(value = "/download/{documentId}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void download(@PathVariable Long documentId, HttpServletResponse response) {
        Document document = documentService.findById(documentId);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + document.getName();
        response.setHeader(headerKey, headerValue);
        StreamUtils.copy(documentService.download(documentId), response);
    }

    @PostMapping(value = "delete/{documentId}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public String delete(@PathVariable Long documentId) {
        documentService.delete(documentId);
        return "redirect:/";
    }

    @PostMapping(value = "/upload")
    public String uploadDocument(@RequestParam("description") String description,
                                 @RequestParam("file") MultipartFile file,
                                 HttpServletRequest request,
                                 RedirectAttributes attributes) {
        User user = userService.findByUsername(request.getUserPrincipal().getName()).orElseThrow();
        InputStream inputStream = StreamUtils.get(file);
        if (inputStream == null) {
            attributes.addFlashAttribute("message", "Can't save file!");
            return "redirect:/";
        }
        documentService.save(new Document(file.getOriginalFilename(), description, user.getId()), inputStream);
        attributes.addFlashAttribute("message", "Saved successfully");
        return "redirect:/";
    }
}
