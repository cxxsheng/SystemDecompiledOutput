package com.android.server.autofill;

/* loaded from: classes.dex */
public final class RemoteInlineSuggestionRenderService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.autofill.RemoteInlineSuggestionRenderService, android.service.autofill.IInlineSuggestionRenderService> {
    private static final java.lang.String TAG = "RemoteInlineSuggestionRenderService";
    private final long mIdleUnbindTimeoutMs;

    interface InlineSuggestionRenderCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.autofill.RemoteInlineSuggestionRenderService> {
    }

    RemoteInlineSuggestionRenderService(android.content.Context context, android.content.ComponentName componentName, java.lang.String str, int i, com.android.server.autofill.RemoteInlineSuggestionRenderService.InlineSuggestionRenderCallbacks inlineSuggestionRenderCallbacks, boolean z, boolean z2) {
        super(context, str, componentName, i, inlineSuggestionRenderCallbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 2);
        this.mIdleUnbindTimeoutMs = 0L;
        ensureBound();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.autofill.IInlineSuggestionRenderService getServiceInterface(@android.annotation.NonNull android.os.IBinder iBinder) {
        return android.service.autofill.IInlineSuggestionRenderService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return 0L;
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (z && getTimeoutIdleBindMillis() != 0) {
            scheduleUnbind();
        }
        super.handleOnConnectedStateChanged(z);
    }

    public void ensureBound() {
        scheduleBind();
    }

    public void renderSuggestion(@android.annotation.NonNull final android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback, @android.annotation.NonNull final android.service.autofill.InlinePresentation inlinePresentation, final int i, final int i2, @android.annotation.Nullable final android.os.IBinder iBinder, final int i3, final int i4, final int i5) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.autofill.IInlineSuggestionRenderService) iInterface).renderSuggestion(iInlineSuggestionUiCallback, inlinePresentation, i, i2, iBinder, i3, i4, i5);
            }
        });
    }

    public void getInlineSuggestionsRendererInfo(@android.annotation.NonNull final android.os.RemoteCallback remoteCallback) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.autofill.IInlineSuggestionRenderService) iInterface).getInlineSuggestionsRendererInfo(remoteCallback);
            }
        });
    }

    public void destroySuggestionViews(final int i, final int i2) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda2
            public final void run(android.os.IInterface iInterface) {
                ((android.service.autofill.IInlineSuggestionRenderService) iInterface).destroySuggestionViews(i, i2);
            }
        });
    }

    @android.annotation.Nullable
    private static android.content.pm.ServiceInfo getServiceInfo(android.content.Context context, int i) {
        java.lang.String servicesSystemSharedLibraryPackageName = context.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            android.util.Slog.w(TAG, "no external services package!");
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.service.autofill.InlineSuggestionRenderService");
        intent.setPackage(servicesSystemSharedLibraryPackageName);
        android.content.pm.ResolveInfo resolveServiceAsUser = context.getPackageManager().resolveServiceAsUser(intent, 132, i);
        android.content.pm.ServiceInfo serviceInfo = resolveServiceAsUser == null ? null : resolveServiceAsUser.serviceInfo;
        if (resolveServiceAsUser == null || serviceInfo == null) {
            android.util.Slog.w(TAG, "No valid components found.");
            return null;
        }
        if (!"android.permission.BIND_INLINE_SUGGESTION_RENDER_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.w(TAG, serviceInfo.name + " does not require permission android.permission.BIND_INLINE_SUGGESTION_RENDER_SERVICE");
            return null;
        }
        return serviceInfo;
    }

    @android.annotation.Nullable
    public static android.content.ComponentName getServiceComponentName(android.content.Context context, int i) {
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo(context, i);
        if (serviceInfo == null) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "getServiceComponentName(): " + componentName);
        }
        return componentName;
    }
}
