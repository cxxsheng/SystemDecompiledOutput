package android.hardware.hdmi;

/* loaded from: classes2.dex */
public class DeviceFeatures {
    public static final android.hardware.hdmi.DeviceFeatures ALL_FEATURES_SUPPORT_UNKNOWN;
    public static final int FEATURE_NOT_SUPPORTED = 0;
    public static final int FEATURE_SUPPORTED = 1;
    public static final int FEATURE_SUPPORT_UNKNOWN = 2;
    public static final android.hardware.hdmi.DeviceFeatures NO_FEATURES_SUPPORTED;
    private final int mArcRxSupport;
    private final int mArcTxSupport;
    private final int mDeckControlSupport;
    private final int mRecordTvScreenSupport;
    private final int mSetAudioRateSupport;
    private final int mSetAudioVolumeLevelSupport;
    private final int mSetOsdStringSupport;

    public @interface FeatureSupportStatus {
    }

    static {
        ALL_FEATURES_SUPPORT_UNKNOWN = new android.hardware.hdmi.DeviceFeatures.Builder(2).build();
        NO_FEATURES_SUPPORTED = new android.hardware.hdmi.DeviceFeatures.Builder(0).build();
    }

    private DeviceFeatures(android.hardware.hdmi.DeviceFeatures.Builder builder) {
        this.mRecordTvScreenSupport = builder.mRecordTvScreenSupport;
        this.mSetOsdStringSupport = builder.mOsdStringSupport;
        this.mDeckControlSupport = builder.mDeckControlSupport;
        this.mSetAudioRateSupport = builder.mSetAudioRateSupport;
        this.mArcTxSupport = builder.mArcTxSupport;
        this.mArcRxSupport = builder.mArcRxSupport;
        this.mSetAudioVolumeLevelSupport = builder.mSetAudioVolumeLevelSupport;
    }

    public android.hardware.hdmi.DeviceFeatures.Builder toBuilder() {
        return new android.hardware.hdmi.DeviceFeatures.Builder();
    }

    public static android.hardware.hdmi.DeviceFeatures fromOperand(byte[] bArr) {
        android.hardware.hdmi.DeviceFeatures.Builder builder = new android.hardware.hdmi.DeviceFeatures.Builder(2);
        if (bArr.length >= 1) {
            byte b = bArr[0];
            builder.setRecordTvScreenSupport(bitToFeatureSupportStatus(((b >> 6) & 1) == 1)).setSetOsdStringSupport(bitToFeatureSupportStatus(((b >> 5) & 1) == 1)).setDeckControlSupport(bitToFeatureSupportStatus(((b >> 4) & 1) == 1)).setSetAudioRateSupport(bitToFeatureSupportStatus(((b >> 3) & 1) == 1)).setArcTxSupport(bitToFeatureSupportStatus(((b >> 2) & 1) == 1)).setArcRxSupport(bitToFeatureSupportStatus(((b >> 1) & 1) == 1)).setSetAudioVolumeLevelSupport(bitToFeatureSupportStatus((b & 1) == 1));
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int updateFeatureSupportStatus(int i, int i2) {
        if (i2 == 2) {
            return i;
        }
        return i2;
    }

    public byte[] toOperand() {
        byte b = this.mRecordTvScreenSupport == 1 ? (byte) 64 : (byte) 0;
        if (this.mSetOsdStringSupport == 1) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        if (this.mDeckControlSupport == 1) {
            b = (byte) (b | 16);
        }
        if (this.mSetAudioRateSupport == 1) {
            b = (byte) (b | 8);
        }
        if (this.mArcTxSupport == 1) {
            b = (byte) (b | 4);
        }
        if (this.mArcRxSupport == 1) {
            b = (byte) (b | 2);
        }
        if (this.mSetAudioVolumeLevelSupport == 1) {
            b = (byte) (b | 1);
        }
        return new byte[]{b};
    }

    private static int bitToFeatureSupportStatus(boolean z) {
        return z ? 1 : 0;
    }

    public int getRecordTvScreenSupport() {
        return this.mRecordTvScreenSupport;
    }

    public int getSetOsdStringSupport() {
        return this.mSetOsdStringSupport;
    }

    public int getDeckControlSupport() {
        return this.mDeckControlSupport;
    }

    public int getSetAudioRateSupport() {
        return this.mSetAudioRateSupport;
    }

    public int getArcTxSupport() {
        return this.mArcTxSupport;
    }

    public int getArcRxSupport() {
        return this.mArcRxSupport;
    }

    public int getSetAudioVolumeLevelSupport() {
        return this.mSetAudioVolumeLevelSupport;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.hdmi.DeviceFeatures)) {
            return false;
        }
        android.hardware.hdmi.DeviceFeatures deviceFeatures = (android.hardware.hdmi.DeviceFeatures) obj;
        return this.mRecordTvScreenSupport == deviceFeatures.mRecordTvScreenSupport && this.mSetOsdStringSupport == deviceFeatures.mSetOsdStringSupport && this.mDeckControlSupport == deviceFeatures.mDeckControlSupport && this.mSetAudioRateSupport == deviceFeatures.mSetAudioRateSupport && this.mArcTxSupport == deviceFeatures.mArcTxSupport && this.mArcRxSupport == deviceFeatures.mArcRxSupport && this.mSetAudioVolumeLevelSupport == deviceFeatures.mSetAudioVolumeLevelSupport;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRecordTvScreenSupport), java.lang.Integer.valueOf(this.mSetOsdStringSupport), java.lang.Integer.valueOf(this.mDeckControlSupport), java.lang.Integer.valueOf(this.mSetAudioRateSupport), java.lang.Integer.valueOf(this.mArcTxSupport), java.lang.Integer.valueOf(this.mArcRxSupport), java.lang.Integer.valueOf(this.mSetAudioVolumeLevelSupport));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Device features: ");
        sb.append("record_tv_screen: ").append(featureSupportStatusToString(this.mRecordTvScreenSupport)).append(" ");
        sb.append("set_osd_string: ").append(featureSupportStatusToString(this.mSetOsdStringSupport)).append(" ");
        sb.append("deck_control: ").append(featureSupportStatusToString(this.mDeckControlSupport)).append(" ");
        sb.append("set_audio_rate: ").append(featureSupportStatusToString(this.mSetAudioRateSupport)).append(" ");
        sb.append("arc_tx: ").append(featureSupportStatusToString(this.mArcTxSupport)).append(" ");
        sb.append("arc_rx: ").append(featureSupportStatusToString(this.mArcRxSupport)).append(" ");
        sb.append("set_audio_volume_level: ").append(featureSupportStatusToString(this.mSetAudioVolumeLevelSupport)).append(" ");
        return sb.toString();
    }

    private static java.lang.String featureSupportStatusToString(int i) {
        switch (i) {
            case 0:
                return android.hardware.gnss.GnssSignalType.CODE_TYPE_N;
            case 1:
                return android.hardware.gnss.GnssSignalType.CODE_TYPE_Y;
            default:
                return "?";
        }
    }

    public static final class Builder {
        private int mArcRxSupport;
        private int mArcTxSupport;
        private int mDeckControlSupport;
        private int mOsdStringSupport;
        private int mRecordTvScreenSupport;
        private int mSetAudioRateSupport;
        private int mSetAudioVolumeLevelSupport;

        private Builder(int i) {
            this.mRecordTvScreenSupport = i;
            this.mOsdStringSupport = i;
            this.mDeckControlSupport = i;
            this.mSetAudioRateSupport = i;
            this.mArcTxSupport = i;
            this.mArcRxSupport = i;
            this.mSetAudioVolumeLevelSupport = i;
        }

        private Builder(android.hardware.hdmi.DeviceFeatures deviceFeatures) {
            this.mRecordTvScreenSupport = deviceFeatures.getRecordTvScreenSupport();
            this.mOsdStringSupport = deviceFeatures.getSetOsdStringSupport();
            this.mDeckControlSupport = deviceFeatures.getDeckControlSupport();
            this.mSetAudioRateSupport = deviceFeatures.getSetAudioRateSupport();
            this.mArcTxSupport = deviceFeatures.getArcTxSupport();
            this.mArcRxSupport = deviceFeatures.getArcRxSupport();
            this.mSetAudioVolumeLevelSupport = deviceFeatures.getSetAudioVolumeLevelSupport();
        }

        public android.hardware.hdmi.DeviceFeatures build() {
            return new android.hardware.hdmi.DeviceFeatures(this);
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setRecordTvScreenSupport(int i) {
            this.mRecordTvScreenSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setSetOsdStringSupport(int i) {
            this.mOsdStringSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setDeckControlSupport(int i) {
            this.mDeckControlSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setSetAudioRateSupport(int i) {
            this.mSetAudioRateSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setArcTxSupport(int i) {
            this.mArcTxSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setArcRxSupport(int i) {
            this.mArcRxSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder setSetAudioVolumeLevelSupport(int i) {
            this.mSetAudioVolumeLevelSupport = i;
            return this;
        }

        public android.hardware.hdmi.DeviceFeatures.Builder update(android.hardware.hdmi.DeviceFeatures deviceFeatures) {
            this.mRecordTvScreenSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mRecordTvScreenSupport, deviceFeatures.getRecordTvScreenSupport());
            this.mOsdStringSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mOsdStringSupport, deviceFeatures.getSetOsdStringSupport());
            this.mDeckControlSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mDeckControlSupport, deviceFeatures.getDeckControlSupport());
            this.mSetAudioRateSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mSetAudioRateSupport, deviceFeatures.getSetAudioRateSupport());
            this.mArcTxSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mArcTxSupport, deviceFeatures.getArcTxSupport());
            this.mArcRxSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mArcRxSupport, deviceFeatures.getArcRxSupport());
            this.mSetAudioVolumeLevelSupport = android.hardware.hdmi.DeviceFeatures.updateFeatureSupportStatus(this.mSetAudioVolumeLevelSupport, deviceFeatures.getSetAudioVolumeLevelSupport());
            return this;
        }
    }
}
