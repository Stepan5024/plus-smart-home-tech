package ru.yandex.practicum.utils;

import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

public class CommonAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private final DecoderFactory decoderFactory;
    private final DatumReader<T> datumReader;

    public CommonAvroDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    public CommonAvroDeserializer(DecoderFactory decoderFactory, Schema schema) {
        this.decoderFactory = decoderFactory;
        datumReader = new SpecificDatumReader<>(schema);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data != null) {
                BinaryDecoder binaryDecoder = decoderFactory.binaryDecoder(data, null);
                return datumReader.read(null, binaryDecoder);
            } else return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }

    }
}
