package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class SectionSettings extends android.media.tv.tuner.filter.Settings {
    final int mBitWidthOfLengthField;
    final boolean mCrcEnabled;
    final boolean mIsRaw;
    final boolean mIsRepeat;

    SectionSettings(int i, boolean z, boolean z2, boolean z3, int i2) {
        super(android.media.tv.tuner.TunerUtils.getFilterSubtype(i, 1));
        this.mCrcEnabled = z;
        this.mIsRepeat = z2;
        this.mIsRaw = z3;
        this.mBitWidthOfLengthField = i2;
    }

    public boolean isCrcEnabled() {
        return this.mCrcEnabled;
    }

    public boolean isRepeat() {
        return this.mIsRepeat;
    }

    public boolean isRaw() {
        return this.mIsRaw;
    }

    public int getLengthFieldBitWidth() {
        return this.mBitWidthOfLengthField;
    }

    public static abstract class Builder<T extends android.media.tv.tuner.filter.SectionSettings.Builder<T>> {
        int mBitWidthOfLengthField;
        boolean mCrcEnabled;
        boolean mIsRaw;
        boolean mIsRepeat;
        final int mMainType;

        abstract T self();

        Builder(int i) {
            this.mMainType = i;
        }

        public T setCrcEnabled(boolean z) {
            this.mCrcEnabled = z;
            return self();
        }

        public T setRepeat(boolean z) {
            this.mIsRepeat = z;
            return self();
        }

        public T setRaw(boolean z) {
            this.mIsRaw = z;
            return self();
        }

        public T setBitWidthOfLengthField(int i) {
            this.mBitWidthOfLengthField = i;
            return self();
        }
    }
}
