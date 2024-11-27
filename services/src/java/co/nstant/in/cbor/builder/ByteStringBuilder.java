package co.nstant.in.cbor.builder;

/* loaded from: classes.dex */
public class ByteStringBuilder<T extends co.nstant.in.cbor.builder.AbstractBuilder<?>> extends co.nstant.in.cbor.builder.AbstractBuilder<T> {
    public ByteStringBuilder(T t) {
        super(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public co.nstant.in.cbor.builder.ByteStringBuilder<T> add(byte[] bArr) {
        ((co.nstant.in.cbor.builder.AbstractBuilder) getParent()).addChunk(convert(bArr));
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T end() {
        ((co.nstant.in.cbor.builder.AbstractBuilder) getParent()).addChunk(co.nstant.in.cbor.model.Special.BREAK);
        return (T) getParent();
    }
}
