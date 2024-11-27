package android.service.notification;

/* loaded from: classes3.dex */
public final class ZenDeviceEffects implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.ZenDeviceEffects> CREATOR = new android.os.Parcelable.Creator<android.service.notification.ZenDeviceEffects>() { // from class: android.service.notification.ZenDeviceEffects.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenDeviceEffects createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.ZenDeviceEffects(parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), java.util.Set.of(parcel.readArray(java.lang.String.class.getClassLoader(), java.lang.String.class)));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ZenDeviceEffects[] newArray(int i) {
            return new android.service.notification.ZenDeviceEffects[i];
        }
    };
    public static final int FIELD_DIM_WALLPAPER = 4;
    public static final int FIELD_DISABLE_AUTO_BRIGHTNESS = 16;
    public static final int FIELD_DISABLE_TAP_TO_WAKE = 32;
    public static final int FIELD_DISABLE_TILT_TO_WAKE = 64;
    public static final int FIELD_DISABLE_TOUCH = 128;
    public static final int FIELD_EXTRA_EFFECTS = 1024;
    public static final int FIELD_GRAYSCALE = 1;
    public static final int FIELD_MAXIMIZE_DOZE = 512;
    public static final int FIELD_MINIMIZE_RADIO_USAGE = 256;
    public static final int FIELD_NIGHT_MODE = 8;
    public static final int FIELD_SUPPRESS_AMBIENT_DISPLAY = 2;
    private static final int MAX_EFFECTS_LENGTH = 2000;
    private final boolean mDimWallpaper;
    private final boolean mDisableAutoBrightness;
    private final boolean mDisableTapToWake;
    private final boolean mDisableTiltToWake;
    private final boolean mDisableTouch;
    private final java.util.Set<java.lang.String> mExtraEffects;
    private final boolean mGrayscale;
    private final boolean mMaximizeDoze;
    private final boolean mMinimizeRadioUsage;
    private final boolean mNightMode;
    private final boolean mSuppressAmbientDisplay;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    private ZenDeviceEffects(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, java.util.Set<java.lang.String> set) {
        this.mGrayscale = z;
        this.mSuppressAmbientDisplay = z2;
        this.mDimWallpaper = z3;
        this.mNightMode = z4;
        this.mDisableAutoBrightness = z5;
        this.mDisableTapToWake = z6;
        this.mDisableTiltToWake = z7;
        this.mDisableTouch = z8;
        this.mMinimizeRadioUsage = z9;
        this.mMaximizeDoze = z10;
        this.mExtraEffects = java.util.Collections.unmodifiableSet(set);
    }

    public void validate() {
        java.util.Iterator<java.lang.String> it = this.mExtraEffects.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().length();
        }
        if (i > 2000) {
            throw new java.lang.IllegalArgumentException("Total size of extra effects must be at most 2000 characters");
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.service.notification.ZenDeviceEffects)) {
            return false;
        }
        android.service.notification.ZenDeviceEffects zenDeviceEffects = (android.service.notification.ZenDeviceEffects) obj;
        if (obj == this) {
            return true;
        }
        return this.mGrayscale == zenDeviceEffects.mGrayscale && this.mSuppressAmbientDisplay == zenDeviceEffects.mSuppressAmbientDisplay && this.mDimWallpaper == zenDeviceEffects.mDimWallpaper && this.mNightMode == zenDeviceEffects.mNightMode && this.mDisableAutoBrightness == zenDeviceEffects.mDisableAutoBrightness && this.mDisableTapToWake == zenDeviceEffects.mDisableTapToWake && this.mDisableTiltToWake == zenDeviceEffects.mDisableTiltToWake && this.mDisableTouch == zenDeviceEffects.mDisableTouch && this.mMinimizeRadioUsage == zenDeviceEffects.mMinimizeRadioUsage && this.mMaximizeDoze == zenDeviceEffects.mMaximizeDoze && java.util.Objects.equals(this.mExtraEffects, zenDeviceEffects.mExtraEffects);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mGrayscale), java.lang.Boolean.valueOf(this.mSuppressAmbientDisplay), java.lang.Boolean.valueOf(this.mDimWallpaper), java.lang.Boolean.valueOf(this.mNightMode), java.lang.Boolean.valueOf(this.mDisableAutoBrightness), java.lang.Boolean.valueOf(this.mDisableTapToWake), java.lang.Boolean.valueOf(this.mDisableTiltToWake), java.lang.Boolean.valueOf(this.mDisableTouch), java.lang.Boolean.valueOf(this.mMinimizeRadioUsage), java.lang.Boolean.valueOf(this.mMaximizeDoze), this.mExtraEffects);
    }

    public java.lang.String toString() {
        java.util.ArrayList arrayList = new java.util.ArrayList(11);
        if (this.mGrayscale) {
            arrayList.add("grayscale");
        }
        if (this.mSuppressAmbientDisplay) {
            arrayList.add("suppressAmbientDisplay");
        }
        if (this.mDimWallpaper) {
            arrayList.add("dimWallpaper");
        }
        if (this.mNightMode) {
            arrayList.add("nightMode");
        }
        if (this.mDisableAutoBrightness) {
            arrayList.add("disableAutoBrightness");
        }
        if (this.mDisableTapToWake) {
            arrayList.add("disableTapToWake");
        }
        if (this.mDisableTiltToWake) {
            arrayList.add("disableTiltToWake");
        }
        if (this.mDisableTouch) {
            arrayList.add("disableTouch");
        }
        if (this.mMinimizeRadioUsage) {
            arrayList.add("minimizeRadioUsage");
        }
        if (this.mMaximizeDoze) {
            arrayList.add("maximizeDoze");
        }
        if (this.mExtraEffects.size() > 0) {
            arrayList.add("extraEffects=[" + java.lang.String.join(",", this.mExtraEffects) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + java.lang.String.join(", ", arrayList) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static java.lang.String fieldsToString(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_GRAYSCALE");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_SUPPRESS_AMBIENT_DISPLAY");
        }
        if ((i & 4) != 0) {
            arrayList.add("FIELD_DIM_WALLPAPER");
        }
        if ((i & 8) != 0) {
            arrayList.add("FIELD_NIGHT_MODE");
        }
        if ((i & 16) != 0) {
            arrayList.add("FIELD_DISABLE_AUTO_BRIGHTNESS");
        }
        if ((i & 32) != 0) {
            arrayList.add("FIELD_DISABLE_TAP_TO_WAKE");
        }
        if ((i & 64) != 0) {
            arrayList.add("FIELD_DISABLE_TILT_TO_WAKE");
        }
        if ((i & 128) != 0) {
            arrayList.add("FIELD_DISABLE_TOUCH");
        }
        if ((i & 256) != 0) {
            arrayList.add("FIELD_MINIMIZE_RADIO_USAGE");
        }
        if ((i & 512) != 0) {
            arrayList.add("FIELD_MAXIMIZE_DOZE");
        }
        if ((i & 1024) != 0) {
            arrayList.add("FIELD_EXTRA_EFFECTS");
        }
        return "{" + java.lang.String.join(",", arrayList) + "}";
    }

    public boolean shouldDisplayGrayscale() {
        return this.mGrayscale;
    }

    public boolean shouldSuppressAmbientDisplay() {
        return this.mSuppressAmbientDisplay;
    }

    public boolean shouldDimWallpaper() {
        return this.mDimWallpaper;
    }

    public boolean shouldUseNightMode() {
        return this.mNightMode;
    }

    public boolean shouldDisableAutoBrightness() {
        return this.mDisableAutoBrightness;
    }

    public boolean shouldDisableTapToWake() {
        return this.mDisableTapToWake;
    }

    public boolean shouldDisableTiltToWake() {
        return this.mDisableTiltToWake;
    }

    public boolean shouldDisableTouch() {
        return this.mDisableTouch;
    }

    public boolean shouldMinimizeRadioUsage() {
        return this.mMinimizeRadioUsage;
    }

    public boolean shouldMaximizeDoze() {
        return this.mMaximizeDoze;
    }

    public java.util.Set<java.lang.String> getExtraEffects() {
        return this.mExtraEffects;
    }

    public boolean hasEffects() {
        return this.mGrayscale || this.mSuppressAmbientDisplay || this.mDimWallpaper || this.mNightMode || this.mDisableAutoBrightness || this.mDisableTapToWake || this.mDisableTiltToWake || this.mDisableTouch || this.mMinimizeRadioUsage || this.mMaximizeDoze || this.mExtraEffects.size() > 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mGrayscale);
        parcel.writeBoolean(this.mSuppressAmbientDisplay);
        parcel.writeBoolean(this.mDimWallpaper);
        parcel.writeBoolean(this.mNightMode);
        parcel.writeBoolean(this.mDisableAutoBrightness);
        parcel.writeBoolean(this.mDisableTapToWake);
        parcel.writeBoolean(this.mDisableTiltToWake);
        parcel.writeBoolean(this.mDisableTouch);
        parcel.writeBoolean(this.mMinimizeRadioUsage);
        parcel.writeBoolean(this.mMaximizeDoze);
        parcel.writeArray(this.mExtraEffects.toArray(new java.lang.String[0]));
    }

    public static final class Builder {
        private boolean mDimWallpaper;
        private boolean mDisableAutoBrightness;
        private boolean mDisableTapToWake;
        private boolean mDisableTiltToWake;
        private boolean mDisableTouch;
        private final java.util.HashSet<java.lang.String> mExtraEffects = new java.util.HashSet<>();
        private boolean mGrayscale;
        private boolean mMaximizeDoze;
        private boolean mMinimizeRadioUsage;
        private boolean mNightMode;
        private boolean mSuppressAmbientDisplay;

        public Builder() {
        }

        public Builder(android.service.notification.ZenDeviceEffects zenDeviceEffects) {
            this.mGrayscale = zenDeviceEffects.shouldDisplayGrayscale();
            this.mSuppressAmbientDisplay = zenDeviceEffects.shouldSuppressAmbientDisplay();
            this.mDimWallpaper = zenDeviceEffects.shouldDimWallpaper();
            this.mNightMode = zenDeviceEffects.shouldUseNightMode();
            this.mDisableAutoBrightness = zenDeviceEffects.shouldDisableAutoBrightness();
            this.mDisableTapToWake = zenDeviceEffects.shouldDisableTapToWake();
            this.mDisableTiltToWake = zenDeviceEffects.shouldDisableTiltToWake();
            this.mDisableTouch = zenDeviceEffects.shouldDisableTouch();
            this.mMinimizeRadioUsage = zenDeviceEffects.shouldMinimizeRadioUsage();
            this.mMaximizeDoze = zenDeviceEffects.shouldMaximizeDoze();
            this.mExtraEffects.addAll(zenDeviceEffects.getExtraEffects());
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDisplayGrayscale(boolean z) {
            this.mGrayscale = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldSuppressAmbientDisplay(boolean z) {
            this.mSuppressAmbientDisplay = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDimWallpaper(boolean z) {
            this.mDimWallpaper = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldUseNightMode(boolean z) {
            this.mNightMode = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDisableAutoBrightness(boolean z) {
            this.mDisableAutoBrightness = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDisableTapToWake(boolean z) {
            this.mDisableTapToWake = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDisableTiltToWake(boolean z) {
            this.mDisableTiltToWake = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldDisableTouch(boolean z) {
            this.mDisableTouch = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldMinimizeRadioUsage(boolean z) {
            this.mMinimizeRadioUsage = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setShouldMaximizeDoze(boolean z) {
            this.mMaximizeDoze = z;
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder setExtraEffects(java.util.Set<java.lang.String> set) {
            java.util.Objects.requireNonNull(set);
            this.mExtraEffects.clear();
            this.mExtraEffects.addAll(set);
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder addExtraEffects(java.util.Set<java.lang.String> set) {
            this.mExtraEffects.addAll((java.util.Collection) java.util.Objects.requireNonNull(set));
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder addExtraEffect(java.lang.String str) {
            this.mExtraEffects.add((java.lang.String) java.util.Objects.requireNonNull(str));
            return this;
        }

        public android.service.notification.ZenDeviceEffects.Builder add(android.service.notification.ZenDeviceEffects zenDeviceEffects) {
            if (zenDeviceEffects == null) {
                return this;
            }
            if (zenDeviceEffects.shouldDisplayGrayscale()) {
                setShouldDisplayGrayscale(true);
            }
            if (zenDeviceEffects.shouldSuppressAmbientDisplay()) {
                setShouldSuppressAmbientDisplay(true);
            }
            if (zenDeviceEffects.shouldDimWallpaper()) {
                setShouldDimWallpaper(true);
            }
            if (zenDeviceEffects.shouldUseNightMode()) {
                setShouldUseNightMode(true);
            }
            if (zenDeviceEffects.shouldDisableAutoBrightness()) {
                setShouldDisableAutoBrightness(true);
            }
            if (zenDeviceEffects.shouldDisableTapToWake()) {
                setShouldDisableTapToWake(true);
            }
            if (zenDeviceEffects.shouldDisableTiltToWake()) {
                setShouldDisableTiltToWake(true);
            }
            if (zenDeviceEffects.shouldDisableTouch()) {
                setShouldDisableTouch(true);
            }
            if (zenDeviceEffects.shouldMinimizeRadioUsage()) {
                setShouldMinimizeRadioUsage(true);
            }
            if (zenDeviceEffects.shouldMaximizeDoze()) {
                setShouldMaximizeDoze(true);
            }
            addExtraEffects(zenDeviceEffects.getExtraEffects());
            return this;
        }

        public android.service.notification.ZenDeviceEffects build() {
            return new android.service.notification.ZenDeviceEffects(this.mGrayscale, this.mSuppressAmbientDisplay, this.mDimWallpaper, this.mNightMode, this.mDisableAutoBrightness, this.mDisableTapToWake, this.mDisableTiltToWake, this.mDisableTouch, this.mMinimizeRadioUsage, this.mMaximizeDoze, this.mExtraEffects);
        }
    }
}
