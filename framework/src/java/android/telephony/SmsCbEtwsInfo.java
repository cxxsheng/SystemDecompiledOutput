package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SmsCbEtwsInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SmsCbEtwsInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.SmsCbEtwsInfo>() { // from class: android.telephony.SmsCbEtwsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbEtwsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SmsCbEtwsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SmsCbEtwsInfo[] newArray(int i) {
            return new android.telephony.SmsCbEtwsInfo[i];
        }
    };
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE = 0;
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE_AND_TSUNAMI = 2;
    public static final int ETWS_WARNING_TYPE_OTHER_EMERGENCY = 4;
    public static final int ETWS_WARNING_TYPE_TEST_MESSAGE = 3;
    public static final int ETWS_WARNING_TYPE_TSUNAMI = 1;
    public static final int ETWS_WARNING_TYPE_UNKNOWN = -1;
    private final boolean mIsEmergencyUserAlert;
    private final boolean mIsPopupAlert;
    private final boolean mIsPrimary;
    private final byte[] mWarningSecurityInformation;
    private final int mWarningType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WarningType {
    }

    public SmsCbEtwsInfo(int i, boolean z, boolean z2, boolean z3, byte[] bArr) {
        this.mWarningType = i;
        this.mIsEmergencyUserAlert = z;
        this.mIsPopupAlert = z2;
        this.mIsPrimary = z3;
        this.mWarningSecurityInformation = bArr;
    }

    SmsCbEtwsInfo(android.os.Parcel parcel) {
        this.mWarningType = parcel.readInt();
        this.mIsEmergencyUserAlert = parcel.readInt() != 0;
        this.mIsPopupAlert = parcel.readInt() != 0;
        this.mIsPrimary = parcel.readInt() != 0;
        this.mWarningSecurityInformation = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mWarningType);
        parcel.writeInt(this.mIsEmergencyUserAlert ? 1 : 0);
        parcel.writeInt(this.mIsPopupAlert ? 1 : 0);
        parcel.writeInt(this.mIsPrimary ? 1 : 0);
        parcel.writeByteArray(this.mWarningSecurityInformation);
    }

    public int getWarningType() {
        return this.mWarningType;
    }

    public boolean isEmergencyUserAlert() {
        return this.mIsEmergencyUserAlert;
    }

    public boolean isPopupAlert() {
        return this.mIsPopupAlert;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }

    public long getPrimaryNotificationTimestamp() {
        if (this.mWarningSecurityInformation == null || this.mWarningSecurityInformation.length < 7) {
            return 0L;
        }
        int gsmBcdByteToInt = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[0]);
        int gsmBcdByteToInt2 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[1]);
        int gsmBcdByteToInt3 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[2]);
        int gsmBcdByteToInt4 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[3]);
        int gsmBcdByteToInt5 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[4]);
        int gsmBcdByteToInt6 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[5]);
        byte b = this.mWarningSecurityInformation[6];
        int gsmBcdByteToInt7 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt((byte) (b & (-9)));
        if ((b & 8) != 0) {
            gsmBcdByteToInt7 = -gsmBcdByteToInt7;
        }
        try {
            return (java.time.LocalDateTime.of(gsmBcdByteToInt + 2000, gsmBcdByteToInt2, gsmBcdByteToInt3, gsmBcdByteToInt4, gsmBcdByteToInt5, gsmBcdByteToInt6).toEpochSecond(java.time.ZoneOffset.UTC) - ((gsmBcdByteToInt7 * 15) * 60)) * 1000;
        } catch (java.time.DateTimeException e) {
            return 0L;
        }
    }

    public byte[] getPrimaryNotificationSignature() {
        if (this.mWarningSecurityInformation == null || this.mWarningSecurityInformation.length < 50) {
            return null;
        }
        return java.util.Arrays.copyOfRange(this.mWarningSecurityInformation, 7, 50);
    }

    public java.lang.String toString() {
        return "SmsCbEtwsInfo{warningType=" + this.mWarningType + ", emergencyUserAlert=" + this.mIsEmergencyUserAlert + ", activatePopup=" + this.mIsPopupAlert + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
