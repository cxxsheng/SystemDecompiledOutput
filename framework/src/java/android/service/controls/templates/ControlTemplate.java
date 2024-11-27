package android.service.controls.templates;

/* loaded from: classes3.dex */
public abstract class ControlTemplate {
    private static final android.service.controls.templates.ControlTemplate ERROR_TEMPLATE;
    private static final java.lang.String KEY_TEMPLATE_ID = "key_template_id";
    private static final java.lang.String KEY_TEMPLATE_TYPE = "key_template_type";
    public static final android.service.controls.templates.ControlTemplate NO_TEMPLATE;
    private static final java.lang.String TAG = "ControlTemplate";
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_NO_TEMPLATE = 0;
    public static final int TYPE_RANGE = 2;
    public static final int TYPE_STATELESS = 8;
    public static final int TYPE_TEMPERATURE = 7;
    public static final int TYPE_THUMBNAIL = 3;
    public static final int TYPE_TOGGLE = 1;
    public static final int TYPE_TOGGLE_RANGE = 6;
    private final java.lang.String mTemplateId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TemplateType {
    }

    public abstract int getTemplateType();

    static {
        java.lang.String str = "";
        NO_TEMPLATE = new android.service.controls.templates.ControlTemplate(str) { // from class: android.service.controls.templates.ControlTemplate.1
            @Override // android.service.controls.templates.ControlTemplate
            public int getTemplateType() {
                return 0;
            }
        };
        ERROR_TEMPLATE = new android.service.controls.templates.ControlTemplate(str) { // from class: android.service.controls.templates.ControlTemplate.2
            @Override // android.service.controls.templates.ControlTemplate
            public int getTemplateType() {
                return -1;
            }
        };
    }

    public java.lang.String getTemplateId() {
        return this.mTemplateId;
    }

    android.os.Bundle getDataBundle() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt(KEY_TEMPLATE_TYPE, getTemplateType());
        bundle.putString(KEY_TEMPLATE_ID, this.mTemplateId);
        return bundle;
    }

    private ControlTemplate() {
        this.mTemplateId = "";
    }

    ControlTemplate(android.os.Bundle bundle) {
        this.mTemplateId = bundle.getString(KEY_TEMPLATE_ID);
    }

    ControlTemplate(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        this.mTemplateId = str;
    }

    public void prepareTemplateForBinder(android.content.Context context) {
    }

    static android.service.controls.templates.ControlTemplate createTemplateFromBundle(android.os.Bundle bundle) {
        if (bundle == null) {
            android.util.Log.e(TAG, "Null bundle");
            return ERROR_TEMPLATE;
        }
        try {
            switch (bundle.getInt(KEY_TEMPLATE_TYPE, -1)) {
                case 0:
                    return NO_TEMPLATE;
                case 1:
                    return new android.service.controls.templates.ToggleTemplate(bundle);
                case 2:
                    return new android.service.controls.templates.RangeTemplate(bundle);
                case 3:
                    return new android.service.controls.templates.ThumbnailTemplate(bundle);
                case 4:
                case 5:
                default:
                    return ERROR_TEMPLATE;
                case 6:
                    return new android.service.controls.templates.ToggleRangeTemplate(bundle);
                case 7:
                    return new android.service.controls.templates.TemperatureControlTemplate(bundle);
                case 8:
                    return new android.service.controls.templates.StatelessTemplate(bundle);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error creating template", e);
            return ERROR_TEMPLATE;
        }
    }

    public static android.service.controls.templates.ControlTemplate getErrorTemplate() {
        return ERROR_TEMPLATE;
    }

    public static android.service.controls.templates.ControlTemplate getNoTemplateObject() {
        return NO_TEMPLATE;
    }
}
