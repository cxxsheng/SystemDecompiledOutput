package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class SectionSettingsWithTableInfo extends android.media.tv.tuner.filter.SectionSettings {
    public static final int INVALID_TABLE_INFO_VERSION = -1;
    private final int mTableId;
    private final int mVersion;

    private SectionSettingsWithTableInfo(int i, boolean z, boolean z2, boolean z3, int i2, int i3, int i4) {
        super(i, z, z2, z3, i2);
        this.mTableId = i3;
        this.mVersion = i4;
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public static android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder builder(int i) {
        return new android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder(i);
    }

    public static class Builder extends android.media.tv.tuner.filter.SectionSettings.Builder<android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder> {
        private int mTableId;
        private int mVersion;

        private Builder(int i) {
            super(i);
            this.mVersion = -1;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder setTableId(int i) {
            this.mTableId = i;
            return this;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder setVersion(int i) {
            this.mVersion = i;
            return this;
        }

        public android.media.tv.tuner.filter.SectionSettingsWithTableInfo build() {
            return new android.media.tv.tuner.filter.SectionSettingsWithTableInfo(this.mMainType, this.mCrcEnabled, this.mIsRepeat, this.mIsRaw, this.mBitWidthOfLengthField, this.mTableId, this.mVersion);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.tv.tuner.filter.SectionSettings.Builder
        public android.media.tv.tuner.filter.SectionSettingsWithTableInfo.Builder self() {
            return this;
        }
    }
}
