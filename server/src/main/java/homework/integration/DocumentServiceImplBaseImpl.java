package homework.integration;

import homework.service.interfaces.IntegrationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImplBaseImpl extends DocumentServiceGrpc.DocumentServiceImplBase {

    private final IntegrationService integrationService;


    @Override
    public StreamObserver<Rpc.UploadDocumentRequest> upload(StreamObserver<Rpc.UploadDocumentResponse> responseObserver) {
        return new StreamObserver<>() {
            ByteArrayOutputStream writer;
            Rpc.Status status = Rpc.Status.IN_PROGRESS;
            String filename;
            String token;

            @Override
            public void onNext(Rpc.UploadDocumentRequest value) {
                if (value.hasInfo()) {
                    //new
                    this.filename = value.getInfo().getFilename();
                    this.token = value.getInfo().getToken();
                    writer = new ByteArrayOutputStream();
                } else {
                    // processing
                    try {
                        writer.write(value.getChunk().toByteArray());
                        writer.flush();
                    } catch (IOException e) {
                        onError(e);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                status = Rpc.Status.FAILED;
                this.onCompleted();
            }

            @Override
            public void onCompleted() {
                Rpc.Status statusOnComplete = saveDocument(token, filename, writer.toByteArray(), status);
                Rpc.UploadDocumentResponse response = Rpc.UploadDocumentResponse.newBuilder()
                        .setStatus(statusOnComplete)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    private Rpc.Status saveDocument(String token, String filename, byte[] bytes, Rpc.Status currentStatus) {
        try {
            integrationService.handleIncome(token, filename, bytes);
            return currentStatus == Rpc.Status.IN_PROGRESS ? Rpc.Status.SUCCESS : Rpc.Status.FAILED;
        } catch (Exception e) {
            return Rpc.Status.FAILED;
        }
    }
}
