package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class HalfPrecisionFloatEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.HalfPrecisionFloat> {
    public HalfPrecisionFloatEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.HalfPrecisionFloat halfPrecisionFloat) throws co.nstant.in.cbor.CborException {
        write(249);
        int fromFloat = fromFloat(halfPrecisionFloat.getValue());
        write((fromFloat >> 8) & 255);
        write((fromFloat >> 0) & 255);
    }

    public static int fromFloat(float f) {
        int floatToIntBits = java.lang.Float.floatToIntBits(f);
        int i = (floatToIntBits >>> 16) & 32768;
        int i2 = (floatToIntBits + 4096) & Integer.MAX_VALUE;
        if (i2 >= 1199570944) {
            if ((Integer.MAX_VALUE & floatToIntBits) >= 1199570944) {
                if (i2 >= 2139095040) {
                    return ((floatToIntBits & 8388607) >>> 13) | i | 31744;
                }
                return i | 31744;
            }
            return i | 31743;
        }
        if (i2 >= 947912704) {
            return ((i2 - 939524096) >>> 13) | i;
        }
        if (i2 < 855638016) {
            return i;
        }
        int i3 = (floatToIntBits & Integer.MAX_VALUE) >>> 23;
        return ((((floatToIntBits & 8388607) | 8388608) + (8388608 >>> (i3 + com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_ID_UNKNOWN))) >>> (126 - i3)) | i;
    }
}
