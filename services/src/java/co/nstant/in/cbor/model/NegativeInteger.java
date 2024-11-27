package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class NegativeInteger extends co.nstant.in.cbor.model.Number {
    public NegativeInteger(long j) {
        this(java.math.BigInteger.valueOf(j));
        assertTrue(j < 0, "value " + j + " is not < 0");
    }

    public NegativeInteger(java.math.BigInteger bigInteger) {
        super(co.nstant.in.cbor.model.MajorType.NEGATIVE_INTEGER, bigInteger);
    }
}
