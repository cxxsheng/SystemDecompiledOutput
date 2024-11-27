package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthCdma extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellSignalStrengthCdma";
    private int mCdmaDbm;
    private int mCdmaEcio;
    private int mEvdoDbm;
    private int mEvdoEcio;
    private int mEvdoSnr;
    private int mLevel;
    private static final android.telephony.CellSignalStrengthCdma sInvalid = new android.telephony.CellSignalStrengthCdma();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthCdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthCdma>() { // from class: android.telephony.CellSignalStrengthCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthCdma createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthCdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthCdma[] newArray(int i) {
            return new android.telephony.CellSignalStrengthCdma[i];
        }
    };

    public CellSignalStrengthCdma() {
        setDefaultValues();
    }

    public CellSignalStrengthCdma(int i, int i2, int i3, int i4, int i5) {
        this.mCdmaDbm = inRangeOrUnavailable(i, -120, 0);
        this.mCdmaEcio = inRangeOrUnavailable(i2, -160, 0);
        this.mEvdoDbm = inRangeOrUnavailable(i3, -120, 0);
        this.mEvdoEcio = inRangeOrUnavailable(i4, -160, 0);
        this.mEvdoSnr = inRangeOrUnavailable(i5, 0, 8);
        updateLevel(null, null);
    }

    public CellSignalStrengthCdma(android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma) {
        copyFrom(cellSignalStrengthCdma);
    }

    protected void copyFrom(android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma) {
        this.mCdmaDbm = cellSignalStrengthCdma.mCdmaDbm;
        this.mCdmaEcio = cellSignalStrengthCdma.mCdmaEcio;
        this.mEvdoDbm = cellSignalStrengthCdma.mEvdoDbm;
        this.mEvdoEcio = cellSignalStrengthCdma.mEvdoEcio;
        this.mEvdoSnr = cellSignalStrengthCdma.mEvdoSnr;
        this.mLevel = cellSignalStrengthCdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthCdma copy() {
        return new android.telephony.CellSignalStrengthCdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mCdmaDbm = Integer.MAX_VALUE;
        this.mCdmaEcio = Integer.MAX_VALUE;
        this.mEvdoDbm = Integer.MAX_VALUE;
        this.mEvdoEcio = Integer.MAX_VALUE;
        this.mEvdoSnr = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        int cdmaLevel = getCdmaLevel();
        int evdoLevel = getEvdoLevel();
        if (evdoLevel == 0) {
            this.mLevel = getCdmaLevel();
        } else {
            if (cdmaLevel == 0) {
                this.mLevel = getEvdoLevel();
                return;
            }
            if (cdmaLevel >= evdoLevel) {
                cdmaLevel = evdoLevel;
            }
            this.mLevel = cdmaLevel;
        }
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        int i;
        int cdmaDbm = getCdmaDbm();
        int cdmaEcio = getCdmaEcio();
        int i2 = 1;
        if (cdmaDbm == Integer.MAX_VALUE) {
            i = 99;
        } else if (cdmaDbm >= -75) {
            i = 16;
        } else if (cdmaDbm >= -82) {
            i = 8;
        } else if (cdmaDbm >= -90) {
            i = 4;
        } else if (cdmaDbm >= -95) {
            i = 2;
        } else if (cdmaDbm >= -100) {
            i = 1;
        } else {
            i = 99;
        }
        if (cdmaEcio == Integer.MAX_VALUE) {
            i2 = 99;
        } else if (cdmaEcio >= -90) {
            i2 = 16;
        } else if (cdmaEcio >= -100) {
            i2 = 8;
        } else if (cdmaEcio >= -115) {
            i2 = 4;
        } else if (cdmaEcio >= -130) {
            i2 = 2;
        } else if (cdmaEcio < -150) {
            i2 = 99;
        }
        return i < i2 ? i : i2;
    }

    public int getCdmaLevel() {
        int i;
        int cdmaDbm = getCdmaDbm();
        int cdmaEcio = getCdmaEcio();
        int i2 = 1;
        if (cdmaDbm == Integer.MAX_VALUE) {
            i = 0;
        } else if (cdmaDbm >= -75) {
            i = 4;
        } else if (cdmaDbm >= -85) {
            i = 3;
        } else if (cdmaDbm >= -95) {
            i = 2;
        } else if (cdmaDbm >= -100) {
            i = 1;
        } else {
            i = 0;
        }
        if (cdmaEcio == Integer.MAX_VALUE) {
            i2 = 0;
        } else if (cdmaEcio >= -90) {
            i2 = 4;
        } else if (cdmaEcio >= -110) {
            i2 = 3;
        } else if (cdmaEcio >= -130) {
            i2 = 2;
        } else if (cdmaEcio < -150) {
            i2 = 0;
        }
        return i < i2 ? i : i2;
    }

    public int getEvdoLevel() {
        int i;
        int evdoDbm = getEvdoDbm();
        int evdoSnr = getEvdoSnr();
        int i2 = 2;
        if (evdoDbm == Integer.MAX_VALUE) {
            i = 0;
        } else if (evdoDbm >= -65) {
            i = 4;
        } else if (evdoDbm >= -75) {
            i = 3;
        } else if (evdoDbm >= -90) {
            i = 2;
        } else {
            i = evdoDbm >= -105 ? 1 : 0;
        }
        if (evdoSnr == Integer.MAX_VALUE) {
            i2 = 0;
        } else if (evdoSnr >= 7) {
            i2 = 4;
        } else if (evdoSnr >= 5) {
            i2 = 3;
        } else if (evdoSnr < 3) {
            i2 = evdoSnr >= 1 ? 1 : 0;
        }
        return i < i2 ? i : i2;
    }

    public int getEvdoAsuLevel() {
        int i;
        int evdoDbm = getEvdoDbm();
        int evdoSnr = getEvdoSnr();
        int i2 = 99;
        if (evdoDbm >= -65) {
            i = 16;
        } else if (evdoDbm >= -75) {
            i = 8;
        } else if (evdoDbm >= -85) {
            i = 4;
        } else if (evdoDbm >= -95) {
            i = 2;
        } else {
            i = evdoDbm >= -105 ? 1 : 99;
        }
        if (evdoSnr >= 7) {
            i2 = 16;
        } else if (evdoSnr >= 6) {
            i2 = 8;
        } else if (evdoSnr >= 5) {
            i2 = 4;
        } else if (evdoSnr >= 3) {
            i2 = 2;
        } else if (evdoSnr >= 1) {
            i2 = 1;
        }
        return i < i2 ? i : i2;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        int cdmaDbm = getCdmaDbm();
        int evdoDbm = getEvdoDbm();
        return cdmaDbm < evdoDbm ? cdmaDbm : evdoDbm;
    }

    public int getCdmaDbm() {
        return this.mCdmaDbm;
    }

    public void setCdmaDbm(int i) {
        this.mCdmaDbm = i;
    }

    public int getCdmaEcio() {
        return this.mCdmaEcio;
    }

    public void setCdmaEcio(int i) {
        this.mCdmaEcio = i;
    }

    public int getEvdoDbm() {
        return this.mEvdoDbm;
    }

    public void setEvdoDbm(int i) {
        this.mEvdoDbm = i;
    }

    public int getEvdoEcio() {
        return this.mEvdoEcio;
    }

    public void setEvdoEcio(int i) {
        this.mEvdoEcio = i;
    }

    public int getEvdoSnr() {
        return this.mEvdoSnr;
    }

    public void setEvdoSnr(int i) {
        this.mEvdoSnr = i;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCdmaDbm), java.lang.Integer.valueOf(this.mCdmaEcio), java.lang.Integer.valueOf(this.mEvdoDbm), java.lang.Integer.valueOf(this.mEvdoEcio), java.lang.Integer.valueOf(this.mEvdoSnr), java.lang.Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthCdma)) {
            return false;
        }
        android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma = (android.telephony.CellSignalStrengthCdma) obj;
        return this.mCdmaDbm == cellSignalStrengthCdma.mCdmaDbm && this.mCdmaEcio == cellSignalStrengthCdma.mCdmaEcio && this.mEvdoDbm == cellSignalStrengthCdma.mEvdoDbm && this.mEvdoEcio == cellSignalStrengthCdma.mEvdoEcio && this.mEvdoSnr == cellSignalStrengthCdma.mEvdoSnr && this.mLevel == cellSignalStrengthCdma.mLevel;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthCdma: cdmaDbm=" + this.mCdmaDbm + " cdmaEcio=" + this.mCdmaEcio + " evdoDbm=" + this.mEvdoDbm + " evdoEcio=" + this.mEvdoEcio + " evdoSnr=" + this.mEvdoSnr + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCdmaDbm);
        parcel.writeInt(this.mCdmaEcio);
        parcel.writeInt(this.mEvdoDbm);
        parcel.writeInt(this.mEvdoEcio);
        parcel.writeInt(this.mEvdoSnr);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthCdma(android.os.Parcel parcel) {
        this.mCdmaDbm = parcel.readInt();
        this.mCdmaEcio = parcel.readInt();
        this.mEvdoDbm = parcel.readInt();
        this.mEvdoEcio = parcel.readInt();
        this.mEvdoSnr = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
