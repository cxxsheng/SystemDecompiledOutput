package android.view;

/* loaded from: classes4.dex */
public final class InputDevice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.InputDevice> CREATOR = new android.os.Parcelable.Creator<android.view.InputDevice>() { // from class: android.view.InputDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputDevice createFromParcel(android.os.Parcel parcel) {
            return new android.view.InputDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputDevice[] newArray(int i) {
            return new android.view.InputDevice[i];
        }
    };
    public static final int KEYBOARD_TYPE_ALPHABETIC = 2;
    public static final int KEYBOARD_TYPE_NONE = 0;
    public static final int KEYBOARD_TYPE_NON_ALPHABETIC = 1;
    private static final int MAX_RANGES = 1000;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_ORIENTATION = 8;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_PRESSURE = 2;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_SIZE = 3;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_TOOL_MAJOR = 6;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_TOOL_MINOR = 7;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_TOUCH_MAJOR = 4;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_TOUCH_MINOR = 5;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_X = 0;

    @java.lang.Deprecated
    public static final int MOTION_RANGE_Y = 1;
    public static final int SOURCE_ANY = -256;
    public static final int SOURCE_BLUETOOTH_STYLUS = 49154;
    public static final int SOURCE_CLASS_BUTTON = 1;
    public static final int SOURCE_CLASS_JOYSTICK = 16;
    public static final int SOURCE_CLASS_MASK = 255;
    public static final int SOURCE_CLASS_NONE = 0;
    public static final int SOURCE_CLASS_POINTER = 2;
    public static final int SOURCE_CLASS_POSITION = 8;
    public static final int SOURCE_CLASS_TRACKBALL = 4;
    public static final int SOURCE_DPAD = 513;
    public static final int SOURCE_GAMEPAD = 1025;
    public static final int SOURCE_HDMI = 33554433;
    public static final int SOURCE_JOYSTICK = 16777232;
    public static final int SOURCE_KEYBOARD = 257;
    public static final int SOURCE_MOUSE = 8194;
    public static final int SOURCE_MOUSE_RELATIVE = 131076;
    public static final int SOURCE_ROTARY_ENCODER = 4194304;
    public static final int SOURCE_SENSOR = 67108864;
    public static final int SOURCE_STYLUS = 16386;
    public static final int SOURCE_TOUCHPAD = 1048584;
    public static final int SOURCE_TOUCHSCREEN = 4098;
    public static final int SOURCE_TOUCH_NAVIGATION = 2097152;
    public static final int SOURCE_TRACKBALL = 65540;
    public static final int SOURCE_UNKNOWN = 0;
    private static final int VIBRATOR_ID_ALL = -1;
    private final int mAssociatedDisplayId;
    private final int mControllerNumber;
    private final java.lang.String mDescriptor;
    private final int mDeviceBus;
    private final int mGeneration;
    private final boolean mHasBattery;
    private final boolean mHasButtonUnderPad;
    private final boolean mHasMicrophone;
    private final boolean mHasSensor;
    private final boolean mHasVibrator;
    private final android.hardware.input.HostUsiVersion mHostUsiVersion;
    private final int mId;
    private final android.hardware.input.InputDeviceIdentifier mIdentifier;
    private final boolean mIsExternal;
    private final android.view.KeyCharacterMap mKeyCharacterMap;
    private final java.lang.String mKeyboardLanguageTag;
    private final java.lang.String mKeyboardLayoutType;
    private final int mKeyboardType;
    private android.hardware.lights.LightsManager mLightsManager;
    private final java.util.ArrayList<android.view.InputDevice.MotionRange> mMotionRanges;
    private final java.lang.String mName;
    private final int mProductId;
    private android.hardware.SensorManager mSensorManager;
    private final int mSources;
    private final int mVendorId;
    private android.os.Vibrator mVibrator;
    private android.os.VibratorManager mVibratorManager;
    private final android.view.InputDevice.ViewBehavior mViewBehavior;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface InputSourceClass {
    }

    private InputDevice(int i, int i2, int i3, java.lang.String str, int i4, int i5, int i6, java.lang.String str2, boolean z, int i7, int i8, android.view.KeyCharacterMap keyCharacterMap, java.lang.String str3, java.lang.String str4, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i9, int i10, int i11) {
        this.mMotionRanges = new java.util.ArrayList<>();
        this.mViewBehavior = new android.view.InputDevice.ViewBehavior(this);
        this.mId = i;
        this.mGeneration = i2;
        this.mControllerNumber = i3;
        this.mName = str;
        this.mVendorId = i4;
        this.mProductId = i5;
        this.mDeviceBus = i6;
        this.mDescriptor = str2;
        this.mIsExternal = z;
        this.mSources = i7;
        this.mKeyboardType = i8;
        this.mKeyCharacterMap = keyCharacterMap;
        if (!android.text.TextUtils.isEmpty(str3)) {
            java.lang.String languageTag = android.icu.util.ULocale.createCanonical(android.icu.util.ULocale.forLanguageTag(str3)).toLanguageTag();
            this.mKeyboardLanguageTag = android.text.TextUtils.equals(languageTag, "und") ? null : languageTag;
        } else {
            this.mKeyboardLanguageTag = null;
        }
        this.mKeyboardLayoutType = str4;
        this.mHasVibrator = z2;
        this.mHasMicrophone = z3;
        this.mHasButtonUnderPad = z4;
        this.mHasSensor = z5;
        this.mHasBattery = z6;
        this.mIdentifier = new android.hardware.input.InputDeviceIdentifier(str2, i4, i5);
        this.mHostUsiVersion = new android.hardware.input.HostUsiVersion(i9, i10);
        this.mAssociatedDisplayId = i11;
    }

    private InputDevice(android.os.Parcel parcel) {
        this.mMotionRanges = new java.util.ArrayList<>();
        this.mViewBehavior = new android.view.InputDevice.ViewBehavior(this);
        this.mKeyCharacterMap = android.view.KeyCharacterMap.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readInt();
        this.mGeneration = parcel.readInt();
        this.mControllerNumber = parcel.readInt();
        this.mName = parcel.readString();
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
        this.mDeviceBus = parcel.readInt();
        this.mDescriptor = parcel.readString();
        this.mIsExternal = parcel.readInt() != 0;
        this.mSources = parcel.readInt();
        this.mKeyboardType = parcel.readInt();
        this.mKeyboardLanguageTag = parcel.readString8();
        this.mKeyboardLayoutType = parcel.readString8();
        this.mHasVibrator = parcel.readInt() != 0;
        this.mHasMicrophone = parcel.readInt() != 0;
        this.mHasButtonUnderPad = parcel.readInt() != 0;
        this.mHasSensor = parcel.readInt() != 0;
        this.mHasBattery = parcel.readInt() != 0;
        this.mHostUsiVersion = android.hardware.input.HostUsiVersion.CREATOR.createFromParcel(parcel);
        this.mAssociatedDisplayId = parcel.readInt();
        this.mIdentifier = new android.hardware.input.InputDeviceIdentifier(this.mDescriptor, this.mVendorId, this.mProductId);
        int readInt = parcel.readInt();
        readInt = readInt > 1000 ? 1000 : readInt;
        for (int i = 0; i < readInt; i++) {
            addMotionRange(parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
        }
        this.mViewBehavior.mShouldSmoothScroll = parcel.readBoolean();
    }

    public static class Builder {
        private boolean mShouldSmoothScroll;
        private int mId = 0;
        private int mGeneration = 0;
        private int mControllerNumber = 0;
        private java.lang.String mName = "";
        private int mVendorId = 0;
        private int mProductId = 0;
        private int mDeviceBus = 0;
        private java.lang.String mDescriptor = "";
        private boolean mIsExternal = false;
        private int mSources = 0;
        private int mKeyboardType = 0;
        private android.view.KeyCharacterMap mKeyCharacterMap = null;
        private boolean mHasVibrator = false;
        private boolean mHasMicrophone = false;
        private boolean mHasButtonUnderPad = false;
        private boolean mHasSensor = false;
        private boolean mHasBattery = false;
        private java.lang.String mKeyboardLanguageTag = null;
        private java.lang.String mKeyboardLayoutType = null;
        private int mUsiVersionMajor = -1;
        private int mUsiVersionMinor = -1;
        private int mAssociatedDisplayId = -1;
        private java.util.List<android.view.InputDevice.MotionRange> mMotionRanges = new java.util.ArrayList();

        public android.view.InputDevice.Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public android.view.InputDevice.Builder setGeneration(int i) {
            this.mGeneration = i;
            return this;
        }

        public android.view.InputDevice.Builder setControllerNumber(int i) {
            this.mControllerNumber = i;
            return this;
        }

        public android.view.InputDevice.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.view.InputDevice.Builder setVendorId(int i) {
            this.mVendorId = i;
            return this;
        }

        public android.view.InputDevice.Builder setProductId(int i) {
            this.mProductId = i;
            return this;
        }

        public android.view.InputDevice.Builder setDeviceBus(int i) {
            this.mDeviceBus = i;
            return this;
        }

        public android.view.InputDevice.Builder setDescriptor(java.lang.String str) {
            this.mDescriptor = str;
            return this;
        }

        public android.view.InputDevice.Builder setExternal(boolean z) {
            this.mIsExternal = z;
            return this;
        }

        public android.view.InputDevice.Builder setSources(int i) {
            this.mSources = i;
            return this;
        }

        public android.view.InputDevice.Builder setKeyboardType(int i) {
            this.mKeyboardType = i;
            return this;
        }

        public android.view.InputDevice.Builder setKeyCharacterMap(android.view.KeyCharacterMap keyCharacterMap) {
            this.mKeyCharacterMap = keyCharacterMap;
            return this;
        }

        public android.view.InputDevice.Builder setHasVibrator(boolean z) {
            this.mHasVibrator = z;
            return this;
        }

        public android.view.InputDevice.Builder setHasMicrophone(boolean z) {
            this.mHasMicrophone = z;
            return this;
        }

        public android.view.InputDevice.Builder setHasButtonUnderPad(boolean z) {
            this.mHasButtonUnderPad = z;
            return this;
        }

        public android.view.InputDevice.Builder setHasSensor(boolean z) {
            this.mHasSensor = z;
            return this;
        }

        public android.view.InputDevice.Builder setHasBattery(boolean z) {
            this.mHasBattery = z;
            return this;
        }

        public android.view.InputDevice.Builder setKeyboardLanguageTag(java.lang.String str) {
            this.mKeyboardLanguageTag = str;
            return this;
        }

        public android.view.InputDevice.Builder setKeyboardLayoutType(java.lang.String str) {
            this.mKeyboardLayoutType = str;
            return this;
        }

        public android.view.InputDevice.Builder setUsiVersion(android.hardware.input.HostUsiVersion hostUsiVersion) {
            this.mUsiVersionMajor = hostUsiVersion != null ? hostUsiVersion.getMajorVersion() : -1;
            this.mUsiVersionMinor = hostUsiVersion != null ? hostUsiVersion.getMinorVersion() : -1;
            return this;
        }

        public android.view.InputDevice.Builder setAssociatedDisplayId(int i) {
            this.mAssociatedDisplayId = i;
            return this;
        }

        public android.view.InputDevice.Builder addMotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
            this.mMotionRanges.add(new android.view.InputDevice.MotionRange(i, i2, f, f2, f3, f4, f5));
            return this;
        }

        public android.view.InputDevice.Builder setShouldSmoothScroll(boolean z) {
            this.mShouldSmoothScroll = z;
            return this;
        }

        public android.view.InputDevice build() {
            android.view.InputDevice inputDevice = new android.view.InputDevice(this.mId, this.mGeneration, this.mControllerNumber, this.mName, this.mVendorId, this.mProductId, this.mDeviceBus, this.mDescriptor, this.mIsExternal, this.mSources, this.mKeyboardType, this.mKeyCharacterMap, this.mKeyboardLanguageTag, this.mKeyboardLayoutType, this.mHasVibrator, this.mHasMicrophone, this.mHasButtonUnderPad, this.mHasSensor, this.mHasBattery, this.mUsiVersionMajor, this.mUsiVersionMinor, this.mAssociatedDisplayId);
            int size = this.mMotionRanges.size();
            for (int i = 0; i < size; i++) {
                android.view.InputDevice.MotionRange motionRange = this.mMotionRanges.get(i);
                inputDevice.addMotionRange(motionRange.getAxis(), motionRange.getSource(), motionRange.getMin(), motionRange.getMax(), motionRange.getFlat(), motionRange.getFuzz(), motionRange.getResolution());
            }
            inputDevice.setShouldSmoothScroll(this.mShouldSmoothScroll);
            return inputDevice;
        }
    }

    public static android.view.InputDevice getDevice(int i) {
        return android.hardware.input.InputManagerGlobal.getInstance().getInputDevice(i);
    }

    public static int[] getDeviceIds() {
        return android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceIds();
    }

    public int getId() {
        return this.mId;
    }

    public int getControllerNumber() {
        return this.mControllerNumber;
    }

    public android.hardware.input.InputDeviceIdentifier getIdentifier() {
        return this.mIdentifier;
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getDeviceBus() {
        return this.mDeviceBus;
    }

    public java.lang.String getDescriptor() {
        return this.mDescriptor;
    }

    public boolean isVirtual() {
        return this.mId < 0;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public boolean isFullKeyboard() {
        return (this.mSources & 257) == 257 && this.mKeyboardType == 2;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getSources() {
        return this.mSources;
    }

    public boolean supportsSource(int i) {
        return (this.mSources & i) == i;
    }

    public int getKeyboardType() {
        return this.mKeyboardType;
    }

    public android.view.KeyCharacterMap getKeyCharacterMap() {
        return this.mKeyCharacterMap;
    }

    public java.lang.String getKeyboardLanguageTag() {
        return this.mKeyboardLanguageTag;
    }

    public java.lang.String getKeyboardLayoutType() {
        return this.mKeyboardLayoutType;
    }

    public boolean[] hasKeys(int... iArr) {
        return android.hardware.input.InputManagerGlobal.getInstance().deviceHasKeys(this.mId, iArr);
    }

    public int getKeyCodeForKeyLocation(int i) {
        return android.hardware.input.InputManagerGlobal.getInstance().getKeyCodeForKeyLocation(this.mId, i);
    }

    public android.view.InputDevice.MotionRange getMotionRange(int i) {
        int size = this.mMotionRanges.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.InputDevice.MotionRange motionRange = this.mMotionRanges.get(i2);
            if (motionRange.mAxis == i) {
                return motionRange;
            }
        }
        return null;
    }

    public android.view.InputDevice.MotionRange getMotionRange(int i, int i2) {
        int size = this.mMotionRanges.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.view.InputDevice.MotionRange motionRange = this.mMotionRanges.get(i3);
            if (motionRange.mAxis == i && motionRange.mSource == i2) {
                return motionRange;
            }
        }
        return null;
    }

    public java.util.List<android.view.InputDevice.MotionRange> getMotionRanges() {
        return this.mMotionRanges;
    }

    public android.view.InputDevice.ViewBehavior getViewBehavior() {
        return this.mViewBehavior;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
        this.mMotionRanges.add(new android.view.InputDevice.MotionRange(i, i2, f, f2, f3, f4, f5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShouldSmoothScroll(boolean z) {
        this.mViewBehavior.mShouldSmoothScroll = z;
    }

    public java.lang.String getBluetoothAddress() {
        return android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceBluetoothAddress(this.mId);
    }

    @java.lang.Deprecated
    public android.os.Vibrator getVibrator() {
        android.os.Vibrator vibrator;
        synchronized (this.mMotionRanges) {
            if (this.mVibrator == null) {
                if (this.mHasVibrator) {
                    this.mVibrator = android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceVibrator(this.mId, -1);
                } else {
                    this.mVibrator = android.os.NullVibrator.getInstance();
                }
            }
            vibrator = this.mVibrator;
        }
        return vibrator;
    }

    public android.os.VibratorManager getVibratorManager() {
        synchronized (this.mMotionRanges) {
            if (this.mVibratorManager == null) {
                this.mVibratorManager = android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceVibratorManager(this.mId);
            }
        }
        return this.mVibratorManager;
    }

    public android.hardware.BatteryState getBatteryState() {
        return android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceBatteryState(this.mId, this.mHasBattery);
    }

    public android.hardware.lights.LightsManager getLightsManager() {
        synchronized (this.mMotionRanges) {
            if (this.mLightsManager == null) {
                this.mLightsManager = android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceLightsManager(this.mId);
            }
        }
        return this.mLightsManager;
    }

    public android.hardware.SensorManager getSensorManager() {
        synchronized (this.mMotionRanges) {
            if (this.mSensorManager == null) {
                this.mSensorManager = android.hardware.input.InputManagerGlobal.getInstance().getInputDeviceSensorManager(this.mId);
            }
        }
        return this.mSensorManager;
    }

    public boolean isEnabled() {
        return android.hardware.input.InputManagerGlobal.getInstance().isInputDeviceEnabled(this.mId);
    }

    public void enable() {
        android.hardware.input.InputManagerGlobal.getInstance().enableInputDevice(this.mId);
    }

    public void disable() {
        android.hardware.input.InputManagerGlobal.getInstance().disableInputDevice(this.mId);
    }

    public boolean hasMicrophone() {
        return this.mHasMicrophone;
    }

    public boolean hasButtonUnderPad() {
        return this.mHasButtonUnderPad;
    }

    public boolean hasSensor() {
        return this.mHasSensor;
    }

    public boolean hasBattery() {
        return this.mHasBattery;
    }

    public android.hardware.input.HostUsiVersion getHostUsiVersion() {
        if (this.mHostUsiVersion.isValid()) {
            return this.mHostUsiVersion;
        }
        return null;
    }

    public int getAssociatedDisplayId() {
        return this.mAssociatedDisplayId;
    }

    public static final class MotionRange {
        private int mAxis;
        private float mFlat;
        private float mFuzz;
        private float mMax;
        private float mMin;
        private float mResolution;
        private int mSource;

        private MotionRange(int i, int i2, float f, float f2, float f3, float f4, float f5) {
            this.mAxis = i;
            this.mSource = i2;
            this.mMin = f;
            this.mMax = f2;
            this.mFlat = f3;
            this.mFuzz = f4;
            this.mResolution = f5;
        }

        public int getAxis() {
            return this.mAxis;
        }

        public int getSource() {
            return this.mSource;
        }

        public boolean isFromSource(int i) {
            return (getSource() & i) == i;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getRange() {
            return this.mMax - this.mMin;
        }

        public float getFlat() {
            return this.mFlat;
        }

        public float getFuzz() {
            return this.mFuzz;
        }

        public float getResolution() {
            return this.mResolution;
        }
    }

    public static final class ViewBehavior {
        private static final boolean DEFAULT_SHOULD_SMOOTH_SCROLL = false;
        private final android.view.InputDevice mInputDevice;
        private boolean mShouldSmoothScroll = false;

        public ViewBehavior(android.view.InputDevice inputDevice) {
            this.mInputDevice = inputDevice;
        }

        public boolean shouldSmoothScroll(int i, int i2) {
            if (this.mInputDevice.getMotionRange(i, i2) == null) {
                return false;
            }
            return this.mShouldSmoothScroll;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mKeyCharacterMap.writeToParcel(parcel, i);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mGeneration);
        parcel.writeInt(this.mControllerNumber);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mDeviceBus);
        parcel.writeString(this.mDescriptor);
        parcel.writeInt(this.mIsExternal ? 1 : 0);
        parcel.writeInt(this.mSources);
        parcel.writeInt(this.mKeyboardType);
        parcel.writeString8(this.mKeyboardLanguageTag);
        parcel.writeString8(this.mKeyboardLayoutType);
        parcel.writeInt(this.mHasVibrator ? 1 : 0);
        parcel.writeInt(this.mHasMicrophone ? 1 : 0);
        parcel.writeInt(this.mHasButtonUnderPad ? 1 : 0);
        parcel.writeInt(this.mHasSensor ? 1 : 0);
        parcel.writeInt(this.mHasBattery ? 1 : 0);
        this.mHostUsiVersion.writeToParcel(parcel, i);
        parcel.writeInt(this.mAssociatedDisplayId);
        int size = this.mMotionRanges.size();
        if (size > 1000) {
            size = 1000;
        }
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.view.InputDevice.MotionRange motionRange = this.mMotionRanges.get(i2);
            parcel.writeInt(motionRange.mAxis);
            parcel.writeInt(motionRange.mSource);
            parcel.writeFloat(motionRange.mMin);
            parcel.writeFloat(motionRange.mMax);
            parcel.writeFloat(motionRange.mFlat);
            parcel.writeFloat(motionRange.mFuzz);
            parcel.writeFloat(motionRange.mResolution);
        }
        parcel.writeBoolean(this.mViewBehavior.mShouldSmoothScroll);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Input Device ").append(this.mId).append(": ").append(this.mName).append("\n");
        sb.append("  Descriptor: ").append(this.mDescriptor).append("\n");
        sb.append("  Generation: ").append(this.mGeneration).append("\n");
        sb.append("  Location: ").append(this.mIsExternal ? "external" : "built-in").append("\n");
        sb.append("  Keyboard Type: ");
        switch (this.mKeyboardType) {
            case 0:
                sb.append("none");
                break;
            case 1:
                sb.append("non-alphabetic");
                break;
            case 2:
                sb.append("alphabetic");
                break;
        }
        sb.append("\n");
        sb.append("  Has Vibrator: ").append(this.mHasVibrator).append("\n");
        sb.append("  Has Sensor: ").append(this.mHasSensor).append("\n");
        sb.append("  Has battery: ").append(this.mHasBattery).append("\n");
        sb.append("  Has mic: ").append(this.mHasMicrophone).append("\n");
        sb.append("  USI Version: ").append(getHostUsiVersion()).append("\n");
        if (this.mKeyboardLanguageTag != null) {
            sb.append(" Keyboard language tag: ").append(this.mKeyboardLanguageTag).append("\n");
        }
        if (this.mKeyboardLayoutType != null) {
            sb.append(" Keyboard layout type: ").append(this.mKeyboardLayoutType).append("\n");
        }
        sb.append("  Sources: 0x").append(java.lang.Integer.toHexString(this.mSources)).append(" (");
        appendSourceDescriptionIfApplicable(sb, 257, "keyboard");
        appendSourceDescriptionIfApplicable(sb, 513, "dpad");
        appendSourceDescriptionIfApplicable(sb, 4098, "touchscreen");
        appendSourceDescriptionIfApplicable(sb, 8194, "mouse");
        appendSourceDescriptionIfApplicable(sb, 16386, "stylus");
        appendSourceDescriptionIfApplicable(sb, 65540, "trackball");
        appendSourceDescriptionIfApplicable(sb, SOURCE_MOUSE_RELATIVE, "mouse_relative");
        appendSourceDescriptionIfApplicable(sb, SOURCE_TOUCHPAD, "touchpad");
        appendSourceDescriptionIfApplicable(sb, SOURCE_JOYSTICK, "joystick");
        appendSourceDescriptionIfApplicable(sb, 1025, "gamepad");
        sb.append(" )\n");
        int size = this.mMotionRanges.size();
        for (int i = 0; i < size; i++) {
            android.view.InputDevice.MotionRange motionRange = this.mMotionRanges.get(i);
            sb.append("    ").append(android.view.MotionEvent.axisToString(motionRange.mAxis));
            sb.append(": source=0x").append(java.lang.Integer.toHexString(motionRange.mSource));
            sb.append(" min=").append(motionRange.mMin);
            sb.append(" max=").append(motionRange.mMax);
            sb.append(" flat=").append(motionRange.mFlat);
            sb.append(" fuzz=").append(motionRange.mFuzz);
            sb.append(" resolution=").append(motionRange.mResolution);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void appendSourceDescriptionIfApplicable(java.lang.StringBuilder sb, int i, java.lang.String str) {
        if ((this.mSources & i) == i) {
            sb.append(" ");
            sb.append(str);
        }
    }
}
