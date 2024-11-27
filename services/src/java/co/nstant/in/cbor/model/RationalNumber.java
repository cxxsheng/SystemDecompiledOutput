package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class RationalNumber extends co.nstant.in.cbor.model.Array {
    public RationalNumber(co.nstant.in.cbor.model.Number number, co.nstant.in.cbor.model.Number number2) throws co.nstant.in.cbor.CborException {
        setTag(30);
        if (number == null) {
            throw new co.nstant.in.cbor.CborException("Numerator is null");
        }
        if (number2 == null) {
            throw new co.nstant.in.cbor.CborException("Denominator is null");
        }
        if (number2.getValue().equals(java.math.BigInteger.ZERO)) {
            throw new co.nstant.in.cbor.CborException("Denominator is zero");
        }
        add(number);
        add(number2);
    }

    public co.nstant.in.cbor.model.Number getNumerator() {
        return (co.nstant.in.cbor.model.Number) getDataItems().get(0);
    }

    public co.nstant.in.cbor.model.Number getDenominator() {
        return (co.nstant.in.cbor.model.Number) getDataItems().get(1);
    }
}
