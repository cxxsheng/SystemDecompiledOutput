package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class FrontendStatus {
    public static final int FRONTEND_STATUS_TYPE_AGC = 14;
    public static final int FRONTEND_STATUS_TYPE_ATSC3_ALL_PLP_INFO = 41;
    public static final int FRONTEND_STATUS_TYPE_ATSC3_PLP_INFO = 21;
    public static final int FRONTEND_STATUS_TYPE_BANDWIDTH = 25;
    public static final int FRONTEND_STATUS_TYPE_BER = 2;
    public static final int FRONTEND_STATUS_TYPE_BERS = 23;
    public static final int FRONTEND_STATUS_TYPE_CODERATES = 24;
    public static final int FRONTEND_STATUS_TYPE_DEMOD_LOCK = 0;
    public static final int FRONTEND_STATUS_TYPE_DVBT_CELL_IDS = 40;
    public static final int FRONTEND_STATUS_TYPE_EWBS = 13;
    public static final int FRONTEND_STATUS_TYPE_FEC = 8;
    public static final int FRONTEND_STATUS_TYPE_FREQ_OFFSET = 18;
    public static final int FRONTEND_STATUS_TYPE_GUARD_INTERVAL = 26;
    public static final int FRONTEND_STATUS_TYPE_HIERARCHY = 19;
    public static final int FRONTEND_STATUS_TYPE_INTERLEAVINGS = 30;
    public static final int FRONTEND_STATUS_TYPE_IPTV_AVERAGE_JITTER_MS = 46;
    public static final int FRONTEND_STATUS_TYPE_IPTV_CONTENT_URL = 42;
    public static final int FRONTEND_STATUS_TYPE_IPTV_PACKETS_LOST = 43;
    public static final int FRONTEND_STATUS_TYPE_IPTV_PACKETS_RECEIVED = 44;
    public static final int FRONTEND_STATUS_TYPE_IPTV_WORST_JITTER_MS = 45;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_MODE = 37;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_PARTIAL_RECEPTION_FLAG = 38;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_SEGMENTS = 31;
    public static final int FRONTEND_STATUS_TYPE_IS_LINEAR = 35;
    public static final int FRONTEND_STATUS_TYPE_IS_MISO_ENABLED = 34;
    public static final int FRONTEND_STATUS_TYPE_IS_SHORT_FRAMES_ENABLED = 36;
    public static final int FRONTEND_STATUS_TYPE_LAYER_ERROR = 16;
    public static final int FRONTEND_STATUS_TYPE_LNA = 15;
    public static final int FRONTEND_STATUS_TYPE_LNB_VOLTAGE = 11;
    public static final int FRONTEND_STATUS_TYPE_MER = 17;
    public static final int FRONTEND_STATUS_TYPE_MODULATION = 9;
    public static final int FRONTEND_STATUS_TYPE_MODULATIONS_EXT = 22;
    public static final int FRONTEND_STATUS_TYPE_PER = 3;
    public static final int FRONTEND_STATUS_TYPE_PLP_ID = 12;
    public static final int FRONTEND_STATUS_TYPE_PRE_BER = 4;
    public static final int FRONTEND_STATUS_TYPE_RF_LOCK = 20;
    public static final int FRONTEND_STATUS_TYPE_ROLL_OFF = 33;
    public static final int FRONTEND_STATUS_TYPE_SIGNAL_QUALITY = 5;
    public static final int FRONTEND_STATUS_TYPE_SIGNAL_STRENGTH = 6;
    public static final int FRONTEND_STATUS_TYPE_SNR = 1;
    public static final int FRONTEND_STATUS_TYPE_SPECTRAL = 10;
    public static final int FRONTEND_STATUS_TYPE_STREAM_IDS = 39;
    public static final int FRONTEND_STATUS_TYPE_SYMBOL_RATE = 7;
    public static final int FRONTEND_STATUS_TYPE_T2_SYSTEM_ID = 29;
    public static final int FRONTEND_STATUS_TYPE_TRANSMISSION_MODE = 27;
    public static final int FRONTEND_STATUS_TYPE_TS_DATA_RATES = 32;
    public static final int FRONTEND_STATUS_TYPE_UEC = 28;
    private java.lang.Integer mAgc;
    private android.media.tv.tuner.frontend.Atsc3PlpInfo[] mAllPlpInfo;
    private java.lang.Integer mBandwidth;
    private java.lang.Integer mBer;
    private int[] mBers;
    private int[] mCodeRates;
    private int[] mDvbtCellIds;
    private java.lang.Long mFreqOffset;
    private java.lang.Integer mGuardInterval;
    private java.lang.Integer mHierarchy;
    private java.lang.Long mInnerFec;
    private int[] mInterleaving;
    private java.lang.Integer mInversion;
    private java.lang.Integer mIptvAverageJitterMs;
    private java.lang.String mIptvContentUrl;
    private java.lang.Long mIptvPacketsLost;
    private java.lang.Long mIptvPacketsReceived;
    private java.lang.Integer mIptvWorstJitterMs;
    private java.lang.Boolean mIsDemodLocked;
    private java.lang.Boolean mIsEwbs;
    private boolean[] mIsLayerErrors;
    private java.lang.Boolean mIsLinear;
    private java.lang.Boolean mIsLnaOn;
    private java.lang.Boolean mIsMisoEnabled;
    private java.lang.Boolean mIsRfLocked;
    private java.lang.Boolean mIsShortFrames;
    private java.lang.Integer mIsdbtMode;
    private java.lang.Integer mIsdbtPartialReceptionFlag;
    private int[] mIsdbtSegment;
    private java.lang.Integer mLnbVoltage;
    private java.lang.Integer mMer;
    private java.lang.Integer mModulation;
    private int[] mModulationsExt;
    private java.lang.Integer mPer;
    private java.lang.Integer mPerBer;
    private java.lang.Integer mPlpId;
    private android.media.tv.tuner.frontend.FrontendStatus.Atsc3PlpTuningInfo[] mPlpInfo;
    private java.lang.Integer mRollOff;
    private java.lang.Integer mSignalQuality;
    private java.lang.Integer mSignalStrength;
    private java.lang.Integer mSnr;
    private int[] mStreamIds;
    private java.lang.Integer mSymbolRate;
    private java.lang.Integer mSystemId;
    private java.lang.Integer mTransmissionMode;
    private int[] mTsDataRate;
    private java.lang.Integer mUec;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendBandwidth {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendGuardInterval {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendInterleaveMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendModulation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendRollOff {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendStatusType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrontendTransmissionMode {
    }

    private FrontendStatus() {
    }

    public boolean isDemodLocked() {
        if (this.mIsDemodLocked == null) {
            throw new java.lang.IllegalStateException("DemodLocked status is empty");
        }
        return this.mIsDemodLocked.booleanValue();
    }

    public int getSnr() {
        if (this.mSnr == null) {
            throw new java.lang.IllegalStateException("Snr status is empty");
        }
        return this.mSnr.intValue();
    }

    public int getBer() {
        if (this.mBer == null) {
            throw new java.lang.IllegalStateException("Ber status is empty");
        }
        return this.mBer.intValue();
    }

    public int getPer() {
        if (this.mPer == null) {
            throw new java.lang.IllegalStateException("Per status is empty");
        }
        return this.mPer.intValue();
    }

    public int getPerBer() {
        if (this.mPerBer == null) {
            throw new java.lang.IllegalStateException("PerBer status is empty");
        }
        return this.mPerBer.intValue();
    }

    public int getSignalQuality() {
        if (this.mSignalQuality == null) {
            throw new java.lang.IllegalStateException("SignalQuality status is empty");
        }
        return this.mSignalQuality.intValue();
    }

    public int getSignalStrength() {
        if (this.mSignalStrength == null) {
            throw new java.lang.IllegalStateException("SignalStrength status is empty");
        }
        return this.mSignalStrength.intValue();
    }

    public int getSymbolRate() {
        if (this.mSymbolRate == null) {
            throw new java.lang.IllegalStateException("SymbolRate status is empty");
        }
        return this.mSymbolRate.intValue();
    }

    public long getInnerFec() {
        if (this.mInnerFec == null) {
            throw new java.lang.IllegalStateException("InnerFec status is empty");
        }
        return this.mInnerFec.longValue();
    }

    public int getModulation() {
        if (this.mModulation == null) {
            throw new java.lang.IllegalStateException("Modulation status is empty");
        }
        return this.mModulation.intValue();
    }

    public int getSpectralInversion() {
        if (this.mInversion == null) {
            throw new java.lang.IllegalStateException("SpectralInversion status is empty");
        }
        return this.mInversion.intValue();
    }

    public int getLnbVoltage() {
        if (this.mLnbVoltage == null) {
            throw new java.lang.IllegalStateException("LnbVoltage status is empty");
        }
        return this.mLnbVoltage.intValue();
    }

    public int getPlpId() {
        if (this.mPlpId == null) {
            throw new java.lang.IllegalStateException("PlpId status is empty");
        }
        return this.mPlpId.intValue();
    }

    public boolean isEwbs() {
        if (this.mIsEwbs == null) {
            throw new java.lang.IllegalStateException("Ewbs status is empty");
        }
        return this.mIsEwbs.booleanValue();
    }

    public int getAgc() {
        if (this.mAgc == null) {
            throw new java.lang.IllegalStateException("Agc status is empty");
        }
        return this.mAgc.intValue();
    }

    public boolean isLnaOn() {
        if (this.mIsLnaOn == null) {
            throw new java.lang.IllegalStateException("LnaOn status is empty");
        }
        return this.mIsLnaOn.booleanValue();
    }

    public boolean[] getLayerErrors() {
        if (this.mIsLayerErrors == null) {
            throw new java.lang.IllegalStateException("LayerErrors status is empty");
        }
        return this.mIsLayerErrors;
    }

    public int getMer() {
        if (this.mMer == null) {
            throw new java.lang.IllegalStateException("Mer status is empty");
        }
        return this.mMer.intValue();
    }

    @java.lang.Deprecated
    public int getFreqOffset() {
        return (int) getFreqOffsetLong();
    }

    public long getFreqOffsetLong() {
        if (this.mFreqOffset == null) {
            throw new java.lang.IllegalStateException("FreqOffset status is empty");
        }
        return this.mFreqOffset.longValue();
    }

    public int getHierarchy() {
        if (this.mHierarchy == null) {
            throw new java.lang.IllegalStateException("Hierarchy status is empty");
        }
        return this.mHierarchy.intValue();
    }

    public boolean isRfLocked() {
        if (this.mIsRfLocked == null) {
            throw new java.lang.IllegalStateException("isRfLocked status is empty");
        }
        return this.mIsRfLocked.booleanValue();
    }

    public android.media.tv.tuner.frontend.FrontendStatus.Atsc3PlpTuningInfo[] getAtsc3PlpTuningInfo() {
        if (this.mPlpInfo == null) {
            throw new java.lang.IllegalStateException("Atsc3PlpTuningInfo status is empty");
        }
        return this.mPlpInfo;
    }

    public int[] getBers() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getBers status");
        if (this.mBers == null) {
            throw new java.lang.IllegalStateException("Bers status is empty");
        }
        return this.mBers;
    }

    public int[] getCodeRates() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getCodeRates status");
        if (this.mCodeRates == null) {
            throw new java.lang.IllegalStateException("CodeRates status is empty");
        }
        return this.mCodeRates;
    }

    public int getBandwidth() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getBandwidth status");
        if (this.mBandwidth == null) {
            throw new java.lang.IllegalStateException("Bandwidth status is empty");
        }
        return this.mBandwidth.intValue();
    }

    public int getGuardInterval() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getGuardInterval status");
        if (this.mGuardInterval == null) {
            throw new java.lang.IllegalStateException("GuardInterval status is empty");
        }
        return this.mGuardInterval.intValue();
    }

    public int getTransmissionMode() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getTransmissionMode status");
        if (this.mTransmissionMode == null) {
            throw new java.lang.IllegalStateException("TransmissionMode status is empty");
        }
        return this.mTransmissionMode.intValue();
    }

    public int getUec() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getUec status");
        if (this.mUec == null) {
            throw new java.lang.IllegalStateException("Uec status is empty");
        }
        return this.mUec.intValue();
    }

    public int getSystemId() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getSystemId status");
        if (this.mSystemId == null) {
            throw new java.lang.IllegalStateException("SystemId status is empty");
        }
        return this.mSystemId.intValue();
    }

    public int[] getInterleaving() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getInterleaving status");
        if (this.mInterleaving == null) {
            throw new java.lang.IllegalStateException("Interleaving status is empty");
        }
        return this.mInterleaving;
    }

    public int[] getIsdbtSegment() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getIsdbtSegment status");
        if (this.mIsdbtSegment == null) {
            throw new java.lang.IllegalStateException("IsdbtSegment status is empty");
        }
        return this.mIsdbtSegment;
    }

    public int[] getTsDataRate() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getTsDataRate status");
        if (this.mTsDataRate == null) {
            throw new java.lang.IllegalStateException("TsDataRate status is empty");
        }
        return this.mTsDataRate;
    }

    public int[] getExtendedModulations() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getExtendedModulations status");
        if (this.mModulationsExt == null) {
            throw new java.lang.IllegalStateException("ExtendedModulations status is empty");
        }
        return this.mModulationsExt;
    }

    public int getRollOff() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getRollOff status");
        if (this.mRollOff == null) {
            throw new java.lang.IllegalStateException("RollOff status is empty");
        }
        return this.mRollOff.intValue();
    }

    public boolean isMisoEnabled() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isMisoEnabled status");
        if (this.mIsMisoEnabled == null) {
            throw new java.lang.IllegalStateException("isMisoEnabled status is empty");
        }
        return this.mIsMisoEnabled.booleanValue();
    }

    public boolean isLinear() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isLinear status");
        if (this.mIsLinear == null) {
            throw new java.lang.IllegalStateException("isLinear status is empty");
        }
        return this.mIsLinear.booleanValue();
    }

    public boolean isShortFramesEnabled() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isShortFramesEnabled status");
        if (this.mIsShortFrames == null) {
            throw new java.lang.IllegalStateException("isShortFramesEnabled status is empty");
        }
        return this.mIsShortFrames.booleanValue();
    }

    public int getIsdbtMode() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "IsdbtMode status");
        if (this.mIsdbtMode == null) {
            throw new java.lang.IllegalStateException("IsdbtMode status is empty");
        }
        return this.mIsdbtMode.intValue();
    }

    public int getIsdbtPartialReceptionFlag() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "IsdbtPartialReceptionFlag status");
        if (this.mIsdbtPartialReceptionFlag == null) {
            throw new java.lang.IllegalStateException("IsdbtPartialReceptionFlag status is empty");
        }
        return this.mIsdbtPartialReceptionFlag.intValue();
    }

    public int[] getStreamIds() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "stream ids status");
        if (this.mStreamIds == null) {
            throw new java.lang.IllegalStateException("stream ids are empty");
        }
        return this.mStreamIds;
    }

    public int[] getDvbtCellIds() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "dvbt cell ids status");
        if (this.mDvbtCellIds == null) {
            throw new java.lang.IllegalStateException("dvbt cell ids are empty");
        }
        return this.mDvbtCellIds;
    }

    public java.util.List<android.media.tv.tuner.frontend.Atsc3PlpInfo> getAllAtsc3PlpInfo() {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Atsc3PlpInfo all status")) {
            throw new java.lang.UnsupportedOperationException("Atsc3PlpInfo all status is empty");
        }
        if (this.mAllPlpInfo == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        return java.util.Arrays.asList(this.mAllPlpInfo);
    }

    public static class Atsc3PlpTuningInfo {
        private final boolean mIsLocked;
        private final int mPlpId;
        private final int mUec;

        private Atsc3PlpTuningInfo(int i, boolean z, int i2) {
            this.mPlpId = i;
            this.mIsLocked = z;
            this.mUec = i2;
        }

        public int getPlpId() {
            return this.mPlpId;
        }

        public boolean isLocked() {
            return this.mIsLocked;
        }

        public int getUec() {
            return this.mUec;
        }
    }

    public java.lang.String getIptvContentUrl() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvContentUrl status");
        if (this.mIptvContentUrl == null) {
            throw new java.lang.IllegalStateException("IptvContentUrl status is empty");
        }
        return this.mIptvContentUrl;
    }

    public long getIptvPacketsLost() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvPacketsLost status");
        if (this.mIptvPacketsLost == null) {
            throw new java.lang.IllegalStateException("IptvPacketsLost status is empty");
        }
        return this.mIptvPacketsLost.longValue();
    }

    public long getIptvPacketsReceived() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvPacketsReceived status");
        if (this.mIptvPacketsReceived == null) {
            throw new java.lang.IllegalStateException("IptvPacketsReceived status is empty");
        }
        return this.mIptvPacketsReceived.longValue();
    }

    public int getIptvWorstJitterMillis() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvWorstJitterMs status");
        if (this.mIptvWorstJitterMs == null) {
            throw new java.lang.IllegalStateException("IptvWorstJitterMs status is empty");
        }
        return this.mIptvWorstJitterMs.intValue();
    }

    public int getIptvAverageJitterMillis() {
        android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvAverageJitterMs status");
        if (this.mIptvAverageJitterMs == null) {
            throw new java.lang.IllegalStateException("IptvAverageJitterMs status is empty");
        }
        return this.mIptvAverageJitterMs.intValue();
    }
}
