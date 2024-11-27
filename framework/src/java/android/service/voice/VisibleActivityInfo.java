package android.service.voice;

/* loaded from: classes3.dex */
public final class VisibleActivityInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.VisibleActivityInfo> CREATOR = new android.os.Parcelable.Creator<android.service.voice.VisibleActivityInfo>() { // from class: android.service.voice.VisibleActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisibleActivityInfo[] newArray(int i) {
            return new android.service.voice.VisibleActivityInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisibleActivityInfo createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.VisibleActivityInfo(parcel);
        }
    };
    public static final int TYPE_ACTIVITY_ADDED = 1;
    public static final int TYPE_ACTIVITY_REMOVED = 2;
    private final android.os.IBinder mAssistToken;
    private final int mTaskId;

    public VisibleActivityInfo(int i, android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder);
        this.mTaskId = i;
        this.mAssistToken = iBinder;
    }

    public android.service.voice.VoiceInteractionSession.ActivityId getActivityId() {
        return new android.service.voice.VoiceInteractionSession.ActivityId(this.mTaskId, this.mAssistToken);
    }

    public java.lang.String toString() {
        return "VisibleActivityInfo { taskId = " + this.mTaskId + ", assistToken = " + this.mAssistToken + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.voice.VisibleActivityInfo visibleActivityInfo = (android.service.voice.VisibleActivityInfo) obj;
        if (this.mTaskId == visibleActivityInfo.mTaskId && java.util.Objects.equals(this.mAssistToken, visibleActivityInfo.mAssistToken)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mTaskId + 31) * 31) + java.util.Objects.hashCode(this.mAssistToken);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mAssistToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisibleActivityInfo(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        this.mTaskId = readInt;
        this.mAssistToken = readStrongBinder;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAssistToken);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
