package android.media.tv;

/* loaded from: classes2.dex */
public final class SignalingDataRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.SignalingDataRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.SignalingDataRequest>() { // from class: android.media.tv.SignalingDataRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataRequest[] newArray(int i) {
            return new android.media.tv.SignalingDataRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataRequest createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.SignalingDataRequest(parcel);
        }
    };
    private static final int REQUEST_TYPE = 9;
    public static final int SIGNALING_DATA_NO_GROUP_ID = -1;
    public static final java.lang.String SIGNALING_METADATA_AEAT = "AEAT";
    public static final java.lang.String SIGNALING_METADATA_AEI = "AEI";
    public static final java.lang.String SIGNALING_METADATA_APD = "APD";
    public static final java.lang.String SIGNALING_METADATA_ASD = "ASD";
    public static final java.lang.String SIGNALING_METADATA_ASPD = "ASPD";
    public static final java.lang.String SIGNALING_METADATA_CAD = "CAD";
    public static final java.lang.String SIGNALING_METADATA_CDT = "CDT";
    public static final java.lang.String SIGNALING_METADATA_CRIT = "CRIT";
    public static final java.lang.String SIGNALING_METADATA_DCIT = "DCIT";
    public static final java.lang.String SIGNALING_METADATA_DWD = "DWD";
    public static final java.lang.String SIGNALING_METADATA_EMSG = "EMSG";
    public static final java.lang.String SIGNALING_METADATA_EVTI = "EVTI";
    public static final java.lang.String SIGNALING_METADATA_HELD = "HELD";
    public static final java.lang.String SIGNALING_METADATA_IED = "IED";
    public static final java.lang.String SIGNALING_METADATA_MPD = "MPD";
    public static final java.lang.String SIGNALING_METADATA_MPIT = "MPIT";
    public static final java.lang.String SIGNALING_METADATA_MPT = "MPT";
    public static final java.lang.String SIGNALING_METADATA_OSN = "OSN";
    public static final java.lang.String SIGNALING_METADATA_PAT = "PAT";
    public static final java.lang.String SIGNALING_METADATA_RDT = "RDT";
    public static final java.lang.String SIGNALING_METADATA_RRT = "RRT";
    public static final java.lang.String SIGNALING_METADATA_RSAT = "RSAT";
    public static final java.lang.String SIGNALING_METADATA_SLT = "SLT";
    public static final java.lang.String SIGNALING_METADATA_SMT = "SMT";
    public static final java.lang.String SIGNALING_METADATA_SSD = "SSD";
    public static final java.lang.String SIGNALING_METADATA_STSID = "STSID";
    public static final java.lang.String SIGNALING_METADATA_STT = "STT";
    public static final java.lang.String SIGNALING_METADATA_USBD = "USBD";
    public static final java.lang.String SIGNALING_METADATA_USD = "USD";
    public static final java.lang.String SIGNALING_METADATA_VSPD = "VSPD";
    private final int mGroup;
    private final java.util.List<java.lang.String> mSignalingDataTypes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignalingMetadata {
    }

    public SignalingDataRequest(int i, int i2, int i3, java.util.List<java.lang.String> list) {
        super(9, i, i2);
        this.mGroup = i3;
        this.mSignalingDataTypes = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataTypes);
    }

    static android.media.tv.SignalingDataRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.SignalingDataRequest(parcel);
    }

    public int getGroup() {
        return this.mGroup;
    }

    public java.util.List<java.lang.String> getSignalingDataTypes() {
        return this.mSignalingDataTypes;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mGroup);
        parcel.writeStringList(this.mSignalingDataTypes);
    }

    SignalingDataRequest(android.os.Parcel parcel) {
        super(9, parcel);
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mGroup = readInt;
        this.mSignalingDataTypes = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataTypes);
    }
}
