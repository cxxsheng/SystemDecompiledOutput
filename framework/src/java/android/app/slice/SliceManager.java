package android.app.slice;

/* loaded from: classes.dex */
public class SliceManager {
    public static final java.lang.String ACTION_REQUEST_SLICE_PERMISSION = "com.android.intent.action.REQUEST_SLICE_PERMISSION";
    public static final java.lang.String CATEGORY_SLICE = "android.app.slice.category.SLICE";
    public static final java.lang.String SLICE_METADATA_KEY = "android.metadata.SLICE_URI";
    private static final java.lang.String TAG = "SliceManager";
    private final android.content.Context mContext;
    private final android.os.IBinder mToken = new android.os.Binder();
    private final android.app.slice.ISliceManager mService = android.app.slice.ISliceManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("slice"));

    public SliceManager(android.content.Context context, android.os.Handler handler) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public void pinSlice(android.net.Uri uri, java.util.Set<android.app.slice.SliceSpec> set) {
        try {
            this.mService.pinSlice(this.mContext.getPackageName(), uri, (android.app.slice.SliceSpec[]) set.toArray(new android.app.slice.SliceSpec[set.size()]), this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unpinSlice(android.net.Uri uri) {
        try {
            this.mService.unpinSlice(this.mContext.getPackageName(), uri, this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasSliceAccess() {
        try {
            return this.mService.hasSliceAccess(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<android.app.slice.SliceSpec> getPinnedSpecs(android.net.Uri uri) {
        try {
            return new android.util.ArraySet(java.util.Arrays.asList(this.mService.getPinnedSpecs(uri, this.mContext.getPackageName())));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.net.Uri> getPinnedSlices() {
        try {
            return java.util.Arrays.asList(this.mService.getPinnedSlices(this.mContext.getPackageName()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Collection<android.net.Uri> getSliceDescendants(android.net.Uri uri) {
        android.content.ContentProviderClient acquireUnstableContentProviderClient;
        try {
            acquireUnstableContentProviderClient = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            try {
            } finally {
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to get slice descendants", e);
        }
        if (acquireUnstableContentProviderClient == null) {
            android.util.Log.w(TAG, android.text.TextUtils.formatSimple("Unknown URI: %s", uri));
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
            return java.util.Collections.emptyList();
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("slice_uri", uri);
        java.util.ArrayList parcelableArrayList = acquireUnstableContentProviderClient.call(android.app.slice.SliceProvider.METHOD_GET_DESCENDANTS, null, bundle).getParcelableArrayList(android.app.slice.SliceProvider.EXTRA_SLICE_DESCENDANTS, android.net.Uri.class);
        if (acquireUnstableContentProviderClient != null) {
            acquireUnstableContentProviderClient.close();
        }
        return parcelableArrayList;
    }

    public android.app.slice.Slice bindSlice(android.net.Uri uri, java.util.Set<android.app.slice.SliceSpec> set) {
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            android.content.ContentProviderClient acquireUnstableContentProviderClient = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            try {
                if (acquireUnstableContentProviderClient == null) {
                    android.util.Log.w(TAG, java.lang.String.format("Unknown URI: %s", uri));
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable("slice_uri", uri);
                bundle.putParcelableArrayList(android.app.slice.SliceProvider.EXTRA_SUPPORTED_SPECS, new java.util.ArrayList<>(set));
                android.os.Bundle call = acquireUnstableContentProviderClient.call(android.app.slice.SliceProvider.METHOD_SLICE, null, bundle);
                android.os.Bundle.setDefusable(call, true);
                if (call == null) {
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.app.slice.Slice slice = (android.app.slice.Slice) call.getParcelable("slice", android.app.slice.Slice.class);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.close();
                }
                return slice;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public android.net.Uri mapIntentToUri(android.content.Intent intent) {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.net.Uri resolveStatic = resolveStatic(intent, contentResolver);
        if (resolveStatic != null) {
            return resolveStatic;
        }
        java.lang.String authority = getAuthority(intent);
        if (authority == null) {
            return null;
        }
        android.net.Uri build = new android.net.Uri.Builder().scheme("content").authority(authority).build();
        try {
            android.content.ContentProviderClient acquireUnstableContentProviderClient = contentResolver.acquireUnstableContentProviderClient(build);
            try {
                if (acquireUnstableContentProviderClient == null) {
                    android.util.Log.w(TAG, java.lang.String.format("Unknown URI: %s", build));
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable(android.app.slice.SliceProvider.EXTRA_INTENT, intent);
                android.os.Bundle call = acquireUnstableContentProviderClient.call(android.app.slice.SliceProvider.METHOD_MAP_ONLY_INTENT, null, bundle);
                if (call == null) {
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.net.Uri uri = (android.net.Uri) call.getParcelable("slice", android.net.Uri.class);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.close();
                }
                return uri;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    private java.lang.String getAuthority(android.content.Intent intent) {
        android.content.Intent intent2 = new android.content.Intent(intent);
        if (!intent2.hasCategory(CATEGORY_SLICE)) {
            intent2.addCategory(CATEGORY_SLICE);
        }
        java.util.List<android.content.pm.ResolveInfo> queryIntentContentProviders = this.mContext.getPackageManager().queryIntentContentProviders(intent2, 0);
        if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
            return null;
        }
        return queryIntentContentProviders.get(0).providerInfo.authority;
    }

    private android.net.Uri resolveStatic(android.content.Intent intent, android.content.ContentResolver contentResolver) {
        java.util.Objects.requireNonNull(intent, "intent");
        com.android.internal.util.Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        android.net.Uri data = intent.getData();
        if (data != null && android.app.slice.SliceProvider.SLICE_TYPE.equals(contentResolver.getType(data))) {
            return data;
        }
        android.content.pm.ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 128);
        if (resolveActivity != null && resolveActivity.activityInfo != null && resolveActivity.activityInfo.metaData != null && resolveActivity.activityInfo.metaData.containsKey(SLICE_METADATA_KEY)) {
            return android.net.Uri.parse(resolveActivity.activityInfo.metaData.getString(SLICE_METADATA_KEY));
        }
        return null;
    }

    public android.app.slice.Slice bindSlice(android.content.Intent intent, java.util.Set<android.app.slice.SliceSpec> set) {
        java.util.Objects.requireNonNull(intent, "intent");
        com.android.internal.util.Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.net.Uri resolveStatic = resolveStatic(intent, contentResolver);
        if (resolveStatic != null) {
            return bindSlice(resolveStatic, set);
        }
        java.lang.String authority = getAuthority(intent);
        if (authority == null) {
            return null;
        }
        android.net.Uri build = new android.net.Uri.Builder().scheme("content").authority(authority).build();
        try {
            android.content.ContentProviderClient acquireUnstableContentProviderClient = contentResolver.acquireUnstableContentProviderClient(build);
            try {
                if (acquireUnstableContentProviderClient == null) {
                    android.util.Log.w(TAG, java.lang.String.format("Unknown URI: %s", build));
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable(android.app.slice.SliceProvider.EXTRA_INTENT, intent);
                bundle.putParcelableArrayList(android.app.slice.SliceProvider.EXTRA_SUPPORTED_SPECS, new java.util.ArrayList<>(set));
                android.os.Bundle call = acquireUnstableContentProviderClient.call(android.app.slice.SliceProvider.METHOD_MAP_INTENT, null, bundle);
                if (call == null) {
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
                android.app.slice.Slice slice = (android.app.slice.Slice) call.getParcelable("slice", android.app.slice.Slice.class);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.close();
                }
                return slice;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public int checkSlicePermission(android.net.Uri uri, int i, int i2) {
        try {
            return this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), i, i2, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantSlicePermission(java.lang.String str, android.net.Uri uri) {
        try {
            this.mService.grantSlicePermission(this.mContext.getPackageName(), str, uri);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeSlicePermission(java.lang.String str, android.net.Uri uri) {
        try {
            this.mService.revokeSlicePermission(this.mContext.getPackageName(), str, uri);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enforceSlicePermission(android.net.Uri uri, int i, int i2, java.lang.String[] strArr) {
        try {
            if (!android.os.UserHandle.isSameApp(i2, android.os.Process.myUid()) && this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), i, i2, strArr) == -1) {
                throw new java.lang.SecurityException("User " + i2 + " does not have slice permission for " + uri + android.media.MediaMetrics.SEPARATOR);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermissionFromUser(android.net.Uri uri, java.lang.String str, boolean z) {
        try {
            this.mService.grantPermissionFromUser(uri, str, this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
