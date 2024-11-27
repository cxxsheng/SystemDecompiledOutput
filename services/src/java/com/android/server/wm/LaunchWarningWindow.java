package com.android.server.wm;

/* loaded from: classes3.dex */
public final class LaunchWarningWindow extends android.app.Dialog {
    public LaunchWarningWindow(android.content.Context context, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        super(context, android.R.style.Theme.SearchBar);
        requestWindowFeature(3);
        getWindow().setType(2003);
        getWindow().addFlags(24);
        setContentView(android.R.layout.launch_warning);
        setTitle(context.getText(android.R.string.kg_password_wrong_pin_code));
        android.util.TypedValue typedValue = new android.util.TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
        getWindow().setFeatureDrawableResource(3, typedValue.resourceId);
        ((android.widget.ImageView) findViewById(android.R.id.removed)).setImageDrawable(activityRecord2.info.applicationInfo.loadIcon(context.getPackageManager()));
        ((android.widget.TextView) findViewById(android.R.id.repeat)).setText(context.getResources().getString(android.R.string.kg_password_instructions, activityRecord2.info.applicationInfo.loadLabel(context.getPackageManager()).toString()));
        ((android.widget.ImageView) findViewById(android.R.id.option3)).setImageDrawable(activityRecord.info.applicationInfo.loadIcon(context.getPackageManager()));
        ((android.widget.TextView) findViewById(android.R.id.orientation)).setText(context.getResources().getString(android.R.string.kg_login_username_hint, activityRecord.info.applicationInfo.loadLabel(context.getPackageManager()).toString()));
    }
}
