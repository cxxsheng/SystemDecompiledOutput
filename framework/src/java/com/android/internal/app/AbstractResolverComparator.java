package com.android.internal.app;

/* loaded from: classes4.dex */
public abstract class AbstractResolverComparator implements java.util.Comparator<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> {
    private static final boolean DEBUG = true;
    private static final int NUM_OF_TOP_ANNOTATIONS_TO_USE = 3;
    static final int RANKER_RESULT_TIMEOUT = 1;
    static final int RANKER_SERVICE_RESULT = 0;
    private static final java.lang.String TAG = "AbstractResolverComp";
    private static final int WATCHDOG_TIMEOUT_MILLIS = 500;
    protected com.android.internal.app.AbstractResolverComparator.AfterCompute mAfterCompute;
    protected java.lang.String[] mAnnotations;
    private final java.util.Comparator<android.content.pm.ResolveInfo> mAzComparator;
    private com.android.internal.app.ChooserActivityLogger mChooserActivityLogger;
    protected java.lang.String mContentType;
    protected final android.os.Handler mHandler;
    private final boolean mHttp;
    protected final java.util.Map<android.os.UserHandle, android.content.pm.PackageManager> mPmMap;
    protected final java.util.Map<android.os.UserHandle, android.app.usage.UsageStatsManager> mUsmMap;

    interface AfterCompute {
        void afterCompute();
    }

    abstract int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2);

    abstract void doCompute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list);

    abstract float getScore(com.android.internal.app.chooser.TargetInfo targetInfo);

    abstract void handleResultMessage(android.os.Message message);

    public AbstractResolverComparator(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        this(context, intent, com.google.android.collect.Lists.newArrayList(userHandle));
    }

    public AbstractResolverComparator(android.content.Context context, android.content.Intent intent, java.util.List<android.os.UserHandle> list) {
        this.mPmMap = new java.util.HashMap();
        this.mUsmMap = new java.util.HashMap();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: com.android.internal.app.AbstractResolverComparator.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 0:
                        android.util.Log.d(com.android.internal.app.AbstractResolverComparator.TAG, "RANKER_SERVICE_RESULT");
                        if (com.android.internal.app.AbstractResolverComparator.this.mHandler.hasMessages(1)) {
                            com.android.internal.app.AbstractResolverComparator.this.handleResultMessage(message);
                            com.android.internal.app.AbstractResolverComparator.this.mHandler.removeMessages(1);
                            com.android.internal.app.AbstractResolverComparator.this.afterCompute();
                            break;
                        }
                        break;
                    case 1:
                        android.util.Log.d(com.android.internal.app.AbstractResolverComparator.TAG, "RANKER_RESULT_TIMEOUT; unbinding services");
                        com.android.internal.app.AbstractResolverComparator.this.mHandler.removeMessages(0);
                        com.android.internal.app.AbstractResolverComparator.this.afterCompute();
                        if (com.android.internal.app.AbstractResolverComparator.this.mChooserActivityLogger != null) {
                            com.android.internal.app.AbstractResolverComparator.this.mChooserActivityLogger.logSharesheetAppShareRankingTimeout();
                            break;
                        }
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        };
        java.lang.String scheme = intent.getScheme();
        this.mHttp = android.content.IntentFilter.SCHEME_HTTP.equals(scheme) || android.content.IntentFilter.SCHEME_HTTPS.equals(scheme);
        this.mContentType = intent.getType();
        getContentAnnotations(intent);
        for (android.os.UserHandle userHandle : list) {
            android.content.Context createContextAsUser = context.createContextAsUser(userHandle, 0);
            this.mPmMap.put(userHandle, createContextAsUser.getPackageManager());
            this.mUsmMap.put(userHandle, (android.app.usage.UsageStatsManager) createContextAsUser.getSystemService(android.content.Context.USAGE_STATS_SERVICE));
        }
        this.mAzComparator = new com.android.internal.app.AbstractResolverComparator.AzInfoComparator(context);
    }

    private void getContentAnnotations(android.content.Intent intent) {
        try {
            java.util.ArrayList<java.lang.String> stringArrayListExtra = intent.getStringArrayListExtra(android.content.Intent.EXTRA_CONTENT_ANNOTATIONS);
            if (stringArrayListExtra != null) {
                int size = stringArrayListExtra.size();
                if (size > 3) {
                    size = 3;
                }
                this.mAnnotations = new java.lang.String[size];
                for (int i = 0; i < size; i++) {
                    this.mAnnotations[i] = stringArrayListExtra.get(i);
                }
            }
        } catch (android.os.BadParcelableException e) {
            android.util.Log.i(TAG, "Couldn't unparcel intent annotations. Ignoring.");
            this.mAnnotations = new java.lang.String[0];
        }
    }

    void setCallBack(com.android.internal.app.AbstractResolverComparator.AfterCompute afterCompute) {
        this.mAfterCompute = afterCompute;
    }

    void setChooserActivityLogger(com.android.internal.app.ChooserActivityLogger chooserActivityLogger) {
        this.mChooserActivityLogger = chooserActivityLogger;
    }

    com.android.internal.app.ChooserActivityLogger getChooserActivityLogger() {
        return this.mChooserActivityLogger;
    }

    protected final void afterCompute() {
        com.android.internal.app.AbstractResolverComparator.AfterCompute afterCompute = this.mAfterCompute;
        if (afterCompute != null) {
            afterCompute.afterCompute();
        }
    }

    @Override // java.util.Comparator
    public final int compare(com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, com.android.internal.app.ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2) {
        boolean isSpecificUriMatch;
        android.content.pm.ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        android.content.pm.ResolveInfo resolveInfoAt2 = resolvedComponentInfo2.getResolveInfoAt(0);
        boolean isFixedAtTop = resolvedComponentInfo.isFixedAtTop();
        boolean isFixedAtTop2 = resolvedComponentInfo2.isFixedAtTop();
        if (isFixedAtTop && !isFixedAtTop2) {
            return -1;
        }
        if (!isFixedAtTop && isFixedAtTop2) {
            return 1;
        }
        if (resolveInfoAt.targetUserId != -2) {
            if (resolveInfoAt2.targetUserId != -2) {
                return 0;
            }
            return 1;
        }
        if (resolveInfoAt2.targetUserId != -2) {
            return -1;
        }
        if (this.mHttp && (isSpecificUriMatch = com.android.internal.app.ResolverActivity.isSpecificUriMatch(resolveInfoAt.match)) != com.android.internal.app.ResolverActivity.isSpecificUriMatch(resolveInfoAt2.match)) {
            if (isSpecificUriMatch) {
                return -1;
            }
            return 1;
        }
        boolean isPinned = resolvedComponentInfo.isPinned();
        boolean isPinned2 = resolvedComponentInfo2.isPinned();
        if (isPinned && !isPinned2) {
            return -1;
        }
        if (!isPinned && isPinned2) {
            return 1;
        }
        if (isPinned && isPinned2) {
            return this.mAzComparator.compare(resolvedComponentInfo.getResolveInfoAt(0), resolvedComponentInfo2.getResolveInfoAt(0));
        }
        return compare(resolveInfoAt, resolveInfoAt2);
    }

    final void compute(java.util.List<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> list) {
        beforeCompute();
        doCompute(list);
    }

    final void updateChooserCounts(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2) {
        if (this.mUsmMap.containsKey(userHandle)) {
            this.mUsmMap.get(userHandle).reportChooserSelection(str, userHandle.getIdentifier(), this.mContentType, this.mAnnotations, str2);
        }
    }

    void updateModel(com.android.internal.app.chooser.TargetInfo targetInfo) {
    }

    void beforeCompute() {
        android.util.Log.d(TAG, "Setting watchdog timer for 500ms");
        if (this.mHandler == null) {
            android.util.Log.d(TAG, "Error: Handler is Null; Needs to be initialized.");
        } else {
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
        }
    }

    void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        afterCompute();
        this.mAfterCompute = null;
    }

    class AzInfoComparator implements java.util.Comparator<android.content.pm.ResolveInfo> {
        java.text.Collator mCollator;

        AzInfoComparator(android.content.Context context) {
            this.mCollator = java.text.Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        @Override // java.util.Comparator
        public int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
            if (resolveInfo == null) {
                return -1;
            }
            if (resolveInfo2 == null) {
                return 1;
            }
            return this.mCollator.compare(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName);
        }
    }
}
