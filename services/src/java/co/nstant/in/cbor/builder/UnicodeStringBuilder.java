package co.nstant.in.cbor.builder;

/* loaded from: classes.dex */
public class UnicodeStringBuilder<T extends co.nstant.in.cbor.builder.AbstractBuilder<?>> extends co.nstant.in.cbor.builder.AbstractBuilder<T> {
    public UnicodeStringBuilder(T t) {
        super(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public co.nstant.in.cbor.builder.UnicodeStringBuilder<T> add(java.lang.String str) {
        ((co.nstant.in.cbor.builder.AbstractBuilder) getParent()).addChunk(convert(str));
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T end() {
        ((co.nstant.in.cbor.builder.AbstractBuilder) getParent()).addChunk(co.nstant.in.cbor.model.Special.BREAK);
        return (T) getParent();
    }
}
