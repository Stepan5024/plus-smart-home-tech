package ru.practicum.service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.annotation.HandlerContext;
import ru.practicum.exception.NotFoundException;
import ru.practicum.handler.CommonHubEventHandler;
import ru.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@GrpcService
@RequiredArgsConstructor
public class CollectorServiceProto extends CollectorControllerGrpc.CollectorControllerImplBase {
    private final HandlerContext handlerContext;

    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            CommonSensorEventHandler<? extends SpecificRecordBase> handler = handlerContext
                    .getSensorEventHandlers().get(request.getPayloadCase());
            if (handler != null) {
                handler.handle(request);
                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            } else {
                throw new NotFoundException("Handler for sensor event type " +
                                            request.getPayloadCase().name() + " not found");
            }
        } catch (Exception e) {
            responseObserver.onError(
                    new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage()).withCause(e))
            );
        }
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            CommonHubEventHandler<? extends SpecificRecordBase> handler = handlerContext
                    .getHubEventHandlers().get(request.getPayloadCase());
            if (handler != null) {
                handler.handle(request);
                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            } else {
                throw new NotFoundException("Handler for hub event type " +
                                            request.getPayloadCase().name() + " not found");
            }
        } catch (Exception e) {
            responseObserver.onError(
                    new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage()).withCause(e))
            );
        }
    }
}
