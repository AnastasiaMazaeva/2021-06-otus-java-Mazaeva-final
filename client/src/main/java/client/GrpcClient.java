package client;

import client.properties.ClientProperties;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class GrpcClient {
    private final DocumentServiceGrpc.DocumentServiceStub serviceStub;
    private final String path;
    private final String token;

    public GrpcClient(ClientProperties clientProperties, String path) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clientProperties.getHost(), clientProperties.getPort())
                .usePlaintext()
                .build();
        this.serviceStub = DocumentServiceGrpc.newStub(channel);
        this.path = path;
        this.token = clientProperties.getToken();
    }

    public void go() throws IOException {
        StreamObserver<Rpc.UploadDocumentRequest> observer = serviceStub.upload(new StreamObserver<>() {
            @Override
            public void onNext(Rpc.UploadDocumentResponse value) {
                System.out.println(
                        "File upload status :: " + value.getStatus()
                );
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
            }
        });

        Path file = Path.of(path);
        Rpc.DocumentInfo documentInfo = Rpc.DocumentInfo.newBuilder().setFilename(file.getFileName().toString()).setToken(token).build();
        observer.onNext(Rpc.UploadDocumentRequest.newBuilder().setInfo(documentInfo).build());

        InputStream inputStream = Files.newInputStream(file);
        byte[] bytes = new byte[4096];
        int size;
        while ((size = inputStream.read(bytes)) > 0){
            Rpc.UploadDocumentRequest uploadRequest = Rpc.UploadDocumentRequest.newBuilder()
                    .setChunk(ByteString.copyFrom(bytes, 0, size))
                    .build();
            observer.onNext(uploadRequest);
        }
        inputStream.close();
        observer.onCompleted();
    }



}
