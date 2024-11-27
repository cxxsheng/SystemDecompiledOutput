package android.media.tv;

/* loaded from: classes2.dex */
public final class SignalingDataResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.SignalingDataResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.SignalingDataResponse>() { // from class: android.media.tv.SignalingDataResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataResponse[] newArray(int i) {
            return new android.media.tv.SignalingDataResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataResponse createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.SignalingDataResponse(parcel);
        }
    };
    private static final int RESPONSE_TYPE = 9;
    private final java.util.List<android.media.tv.SignalingDataInfo> mSignalingDataInfoList;
    private final java.util.List<java.lang.String> mSignalingDataTypes;

    static android.media.tv.SignalingDataResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.SignalingDataResponse(parcel);
    }

    public SignalingDataResponse(int i, int i2, int i3, java.util.List<java.lang.String> list, java.util.List<android.media.tv.SignalingDataInfo> list2) {
        super(9, i, i2, i3);
        this.mSignalingDataTypes = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataTypes);
        this.mSignalingDataInfoList = list2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataInfoList);
    }

    public java.util.List<java.lang.String> getSignalingDataTypes() {
        return this.mSignalingDataTypes;
    }

    public java.util.List<android.media.tv.SignalingDataInfo> getSignalingDataInfoList() {
        return this.mSignalingDataInfoList;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeStringList(this.mSignalingDataTypes);
        parcel.writeParcelableList(this.mSignalingDataInfoList, i);
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SignalingDataResponse(android.os.Parcel parcel) {
        super(9, parcel);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        parcel.readParcelableList(arrayList2, android.media.tv.SignalingDataInfo.class.getClassLoader());
        this.mSignalingDataTypes = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataTypes);
        this.mSignalingDataInfoList = arrayList2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataInfoList);
    }
}
