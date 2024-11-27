package android.view;

/* loaded from: classes4.dex */
public class InsetsSourceControl implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.InsetsSourceControl> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsSourceControl>() { // from class: android.view.InsetsSourceControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsSourceControl createFromParcel(android.os.Parcel parcel) {
            return new android.view.InsetsSourceControl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsSourceControl[] newArray(int i) {
            return new android.view.InsetsSourceControl[i];
        }
    };
    private final int mId;
    private final boolean mInitiallyVisible;
    private android.graphics.Insets mInsetsHint;
    private final android.view.SurfaceControl mLeash;
    private int mParcelableFlags;
    private boolean mSkipAnimationOnce;
    private final android.graphics.Point mSurfacePosition;
    private final int mType;

    public InsetsSourceControl(int i, int i2, android.view.SurfaceControl surfaceControl, boolean z, android.graphics.Point point, android.graphics.Insets insets) {
        this.mId = i;
        this.mType = i2;
        this.mLeash = surfaceControl;
        this.mInitiallyVisible = z;
        this.mSurfacePosition = point;
        this.mInsetsHint = insets;
    }

    public InsetsSourceControl(android.view.InsetsSourceControl insetsSourceControl) {
        this.mId = insetsSourceControl.mId;
        this.mType = insetsSourceControl.mType;
        if (insetsSourceControl.mLeash != null) {
            this.mLeash = new android.view.SurfaceControl(insetsSourceControl.mLeash, "InsetsSourceControl");
        } else {
            this.mLeash = null;
        }
        this.mInitiallyVisible = insetsSourceControl.mInitiallyVisible;
        this.mSurfacePosition = new android.graphics.Point(insetsSourceControl.mSurfacePosition);
        this.mInsetsHint = insetsSourceControl.mInsetsHint;
        this.mSkipAnimationOnce = insetsSourceControl.getAndClearSkipAnimationOnce();
    }

    public InsetsSourceControl(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mLeash = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        this.mInitiallyVisible = parcel.readBoolean();
        this.mSurfacePosition = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
        this.mInsetsHint = (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR);
        this.mSkipAnimationOnce = parcel.readBoolean();
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public android.view.SurfaceControl getLeash() {
        return this.mLeash;
    }

    public boolean isInitiallyVisible() {
        return this.mInitiallyVisible;
    }

    public boolean setSurfacePosition(int i, int i2) {
        if (this.mSurfacePosition.equals(i, i2)) {
            return false;
        }
        this.mSurfacePosition.set(i, i2);
        return true;
    }

    public android.graphics.Point getSurfacePosition() {
        return this.mSurfacePosition;
    }

    public void setInsetsHint(android.graphics.Insets insets) {
        this.mInsetsHint = insets;
    }

    public void setInsetsHint(int i, int i2, int i3, int i4) {
        this.mInsetsHint = android.graphics.Insets.of(i, i2, i3, i4);
    }

    public android.graphics.Insets getInsetsHint() {
        return this.mInsetsHint;
    }

    public void setSkipAnimationOnce(boolean z) {
        this.mSkipAnimationOnce = z;
    }

    public boolean getAndClearSkipAnimationOnce() {
        boolean z = this.mSkipAnimationOnce;
        this.mSkipAnimationOnce = false;
        return z;
    }

    public void setParcelableFlags(int i) {
        this.mParcelableFlags = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeTypedObject(this.mLeash, this.mParcelableFlags);
        parcel.writeBoolean(this.mInitiallyVisible);
        parcel.writeTypedObject(this.mSurfacePosition, this.mParcelableFlags);
        parcel.writeTypedObject(this.mInsetsHint, this.mParcelableFlags);
        parcel.writeBoolean(this.mSkipAnimationOnce);
    }

    public void release(java.util.function.Consumer<android.view.SurfaceControl> consumer) {
        if (this.mLeash != null) {
            consumer.accept(this.mLeash);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.InsetsSourceControl insetsSourceControl = (android.view.InsetsSourceControl) obj;
        android.view.SurfaceControl surfaceControl = insetsSourceControl.mLeash;
        if (this.mId == insetsSourceControl.mId && this.mType == insetsSourceControl.mType && ((this.mLeash == surfaceControl || (this.mLeash != null && surfaceControl != null && this.mLeash.isSameSurface(surfaceControl))) && this.mInitiallyVisible == insetsSourceControl.mInitiallyVisible && this.mSurfacePosition.equals(insetsSourceControl.mSurfacePosition) && this.mInsetsHint.equals(insetsSourceControl.mInsetsHint) && this.mSkipAnimationOnce == insetsSourceControl.mSkipAnimationOnce)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mType), this.mLeash, java.lang.Boolean.valueOf(this.mInitiallyVisible), this.mSurfacePosition, this.mInsetsHint, java.lang.Boolean.valueOf(this.mSkipAnimationOnce));
    }

    public java.lang.String toString() {
        return "InsetsSourceControl: {" + java.lang.Integer.toHexString(this.mId) + " mType=" + android.view.WindowInsets.Type.toString(this.mType) + (this.mInitiallyVisible ? " initiallyVisible" : "") + " mSurfacePosition=" + this.mSurfacePosition + " mInsetsHint=" + this.mInsetsHint + (this.mSkipAnimationOnce ? " skipAnimationOnce" : "") + "}";
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("InsetsSourceControl mId=");
        printWriter.print(java.lang.Integer.toHexString(this.mId));
        printWriter.print(" mType=");
        printWriter.print(android.view.WindowInsets.Type.toString(this.mType));
        printWriter.print(" mLeash=");
        printWriter.print(this.mLeash);
        printWriter.print(" mInitiallyVisible=");
        printWriter.print(this.mInitiallyVisible);
        printWriter.print(" mSurfacePosition=");
        printWriter.print(this.mSurfacePosition);
        printWriter.print(" mInsetsHint=");
        printWriter.print(this.mInsetsHint);
        printWriter.print(" mSkipAnimationOnce=");
        printWriter.print(this.mSkipAnimationOnce);
        printWriter.println();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, this.mSurfacePosition.x);
        protoOutputStream.write(1120986464258L, this.mSurfacePosition.y);
        protoOutputStream.end(start2);
        if (this.mLeash != null) {
            this.mLeash.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.write(1120986464260L, this.mType);
        protoOutputStream.end(start);
    }

    public static class Array implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.InsetsSourceControl.Array> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsSourceControl.Array>() { // from class: android.view.InsetsSourceControl.Array.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.InsetsSourceControl.Array createFromParcel(android.os.Parcel parcel) {
                return new android.view.InsetsSourceControl.Array(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.InsetsSourceControl.Array[] newArray(int i) {
                return new android.view.InsetsSourceControl.Array[i];
            }
        };
        private android.view.InsetsSourceControl[] mControls;

        public Array() {
        }

        public Array(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        public void set(android.view.InsetsSourceControl[] insetsSourceControlArr) {
            this.mControls = insetsSourceControlArr;
        }

        public android.view.InsetsSourceControl[] get() {
            return this.mControls;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.mControls = (android.view.InsetsSourceControl[]) parcel.createTypedArray(android.view.InsetsSourceControl.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedArray(this.mControls, i);
        }
    }
}
