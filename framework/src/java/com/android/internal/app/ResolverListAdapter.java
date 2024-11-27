package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverListAdapter extends android.widget.BaseAdapter {
    private static final java.lang.String TAG = "ResolverListAdapter";
    private static android.graphics.ColorMatrixColorFilter sSuspendedMatrixColorFilter;
    private final java.util.List<android.content.pm.ResolveInfo> mBaseResolveList;
    protected final android.content.Context mContext;
    private boolean mFilterLastUsed;
    private final int mIconDpi;
    protected final android.view.LayoutInflater mInflater;
    private final android.content.Intent[] mInitialIntents;
    private final android.os.UserHandle mInitialIntentsUserSpace;
    private final java.util.List<android.content.Intent> mIntents;
    private final boolean mIsAudioCaptureDevice;
    private boolean mIsTabLoaded;
    protected android.content.pm.ResolveInfo mLastChosen;
    private com.android.internal.app.chooser.DisplayResolveInfo mOtherProfile;
    private int mPlaceholderCount;
    private final android.content.pm.PackageManager mPm;
    private java.lang.Runnable mPostListReadyRunnable;
    final com.android.internal.app.ResolverListAdapter.ResolverListCommunicator mResolverListCommunicator;
    com.android.internal.app.ResolverListController mResolverListController;
    private java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> mUnfilteredResolveList;
    private int mLastChosenPosition = -1;
    private final java.util.Map<com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.ResolverListAdapter.LoadIconTask> mIconLoaders = new java.util.HashMap();
    private final java.util.Map<com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.ResolverListAdapter.LoadLabelTask> mLabelLoaders = new java.util.HashMap();
    java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> mDisplayList = new java.util.ArrayList();

    public ResolverListAdapter(android.content.Context context, java.util.List<android.content.Intent> list, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list2, boolean z, com.android.internal.app.ResolverListController resolverListController, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator resolverListCommunicator, boolean z2, android.os.UserHandle userHandle) {
        this.mContext = context;
        this.mIntents = list;
        this.mInitialIntents = intentArr;
        this.mBaseResolveList = list2;
        this.mInflater = android.view.LayoutInflater.from(context);
        this.mPm = context.getPackageManager();
        this.mFilterLastUsed = z;
        this.mResolverListController = resolverListController;
        this.mResolverListCommunicator = resolverListCommunicator;
        this.mIsAudioCaptureDevice = z2;
        this.mIconDpi = ((android.app.ActivityManager) this.mContext.getSystemService("activity")).getLauncherLargeIconDensity();
        this.mInitialIntentsUserSpace = userHandle;
    }

    public com.android.internal.app.ResolverListController getResolverListController() {
        return this.mResolverListController;
    }

    public void handlePackagesChanged() {
        this.mResolverListCommunicator.onHandlePackagesChanged(this);
    }

    public void setPlaceholderCount(int i) {
        this.mPlaceholderCount = i;
    }

    public int getPlaceholderCount() {
        return this.mPlaceholderCount;
    }

    public com.android.internal.app.chooser.DisplayResolveInfo getFilteredItem() {
        if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
            return this.mDisplayList.get(this.mLastChosenPosition);
        }
        return null;
    }

    public com.android.internal.app.chooser.DisplayResolveInfo getOtherProfile() {
        return this.mOtherProfile;
    }

    public int getFilteredPosition() {
        if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
            return this.mLastChosenPosition;
        }
        return -1;
    }

    public boolean hasFilteredItem() {
        return this.mFilterLastUsed && this.mLastChosen != null;
    }

    public float getScore(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return this.mResolverListController.getScore(displayResolveInfo);
    }

    public float getScore(com.android.internal.app.chooser.TargetInfo targetInfo) {
        return this.mResolverListController.getScore(targetInfo);
    }

    public void updateModel(com.android.internal.app.chooser.TargetInfo targetInfo) {
        this.mResolverListController.updateModel(targetInfo);
    }

    public void updateChooserCounts(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        this.mResolverListController.updateChooserCounts(str, userHandle, str2);
    }

    java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getUnfilteredResolveList() {
        return this.mUnfilteredResolveList;
    }

    protected boolean rebuildList(boolean z) {
        android.os.Trace.beginSection("ResolverListAdapter#rebuildList");
        this.mDisplayList.clear();
        this.mIsTabLoaded = false;
        this.mLastChosenPosition = -1;
        java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> initialRebuiltResolveList = getInitialRebuiltResolveList();
        this.mUnfilteredResolveList = performPrimaryResolveListFiltering(initialRebuiltResolveList);
        com.android.internal.app.ResolverActivity.ResolvedComponentInfo firstNonCurrentUserResolvedComponentInfo = getFirstNonCurrentUserResolvedComponentInfo(initialRebuiltResolveList);
        updateOtherProfileTreatment(firstNonCurrentUserResolvedComponentInfo);
        if (firstNonCurrentUserResolvedComponentInfo != null) {
            initialRebuiltResolveList.remove(firstNonCurrentUserResolvedComponentInfo);
        }
        java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> performSecondaryResolveListFiltering = performSecondaryResolveListFiltering(initialRebuiltResolveList, this.mUnfilteredResolveList == initialRebuiltResolveList);
        if (performSecondaryResolveListFiltering != null) {
            this.mUnfilteredResolveList = performSecondaryResolveListFiltering;
        }
        boolean finishRebuildingListWithFilteredResults = finishRebuildingListWithFilteredResults(initialRebuiltResolveList, z);
        android.os.Trace.endSection();
        return finishRebuildingListWithFilteredResults;
    }

    java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getInitialRebuiltResolveList() {
        if (this.mBaseResolveList != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            this.mResolverListController.addResolveListDedupe(arrayList, this.mResolverListCommunicator.getTargetIntent(), this.mBaseResolveList);
            return arrayList;
        }
        return this.mResolverListController.getResolversForIntent(true, this.mResolverListCommunicator.shouldGetActivityMetadata(), this.mResolverListCommunicator.shouldGetOnlyDefaultActivities(), this.mIntents);
    }

    java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> performPrimaryResolveListFiltering(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        if (this.mBaseResolveList != null || list == null) {
            return list;
        }
        java.util.ArrayList<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> filterIneligibleActivities = this.mResolverListController.filterIneligibleActivities(list, true);
        return filterIneligibleActivities == null ? list : filterIneligibleActivities;
    }

    java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> performSecondaryResolveListFiltering(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        return this.mResolverListController.filterLowPriority(list, z);
    }

    void updateOtherProfileTreatment(com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        this.mLastChosen = null;
        if (resolvedComponentInfo != null) {
            this.mOtherProfile = makeOtherProfileDisplayResolveInfo(this.mContext, resolvedComponentInfo, this.mPm, this.mResolverListCommunicator, this.mIconDpi);
            return;
        }
        this.mOtherProfile = null;
        try {
            this.mLastChosen = this.mResolverListController.getLastChosen();
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Error calling getLastChosenActivity\n" + e);
        }
    }

    boolean finishRebuildingListWithFilteredResults(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        if (list == null || list.size() < 2) {
            setPlaceholderCount(0);
            processSortedList(list, z);
            return true;
        }
        int size = list.size();
        if (this.mResolverListCommunicator.useLayoutWithDefault()) {
            size--;
        }
        setPlaceholderCount(size);
        postListReadyRunnable(z, false);
        createSortingTask(z).execute(list);
        return false;
    }

    android.os.AsyncTask<java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>, java.lang.Void, java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>> createSortingTask(final boolean z) {
        return new android.os.AsyncTask<java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>, java.lang.Void, java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>>() { // from class: com.android.internal.app.ResolverListAdapter.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> doInBackground(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo>... listArr) {
                com.android.internal.app.ResolverListAdapter.this.mResolverListController.sort(listArr[0]);
                return listArr[0];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
                com.android.internal.app.ResolverListAdapter.this.processSortedList(list, z);
                com.android.internal.app.ResolverListAdapter.this.notifyDataSetChanged();
                if (z) {
                    com.android.internal.app.ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
                }
            }
        };
    }

    protected void processSortedList(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        int size = list != null ? list.size() : 0;
        android.os.Trace.beginSection("ResolverListAdapter#processSortedList:" + size);
        if (size != 0) {
            if (this.mInitialIntents != null) {
                for (int i = 0; i < this.mInitialIntents.length; i++) {
                    android.content.Intent intent = this.mInitialIntents[i];
                    if (intent != null) {
                        android.content.pm.ActivityInfo resolveActivityInfo = (intent.getClass() == android.content.Intent.class ? intent : new android.content.Intent(intent)).resolveActivityInfo(this.mPm, 0);
                        if (resolveActivityInfo == null) {
                            android.util.Log.w(TAG, "No activity found for " + intent);
                        } else {
                            android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
                            resolveInfo.activityInfo = resolveActivityInfo;
                            android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService("user");
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
                            addResolveInfo(new com.android.internal.app.chooser.DisplayResolveInfo(intent, resolveInfo, resolveInfo.loadLabel(this.mPm), null, intent, makePresentationGetter(resolveInfo)));
                        }
                    }
                }
            }
            for (com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
                if (resolvedComponentInfo.getResolveInfoAt(0) != null) {
                    addResolveInfoWithAlternates(resolvedComponentInfo);
                }
            }
        }
        this.mResolverListCommunicator.sendVoiceChoicesIfNeeded();
        postListReadyRunnable(z, true);
        this.mIsTabLoaded = true;
        android.os.Trace.endSection();
    }

    void postListReadyRunnable(final boolean z, final boolean z2) {
        if (this.mPostListReadyRunnable == null) {
            this.mPostListReadyRunnable = new java.lang.Runnable() { // from class: com.android.internal.app.ResolverListAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    com.android.internal.app.ResolverListAdapter.this.mResolverListCommunicator.onPostListReady(com.android.internal.app.ResolverListAdapter.this, z, z2);
                    com.android.internal.app.ResolverListAdapter.this.mPostListReadyRunnable = null;
                }
            };
            this.mContext.getMainThreadHandler().post(this.mPostListReadyRunnable);
        }
    }

    private void addResolveInfoWithAlternates(com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        int count = resolvedComponentInfo.getCount();
        android.content.Intent intentAt = resolvedComponentInfo.getIntentAt(0);
        android.content.pm.ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        android.content.Intent replacementIntent = this.mResolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, intentAt);
        android.content.Intent replacementIntent2 = this.mResolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, this.mResolverListCommunicator.getTargetIntent());
        if (replacementIntent != null) {
            replacementIntent2 = replacementIntent;
        }
        com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = new com.android.internal.app.chooser.DisplayResolveInfo(intentAt, resolveInfoAt, replacementIntent2, makePresentationGetter(resolveInfoAt));
        displayResolveInfo.setPinned(resolvedComponentInfo.isPinned());
        if (resolvedComponentInfo.isPinned()) {
            android.util.Log.i(TAG, "Pinned item: " + resolvedComponentInfo.name);
        }
        addResolveInfo(displayResolveInfo);
        if (replacementIntent == intentAt) {
            for (int i = 1; i < count; i++) {
                displayResolveInfo.addAlternateSourceIntent(resolvedComponentInfo.getIntentAt(i));
            }
        }
        updateLastChosenPosition(resolveInfoAt);
    }

    private void updateLastChosenPosition(android.content.pm.ResolveInfo resolveInfo) {
        if (this.mOtherProfile != null) {
            this.mLastChosenPosition = -1;
        } else if (this.mLastChosen != null && this.mLastChosen.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName) && this.mLastChosen.activityInfo.name.equals(resolveInfo.activityInfo.name)) {
            this.mLastChosenPosition = this.mDisplayList.size() - 1;
        }
    }

    private void addResolveInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        if (displayResolveInfo != null && displayResolveInfo.getResolveInfo() != null && displayResolveInfo.getResolveInfo().targetUserId == -2 && shouldAddResolveInfo(displayResolveInfo)) {
            this.mDisplayList.add(displayResolveInfo);
            android.util.Log.i(TAG, "Add DisplayResolveInfo component: " + displayResolveInfo.getResolvedComponentName() + ", intent component: " + displayResolveInfo.getResolvedIntent().getComponent());
        }
    }

    protected boolean shouldAddResolveInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        java.util.Iterator<com.android.internal.app.chooser.DisplayResolveInfo> it = this.mDisplayList.iterator();
        while (it.hasNext()) {
            if (this.mResolverListCommunicator.resolveInfoMatch(displayResolveInfo.getResolveInfo(), it.next().getResolveInfo())) {
                return false;
            }
        }
        return true;
    }

    public android.content.pm.ResolveInfo resolveInfoForPosition(int i, boolean z) {
        com.android.internal.app.chooser.TargetInfo targetInfoForPosition = targetInfoForPosition(i, z);
        if (targetInfoForPosition != null) {
            return targetInfoForPosition.getResolveInfo();
        }
        return null;
    }

    public com.android.internal.app.chooser.TargetInfo targetInfoForPosition(int i, boolean z) {
        if (z) {
            return getItem(i);
        }
        if (this.mDisplayList.size() > i) {
            return this.mDisplayList.get(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int i;
        if (this.mDisplayList == null || this.mDisplayList.isEmpty()) {
            i = this.mPlaceholderCount;
        } else {
            i = this.mDisplayList.size();
        }
        if (this.mFilterLastUsed && this.mLastChosenPosition >= 0) {
            return i - 1;
        }
        return i;
    }

    public int getUnfilteredCount() {
        return this.mDisplayList.size();
    }

    @Override // android.widget.Adapter
    public com.android.internal.app.chooser.TargetInfo getItem(int i) {
        if (this.mFilterLastUsed && this.mLastChosenPosition >= 0 && i >= this.mLastChosenPosition) {
            i++;
        }
        if (this.mDisplayList.size() > i) {
            return this.mDisplayList.get(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public int getDisplayResolveInfoCount() {
        return this.mDisplayList.size();
    }

    public com.android.internal.app.chooser.DisplayResolveInfo getDisplayResolveInfo(int i) {
        return this.mDisplayList.get(i);
    }

    @Override // android.widget.Adapter
    public final android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        if (view == null) {
            view = createView(viewGroup);
        }
        onBindView(view, getItem(i), i);
        return view;
    }

    public final android.view.View createView(android.view.ViewGroup viewGroup) {
        android.view.View onCreateView = onCreateView(viewGroup);
        onCreateView.setTag(new com.android.internal.app.ResolverListAdapter.ViewHolder(onCreateView));
        return onCreateView;
    }

    android.view.View onCreateView(android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(com.android.internal.R.layout.resolve_list_item, viewGroup, false);
    }

    public final void bindView(int i, android.view.View view) {
        onBindView(view, getItem(i), i);
    }

    protected void onBindView(android.view.View view, com.android.internal.app.chooser.TargetInfo targetInfo, int i) {
        com.android.internal.app.ResolverListAdapter.ViewHolder viewHolder = (com.android.internal.app.ResolverListAdapter.ViewHolder) view.getTag();
        if (targetInfo == null) {
            viewHolder.icon.setImageDrawable(this.mContext.getDrawable(com.android.internal.R.drawable.resolver_icon_placeholder));
            viewHolder.bindLabel("", "", false);
            return;
        }
        if (targetInfo instanceof com.android.internal.app.chooser.DisplayResolveInfo) {
            com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = (com.android.internal.app.chooser.DisplayResolveInfo) targetInfo;
            if (displayResolveInfo.hasDisplayLabel()) {
                viewHolder.bindLabel(displayResolveInfo.getDisplayLabel(), displayResolveInfo.getExtendedInfo(), alwaysShowSubLabel());
            } else {
                viewHolder.bindLabel("", "", false);
                loadLabel(displayResolveInfo);
            }
            viewHolder.bindIcon(targetInfo);
            if (!displayResolveInfo.hasDisplayIcon()) {
                loadIcon(displayResolveInfo);
            }
        }
    }

    protected final void loadIcon(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        if (this.mIconLoaders.get(displayResolveInfo) == null) {
            com.android.internal.app.ResolverListAdapter.LoadIconTask loadIconTask = new com.android.internal.app.ResolverListAdapter.LoadIconTask(displayResolveInfo);
            this.mIconLoaders.put(displayResolveInfo, loadIconTask);
            loadIconTask.execute(new java.lang.Void[0]);
        }
    }

    private void loadLabel(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        if (this.mLabelLoaders.get(displayResolveInfo) == null) {
            com.android.internal.app.ResolverListAdapter.LoadLabelTask createLoadLabelTask = createLoadLabelTask(displayResolveInfo);
            this.mLabelLoaders.put(displayResolveInfo, createLoadLabelTask);
            createLoadLabelTask.execute(new java.lang.Void[0]);
        }
    }

    protected com.android.internal.app.ResolverListAdapter.LoadLabelTask createLoadLabelTask(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return new com.android.internal.app.ResolverListAdapter.LoadLabelTask(displayResolveInfo);
    }

    public void onDestroy() {
        if (this.mPostListReadyRunnable != null) {
            this.mContext.getMainThreadHandler().removeCallbacks(this.mPostListReadyRunnable);
            this.mPostListReadyRunnable = null;
        }
        if (this.mResolverListController != null) {
            this.mResolverListController.destroy();
        }
        cancelTasks(this.mIconLoaders.values());
        cancelTasks(this.mLabelLoaders.values());
        this.mIconLoaders.clear();
        this.mLabelLoaders.clear();
    }

    private <T extends android.os.AsyncTask> void cancelTasks(java.util.Collection<T> collection) {
        java.util.Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            it.next().cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.ColorMatrixColorFilter getSuspendedColorMatrix() {
        if (sSuspendedMatrixColorFilter == null) {
            android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();
            float[] array = colorMatrix.getArray();
            array[0] = 0.5f;
            array[6] = 0.5f;
            array[12] = 0.5f;
            float f = 127;
            array[4] = f;
            array[9] = f;
            array[14] = f;
            android.graphics.ColorMatrix colorMatrix2 = new android.graphics.ColorMatrix();
            colorMatrix2.setSaturation(0.0f);
            colorMatrix2.preConcat(colorMatrix);
            sSuspendedMatrixColorFilter = new android.graphics.ColorMatrixColorFilter(colorMatrix2);
        }
        return sSuspendedMatrixColorFilter;
    }

    com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter makePresentationGetter(android.content.pm.ActivityInfo activityInfo) {
        return new com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter(this.mContext, this.mIconDpi, activityInfo);
    }

    com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter makePresentationGetter(android.content.pm.ResolveInfo resolveInfo) {
        return new com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter(this.mContext, this.mIconDpi, resolveInfo);
    }

    android.graphics.drawable.Drawable loadIconForResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        return makePresentationGetter(resolveInfo).getIcon(com.android.internal.app.ResolverActivity.getResolveInfoUserHandle(resolveInfo, getUserHandle()));
    }

    void loadFilteredItemIconTaskAsync(final android.widget.ImageView imageView) {
        final com.android.internal.app.chooser.DisplayResolveInfo filteredItem = getFilteredItem();
        if (imageView != null && filteredItem != null) {
            new android.os.AsyncTask<java.lang.Void, java.lang.Void, android.graphics.drawable.Drawable>() { // from class: com.android.internal.app.ResolverListAdapter.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public android.graphics.drawable.Drawable doInBackground(java.lang.Void... voidArr) {
                    return com.android.internal.app.ResolverListAdapter.this.loadIconForResolveInfo(filteredItem.getResolveInfo());
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(android.graphics.drawable.Drawable drawable) {
                    imageView.setImageDrawable(drawable);
                }
            }.execute(new java.lang.Void[0]);
        }
    }

    public android.os.UserHandle getUserHandle() {
        return this.mResolverListController.getUserHandle();
    }

    protected java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> getResolversForUser(android.os.UserHandle userHandle) {
        return this.mResolverListController.getResolversForIntentAsUser(true, this.mResolverListCommunicator.shouldGetActivityMetadata(), this.mResolverListCommunicator.shouldGetOnlyDefaultActivities(), this.mIntents, userHandle);
    }

    protected java.util.List<android.content.Intent> getIntents() {
        return this.mIntents;
    }

    protected boolean isTabLoaded() {
        return this.mIsTabLoaded;
    }

    protected void markTabLoaded() {
        this.mIsTabLoaded = true;
    }

    protected boolean alwaysShowSubLabel() {
        return false;
    }

    private static com.android.internal.app.ResolverActivity.ResolvedComponentInfo getFirstNonCurrentUserResolvedComponentInfo(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        if (list == null) {
            return null;
        }
        for (com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            if (resolvedComponentInfo.getResolveInfoAt(0).targetUserId != -2) {
                return resolvedComponentInfo;
            }
        }
        return null;
    }

    private static com.android.internal.app.chooser.DisplayResolveInfo makeOtherProfileDisplayResolveInfo(android.content.Context context, com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, android.content.pm.PackageManager packageManager, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator resolverListCommunicator, int i) {
        android.content.pm.ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        android.content.Intent replacementIntent = resolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, resolvedComponentInfo.getIntentAt(0));
        android.content.Intent replacementIntent2 = resolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, resolverListCommunicator.getTargetIntent());
        return new com.android.internal.app.chooser.DisplayResolveInfo(resolvedComponentInfo.getIntentAt(0), resolveInfoAt, resolveInfoAt.loadLabel(packageManager), resolveInfoAt.loadLabel(packageManager), replacementIntent != null ? replacementIntent : replacementIntent2, new com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter(context, i, resolveInfoAt));
    }

    interface ResolverListCommunicator {
        android.content.Intent getReplacementIntent(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent);

        android.content.Intent getTargetIntent();

        void onHandlePackagesChanged(com.android.internal.app.ResolverListAdapter resolverListAdapter);

        void onPostListReady(com.android.internal.app.ResolverListAdapter resolverListAdapter, boolean z, boolean z2);

        boolean resolveInfoMatch(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2);

        void sendVoiceChoicesIfNeeded();

        boolean shouldGetActivityMetadata();

        void updateProfileViewButton();

        boolean useLayoutWithDefault();

        default boolean shouldGetOnlyDefaultActivities() {
            return true;
        }
    }

    public static class ViewHolder {
        public android.graphics.drawable.Drawable defaultItemViewBackground;
        public android.widget.ImageView icon;
        public android.view.View itemView;
        public android.widget.TextView text;
        public android.widget.TextView text2;

        public ViewHolder(android.view.View view) {
            this.itemView = view;
            this.defaultItemViewBackground = view.getBackground();
            this.text = (android.widget.TextView) view.findViewById(16908308);
            this.text2 = (android.widget.TextView) view.findViewById(16908309);
            this.icon = (android.widget.ImageView) view.findViewById(16908294);
        }

        public void bindLabel(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z) {
            this.text.lambda$setTextAsync$0(charSequence);
            if (android.text.TextUtils.equals(charSequence, charSequence2)) {
                charSequence2 = null;
            }
            this.text2.lambda$setTextAsync$0(charSequence2);
            if (z || charSequence2 != null) {
                this.text2.setVisibility(0);
            } else {
                this.text2.setVisibility(8);
            }
            this.itemView.setContentDescription(null);
        }

        public void updateContentDescription(java.lang.String str) {
            this.itemView.setContentDescription(str);
        }

        public void bindIcon(com.android.internal.app.chooser.TargetInfo targetInfo) {
            this.icon.setImageDrawable(targetInfo.getDisplayIcon(this.itemView.getContext()));
            if (targetInfo.isSuspended()) {
                this.icon.setColorFilter(com.android.internal.app.ResolverListAdapter.getSuspendedColorMatrix());
            } else {
                this.icon.setColorFilter((android.graphics.ColorFilter) null);
            }
        }
    }

    protected class LoadLabelTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.CharSequence[]> {
        private final com.android.internal.app.chooser.DisplayResolveInfo mDisplayResolveInfo;

        protected LoadLabelTask(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
            this.mDisplayResolveInfo = displayResolveInfo;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.CharSequence[] doInBackground(java.lang.Void... voidArr) {
            com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter makePresentationGetter = com.android.internal.app.ResolverListAdapter.this.makePresentationGetter(this.mDisplayResolveInfo.getResolveInfo());
            if (com.android.internal.app.ResolverListAdapter.this.mIsAudioCaptureDevice) {
                android.content.pm.ActivityInfo activityInfo = this.mDisplayResolveInfo.getResolveInfo().activityInfo;
                if (!(android.content.PermissionChecker.checkPermissionForPreflight(com.android.internal.app.ResolverListAdapter.this.mContext, android.Manifest.permission.RECORD_AUDIO, -1, activityInfo.applicationInfo.uid, activityInfo.packageName) == 0)) {
                    return new java.lang.CharSequence[]{makePresentationGetter.getLabel(), com.android.internal.app.ResolverListAdapter.this.mContext.getString(com.android.internal.R.string.usb_device_resolve_prompt_warn)};
                }
            }
            return new java.lang.CharSequence[]{makePresentationGetter.getLabel(), makePresentationGetter.getSubLabel()};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(java.lang.CharSequence[] charSequenceArr) {
            if (this.mDisplayResolveInfo.hasDisplayLabel()) {
                return;
            }
            this.mDisplayResolveInfo.setDisplayLabel(charSequenceArr[0]);
            this.mDisplayResolveInfo.setExtendedInfo(charSequenceArr[1]);
            com.android.internal.app.ResolverListAdapter.this.notifyDataSetChanged();
        }
    }

    class LoadIconTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, android.graphics.drawable.Drawable> {
        protected final com.android.internal.app.chooser.DisplayResolveInfo mDisplayResolveInfo;
        private final android.content.pm.ResolveInfo mResolveInfo;

        LoadIconTask(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
            this.mDisplayResolveInfo = displayResolveInfo;
            this.mResolveInfo = displayResolveInfo.getResolveInfo();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public android.graphics.drawable.Drawable doInBackground(java.lang.Void... voidArr) {
            return com.android.internal.app.ResolverListAdapter.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.AsyncTask
        public void onPostExecute(android.graphics.drawable.Drawable drawable) {
            if (com.android.internal.app.ResolverListAdapter.this.getOtherProfile() == this.mDisplayResolveInfo) {
                com.android.internal.app.ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
            } else if (!this.mDisplayResolveInfo.hasDisplayIcon()) {
                this.mDisplayResolveInfo.setDisplayIcon(drawable);
                com.android.internal.app.ResolverListAdapter.this.notifyDataSetChanged();
            }
        }
    }

    public static class ResolveInfoPresentationGetter extends com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter {
        private final android.content.pm.ResolveInfo mRi;

        public ResolveInfoPresentationGetter(android.content.Context context, int i, android.content.pm.ResolveInfo resolveInfo) {
            super(context, i, resolveInfo.activityInfo);
            this.mRi = resolveInfo;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        android.graphics.drawable.Drawable getIconSubstituteInternal() {
            android.graphics.drawable.Drawable drawable = null;
            try {
                if (this.mRi.resolvePackageName != null && this.mRi.icon != 0) {
                    drawable = loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(com.android.internal.app.ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
            }
            return drawable == null ? super.getIconSubstituteInternal() : drawable;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        java.lang.String getAppSubLabelInternal() {
            return this.mRi.loadLabel(this.mPm).toString();
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        java.lang.String getAppLabelForSubstitutePermission() {
            return this.mRi.getComponentInfo().loadLabel(this.mPm).toString();
        }
    }

    public static class ActivityInfoPresentationGetter extends com.android.internal.app.ResolverListAdapter.TargetPresentationGetter {
        private final android.content.pm.ActivityInfo mActivityInfo;

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ android.graphics.drawable.Drawable getIcon(android.os.UserHandle userHandle) {
            return super.getIcon(userHandle);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ android.graphics.Bitmap getIconBitmap(android.os.UserHandle userHandle) {
            return super.getIconBitmap(userHandle);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ java.lang.String getLabel() {
            return super.getLabel();
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ java.lang.String getSubLabel() {
            return super.getSubLabel();
        }

        public ActivityInfoPresentationGetter(android.content.Context context, int i, android.content.pm.ActivityInfo activityInfo) {
            super(context, i, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        android.graphics.drawable.Drawable getIconSubstituteInternal() {
            try {
                if (this.mActivityInfo.icon == 0) {
                    return null;
                }
                return loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(com.android.internal.app.ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
                return null;
            }
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        java.lang.String getAppSubLabelInternal() {
            return (java.lang.String) this.mActivityInfo.loadLabel(this.mPm);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        java.lang.String getAppLabelForSubstitutePermission() {
            return getAppSubLabelInternal();
        }
    }

    private static abstract class TargetPresentationGetter {
        private final android.content.pm.ApplicationInfo mAi;
        private android.content.Context mCtx;
        private final boolean mHasSubstitutePermission;
        private final int mIconDpi;
        protected android.content.pm.PackageManager mPm;

        abstract java.lang.String getAppLabelForSubstitutePermission();

        abstract java.lang.String getAppSubLabelInternal();

        abstract android.graphics.drawable.Drawable getIconSubstituteInternal();

        TargetPresentationGetter(android.content.Context context, int i, android.content.pm.ApplicationInfo applicationInfo) {
            this.mCtx = context;
            this.mPm = context.getPackageManager();
            this.mAi = applicationInfo;
            this.mIconDpi = i;
            this.mHasSubstitutePermission = this.mPm.checkPermission(android.Manifest.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON, this.mAi.packageName) == 0;
        }

        public android.graphics.drawable.Drawable getIcon(android.os.UserHandle userHandle) {
            return new android.graphics.drawable.BitmapDrawable(this.mCtx.getResources(), getIconBitmap(userHandle));
        }

        public android.graphics.Bitmap getIconBitmap(android.os.UserHandle userHandle) {
            android.graphics.drawable.Drawable drawable;
            if (!this.mHasSubstitutePermission) {
                drawable = null;
            } else {
                drawable = getIconSubstituteInternal();
            }
            if (drawable == null) {
                try {
                    if (this.mAi.icon != 0) {
                        drawable = loadIconFromResource(this.mPm.getResourcesForApplication(this.mAi), this.mAi.icon);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
            if (drawable == null) {
                drawable = this.mAi.loadIcon(this.mPm);
            }
            com.android.internal.app.SimpleIconFactory obtain = com.android.internal.app.SimpleIconFactory.obtain(this.mCtx);
            android.graphics.Bitmap createUserBadgedIconBitmap = obtain.createUserBadgedIconBitmap(drawable, userHandle);
            obtain.recycle();
            return createUserBadgedIconBitmap;
        }

        public java.lang.String getLabel() {
            java.lang.String str;
            if (!this.mHasSubstitutePermission) {
                str = null;
            } else {
                str = getAppLabelForSubstitutePermission();
            }
            if (str == null) {
                return (java.lang.String) this.mAi.loadLabel(this.mPm);
            }
            return str;
        }

        public java.lang.String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                java.lang.String appSubLabelInternal = getAppSubLabelInternal();
                if (!android.text.TextUtils.isEmpty(appSubLabelInternal) && !android.text.TextUtils.equals(appSubLabelInternal, getLabel())) {
                    return appSubLabelInternal;
                }
                return null;
            }
            return getAppSubLabelInternal();
        }

        protected java.lang.String loadLabelFromResource(android.content.res.Resources resources, int i) {
            return resources.getString(i);
        }

        protected android.graphics.drawable.Drawable loadIconFromResource(android.content.res.Resources resources, int i) {
            return resources.getDrawableForDensity(i, this.mIconDpi);
        }
    }
}
