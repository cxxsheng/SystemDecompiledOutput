package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendStatus>() { // from class: android.hardware.tv.tuner.FrontendStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendStatus createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendStatus[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendStatus[i];
        }
    };
    public static final int agc = 14;
    public static final int allPlpInfo = 41;
    public static final int bandwidth = 25;
    public static final int ber = 2;
    public static final int bers = 23;
    public static final int codeRates = 24;
    public static final int dvbtCellIds = 40;
    public static final int freqOffset = 18;
    public static final int hierarchy = 19;
    public static final int innerFec = 8;
    public static final int interleaving = 30;
    public static final int interval = 26;
    public static final int inversion = 10;
    public static final int iptvAverageJitterMs = 46;
    public static final int iptvContentUrl = 42;
    public static final int iptvPacketsLost = 44;
    public static final int iptvPacketsReceived = 43;
    public static final int iptvWorstJitterMs = 45;
    public static final int isDemodLocked = 0;
    public static final int isEWBS = 13;
    public static final int isLayerError = 16;
    public static final int isLinear = 35;
    public static final int isLnaOn = 15;
    public static final int isMiso = 34;
    public static final int isRfLocked = 20;
    public static final int isShortFrames = 36;
    public static final int isdbtMode = 37;
    public static final int isdbtSegment = 31;
    public static final int lnbVoltage = 11;
    public static final int mer = 17;
    public static final int modulationStatus = 9;
    public static final int modulations = 22;
    public static final int partialReceptionFlag = 38;
    public static final int per = 3;
    public static final int plpId = 12;
    public static final int plpInfo = 21;
    public static final int preBer = 4;
    public static final int rollOff = 33;
    public static final int signalQuality = 5;
    public static final int signalStrength = 6;
    public static final int snr = 1;
    public static final int streamIdList = 39;
    public static final int symbolRate = 7;
    public static final int systemId = 29;
    public static final int transmissionMode = 27;
    public static final int tsDataRate = 32;
    public static final int uec = 28;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int agc = 14;
        public static final int allPlpInfo = 41;
        public static final int bandwidth = 25;
        public static final int ber = 2;
        public static final int bers = 23;
        public static final int codeRates = 24;
        public static final int dvbtCellIds = 40;
        public static final int freqOffset = 18;
        public static final int hierarchy = 19;
        public static final int innerFec = 8;
        public static final int interleaving = 30;
        public static final int interval = 26;
        public static final int inversion = 10;
        public static final int iptvAverageJitterMs = 46;
        public static final int iptvContentUrl = 42;
        public static final int iptvPacketsLost = 44;
        public static final int iptvPacketsReceived = 43;
        public static final int iptvWorstJitterMs = 45;
        public static final int isDemodLocked = 0;
        public static final int isEWBS = 13;
        public static final int isLayerError = 16;
        public static final int isLinear = 35;
        public static final int isLnaOn = 15;
        public static final int isMiso = 34;
        public static final int isRfLocked = 20;
        public static final int isShortFrames = 36;
        public static final int isdbtMode = 37;
        public static final int isdbtSegment = 31;
        public static final int lnbVoltage = 11;
        public static final int mer = 17;
        public static final int modulationStatus = 9;
        public static final int modulations = 22;
        public static final int partialReceptionFlag = 38;
        public static final int per = 3;
        public static final int plpId = 12;
        public static final int plpInfo = 21;
        public static final int preBer = 4;
        public static final int rollOff = 33;
        public static final int signalQuality = 5;
        public static final int signalStrength = 6;
        public static final int snr = 1;
        public static final int streamIdList = 39;
        public static final int symbolRate = 7;
        public static final int systemId = 29;
        public static final int transmissionMode = 27;
        public static final int tsDataRate = 32;
        public static final int uec = 28;
    }

    public FrontendStatus() {
        this._tag = 0;
        this._value = false;
    }

    private FrontendStatus(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendStatus(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendStatus isDemodLocked(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsDemodLocked() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsDemodLocked(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus snr(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(1, java.lang.Integer.valueOf(i));
    }

    public int getSnr() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSnr(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus ber(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(2, java.lang.Integer.valueOf(i));
    }

    public int getBer() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setBer(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus per(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(3, java.lang.Integer.valueOf(i));
    }

    public int getPer() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setPer(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus preBer(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(4, java.lang.Integer.valueOf(i));
    }

    public int getPreBer() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setPreBer(int i) {
        _set(4, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus signalQuality(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(5, java.lang.Integer.valueOf(i));
    }

    public int getSignalQuality() {
        _assertTag(5);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSignalQuality(int i) {
        _set(5, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus signalStrength(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(6, java.lang.Integer.valueOf(i));
    }

    public int getSignalStrength() {
        _assertTag(6);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSignalStrength(int i) {
        _set(6, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus symbolRate(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(7, java.lang.Integer.valueOf(i));
    }

    public int getSymbolRate() {
        _assertTag(7);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSymbolRate(int i) {
        _set(7, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus innerFec(long j) {
        return new android.hardware.tv.tuner.FrontendStatus(8, java.lang.Long.valueOf(j));
    }

    public long getInnerFec() {
        _assertTag(8);
        return ((java.lang.Long) this._value).longValue();
    }

    public void setInnerFec(long j) {
        _set(8, java.lang.Long.valueOf(j));
    }

    public static android.hardware.tv.tuner.FrontendStatus modulationStatus(android.hardware.tv.tuner.FrontendModulationStatus frontendModulationStatus) {
        return new android.hardware.tv.tuner.FrontendStatus(9, frontendModulationStatus);
    }

    public android.hardware.tv.tuner.FrontendModulationStatus getModulationStatus() {
        _assertTag(9);
        return (android.hardware.tv.tuner.FrontendModulationStatus) this._value;
    }

    public void setModulationStatus(android.hardware.tv.tuner.FrontendModulationStatus frontendModulationStatus) {
        _set(9, frontendModulationStatus);
    }

    public static android.hardware.tv.tuner.FrontendStatus inversion(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(10, java.lang.Integer.valueOf(i));
    }

    public int getInversion() {
        _assertTag(10);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setInversion(int i) {
        _set(10, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus lnbVoltage(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(11, java.lang.Integer.valueOf(i));
    }

    public int getLnbVoltage() {
        _assertTag(11);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setLnbVoltage(int i) {
        _set(11, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus plpId(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(12, java.lang.Integer.valueOf(i));
    }

    public int getPlpId() {
        _assertTag(12);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setPlpId(int i) {
        _set(12, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus isEWBS(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(13, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsEWBS() {
        _assertTag(13);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsEWBS(boolean z) {
        _set(13, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus agc(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(14, java.lang.Integer.valueOf(i));
    }

    public int getAgc() {
        _assertTag(14);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAgc(int i) {
        _set(14, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus isLnaOn(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(15, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsLnaOn() {
        _assertTag(15);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsLnaOn(boolean z) {
        _set(15, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus isLayerError(boolean[] zArr) {
        return new android.hardware.tv.tuner.FrontendStatus(16, zArr);
    }

    public boolean[] getIsLayerError() {
        _assertTag(16);
        return (boolean[]) this._value;
    }

    public void setIsLayerError(boolean[] zArr) {
        _set(16, zArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus mer(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(17, java.lang.Integer.valueOf(i));
    }

    public int getMer() {
        _assertTag(17);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setMer(int i) {
        _set(17, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus freqOffset(long j) {
        return new android.hardware.tv.tuner.FrontendStatus(18, java.lang.Long.valueOf(j));
    }

    public long getFreqOffset() {
        _assertTag(18);
        return ((java.lang.Long) this._value).longValue();
    }

    public void setFreqOffset(long j) {
        _set(18, java.lang.Long.valueOf(j));
    }

    public static android.hardware.tv.tuner.FrontendStatus hierarchy(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(19, java.lang.Integer.valueOf(i));
    }

    public int getHierarchy() {
        _assertTag(19);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setHierarchy(int i) {
        _set(19, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus isRfLocked(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(20, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsRfLocked() {
        _assertTag(20);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsRfLocked(boolean z) {
        _set(20, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus plpInfo(android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo[] frontendStatusAtsc3PlpInfoArr) {
        return new android.hardware.tv.tuner.FrontendStatus(21, frontendStatusAtsc3PlpInfoArr);
    }

    public android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo[] getPlpInfo() {
        _assertTag(21);
        return (android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo[]) this._value;
    }

    public void setPlpInfo(android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo[] frontendStatusAtsc3PlpInfoArr) {
        _set(21, frontendStatusAtsc3PlpInfoArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus modulations(android.hardware.tv.tuner.FrontendModulation[] frontendModulationArr) {
        return new android.hardware.tv.tuner.FrontendStatus(22, frontendModulationArr);
    }

    public android.hardware.tv.tuner.FrontendModulation[] getModulations() {
        _assertTag(22);
        return (android.hardware.tv.tuner.FrontendModulation[]) this._value;
    }

    public void setModulations(android.hardware.tv.tuner.FrontendModulation[] frontendModulationArr) {
        _set(22, frontendModulationArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus bers(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendStatus(23, iArr);
    }

    public int[] getBers() {
        _assertTag(23);
        return (int[]) this._value;
    }

    public void setBers(int[] iArr) {
        _set(23, iArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus codeRates(long[] jArr) {
        return new android.hardware.tv.tuner.FrontendStatus(24, jArr);
    }

    public long[] getCodeRates() {
        _assertTag(24);
        return (long[]) this._value;
    }

    public void setCodeRates(long[] jArr) {
        _set(24, jArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus bandwidth(android.hardware.tv.tuner.FrontendBandwidth frontendBandwidth) {
        return new android.hardware.tv.tuner.FrontendStatus(25, frontendBandwidth);
    }

    public android.hardware.tv.tuner.FrontendBandwidth getBandwidth() {
        _assertTag(25);
        return (android.hardware.tv.tuner.FrontendBandwidth) this._value;
    }

    public void setBandwidth(android.hardware.tv.tuner.FrontendBandwidth frontendBandwidth) {
        _set(25, frontendBandwidth);
    }

    public static android.hardware.tv.tuner.FrontendStatus interval(android.hardware.tv.tuner.FrontendGuardInterval frontendGuardInterval) {
        return new android.hardware.tv.tuner.FrontendStatus(26, frontendGuardInterval);
    }

    public android.hardware.tv.tuner.FrontendGuardInterval getInterval() {
        _assertTag(26);
        return (android.hardware.tv.tuner.FrontendGuardInterval) this._value;
    }

    public void setInterval(android.hardware.tv.tuner.FrontendGuardInterval frontendGuardInterval) {
        _set(26, frontendGuardInterval);
    }

    public static android.hardware.tv.tuner.FrontendStatus transmissionMode(android.hardware.tv.tuner.FrontendTransmissionMode frontendTransmissionMode) {
        return new android.hardware.tv.tuner.FrontendStatus(27, frontendTransmissionMode);
    }

    public android.hardware.tv.tuner.FrontendTransmissionMode getTransmissionMode() {
        _assertTag(27);
        return (android.hardware.tv.tuner.FrontendTransmissionMode) this._value;
    }

    public void setTransmissionMode(android.hardware.tv.tuner.FrontendTransmissionMode frontendTransmissionMode) {
        _set(27, frontendTransmissionMode);
    }

    public static android.hardware.tv.tuner.FrontendStatus uec(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(28, java.lang.Integer.valueOf(i));
    }

    public int getUec() {
        _assertTag(28);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setUec(int i) {
        _set(28, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus systemId(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(29, java.lang.Integer.valueOf(i));
    }

    public int getSystemId() {
        _assertTag(29);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSystemId(int i) {
        _set(29, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus interleaving(android.hardware.tv.tuner.FrontendInterleaveMode[] frontendInterleaveModeArr) {
        return new android.hardware.tv.tuner.FrontendStatus(30, frontendInterleaveModeArr);
    }

    public android.hardware.tv.tuner.FrontendInterleaveMode[] getInterleaving() {
        _assertTag(30);
        return (android.hardware.tv.tuner.FrontendInterleaveMode[]) this._value;
    }

    public void setInterleaving(android.hardware.tv.tuner.FrontendInterleaveMode[] frontendInterleaveModeArr) {
        _set(30, frontendInterleaveModeArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus isdbtSegment(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendStatus(31, iArr);
    }

    public int[] getIsdbtSegment() {
        _assertTag(31);
        return (int[]) this._value;
    }

    public void setIsdbtSegment(int[] iArr) {
        _set(31, iArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus tsDataRate(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendStatus(32, iArr);
    }

    public int[] getTsDataRate() {
        _assertTag(32);
        return (int[]) this._value;
    }

    public void setTsDataRate(int[] iArr) {
        _set(32, iArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus rollOff(android.hardware.tv.tuner.FrontendRollOff frontendRollOff) {
        return new android.hardware.tv.tuner.FrontendStatus(33, frontendRollOff);
    }

    public android.hardware.tv.tuner.FrontendRollOff getRollOff() {
        _assertTag(33);
        return (android.hardware.tv.tuner.FrontendRollOff) this._value;
    }

    public void setRollOff(android.hardware.tv.tuner.FrontendRollOff frontendRollOff) {
        _set(33, frontendRollOff);
    }

    public static android.hardware.tv.tuner.FrontendStatus isMiso(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(34, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsMiso() {
        _assertTag(34);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsMiso(boolean z) {
        _set(34, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus isLinear(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(35, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsLinear() {
        _assertTag(35);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsLinear(boolean z) {
        _set(35, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus isShortFrames(boolean z) {
        return new android.hardware.tv.tuner.FrontendStatus(36, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsShortFrames() {
        _assertTag(36);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsShortFrames(boolean z) {
        _set(36, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendStatus isdbtMode(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(37, java.lang.Integer.valueOf(i));
    }

    public int getIsdbtMode() {
        _assertTag(37);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbtMode(int i) {
        _set(37, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus partialReceptionFlag(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(38, java.lang.Integer.valueOf(i));
    }

    public int getPartialReceptionFlag() {
        _assertTag(38);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setPartialReceptionFlag(int i) {
        _set(38, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus streamIdList(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendStatus(39, iArr);
    }

    public int[] getStreamIdList() {
        _assertTag(39);
        return (int[]) this._value;
    }

    public void setStreamIdList(int[] iArr) {
        _set(39, iArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus dvbtCellIds(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendStatus(40, iArr);
    }

    public int[] getDvbtCellIds() {
        _assertTag(40);
        return (int[]) this._value;
    }

    public void setDvbtCellIds(int[] iArr) {
        _set(40, iArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus allPlpInfo(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        return new android.hardware.tv.tuner.FrontendStatus(41, frontendScanAtsc3PlpInfoArr);
    }

    public android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] getAllPlpInfo() {
        _assertTag(41);
        return (android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[]) this._value;
    }

    public void setAllPlpInfo(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        _set(41, frontendScanAtsc3PlpInfoArr);
    }

    public static android.hardware.tv.tuner.FrontendStatus iptvContentUrl(java.lang.String str) {
        return new android.hardware.tv.tuner.FrontendStatus(42, str);
    }

    public java.lang.String getIptvContentUrl() {
        _assertTag(42);
        return (java.lang.String) this._value;
    }

    public void setIptvContentUrl(java.lang.String str) {
        _set(42, str);
    }

    public static android.hardware.tv.tuner.FrontendStatus iptvPacketsReceived(long j) {
        return new android.hardware.tv.tuner.FrontendStatus(43, java.lang.Long.valueOf(j));
    }

    public long getIptvPacketsReceived() {
        _assertTag(43);
        return ((java.lang.Long) this._value).longValue();
    }

    public void setIptvPacketsReceived(long j) {
        _set(43, java.lang.Long.valueOf(j));
    }

    public static android.hardware.tv.tuner.FrontendStatus iptvPacketsLost(long j) {
        return new android.hardware.tv.tuner.FrontendStatus(44, java.lang.Long.valueOf(j));
    }

    public long getIptvPacketsLost() {
        _assertTag(44);
        return ((java.lang.Long) this._value).longValue();
    }

    public void setIptvPacketsLost(long j) {
        _set(44, java.lang.Long.valueOf(j));
    }

    public static android.hardware.tv.tuner.FrontendStatus iptvWorstJitterMs(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(45, java.lang.Integer.valueOf(i));
    }

    public int getIptvWorstJitterMs() {
        _assertTag(45);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIptvWorstJitterMs(int i) {
        _set(45, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendStatus iptvAverageJitterMs(int i) {
        return new android.hardware.tv.tuner.FrontendStatus(46, java.lang.Integer.valueOf(i));
    }

    public int getIptvAverageJitterMs() {
        _assertTag(46);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIptvAverageJitterMs(int i) {
        _set(46, java.lang.Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeBoolean(getIsDemodLocked());
                break;
            case 1:
                parcel.writeInt(getSnr());
                break;
            case 2:
                parcel.writeInt(getBer());
                break;
            case 3:
                parcel.writeInt(getPer());
                break;
            case 4:
                parcel.writeInt(getPreBer());
                break;
            case 5:
                parcel.writeInt(getSignalQuality());
                break;
            case 6:
                parcel.writeInt(getSignalStrength());
                break;
            case 7:
                parcel.writeInt(getSymbolRate());
                break;
            case 8:
                parcel.writeLong(getInnerFec());
                break;
            case 9:
                parcel.writeTypedObject(getModulationStatus(), i);
                break;
            case 10:
                parcel.writeInt(getInversion());
                break;
            case 11:
                parcel.writeInt(getLnbVoltage());
                break;
            case 12:
                parcel.writeInt(getPlpId());
                break;
            case 13:
                parcel.writeBoolean(getIsEWBS());
                break;
            case 14:
                parcel.writeInt(getAgc());
                break;
            case 15:
                parcel.writeBoolean(getIsLnaOn());
                break;
            case 16:
                parcel.writeBooleanArray(getIsLayerError());
                break;
            case 17:
                parcel.writeInt(getMer());
                break;
            case 18:
                parcel.writeLong(getFreqOffset());
                break;
            case 19:
                parcel.writeInt(getHierarchy());
                break;
            case 20:
                parcel.writeBoolean(getIsRfLocked());
                break;
            case 21:
                parcel.writeTypedArray(getPlpInfo(), i);
                break;
            case 22:
                parcel.writeTypedArray(getModulations(), i);
                break;
            case 23:
                parcel.writeIntArray(getBers());
                break;
            case 24:
                parcel.writeLongArray(getCodeRates());
                break;
            case 25:
                parcel.writeTypedObject(getBandwidth(), i);
                break;
            case 26:
                parcel.writeTypedObject(getInterval(), i);
                break;
            case 27:
                parcel.writeTypedObject(getTransmissionMode(), i);
                break;
            case 28:
                parcel.writeInt(getUec());
                break;
            case 29:
                parcel.writeInt(getSystemId());
                break;
            case 30:
                parcel.writeTypedArray(getInterleaving(), i);
                break;
            case 31:
                parcel.writeIntArray(getIsdbtSegment());
                break;
            case 32:
                parcel.writeIntArray(getTsDataRate());
                break;
            case 33:
                parcel.writeTypedObject(getRollOff(), i);
                break;
            case 34:
                parcel.writeBoolean(getIsMiso());
                break;
            case 35:
                parcel.writeBoolean(getIsLinear());
                break;
            case 36:
                parcel.writeBoolean(getIsShortFrames());
                break;
            case 37:
                parcel.writeInt(getIsdbtMode());
                break;
            case 38:
                parcel.writeInt(getPartialReceptionFlag());
                break;
            case 39:
                parcel.writeIntArray(getStreamIdList());
                break;
            case 40:
                parcel.writeIntArray(getDvbtCellIds());
                break;
            case 41:
                parcel.writeTypedArray(getAllPlpInfo(), i);
                break;
            case 42:
                parcel.writeString(getIptvContentUrl());
                break;
            case 43:
                parcel.writeLong(getIptvPacketsReceived());
                break;
            case 44:
                parcel.writeLong(getIptvPacketsLost());
                break;
            case 45:
                parcel.writeInt(getIptvWorstJitterMs());
                break;
            case 46:
                parcel.writeInt(getIptvAverageJitterMs());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 1:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 5:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 8:
                _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                return;
            case 9:
                _set(readInt, (android.hardware.tv.tuner.FrontendModulationStatus) parcel.readTypedObject(android.hardware.tv.tuner.FrontendModulationStatus.CREATOR));
                return;
            case 10:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 11:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 12:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 13:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 14:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 15:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 16:
                _set(readInt, parcel.createBooleanArray());
                return;
            case 17:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 18:
                _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                return;
            case 19:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 20:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 21:
                _set(readInt, (android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo[]) parcel.createTypedArray(android.hardware.tv.tuner.FrontendStatusAtsc3PlpInfo.CREATOR));
                return;
            case 22:
                _set(readInt, (android.hardware.tv.tuner.FrontendModulation[]) parcel.createTypedArray(android.hardware.tv.tuner.FrontendModulation.CREATOR));
                return;
            case 23:
                _set(readInt, parcel.createIntArray());
                return;
            case 24:
                _set(readInt, parcel.createLongArray());
                return;
            case 25:
                _set(readInt, (android.hardware.tv.tuner.FrontendBandwidth) parcel.readTypedObject(android.hardware.tv.tuner.FrontendBandwidth.CREATOR));
                return;
            case 26:
                _set(readInt, (android.hardware.tv.tuner.FrontendGuardInterval) parcel.readTypedObject(android.hardware.tv.tuner.FrontendGuardInterval.CREATOR));
                return;
            case 27:
                _set(readInt, (android.hardware.tv.tuner.FrontendTransmissionMode) parcel.readTypedObject(android.hardware.tv.tuner.FrontendTransmissionMode.CREATOR));
                return;
            case 28:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 29:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 30:
                _set(readInt, (android.hardware.tv.tuner.FrontendInterleaveMode[]) parcel.createTypedArray(android.hardware.tv.tuner.FrontendInterleaveMode.CREATOR));
                return;
            case 31:
                _set(readInt, parcel.createIntArray());
                return;
            case 32:
                _set(readInt, parcel.createIntArray());
                return;
            case 33:
                _set(readInt, (android.hardware.tv.tuner.FrontendRollOff) parcel.readTypedObject(android.hardware.tv.tuner.FrontendRollOff.CREATOR));
                return;
            case 34:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 35:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 36:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 37:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 38:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 39:
                _set(readInt, parcel.createIntArray());
                return;
            case 40:
                _set(readInt, parcel.createIntArray());
                return;
            case 41:
                _set(readInt, (android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[]) parcel.createTypedArray(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo.CREATOR));
                return;
            case 42:
                _set(readInt, parcel.readString());
                return;
            case 43:
                _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                return;
            case 44:
                _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                return;
            case 45:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 46:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 9:
                return 0 | describeContents(getModulationStatus());
            case 21:
                return 0 | describeContents(getPlpInfo());
            case 22:
                return 0 | describeContents(getModulations());
            case 25:
                return 0 | describeContents(getBandwidth());
            case 26:
                return 0 | describeContents(getInterval());
            case 27:
                return 0 | describeContents(getTransmissionMode());
            case 30:
                return 0 | describeContents(getInterleaving());
            case 33:
                return 0 | describeContents(getRollOff());
            case 41:
                return 0 | describeContents(getAllPlpInfo());
            default:
                return 0;
        }
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "isDemodLocked";
            case 1:
                return "snr";
            case 2:
                return "ber";
            case 3:
                return "per";
            case 4:
                return "preBer";
            case 5:
                return "signalQuality";
            case 6:
                return "signalStrength";
            case 7:
                return "symbolRate";
            case 8:
                return "innerFec";
            case 9:
                return "modulationStatus";
            case 10:
                return "inversion";
            case 11:
                return "lnbVoltage";
            case 12:
                return "plpId";
            case 13:
                return "isEWBS";
            case 14:
                return "agc";
            case 15:
                return "isLnaOn";
            case 16:
                return "isLayerError";
            case 17:
                return "mer";
            case 18:
                return "freqOffset";
            case 19:
                return "hierarchy";
            case 20:
                return "isRfLocked";
            case 21:
                return "plpInfo";
            case 22:
                return "modulations";
            case 23:
                return "bers";
            case 24:
                return "codeRates";
            case 25:
                return "bandwidth";
            case 26:
                return "interval";
            case 27:
                return "transmissionMode";
            case 28:
                return "uec";
            case 29:
                return android.content.Intent.EXTRA_SYSTEM_ID;
            case 30:
                return "interleaving";
            case 31:
                return "isdbtSegment";
            case 32:
                return "tsDataRate";
            case 33:
                return "rollOff";
            case 34:
                return "isMiso";
            case 35:
                return "isLinear";
            case 36:
                return "isShortFrames";
            case 37:
                return "isdbtMode";
            case 38:
                return "partialReceptionFlag";
            case 39:
                return "streamIdList";
            case 40:
                return "dvbtCellIds";
            case 41:
                return "allPlpInfo";
            case 42:
                return "iptvContentUrl";
            case 43:
                return "iptvPacketsReceived";
            case 44:
                return "iptvPacketsLost";
            case 45:
                return "iptvWorstJitterMs";
            case 46:
                return "iptvAverageJitterMs";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
