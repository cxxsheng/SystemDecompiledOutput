package android.view;

/* loaded from: classes4.dex */
public class InsetsSource implements android.os.Parcelable {
    public static final int FLAG_FORCE_CONSUMING = 4;
    public static final int FLAG_INSETS_ROUNDED_CORNER = 2;
    public static final int FLAG_SUPPRESS_SCRIM = 1;
    static final int SIDE_BOTTOM = 4;
    static final int SIDE_LEFT = 1;
    static final int SIDE_NONE = 0;
    static final int SIDE_RIGHT = 3;
    static final int SIDE_TOP = 2;
    static final int SIDE_UNKNOWN = 5;
    private android.graphics.Rect[] mBoundingRects;
    private int mFlags;
    private final android.graphics.Rect mFrame;
    private final int mId;
    private int mSideHint;
    private final android.graphics.Rect mTmpBoundingRect;
    private final android.graphics.Rect mTmpFrame;
    private final int mType;
    private boolean mVisible;
    private android.graphics.Rect mVisibleFrame;
    public static final int ID_IME = createId(null, 0, android.view.WindowInsets.Type.ime());
    public static final int ID_IME_CAPTION_BAR = createId(null, 1, android.view.WindowInsets.Type.captionBar());
    private static final android.graphics.Rect[] NO_BOUNDING_RECTS = new android.graphics.Rect[0];
    public static final android.os.Parcelable.Creator<android.view.InsetsSource> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsSource>() { // from class: android.view.InsetsSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsSource createFromParcel(android.os.Parcel parcel) {
            return new android.view.InsetsSource(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsSource[] newArray(int i) {
            return new android.view.InsetsSource[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InternalInsetsSide {
    }

    public InsetsSource(int i, int i2) {
        this.mSideHint = 0;
        this.mTmpFrame = new android.graphics.Rect();
        this.mTmpBoundingRect = new android.graphics.Rect();
        this.mId = i;
        this.mType = i2;
        this.mFrame = new android.graphics.Rect();
        this.mVisible = (android.view.WindowInsets.Type.defaultVisible() & i2) != 0;
    }

    public InsetsSource(android.view.InsetsSource insetsSource) {
        android.graphics.Rect rect;
        this.mSideHint = 0;
        this.mTmpFrame = new android.graphics.Rect();
        this.mTmpBoundingRect = new android.graphics.Rect();
        this.mId = insetsSource.mId;
        this.mType = insetsSource.mType;
        this.mFrame = new android.graphics.Rect(insetsSource.mFrame);
        this.mVisible = insetsSource.mVisible;
        if (insetsSource.mVisibleFrame != null) {
            rect = new android.graphics.Rect(insetsSource.mVisibleFrame);
        } else {
            rect = null;
        }
        this.mVisibleFrame = rect;
        this.mFlags = insetsSource.mFlags;
        this.mSideHint = insetsSource.mSideHint;
        this.mBoundingRects = insetsSource.mBoundingRects != null ? (android.graphics.Rect[]) insetsSource.mBoundingRects.clone() : null;
    }

    public void set(android.view.InsetsSource insetsSource) {
        android.graphics.Rect rect;
        this.mFrame.set(insetsSource.mFrame);
        this.mVisible = insetsSource.mVisible;
        if (insetsSource.mVisibleFrame != null) {
            rect = new android.graphics.Rect(insetsSource.mVisibleFrame);
        } else {
            rect = null;
        }
        this.mVisibleFrame = rect;
        this.mFlags = insetsSource.mFlags;
        this.mSideHint = insetsSource.mSideHint;
        this.mBoundingRects = insetsSource.mBoundingRects != null ? (android.graphics.Rect[]) insetsSource.mBoundingRects.clone() : null;
    }

    public android.view.InsetsSource setFrame(int i, int i2, int i3, int i4) {
        this.mFrame.set(i, i2, i3, i4);
        return this;
    }

    public android.view.InsetsSource setFrame(android.graphics.Rect rect) {
        this.mFrame.set(rect);
        return this;
    }

    public android.view.InsetsSource setVisibleFrame(android.graphics.Rect rect) {
        this.mVisibleFrame = rect != null ? new android.graphics.Rect(rect) : null;
        return this;
    }

    public android.view.InsetsSource setVisible(boolean z) {
        this.mVisible = z;
        return this;
    }

    public android.view.InsetsSource setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public android.view.InsetsSource setFlags(int i, int i2) {
        this.mFlags = (i & i2) | (this.mFlags & (~i2));
        return this;
    }

    public android.view.InsetsSource updateSideHint(android.graphics.Rect rect) {
        this.mSideHint = getInsetSide(calculateInsets(rect, this.mFrame, true));
        return this;
    }

    public android.view.InsetsSource setBoundingRects(android.graphics.Rect[] rectArr) {
        this.mBoundingRects = rectArr != null ? (android.graphics.Rect[]) rectArr.clone() : null;
        return this;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public android.graphics.Rect getFrame() {
        return this.mFrame;
    }

    public android.graphics.Rect getVisibleFrame() {
        return this.mVisibleFrame;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public android.graphics.Rect[] getBoundingRects() {
        return this.mBoundingRects;
    }

    public android.graphics.Insets calculateInsets(android.graphics.Rect rect, boolean z) {
        return calculateInsets(rect, this.mFrame, z);
    }

    public android.graphics.Insets calculateVisibleInsets(android.graphics.Rect rect) {
        return calculateInsets(rect, this.mVisibleFrame != null ? this.mVisibleFrame : this.mFrame, false);
    }

    private android.graphics.Insets calculateInsets(android.graphics.Rect rect, android.graphics.Rect rect2, boolean z) {
        boolean intersect;
        if (!z && !this.mVisible) {
            return android.graphics.Insets.NONE;
        }
        if (getType() == android.view.WindowInsets.Type.captionBar()) {
            if (getId() == ID_IME_CAPTION_BAR) {
                return android.graphics.Insets.of(0, 0, 0, rect2.height());
            }
            return android.graphics.Insets.of(0, rect2.height(), 0, 0);
        }
        if (rect.isEmpty()) {
            intersect = getIntersection(rect2, rect, this.mTmpFrame);
        } else {
            intersect = this.mTmpFrame.setIntersect(rect2, rect);
        }
        if (!intersect) {
            return android.graphics.Insets.NONE;
        }
        if (getType() == android.view.WindowInsets.Type.ime()) {
            return android.graphics.Insets.of(0, 0, 0, this.mTmpFrame.height());
        }
        if (this.mTmpFrame.equals(rect)) {
            switch (this.mSideHint) {
                case 2:
                    return android.graphics.Insets.of(0, this.mTmpFrame.height(), 0, 0);
                case 3:
                    return android.graphics.Insets.of(0, 0, this.mTmpFrame.width(), 0);
                case 4:
                    return android.graphics.Insets.of(0, 0, 0, this.mTmpFrame.height());
                default:
                    return android.graphics.Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
        }
        if (this.mTmpFrame.width() == rect.width()) {
            if (this.mTmpFrame.top == rect.top) {
                return android.graphics.Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
            if (this.mTmpFrame.bottom == rect.bottom) {
                return android.graphics.Insets.of(0, 0, 0, this.mTmpFrame.height());
            }
            if (this.mTmpFrame.top == 0) {
                return android.graphics.Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
        } else if (this.mTmpFrame.height() == rect.height()) {
            if (this.mTmpFrame.left == rect.left) {
                return android.graphics.Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
            if (this.mTmpFrame.right == rect.right) {
                return android.graphics.Insets.of(0, 0, this.mTmpFrame.width(), 0);
            }
        }
        return android.graphics.Insets.NONE;
    }

    public android.graphics.Rect[] calculateBoundingRects(android.graphics.Rect rect, boolean z) {
        if (!z && !this.mVisible) {
            return NO_BOUNDING_RECTS;
        }
        android.graphics.Rect frame = getFrame();
        if (this.mBoundingRects == null) {
            if (this.mTmpBoundingRect.setIntersect(frame, rect)) {
                return new android.graphics.Rect[]{new android.graphics.Rect(this.mTmpBoundingRect)};
            }
            return NO_BOUNDING_RECTS;
        }
        if (getType() == android.view.WindowInsets.Type.captionBar()) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.graphics.Rect rect2 : this.mBoundingRects) {
                int height = frame.height();
                this.mTmpBoundingRect.set(rect2);
                if (getId() == ID_IME_CAPTION_BAR) {
                    this.mTmpBoundingRect.offset(0, rect.height() - height);
                }
                arrayList.add(new android.graphics.Rect(this.mTmpBoundingRect));
            }
            return (android.graphics.Rect[]) arrayList.toArray(new android.graphics.Rect[arrayList.size()]);
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (android.graphics.Rect rect3 : this.mBoundingRects) {
            if (this.mTmpBoundingRect.setIntersect(new android.graphics.Rect(rect3.left + frame.left, rect3.top + frame.top, rect3.right + frame.left, rect3.bottom + frame.top), rect)) {
                arrayList2.add(new android.graphics.Rect(this.mTmpBoundingRect.left - rect.left, this.mTmpBoundingRect.top - rect.top, this.mTmpBoundingRect.right - rect.left, this.mTmpBoundingRect.bottom - rect.top));
            }
        }
        if (arrayList2.isEmpty()) {
            return NO_BOUNDING_RECTS;
        }
        return (android.graphics.Rect[]) arrayList2.toArray(new android.graphics.Rect[arrayList2.size()]);
    }

    private static boolean getIntersection(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (rect.left <= rect2.right && rect2.left <= rect.right && rect.top <= rect2.bottom && rect2.top <= rect.bottom) {
            rect3.left = java.lang.Math.max(rect.left, rect2.left);
            rect3.top = java.lang.Math.max(rect.top, rect2.top);
            rect3.right = java.lang.Math.min(rect.right, rect2.right);
            rect3.bottom = java.lang.Math.min(rect.bottom, rect2.bottom);
            return true;
        }
        rect3.setEmpty();
        return false;
    }

    static int getInsetSide(android.graphics.Insets insets) {
        if (android.graphics.Insets.NONE.equals(insets)) {
            return 0;
        }
        if (insets.left != 0) {
            return 1;
        }
        if (insets.top != 0) {
            return 2;
        }
        if (insets.right != 0) {
            return 3;
        }
        if (insets.bottom != 0) {
            return 4;
        }
        return 5;
    }

    static java.lang.String sideToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "LEFT";
            case 2:
                return "TOP";
            case 3:
                return "RIGHT";
            case 4:
                return "BOTTOM";
            default:
                return "UNKNOWN:" + i;
        }
    }

    public static int createId(java.lang.Object obj, int i, int i2) {
        if (i < 0 || i >= 2048) {
            throw new java.lang.IllegalArgumentException();
        }
        return ((java.lang.System.identityHashCode(obj) % 65536) << 16) + (i << 5) + android.view.WindowInsets.Type.indexOf(i2);
    }

    public static int getIndex(int i) {
        return (i & 65535) >> 5;
    }

    public static int getType(int i) {
        return 1 << (i & 31);
    }

    public static java.lang.String flagsToString(int i) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if ((i & 1) != 0) {
            stringJoiner.add("SUPPRESS_SCRIM");
        }
        if ((i & 2) != 0) {
            stringJoiner.add("INSETS_ROUNDED_CORNER");
        }
        if ((i & 4) != 0) {
            stringJoiner.add("FORCE_CONSUMING");
        }
        return stringJoiner.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (!android.os.Flags.androidOsBuildVanillaIceCream()) {
            protoOutputStream.write(1138166333441L, android.view.WindowInsets.Type.toString(this.mType));
        }
        this.mFrame.dumpDebug(protoOutputStream, 1146756268034L);
        if (this.mVisibleFrame != null) {
            this.mVisibleFrame.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.write(1133871366148L, this.mVisible);
        protoOutputStream.write(1120986464261L, this.mType);
        protoOutputStream.end(start);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("InsetsSource id=");
        printWriter.print(java.lang.Integer.toHexString(this.mId));
        printWriter.print(" type=");
        printWriter.print(android.view.WindowInsets.Type.toString(this.mType));
        printWriter.print(" frame=");
        printWriter.print(this.mFrame.toShortString());
        if (this.mVisibleFrame != null) {
            printWriter.print(" visibleFrame=");
            printWriter.print(this.mVisibleFrame.toShortString());
        }
        printWriter.print(" visible=");
        printWriter.print(this.mVisible);
        printWriter.print(" flags=");
        printWriter.print(flagsToString(this.mFlags));
        printWriter.print(" sideHint=");
        printWriter.print(sideToString(this.mSideHint));
        printWriter.print(" boundingRects=");
        printWriter.print(java.util.Arrays.toString(this.mBoundingRects));
        printWriter.println();
    }

    public boolean equals(java.lang.Object obj) {
        return equals(obj, false);
    }

    public boolean equals(java.lang.Object obj, boolean z) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.InsetsSource insetsSource = (android.view.InsetsSource) obj;
        if (this.mId != insetsSource.mId || this.mType != insetsSource.mType || this.mVisible != insetsSource.mVisible || this.mFlags != insetsSource.mFlags || this.mSideHint != insetsSource.mSideHint) {
            return false;
        }
        if (z && !this.mVisible && this.mType == android.view.WindowInsets.Type.ime()) {
            return true;
        }
        if (!java.util.Objects.equals(this.mVisibleFrame, insetsSource.mVisibleFrame) || !this.mFrame.equals(insetsSource.mFrame)) {
            return false;
        }
        return java.util.Arrays.equals(this.mBoundingRects, insetsSource.mBoundingRects);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mType), this.mFrame, this.mVisibleFrame, java.lang.Boolean.valueOf(this.mVisible), java.lang.Integer.valueOf(this.mFlags), java.lang.Integer.valueOf(this.mSideHint), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mBoundingRects)));
    }

    public InsetsSource(android.os.Parcel parcel) {
        this.mSideHint = 0;
        this.mTmpFrame = new android.graphics.Rect();
        this.mTmpBoundingRect = new android.graphics.Rect();
        this.mId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mFrame = android.graphics.Rect.CREATOR.createFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mVisibleFrame = android.graphics.Rect.CREATOR.createFromParcel(parcel);
        } else {
            this.mVisibleFrame = null;
        }
        this.mVisible = parcel.readBoolean();
        this.mFlags = parcel.readInt();
        this.mSideHint = parcel.readInt();
        this.mBoundingRects = (android.graphics.Rect[]) parcel.createTypedArray(android.graphics.Rect.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        this.mFrame.writeToParcel(parcel, 0);
        if (this.mVisibleFrame != null) {
            parcel.writeInt(1);
            this.mVisibleFrame.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mVisible);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSideHint);
        parcel.writeTypedArray(this.mBoundingRects, i);
    }

    public java.lang.String toString() {
        return "InsetsSource: {" + java.lang.Integer.toHexString(this.mId) + " mType=" + android.view.WindowInsets.Type.toString(this.mType) + " mFrame=" + this.mFrame.toShortString() + " mVisible=" + this.mVisible + " mFlags=" + flagsToString(this.mFlags) + " mSideHint=" + sideToString(this.mSideHint) + " mBoundingRects=" + java.util.Arrays.toString(this.mBoundingRects) + "}";
    }
}
