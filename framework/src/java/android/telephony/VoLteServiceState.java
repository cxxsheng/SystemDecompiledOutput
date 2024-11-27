package android.telephony;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class VoLteServiceState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.VoLteServiceState> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.VoLteServiceState.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.VoLteServiceState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.VoLteServiceState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.VoLteServiceState[] newArray(int i) {
            return new android.telephony.VoLteServiceState[i];
        }
    };
    private static final boolean DBG = false;
    public static final int HANDOVER_CANCELED = 3;
    public static final int HANDOVER_COMPLETED = 1;
    public static final int HANDOVER_FAILED = 2;
    public static final int HANDOVER_STARTED = 0;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final java.lang.String LOG_TAG = "VoLteServiceState";
    public static final int NOT_SUPPORTED = 0;
    public static final int SUPPORTED = 1;
    private int mSrvccState;

    public static android.telephony.VoLteServiceState newFromBundle(android.os.Bundle bundle) {
        android.telephony.VoLteServiceState voLteServiceState = new android.telephony.VoLteServiceState();
        voLteServiceState.setFromNotifierBundle(bundle);
        return voLteServiceState;
    }

    public VoLteServiceState() {
        initialize();
    }

    public VoLteServiceState(int i) {
        initialize();
        this.mSrvccState = i;
    }

    public VoLteServiceState(android.telephony.VoLteServiceState voLteServiceState) {
        copyFrom(voLteServiceState);
    }

    private void initialize() {
        this.mSrvccState = Integer.MAX_VALUE;
    }

    protected void copyFrom(android.telephony.VoLteServiceState voLteServiceState) {
        this.mSrvccState = voLteServiceState.mSrvccState;
    }

    public VoLteServiceState(android.os.Parcel parcel) {
        this.mSrvccState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSrvccState);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void validateInput() {
    }

    public int hashCode() {
        return this.mSrvccState * 31;
    }

    public boolean equals(java.lang.Object obj) {
        try {
            return obj != null && this.mSrvccState == ((android.telephony.VoLteServiceState) obj).mSrvccState;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public java.lang.String toString() {
        return "VoLteServiceState: " + this.mSrvccState;
    }

    private void setFromNotifierBundle(android.os.Bundle bundle) {
        this.mSrvccState = bundle.getInt("mSrvccState");
    }

    public void fillInNotifierBundle(android.os.Bundle bundle) {
        bundle.putInt("mSrvccState", this.mSrvccState);
    }

    public int getSrvccState() {
        return this.mSrvccState;
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
