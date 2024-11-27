package android.credentials.selection;

/* loaded from: classes.dex */
public class IntentFactory {
    private static final java.lang.String TAG = "CredManIntentHelper";

    public static android.content.Intent createCredentialSelectorIntentForAutofill(android.content.Context context, android.credentials.selection.RequestInfo requestInfo, java.util.ArrayList<android.credentials.selection.DisabledProviderData> arrayList, android.os.ResultReceiver resultReceiver) {
        return createCredentialSelectorIntent(context, requestInfo, arrayList, resultReceiver);
    }

    private static android.content.Intent createCredentialSelectorIntent(android.content.Context context, android.credentials.selection.RequestInfo requestInfo, java.util.ArrayList<android.credentials.selection.DisabledProviderData> arrayList, android.os.ResultReceiver resultReceiver) {
        android.content.Intent intent = new android.content.Intent();
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_credentialManagerDialogComponent));
        android.content.ComponentName oemOverrideComponentName = getOemOverrideComponentName(context);
        if (oemOverrideComponentName != null) {
            unflattenFromString = oemOverrideComponentName;
        }
        intent.setComponent(unflattenFromString);
        intent.putParcelableArrayListExtra(android.credentials.selection.ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, arrayList);
        intent.putExtra(android.credentials.selection.RequestInfo.EXTRA_REQUEST_INFO, requestInfo);
        intent.putExtra(android.credentials.selection.Constants.EXTRA_RESULT_RECEIVER, toIpcFriendlyResultReceiver(resultReceiver));
        return intent;
    }

    private static android.content.ComponentName getOemOverrideComponentName(android.content.Context context) {
        android.content.ComponentName componentName = null;
        if (android.credentials.flags.Flags.configurableSelectorUiEnabled() && android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_enableOemCredentialManagerDialogComponent)) {
            java.lang.String string = android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_oemCredentialManagerDialogComponent);
            if (!android.text.TextUtils.isEmpty(string)) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(string);
                if (unflattenFromString != null) {
                    try {
                        android.content.pm.ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(unflattenFromString, android.content.pm.PackageManager.ComponentInfoFlags.of(1048576L));
                        if (activityInfo.enabled && activityInfo.exported) {
                            android.util.Slog.i(TAG, "Found enabled oem CredMan UI component." + string);
                            componentName = unflattenFromString;
                        } else {
                            android.util.Slog.i(TAG, "Found enabled oem CredMan UI component but it was not enabled.");
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.i(TAG, "Unable to find oem CredMan UI component: " + string + android.media.MediaMetrics.SEPARATOR);
                    }
                } else {
                    android.util.Slog.i(TAG, "Invalid OEM ComponentName format.");
                }
            } else {
                android.util.Slog.i(TAG, "Invalid empty OEM component name.");
            }
        }
        return componentName;
    }

    public static android.content.Intent createCredentialSelectorIntent(android.content.Context context, android.credentials.selection.RequestInfo requestInfo, java.util.ArrayList<android.credentials.selection.ProviderData> arrayList, java.util.ArrayList<android.credentials.selection.DisabledProviderData> arrayList2, android.os.ResultReceiver resultReceiver) {
        android.content.Intent createCredentialSelectorIntent = createCredentialSelectorIntent(context, requestInfo, arrayList2, resultReceiver);
        createCredentialSelectorIntent.putParcelableArrayListExtra(android.credentials.selection.ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, arrayList);
        return createCredentialSelectorIntent;
    }

    public static android.content.Intent createCancelUiIntent(android.os.IBinder iBinder, boolean z, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(android.content.ComponentName.unflattenFromString(android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_credentialManagerDialogComponent)));
        intent.putExtra(android.credentials.selection.CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST, new android.credentials.selection.CancelSelectionRequest(new android.credentials.selection.RequestToken(iBinder), z, str));
        return intent;
    }

    private static <T extends android.os.ResultReceiver> android.os.ResultReceiver toIpcFriendlyResultReceiver(T t) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        t.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        android.os.ResultReceiver createFromParcel = android.os.ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    private IntentFactory() {
    }
}
