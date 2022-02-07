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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private final DocumentService documentService;
    private final UserService userService;

    @Override
    public void handleIncome(String token, String filename, byte[] bytes) {
        User user = userService.findByToken(UUID.fromString(token));
        log.info("User found {}", user.getLogin());
        InputStream inputStream = new ByteArrayInputStream(bytes);
        documentService.save(new Document(filename, null, user.getId()), inputStream);
    }
}
