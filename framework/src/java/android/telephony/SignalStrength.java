package android.telephony;

/* loaded from: classes3.dex */
public class SignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SignalStrength> CREATOR = new android.os.Parcelable.Creator<android.telephony.SignalStrength>() { // from class: android.telephony.SignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalStrength createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SignalStrength(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalStrength[] newArray(int i) {
            return new android.telephony.SignalStrength[i];
        }
    };
    private static final boolean DBG = false;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final java.lang.String LOG_TAG = "SignalStrength";
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;
    android.telephony.CellSignalStrengthCdma mCdma;
    android.telephony.CellSignalStrengthGsm mGsm;
    android.telephony.CellSignalStrengthLte mLte;
    private boolean mLteAsPrimaryInNrNsa;
    android.telephony.CellSignalStrengthNr mNr;
    android.telephony.CellSignalStrengthTdscdma mTdscdma;
    private long mTimestampMillis;
    android.telephony.CellSignalStrengthWcdma mWcdma;

    public SignalStrength() {
        this(new android.telephony.CellSignalStrengthCdma(), new android.telephony.CellSignalStrengthGsm(), new android.telephony.CellSignalStrengthWcdma(), new android.telephony.CellSignalStrengthTdscdma(), new android.telephony.CellSignalStrengthLte(), new android.telephony.CellSignalStrengthNr());
    }

    public SignalStrength(android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma, android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm, android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma, android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma, android.telephony.CellSignalStrengthLte cellSignalStrengthLte, android.telephony.CellSignalStrengthNr cellSignalStrengthNr) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mCdma = cellSignalStrengthCdma;
        this.mGsm = cellSignalStrengthGsm;
        this.mWcdma = cellSignalStrengthWcdma;
        this.mTdscdma = cellSignalStrengthTdscdma;
        this.mLte = cellSignalStrengthLte;
        this.mNr = cellSignalStrengthNr;
        this.mTimestampMillis = android.os.SystemClock.elapsedRealtime();
    }

    private android.telephony.CellSignalStrength getPrimary() {
        return (this.mLteAsPrimaryInNrNsa && this.mLte.isValid()) ? this.mLte : this.mNr.isValid() ? this.mNr : this.mLte.isValid() ? this.mLte : this.mCdma.isValid() ? this.mCdma : this.mTdscdma.isValid() ? this.mTdscdma : this.mWcdma.isValid() ? this.mWcdma : this.mGsm.isValid() ? this.mGsm : this.mLte;
    }

    public java.util.List<android.telephony.CellSignalStrength> getCellSignalStrengths() {
        return getCellSignalStrengths(android.telephony.CellSignalStrength.class);
    }

    public <T extends android.telephony.CellSignalStrength> java.util.List<T> getCellSignalStrengths(java.lang.Class<T> cls) {
        java.util.ArrayList arrayList = new java.util.ArrayList(2);
        if (this.mLte.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthLte.class)) {
            arrayList.add(this.mLte);
        }
        if (this.mCdma.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthCdma.class)) {
            arrayList.add(this.mCdma);
        }
        if (this.mTdscdma.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthTdscdma.class)) {
            arrayList.add(this.mTdscdma);
        }
        if (this.mWcdma.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthWcdma.class)) {
            arrayList.add(this.mWcdma);
        }
        if (this.mGsm.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthGsm.class)) {
            arrayList.add(this.mGsm);
        }
        if (this.mNr.isValid() && cls.isAssignableFrom(android.telephony.CellSignalStrengthNr.class)) {
            arrayList.add(this.mNr);
        }
        return arrayList;
    }

    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        if (persistableBundle != null) {
            this.mLteAsPrimaryInNrNsa = persistableBundle.getBoolean(android.telephony.CarrierConfigManager.KEY_SIGNAL_STRENGTH_NR_NSA_USE_LTE_AS_PRIMARY_BOOL, true);
        }
        this.mCdma.updateLevel(persistableBundle, serviceState);
        this.mGsm.updateLevel(persistableBundle, serviceState);
        this.mWcdma.updateLevel(persistableBundle, serviceState);
        this.mTdscdma.updateLevel(persistableBundle, serviceState);
        this.mLte.updateLevel(persistableBundle, serviceState);
        this.mNr.updateLevel(persistableBundle, serviceState);
    }

    public SignalStrength(android.telephony.SignalStrength signalStrength) {
        this.mLteAsPrimaryInNrNsa = true;
        copyFrom(signalStrength);
    }

    protected void copyFrom(android.telephony.SignalStrength signalStrength) {
        this.mCdma = new android.telephony.CellSignalStrengthCdma(signalStrength.mCdma);
        this.mGsm = new android.telephony.CellSignalStrengthGsm(signalStrength.mGsm);
        this.mWcdma = new android.telephony.CellSignalStrengthWcdma(signalStrength.mWcdma);
        this.mTdscdma = new android.telephony.CellSignalStrengthTdscdma(signalStrength.mTdscdma);
        this.mLte = new android.telephony.CellSignalStrengthLte(signalStrength.mLte);
        this.mNr = new android.telephony.CellSignalStrengthNr(signalStrength.mNr);
        this.mTimestampMillis = signalStrength.getTimestampMillis();
    }

    public SignalStrength(android.os.Parcel parcel) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mCdma = (android.telephony.CellSignalStrengthCdma) parcel.readParcelable(android.telephony.CellSignalStrengthCdma.class.getClassLoader(), android.telephony.CellSignalStrengthCdma.class);
        this.mGsm = (android.telephony.CellSignalStrengthGsm) parcel.readParcelable(android.telephony.CellSignalStrengthGsm.class.getClassLoader(), android.telephony.CellSignalStrengthGsm.class);
        this.mWcdma = (android.telephony.CellSignalStrengthWcdma) parcel.readParcelable(android.telephony.CellSignalStrengthWcdma.class.getClassLoader(), android.telephony.CellSignalStrengthWcdma.class);
        this.mTdscdma = (android.telephony.CellSignalStrengthTdscdma) parcel.readParcelable(android.telephony.CellSignalStrengthTdscdma.class.getClassLoader(), android.telephony.CellSignalStrengthTdscdma.class);
        this.mLte = (android.telephony.CellSignalStrengthLte) parcel.readParcelable(android.telephony.CellSignalStrengthLte.class.getClassLoader(), android.telephony.CellSignalStrengthLte.class);
        this.mNr = (android.telephony.CellSignalStrengthNr) parcel.readParcelable(android.telephony.CellSignalStrengthLte.class.getClassLoader(), android.telephony.CellSignalStrengthNr.class);
        this.mTimestampMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCdma, i);
        parcel.writeParcelable(this.mGsm, i);
        parcel.writeParcelable(this.mWcdma, i);
        parcel.writeParcelable(this.mTdscdma, i);
        parcel.writeParcelable(this.mLte, i);
        parcel.writeParcelable(this.mNr, i);
        parcel.writeLong(this.mTimestampMillis);
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @java.lang.Deprecated
    public int getGsmSignalStrength() {
        return this.mGsm.getAsuLevel();
    }

    @java.lang.Deprecated
    public int getGsmBitErrorRate() {
        return this.mGsm.getBitErrorRate();
    }

    @java.lang.Deprecated
    public int getCdmaDbm() {
        return this.mCdma.getCdmaDbm();
    }

    @java.lang.Deprecated
    public int getCdmaEcio() {
        return this.mCdma.getCdmaEcio();
    }

    @java.lang.Deprecated
    public int getEvdoDbm() {
        return this.mCdma.getEvdoDbm();
    }

    @java.lang.Deprecated
    public int getEvdoEcio() {
        return this.mCdma.getEvdoEcio();
    }

    @java.lang.Deprecated
    public int getEvdoSnr() {
        return this.mCdma.getEvdoSnr();
    }

    @java.lang.Deprecated
    public int getLteSignalStrength() {
        return this.mLte.getRssi();
    }

    @java.lang.Deprecated
    public int getLteRsrp() {
        return this.mLte.getRsrp();
    }

    @java.lang.Deprecated
    public int getLteRsrq() {
        return this.mLte.getRsrq();
    }

    @java.lang.Deprecated
    public int getLteRssnr() {
        return this.mLte.getRssnr();
    }

    @java.lang.Deprecated
    public int getLteCqi() {
        return this.mLte.getCqi();
    }

    public int getLevel() {
        int level = getPrimary().getLevel();
        if (level < 0 || level > 4) {
            loge("Invalid Level " + level + ", this=" + this);
            return 0;
        }
        return getPrimary().getLevel();
    }

    @java.lang.Deprecated
    public int getAsuLevel() {
        return getPrimary().getAsuLevel();
    }

    @java.lang.Deprecated
    public int getDbm() {
        return getPrimary().getDbm();
    }

    @java.lang.Deprecated
    public int getGsmDbm() {
        return this.mGsm.getDbm();
    }

    @java.lang.Deprecated
    public int getGsmLevel() {
        return this.mGsm.getLevel();
    }

    @java.lang.Deprecated
    public int getGsmAsuLevel() {
        return this.mGsm.getAsuLevel();
    }

    @java.lang.Deprecated
    public int getCdmaLevel() {
        return this.mCdma.getLevel();
    }

    @java.lang.Deprecated
    public int getCdmaAsuLevel() {
        return this.mCdma.getAsuLevel();
    }

    @java.lang.Deprecated
    public int getEvdoLevel() {
        return this.mCdma.getEvdoLevel();
    }

    @java.lang.Deprecated
    public int getEvdoAsuLevel() {
        return this.mCdma.getEvdoAsuLevel();
    }

    @java.lang.Deprecated
    public int getLteDbm() {
        return this.mLte.getRsrp();
    }

    @java.lang.Deprecated
    public int getLteLevel() {
        return this.mLte.getLevel();
    }

    @java.lang.Deprecated
    public int getLteAsuLevel() {
        return this.mLte.getAsuLevel();
    }

    @java.lang.Deprecated
    public boolean isGsm() {
        return !(getPrimary() instanceof android.telephony.CellSignalStrengthCdma);
    }

    @java.lang.Deprecated
    public int getTdScdmaDbm() {
        return this.mTdscdma.getRscp();
    }

    @java.lang.Deprecated
    public int getTdScdmaLevel() {
        return this.mTdscdma.getLevel();
    }

    @java.lang.Deprecated
    public int getTdScdmaAsuLevel() {
        return this.mTdscdma.getAsuLevel();
    }

    @java.lang.Deprecated
    public int getWcdmaRscp() {
        return this.mWcdma.getRscp();
    }

    @java.lang.Deprecated
    public int getWcdmaAsuLevel() {
        return this.mWcdma.getAsuLevel();
    }

    @java.lang.Deprecated
    public int getWcdmaDbm() {
        return this.mWcdma.getDbm();
    }

    @java.lang.Deprecated
    public int getWcdmaLevel() {
        return this.mWcdma.getLevel();
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mCdma, this.mGsm, this.mWcdma, this.mTdscdma, this.mLte, this.mNr);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.SignalStrength)) {
            return false;
        }
        android.telephony.SignalStrength signalStrength = (android.telephony.SignalStrength) obj;
        return this.mCdma.equals(signalStrength.mCdma) && this.mGsm.equals(signalStrength.mGsm) && this.mWcdma.equals(signalStrength.mWcdma) && this.mTdscdma.equals(signalStrength.mTdscdma) && this.mLte.equals(signalStrength.mLte) && this.mNr.equals(signalStrength.mNr);
    }

    public java.lang.String toString() {
        return "SignalStrength:{mCdma=" + this.mCdma + ",mGsm=" + this.mGsm + ",mWcdma=" + this.mWcdma + ",mTdscdma=" + this.mTdscdma + ",mLte=" + this.mLte + ",mNr=" + this.mNr + ",primary=" + getPrimary().getClass().getSimpleName() + "}";
    }

    @java.lang.Deprecated
    public void fillInNotifierBundle(android.os.Bundle bundle) {
        bundle.putParcelable("Cdma", this.mCdma);
        bundle.putParcelable("Gsm", this.mGsm);
        bundle.putParcelable("Wcdma", this.mWcdma);
        bundle.putParcelable("Tdscdma", this.mTdscdma);
        bundle.putParcelable("Lte", this.mLte);
        bundle.putParcelable("Nr", this.mNr);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }

    private static void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }
}
