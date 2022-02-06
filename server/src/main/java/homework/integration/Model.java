package homework.integration;

import lombok.Data;

import java.util.UUID;

@Data
public class Model {
    private UUID token;
    private String filename;
    private String body;
}
