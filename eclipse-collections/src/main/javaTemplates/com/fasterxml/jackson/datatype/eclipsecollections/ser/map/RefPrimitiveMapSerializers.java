package com.fasterxml.jackson.datatype.eclipsecollections.ser.map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.primitive_collections_base.ser.map.RefPrimitiveMapSerializer;
import org.eclipse.collections.api.map.primitive.*;

import java.io.IOException;

import static com.fasterxml.jackson.datatype.eclipsecollections.ser.map.PrimitiveRefMapSerializers.rethrowUnchecked;

/**
 * @author yawkat
 */
@SuppressWarnings({ "Duplicates", "NewClassNamingConvention" })
public final class RefPrimitiveMapSerializers {
    private RefPrimitiveMapSerializers() {
    }

    /* with char|boolean|byte|short|int|float|long|double value */

    public static final class Char<K> extends RefPrimitiveMapSerializer<ObjectCharMap<K>, K> {
        public Char(JavaType type, BeanProperty property, JsonSerializer<Object> keySerializer) {
            super(type, property, keySerializer);
        }

        @Override
        protected RefPrimitiveMapSerializer<ObjectCharMap<K>, K> withResolved(
                BeanProperty property, JsonSerializer<Object> keySerializer
        ) {
            return new Char<>(_type, property, keySerializer);
        }

        @SuppressWarnings("RedundantThrows") // May throw IOException from inside lambda
        @Override
        protected void serializeEntries(ObjectCharMap<K> value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            value.forEachKeyValue((k, v) -> {
                try {
                    _serializeKey(k, gen, serializers);
                    /* if !(char|boolean value) //
                    gen.writeNumber(v);
                    /* elif char value */
                    gen.writeString(new char[]{v}, 0, 1);
                    /* elif boolean value //
                    gen.writeBoolean(v);
                    // endif */
                } catch (IOException e) {
                    rethrowUnchecked(e);
                }
            });
        }

        @Override
        public boolean isEmpty(SerializerProvider provider, ObjectCharMap<K> value) {
            return value.isEmpty();
        }
    }

    /* endwith */
}
