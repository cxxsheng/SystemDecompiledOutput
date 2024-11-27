package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class TemperatureControlTemplate extends android.service.controls.templates.ControlTemplate {
    private static final int ALL_FLAGS = 62;
    public static final int FLAG_MODE_COOL = 8;
    public static final int FLAG_MODE_ECO = 32;
    public static final int FLAG_MODE_HEAT = 4;
    public static final int FLAG_MODE_HEAT_COOL = 16;
    public static final int FLAG_MODE_OFF = 2;
    private static final java.lang.String KEY_CURRENT_ACTIVE_MODE = "key_current_active_mode";
    private static final java.lang.String KEY_CURRENT_MODE = "key_current_mode";
    private static final java.lang.String KEY_MODES = "key_modes";
    private static final java.lang.String KEY_TEMPLATE = "key_template";
    public static final int MODE_COOL = 3;
    public static final int MODE_ECO = 5;
    public static final int MODE_HEAT = 2;
    public static final int MODE_HEAT_COOL = 4;
    public static final int MODE_OFF = 1;
    public static final int MODE_UNKNOWN = 0;
    private static final int NUM_MODES = 6;
    private static final java.lang.String TAG = "ThermostatTemplate";
    private static final int TYPE = 7;
    private static final int[] modeToFlag = {0, 2, 4, 8, 16, 32};
    private final int mCurrentActiveMode;
    private final int mCurrentMode;
    private final int mModes;
    private final android.service.controls.templates.ControlTemplate mTemplate;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModeFlag {
    }

    public TemperatureControlTemplate(java.lang.String str, android.service.controls.templates.ControlTemplate controlTemplate, int i, int i2, int i3) {
        super(str);
        com.android.internal.util.Preconditions.checkNotNull(controlTemplate);
        this.mTemplate = controlTemplate;
        if (i < 0 || i >= 6) {
            android.util.Log.e(TAG, "Invalid current mode:" + i);
            this.mCurrentMode = 0;
        } else {
            this.mCurrentMode = i;
        }
        if (i2 < 0 || i2 >= 6) {
            android.util.Log.e(TAG, "Invalid current active mode:" + i2);
            this.mCurrentActiveMode = 0;
        } else {
            this.mCurrentActiveMode = i2;
        }
        this.mModes = i3 & 62;
        if (this.mCurrentMode != 0 && (modeToFlag[this.mCurrentMode] & this.mModes) == 0) {
            throw new java.lang.IllegalArgumentException("Mode " + this.mCurrentMode + " not supported in flag.");
        }
        if (this.mCurrentActiveMode != 0 && (modeToFlag[this.mCurrentActiveMode] & this.mModes) == 0) {
            throw new java.lang.IllegalArgumentException("Mode " + i2 + " not supported in flag.");
        }
    }

    TemperatureControlTemplate(android.os.Bundle bundle) {
        super(bundle);
        this.mTemplate = android.service.controls.templates.ControlTemplate.createTemplateFromBundle(bundle.getBundle(KEY_TEMPLATE));
        this.mCurrentMode = bundle.getInt(KEY_CURRENT_MODE);
        this.mCurrentActiveMode = bundle.getInt(KEY_CURRENT_ACTIVE_MODE);
        this.mModes = bundle.getInt(KEY_MODES);
    }

    @Override // android.service.controls.templates.ControlTemplate
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putBundle(KEY_TEMPLATE, this.mTemplate.getDataBundle());
        dataBundle.putInt(KEY_CURRENT_MODE, this.mCurrentMode);
        dataBundle.putInt(KEY_CURRENT_ACTIVE_MODE, this.mCurrentActiveMode);
        dataBundle.putInt(KEY_MODES, this.mModes);
        return dataBundle;
    }

    public android.service.controls.templates.ControlTemplate getTemplate() {
        return this.mTemplate;
    }

    public int getCurrentMode() {
        return this.mCurrentMode;
    }

    public int getCurrentActiveMode() {
        return this.mCurrentActiveMode;
    }

    public int getModes() {
        return this.mModes;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 7;
    }
}
