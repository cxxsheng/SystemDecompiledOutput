package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserListAdapter extends com.android.internal.app.ResolverListAdapter {
    public static final float CALLER_TARGET_SCORE_BOOST = 900.0f;
    private static final boolean DEBUG = false;
    private static final int MAX_CHOOSER_TARGETS_PER_APP = 2;
    private static final int MAX_SUGGESTED_APP_TARGETS = 4;
    public static final int NO_POSITION = -1;
    private static final float PINNED_SHORTCUT_TARGET_SCORE_BOOST = 1000.0f;
    public static final float SHORTCUT_TARGET_SCORE_BOOST = 90.0f;
    private static final java.lang.String TAG = "ChooserListAdapter";
    public static final int TARGET_BAD = -1;
    public static final int TARGET_CALLER = 0;
    public static final int TARGET_SERVICE = 1;
    public static final int TARGET_STANDARD = 2;
    public static final int TARGET_STANDARD_AZ = 3;
    private android.app.prediction.AppPredictor mAppPredictor;
    private android.app.prediction.AppPredictor.Callback mAppPredictorCallback;
    private com.android.internal.app.ResolverAppPredictorCallback mAppPredictorCallbackWrapper;
    private boolean mApplySharingAppLimits;
    private final com.android.internal.app.ChooserActivity.BaseChooserTargetComparator mBaseTargetComparator;
    private final java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> mCallerTargets;
    private final com.android.internal.app.ChooserActivityLogger mChooserActivityLogger;
    private final com.android.internal.app.ChooserListAdapter.ChooserListCommunicator mChooserListCommunicator;
    private boolean mEnableStackedApps;
    private final java.util.Map<com.android.internal.app.chooser.SelectableTargetInfo, com.android.internal.app.ChooserListAdapter.LoadDirectShareIconTask> mIconLoaders;
    private final android.os.UserHandle mInitialIntentsUserSpace;
    private boolean mListViewDataChanged;
    private final int mMaxShortcutTargetsPerApp;
    private int mNumShortcutResults;
    private final android.view.View.OnLayoutChangeListener mPinTextSpacingListener;
    private com.android.internal.app.chooser.ChooserTargetInfo mPlaceHolderTargetInfo;
    private final com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator mSelectableTargetInfoCommunicator;
    private final java.util.List<com.android.internal.app.chooser.ChooserTargetInfo> mServiceTargets;
    private java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> mSortedList;

    public interface ChooserListCommunicator extends com.android.internal.app.ResolverListAdapter.ResolverListCommunicator {
        int getMaxRankedTargets();

        boolean isSendAction(android.content.Intent intent);

        void sendListViewUpdateMessage(android.os.UserHandle userHandle);

        boolean shouldShowContentPreview();

        boolean shouldShowServiceTargets();
    }

    /* renamed from: com.android.internal.app.ChooserListAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.View.OnLayoutChangeListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            final android.widget.TextView textView = (android.widget.TextView) view;
            android.text.Layout layout = textView.getLayout();
            if (layout != null) {
                int i9 = 0;
                for (int i10 = 0; i10 < layout.getLineCount(); i10++) {
                    i9 = java.lang.Math.max((int) java.lang.Math.ceil(layout.getLineMax(i10)), i9);
                }
                int paddingLeft = i9 + textView.getPaddingLeft() + textView.getPaddingRight();
                if (textView.getWidth() > paddingLeft) {
                    android.view.ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.width = paddingLeft;
                    textView.setLayoutParams(layoutParams);
                    textView.post(new java.lang.Runnable() { // from class: com.android.internal.app.ChooserListAdapter$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.widget.TextView.this.requestLayout();
                        }
                    });
                }
                textView.removeOnLayoutChangeListener(this);
            }
        }
    }

    public ChooserListAdapter(android.content.Context context, java.util.List<android.content.Intent> list, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list2, boolean z, com.android.internal.app.ResolverListController resolverListController, com.android.internal.app.ChooserListAdapter.ChooserListCommunicator chooserListCommunicator, com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator selectableTargetInfoCommunicator, android.content.pm.PackageManager packageManager, com.android.internal.app.ChooserActivityLogger chooserActivityLogger, android.os.UserHandle userHandle) {
        super(context, list, null, list2, z, resolverListController, chooserListCommunicator, false, userHandle);
        android.content.pm.ActivityInfo activityInfo;
        android.content.pm.ResolveInfo resolveInfo;
        this.mEnableStackedApps = true;
        this.mNumShortcutResults = 0;
        this.mIconLoaders = new java.util.HashMap();
        this.mPlaceHolderTargetInfo = new com.android.internal.app.ChooserActivity.PlaceHolderTargetInfo();
        this.mServiceTargets = new java.util.ArrayList();
        this.mCallerTargets = new java.util.ArrayList();
        this.mBaseTargetComparator = new com.android.internal.app.ChooserActivity.BaseChooserTargetComparator();
        this.mListViewDataChanged = false;
        this.mSortedList = new java.util.ArrayList();
        this.mPinTextSpacingListener = new com.android.internal.app.ChooserListAdapter.AnonymousClass1();
        this.mMaxShortcutTargetsPerApp = context.getResources().getInteger(com.android.internal.R.integer.config_maxShortcutTargetsPerApp);
        this.mChooserListCommunicator = chooserListCommunicator;
        createPlaceHolders();
        this.mSelectableTargetInfoCommunicator = selectableTargetInfoCommunicator;
        this.mChooserActivityLogger = chooserActivityLogger;
        this.mInitialIntentsUserSpace = userHandle;
        if (intentArr != null) {
            for (android.content.Intent intent : intentArr) {
                if (intent != null) {
                    if (intent.getComponent() != null) {
                        try {
                            activityInfo = packageManager.getActivityInfo(intent.getComponent(), 0);
                            try {
                                resolveInfo = new android.content.pm.ResolveInfo();
                                try {
                                    resolveInfo.activityInfo = activityInfo;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                }
                            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                resolveInfo = null;
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                            activityInfo = null;
                            resolveInfo = null;
                        }
                    } else {
                        activityInfo = null;
                        resolveInfo = null;
                    }
                    if (activityInfo == null) {
                        resolveInfo = packageManager.resolveActivity(intent.getClass() == android.content.Intent.class ? intent : new android.content.Intent(intent), 65536);
                        activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
                    }
                    if (activityInfo == null) {
                        android.util.Log.w(TAG, "No activity found for " + intent);
                    } else {
                        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService("user");
                        if (intent instanceof android.content.pm.LabeledIntent) {
                            android.content.pm.LabeledIntent labeledIntent = (android.content.pm.LabeledIntent) intent;
                            resolveInfo.resolvePackageName = labeledIntent.getSourcePackage();
                            resolveInfo.labelRes = labeledIntent.getLabelResource();
                            resolveInfo.nonLocalizedLabel = labeledIntent.getNonLocalizedLabel();
                            resolveInfo.icon = labeledIntent.getIconResource();
                            resolveInfo.iconResourceId = resolveInfo.icon;
                        }
                        if (userManager.isManagedProfile()) {
                            resolveInfo.noResourceId = true;
                            resolveInfo.icon = 0;
                        }
                        resolveInfo.userHandle = this.mInitialIntentsUserSpace;
                        this.mCallerTargets.add(new com.android.internal.app.chooser.DisplayResolveInfo(intent, resolveInfo, intent, makePresentationGetter(resolveInfo)));
                        if (this.mCallerTargets.size() == 4) {
                            break;
                        }
                    }
                }
            }
        }
        this.mApplySharingAppLimits = android.provider.DeviceConfig.getBoolean("systemui", com.android.internal.config.sysui.SystemUiDeviceConfigFlags.APPLY_SHARING_APP_LIMITS_IN_SYSUI, true);
    }

    android.app.prediction.AppPredictor getAppPredictor() {
        return this.mAppPredictor;
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public void handlePackagesChanged() {
        createPlaceHolders();
        this.mChooserListCommunicator.onHandlePackagesChanged(this);
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        if (!this.mListViewDataChanged) {
            this.mChooserListCommunicator.sendListViewUpdateMessage(getUserHandle());
            this.mListViewDataChanged = true;
        }
    }

    void refreshListView() {
        if (this.mListViewDataChanged) {
            super.notifyDataSetChanged();
        }
        this.mListViewDataChanged = false;
    }

    private void createPlaceHolders() {
        this.mNumShortcutResults = 0;
        this.mServiceTargets.clear();
        for (int i = 0; i < this.mChooserListCommunicator.getMaxRankedTargets(); i++) {
            this.mServiceTargets.add(this.mPlaceHolderTargetInfo);
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter
    android.view.View onCreateView(android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(com.android.internal.R.layout.resolve_grid_item, viewGroup, false);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected void onBindView(android.view.View view, com.android.internal.app.chooser.TargetInfo targetInfo, int i) {
        com.android.internal.app.ResolverListAdapter.ViewHolder viewHolder = (com.android.internal.app.ResolverListAdapter.ViewHolder) view.getTag();
        if (targetInfo == null) {
            viewHolder.icon.lambda$setImageURIAsync$2(this.mContext.getDrawable(com.android.internal.R.drawable.resolver_icon_placeholder));
            return;
        }
        viewHolder.bindLabel(targetInfo.getDisplayLabel(), targetInfo.getExtendedInfo(), alwaysShowSubLabel());
        viewHolder.bindIcon(targetInfo);
        if (targetInfo instanceof com.android.internal.app.chooser.SelectableTargetInfo) {
            com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo = (com.android.internal.app.chooser.SelectableTargetInfo) targetInfo;
            com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = selectableTargetInfo.getDisplayResolveInfo();
            java.lang.String displayLabel = displayResolveInfo != null ? displayResolveInfo.getDisplayLabel() : "";
            java.lang.CharSequence extendedInfo = targetInfo.getExtendedInfo();
            java.lang.CharSequence[] charSequenceArr = new java.lang.CharSequence[3];
            charSequenceArr[0] = targetInfo.getDisplayLabel();
            charSequenceArr[1] = extendedInfo != null ? extendedInfo : "";
            charSequenceArr[2] = displayLabel;
            viewHolder.updateContentDescription(java.lang.String.join(" ", charSequenceArr));
            if (!selectableTargetInfo.hasDisplayIcon()) {
                loadDirectShareIcon(selectableTargetInfo);
            }
        } else if (targetInfo instanceof com.android.internal.app.chooser.DisplayResolveInfo) {
            com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo2 = (com.android.internal.app.chooser.DisplayResolveInfo) targetInfo;
            if (!displayResolveInfo2.hasDisplayIcon()) {
                loadIcon(displayResolveInfo2);
            }
        }
        if (targetInfo instanceof com.android.internal.app.ChooserActivity.PlaceHolderTargetInfo) {
            viewHolder.text.setMaxWidth(this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_direct_share_label_placeholder_max_width));
            viewHolder.text.setBackground(this.mContext.getResources().getDrawable(com.android.internal.R.drawable.chooser_direct_share_label_placeholder, this.mContext.getTheme()));
            viewHolder.itemView.setBackground(null);
        } else {
            viewHolder.text.setMaxWidth(Integer.MAX_VALUE);
            viewHolder.text.setBackground(null);
            viewHolder.itemView.setBackground(viewHolder.defaultItemViewBackground);
        }
        viewHolder.text.removeOnLayoutChangeListener(this.mPinTextSpacingListener);
        if (targetInfo instanceof com.android.internal.app.chooser.MultiDisplayResolveInfo) {
            android.graphics.drawable.Drawable drawable = this.mContext.getDrawable(com.android.internal.R.drawable.chooser_group_background);
            viewHolder.text.setPaddingRelative(0, 0, drawable.getIntrinsicWidth(), 0);
            viewHolder.text.setBackground(drawable);
        } else {
            if (targetInfo.isPinned() && (getPositionTargetType(i) == 2 || getPositionTargetType(i) == 1)) {
                android.graphics.drawable.Drawable drawable2 = this.mContext.getDrawable(com.android.internal.R.drawable.chooser_pinned_background);
                viewHolder.text.setPaddingRelative(drawable2.getIntrinsicWidth(), 0, 0, 0);
                viewHolder.text.setBackground(drawable2);
                viewHolder.text.addOnLayoutChangeListener(this.mPinTextSpacingListener);
                return;
            }
            viewHolder.text.setBackground(null);
            viewHolder.text.setPaddingRelative(0, 0, 0, 0);
        }
    }

    private void loadDirectShareIcon(com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo) {
        if (this.mIconLoaders.get(selectableTargetInfo) == null) {
            com.android.internal.app.ChooserListAdapter.LoadDirectShareIconTask createLoadDirectShareIconTask = createLoadDirectShareIconTask(selectableTargetInfo);
            this.mIconLoaders.put(selectableTargetInfo, createLoadDirectShareIconTask);
            createLoadDirectShareIconTask.loadIcon();
        }
    }

    protected com.android.internal.app.ChooserListAdapter.LoadDirectShareIconTask createLoadDirectShareIconTask(com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo) {
        return new com.android.internal.app.ChooserListAdapter.LoadDirectShareIconTask(selectableTargetInfo);
    }

    void updateAlphabeticalList() {
        new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.util.List<com.android.internal.app.chooser.DisplayResolveInfo>>() { // from class: com.android.internal.app.ChooserListAdapter.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> doInBackground(java.lang.Void... voidArr) {
                java.util.ArrayList<com.android.internal.app.chooser.DisplayResolveInfo> arrayList = new java.util.ArrayList();
                arrayList.addAll(com.android.internal.app.ChooserListAdapter.this.mDisplayList);
                arrayList.addAll(com.android.internal.app.ChooserListAdapter.this.mCallerTargets);
                if (!com.android.internal.app.ChooserListAdapter.this.mEnableStackedApps) {
                    return arrayList;
                }
                java.util.HashMap hashMap = new java.util.HashMap();
                for (com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo : arrayList) {
                    if (displayResolveInfo.getResolveInfo().userHandle == null) {
                        android.util.Log.e(com.android.internal.app.ChooserListAdapter.TAG, "ResolveInfo with null UserHandle found: " + displayResolveInfo.getResolveInfo());
                    }
                    java.lang.String str = displayResolveInfo.getResolvedComponentName().getPackageName() + '#' + ((java.lang.Object) displayResolveInfo.getDisplayLabel()) + '#' + com.android.internal.app.ResolverActivity.getResolveInfoUserHandle(displayResolveInfo.getResolveInfo(), com.android.internal.app.ChooserListAdapter.this.getUserHandle()).getIdentifier();
                    com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo2 = (com.android.internal.app.chooser.DisplayResolveInfo) hashMap.get(str);
                    if (displayResolveInfo2 == null) {
                        hashMap.put(str, displayResolveInfo);
                    } else if (displayResolveInfo2 instanceof com.android.internal.app.chooser.MultiDisplayResolveInfo) {
                        ((com.android.internal.app.chooser.MultiDisplayResolveInfo) displayResolveInfo2).addTarget(displayResolveInfo);
                    } else {
                        com.android.internal.app.chooser.MultiDisplayResolveInfo multiDisplayResolveInfo = new com.android.internal.app.chooser.MultiDisplayResolveInfo(str, displayResolveInfo2);
                        multiDisplayResolveInfo.addTarget(displayResolveInfo);
                        hashMap.put(str, multiDisplayResolveInfo);
                    }
                }
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                arrayList2.addAll(hashMap.values());
                java.util.Collections.sort(arrayList2, new com.android.internal.app.ChooserActivity.AzInfoComparator(com.android.internal.app.ChooserListAdapter.this.mContext));
                return arrayList2;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> list) {
                com.android.internal.app.ChooserListAdapter.this.mSortedList = list;
                com.android.internal.app.ChooserListAdapter.this.notifyDataSetChanged();
            }
        }.execute(new java.lang.Void[0]);
    }

    @Override // com.android.internal.app.ResolverListAdapter, android.widget.Adapter
    public int getCount() {
        return getRankedTargetCount() + getAlphaTargetCount() + getSelectableServiceTargetCount() + getCallerTargetCount();
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public int getUnfilteredCount() {
        int unfilteredCount = super.getUnfilteredCount();
        if (unfilteredCount > this.mChooserListCommunicator.getMaxRankedTargets()) {
            unfilteredCount += this.mChooserListCommunicator.getMaxRankedTargets();
        }
        return unfilteredCount + getSelectableServiceTargetCount() + getCallerTargetCount();
    }

    public int getCallerTargetCount() {
        return this.mCallerTargets.size();
    }

    public int getSelectableServiceTargetCount() {
        java.util.Iterator<com.android.internal.app.chooser.ChooserTargetInfo> it = this.mServiceTargets.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() instanceof com.android.internal.app.chooser.SelectableTargetInfo) {
                i++;
            }
        }
        return i;
    }

    public int getServiceTargetCount() {
        if (this.mChooserListCommunicator.shouldShowServiceTargets()) {
            return java.lang.Math.min(this.mServiceTargets.size(), this.mChooserListCommunicator.getMaxRankedTargets());
        }
        return 0;
    }

    int getAlphaTargetCount() {
        int size = this.mSortedList.size();
        if (this.mCallerTargets.size() + this.mDisplayList.size() > this.mChooserListCommunicator.getMaxRankedTargets()) {
            return size;
        }
        return 0;
    }

    public int getRankedTargetCount() {
        return java.lang.Math.min(this.mChooserListCommunicator.getMaxRankedTargets() - getCallerTargetCount(), super.getCount());
    }

    public int getPositionTargetType(int i) {
        int serviceTargetCount = getServiceTargetCount();
        if (i < serviceTargetCount) {
            return 1;
        }
        int i2 = serviceTargetCount + 0;
        int callerTargetCount = getCallerTargetCount();
        if (i - i2 < callerTargetCount) {
            return 0;
        }
        int i3 = i2 + callerTargetCount;
        int rankedTargetCount = getRankedTargetCount();
        if (i - i3 < rankedTargetCount) {
            return 2;
        }
        if (i - (i3 + rankedTargetCount) < getAlphaTargetCount()) {
            return 3;
        }
        return -1;
    }

    @Override // com.android.internal.app.ResolverListAdapter, android.widget.Adapter
    public com.android.internal.app.chooser.TargetInfo getItem(int i) {
        return targetInfoForPosition(i, true);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public com.android.internal.app.chooser.TargetInfo targetInfoForPosition(int i, boolean z) {
        if (i == -1) {
            return null;
        }
        int serviceTargetCount = z ? getServiceTargetCount() : getSelectableServiceTargetCount();
        if (i < serviceTargetCount) {
            return this.mServiceTargets.get(i);
        }
        int i2 = serviceTargetCount + 0;
        int callerTargetCount = getCallerTargetCount();
        int i3 = i - i2;
        if (i3 < callerTargetCount) {
            return this.mCallerTargets.get(i3);
        }
        int i4 = i2 + callerTargetCount;
        int rankedTargetCount = getRankedTargetCount();
        int i5 = i - i4;
        if (i5 < rankedTargetCount) {
            return z ? super.getItem(i5) : getDisplayResolveInfo(i5);
        }
        int i6 = i - (i4 + rankedTargetCount);
        if (i6 >= getAlphaTargetCount() || this.mSortedList.isEmpty()) {
            return null;
        }
        return this.mSortedList.get(i6);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected boolean shouldAddResolveInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        java.util.Iterator<com.android.internal.app.chooser.DisplayResolveInfo> it = this.mCallerTargets.iterator();
        while (it.hasNext()) {
            if (this.mResolverListCommunicator.resolveInfoMatch(displayResolveInfo.getResolveInfo(), it.next().getResolveInfo())) {
                return false;
            }
        }
        return super.shouldAddResolveInfo(displayResolveInfo);
    }

    public java.util.List<com.android.internal.app.chooser.ChooserTargetInfo> getSurfacedTargetInfo() {
        return this.mServiceTargets.subList(0, java.lang.Math.min(this.mChooserListCommunicator.getMaxRankedTargets(), getSelectableServiceTargetCount()));
    }

    public void addServiceResults(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, java.util.List<android.service.chooser.ChooserTarget> list, int i, java.util.Map<android.service.chooser.ChooserTarget, android.content.pm.ShortcutInfo> map) {
        float f;
        if (list.size() != 0) {
            float baseScore = getBaseScore(displayResolveInfo, i);
            java.util.Collections.sort(list, this.mBaseTargetComparator);
            int i2 = 2;
            int i3 = 0;
            boolean z = i == 2 || i == 3;
            if (z) {
                i2 = this.mMaxShortcutTargetsPerApp;
            }
            int min = this.mApplySharingAppLimits ? java.lang.Math.min(list.size(), i2) : list.size();
            float f2 = 0.0f;
            int i4 = 0;
            boolean z2 = false;
            while (i4 < min) {
                android.service.chooser.ChooserTarget chooserTarget = list.get(i4);
                float score = chooserTarget.getScore();
                if (this.mApplySharingAppLimits) {
                    score *= baseScore;
                    if (i4 > 0 && score >= f2) {
                        score = 0.95f * f2;
                    }
                }
                android.content.pm.ShortcutInfo shortcutInfo = z ? map.get(chooserTarget) : null;
                if (shortcutInfo != null && shortcutInfo.isPinned()) {
                    f = score + 1000.0f;
                } else {
                    f = score;
                }
                boolean insertServiceTarget = insertServiceTarget(new com.android.internal.app.chooser.SelectableTargetInfo(this.mContext.createContextAsUser(getUserHandle(), i3), displayResolveInfo, chooserTarget, f, this.mSelectableTargetInfoCommunicator, shortcutInfo));
                if (insertServiceTarget && z) {
                    this.mNumShortcutResults++;
                }
                z2 |= insertServiceTarget;
                i4++;
                f2 = f;
                i3 = 0;
            }
            if (z2) {
                notifyDataSetChanged();
            }
        }
    }

    int getNumServiceTargetsForExpand() {
        return this.mNumShortcutResults;
    }

    public float getBaseScore(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, int i) {
        if (displayResolveInfo == null) {
            return 900.0f;
        }
        float score = super.getScore(displayResolveInfo);
        if (i == 2 || i == 3) {
            return score * 90.0f;
        }
        return score;
    }

    static /* synthetic */ boolean lambda$completeServiceTargetLoading$0(com.android.internal.app.chooser.ChooserTargetInfo chooserTargetInfo) {
        return chooserTargetInfo instanceof com.android.internal.app.ChooserActivity.PlaceHolderTargetInfo;
    }

    public void completeServiceTargetLoading() {
        this.mServiceTargets.removeIf(new java.util.function.Predicate() { // from class: com.android.internal.app.ChooserListAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.app.ChooserListAdapter.lambda$completeServiceTargetLoading$0((com.android.internal.app.chooser.ChooserTargetInfo) obj);
            }
        });
        if (this.mServiceTargets.isEmpty()) {
            this.mServiceTargets.add(new com.android.internal.app.ChooserActivity.EmptyTargetInfo());
            this.mChooserActivityLogger.logSharesheetEmptyDirectShareRow();
        }
        notifyDataSetChanged();
    }

    private boolean insertServiceTarget(com.android.internal.app.chooser.ChooserTargetInfo chooserTargetInfo) {
        if (this.mServiceTargets.size() == 1 && (this.mServiceTargets.get(0) instanceof com.android.internal.app.ChooserActivity.EmptyTargetInfo)) {
            return false;
        }
        java.util.Iterator<com.android.internal.app.chooser.ChooserTargetInfo> it = this.mServiceTargets.iterator();
        while (it.hasNext()) {
            if (chooserTargetInfo.isSimilar(it.next())) {
                return false;
            }
        }
        int size = this.mServiceTargets.size();
        float modifiedScore = chooserTargetInfo.getModifiedScore();
        for (int i = 0; i < java.lang.Math.min(size, this.mChooserListCommunicator.getMaxRankedTargets()); i++) {
            com.android.internal.app.chooser.ChooserTargetInfo chooserTargetInfo2 = this.mServiceTargets.get(i);
            if (chooserTargetInfo2 == null) {
                this.mServiceTargets.set(i, chooserTargetInfo);
                return true;
            }
            if (modifiedScore > chooserTargetInfo2.getModifiedScore()) {
                this.mServiceTargets.add(i, chooserTargetInfo);
                return true;
            }
        }
        if (size >= this.mChooserListCommunicator.getMaxRankedTargets()) {
            return false;
        }
        this.mServiceTargets.add(chooserTargetInfo);
        return true;
    }

    public android.service.chooser.ChooserTarget getChooserTargetForValue(int i) {
        return this.mServiceTargets.get(i).getChooserTarget();
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected boolean alwaysShowSubLabel() {
        return true;
    }

    @Override // com.android.internal.app.ResolverListAdapter
    android.os.AsyncTask<java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>, java.lang.Void, java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>> createSortingTask(final boolean z) {
        return new android.os.AsyncTask<java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>, java.lang.Void, java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>>() { // from class: com.android.internal.app.ChooserListAdapter.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> doInBackground(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>... listArr) {
                android.os.Trace.beginSection("ChooserListAdapter#SortingTask");
                com.android.internal.app.ChooserListAdapter.this.mResolverListController.topK(listArr[0], com.android.internal.app.ChooserListAdapter.this.mChooserListCommunicator.getMaxRankedTargets());
                android.os.Trace.endSection();
                return listArr[0];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
                com.android.internal.app.ChooserListAdapter.this.processSortedList(list, z);
                if (z) {
                    com.android.internal.app.ChooserListAdapter.this.mChooserListCommunicator.updateProfileViewButton();
                    com.android.internal.app.ChooserListAdapter.this.notifyDataSetChanged();
                }
            }
        };
    }

    public void setAppPredictor(android.app.prediction.AppPredictor appPredictor) {
        this.mAppPredictor = appPredictor;
    }

    public void setAppPredictorCallback(android.app.prediction.AppPredictor.Callback callback, com.android.internal.app.ResolverAppPredictorCallback resolverAppPredictorCallback) {
        this.mAppPredictorCallback = callback;
        this.mAppPredictorCallbackWrapper = resolverAppPredictorCallback;
    }

    public void destroyAppPredictor() {
        if (getAppPredictor() != null) {
            getAppPredictor().unregisterPredictionUpdates(this.mAppPredictorCallback);
            getAppPredictor().destroy();
            setAppPredictor(null);
        }
        if (this.mAppPredictorCallbackWrapper != null) {
            this.mAppPredictorCallbackWrapper.destroy();
        }
    }

    public class LoadDirectShareIconTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Boolean> {
        private final com.android.internal.app.chooser.SelectableTargetInfo mTargetInfo;

        private LoadDirectShareIconTask(com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo) {
            this.mTargetInfo = selectableTargetInfo;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Boolean doInBackground(java.lang.Void... voidArr) {
            return java.lang.Boolean.valueOf(this.mTargetInfo.loadIcon());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(java.lang.Boolean bool) {
            if (bool.booleanValue()) {
                com.android.internal.app.ChooserListAdapter.this.notifyDataSetChanged();
            }
        }

        public void loadIcon() {
            execute(new java.lang.Void[0]);
        }
    }
}
