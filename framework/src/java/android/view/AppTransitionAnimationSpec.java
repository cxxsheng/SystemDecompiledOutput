package android.view;

/* loaded from: classes4.dex */
public class AppTransitionAnimationSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.AppTransitionAnimationSpec> CREATOR = new android.os.Parcelable.Creator<android.view.AppTransitionAnimationSpec>() { // from class: android.view.AppTransitionAnimationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.AppTransitionAnimationSpec createFromParcel(android.os.Parcel parcel) {
            return new android.view.AppTransitionAnimationSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.AppTransitionAnimationSpec[] newArray(int i) {
            return new android.view.AppTransitionAnimationSpec[i];
        }
    };
    public final android.hardware.HardwareBuffer buffer;
    public final android.graphics.Rect rect;
    public final int taskId;

    public AppTransitionAnimationSpec(int i, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect) {
        this.taskId = i;
        this.rect = rect;
        this.buffer = hardwareBuffer;
    }

    public AppTransitionAnimationSpec(android.os.Parcel parcel) {
        this.taskId = parcel.readInt();
        this.rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.buffer = (android.hardware.HardwareBuffer) parcel.readTypedObject(android.hardware.HardwareBuffer.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeTypedObject(this.rect, 0);
        parcel.writeTypedObject(this.buffer, 0);
    }

    public java.lang.String toString() {
        return "{taskId: " + this.taskId + ", buffer: " + this.buffer + ", rect: " + this.rect + "}";
    }
}
