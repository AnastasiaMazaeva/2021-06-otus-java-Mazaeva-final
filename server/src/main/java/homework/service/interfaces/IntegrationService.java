package homework.service.interfaces;

public interface IntegrationService {

    void handleIncome(String token, String filename, byte[] bytes);
}
