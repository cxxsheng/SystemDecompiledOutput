package android.app.slice;

/* loaded from: classes.dex */
public abstract class SliceProvider extends android.content.ContentProvider {
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_BIND_URI = "slice_uri";
    public static final java.lang.String EXTRA_INTENT = "slice_intent";
    public static final java.lang.String EXTRA_PKG = "pkg";
    public static final java.lang.String EXTRA_RESULT = "result";
    public static final java.lang.String EXTRA_SLICE = "slice";
    public static final java.lang.String EXTRA_SLICE_DESCENDANTS = "slice_descendants";
    public static final java.lang.String EXTRA_SUPPORTED_SPECS = "supported_specs";
    public static final java.lang.String METHOD_GET_DESCENDANTS = "get_descendants";
    public static final java.lang.String METHOD_GET_PERMISSIONS = "get_permissions";
    public static final java.lang.String METHOD_MAP_INTENT = "map_slice";
    public static final java.lang.String METHOD_MAP_ONLY_INTENT = "map_only";
    public static final java.lang.String METHOD_PIN = "pin";
    public static final java.lang.String METHOD_SLICE = "bind_slice";
    public static final java.lang.String METHOD_UNPIN = "unpin";
    private static final long SLICE_BIND_ANR = 2000;
    public static final java.lang.String SLICE_TYPE = "vnd.android.slice";
    private static final java.lang.String TAG = "SliceProvider";
    private final java.lang.Runnable mAnr;
    private final java.lang.String[] mAutoGrantPermissions;
    private java.lang.String mCallback;
    private android.app.slice.SliceManager mSliceManager;

    public SliceProvider(java.lang.String... strArr) {
        this.mAnr = new java.lang.Runnable() { // from class: android.app.slice.SliceProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.slice.SliceProvider.this.lambda$new$0();
            }
        };
        this.mAutoGrantPermissions = strArr;
    }

    public SliceProvider() {
        this.mAnr = new java.lang.Runnable() { // from class: android.app.slice.SliceProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.slice.SliceProvider.this.lambda$new$0();
            }
        };
        this.mAutoGrantPermissions = new java.lang.String[0];
    }

    @Override // android.content.ContentProvider
    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        this.mSliceManager = (android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class);
    }

    public android.app.slice.Slice onBindSlice(android.net.Uri uri, java.util.Set<android.app.slice.SliceSpec> set) {
        return null;
    }

    public void onSlicePinned(android.net.Uri uri) {
    }

    public void onSliceUnpinned(android.net.Uri uri) {
    }

    public java.util.Collection<android.net.Uri> onGetSliceDescendants(android.net.Uri uri) {
        return java.util.Collections.emptyList();
    }

    public android.net.Uri onMapIntentToUri(android.content.Intent intent) {
        throw new java.lang.UnsupportedOperationException("This provider has not implemented intent to uri mapping");
    }

    public android.app.PendingIntent onCreatePermissionRequest(android.net.Uri uri) {
        return createPermissionPendingIntent(getContext(), uri, getCallingPackage());
    }

    @Override // android.content.ContentProvider
    public final int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        return null;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final java.lang.String getType(android.net.Uri uri) {
        return SLICE_TYPE;
    }

    @Override // android.content.ContentProvider
    public android.os.Bundle call(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        if (str.equals(METHOD_SLICE)) {
            android.app.slice.Slice handleBindSlice = handleBindSlice(getUriWithoutUserId(validateIncomingUriOrNull((android.net.Uri) bundle.getParcelable("slice_uri", android.net.Uri.class))), bundle.getParcelableArrayList(EXTRA_SUPPORTED_SPECS, android.app.slice.SliceSpec.class), getCallingPackage(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putParcelable("slice", handleBindSlice);
            return bundle2;
        }
        if (str.equals(METHOD_MAP_INTENT)) {
            android.content.Intent intent = (android.content.Intent) bundle.getParcelable(EXTRA_INTENT, android.content.Intent.class);
            if (intent == null) {
                return null;
            }
            android.net.Uri validateIncomingUriOrNull = validateIncomingUriOrNull(onMapIntentToUri(intent));
            java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList(EXTRA_SUPPORTED_SPECS, android.app.slice.SliceSpec.class);
            android.os.Bundle bundle3 = new android.os.Bundle();
            if (validateIncomingUriOrNull != null) {
                bundle3.putParcelable("slice", handleBindSlice(validateIncomingUriOrNull, parcelableArrayList, getCallingPackage(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid()));
            } else {
                bundle3.putParcelable("slice", null);
            }
            return bundle3;
        }
        if (str.equals(METHOD_MAP_ONLY_INTENT)) {
            android.content.Intent intent2 = (android.content.Intent) bundle.getParcelable(EXTRA_INTENT, android.content.Intent.class);
            if (intent2 == null) {
                return null;
            }
            android.net.Uri validateIncomingUriOrNull2 = validateIncomingUriOrNull(onMapIntentToUri(intent2));
            android.os.Bundle bundle4 = new android.os.Bundle();
            bundle4.putParcelable("slice", validateIncomingUriOrNull2);
            return bundle4;
        }
        if (str.equals(METHOD_PIN)) {
            android.net.Uri uriWithoutUserId = getUriWithoutUserId(validateIncomingUriOrNull((android.net.Uri) bundle.getParcelable("slice_uri", android.net.Uri.class)));
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Only the system can pin/unpin slices");
            }
            handlePinSlice(uriWithoutUserId);
        } else if (str.equals(METHOD_UNPIN)) {
            android.net.Uri uriWithoutUserId2 = getUriWithoutUserId(validateIncomingUriOrNull((android.net.Uri) bundle.getParcelable("slice_uri", android.net.Uri.class)));
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Only the system can pin/unpin slices");
            }
            handleUnpinSlice(uriWithoutUserId2);
        } else {
            if (str.equals(METHOD_GET_DESCENDANTS)) {
                android.net.Uri uriWithoutUserId3 = getUriWithoutUserId(validateIncomingUriOrNull((android.net.Uri) bundle.getParcelable("slice_uri", android.net.Uri.class)));
                android.os.Bundle bundle5 = new android.os.Bundle();
                bundle5.putParcelableArrayList(EXTRA_SLICE_DESCENDANTS, new java.util.ArrayList<>(handleGetDescendants(uriWithoutUserId3)));
                return bundle5;
            }
            if (str.equals(METHOD_GET_PERMISSIONS)) {
                if (android.os.Binder.getCallingUid() != 1000) {
                    throw new java.lang.SecurityException("Only the system can get permissions");
                }
                android.os.Bundle bundle6 = new android.os.Bundle();
                bundle6.putStringArray("result", this.mAutoGrantPermissions);
                return bundle6;
            }
        }
        return super.call(str, str2, bundle);
    }

    private android.net.Uri validateIncomingUriOrNull(android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        return validateIncomingUri(uri);
    }

    private java.util.Collection<android.net.Uri> handleGetDescendants(android.net.Uri uri) {
        this.mCallback = "onGetSliceDescendants";
        return onGetSliceDescendants(uri);
    }

    private void handlePinSlice(android.net.Uri uri) {
        this.mCallback = "onSlicePinned";
        android.os.Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            onSlicePinned(uri);
        } finally {
            android.os.Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private void handleUnpinSlice(android.net.Uri uri) {
        this.mCallback = "onSliceUnpinned";
        android.os.Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            onSliceUnpinned(uri);
        } finally {
            android.os.Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private android.app.slice.Slice handleBindSlice(android.net.Uri uri, java.util.List<android.app.slice.SliceSpec> list, java.lang.String str, int i, int i2) {
        if (str == null) {
            str = getContext().getPackageManager().getNameForUid(i);
        }
        try {
            this.mSliceManager.enforceSlicePermission(uri, i2, i, this.mAutoGrantPermissions);
            this.mCallback = "onBindSlice";
            android.os.Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
            try {
                return onBindSliceStrict(uri, list);
            } finally {
                android.os.Handler.getMain().removeCallbacks(this.mAnr);
            }
        } catch (java.lang.SecurityException e) {
            return createPermissionSlice(getContext(), uri, str);
        }
    }

    public android.app.slice.Slice createPermissionSlice(android.content.Context context, android.net.Uri uri, java.lang.String str) {
        this.mCallback = "onCreatePermissionRequest";
        android.os.Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            android.app.PendingIntent onCreatePermissionRequest = onCreatePermissionRequest(uri);
            android.os.Handler.getMain().removeCallbacks(this.mAnr);
            android.app.slice.Slice.Builder builder = new android.app.slice.Slice.Builder(uri, null);
            android.app.slice.Slice.Builder addAction = new android.app.slice.Slice.Builder(builder).addIcon(android.graphics.drawable.Icon.createWithResource(context, com.android.internal.R.drawable.ic_permission), null, java.util.Collections.emptyList()).addHints(java.util.Arrays.asList("title", "shortcut")).addAction(onCreatePermissionRequest, new android.app.slice.Slice.Builder(builder).build(), null);
            android.util.TypedValue typedValue = new android.util.TypedValue();
            new android.view.ContextThemeWrapper(context, 16974123).getTheme().resolveAttribute(16843829, typedValue, true);
            builder.addSubSlice(new android.app.slice.Slice.Builder(uri.buildUpon().appendPath("permission").build(), null).addIcon(android.graphics.drawable.Icon.createWithResource(context, com.android.internal.R.drawable.ic_arrow_forward), null, java.util.Collections.emptyList()).addText(getPermissionString(context, str), null, java.util.Collections.emptyList()).addInt(typedValue.data, "color", java.util.Collections.emptyList()).addSubSlice(addAction.build(), null).build(), null);
            return builder.addHints(java.util.Arrays.asList(android.app.slice.Slice.HINT_PERMISSION_REQUEST)).build();
        } catch (java.lang.Throwable th) {
            android.os.Handler.getMain().removeCallbacks(this.mAnr);
            throw th;
        }
    }

    public static android.app.PendingIntent createPermissionPendingIntent(android.content.Context context, android.net.Uri uri, java.lang.String str) {
        return android.app.PendingIntent.getActivity(context, 0, createPermissionIntent(context, uri, str), 67108864);
    }

    public static android.content.Intent createPermissionIntent(android.content.Context context, android.net.Uri uri, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(android.app.slice.SliceManager.ACTION_REQUEST_SLICE_PERMISSION);
        intent.setComponent(android.content.ComponentName.unflattenFromString(context.getResources().getString(com.android.internal.R.string.config_slicePermissionComponent)));
        intent.putExtra("slice_uri", uri);
        intent.putExtra("pkg", str);
        intent.setData(uri.buildUpon().appendQueryParameter("package", str).build());
        return intent;
    }

    public static java.lang.CharSequence getPermissionString(android.content.Context context, java.lang.String str) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            return context.getString(com.android.internal.R.string.slices_permission_request, packageManager.getApplicationInfo(str, 0).loadLabel(packageManager), context.getApplicationInfo().loadLabel(packageManager));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Unknown calling app", e);
        }
    }

    private android.app.slice.Slice onBindSliceStrict(android.net.Uri uri, java.util.List<android.app.slice.SliceSpec> list) {
        android.os.StrictMode.ThreadPolicy threadPolicy = android.os.StrictMode.getThreadPolicy();
        try {
            android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().detectAll().penaltyDeath().build());
            return onBindSlice(uri, new android.util.ArraySet(list));
        } finally {
            android.os.StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        android.os.Process.sendSignal(android.os.Process.myPid(), 3);
        android.util.Log.wtf(TAG, "Timed out while handling slice callback " + this.mCallback);
    }
}
