package android.window;

/* loaded from: classes4.dex */
public final class DisplayAreaAppearedInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.DisplayAreaAppearedInfo> CREATOR = new android.os.Parcelable.Creator<android.window.DisplayAreaAppearedInfo>() { // from class: android.window.DisplayAreaAppearedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.DisplayAreaAppearedInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.DisplayAreaAppearedInfo((android.window.DisplayAreaInfo) parcel.readTypedObject(android.window.DisplayAreaInfo.CREATOR), (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.DisplayAreaAppearedInfo[] newArray(int i) {
            return new android.window.DisplayAreaAppearedInfo[i];
        }
    };
    private final android.window.DisplayAreaInfo mDisplayAreaInfo;
    private final android.view.SurfaceControl mLeash;

    public DisplayAreaAppearedInfo(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) {
        this.mDisplayAreaInfo = displayAreaInfo;
        this.mLeash = surfaceControl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mDisplayAreaInfo, i);
        parcel.writeTypedObject(this.mLeash, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.window.DisplayAreaInfo getDisplayAreaInfo() {
        return this.mDisplayAreaInfo;
    }

    public android.view.SurfaceControl getLeash() {
        return this.mLeash;
    }
}
