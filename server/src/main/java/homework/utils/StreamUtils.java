package homework.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@UtilityClass
public class StreamUtils {

    public void copy(InputStream inputStream, HttpServletResponse response) {
        try (inputStream; ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("Ошибка чтения файла", e);
        }
    }

    public InputStream get(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            log.error("Ошибка загрузки файла", e);
            return null;
        }
    }
}
