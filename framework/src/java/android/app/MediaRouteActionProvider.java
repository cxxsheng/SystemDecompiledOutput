package android.app;

/* loaded from: classes.dex */
public class MediaRouteActionProvider extends android.view.ActionProvider {
    private static final java.lang.String TAG = "MediaRouteActionProvider";
    private android.app.MediaRouteButton mButton;
    private final android.app.MediaRouteActionProvider.MediaRouterCallback mCallback;
    private final android.content.Context mContext;
    private android.view.View.OnClickListener mExtendedSettingsListener;
    private int mRouteTypes;
    private final android.media.MediaRouter mRouter;

    public MediaRouteActionProvider(android.content.Context context) {
        super(context);
        this.mContext = context;
        this.mRouter = (android.media.MediaRouter) context.getSystemService(android.content.Context.MEDIA_ROUTER_SERVICE);
        this.mCallback = new android.app.MediaRouteActionProvider.MediaRouterCallback(this);
        setRouteTypes(1);
    }

    public void setRouteTypes(int i) {
        if (this.mRouteTypes != i) {
            if (this.mRouteTypes != 0) {
                this.mRouter.removeCallback(this.mCallback);
            }
            this.mRouteTypes = i;
            if (i != 0) {
                this.mRouter.addCallback(i, this.mCallback, 8);
            }
            refreshRoute();
            if (this.mButton != null) {
                this.mButton.setRouteTypes(this.mRouteTypes);
            }
        }
    }

    public void setExtendedSettingsClickListener(android.view.View.OnClickListener onClickListener) {
        this.mExtendedSettingsListener = onClickListener;
        if (this.mButton != null) {
            this.mButton.setExtendedSettingsClickListener(onClickListener);
        }
    }

    @Override // android.view.ActionProvider
    public android.view.View onCreateActionView() {
        throw new java.lang.UnsupportedOperationException("Use onCreateActionView(MenuItem) instead.");
    }

    @Override // android.view.ActionProvider
    public android.view.View onCreateActionView(android.view.MenuItem menuItem) {
        if (this.mButton != null) {
            android.util.Log.e(TAG, "onCreateActionView: this ActionProvider is already associated with a menu item. Don't reuse MediaRouteActionProvider instances! Abandoning the old one...");
        }
        this.mButton = new android.app.MediaRouteButton(this.mContext);
        this.mButton.setRouteTypes(this.mRouteTypes);
        this.mButton.setExtendedSettingsClickListener(this.mExtendedSettingsListener);
        this.mButton.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -1));
        return this.mButton;
    }

    @Override // android.view.ActionProvider
    public boolean onPerformDefaultAction() {
        if (this.mButton != null) {
            return this.mButton.showDialogInternal();
        }
        return false;
    }

    @Override // android.view.ActionProvider
    public boolean overridesItemVisibility() {
        return true;
    }

    @Override // android.view.ActionProvider
    public boolean isVisible() {
        return this.mRouter.isRouteAvailable(this.mRouteTypes, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshRoute() {
        refreshVisibility();
    }

    private static class MediaRouterCallback extends android.media.MediaRouter.SimpleCallback {
        private final java.lang.ref.WeakReference<android.app.MediaRouteActionProvider> mProviderWeak;

        public MediaRouterCallback(android.app.MediaRouteActionProvider mediaRouteActionProvider) {
            this.mProviderWeak = new java.lang.ref.WeakReference<>(mediaRouteActionProvider);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteAdded(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteRemoved(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            refreshRoute(mediaRouter);
        }

        private void refreshRoute(android.media.MediaRouter mediaRouter) {
            android.app.MediaRouteActionProvider mediaRouteActionProvider = this.mProviderWeak.get();
            if (mediaRouteActionProvider != null) {
                mediaRouteActionProvider.refreshRoute();
            } else {
                mediaRouter.removeCallback(this);
            }
        }
    }
}
