package android.app;

/* loaded from: classes.dex */
public class WindowConfiguration implements android.os.Parcelable, java.lang.Comparable<android.app.WindowConfiguration> {
    public static final int ACTIVITY_TYPE_ASSISTANT = 4;
    public static final int ACTIVITY_TYPE_DREAM = 5;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    private static final int ALWAYS_ON_TOP_OFF = 2;
    private static final int ALWAYS_ON_TOP_ON = 1;
    private static final int ALWAYS_ON_TOP_UNDEFINED = 0;
    public static final android.os.Parcelable.Creator<android.app.WindowConfiguration> CREATOR = new android.os.Parcelable.Creator<android.app.WindowConfiguration>() { // from class: android.app.WindowConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WindowConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.app.WindowConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WindowConfiguration[] newArray(int i) {
            return new android.app.WindowConfiguration[i];
        }
    };
    public static final int ROTATION_UNDEFINED = -1;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_MULTI_WINDOW = 6;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static final int WINDOW_CONFIG_ACTIVITY_TYPE = 16;
    public static final int WINDOW_CONFIG_ALWAYS_ON_TOP = 32;
    public static final int WINDOW_CONFIG_APP_BOUNDS = 2;
    public static final int WINDOW_CONFIG_BOUNDS = 1;
    public static final int WINDOW_CONFIG_DISPLAY_ROTATION = 256;
    public static final int WINDOW_CONFIG_DISPLAY_WINDOWING_MODE = 128;
    public static final int WINDOW_CONFIG_MAX_BOUNDS = 4;
    public static final int WINDOW_CONFIG_ROTATION = 64;
    public static final int WINDOW_CONFIG_WINDOWING_MODE = 8;
    private int mActivityType;
    private int mAlwaysOnTop;
    private android.graphics.Rect mAppBounds;
    private final android.graphics.Rect mBounds;
    private int mDisplayRotation;
    private int mDisplayWindowingMode;
    private final android.graphics.Rect mMaxBounds;
    private int mRotation;
    private int mWindowingMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActivityType {
    }

    private @interface AlwaysOnTop {
    }

    public @interface WindowConfig {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WindowingMode {
    }

    public WindowConfiguration() {
        this.mBounds = new android.graphics.Rect();
        this.mMaxBounds = new android.graphics.Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        unset();
    }

    public WindowConfiguration(android.app.WindowConfiguration windowConfiguration) {
        this.mBounds = new android.graphics.Rect();
        this.mMaxBounds = new android.graphics.Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        setTo(windowConfiguration);
    }

    private WindowConfiguration(android.os.Parcel parcel) {
        this.mBounds = new android.graphics.Rect();
        this.mMaxBounds = new android.graphics.Rect();
        this.mDisplayRotation = -1;
        this.mRotation = -1;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mBounds.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mAppBounds, i);
        this.mMaxBounds.writeToParcel(parcel, i);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeInt(this.mActivityType);
        parcel.writeInt(this.mAlwaysOnTop);
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mDisplayWindowingMode);
        parcel.writeInt(this.mDisplayRotation);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mBounds.readFromParcel(parcel);
        this.mAppBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mMaxBounds.readFromParcel(parcel);
        this.mWindowingMode = parcel.readInt();
        this.mActivityType = parcel.readInt();
        this.mAlwaysOnTop = parcel.readInt();
        this.mRotation = parcel.readInt();
        this.mDisplayWindowingMode = parcel.readInt();
        this.mDisplayRotation = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setBounds(android.graphics.Rect rect) {
        if (rect == null) {
            this.mBounds.setEmpty();
        } else {
            this.mBounds.set(rect);
        }
    }

    public void setAppBounds(android.graphics.Rect rect) {
        if (rect == null) {
            this.mAppBounds = null;
        } else {
            setAppBounds(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void setMaxBounds(android.graphics.Rect rect) {
        if (rect == null) {
            this.mMaxBounds.setEmpty();
        } else {
            this.mMaxBounds.set(rect);
        }
    }

    public void setMaxBounds(int i, int i2, int i3, int i4) {
        this.mMaxBounds.set(i, i2, i3, i4);
    }

    public void setDisplayRotation(int i) {
        this.mDisplayRotation = i;
    }

    public void setAlwaysOnTop(boolean z) {
        this.mAlwaysOnTop = z ? 1 : 2;
    }

    public void unsetAlwaysOnTop() {
        this.mAlwaysOnTop = 0;
    }

    private void setAlwaysOnTop(int i) {
        this.mAlwaysOnTop = i;
    }

    public void setAppBounds(int i, int i2, int i3, int i4) {
        if (this.mAppBounds == null) {
            this.mAppBounds = new android.graphics.Rect();
        }
        this.mAppBounds.set(i, i2, i3, i4);
    }

    public android.graphics.Rect getAppBounds() {
        return this.mAppBounds;
    }

    public android.graphics.Rect getBounds() {
        return this.mBounds;
    }

    public android.graphics.Rect getMaxBounds() {
        return this.mMaxBounds;
    }

    public int getDisplayRotation() {
        return this.mDisplayRotation;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setWindowingMode(int i) {
        this.mWindowingMode = i;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public void setDisplayWindowingMode(int i) {
        this.mDisplayWindowingMode = i;
    }

    public int getDisplayWindowingMode() {
        return this.mDisplayWindowingMode;
    }

    public void setActivityType(int i) {
        if (this.mActivityType == i) {
            return;
        }
        if (android.app.ActivityThread.isSystem() && this.mActivityType != 0 && i != 0) {
            throw new java.lang.IllegalStateException("Can't change activity type once set: " + this + " activityType=" + activityTypeToString(i));
        }
        this.mActivityType = i;
    }

    public int getActivityType() {
        return this.mActivityType;
    }

    public void setTo(android.app.WindowConfiguration windowConfiguration) {
        setBounds(windowConfiguration.mBounds);
        setAppBounds(windowConfiguration.mAppBounds);
        setMaxBounds(windowConfiguration.mMaxBounds);
        setDisplayRotation(windowConfiguration.mDisplayRotation);
        setWindowingMode(windowConfiguration.mWindowingMode);
        setActivityType(windowConfiguration.mActivityType);
        setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        setRotation(windowConfiguration.mRotation);
        setDisplayWindowingMode(windowConfiguration.mDisplayWindowingMode);
    }

    public void unset() {
        setToDefaults();
    }

    public void setToDefaults() {
        setAppBounds(null);
        setBounds(null);
        setMaxBounds(null);
        setDisplayRotation(-1);
        setWindowingMode(0);
        setActivityType(0);
        setAlwaysOnTop(0);
        setRotation(-1);
        setDisplayWindowingMode(0);
    }

    public void scale(float f) {
        scaleBounds(f, this.mBounds);
        scaleBounds(f, this.mMaxBounds);
        if (this.mAppBounds != null) {
            scaleBounds(f, this.mAppBounds);
        }
    }

    private static void scaleBounds(float f, android.graphics.Rect rect) {
        int width = rect.width();
        int height = rect.height();
        rect.left = (int) ((rect.left * f) + 0.5f);
        rect.top = (int) ((rect.top * f) + 0.5f);
        rect.right = rect.left + ((int) ((width * f) + 0.5f));
        rect.bottom = rect.top + ((int) ((height * f) + 0.5f));
    }

    public int updateFrom(android.app.WindowConfiguration windowConfiguration) {
        int i;
        if (!windowConfiguration.mBounds.isEmpty() && !windowConfiguration.mBounds.equals(this.mBounds)) {
            setBounds(windowConfiguration.mBounds);
            i = 1;
        } else {
            i = 0;
        }
        if (windowConfiguration.mAppBounds != null && !windowConfiguration.mAppBounds.equals(this.mAppBounds)) {
            i |= 2;
            setAppBounds(windowConfiguration.mAppBounds);
        }
        if (!windowConfiguration.mMaxBounds.isEmpty() && !windowConfiguration.mMaxBounds.equals(this.mMaxBounds)) {
            i |= 4;
            setMaxBounds(windowConfiguration.mMaxBounds);
        }
        if (windowConfiguration.mWindowingMode != 0 && this.mWindowingMode != windowConfiguration.mWindowingMode) {
            i |= 8;
            setWindowingMode(windowConfiguration.mWindowingMode);
        }
        if (windowConfiguration.mActivityType != 0 && this.mActivityType != windowConfiguration.mActivityType) {
            i |= 16;
            setActivityType(windowConfiguration.mActivityType);
        }
        if (windowConfiguration.mAlwaysOnTop != 0 && this.mAlwaysOnTop != windowConfiguration.mAlwaysOnTop) {
            i |= 32;
            setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        }
        if (windowConfiguration.mRotation != -1 && windowConfiguration.mRotation != this.mRotation) {
            i |= 64;
            setRotation(windowConfiguration.mRotation);
        }
        if (windowConfiguration.mDisplayWindowingMode != 0 && this.mDisplayWindowingMode != windowConfiguration.mDisplayWindowingMode) {
            i |= 128;
            setDisplayWindowingMode(windowConfiguration.mDisplayWindowingMode);
        }
        if (windowConfiguration.mDisplayRotation != -1 && windowConfiguration.mDisplayRotation != this.mDisplayRotation) {
            int i2 = i | 256;
            setDisplayRotation(windowConfiguration.mDisplayRotation);
            return i2;
        }
        return i;
    }

    public void setTo(android.app.WindowConfiguration windowConfiguration, int i) {
        if ((i & 1) != 0) {
            setBounds(windowConfiguration.mBounds);
        }
        if ((i & 2) != 0) {
            setAppBounds(windowConfiguration.mAppBounds);
        }
        if ((i & 4) != 0) {
            setMaxBounds(windowConfiguration.mMaxBounds);
        }
        if ((i & 8) != 0) {
            setWindowingMode(windowConfiguration.mWindowingMode);
        }
        if ((i & 16) != 0) {
            setActivityType(windowConfiguration.mActivityType);
        }
        if ((i & 32) != 0) {
            setAlwaysOnTop(windowConfiguration.mAlwaysOnTop);
        }
        if ((i & 64) != 0) {
            setRotation(windowConfiguration.mRotation);
        }
        if ((i & 128) != 0) {
            setDisplayWindowingMode(windowConfiguration.mDisplayWindowingMode);
        }
        if ((i & 256) != 0) {
            setDisplayRotation(windowConfiguration.mDisplayRotation);
        }
    }

    public long diff(android.app.WindowConfiguration windowConfiguration, boolean z) {
        long j;
        if (this.mBounds.equals(windowConfiguration.mBounds)) {
            j = 0;
        } else {
            j = 1;
        }
        if ((z || windowConfiguration.mAppBounds != null) && this.mAppBounds != windowConfiguration.mAppBounds && (this.mAppBounds == null || !this.mAppBounds.equals(windowConfiguration.mAppBounds))) {
            j |= 2;
        }
        if (!this.mMaxBounds.equals(windowConfiguration.mMaxBounds)) {
            j |= 4;
        }
        if ((z || windowConfiguration.mWindowingMode != 0) && this.mWindowingMode != windowConfiguration.mWindowingMode) {
            j |= 8;
        }
        if ((z || windowConfiguration.mActivityType != 0) && this.mActivityType != windowConfiguration.mActivityType) {
            j |= 16;
        }
        if ((z || windowConfiguration.mAlwaysOnTop != 0) && this.mAlwaysOnTop != windowConfiguration.mAlwaysOnTop) {
            j |= 32;
        }
        if ((z || windowConfiguration.mRotation != -1) && this.mRotation != windowConfiguration.mRotation) {
            j |= 64;
        }
        if ((z || windowConfiguration.mDisplayWindowingMode != 0) && this.mDisplayWindowingMode != windowConfiguration.mDisplayWindowingMode) {
            j |= 128;
        }
        if ((z || windowConfiguration.mDisplayRotation != -1) && this.mDisplayRotation != windowConfiguration.mDisplayRotation) {
            return j | 256;
        }
        return j;
    }

    @Override // java.lang.Comparable
    public int compareTo(android.app.WindowConfiguration windowConfiguration) {
        if (this.mAppBounds == null && windowConfiguration.mAppBounds != null) {
            return 1;
        }
        if (this.mAppBounds != null && windowConfiguration.mAppBounds == null) {
            return -1;
        }
        if (this.mAppBounds != null && windowConfiguration.mAppBounds != null) {
            int i = this.mAppBounds.left - windowConfiguration.mAppBounds.left;
            if (i != 0) {
                return i;
            }
            int i2 = this.mAppBounds.top - windowConfiguration.mAppBounds.top;
            if (i2 != 0) {
                return i2;
            }
            int i3 = this.mAppBounds.right - windowConfiguration.mAppBounds.right;
            if (i3 != 0) {
                return i3;
            }
            int i4 = this.mAppBounds.bottom - windowConfiguration.mAppBounds.bottom;
            if (i4 != 0) {
                return i4;
            }
        }
        int i5 = this.mMaxBounds.left - windowConfiguration.mMaxBounds.left;
        if (i5 != 0) {
            return i5;
        }
        int i6 = this.mMaxBounds.top - windowConfiguration.mMaxBounds.top;
        if (i6 != 0) {
            return i6;
        }
        int i7 = this.mMaxBounds.right - windowConfiguration.mMaxBounds.right;
        if (i7 != 0) {
            return i7;
        }
        int i8 = this.mMaxBounds.bottom - windowConfiguration.mMaxBounds.bottom;
        if (i8 != 0) {
            return i8;
        }
        int i9 = this.mBounds.left - windowConfiguration.mBounds.left;
        if (i9 != 0) {
            return i9;
        }
        int i10 = this.mBounds.top - windowConfiguration.mBounds.top;
        if (i10 != 0) {
            return i10;
        }
        int i11 = this.mBounds.right - windowConfiguration.mBounds.right;
        if (i11 != 0) {
            return i11;
        }
        int i12 = this.mBounds.bottom - windowConfiguration.mBounds.bottom;
        if (i12 != 0) {
            return i12;
        }
        int i13 = this.mWindowingMode - windowConfiguration.mWindowingMode;
        if (i13 != 0) {
            return i13;
        }
        int i14 = this.mActivityType - windowConfiguration.mActivityType;
        if (i14 != 0) {
            return i14;
        }
        int i15 = this.mAlwaysOnTop - windowConfiguration.mAlwaysOnTop;
        if (i15 != 0) {
            return i15;
        }
        int i16 = this.mRotation - windowConfiguration.mRotation;
        if (i16 != 0) {
            return i16;
        }
        int i17 = this.mDisplayWindowingMode - windowConfiguration.mDisplayWindowingMode;
        return i17 != 0 ? i17 : this.mDisplayRotation - windowConfiguration.mDisplayRotation;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.app.WindowConfiguration) || compareTo((android.app.WindowConfiguration) obj) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((((((((((((((0 + java.util.Objects.hashCode(this.mAppBounds)) * 31) + java.util.Objects.hashCode(this.mBounds)) * 31) + java.util.Objects.hashCode(this.mMaxBounds)) * 31) + this.mWindowingMode) * 31) + this.mActivityType) * 31) + this.mAlwaysOnTop) * 31) + this.mRotation) * 31) + this.mDisplayWindowingMode) * 31) + this.mDisplayRotation;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("{ mBounds=").append(this.mBounds).append(" mAppBounds=").append(this.mAppBounds).append(" mMaxBounds=").append(this.mMaxBounds).append(" mDisplayRotation=");
        int i = this.mRotation;
        java.lang.String str = android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        java.lang.StringBuilder append2 = append.append(i == -1 ? android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED : android.view.Surface.rotationToString(this.mDisplayRotation)).append(" mWindowingMode=").append(windowingModeToString(this.mWindowingMode)).append(" mDisplayWindowingMode=").append(windowingModeToString(this.mDisplayWindowingMode)).append(" mActivityType=").append(activityTypeToString(this.mActivityType)).append(" mAlwaysOnTop=").append(alwaysOnTopToString(this.mAlwaysOnTop)).append(" mRotation=");
        if (this.mRotation != -1) {
            str = android.view.Surface.rotationToString(this.mRotation);
        }
        return append2.append(str).append("}").toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mAppBounds != null) {
            this.mAppBounds.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1120986464258L, this.mWindowingMode);
        protoOutputStream.write(1120986464259L, this.mActivityType);
        this.mBounds.dumpDebug(protoOutputStream, 1146756268036L);
        this.mMaxBounds.dumpDebug(protoOutputStream, 1146756268037L);
        protoOutputStream.end(start);
    }

    public void readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException {
        long start = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            try {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        this.mAppBounds = new android.graphics.Rect();
                        this.mAppBounds.readFromProto(protoInputStream, 1146756268033L);
                        break;
                    case 2:
                        this.mWindowingMode = protoInputStream.readInt(1120986464258L);
                        break;
                    case 3:
                        this.mActivityType = protoInputStream.readInt(1120986464259L);
                        break;
                    case 4:
                        this.mBounds.readFromProto(protoInputStream, 1146756268036L);
                        break;
                    case 5:
                        this.mMaxBounds.readFromProto(protoInputStream, 1146756268037L);
                        break;
                }
            } finally {
                protoInputStream.end(start);
            }
        }
    }

    public boolean hasWindowShadow() {
        return this.mWindowingMode != 6 && tasksAreFloating();
    }

    public boolean hasWindowDecorCaption() {
        return this.mActivityType == 1 && (this.mWindowingMode == 5 || this.mDisplayWindowingMode == 5);
    }

    public boolean canResizeTask() {
        return this.mWindowingMode == 5 || this.mWindowingMode == 6;
    }

    public boolean persistTaskBounds() {
        return this.mWindowingMode == 5;
    }

    public boolean tasksAreFloating() {
        return isFloating(this.mWindowingMode);
    }

    public static boolean isFloating(int i) {
        return i == 5 || i == 2;
    }

    public static boolean inMultiWindowMode(int i) {
        return (i == 1 || i == 0) ? false : true;
    }

    public boolean canReceiveKeys() {
        return this.mWindowingMode != 2;
    }

    public boolean isAlwaysOnTop() {
        if (this.mWindowingMode == 2 || this.mActivityType == 5) {
            return true;
        }
        if (this.mAlwaysOnTop != 1) {
            return false;
        }
        return this.mWindowingMode == 5 || this.mWindowingMode == 6;
    }

    public boolean useWindowFrameForBackdrop() {
        return this.mWindowingMode == 5 || this.mWindowingMode == 2;
    }

    public boolean hasMovementAnimations() {
        return this.mWindowingMode != 2;
    }

    public boolean supportSplitScreenWindowingMode() {
        return supportSplitScreenWindowingMode(this.mActivityType);
    }

    public static boolean supportSplitScreenWindowingMode(int i) {
        return (i == 4 || i == 5) ? false : true;
    }

    public static boolean areConfigurationsEqualForDisplay(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        return configuration.windowConfiguration.getMaxBounds().equals(configuration2.windowConfiguration.getMaxBounds()) && configuration.windowConfiguration.getDisplayRotation() == configuration2.windowConfiguration.getDisplayRotation();
    }

    public static java.lang.String windowingModeToString(int i) {
        switch (i) {
            case 0:
                return android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "fullscreen";
            case 2:
                return android.provider.ContactsContract.ContactOptionsColumns.PINNED;
            case 3:
            case 4:
            default:
                return java.lang.String.valueOf(i);
            case 5:
                return "freeform";
            case 6:
                return "multi-window";
        }
    }

    public static java.lang.String activityTypeToString(int i) {
        switch (i) {
            case 0:
                return android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "standard";
            case 2:
                return "home";
            case 3:
                return "recents";
            case 4:
                return android.provider.Settings.Secure.ASSISTANT;
            case 5:
                return android.content.Context.DREAM_SERVICE;
            default:
                return java.lang.String.valueOf(i);
        }
    }

    public static java.lang.String alwaysOnTopToString(int i) {
        switch (i) {
            case 0:
                return android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "on";
            case 2:
                return "off";
            default:
                return java.lang.String.valueOf(i);
        }
    }
}
