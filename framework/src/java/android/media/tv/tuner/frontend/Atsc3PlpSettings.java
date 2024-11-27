package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Atsc3PlpSettings {
    private final int mCodeRate;
    private final int mFec;
    private final int mInterleaveMode;
    private final int mModulation;
    private final int mPlpId;

    private Atsc3PlpSettings(int i, int i2, int i3, int i4, int i5) {
        this.mPlpId = i;
        this.mModulation = i2;
        this.mInterleaveMode = i3;
        this.mCodeRate = i4;
        this.mFec = i5;
    }

    public int getPlpId() {
        return this.mPlpId;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public int getInterleaveMode() {
        return this.mInterleaveMode;
    }

    public int getCodeRate() {
        return this.mCodeRate;
    }

    public int getFec() {
        return this.mFec;
    }

    public static android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder();
    }

    public static class Builder {
        private int mCodeRate;
        private int mFec;
        private int mInterleaveMode;
        private int mModulation;
        private int mPlpId;

        private Builder() {
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder setPlpId(int i) {
            this.mPlpId = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder setInterleaveMode(int i) {
            this.mInterleaveMode = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder setCodeRate(int i) {
            this.mCodeRate = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings.Builder setFec(int i) {
            this.mFec = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3PlpSettings build() {
            return new android.media.tv.tuner.frontend.Atsc3PlpSettings(this.mPlpId, this.mModulation, this.mInterleaveMode, this.mCodeRate, this.mFec);
        }
    }
}
