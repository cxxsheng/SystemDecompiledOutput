package co.nstant.in.cbor.builder;

/* loaded from: classes.dex */
public class MapBuilder<T extends co.nstant.in.cbor.builder.AbstractBuilder<?>> extends co.nstant.in.cbor.builder.AbstractBuilder<T> {
    private final co.nstant.in.cbor.model.Map map;

    public MapBuilder(T t, co.nstant.in.cbor.model.Map map) {
        super(t);
        this.map = map;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(co.nstant.in.cbor.model.DataItem dataItem, co.nstant.in.cbor.model.DataItem dataItem2) {
        this.map.put(dataItem, dataItem2);
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, long j2) {
        put(convert(j), convert(j2));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, boolean z) {
        put(convert(j), convert(z));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, float f) {
        put(convert(j), convert(f));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, double d) {
        put(convert(j), convert(d));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, byte[] bArr) {
        put(convert(j), convert(bArr));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(long j, java.lang.String str) {
        put(convert(j), convert(str));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, long j) {
        put(convert(str), convert(j));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, boolean z) {
        put(convert(str), convert(z));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, float f) {
        put(convert(str), convert(f));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, double d) {
        put(convert(str), convert(d));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, byte[] bArr) {
        this.map.put(convert(str), convert(bArr));
        return this;
    }

    public co.nstant.in.cbor.builder.MapBuilder<T> put(java.lang.String str, java.lang.String str2) {
        put(convert(str), convert(str2));
        return this;
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putArray(co.nstant.in.cbor.model.DataItem dataItem) {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        put(dataItem, array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putArray(long j) {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        put(convert(j), array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putArray(java.lang.String str) {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        put(convert(str), array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startArray(co.nstant.in.cbor.model.DataItem dataItem) {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        array.setChunked(true);
        put(dataItem, array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startArray(long j) {
        return startArray(convert(j));
    }

    public co.nstant.in.cbor.builder.ArrayBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startArray(java.lang.String str) {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        array.setChunked(true);
        put(convert(str), array);
        return new co.nstant.in.cbor.builder.ArrayBuilder<>(this, array);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putMap(co.nstant.in.cbor.model.DataItem dataItem) {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        put(dataItem, map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putMap(long j) {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        put(convert(j), map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> putMap(java.lang.String str) {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        put(convert(str), map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startMap(co.nstant.in.cbor.model.DataItem dataItem) {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        map.setChunked(true);
        put(dataItem, map);
        return new co.nstant.in.cbor.builder.MapBuilder<>(this, map);
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startMap(long j) {
        return startMap(convert(j));
    }

    public co.nstant.in.cbor.builder.MapBuilder<co.nstant.in.cbor.builder.MapBuilder<T>> startMap(java.lang.String str) {
        return startMap(convert(str));
    }

    public T end() {
        return (T) getParent();
    }
}
