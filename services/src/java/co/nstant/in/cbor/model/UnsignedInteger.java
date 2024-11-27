package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class UnsignedInteger extends co.nstant.in.cbor.model.Number {
    public UnsignedInteger(long j) {
        this(java.math.BigInteger.valueOf(j));
        assertTrue(j >= 0, "value " + j + " is not >= 0");
    }

    public UnsignedInteger(java.math.BigInteger bigInteger) {
        super(co.nstant.in.cbor.model.MajorType.UNSIGNED_INTEGER, bigInteger);
    }
}
