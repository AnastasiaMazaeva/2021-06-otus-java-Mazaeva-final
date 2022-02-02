package client.utils;

import client.exception.FileException;
import client.model.Model;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class FileUtils {

    private byte[] read(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Не удалось вычитать файл {}", path.getFileName());
            throw new FileException(e);
        }
    }

    public List<Model> handle(String token, Path directory) {
        List<Model> list = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(directory)) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                list.add(new Model(token, file.getFileName().toString(), toBase64(read(file))));
                delete(file);
            });
            return list;
        } catch (IOException e) {
            log.error("Не удалось обработать директорию {}", directory.getFileName());
            throw new FileException(e);
        }
    }

    private void delete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error("Не удалось удалить файл {}", file.getFileName());
            throw new FileException(e);
        }
    }

    private String toBase64(byte[] bytes) {
        return Base64.encodeBase64URLSafeString(bytes);
    }
}
