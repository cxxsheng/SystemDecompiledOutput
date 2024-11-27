package com.android.internal.app;

/* loaded from: classes4.dex */
public abstract class MediaRouteDialogPresenter {
    private static final java.lang.String CHOOSER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteChooserDialogFragment";
    private static final java.lang.String CONTROLLER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteControllerDialogFragment";
    private static final java.lang.String TAG = "MediaRouter";

    public static android.app.DialogFragment showDialogFragment(android.app.Activity activity, int i, android.view.View.OnClickListener onClickListener) {
        android.media.MediaRouter mediaRouter = (android.media.MediaRouter) activity.getSystemService(android.content.Context.MEDIA_ROUTER_SERVICE);
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.media.MediaRouter.RouteInfo selectedRoute = mediaRouter.getSelectedRoute();
        if (selectedRoute.isDefault() || !selectedRoute.matchesTypes(i)) {
            if (fragmentManager.findFragmentByTag(CHOOSER_FRAGMENT_TAG) != null) {
                android.util.Log.w(TAG, "showDialog(): Route chooser dialog already showing!");
                return null;
            }
            com.android.internal.app.MediaRouteChooserDialogFragment mediaRouteChooserDialogFragment = new com.android.internal.app.MediaRouteChooserDialogFragment();
            mediaRouteChooserDialogFragment.setRouteTypes(i);
            mediaRouteChooserDialogFragment.setExtendedSettingsClickListener(onClickListener);
            mediaRouteChooserDialogFragment.show(fragmentManager, CHOOSER_FRAGMENT_TAG);
            return mediaRouteChooserDialogFragment;
        }
        if (fragmentManager.findFragmentByTag(CONTROLLER_FRAGMENT_TAG) != null) {
            android.util.Log.w(TAG, "showDialog(): Route controller dialog already showing!");
            return null;
        }
        com.android.internal.app.MediaRouteControllerDialogFragment mediaRouteControllerDialogFragment = new com.android.internal.app.MediaRouteControllerDialogFragment();
        mediaRouteControllerDialogFragment.show(fragmentManager, CONTROLLER_FRAGMENT_TAG);
        return mediaRouteControllerDialogFragment;
    }

    public static android.app.Dialog createDialog(android.content.Context context, int i, android.view.View.OnClickListener onClickListener) {
        int i2;
        if (com.android.internal.app.MediaRouteChooserDialog.isLightTheme(context)) {
            i2 = 16974130;
        } else {
            i2 = 16974126;
        }
        return createDialog(context, i, onClickListener, i2);
    }

    public static android.app.Dialog createDialog(android.content.Context context, int i, android.view.View.OnClickListener onClickListener, int i2) {
        return createDialog(context, i, onClickListener, i2, true);
    }

    public static android.app.Dialog createDialog(android.content.Context context, int i, android.view.View.OnClickListener onClickListener, int i2, boolean z) {
        android.media.MediaRouter.RouteInfo selectedRoute = ((android.media.MediaRouter) context.getSystemService(android.media.MediaRouter.class)).getSelectedRoute();
        if (selectedRoute.isDefault() || !selectedRoute.matchesTypes(i) || selectedRoute.getStatusCode() == 4) {
            com.android.internal.app.MediaRouteChooserDialog mediaRouteChooserDialog = new com.android.internal.app.MediaRouteChooserDialog(context, i2, z);
            mediaRouteChooserDialog.setRouteTypes(i);
            mediaRouteChooserDialog.setExtendedSettingsClickListener(onClickListener);
            return mediaRouteChooserDialog;
        }
        return new com.android.internal.app.MediaRouteControllerDialog(context, i2);
    }
}
