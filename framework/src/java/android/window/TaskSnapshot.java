package android.window;

/* loaded from: classes4.dex */
public class TaskSnapshot implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskSnapshot> CREATOR = new android.os.Parcelable.Creator<android.window.TaskSnapshot>() { // from class: android.window.TaskSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskSnapshot createFromParcel(android.os.Parcel parcel) {
            return new android.window.TaskSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskSnapshot[] newArray(int i) {
            return new android.window.TaskSnapshot[i];
        }
    };
    private final int mAppearance;
    private final long mCaptureTime;
    private final android.graphics.ColorSpace mColorSpace;
    private final android.graphics.Rect mContentInsets;
    private final boolean mHasImeSurface;
    private final long mId;
    private final boolean mIsLowResolution;
    private final boolean mIsRealSnapshot;
    private final boolean mIsTranslucent;
    private final android.graphics.Rect mLetterboxInsets;
    private final int mOrientation;
    private final int mRotation;
    private final android.hardware.HardwareBuffer mSnapshot;
    private final android.graphics.Point mTaskSize;
    private final android.content.ComponentName mTopActivityComponent;
    private final int mWindowingMode;

    public TaskSnapshot(long j, long j2, android.content.ComponentName componentName, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace, int i, int i2, android.graphics.Point point, android.graphics.Rect rect, android.graphics.Rect rect2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4) {
        this.mId = j;
        this.mCaptureTime = j2;
        this.mTopActivityComponent = componentName;
        this.mSnapshot = hardwareBuffer;
        this.mColorSpace = colorSpace.getId() < 0 ? android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB) : colorSpace;
        this.mOrientation = i;
        this.mRotation = i2;
        this.mTaskSize = new android.graphics.Point(point);
        this.mContentInsets = new android.graphics.Rect(rect);
        this.mLetterboxInsets = new android.graphics.Rect(rect2);
        this.mIsLowResolution = z;
        this.mIsRealSnapshot = z2;
        this.mWindowingMode = i3;
        this.mAppearance = i4;
        this.mIsTranslucent = z3;
        this.mHasImeSurface = z4;
    }

    private TaskSnapshot(android.os.Parcel parcel) {
        android.graphics.ColorSpace colorSpace;
        this.mId = parcel.readLong();
        this.mCaptureTime = android.os.SystemClock.elapsedRealtimeNanos();
        this.mTopActivityComponent = android.content.ComponentName.readFromParcel(parcel);
        this.mSnapshot = (android.hardware.HardwareBuffer) parcel.readTypedObject(android.hardware.HardwareBuffer.CREATOR);
        int readInt = parcel.readInt();
        if (readInt >= 0 && readInt < android.graphics.ColorSpace.Named.values().length) {
            colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[readInt]);
        } else {
            colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
        }
        this.mColorSpace = colorSpace;
        this.mOrientation = parcel.readInt();
        this.mRotation = parcel.readInt();
        this.mTaskSize = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
        this.mContentInsets = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mLetterboxInsets = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mIsLowResolution = parcel.readBoolean();
        this.mIsRealSnapshot = parcel.readBoolean();
        this.mWindowingMode = parcel.readInt();
        this.mAppearance = parcel.readInt();
        this.mIsTranslucent = parcel.readBoolean();
        this.mHasImeSurface = parcel.readBoolean();
    }

    public long getId() {
        return this.mId;
    }

    public long getCaptureTime() {
        return this.mCaptureTime;
    }

    public android.content.ComponentName getTopActivityComponent() {
        return this.mTopActivityComponent;
    }

    public android.graphics.GraphicBuffer getSnapshot() {
        return android.graphics.GraphicBuffer.createFromHardwareBuffer(this.mSnapshot);
    }

    public android.hardware.HardwareBuffer getHardwareBuffer() {
        return this.mSnapshot;
    }

    public android.graphics.ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public android.graphics.Point getTaskSize() {
        return this.mTaskSize;
    }

    public android.graphics.Rect getContentInsets() {
        return this.mContentInsets;
    }

    public android.graphics.Rect getLetterboxInsets() {
        return this.mLetterboxInsets;
    }

    public boolean isLowResolution() {
        return this.mIsLowResolution;
    }

    public boolean isRealSnapshot() {
        return this.mIsRealSnapshot;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    public boolean hasImeSurface() {
        return this.mHasImeSurface;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public int getAppearance() {
        return this.mAppearance;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        android.content.ComponentName.writeToParcel(this.mTopActivityComponent, parcel);
        parcel.writeTypedObject((this.mSnapshot == null || this.mSnapshot.isClosed()) ? null : this.mSnapshot, 0);
        parcel.writeInt(this.mColorSpace.getId());
        parcel.writeInt(this.mOrientation);
        parcel.writeInt(this.mRotation);
        parcel.writeTypedObject(this.mTaskSize, 0);
        parcel.writeTypedObject(this.mContentInsets, 0);
        parcel.writeTypedObject(this.mLetterboxInsets, 0);
        parcel.writeBoolean(this.mIsLowResolution);
        parcel.writeBoolean(this.mIsRealSnapshot);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeInt(this.mAppearance);
        parcel.writeBoolean(this.mIsTranslucent);
        parcel.writeBoolean(this.mHasImeSurface);
    }

    public java.lang.String toString() {
        return "TaskSnapshot{ mId=" + this.mId + " mCaptureTime=" + this.mCaptureTime + " mTopActivityComponent=" + this.mTopActivityComponent.flattenToShortString() + " mSnapshot=" + this.mSnapshot + " (" + (this.mSnapshot != null ? this.mSnapshot.getWidth() : 0) + "x" + (this.mSnapshot != null ? this.mSnapshot.getHeight() : 0) + ") mColorSpace=" + this.mColorSpace.toString() + " mOrientation=" + this.mOrientation + " mRotation=" + this.mRotation + " mTaskSize=" + this.mTaskSize.toString() + " mContentInsets=" + this.mContentInsets.toShortString() + " mLetterboxInsets=" + this.mLetterboxInsets.toShortString() + " mIsLowResolution=" + this.mIsLowResolution + " mIsRealSnapshot=" + this.mIsRealSnapshot + " mWindowingMode=" + this.mWindowingMode + " mAppearance=" + this.mAppearance + " mIsTranslucent=" + this.mIsTranslucent + " mHasImeSurface=" + this.mHasImeSurface;
    }

    public static final class Builder {
        private int mAppearance;
        private long mCaptureTime;
        private android.graphics.ColorSpace mColorSpace;
        private android.graphics.Rect mContentInsets;
        private boolean mHasImeSurface;
        private long mId;
        private boolean mIsRealSnapshot;
        private boolean mIsTranslucent;
        private android.graphics.Rect mLetterboxInsets;
        private int mOrientation;
        private int mPixelFormat;
        private int mRotation;
        private android.hardware.HardwareBuffer mSnapshot;
        private android.graphics.Point mTaskSize;
        private android.content.ComponentName mTopActivity;
        private int mWindowingMode;

        public android.window.TaskSnapshot.Builder setId(long j) {
            this.mId = j;
            return this;
        }

        public android.window.TaskSnapshot.Builder setCaptureTime(long j) {
            this.mCaptureTime = j;
            return this;
        }

        public android.window.TaskSnapshot.Builder setTopActivityComponent(android.content.ComponentName componentName) {
            this.mTopActivity = componentName;
            return this;
        }

        public android.window.TaskSnapshot.Builder setSnapshot(android.hardware.HardwareBuffer hardwareBuffer) {
            this.mSnapshot = hardwareBuffer;
            return this;
        }

        public android.window.TaskSnapshot.Builder setColorSpace(android.graphics.ColorSpace colorSpace) {
            this.mColorSpace = colorSpace;
            return this;
        }

        public android.window.TaskSnapshot.Builder setOrientation(int i) {
            this.mOrientation = i;
            return this;
        }

        public android.window.TaskSnapshot.Builder setRotation(int i) {
            this.mRotation = i;
            return this;
        }

        public android.window.TaskSnapshot.Builder setTaskSize(android.graphics.Point point) {
            this.mTaskSize = point;
            return this;
        }

        public android.window.TaskSnapshot.Builder setContentInsets(android.graphics.Rect rect) {
            this.mContentInsets = rect;
            return this;
        }

        public android.window.TaskSnapshot.Builder setLetterboxInsets(android.graphics.Rect rect) {
            this.mLetterboxInsets = rect;
            return this;
        }

        public android.window.TaskSnapshot.Builder setIsRealSnapshot(boolean z) {
            this.mIsRealSnapshot = z;
            return this;
        }

        public android.window.TaskSnapshot.Builder setWindowingMode(int i) {
            this.mWindowingMode = i;
            return this;
        }

        public android.window.TaskSnapshot.Builder setAppearance(int i) {
            this.mAppearance = i;
            return this;
        }

        public android.window.TaskSnapshot.Builder setIsTranslucent(boolean z) {
            this.mIsTranslucent = z;
            return this;
        }

        public android.window.TaskSnapshot.Builder setHasImeSurface(boolean z) {
            this.mHasImeSurface = z;
            return this;
        }

        public int getPixelFormat() {
            return this.mPixelFormat;
        }

        public android.window.TaskSnapshot.Builder setPixelFormat(int i) {
            this.mPixelFormat = i;
            return this;
        }

        public android.window.TaskSnapshot build() {
            return new android.window.TaskSnapshot(this.mId, this.mCaptureTime, this.mTopActivity, this.mSnapshot, this.mColorSpace, this.mOrientation, this.mRotation, this.mTaskSize, this.mContentInsets, this.mLetterboxInsets, false, this.mIsRealSnapshot, this.mWindowingMode, this.mAppearance, this.mIsTranslucent, this.mHasImeSurface);
        }
    }
}
