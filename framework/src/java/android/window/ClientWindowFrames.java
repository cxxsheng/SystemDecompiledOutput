package android.window;

/* loaded from: classes4.dex */
public class ClientWindowFrames implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.ClientWindowFrames> CREATOR = new android.os.Parcelable.Creator<android.window.ClientWindowFrames>() { // from class: android.window.ClientWindowFrames.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ClientWindowFrames createFromParcel(android.os.Parcel parcel) {
            return new android.window.ClientWindowFrames(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ClientWindowFrames[] newArray(int i) {
            return new android.window.ClientWindowFrames[i];
        }
    };
    public android.graphics.Rect attachedFrame;
    public float compatScale;
    public final android.graphics.Rect displayFrame;
    public final android.graphics.Rect frame;
    public boolean isParentFrameClippedByDisplayCutout;
    public final android.graphics.Rect parentFrame;

    public ClientWindowFrames() {
        this.frame = new android.graphics.Rect();
        this.displayFrame = new android.graphics.Rect();
        this.parentFrame = new android.graphics.Rect();
        this.compatScale = 1.0f;
    }

    public ClientWindowFrames(android.window.ClientWindowFrames clientWindowFrames) {
        this.frame = new android.graphics.Rect();
        this.displayFrame = new android.graphics.Rect();
        this.parentFrame = new android.graphics.Rect();
        this.compatScale = 1.0f;
        this.frame.set(clientWindowFrames.frame);
        this.displayFrame.set(clientWindowFrames.displayFrame);
        this.parentFrame.set(clientWindowFrames.parentFrame);
        if (clientWindowFrames.attachedFrame != null) {
            this.attachedFrame = new android.graphics.Rect(clientWindowFrames.attachedFrame);
        }
        this.isParentFrameClippedByDisplayCutout = clientWindowFrames.isParentFrameClippedByDisplayCutout;
        this.compatScale = clientWindowFrames.compatScale;
    }

    private ClientWindowFrames(android.os.Parcel parcel) {
        this.frame = new android.graphics.Rect();
        this.displayFrame = new android.graphics.Rect();
        this.parentFrame = new android.graphics.Rect();
        this.compatScale = 1.0f;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.frame.readFromParcel(parcel);
        this.displayFrame.readFromParcel(parcel);
        this.parentFrame.readFromParcel(parcel);
        this.attachedFrame = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.isParentFrameClippedByDisplayCutout = parcel.readBoolean();
        this.compatScale = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.frame.writeToParcel(parcel, i);
        this.displayFrame.writeToParcel(parcel, i);
        this.parentFrame.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.attachedFrame, i);
        parcel.writeBoolean(this.isParentFrameClippedByDisplayCutout);
        parcel.writeFloat(this.compatScale);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
        return "ClientWindowFrames{frame=" + this.frame.toShortString(sb) + " display=" + this.displayFrame.toShortString(sb) + " parentFrame=" + this.parentFrame.toShortString(sb) + (this.attachedFrame != null ? " attachedFrame=" + this.attachedFrame.toShortString() : "") + (this.isParentFrameClippedByDisplayCutout ? " parentClippedByDisplayCutout" : "") + (this.compatScale != 1.0f ? " sizeCompatScale=" + this.compatScale : "") + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.window.ClientWindowFrames clientWindowFrames = (android.window.ClientWindowFrames) obj;
        if (this.frame.equals(clientWindowFrames.frame) && this.displayFrame.equals(clientWindowFrames.displayFrame) && this.parentFrame.equals(clientWindowFrames.parentFrame) && java.util.Objects.equals(this.attachedFrame, clientWindowFrames.attachedFrame) && this.isParentFrameClippedByDisplayCutout == clientWindowFrames.isParentFrameClippedByDisplayCutout && this.compatScale == clientWindowFrames.compatScale) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.frame, this.displayFrame, this.parentFrame, this.attachedFrame, java.lang.Boolean.valueOf(this.isParentFrameClippedByDisplayCutout), java.lang.Float.valueOf(this.compatScale));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
