package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public class AccessibilityServiceWarning {
    public static android.app.AlertDialog createAccessibilityServiceWarningDialog(android.content.Context context, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, android.view.View.OnClickListener onClickListener, android.view.View.OnClickListener onClickListener2, android.view.View.OnClickListener onClickListener3) {
        android.app.AlertDialog create = new android.app.AlertDialog.Builder(context).setView(createAccessibilityServiceWarningDialogContentView(context, accessibilityServiceInfo, onClickListener, onClickListener2, onClickListener3)).setCancelable(true).create();
        android.view.Window window = create.getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags |= 524288;
        attributes.type = 2008;
        window.setAttributes(attributes);
        return create;
    }

    public static android.view.View createAccessibilityServiceWarningDialogContentView(android.content.Context context, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, android.view.View.OnClickListener onClickListener, android.view.View.OnClickListener onClickListener2, android.view.View.OnClickListener onClickListener3) {
        android.graphics.drawable.Drawable loadIcon;
        android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.view.LayoutInflater.class)).inflate(com.android.internal.R.layout.accessibility_service_warning, (android.view.ViewGroup) null);
        if (accessibilityServiceInfo.getResolveInfo().getIconResource() == 0) {
            loadIcon = context.getDrawable(com.android.internal.R.drawable.ic_accessibility_generic);
        } else {
            loadIcon = accessibilityServiceInfo.getResolveInfo().loadIcon(context.getPackageManager());
        }
        ((android.widget.ImageView) inflate.findViewById(com.android.internal.R.id.accessibility_permissionDialog_icon)).lambda$setImageURIAsync$2(loadIcon);
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.accessibility_permissionDialog_title)).lambda$setTextAsync$0(context.getString(com.android.internal.R.string.accessibility_enable_service_title, getServiceName(context, accessibilityServiceInfo)));
        android.widget.Button button = (android.widget.Button) inflate.findViewById(com.android.internal.R.id.accessibility_permission_enable_allow_button);
        android.widget.Button button2 = (android.widget.Button) inflate.findViewById(com.android.internal.R.id.accessibility_permission_enable_deny_button);
        button.setOnClickListener(onClickListener);
        button.setOnTouchListener(getTouchConsumingListener());
        button2.setOnClickListener(onClickListener2);
        android.widget.Button button3 = (android.widget.Button) inflate.findViewById(com.android.internal.R.id.accessibility_permission_enable_uninstall_button);
        if (!accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.isSystemApp()) {
            button3.setVisibility(0);
            button3.setOnClickListener(onClickListener3);
        }
        return inflate;
    }

    public static android.view.View.OnTouchListener getTouchConsumingListener() {
        return new android.view.View.OnTouchListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityServiceWarning$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
                return com.android.internal.accessibility.dialog.AccessibilityServiceWarning.lambda$getTouchConsumingListener$0(view, motionEvent);
            }
        };
    }

    static /* synthetic */ boolean lambda$getTouchConsumingListener$0(android.view.View view, android.view.MotionEvent motionEvent) {
        if ((motionEvent.getFlags() & 1) == 0 && (motionEvent.getFlags() & 2) == 0) {
            return false;
        }
        if (motionEvent.getAction() == 1) {
            android.widget.Toast.makeText(view.getContext(), com.android.internal.R.string.accessibility_dialog_touch_filtered_warning, 0).show();
        }
        return true;
    }

    private static java.lang.CharSequence getServiceName(android.content.Context context, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        java.util.Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        return android.text.BidiFormatter.getInstance(locale).unicodeWrap(accessibilityServiceInfo.getResolveInfo().loadLabel(context.getPackageManager()));
    }
}
