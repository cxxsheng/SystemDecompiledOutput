package com.android.internal.app;

/* loaded from: classes4.dex */
public class MediaRouteChooserDialog extends android.app.AlertDialog {
    private com.android.internal.app.MediaRouteChooserDialog.RouteAdapter mAdapter;
    private boolean mAttachedToWindow;
    private final com.android.internal.app.MediaRouteChooserDialog.MediaRouterCallback mCallback;
    private android.widget.Button mExtendedSettingsButton;
    private android.view.View.OnClickListener mExtendedSettingsClickListener;
    private android.widget.ListView mListView;
    private int mRouteTypes;
    private final android.media.MediaRouter mRouter;
    private final boolean mShowProgressBarWhenEmpty;

    public MediaRouteChooserDialog(android.content.Context context, int i) {
        this(context, i, true);
    }

    public MediaRouteChooserDialog(android.content.Context context, int i, boolean z) {
        super(context, i);
        this.mRouter = (android.media.MediaRouter) context.getSystemService(android.content.Context.MEDIA_ROUTER_SERVICE);
        this.mCallback = new com.android.internal.app.MediaRouteChooserDialog.MediaRouterCallback();
        this.mShowProgressBarWhenEmpty = z;
    }

    public int getRouteTypes() {
        return this.mRouteTypes;
    }

    public void setRouteTypes(int i) {
        if (this.mRouteTypes != i) {
            this.mRouteTypes = i;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(i, this.mCallback, 1);
            }
            refreshRoutes();
        }
    }

    public void setExtendedSettingsClickListener(android.view.View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            updateExtendedSettingsButton();
        }
    }

    public boolean onFilterRoute(android.media.MediaRouter.RouteInfo routeInfo) {
        return !routeInfo.isDefault() && routeInfo.isEnabled() && routeInfo.matchesTypes(this.mRouteTypes);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        int i;
        setView(android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.media_route_chooser_dialog, (android.view.ViewGroup) null));
        if (this.mRouteTypes == 4) {
            i = com.android.internal.R.string.media_route_chooser_title_for_remote_display;
        } else {
            i = com.android.internal.R.string.media_route_chooser_title;
        }
        setTitle(i);
        setIcon(isLightTheme(getContext()) ? com.android.internal.R.drawable.ic_media_route_off_holo_light : com.android.internal.R.drawable.ic_media_route_off_holo_dark);
        super.onCreate(bundle);
        android.view.View findViewById = findViewById(16908292);
        this.mAdapter = new com.android.internal.app.MediaRouteChooserDialog.RouteAdapter(getContext());
        this.mListView = (android.widget.ListView) findViewById(com.android.internal.R.id.media_route_list);
        this.mListView.setAdapter((android.widget.ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this.mAdapter);
        this.mListView.setEmptyView(findViewById);
        this.mExtendedSettingsButton = (android.widget.Button) findViewById(com.android.internal.R.id.media_route_extended_settings_button);
        updateExtendedSettingsButton();
        if (!this.mShowProgressBarWhenEmpty) {
            findViewById(com.android.internal.R.id.media_route_progress_bar).setVisibility(8);
            android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.gravity = 17;
            findViewById.setLayoutParams(layoutParams);
        }
    }

    private void updateExtendedSettingsButton() {
        if (this.mExtendedSettingsButton != null) {
            this.mExtendedSettingsButton.setOnClickListener(this.mExtendedSettingsClickListener);
            this.mExtendedSettingsButton.setVisibility(this.mExtendedSettingsClickListener != null ? 0 : 8);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mRouteTypes, this.mCallback, 1);
        refreshRoutes();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        super.onDetachedFromWindow();
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            this.mAdapter.update();
        }
    }

    static boolean isLightTheme(android.content.Context context) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        return context.getTheme().resolveAttribute(16844176, typedValue, true) && typedValue.data != 0;
    }

    private final class RouteAdapter extends android.widget.ArrayAdapter<android.media.MediaRouter.RouteInfo> implements android.widget.AdapterView.OnItemClickListener {
        private final android.view.LayoutInflater mInflater;

        public RouteAdapter(android.content.Context context) {
            super(context, 0);
            this.mInflater = android.view.LayoutInflater.from(context);
        }

        public void update() {
            clear();
            int routeCount = com.android.internal.app.MediaRouteChooserDialog.this.mRouter.getRouteCount();
            for (int i = 0; i < routeCount; i++) {
                android.media.MediaRouter.RouteInfo routeAt = com.android.internal.app.MediaRouteChooserDialog.this.mRouter.getRouteAt(i);
                if (com.android.internal.app.MediaRouteChooserDialog.this.onFilterRoute(routeAt)) {
                    add(routeAt);
                }
            }
            sort(com.android.internal.app.MediaRouteChooserDialog.RouteComparator.sInstance);
            notifyDataSetChanged();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(com.android.internal.R.layout.media_route_list_item, viewGroup, false);
            }
            android.media.MediaRouter.RouteInfo item = getItem(i);
            android.widget.TextView textView = (android.widget.TextView) view.findViewById(16908308);
            android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(16908309);
            textView.lambda$setTextAsync$0(item.getName());
            java.lang.CharSequence description = item.getDescription();
            if (android.text.TextUtils.isEmpty(description)) {
                textView2.setVisibility(8);
                textView2.lambda$setTextAsync$0("");
            } else {
                textView2.setVisibility(0);
                textView2.lambda$setTextAsync$0(description);
            }
            view.setEnabled(item.isEnabled());
            return view;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.media.MediaRouter.RouteInfo item = getItem(i);
            if (item.isEnabled()) {
                item.select();
                com.android.internal.app.MediaRouteChooserDialog.this.dismiss();
            }
        }
    }

    private final class MediaRouterCallback extends android.media.MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteAdded(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteRemoved(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteSelected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            com.android.internal.app.MediaRouteChooserDialog.this.dismiss();
        }
    }

    private static final class RouteComparator implements java.util.Comparator<android.media.MediaRouter.RouteInfo> {
        public static final com.android.internal.app.MediaRouteChooserDialog.RouteComparator sInstance = new com.android.internal.app.MediaRouteChooserDialog.RouteComparator();

        private RouteComparator() {
        }

        @Override // java.util.Comparator
        public int compare(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteInfo routeInfo2) {
            return routeInfo.getName().toString().compareTo(routeInfo2.getName().toString());
        }
    }
}
