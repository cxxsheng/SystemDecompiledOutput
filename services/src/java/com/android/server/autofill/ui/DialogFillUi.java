package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class DialogFillUi {
    private static final java.lang.String TAG = "DialogFillUi";
    private static final int THEME_ID_DARK = 16974829;
    private static final int THEME_ID_LIGHT = 16974840;

    @android.annotation.Nullable
    private final com.android.server.autofill.ui.DialogFillUi.ItemsAdapter mAdapter;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.DialogFillUi.AnnounceFilterResult mAnnounceFilterResult;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.DialogFillUi.UiCallback mCallback;
    private final android.content.ComponentName mComponentName;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private boolean mDestroyed;

    @android.annotation.NonNull
    private final android.app.Dialog mDialog;

    @android.annotation.Nullable
    private java.lang.String mFilterText;

    @android.annotation.NonNull
    private final android.widget.ListView mListView;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.OverlayControl mOverlayControl;
    private final java.lang.String mServicePackageName;
    private final int mThemeId;
    private final int mVisibleDatasetsMaxCount;

    interface UiCallback {
        void onCanceled();

        void onDatasetPicked(@android.annotation.NonNull android.service.autofill.Dataset dataset);

        void onDismissed();

        void onResponsePicked(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse);

        void onShown();

        void startIntentSender(android.content.IntentSender intentSender);
    }

    DialogFillUi(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.graphics.drawable.Drawable drawable, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.NonNull com.android.server.autofill.ui.OverlayControl overlayControl, boolean z, @android.annotation.NonNull com.android.server.autofill.ui.DialogFillUi.UiCallback uiCallback) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "nightMode: " + z);
        }
        this.mThemeId = z ? 16974829 : 16974840;
        this.mCallback = uiCallback;
        this.mOverlayControl = overlayControl;
        this.mServicePackageName = str2;
        this.mComponentName = componentName;
        this.mContext = new android.view.ContextThemeWrapper(context, this.mThemeId);
        android.view.View inflate = android.view.LayoutInflater.from(this.mContext).inflate(android.R.layout.autofill_fill_dialog, (android.view.ViewGroup) null);
        if (fillResponse.getShowFillDialogIcon()) {
            setServiceIcon(inflate, drawable);
        }
        setHeader(inflate, fillResponse);
        this.mVisibleDatasetsMaxCount = getVisibleDatasetsMaxCount();
        if (fillResponse.getAuthentication() != null) {
            this.mListView = null;
            this.mAdapter = null;
            try {
                initialAuthenticationLayout(inflate, fillResponse);
            } catch (java.lang.RuntimeException e) {
                uiCallback.onCanceled();
                android.util.Slog.e(TAG, "Error inflating remote views", e);
                this.mDialog = null;
                return;
            }
        } else {
            this.mAdapter = new com.android.server.autofill.ui.DialogFillUi.ItemsAdapter(createDatasetItems(fillResponse, autofillId));
            this.mListView = (android.widget.ListView) inflate.findViewById(android.R.id.autofill_dialog_list);
            initialDatasetLayout(inflate, str);
        }
        setDismissButton(inflate);
        this.mDialog = new android.app.Dialog(this.mContext, this.mThemeId);
        this.mDialog.setContentView(inflate);
        setDialogParamsAsBottomSheet();
        this.mDialog.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(android.content.DialogInterface dialogInterface) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$new$0(dialogInterface);
            }
        });
        this.mDialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(android.content.DialogInterface dialogInterface) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$new$1(dialogInterface);
            }
        });
        show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.DialogInterface dialogInterface) {
        this.mCallback.onCanceled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.content.DialogInterface dialogInterface) {
        this.mCallback.onShown();
    }

    private int getVisibleDatasetsMaxCount() {
        if (com.android.server.autofill.AutofillManagerService.getVisibleDatasetsMaxCount() > 0) {
            int visibleDatasetsMaxCount = com.android.server.autofill.AutofillManagerService.getVisibleDatasetsMaxCount();
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "overriding maximum visible datasets to " + visibleDatasetsMaxCount);
            }
            return visibleDatasetsMaxCount;
        }
        return this.mContext.getResources().getInteger(android.R.integer.autofill_max_visible_datasets);
    }

    private void setDialogParamsAsBottomSheet() {
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2038);
        window.addFlags(131074);
        window.setDimAmount(0.6f);
        window.addPrivateFlags(16);
        window.setSoftInputMode(32);
        window.setGravity(81);
        window.setCloseOnTouchOutside(true);
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        attributes.width = java.lang.Math.min(displayMetrics.widthPixels, this.mContext.getResources().getDimensionPixelSize(android.R.dimen.app_header_height));
        attributes.accessibilityTitle = this.mContext.getString(android.R.string.app_upgrading_toast);
        attributes.windowAnimations = android.R.style.AutofillHalfSheetTonalButton;
    }

    private void setServiceIcon(android.view.View view, android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return;
        }
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(android.R.id.autofill_service_icon);
        int minimumWidth = drawable.getMinimumWidth();
        int minimumHeight = drawable.getMinimumHeight();
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "Adding service icon (" + minimumWidth + "x" + minimumHeight + ")");
        }
        imageView.setImageDrawable(drawable);
        imageView.setVisibility(0);
    }

    private void setHeader(android.view.View view, android.service.autofill.FillResponse fillResponse) {
        android.widget.RemoteViews sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getDialogHeader());
        if (sanitizeRemoteView == null) {
            return;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.findViewById(android.R.id.autofill_dialog_header);
        viewGroup.addView(sanitizeRemoteView.applyWithTheme(this.mContext, (android.view.ViewGroup) view, new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda8
            public final boolean onInteraction(android.view.View view2, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$setHeader$2;
                lambda$setHeader$2 = com.android.server.autofill.ui.DialogFillUi.this.lambda$setHeader$2(view2, pendingIntent, remoteResponse);
                return lambda$setHeader$2;
            }
        }, this.mThemeId));
        viewGroup.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setHeader$2(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        if (pendingIntent != null) {
            this.mCallback.startIntentSender(pendingIntent.getIntentSender());
            return true;
        }
        return true;
    }

    private void setDismissButton(android.view.View view) {
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(android.R.id.autofill_dialog_no);
        textView.setText(android.R.string.autofill_error_cannot_autofill);
        textView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view2) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$setDismissButton$3(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDismissButton$3(android.view.View view) {
        this.mCallback.onDismissed();
    }

    private void setContinueButton(android.view.View view, android.view.View.OnClickListener onClickListener) {
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(android.R.id.autofill_dialog_yes);
        textView.setText(android.R.string.app_suspended_title);
        textView.setOnClickListener(onClickListener);
        textView.setVisibility(0);
    }

    private void initialAuthenticationLayout(android.view.View view, final android.service.autofill.FillResponse fillResponse) {
        android.widget.RemoteViews sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getDialogPresentation());
        if (sanitizeRemoteView == null) {
            sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getPresentation());
        }
        if (sanitizeRemoteView == null) {
            throw new java.lang.RuntimeException("No presentation for fill dialog authentication");
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.findViewById(android.R.id.autofill_dialog_container);
        viewGroup.addView(sanitizeRemoteView.applyWithTheme(this.mContext, (android.view.ViewGroup) view, new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda3
            public final boolean onInteraction(android.view.View view2, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$initialAuthenticationLayout$4;
                lambda$initialAuthenticationLayout$4 = com.android.server.autofill.ui.DialogFillUi.this.lambda$initialAuthenticationLayout$4(view2, pendingIntent, remoteResponse);
                return lambda$initialAuthenticationLayout$4;
            }
        }, this.mThemeId));
        viewGroup.setVisibility(0);
        viewGroup.setFocusable(true);
        viewGroup.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view2) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$initialAuthenticationLayout$5(fillResponse, view2);
            }
        });
        setContinueButton(view, new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view2) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$initialAuthenticationLayout$6(fillResponse, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initialAuthenticationLayout$4(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        if (pendingIntent != null) {
            this.mCallback.startIntentSender(pendingIntent.getIntentSender());
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialAuthenticationLayout$5(android.service.autofill.FillResponse fillResponse, android.view.View view) {
        this.mCallback.onResponsePicked(fillResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialAuthenticationLayout$6(android.service.autofill.FillResponse fillResponse, android.view.View view) {
        this.mCallback.onResponsePicked(fillResponse);
    }

    private java.util.ArrayList<com.android.server.autofill.ui.DialogFillUi.ViewItem> createDatasetItems(android.service.autofill.FillResponse fillResponse, android.view.autofill.AutofillId autofillId) {
        boolean z;
        java.util.regex.Pattern pattern;
        java.lang.String str;
        int size = fillResponse.getDatasets().size();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Number datasets: " + size + " max visible: " + this.mVisibleDatasetsMaxCount);
        }
        android.widget.RemoteViews.InteractionHandler interactionHandler = new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda10
            public final boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$createDatasetItems$7;
                lambda$createDatasetItems$7 = com.android.server.autofill.ui.DialogFillUi.this.lambda$createDatasetItems$7(view, pendingIntent, remoteResponse);
                return lambda$createDatasetItems$7;
            }
        };
        java.util.ArrayList<com.android.server.autofill.ui.DialogFillUi.ViewItem> arrayList = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) fillResponse.getDatasets().get(i);
            int indexOf = dataset.getFieldIds().indexOf(autofillId);
            if (indexOf >= 0) {
                android.widget.RemoteViews sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(dataset.getFieldDialogPresentation(indexOf));
                if (sanitizeRemoteView == null) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.w(TAG, "not displaying UI on field " + autofillId + " because service didn't provide a presentation for it on " + dataset);
                    }
                } else {
                    try {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "setting remote view for " + autofillId);
                        }
                        java.lang.String str2 = null;
                        android.view.View applyWithTheme = sanitizeRemoteView.applyWithTheme(this.mContext, null, interactionHandler, this.mThemeId);
                        android.service.autofill.Dataset.DatasetFieldFilter filter = dataset.getFilter(indexOf);
                        if (filter == null) {
                            android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) dataset.getFieldValues().get(indexOf);
                            if (autofillValue != null && autofillValue.isText()) {
                                str = autofillValue.getTextValue().toString().toLowerCase();
                            } else {
                                str = null;
                            }
                            z = true;
                            pattern = null;
                            str2 = str;
                        } else {
                            java.util.regex.Pattern pattern2 = filter.pattern;
                            if (pattern2 != null) {
                                z = true;
                                pattern = pattern2;
                            } else {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "Explicitly disabling filter at id " + autofillId + " for dataset #" + indexOf);
                                }
                                z = false;
                                pattern = pattern2;
                            }
                        }
                        arrayList.add(new com.android.server.autofill.ui.DialogFillUi.ViewItem(dataset, pattern, z, str2, applyWithTheme));
                    } catch (java.lang.RuntimeException e) {
                        android.util.Slog.e(TAG, "Error inflating remote views", e);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createDatasetItems$7(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        if (pendingIntent != null) {
            this.mCallback.startIntentSender(pendingIntent.getIntentSender());
            return true;
        }
        return true;
    }

    private void initialDatasetLayout(android.view.View view, java.lang.String str) {
        final android.widget.AdapterView.OnItemClickListener onItemClickListener = new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view2, int i, long j) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$initialDatasetLayout$8(adapterView, view2, i, j);
            }
        };
        this.mListView.setAdapter((android.widget.ListAdapter) this.mAdapter);
        this.mListView.setVisibility(0);
        this.mListView.setOnItemClickListener(onItemClickListener);
        if (this.mAdapter.getCount() == 1) {
            setContinueButton(view, new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view2) {
                    onItemClickListener.onItemClick(null, null, 0, 0L);
                }
            });
        }
        if (str == null) {
            this.mFilterText = null;
        } else {
            this.mFilterText = str.toLowerCase();
        }
        final int count = this.mAdapter.getCount();
        this.mAdapter.getFilter().filter(this.mFilterText, new android.widget.Filter.FilterListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda2
            @Override // android.widget.Filter.FilterListener
            public final void onFilterComplete(int i) {
                com.android.server.autofill.ui.DialogFillUi.this.lambda$initialDatasetLayout$10(count, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialDatasetLayout$8(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        this.mCallback.onDatasetPicked(this.mAdapter.getItem(i).dataset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialDatasetLayout$10(int i, int i2) {
        if (this.mDestroyed) {
            return;
        }
        if (i2 <= 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "No dataset matches filter with " + (this.mFilterText != null ? this.mFilterText.length() : 0) + " chars");
            }
            this.mCallback.onCanceled();
            return;
        }
        if (this.mAdapter.getCount() <= this.mVisibleDatasetsMaxCount) {
            this.mListView.setVerticalScrollBarEnabled(false);
        } else {
            this.mListView.setVerticalScrollBarEnabled(true);
            this.mListView.onVisibilityAggregated(true);
        }
        if (this.mAdapter.getCount() != i) {
            this.mListView.requestLayout();
        }
    }

    private void show() {
        android.util.Slog.i(TAG, "Showing fill dialog");
        this.mDialog.show();
        this.mOverlayControl.hideOverlays();
    }

    boolean isShowing() {
        return this.mDialog.isShowing();
    }

    void hide() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Hiding fill dialog.");
        }
        try {
            this.mDialog.hide();
        } finally {
            this.mOverlayControl.showOverlays();
        }
    }

    void destroy() {
        try {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "destroy()");
            }
            throwIfDestroyed();
            this.mDialog.dismiss();
            this.mDestroyed = true;
            this.mOverlayControl.showOverlays();
        } catch (java.lang.Throwable th) {
            this.mOverlayControl.showOverlays();
            throw th;
        }
    }

    private void throwIfDestroyed() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("cannot interact with a destroyed instance");
        }
    }

    public java.lang.String toString() {
        return "NO TITLE";
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("service: ");
        printWriter.println(this.mServicePackageName);
        printWriter.print(str);
        printWriter.print("app: ");
        printWriter.println(this.mComponentName.toShortString());
        printWriter.print(str);
        printWriter.print("theme id: ");
        printWriter.print(this.mThemeId);
        switch (this.mThemeId) {
            case 16974829:
                printWriter.println(" (dark)");
                break;
            case 16974840:
                printWriter.println(" (light)");
                break;
            default:
                printWriter.println("(UNKNOWN_MODE)");
                break;
        }
        android.view.View decorView = this.mDialog.getWindow().getDecorView();
        int[] locationOnScreen = decorView.getLocationOnScreen();
        printWriter.print(str);
        printWriter.print("coordinates: ");
        printWriter.print('(');
        printWriter.print(locationOnScreen[0]);
        printWriter.print(',');
        printWriter.print(locationOnScreen[1]);
        printWriter.print(')');
        printWriter.print('(');
        printWriter.print(locationOnScreen[0] + decorView.getWidth());
        printWriter.print(',');
        printWriter.print(locationOnScreen[1] + decorView.getHeight());
        printWriter.println(')');
        printWriter.print(str);
        printWriter.print("destroyed: ");
        printWriter.println(this.mDestroyed);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void announceSearchResultIfNeeded() {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAnnounceFilterResult == null) {
                this.mAnnounceFilterResult = new com.android.server.autofill.ui.DialogFillUi.AnnounceFilterResult();
            }
            this.mAnnounceFilterResult.post();
        }
    }

    private final class AnnounceFilterResult implements java.lang.Runnable {
        private static final int SEARCH_RESULT_ANNOUNCEMENT_DELAY = 1000;

        private AnnounceFilterResult() {
        }

        public void post() {
            remove();
            com.android.server.autofill.ui.DialogFillUi.this.mListView.postDelayed(this, 1000L);
        }

        public void remove() {
            com.android.server.autofill.ui.DialogFillUi.this.mListView.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            java.lang.String format;
            int count = com.android.server.autofill.ui.DialogFillUi.this.mListView.getAdapter().getCount();
            if (count <= 0) {
                format = com.android.server.autofill.ui.DialogFillUi.this.mContext.getString(android.R.string.as_app_forced_to_restricted_bucket);
            } else {
                java.util.HashMap hashMap = new java.util.HashMap();
                hashMap.put(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, java.lang.Integer.valueOf(count));
                format = android.util.PluralsMessageFormatter.format(com.android.server.autofill.ui.DialogFillUi.this.mContext.getResources(), hashMap, android.R.string.auto_data_switch_content);
            }
            com.android.server.autofill.ui.DialogFillUi.this.mListView.announceForAccessibility(format);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ItemsAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {

        @android.annotation.NonNull
        private final java.util.List<com.android.server.autofill.ui.DialogFillUi.ViewItem> mAllItems;

        @android.annotation.NonNull
        private final java.util.List<com.android.server.autofill.ui.DialogFillUi.ViewItem> mFilteredItems = new java.util.ArrayList();

        ItemsAdapter(@android.annotation.NonNull java.util.List<com.android.server.autofill.ui.DialogFillUi.ViewItem> list) {
            this.mAllItems = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
            this.mFilteredItems.addAll(list);
        }

        /* renamed from: com.android.server.autofill.ui.DialogFillUi$ItemsAdapter$1, reason: invalid class name */
        class AnonymousClass1 extends android.widget.Filter {
            AnonymousClass1() {
            }

            @Override // android.widget.Filter
            protected android.widget.Filter.FilterResults performFiltering(final java.lang.CharSequence charSequence) {
                java.util.List list = (java.util.List) com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.mAllItems.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.autofill.ui.DialogFillUi$ItemsAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$performFiltering$0;
                        lambda$performFiltering$0 = com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.AnonymousClass1.lambda$performFiltering$0(charSequence, (com.android.server.autofill.ui.DialogFillUi.ViewItem) obj);
                        return lambda$performFiltering$0;
                    }
                }).collect(java.util.stream.Collectors.toList());
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values = list;
                filterResults.count = list.size();
                return filterResults;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ boolean lambda$performFiltering$0(java.lang.CharSequence charSequence, com.android.server.autofill.ui.DialogFillUi.ViewItem viewItem) {
                return viewItem.matches(charSequence);
            }

            @Override // android.widget.Filter
            protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                int size = com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.mFilteredItems.size();
                com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.mFilteredItems.clear();
                if (filterResults.count > 0) {
                    com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.mFilteredItems.addAll((java.util.List) filterResults.values);
                }
                if (size != com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.mFilteredItems.size()) {
                    com.android.server.autofill.ui.DialogFillUi.this.announceSearchResultIfNeeded();
                }
                com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.this.notifyDataSetChanged();
            }
        }

        @Override // android.widget.Filterable
        public android.widget.Filter getFilter() {
            return new com.android.server.autofill.ui.DialogFillUi.ItemsAdapter.AnonymousClass1();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mFilteredItems.size();
        }

        @Override // android.widget.Adapter
        public com.android.server.autofill.ui.DialogFillUi.ViewItem getItem(int i) {
            return this.mFilteredItems.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            return getItem(i).view;
        }

        public java.lang.String toString() {
            return "ItemsAdapter: [all=" + this.mAllItems + ", filtered=" + this.mFilteredItems + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewItem {

        @android.annotation.Nullable
        public final android.service.autofill.Dataset dataset;

        @android.annotation.Nullable
        public final java.util.regex.Pattern filter;
        public final boolean filterable;

        @android.annotation.Nullable
        public final java.lang.String value;

        @android.annotation.NonNull
        public final android.view.View view;

        ViewItem(@android.annotation.NonNull android.service.autofill.Dataset dataset, @android.annotation.Nullable java.util.regex.Pattern pattern, boolean z, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.view.View view) {
            this.dataset = dataset;
            this.value = str;
            this.view = view;
            this.filter = pattern;
            this.filterable = z;
        }

        public boolean matches(java.lang.CharSequence charSequence) {
            if (android.text.TextUtils.isEmpty(charSequence)) {
                return true;
            }
            if (!this.filterable) {
                return false;
            }
            java.lang.String lowerCase = charSequence.toString().toLowerCase();
            if (this.filter != null) {
                return this.filter.matcher(lowerCase).matches();
            }
            if (this.value == null) {
                return this.dataset.getAuthentication() == null;
            }
            return this.value.toLowerCase().startsWith(lowerCase);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("ViewItem:[view=");
            sb.append(this.view.getAutofillId());
            java.lang.String id = this.dataset == null ? null : this.dataset.getId();
            if (id != null) {
                sb.append(", dataset=");
                sb.append(id);
            }
            if (this.value != null) {
                sb.append(", value=");
                sb.append(this.value.length());
                sb.append("_chars");
            }
            if (this.filterable) {
                sb.append(", filterable");
            }
            if (this.filter != null) {
                sb.append(", filter=");
                sb.append(this.filter.pattern().length());
                sb.append("_chars");
            }
            sb.append(']');
            return sb.toString();
        }
    }
}
