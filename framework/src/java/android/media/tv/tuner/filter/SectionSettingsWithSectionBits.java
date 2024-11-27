package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class SectionSettingsWithSectionBits extends android.media.tv.tuner.filter.SectionSettings {
    private final byte[] mFilter;
    private final byte[] mMask;
    private final byte[] mMode;

    private SectionSettingsWithSectionBits(int i, boolean z, boolean z2, boolean z3, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        super(i, z, z2, z3, i2);
        this.mFilter = bArr;
        this.mMask = bArr2;
        this.mMode = bArr3;
    }

    public byte[] getFilterBytes() {
        return this.mFilter;
    }

    public byte[] getMask() {
        return this.mMask;
    }

    public byte[] getMode() {
        return this.mMode;
    }

    public static android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder builder(int i) {
        return new android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder(i);
    }

    public static class Builder extends android.media.tv.tuner.filter.SectionSettings.Builder<android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder> {
        private byte[] mFilter;
        private byte[] mMask;
        private byte[] mMode;

        private Builder(int i) {
            super(i);
            this.mFilter = new byte[0];
            this.mMask = new byte[0];
            this.mMode = new byte[0];
        }

        public android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder setFilter(byte[] bArr) {
            this.mFilter = bArr;
            return this;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder setMask(byte[] bArr) {
            this.mMask = bArr;
            return this;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder setMode(byte[] bArr) {
            this.mMode = bArr;
            return this;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithSectionBits build() {
            return new android.media.tv.tuner.filter.SectionSettingsWithSectionBits(this.mMainType, this.mCrcEnabled, this.mIsRepeat, this.mIsRaw, this.mBitWidthOfLengthField, this.mFilter, this.mMask, this.mMode);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.tv.tuner.filter.SectionSettings.Builder
        public android.media.tv.tuner.filter.SectionSettingsWithSectionBits.Builder self() {
            return this;
        }
    }
}
