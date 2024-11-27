package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class IptvFrontendSettingsFec {
    public static final int FEC_TYPE_COLUMN = 1;
    public static final int FEC_TYPE_COLUMN_ROW = 4;
    public static final int FEC_TYPE_ROW = 2;
    public static final int FEC_TYPE_UNDEFINED = 0;
    private final int mFecColNum;
    private final int mFecRowNum;
    private final int mFecType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FecType {
    }

    private IptvFrontendSettingsFec(int i, int i2, int i3) {
        this.mFecType = i;
        this.mFecRowNum = i2;
        this.mFecColNum = i3;
    }

    public int getFecType() {
        return this.mFecType;
    }

    public int getFecRowNum() {
        return this.mFecRowNum;
    }

    public int getFecColNum() {
        return this.mFecColNum;
    }

    public static final class Builder {
        private int mFecColNum;
        private int mFecRowNum;
        private int mFecType;

        public android.media.tv.tuner.frontend.IptvFrontendSettingsFec.Builder setFecType(int i) {
            this.mFecType = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettingsFec.Builder setFecRowNum(int i) {
            this.mFecRowNum = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettingsFec.Builder setFecColNum(int i) {
            this.mFecColNum = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettingsFec build() {
            return new android.media.tv.tuner.frontend.IptvFrontendSettingsFec(this.mFecType, this.mFecRowNum, this.mFecColNum);
        }
    }
}
