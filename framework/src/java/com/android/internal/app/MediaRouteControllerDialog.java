package com.android.internal.app;

/* loaded from: classes4.dex */
public class MediaRouteControllerDialog extends android.app.AlertDialog {
    private static final int VOLUME_UPDATE_DELAY_MILLIS = 250;
    private boolean mAttachedToWindow;
    private final com.android.internal.app.MediaRouteControllerDialog.MediaRouterCallback mCallback;
    private android.view.View mControlView;
    private boolean mCreated;
    private android.graphics.drawable.Drawable mCurrentIconDrawable;
    private android.graphics.drawable.Drawable mMediaRouteButtonDrawable;
    private int[] mMediaRouteConnectingState;
    private int[] mMediaRouteOnState;
    private final android.media.MediaRouter.RouteInfo mRoute;
    private final android.media.MediaRouter mRouter;
    private boolean mVolumeControlEnabled;
    private android.widget.LinearLayout mVolumeLayout;
    private android.widget.SeekBar mVolumeSlider;
    private boolean mVolumeSliderTouched;

    public MediaRouteControllerDialog(android.content.Context context, int i) {
        super(context, i);
        this.mMediaRouteConnectingState = new int[]{16842912, 16842910};
        this.mMediaRouteOnState = new int[]{16843518, 16842910};
        this.mVolumeControlEnabled = true;
        this.mRouter = (android.media.MediaRouter) context.getSystemService(android.content.Context.MEDIA_ROUTER_SERVICE);
        this.mCallback = new com.android.internal.app.MediaRouteControllerDialog.MediaRouterCallback();
        this.mRoute = this.mRouter.getSelectedRoute();
    }

    public android.media.MediaRouter.RouteInfo getRoute() {
        return this.mRoute;
    }

    public android.view.View onCreateMediaControlView(android.os.Bundle bundle) {
        return null;
    }

    public android.view.View getMediaControlView() {
        return this.mControlView;
    }

    public void setVolumeControlEnabled(boolean z) {
        if (this.mVolumeControlEnabled != z) {
            this.mVolumeControlEnabled = z;
            if (this.mCreated) {
                updateVolume();
            }
        }
    }

    public boolean isVolumeControlEnabled() {
        return this.mVolumeControlEnabled;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        setTitle(this.mRoute.getName());
        setButton(-2, getContext().getResources().getString(com.android.internal.R.string.media_route_controller_disconnect), new android.content.DialogInterface.OnClickListener() { // from class: com.android.internal.app.MediaRouteControllerDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                if (com.android.internal.app.MediaRouteControllerDialog.this.mRoute.isSelected()) {
                    if (com.android.internal.app.MediaRouteControllerDialog.this.mRoute.isBluetooth()) {
                        com.android.internal.app.MediaRouteControllerDialog.this.mRouter.getDefaultRoute().select();
                    } else {
                        com.android.internal.app.MediaRouteControllerDialog.this.mRouter.getFallbackRoute().select();
                    }
                }
                com.android.internal.app.MediaRouteControllerDialog.this.dismiss();
            }
        });
        android.view.View inflate = getLayoutInflater().inflate(com.android.internal.R.layout.media_route_controller_dialog, (android.view.ViewGroup) null);
        setView(inflate, 0, 0, 0, 0);
        super.onCreate(bundle);
        android.view.View findViewById = getWindow().findViewById(com.android.internal.R.id.customPanel);
        if (findViewById != null) {
            findViewById.setMinimumHeight(0);
        }
        this.mVolumeLayout = (android.widget.LinearLayout) inflate.findViewById(com.android.internal.R.id.media_route_volume_layout);
        this.mVolumeSlider = (android.widget.SeekBar) inflate.findViewById(com.android.internal.R.id.media_route_volume_slider);
        this.mVolumeSlider.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.android.internal.app.MediaRouteControllerDialog.2
            private final java.lang.Runnable mStopTrackingTouch = new java.lang.Runnable() { // from class: com.android.internal.app.MediaRouteControllerDialog.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSliderTouched) {
                        com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSliderTouched = false;
                        com.android.internal.app.MediaRouteControllerDialog.this.updateVolume();
                    }
                }
            };

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                if (com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSliderTouched) {
                    com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
                } else {
                    com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSliderTouched = true;
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                com.android.internal.app.MediaRouteControllerDialog.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 250L);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
                if (z) {
                    com.android.internal.app.MediaRouteControllerDialog.this.mRoute.requestSetVolume(i);
                }
            }
        });
        this.mMediaRouteButtonDrawable = obtainMediaRouteButtonDrawable();
        this.mCreated = true;
        if (update()) {
            this.mControlView = onCreateMediaControlView(bundle);
            android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) inflate.findViewById(com.android.internal.R.id.media_route_control_frame);
            if (this.mControlView != null) {
                frameLayout.addView(this.mControlView);
                frameLayout.setVisibility(0);
            } else {
                frameLayout.setVisibility(8);
            }
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(0, this.mCallback, 2);
        update();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        this.mAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            this.mRoute.requestUpdateVolume(i == 25 ? -1 : 1);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean update() {
        if (!this.mRoute.isSelected() || this.mRoute.isDefault()) {
            dismiss();
            return false;
        }
        setTitle(this.mRoute.getName());
        updateVolume();
        android.graphics.drawable.Drawable iconDrawable = getIconDrawable();
        if (iconDrawable != this.mCurrentIconDrawable) {
            this.mCurrentIconDrawable = iconDrawable;
            if (iconDrawable instanceof android.graphics.drawable.AnimationDrawable) {
                android.graphics.drawable.AnimationDrawable animationDrawable = (android.graphics.drawable.AnimationDrawable) iconDrawable;
                if (!this.mAttachedToWindow && !this.mRoute.isConnecting()) {
                    if (animationDrawable.isRunning()) {
                        animationDrawable.stop();
                    }
                    iconDrawable = animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1);
                } else if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
            }
            setIcon(iconDrawable);
        }
        return true;
    }

    private android.graphics.drawable.Drawable obtainMediaRouteButtonDrawable() {
        android.content.Context context = getContext();
        android.util.TypedValue typedValue = new android.util.TypedValue();
        if (!context.getTheme().resolveAttribute(16843693, typedValue, true)) {
            return null;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(typedValue.data, new int[]{com.android.internal.R.attr.externalRouteEnabledDrawable});
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }

    private android.graphics.drawable.Drawable getIconDrawable() {
        if (!(this.mMediaRouteButtonDrawable instanceof android.graphics.drawable.StateListDrawable)) {
            return this.mMediaRouteButtonDrawable;
        }
        if (this.mRoute.isConnecting()) {
            android.graphics.drawable.StateListDrawable stateListDrawable = (android.graphics.drawable.StateListDrawable) this.mMediaRouteButtonDrawable;
            stateListDrawable.setState(this.mMediaRouteConnectingState);
            return stateListDrawable.getCurrent();
        }
        android.graphics.drawable.StateListDrawable stateListDrawable2 = (android.graphics.drawable.StateListDrawable) this.mMediaRouteButtonDrawable;
        stateListDrawable2.setState(this.mMediaRouteOnState);
        return stateListDrawable2.getCurrent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolume() {
        if (!this.mVolumeSliderTouched) {
            if (isVolumeControlAvailable()) {
                this.mVolumeLayout.setVisibility(0);
                this.mVolumeSlider.setMax(this.mRoute.getVolumeMax());
                this.mVolumeSlider.setProgress(this.mRoute.getVolume());
                return;
            }
            this.mVolumeLayout.setVisibility(8);
        }
    }

    private boolean isVolumeControlAvailable() {
        return this.mVolumeControlEnabled && this.mRoute.getVolumeHandling() == 1;
    }

    private final class MediaRouterCallback extends android.media.MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteControllerDialog.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteControllerDialog.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            if (routeInfo == com.android.internal.app.MediaRouteControllerDialog.this.mRoute) {
                com.android.internal.app.MediaRouteControllerDialog.this.updateVolume();
            }
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteGrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
            com.android.internal.app.MediaRouteControllerDialog.this.update();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUngrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
            com.android.internal.app.MediaRouteControllerDialog.this.update();
        }
    }
}
