package com.android.internal.policy;

/* loaded from: classes5.dex */
public class DecorContext extends android.view.ContextThemeWrapper {
    private android.view.contentcapture.ContentCaptureManager mContentCaptureManager;
    private java.lang.ref.WeakReference<android.content.Context> mContext;
    private com.android.internal.policy.PhoneWindow mPhoneWindow;
    private android.content.res.Resources mResources;

    public DecorContext(android.content.Context context, com.android.internal.policy.PhoneWindow phoneWindow) {
        super((android.content.Context) null, (android.content.res.Resources.Theme) null);
        android.content.Context createDisplayContext;
        setPhoneWindow(phoneWindow);
        android.view.Display displayNoVerify = phoneWindow.getContext().getDisplayNoVerify();
        if (displayNoVerify.getDisplayId() == 0) {
            createDisplayContext = context.createConfigurationContext(android.content.res.Configuration.EMPTY);
            createDisplayContext.updateDisplay(0);
        } else {
            createDisplayContext = context.createDisplayContext(displayNoVerify);
        }
        attachBaseContext(createDisplayContext);
    }

    void setPhoneWindow(com.android.internal.policy.PhoneWindow phoneWindow) {
        this.mPhoneWindow = phoneWindow;
        android.content.Context context = phoneWindow.getContext();
        this.mContext = new java.lang.ref.WeakReference<>(context);
        this.mResources = context.getResources();
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (android.content.Context.WINDOW_SERVICE.equals(str)) {
            return this.mPhoneWindow.getWindowManager();
        }
        android.content.Context context = this.mContext.get();
        if (!android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(str)) {
            return context != null ? context.getSystemService(str) : super.getSystemService(str);
        }
        if (context != null && this.mContentCaptureManager == null) {
            this.mContentCaptureManager = (android.view.contentcapture.ContentCaptureManager) context.getSystemService(str);
        }
        return this.mContentCaptureManager;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public android.content.res.Resources getResources() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            this.mResources = context.getResources();
        }
        return this.mResources;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public android.content.res.AssetManager getAssets() {
        return this.mResources.getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.AutofillOptions getAutofillOptions() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            return context.getAutofillOptions();
        }
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.ContentCaptureOptions getContentCaptureOptions() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            return context.getContentCaptureOptions();
        }
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean isUiContext() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            return context.isUiContext();
        }
        return false;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean isConfigurationContext() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            return context.isConfigurationContext();
        }
        return false;
    }
}
