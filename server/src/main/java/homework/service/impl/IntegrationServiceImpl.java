package homework.service.impl;

import homework.domain.model.Document;
import homework.domain.model.User;
import homework.integration.Model;
import homework.service.interfaces.DocumentService;
import homework.service.interfaces.IntegrationService;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private final DocumentService documentService;
    private final UserService userService;

    @Override
    public void handleIncome(Model[] models) {
        for (Model model : models) {
            User user = userService.findByToken(model.getToken());
            log.info("User found {}", user.getLogin());
            InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(model.getBody()));
            documentService.save(new Document(model.getFilename(), null, user.getId()), inputStream);
        }
    }
}
