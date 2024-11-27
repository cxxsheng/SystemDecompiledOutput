package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class FillUi {
    private static final int AUTOFILL_CREDMAN_MAX_VISIBLE_DATASETS = 5;
    private static final java.lang.String TAG = "FillUi";
    private static final int THEME_ID_DARK = 16974827;
    private static final int THEME_ID_LIGHT = 16974839;
    private static final android.util.TypedValue sTempTypedValue = new android.util.TypedValue();

    @android.annotation.Nullable
    private final com.android.server.autofill.ui.FillUi.ItemsAdapter mAdapter;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.FillUi.AnnounceFilterResult mAnnounceFilterResult;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.FillUi.Callback mCallback;
    private int mContentHeight;
    private int mContentWidth;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private boolean mDestroyed;

    @android.annotation.Nullable
    private java.lang.String mFilterText;

    @android.annotation.Nullable
    private final android.view.View mFooter;
    private final boolean mFullScreen;

    @android.annotation.Nullable
    private final android.view.View mHeader;

    @android.annotation.NonNull
    private final android.widget.ListView mListView;
    private int mMaxInputLengthForAutofill;
    private final int mThemeId;
    private final int mVisibleDatasetsMaxCount;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.FillUi.AnchoredWindow mWindow;

    @android.annotation.NonNull
    private final android.graphics.Point mTempPoint = new android.graphics.Point();

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.FillUi.AutofillWindowPresenter mWindowPresenter = new com.android.server.autofill.ui.FillUi.AutofillWindowPresenter();

    interface Callback {
        void cancelSession();

        void dispatchUnhandledKey(android.view.KeyEvent keyEvent);

        void onCanceled();

        void onDatasetPicked(@android.annotation.NonNull android.service.autofill.Dataset dataset);

        void onDestroy();

        void onResponsePicked(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse);

        void onShown();

        void requestHideFillUi();

        void requestHideFillUiWhenDestroyed();

        void requestShowFillUi(int i, int i2, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter);

        void startIntentSender(android.content.IntentSender intentSender);
    }

    public static boolean isFullScreen(android.content.Context context) {
        if (com.android.server.autofill.Helper.sFullScreenMode != null) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "forcing full-screen mode to " + com.android.server.autofill.Helper.sFullScreenMode);
            }
            return com.android.server.autofill.Helper.sFullScreenMode.booleanValue();
        }
        return context.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    FillUi(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull final android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull com.android.server.autofill.ui.OverlayControl overlayControl, @android.annotation.NonNull java.lang.CharSequence charSequence, @android.annotation.NonNull android.graphics.drawable.Drawable drawable, boolean z, int i, @android.annotation.NonNull com.android.server.autofill.ui.FillUi.Callback callback) {
        android.view.ViewGroup viewGroup;
        android.widget.RemoteViews.InteractionHandler interactionHandler;
        java.util.regex.Pattern pattern;
        boolean z2;
        java.lang.String str2;
        java.lang.String str3;
        if (com.android.server.autofill.Helper.sVerbose) {
            com.android.server.utils.Slogf.v(TAG, "nightMode: %b displayId: %d", java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(context.getDisplayId()));
        }
        this.mThemeId = z ? 16974827 : 16974839;
        this.mCallback = callback;
        this.mFullScreen = isFullScreen(context);
        this.mContext = new android.view.ContextThemeWrapper(context, this.mThemeId);
        this.mMaxInputLengthForAutofill = i;
        android.view.LayoutInflater from = android.view.LayoutInflater.from(this.mContext);
        android.widget.RemoteViews sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getHeader());
        android.widget.RemoteViews sanitizeRemoteView2 = com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getFooter());
        if (this.mFullScreen) {
            viewGroup = (android.view.ViewGroup) from.inflate(android.R.layout.autofill_dataset_picker_fullscreen, (android.view.ViewGroup) null);
        } else if (sanitizeRemoteView != null || sanitizeRemoteView2 != null) {
            viewGroup = (android.view.ViewGroup) from.inflate(android.R.layout.autofill_dataset_picker_header_footer, (android.view.ViewGroup) null);
        } else {
            viewGroup = (android.view.ViewGroup) from.inflate(android.R.layout.autofill_dataset_picker, (android.view.ViewGroup) null);
        }
        viewGroup.setClipToOutline(true);
        android.widget.TextView textView = (android.widget.TextView) viewGroup.findViewById(android.R.id.autofill_dataset_title);
        if (textView != null) {
            textView.setText(this.mContext.getString(android.R.string.autofill_save_yes, charSequence));
        }
        android.widget.ImageView imageView = (android.widget.ImageView) viewGroup.findViewById(android.R.id.autofill_dataset_icon);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        if (this.mFullScreen) {
            android.graphics.Point point = this.mTempPoint;
            this.mContext.getDisplayNoVerify().getSize(point);
            this.mContentWidth = -1;
            this.mContentHeight = point.y / 2;
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "initialized fillscreen LayoutParams " + this.mContentWidth + "," + this.mContentHeight);
            }
        }
        viewGroup.addOnUnhandledKeyEventListener(new android.view.View.OnUnhandledKeyEventListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda3
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(android.view.View view, android.view.KeyEvent keyEvent) {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.autofill.ui.FillUi.this.lambda$new$0(view, keyEvent);
                return lambda$new$0;
            }
        });
        if (com.android.server.autofill.AutofillManagerService.getVisibleDatasetsMaxCount() > 0) {
            this.mVisibleDatasetsMaxCount = com.android.server.autofill.AutofillManagerService.getVisibleDatasetsMaxCount();
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "overriding maximum visible datasets to " + this.mVisibleDatasetsMaxCount);
            }
        } else if (!android.service.autofill.Flags.autofillCredmanIntegration() || (fillResponse.getFlags() & 8) == 0) {
            this.mVisibleDatasetsMaxCount = this.mContext.getResources().getInteger(android.R.integer.autofill_max_visible_datasets);
        } else {
            this.mVisibleDatasetsMaxCount = 5;
        }
        android.widget.RemoteViews.InteractionHandler interactionHandler2 = new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda4
            public final boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$new$1;
                lambda$new$1 = com.android.server.autofill.ui.FillUi.this.lambda$new$1(view, pendingIntent, remoteResponse);
                return lambda$new$1;
            }
        };
        if (fillResponse.getAuthentication() != null) {
            this.mHeader = null;
            this.mListView = null;
            this.mFooter = null;
            this.mAdapter = null;
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) viewGroup.findViewById(android.R.id.autofill_dataset_picker);
            try {
                if (com.android.server.autofill.Helper.sanitizeRemoteView(fillResponse.getPresentation()) != null) {
                    android.view.View applyWithTheme = fillResponse.getPresentation().applyWithTheme(this.mContext, viewGroup, interactionHandler2, this.mThemeId);
                    viewGroup2.addView(applyWithTheme);
                    viewGroup2.setFocusable(true);
                    viewGroup2.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(android.view.View view) {
                            com.android.server.autofill.ui.FillUi.this.lambda$new$2(fillResponse, view);
                        }
                    });
                    if (!this.mFullScreen) {
                        android.graphics.Point point2 = this.mTempPoint;
                        resolveMaxWindowSize(this.mContext, point2);
                        applyWithTheme.getLayoutParams().width = this.mFullScreen ? point2.x : -2;
                        applyWithTheme.getLayoutParams().height = -2;
                        viewGroup.measure(android.view.View.MeasureSpec.makeMeasureSpec(point2.x, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(point2.y, Integer.MIN_VALUE));
                        this.mContentWidth = applyWithTheme.getMeasuredWidth();
                        this.mContentHeight = applyWithTheme.getMeasuredHeight();
                    }
                    this.mWindow = new com.android.server.autofill.ui.FillUi.AnchoredWindow(viewGroup, overlayControl);
                    requestShowFillUi();
                    return;
                }
                throw new java.lang.RuntimeException("Permission error accessing RemoteView");
            } catch (java.lang.RuntimeException e) {
                callback.onCanceled();
                android.util.Slog.e(TAG, "Error inflating remote views", e);
                this.mWindow = null;
                return;
            }
        }
        int size = fillResponse.getDatasets().size();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Number datasets: " + size + " max visible: " + this.mVisibleDatasetsMaxCount);
        }
        if (sanitizeRemoteView != null) {
            interactionHandler = newInteractionBlocker();
            this.mHeader = sanitizeRemoteView.applyWithTheme(this.mContext, null, interactionHandler, this.mThemeId);
            android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) viewGroup.findViewById(android.R.id.autofill_dataset_header);
            applyCancelAction(this.mHeader, fillResponse.getCancelIds());
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "adding header");
            }
            linearLayout.addView(this.mHeader);
            linearLayout.setVisibility(0);
        } else {
            this.mHeader = null;
            interactionHandler = null;
        }
        if (sanitizeRemoteView2 != null) {
            android.widget.LinearLayout linearLayout2 = (android.widget.LinearLayout) viewGroup.findViewById(android.R.id.autofill_dataset_footer);
            if (linearLayout2 != null) {
                this.mFooter = sanitizeRemoteView2.applyWithTheme(this.mContext, null, interactionHandler == null ? newInteractionBlocker() : interactionHandler, this.mThemeId);
                applyCancelAction(this.mFooter, fillResponse.getCancelIds());
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "adding footer");
                }
                linearLayout2.addView(this.mFooter);
                linearLayout2.setVisibility(0);
            } else {
                this.mFooter = null;
            }
        } else {
            this.mFooter = null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) fillResponse.getDatasets().get(i2);
            int indexOf = dataset.getFieldIds().indexOf(autofillId);
            if (indexOf >= 0) {
                android.widget.RemoteViews sanitizeRemoteView3 = com.android.server.autofill.Helper.sanitizeRemoteView(dataset.getFieldPresentation(indexOf));
                if (sanitizeRemoteView3 == null) {
                    android.util.Slog.w(TAG, "not displaying UI on field " + autofillId + " because service didn't provide a presentation for it on " + dataset);
                } else {
                    try {
                        if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(TAG, "setting remote view for " + autofillId);
                        }
                        android.view.View applyWithTheme2 = sanitizeRemoteView3.applyWithTheme(this.mContext, null, interactionHandler2, this.mThemeId);
                        android.service.autofill.Dataset.DatasetFieldFilter filter = dataset.getFilter(indexOf);
                        if (filter == null) {
                            android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) dataset.getFieldValues().get(indexOf);
                            if (autofillValue != null && autofillValue.isText()) {
                                str3 = autofillValue.getTextValue().toString().toLowerCase();
                            } else {
                                str3 = null;
                            }
                            str2 = str3;
                            pattern = null;
                            z2 = true;
                        } else {
                            java.util.regex.Pattern pattern2 = filter.pattern;
                            if (pattern2 != null) {
                                pattern = pattern2;
                                z2 = true;
                                str2 = null;
                            } else {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(TAG, "Explicitly disabling filter at id " + autofillId + " for dataset #" + indexOf);
                                }
                                pattern = pattern2;
                                z2 = false;
                                str2 = null;
                            }
                        }
                        applyCancelAction(applyWithTheme2, fillResponse.getCancelIds());
                        arrayList.add(new com.android.server.autofill.ui.FillUi.ViewItem(dataset, pattern, z2, str2, applyWithTheme2));
                    } catch (java.lang.RuntimeException e2) {
                        android.util.Slog.e(TAG, "Error inflating remote views", e2);
                    }
                }
            }
        }
        this.mAdapter = new com.android.server.autofill.ui.FillUi.ItemsAdapter(arrayList);
        this.mListView = (android.widget.ListView) viewGroup.findViewById(android.R.id.autofill_dataset_list);
        this.mListView.setAdapter((android.widget.ListAdapter) this.mAdapter);
        this.mListView.setVisibility(0);
        this.mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda6
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i3, long j) {
                com.android.server.autofill.ui.FillUi.this.lambda$new$3(adapterView, view, i3, j);
            }
        });
        if (str == null) {
            this.mFilterText = null;
        } else {
            this.mFilterText = str.toLowerCase();
        }
        applyNewFilterText();
        this.mWindow = new com.android.server.autofill.ui.FillUi.AnchoredWindow(viewGroup, overlayControl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(android.view.View view, android.view.KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 4:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 66:
            case 111:
                return false;
            default:
                this.mCallback.dispatchUnhandledKey(keyEvent);
                return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        if (pendingIntent != null) {
            this.mCallback.startIntentSender(pendingIntent.getIntentSender());
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(android.service.autofill.FillResponse fillResponse, android.view.View view) {
        this.mCallback.onResponsePicked(fillResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        this.mCallback.onDatasetPicked(this.mAdapter.getItem(i).dataset);
    }

    private void applyCancelAction(android.view.View view, int[] iArr) {
        if (iArr == null) {
            return;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "fill UI has " + iArr.length + " actions");
        }
        if (!(view instanceof android.view.ViewGroup)) {
            android.util.Slog.w(TAG, "cannot apply actions because fill UI root is not a ViewGroup: " + view);
            return;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
        for (int i : iArr) {
            android.view.View findViewById = viewGroup.findViewById(i);
            if (findViewById == null) {
                android.util.Slog.w(TAG, "Ignoring cancel action for view " + i + " because it's not on " + viewGroup);
            } else {
                findViewById.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(android.view.View view2) {
                        com.android.server.autofill.ui.FillUi.this.lambda$applyCancelAction$4(view2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyCancelAction$4(android.view.View view) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, " Cancelling session after " + view + " clicked");
        }
        this.mCallback.cancelSession();
    }

    void requestShowFillUi() {
        this.mCallback.requestShowFillUi(this.mContentWidth, this.mContentHeight, this.mWindowPresenter);
    }

    private android.widget.RemoteViews.InteractionHandler newInteractionBlocker() {
        return new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda0
            public final boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$newInteractionBlocker$5;
                lambda$newInteractionBlocker$5 = com.android.server.autofill.ui.FillUi.lambda$newInteractionBlocker$5(view, pendingIntent, remoteResponse);
                return lambda$newInteractionBlocker$5;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$newInteractionBlocker$5(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Ignoring click on " + view);
            return true;
        }
        return true;
    }

    private void applyNewFilterText() {
        final int count = this.mAdapter.getCount();
        this.mAdapter.getFilter().filter(this.mFilterText, new android.widget.Filter.FilterListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda2
            @Override // android.widget.Filter.FilterListener
            public final void onFilterComplete(int i) {
                com.android.server.autofill.ui.FillUi.this.lambda$applyNewFilterText$6(count, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyNewFilterText$6(int i, int i2) {
        if (this.mDestroyed) {
            return;
        }
        int length = this.mFilterText == null ? 0 : this.mFilterText.length();
        if (i2 <= 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "No dataset matches filter with " + length + " chars");
            }
            this.mCallback.requestHideFillUi();
            return;
        }
        if (length > this.mMaxInputLengthForAutofill) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Not showing fill UI because user entered more than " + this.mMaxInputLengthForAutofill + " characters");
            }
            this.mCallback.requestHideFillUi();
            return;
        }
        if (updateContentSize()) {
            requestShowFillUi();
        }
        if (this.mAdapter.getCount() > this.mVisibleDatasetsMaxCount) {
            this.mListView.setVerticalScrollBarEnabled(true);
            this.mListView.onVisibilityAggregated(true);
        } else {
            this.mListView.setVerticalScrollBarEnabled(false);
        }
        if (this.mAdapter.getCount() != i) {
            this.mListView.requestLayout();
        }
    }

    public void setFilterText(@android.annotation.Nullable java.lang.String str) {
        java.lang.String lowerCase;
        throwIfDestroyed();
        if (this.mAdapter == null) {
            if (android.text.TextUtils.isEmpty(str)) {
                requestShowFillUi();
                return;
            } else {
                this.mCallback.requestHideFillUi();
                return;
            }
        }
        if (str == null) {
            lowerCase = null;
        } else {
            lowerCase = str.toLowerCase();
        }
        if (java.util.Objects.equals(this.mFilterText, lowerCase)) {
            return;
        }
        this.mFilterText = lowerCase;
        applyNewFilterText();
    }

    public void destroy(boolean z) {
        throwIfDestroyed();
        if (this.mWindow != null) {
            this.mWindow.hide(false);
        }
        this.mCallback.onDestroy();
        if (z) {
            this.mCallback.requestHideFillUiWhenDestroyed();
        }
        this.mDestroyed = true;
    }

    private boolean updateContentSize() {
        boolean z;
        boolean z2;
        if (this.mAdapter == null) {
            return false;
        }
        if (this.mFullScreen) {
            return true;
        }
        if (this.mAdapter.getCount() <= 0) {
            if (this.mContentWidth == 0) {
                z2 = false;
            } else {
                this.mContentWidth = 0;
                z2 = true;
            }
            if (this.mContentHeight == 0) {
                return z2;
            }
            this.mContentHeight = 0;
            return true;
        }
        android.graphics.Point point = this.mTempPoint;
        resolveMaxWindowSize(this.mContext, point);
        this.mContentWidth = 0;
        this.mContentHeight = 0;
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(point.x, Integer.MIN_VALUE);
        int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(point.y, Integer.MIN_VALUE);
        int count = this.mAdapter.getCount();
        if (this.mHeader == null) {
            z = false;
        } else {
            this.mHeader.measure(makeMeasureSpec, makeMeasureSpec2);
            z = updateWidth(this.mHeader, point) | false | updateHeight(this.mHeader, point);
        }
        for (int i = 0; i < count; i++) {
            android.view.View view = this.mAdapter.getItem(i).view;
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            z |= updateWidth(view, point);
            if (i < this.mVisibleDatasetsMaxCount) {
                z |= updateHeight(view, point);
            }
        }
        if (this.mFooter != null) {
            this.mFooter.measure(makeMeasureSpec, makeMeasureSpec2);
            return updateWidth(this.mFooter, point) | z | updateHeight(this.mFooter, point);
        }
        return z;
    }

    private boolean updateWidth(android.view.View view, android.graphics.Point point) {
        int max = java.lang.Math.max(this.mContentWidth, java.lang.Math.min(view.getMeasuredWidth(), point.x));
        if (max == this.mContentWidth) {
            return false;
        }
        this.mContentWidth = max;
        return true;
    }

    private boolean updateHeight(android.view.View view, android.graphics.Point point) {
        int min = this.mContentHeight + java.lang.Math.min(view.getMeasuredHeight(), point.y);
        if (min == this.mContentHeight) {
            return false;
        }
        this.mContentHeight = min;
        return true;
    }

    private void throwIfDestroyed() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("cannot interact with a destroyed instance");
        }
    }

    private static void resolveMaxWindowSize(android.content.Context context, android.graphics.Point point) {
        context.getDisplayNoVerify().getSize(point);
        android.util.TypedValue typedValue = sTempTypedValue;
        context.getTheme().resolveAttribute(android.R.^attr-private.autofillDatasetPickerMaxWidth, typedValue, true);
        point.x = (int) typedValue.getFraction(point.x, point.x);
        context.getTheme().resolveAttribute(android.R.^attr-private.autofillDatasetPickerMaxHeight, typedValue, true);
        point.y = (int) typedValue.getFraction(point.y, point.y);
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

        ViewItem(@android.annotation.Nullable android.service.autofill.Dataset dataset, @android.annotation.Nullable java.util.regex.Pattern pattern, boolean z, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.view.View view) {
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

    /* JADX INFO: Access modifiers changed from: private */
    final class AutofillWindowPresenter extends android.view.autofill.IAutofillWindowPresenter.Stub {
        private AutofillWindowPresenter() {
        }

        public void show(final android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.ui.FillUi.TAG, "AutofillWindowPresenter.show(): fit=" + z + ", params=" + com.android.server.autofill.Helper.paramsToString(layoutParams));
            }
            com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.FillUi$AutofillWindowPresenter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.FillUi.AutofillWindowPresenter.this.lambda$show$0(layoutParams);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$show$0(android.view.WindowManager.LayoutParams layoutParams) {
            com.android.server.autofill.ui.FillUi.this.mWindow.show(layoutParams);
        }

        public void hide(android.graphics.Rect rect) {
            android.os.Handler handler = com.android.server.UiThread.getHandler();
            final com.android.server.autofill.ui.FillUi.AnchoredWindow anchoredWindow = com.android.server.autofill.ui.FillUi.this.mWindow;
            java.util.Objects.requireNonNull(anchoredWindow);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.FillUi$AutofillWindowPresenter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.autofill.ui.FillUi.AnchoredWindow.this.hide();
                }
            });
        }
    }

    final class AnchoredWindow {
        private final android.view.View mContentView;

        @android.annotation.NonNull
        private final com.android.server.autofill.ui.OverlayControl mOverlayControl;
        private android.view.WindowManager.LayoutParams mShowParams;
        private boolean mShowing;
        private final android.view.WindowManager mWm;

        AnchoredWindow(android.view.View view, @android.annotation.NonNull com.android.server.autofill.ui.OverlayControl overlayControl) {
            this.mWm = (android.view.WindowManager) view.getContext().getSystemService(android.view.WindowManager.class);
            this.mContentView = view;
            this.mOverlayControl = overlayControl;
        }

        public void show(android.view.WindowManager.LayoutParams layoutParams) {
            this.mShowParams = layoutParams;
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.ui.FillUi.TAG, "show(): showing=" + this.mShowing + ", params=" + com.android.server.autofill.Helper.paramsToString(layoutParams));
            }
            try {
                layoutParams.packageName = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
                layoutParams.setTitle("Autofill UI");
                if (!this.mShowing) {
                    layoutParams.accessibilityTitle = this.mContentView.getContext().getString(android.R.string.app_upgrading_toast);
                    this.mWm.addView(this.mContentView, layoutParams);
                    this.mOverlayControl.hideOverlays();
                    this.mShowing = true;
                    com.android.server.autofill.ui.FillUi.this.mCallback.onShown();
                    return;
                }
                this.mWm.updateViewLayout(this.mContentView, layoutParams);
            } catch (android.view.WindowManager.BadTokenException e) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(com.android.server.autofill.ui.FillUi.TAG, "Filed with with token " + layoutParams.token + " gone.");
                }
                com.android.server.autofill.ui.FillUi.this.mCallback.onDestroy();
            } catch (java.lang.IllegalStateException e2) {
                android.util.Slog.wtf(com.android.server.autofill.ui.FillUi.TAG, "Exception showing window " + layoutParams, e2);
                com.android.server.autofill.ui.FillUi.this.mCallback.onDestroy();
            }
        }

        void hide() {
            hide(true);
        }

        void hide(boolean z) {
            try {
                try {
                    if (this.mShowing) {
                        this.mWm.removeView(this.mContentView);
                        this.mShowing = false;
                    }
                } catch (java.lang.IllegalStateException e) {
                    android.util.Slog.e(com.android.server.autofill.ui.FillUi.TAG, "Exception hiding window ", e);
                    if (z) {
                        com.android.server.autofill.ui.FillUi.this.mCallback.onDestroy();
                    }
                }
            } finally {
                this.mOverlayControl.showOverlays();
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mCallback: ");
        printWriter.println(this.mCallback != null);
        printWriter.print(str);
        printWriter.print("mFullScreen: ");
        printWriter.println(this.mFullScreen);
        printWriter.print(str);
        printWriter.print("mVisibleDatasetsMaxCount: ");
        printWriter.println(this.mVisibleDatasetsMaxCount);
        if (this.mHeader != null) {
            printWriter.print(str);
            printWriter.print("mHeader: ");
            printWriter.println(this.mHeader);
        }
        if (this.mListView != null) {
            printWriter.print(str);
            printWriter.print("mListView: ");
            printWriter.println(this.mListView);
        }
        if (this.mFooter != null) {
            printWriter.print(str);
            printWriter.print("mFooter: ");
            printWriter.println(this.mFooter);
        }
        if (this.mAdapter != null) {
            printWriter.print(str);
            printWriter.print("mAdapter: ");
            printWriter.println(this.mAdapter);
        }
        if (this.mFilterText != null) {
            printWriter.print(str);
            printWriter.print("mFilterText: ");
            com.android.server.autofill.Helper.printlnRedactedText(printWriter, this.mFilterText);
        }
        printWriter.print(str);
        printWriter.print("mContentWidth: ");
        printWriter.println(this.mContentWidth);
        printWriter.print(str);
        printWriter.print("mContentHeight: ");
        printWriter.println(this.mContentHeight);
        printWriter.print(str);
        printWriter.print("mDestroyed: ");
        printWriter.println(this.mDestroyed);
        printWriter.print(str);
        printWriter.print("mContext: ");
        printWriter.println(this.mContext);
        printWriter.print(str);
        printWriter.print("theme id: ");
        printWriter.print(this.mThemeId);
        switch (this.mThemeId) {
            case 16974827:
                printWriter.println(" (dark)");
                break;
            case 16974839:
                printWriter.println(" (light)");
                break;
            default:
                printWriter.println("(UNKNOWN_MODE)");
                break;
        }
        if (this.mWindow != null) {
            printWriter.print(str);
            printWriter.print("mWindow: ");
            java.lang.String str2 = str + "  ";
            printWriter.println();
            printWriter.print(str2);
            printWriter.print("showing: ");
            printWriter.println(this.mWindow.mShowing);
            printWriter.print(str2);
            printWriter.print("view: ");
            printWriter.println(this.mWindow.mContentView);
            if (this.mWindow.mShowParams != null) {
                printWriter.print(str2);
                printWriter.print("params: ");
                printWriter.println(this.mWindow.mShowParams);
            }
            printWriter.print(str2);
            printWriter.print("screen coordinates: ");
            if (this.mWindow.mContentView == null) {
                printWriter.println("N/A");
                return;
            }
            int[] locationOnScreen = this.mWindow.mContentView.getLocationOnScreen();
            printWriter.print(locationOnScreen[0]);
            printWriter.print("x");
            printWriter.println(locationOnScreen[1]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void announceSearchResultIfNeeded() {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAnnounceFilterResult == null) {
                this.mAnnounceFilterResult = new com.android.server.autofill.ui.FillUi.AnnounceFilterResult();
            }
            this.mAnnounceFilterResult.post();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ItemsAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {

        @android.annotation.NonNull
        private final java.util.List<com.android.server.autofill.ui.FillUi.ViewItem> mAllItems;

        @android.annotation.NonNull
        private final java.util.List<com.android.server.autofill.ui.FillUi.ViewItem> mFilteredItems = new java.util.ArrayList();

        ItemsAdapter(@android.annotation.NonNull java.util.List<com.android.server.autofill.ui.FillUi.ViewItem> list) {
            this.mAllItems = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
            this.mFilteredItems.addAll(list);
        }

        /* renamed from: com.android.server.autofill.ui.FillUi$ItemsAdapter$1, reason: invalid class name */
        class AnonymousClass1 extends android.widget.Filter {
            AnonymousClass1() {
            }

            @Override // android.widget.Filter
            protected android.widget.Filter.FilterResults performFiltering(final java.lang.CharSequence charSequence) {
                java.util.List list = (java.util.List) com.android.server.autofill.ui.FillUi.ItemsAdapter.this.mAllItems.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.autofill.ui.FillUi$ItemsAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$performFiltering$0;
                        lambda$performFiltering$0 = com.android.server.autofill.ui.FillUi.ItemsAdapter.AnonymousClass1.lambda$performFiltering$0(charSequence, (com.android.server.autofill.ui.FillUi.ViewItem) obj);
                        return lambda$performFiltering$0;
                    }
                }).collect(java.util.stream.Collectors.toList());
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values = list;
                filterResults.count = list.size();
                return filterResults;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ boolean lambda$performFiltering$0(java.lang.CharSequence charSequence, com.android.server.autofill.ui.FillUi.ViewItem viewItem) {
                return viewItem.matches(charSequence);
            }

            @Override // android.widget.Filter
            protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                int size = com.android.server.autofill.ui.FillUi.ItemsAdapter.this.mFilteredItems.size();
                com.android.server.autofill.ui.FillUi.ItemsAdapter.this.mFilteredItems.clear();
                if (filterResults.count > 0) {
                    com.android.server.autofill.ui.FillUi.ItemsAdapter.this.mFilteredItems.addAll((java.util.List) filterResults.values);
                }
                if (size != com.android.server.autofill.ui.FillUi.ItemsAdapter.this.mFilteredItems.size()) {
                    com.android.server.autofill.ui.FillUi.this.announceSearchResultIfNeeded();
                }
                com.android.server.autofill.ui.FillUi.ItemsAdapter.this.notifyDataSetChanged();
            }
        }

        @Override // android.widget.Filterable
        public android.widget.Filter getFilter() {
            return new com.android.server.autofill.ui.FillUi.ItemsAdapter.AnonymousClass1();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mFilteredItems.size();
        }

        @Override // android.widget.Adapter
        public com.android.server.autofill.ui.FillUi.ViewItem getItem(int i) {
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

    private final class AnnounceFilterResult implements java.lang.Runnable {
        private static final int SEARCH_RESULT_ANNOUNCEMENT_DELAY = 1000;

        private AnnounceFilterResult() {
        }

        public void post() {
            remove();
            com.android.server.autofill.ui.FillUi.this.mListView.postDelayed(this, 1000L);
        }

        public void remove() {
            com.android.server.autofill.ui.FillUi.this.mListView.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            java.lang.String format;
            int count = com.android.server.autofill.ui.FillUi.this.mListView.getAdapter().getCount();
            if (count <= 0) {
                format = com.android.server.autofill.ui.FillUi.this.mContext.getString(android.R.string.as_app_forced_to_restricted_bucket);
            } else {
                java.util.HashMap hashMap = new java.util.HashMap();
                hashMap.put(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, java.lang.Integer.valueOf(count));
                format = android.util.PluralsMessageFormatter.format(com.android.server.autofill.ui.FillUi.this.mContext.getResources(), hashMap, android.R.string.auto_data_switch_content);
            }
            com.android.server.autofill.ui.FillUi.this.mListView.announceForAccessibility(format);
        }
    }
}
