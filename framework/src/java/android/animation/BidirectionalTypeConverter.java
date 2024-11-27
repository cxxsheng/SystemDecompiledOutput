package android.animation;

/* loaded from: classes.dex */
public abstract class BidirectionalTypeConverter<T, V> extends android.animation.TypeConverter<T, V> {
    private android.animation.BidirectionalTypeConverter mInvertedConverter;

    public abstract T convertBack(V v);

    public BidirectionalTypeConverter(java.lang.Class<T> cls, java.lang.Class<V> cls2) {
        super(cls, cls2);
    }

    public android.animation.BidirectionalTypeConverter<V, T> invert() {
        if (this.mInvertedConverter == null) {
            this.mInvertedConverter = new android.animation.BidirectionalTypeConverter.InvertedConverter(this);
        }
        return this.mInvertedConverter;
    }

    private static class InvertedConverter<From, To> extends android.animation.BidirectionalTypeConverter<From, To> {
        private android.animation.BidirectionalTypeConverter<To, From> mConverter;

        public InvertedConverter(android.animation.BidirectionalTypeConverter<To, From> bidirectionalTypeConverter) {
            super(bidirectionalTypeConverter.getTargetType(), bidirectionalTypeConverter.getSourceType());
            this.mConverter = bidirectionalTypeConverter;
        }

        @Override // android.animation.BidirectionalTypeConverter
        public From convertBack(To to) {
            return this.mConverter.convert(to);
        }

        @Override // android.animation.TypeConverter
        public To convert(From from) {
            return this.mConverter.convertBack(from);
        }
    }
}
