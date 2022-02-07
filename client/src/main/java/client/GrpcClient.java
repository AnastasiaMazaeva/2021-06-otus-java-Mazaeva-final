package client;

import client.properties.ClientProperties;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@Slf4j
public class GrpcClient {
    private final DocumentServiceGrpc.DocumentServiceStub serviceStub;
    private final String path;
    private final String token;

    public GrpcClient(ClientProperties clientProperties) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clientProperties.getHost(), clientProperties.getPort())
                .usePlaintext()
                .build();
        this.serviceStub = DocumentServiceGrpc.newStub(channel);
        this.path = clientProperties.getPath();
        this.token = clientProperties.getToken();
    }

    public void go() {
        try {
            Path path = Paths.get(this.path);
            try (Stream<Path> paths = Files.walk(path)) {
                paths.filter(Files::isRegularFile).forEach(this::handleFile);
            }
        } catch (Exception ex) {
            log.error("Ошибка обмена: ", ex);
        }
    }

    private void handleFile(Path file) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            StreamObserver<Rpc.UploadDocumentRequest> observer = serviceStub.upload(new StreamObserver<>() {
                @Override
                public void onNext(Rpc.UploadDocumentResponse value) {
                    System.out.println(
                            "File upload status :: " + value.getStatus()
                    );
                }

                @Override
                public void onError(Throwable t) {
                    latch.countDown();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            });

            Rpc.DocumentInfo documentInfo = Rpc.DocumentInfo.newBuilder().setFilename(file.getFileName().toString())
                    .setToken(token).build();
            observer.onNext(Rpc.UploadDocumentRequest.newBuilder().setInfo(documentInfo).build());
            //Thread.sleep(60*1_000);

            InputStream inputStream = Files.newInputStream(file);
            byte[] bytes = new byte[4096];
            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                Rpc.UploadDocumentRequest uploadRequest = Rpc.UploadDocumentRequest.newBuilder()
                        .setChunk(ByteString.copyFrom(bytes, 0, size))
                        .build();
                observer.onNext(uploadRequest);
            }
            inputStream.close();
            observer.onCompleted();
            latch.await();
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка обмена ", e);
        }
    }


}
