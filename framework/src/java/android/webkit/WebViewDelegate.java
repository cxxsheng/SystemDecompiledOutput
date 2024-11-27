package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class WebViewDelegate {

    public interface OnTraceEnabledChangeListener {
        void onTraceEnabledChange(boolean z);
    }

    WebViewDelegate() {
    }

    public void setOnTraceEnabledChangeListener(final android.webkit.WebViewDelegate.OnTraceEnabledChangeListener onTraceEnabledChangeListener) {
        android.os.SystemProperties.addChangeCallback(new java.lang.Runnable() { // from class: android.webkit.WebViewDelegate.1
            @Override // java.lang.Runnable
            public void run() {
                onTraceEnabledChangeListener.onTraceEnabledChange(android.webkit.WebViewDelegate.this.isTraceTagEnabled());
            }
        });
    }

    public boolean isTraceTagEnabled() {
        return android.os.Trace.isTagEnabled(16L);
    }

    @java.lang.Deprecated
    public boolean canInvokeDrawGlFunctor(android.view.View view) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    public void invokeDrawGlFunctor(android.view.View view, long j, boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    public void callDrawGlFunction(android.graphics.Canvas canvas, long j) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Deprecated
    public void callDrawGlFunction(android.graphics.Canvas canvas, long j, java.lang.Runnable runnable) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void drawWebViewFunctor(android.graphics.Canvas canvas, int i) {
        if (!(canvas instanceof android.graphics.RecordingCanvas)) {
            throw new java.lang.IllegalArgumentException(canvas.getClass().getName() + " is not a RecordingCanvas canvas");
        }
        ((android.graphics.RecordingCanvas) canvas).drawWebViewFunctor(i);
    }

    @java.lang.Deprecated
    public void detachDrawGlFunctor(android.view.View view, long j) {
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (j != 0 && viewRootImpl != null) {
            viewRootImpl.detachFunctor(j);
        }
    }

    public int getPackageId(android.content.res.Resources resources, java.lang.String str) {
        android.util.SparseArray<java.lang.String> assignedPackageIdentifiers = resources.getAssets().getAssignedPackageIdentifiers();
        for (int i = 0; i < assignedPackageIdentifiers.size(); i++) {
            if (str.equals(assignedPackageIdentifiers.valueAt(i))) {
                return assignedPackageIdentifiers.keyAt(i);
            }
        }
        throw new java.lang.RuntimeException("Package not found: " + str);
    }

    public android.app.Application getApplication() {
        return android.app.ActivityThread.currentApplication();
    }

    public java.lang.String getErrorString(android.content.Context context, int i) {
        return android.webkit.LegacyErrorStrings.getString(i, context);
    }

    public void addWebViewAssetPath(android.content.Context context) {
        java.lang.String[] allApkPaths = android.webkit.WebViewFactory.getLoadedPackageInfo().applicationInfo.getAllApkPaths();
        android.content.pm.ApplicationInfo applicationInfo = context.getApplicationInfo();
        java.lang.String[] strArr = applicationInfo.sharedLibraryFiles;
        for (java.lang.String str : allApkPaths) {
            strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr, str);
        }
        if (strArr != applicationInfo.sharedLibraryFiles) {
            applicationInfo.sharedLibraryFiles = strArr;
            android.app.ResourcesManager.getInstance().appendLibAssetsForMainAssetPath(applicationInfo.getBaseResourcePath(), allApkPaths);
        }
    }

    public boolean isMultiProcessEnabled() {
        if (android.webkit.Flags.updateServiceV2() || android.webkit.Flags.updateServiceIpcWrapper()) {
            return true;
        }
        try {
            return android.webkit.WebViewFactory.getUpdateService().isMultiProcessEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getDataDirectorySuffix() {
        return android.webkit.WebViewFactory.getDataDirectorySuffix();
    }

    public android.webkit.WebViewFactory.StartupTimestamps getStartupTimestamps() {
        return android.webkit.WebViewFactory.getStartupTimestamps();
    }
}
