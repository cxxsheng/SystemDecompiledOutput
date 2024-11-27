package co.nstant.in.cbor.builder;

/* loaded from: classes.dex */
public class ArrayBuilder<T extends co.nstant.in.cbor.builder.AbstractBuilder<?>> extends co.nstant.in.cbor.builder.AbstractBuilder<T> {
    private final co.nstant.in.cbor.model.Array array;

    public ArrayBuilder(T t, co.nstant.in.cbor.model.Array array) {
        super(t);
        this.array = array;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(co.nstant.in.cbor.model.DataItem dataItem) {
        this.array.add(dataItem);
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(long j) {
        add(convert(j));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(boolean z) {
        add(convert(z));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(float f) {
        add(convert(f));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(double d) {
        add(convert(d));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(byte[] bArr) {
        add(convert(bArr));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<T> add(java.lang.String str) {
        add(convert(str));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.ArrayBuilder<T>> addArray() {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        add(array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.ArrayBuilder<T>> startArray() {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        array.setChunked(true);
        add(array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.ArrayBuilder<T>> addMap() {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        add(map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.ArrayBuilder<T>> startMap() {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        map.setChunked(true);
        add(map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public T end() {
        if (this.array.isChunked()) {
            add(co.nstant.in.cbor.model.Special.BREAK);
        }
        return (T) getParent();
    }
}
