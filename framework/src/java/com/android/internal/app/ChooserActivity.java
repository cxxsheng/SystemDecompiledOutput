package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserActivity extends com.android.internal.app.ResolverActivity implements com.android.internal.app.ChooserListAdapter.ChooserListCommunicator, com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator {
    public static final java.lang.String APP_PREDICTION_INTENT_FILTER_KEY = "intent_filter";
    private static final int APP_PREDICTION_SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final java.lang.String APP_PREDICTION_SHARE_UI_SURFACE = "share";
    private static final java.lang.String CHIP_ICON_METADATA_KEY = "android.service.chooser.chip_icon";
    private static final java.lang.String CHIP_LABEL_METADATA_KEY = "android.service.chooser.chip_label";
    public static final java.lang.String CHOOSER_TARGET = "chooser_target";
    protected static final int CONTENT_PREVIEW_FILE = 2;
    protected static final int CONTENT_PREVIEW_IMAGE = 1;
    protected static final int CONTENT_PREVIEW_TEXT = 3;
    private static final boolean DEBUG = true;
    private static final boolean DEFAULT_IS_NEARBY_SHARE_FIRST_TARGET_IN_RANKED_APP = false;
    private static final int DEFAULT_LIST_VIEW_UPDATE_DELAY_MS = 0;
    private static final int DEFAULT_SALT_EXPIRATION_DAYS = 7;
    private static final float DIRECT_SHARE_EXPANSION_RATE = 0.78f;
    public static final java.lang.String EXTRA_PRIVATE_RETAIN_IN_ON_STOP = "com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP";
    public static final java.lang.String FIRST_IMAGE_PREVIEW_TRANSITION_NAME = "screenshot_preview_image";
    private static final java.lang.String IMAGE_EDITOR_SHARED_ELEMENT = "screenshot_preview_image";
    public static final java.lang.String LAUNCH_LOCATION_DIRECT_SHARE = "direct_share";
    private static final int MAX_EXTRA_CHOOSER_TARGETS = 2;
    private static final int MAX_EXTRA_INITIAL_INTENTS = 2;
    private static final int MAX_LOG_RANK_POSITION = 12;
    private static final int NO_DIRECT_SHARE_ANIM_IN_MILLIS = 200;
    private static final java.lang.String PINNED_SHARED_PREFS_NAME = "chooser_pin_settings";
    private static final java.lang.String PLURALS_COUNT = "count";
    private static final java.lang.String PLURALS_FILE_NAME = "file_name";
    private static final java.lang.String PREF_NUM_SHEET_EXPANSIONS = "pref_num_sheet_expansions";
    private static final int SCROLL_STATUS_IDLE = 0;
    private static final int SCROLL_STATUS_SCROLLING_HORIZONTAL = 2;
    private static final int SCROLL_STATUS_SCROLLING_VERTICAL = 1;
    public static final int SELECTION_TYPE_APP = 2;
    public static final int SELECTION_TYPE_COPY = 4;
    public static final int SELECTION_TYPE_EDIT = 6;
    public static final int SELECTION_TYPE_NEARBY = 5;
    public static final int SELECTION_TYPE_SERVICE = 1;
    public static final int SELECTION_TYPE_STANDARD = 3;
    private static final java.lang.String SHARED_TEXT_KEY = "shared_text";
    private static final java.lang.String SHORTCUT_TARGET = "shortcut_target";
    private static final java.lang.String TAG = "ChooserActivity";
    private static final java.lang.String TARGET_DETAILS_FRAGMENT_TAG = "targetDetailsFragment";
    public static final int TARGET_TYPE_CHOOSER_TARGET = 1;
    public static final int TARGET_TYPE_DEFAULT = 0;
    public static final int TARGET_TYPE_SHORTCUTS_FROM_PREDICTION_SERVICE = 3;
    public static final int TARGET_TYPE_SHORTCUTS_FROM_SHORTCUT_MANAGER = 2;
    private static final int URI_PERMISSION_INTENT_FLAGS = 195;
    private static final boolean USE_PREDICTION_MANAGER_FOR_SHARE_ACTIVITIES = true;
    private android.service.chooser.ChooserTarget[] mCallerChooserTargets;
    protected com.android.internal.app.ChooserActivityLogger mChooserActivityLogger;
    protected com.android.internal.app.ChooserMultiProfilePagerAdapter mChooserMultiProfilePagerAdapter;
    private long mChooserShownTime;
    private android.content.IntentSender mChosenComponentSender;
    private java.util.Map<android.service.chooser.ChooserTarget, android.app.prediction.AppTarget> mDirectShareAppTargetCache;
    private java.util.Map<android.service.chooser.ChooserTarget, android.content.pm.ShortcutInfo> mDirectShareShortcutInfoCache;
    private android.content.ComponentName[] mFilteredComponentNames;
    private boolean mIsAppPredictorComponentAvailable;
    protected boolean mIsSuccessfullySelected;
    protected com.android.internal.logging.MetricsLogger mMetricsLogger;
    private android.app.prediction.AppPredictor mPersonalAppPredictor;
    private android.content.SharedPreferences mPinnedSharedPrefs;
    private com.android.internal.app.ChooserActivity.ContentPreviewCoordinator mPreviewCoord;
    private long mQueriedSharingShortcutsTimeMs;
    private android.content.Intent mReferrerFillInIntent;
    private android.content.IntentSender mRefinementIntentSender;
    private com.android.internal.app.ChooserActivity.RefinementResultReceiver mRefinementResultReceiver;
    private android.os.Bundle mReplacementExtras;
    private boolean mShouldDisplayLandscape;
    private android.app.prediction.AppPredictor mWorkAppPredictor;
    private int mMaxHashSaltDays = android.provider.DeviceConfig.getInt("systemui", com.android.internal.config.sysui.SystemUiDeviceConfigFlags.HASH_SALT_MAX_DAYS, 7);
    private boolean mIsNearbyShareFirstTargetInRankedApp = android.provider.DeviceConfig.getBoolean("systemui", com.android.internal.config.sysui.SystemUiDeviceConfigFlags.IS_NEARBY_SHARE_FIRST_TARGET_IN_RANKED_APP, false);
    int mListViewUpdateDelayMs = android.provider.DeviceConfig.getInt("systemui", com.android.internal.config.sysui.SystemUiDeviceConfigFlags.SHARESHEET_LIST_VIEW_UPDATE_DELAY, 0);
    private int mCurrAvailableWidth = 0;
    private android.graphics.Insets mLastAppliedInsets = null;
    private int mLastNumberOfChildren = -1;
    private int mMaxTargetsPerRow = 1;
    private int mScrollStatus = 0;
    private final com.android.internal.app.ChooserActivity.EnterTransitionAnimationDelegate mEnterTransitionAnimationDelegate = new com.android.internal.app.ChooserActivity.EnterTransitionAnimationDelegate();
    private boolean mRemoveSharedElements = false;
    private android.view.View mContentView = null;
    private final com.android.internal.app.ChooserActivity.ChooserHandler mChooserHandler = new com.android.internal.app.ChooserActivity.ChooserHandler();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ContentPreviewType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShareTargetType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ContentPreviewCoordinator {
        private static final int IMAGE_FADE_IN_MILLIS = 150;
        private static final int IMAGE_LOAD_INTO_VIEW = 2;
        private static final int IMAGE_LOAD_TIMEOUT = 1;
        private boolean mAtLeastOneLoaded = false;
        private final android.os.Handler mHandler = new android.os.Handler() { // from class: com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.maybeHideContentPreview();
                        break;
                    case 2:
                        if (!com.android.internal.app.ChooserActivity.this.isFinishing()) {
                            com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.LoadUriTask loadUriTask = (com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.LoadUriTask) message.obj;
                            com.android.internal.app.ChooserActivity.RoundedRectImageView roundedRectImageView = (com.android.internal.app.ChooserActivity.RoundedRectImageView) com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.mParentView.findViewById(loadUriTask.mImageResourceId);
                            if (loadUriTask.mBmp == null) {
                                roundedRectImageView.setVisibility(8);
                                com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.maybeHideContentPreview();
                                break;
                            } else {
                                com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.mAtLeastOneLoaded = true;
                                roundedRectImageView.setVisibility(0);
                                roundedRectImageView.setAlpha(0.0f);
                                roundedRectImageView.setImageBitmap(loadUriTask.mBmp);
                                android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(roundedRectImageView, "alpha", 0.0f, 1.0f);
                                ofFloat.setInterpolator(new android.view.animation.DecelerateInterpolator(1.0f));
                                ofFloat.setDuration(150L);
                                ofFloat.start();
                                if (loadUriTask.mExtraCount > 0) {
                                    roundedRectImageView.setExtraImageCount(loadUriTask.mExtraCount);
                                }
                                com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.setupPreDrawForSharedElementTransition(roundedRectImageView);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        private boolean mHideParentOnFail;
        private final int mImageLoadTimeoutMillis;
        private final android.view.View mParentView;

        class LoadUriTask {
            public final android.graphics.Bitmap mBmp;
            public final int mExtraCount;
            public final int mImageResourceId;
            public final android.net.Uri mUri;

            LoadUriTask(int i, android.net.Uri uri, int i2, android.graphics.Bitmap bitmap) {
                this.mImageResourceId = i;
                this.mUri = uri;
                this.mExtraCount = i2;
                this.mBmp = bitmap;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setupPreDrawForSharedElementTransition(final android.view.View view) {
            view.getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (!com.android.internal.app.ChooserActivity.this.mRemoveSharedElements && com.android.internal.app.ChooserActivity.this.isActivityTransitionRunning()) {
                        com.android.internal.app.ChooserActivity.this.getWindow().setWindowAnimations(0);
                    }
                    com.android.internal.app.ChooserActivity.this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
                    return true;
                }
            });
        }

        ContentPreviewCoordinator(android.view.View view, boolean z) {
            this.mImageLoadTimeoutMillis = com.android.internal.app.ChooserActivity.this.getResources().getInteger(17694720);
            this.mParentView = view;
            this.mHideParentOnFail = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void loadUriIntoView(final int i, final android.net.Uri uri, final int i2) {
            this.mHandler.sendEmptyMessageDelayed(1, this.mImageLoadTimeoutMillis);
            android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.internal.app.ChooserActivity$ContentPreviewCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.this.lambda$loadUriIntoView$0(uri, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadUriIntoView$0(android.net.Uri uri, int i, int i2) {
            int dimensionPixelSize = com.android.internal.app.ChooserActivity.this.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_preview_image_max_dimen);
            android.graphics.Bitmap loadThumbnail = com.android.internal.app.ChooserActivity.this.loadThumbnail(uri, new android.util.Size(dimensionPixelSize, dimensionPixelSize));
            android.os.Message obtain = android.os.Message.obtain();
            obtain.what = 2;
            obtain.obj = new com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.LoadUriTask(i, uri, i2, loadThumbnail);
            this.mHandler.sendMessage(obtain);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancelLoads() {
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void maybeHideContentPreview() {
            if (!this.mAtLeastOneLoaded) {
                if (this.mHideParentOnFail) {
                    android.util.Log.i(com.android.internal.app.ChooserActivity.TAG, "Hiding image preview area. Timed out waiting for preview to load within " + this.mImageLoadTimeoutMillis + "ms.");
                    collapseParentView();
                    if (com.android.internal.app.ChooserActivity.this.shouldShowTabs()) {
                        com.android.internal.app.ChooserActivity.this.hideStickyContentPreview();
                    } else if (com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter() != null) {
                        com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().hideContentPreview();
                    }
                    this.mHideParentOnFail = false;
                }
                com.android.internal.app.ChooserActivity.this.mRemoveSharedElements = true;
                com.android.internal.app.ChooserActivity.this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
            }
        }

        private void collapseParentView() {
            android.view.View view = this.mParentView;
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(0, 1073741824));
            view.getLayoutParams().height = 0;
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop());
            view.invalidate();
        }
    }

    private class ChooserHandler extends android.os.Handler {
        private static final int LIST_VIEW_UPDATE_MESSAGE = 6;
        private static final int SHORTCUT_MANAGER_ALL_SHARE_TARGET_RESULTS = 7;

        private ChooserHandler() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAllMessages() {
            removeMessages(6);
            removeMessages(7);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.app.ChooserListAdapter listAdapterForUserHandle;
            if (com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveListAdapter() == null || com.android.internal.app.ChooserActivity.this.isDestroyed()) {
                return;
            }
            switch (message.what) {
                case 6:
                    android.util.Log.d(com.android.internal.app.ChooserActivity.TAG, "LIST_VIEW_UPDATE_MESSAGE; ");
                    com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getListAdapterForUserHandle((android.os.UserHandle) message.obj).refreshListView();
                    break;
                case 7:
                    android.util.Log.d(com.android.internal.app.ChooserActivity.TAG, "SHORTCUT_MANAGER_ALL_SHARE_TARGET_RESULTS");
                    for (com.android.internal.app.ChooserActivity.ServiceResultInfo serviceResultInfo : (com.android.internal.app.ChooserActivity.ServiceResultInfo[]) message.obj) {
                        if (serviceResultInfo.resultTargets != null && (listAdapterForUserHandle = com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getListAdapterForUserHandle(serviceResultInfo.userHandle)) != null) {
                            listAdapterForUserHandle.addServiceResults(serviceResultInfo.originalTarget, serviceResultInfo.resultTargets, message.arg1, com.android.internal.app.ChooserActivity.this.mDirectShareShortcutInfoCache);
                        }
                    }
                    com.android.internal.app.ChooserActivity.this.logDirectShareTargetReceived(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_DIRECT_SHARE_TARGETS_LOADED_SHORTCUT_MANAGER);
                    com.android.internal.app.ChooserActivity.this.sendVoiceChoicesIfNeeded();
                    com.android.internal.app.ChooserActivity.this.getChooserActivityLogger().logSharesheetDirectLoadComplete();
                    com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().completeServiceTargetLoading();
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02f5  */
    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onCreate(android.os.Bundle bundle) {
        android.content.Intent intent;
        java.lang.CharSequence charSequence;
        int i;
        android.os.Parcelable[] parcelableArrayExtra;
        android.content.Intent[] intentArr;
        android.os.Parcelable[] parcelableArrayExtra2;
        android.os.Parcelable[] parcelableArrayExtra3;
        int i2;
        android.service.chooser.ChooserTarget[] chooserTargetArr;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        this.mLatencyTracker.onActionStart(16);
        getChooserActivityLogger().logSharesheetTriggered();
        this.mIsAppPredictorComponentAvailable = isAppPredictionServiceAvailable();
        this.mIsSuccessfullySelected = false;
        android.content.Intent intent2 = getIntent();
        java.lang.Object parcelableExtra = intent2.getParcelableExtra(android.content.Intent.EXTRA_INTENT);
        if (parcelableExtra instanceof android.net.Uri) {
            try {
                parcelableExtra = android.content.Intent.parseUri(parcelableExtra.toString(), 1);
            } catch (java.net.URISyntaxException e) {
            }
        }
        if (!(parcelableExtra instanceof android.content.Intent)) {
            android.util.Log.w(TAG, "Target is not an intent: " + parcelableExtra);
            finish();
            super.onCreate(null);
            return;
        }
        android.content.Intent intent3 = (android.content.Intent) parcelableExtra;
        if (intent3 != null) {
            modifyTargetIntent(intent3);
        }
        android.os.Parcelable[] parcelableArrayExtra4 = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_ALTERNATE_INTENTS);
        if (parcelableArrayExtra4 == null) {
            intent = intent3;
        } else {
            boolean z = intent3 == null;
            int length = parcelableArrayExtra4.length;
            if (z) {
                length--;
            }
            android.content.Intent[] intentArr2 = new android.content.Intent[length];
            for (int i3 = 0; i3 < parcelableArrayExtra4.length; i3++) {
                if (!(parcelableArrayExtra4[i3] instanceof android.content.Intent)) {
                    android.util.Log.w(TAG, "EXTRA_ALTERNATE_INTENTS array entry #" + i3 + " is not an Intent: " + parcelableArrayExtra4[i3]);
                    finish();
                    super.onCreate(null);
                    return;
                }
                android.content.Intent intent4 = (android.content.Intent) parcelableArrayExtra4[i3];
                if (i3 == 0 && intent3 == null) {
                    modifyTargetIntent(intent4);
                    intent3 = intent4;
                } else {
                    intentArr2[z ? i3 - 1 : i3] = intent4;
                    modifyTargetIntent(intent4);
                }
            }
            setAdditionalTargets(intentArr2);
            intent = intent3;
        }
        this.mReplacementExtras = intent2.getBundleExtra(android.content.Intent.EXTRA_REPLACEMENT_EXTRAS);
        if (intent != null) {
            if (!isSendAction(intent)) {
                charSequence = intent2.getCharSequenceExtra(android.content.Intent.EXTRA_TITLE);
                if (charSequence == null) {
                    i = 0;
                } else {
                    i = 17039816;
                }
                parcelableArrayExtra = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_INITIAL_INTENTS);
                if (parcelableArrayExtra == null) {
                    int min = java.lang.Math.min(parcelableArrayExtra.length, 2);
                    android.content.Intent[] intentArr3 = new android.content.Intent[min];
                    for (int i4 = 0; i4 < min; i4++) {
                        if (!(parcelableArrayExtra[i4] instanceof android.content.Intent)) {
                            android.util.Log.w(TAG, "Initial intent #" + i4 + " not an Intent: " + parcelableArrayExtra[i4]);
                            finish();
                            super.onCreate(null);
                            return;
                        } else {
                            android.content.Intent intent5 = (android.content.Intent) parcelableArrayExtra[i4];
                            modifyTargetIntent(intent5);
                            intentArr3[i4] = intent5;
                        }
                    }
                    intentArr = intentArr3;
                } else {
                    intentArr = null;
                }
                this.mReferrerFillInIntent = new android.content.Intent().putExtra(android.content.Intent.EXTRA_REFERRER, getReferrer());
                this.mChosenComponentSender = (android.content.IntentSender) intent2.getParcelableExtra(android.content.Intent.EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, android.content.IntentSender.class);
                this.mRefinementIntentSender = (android.content.IntentSender) intent2.getParcelableExtra(android.content.Intent.EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER, android.content.IntentSender.class);
                setSafeForwardingMode(true);
                this.mPinnedSharedPrefs = getPinnedSharedPrefs(this);
                parcelableArrayExtra2 = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_EXCLUDE_COMPONENTS);
                android.content.ComponentName nearbySharingComponent = getNearbySharingComponent();
                int i5 = (!shouldNearbyShareBeFirstInRankedRow() || nearbySharingComponent == null) ? 0 : 1;
                if (parcelableArrayExtra2 == null) {
                    android.content.ComponentName[] componentNameArr = new android.content.ComponentName[parcelableArrayExtra2.length + i5];
                    int i6 = 0;
                    while (true) {
                        if (i6 >= parcelableArrayExtra2.length) {
                            break;
                        }
                        if (!(parcelableArrayExtra2[i6] instanceof android.content.ComponentName)) {
                            android.util.Log.w(TAG, "Filtered component #" + i6 + " not a ComponentName: " + parcelableArrayExtra2[i6]);
                            componentNameArr = null;
                            break;
                        } else {
                            componentNameArr[i6] = (android.content.ComponentName) parcelableArrayExtra2[i6];
                            i6++;
                        }
                    }
                    if (i5 != 0) {
                        componentNameArr[componentNameArr.length - 1] = nearbySharingComponent;
                    }
                    this.mFilteredComponentNames = componentNameArr;
                } else if (i5 != 0) {
                    this.mFilteredComponentNames = new android.content.ComponentName[1];
                    this.mFilteredComponentNames[0] = nearbySharingComponent;
                }
                parcelableArrayExtra3 = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_CHOOSER_TARGETS);
                if (parcelableArrayExtra3 != null) {
                    i2 = 2;
                } else {
                    i2 = 2;
                    int min2 = java.lang.Math.min(parcelableArrayExtra3.length, 2);
                    android.service.chooser.ChooserTarget[] chooserTargetArr2 = new android.service.chooser.ChooserTarget[min2];
                    int i7 = 0;
                    while (true) {
                        if (i7 >= min2) {
                            chooserTargetArr = chooserTargetArr2;
                            break;
                        } else if (!(parcelableArrayExtra3[i7] instanceof android.service.chooser.ChooserTarget)) {
                            android.util.Log.w(TAG, "Chooser target #" + i7 + " not a ChooserTarget: " + parcelableArrayExtra3[i7]);
                            chooserTargetArr = null;
                            break;
                        } else {
                            chooserTargetArr2[i7] = (android.service.chooser.ChooserTarget) parcelableArrayExtra3[i7];
                            i7++;
                        }
                    }
                    this.mCallerChooserTargets = chooserTargetArr;
                }
                this.mMaxTargetsPerRow = getResources().getInteger(com.android.internal.R.integer.config_chooser_max_targets_per_row);
                this.mShouldDisplayLandscape = shouldDisplayLandscape(getResources().getConfiguration().orientation);
                setRetainInOnStop(intent2.getBooleanExtra(EXTRA_PRIVATE_RETAIN_IN_ON_STOP, false));
                super.onCreate(bundle, intent, charSequence, i, intentArr, null, false);
                this.mChooserShownTime = java.lang.System.currentTimeMillis();
                long j = this.mChooserShownTime - currentTimeMillis;
                getMetricsLogger().write(new android.metrics.LogMaker(214).setSubtype(isWorkProfile() ? i2 : 1).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_SHARESHEET_MIMETYPE, intent.getType()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_TIME_TO_APP_TARGETS, java.lang.Long.valueOf(j)));
                if (this.mResolverDrawerLayout != null) {
                    this.mResolverDrawerLayout.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(android.view.View view, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
                            com.android.internal.app.ChooserActivity.this.handleLayoutChange(view, i8, i9, i10, i11, i12, i13, i14, i15);
                        }
                    });
                    if (isSendAction(intent)) {
                        this.mResolverDrawerLayout.setOnScrollChangeListener(new android.view.View.OnScrollChangeListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda5
                            @Override // android.view.View.OnScrollChangeListener
                            public final void onScrollChange(android.view.View view, int i8, int i9, int i10, int i11) {
                                com.android.internal.app.ChooserActivity.this.handleScroll(view, i8, i9, i10, i11);
                            }
                        });
                    }
                    this.mResolverDrawerLayout.setOnCollapsedChangedListener(new com.android.internal.widget.ResolverDrawerLayout.OnCollapsedChangedListener() { // from class: com.android.internal.app.ChooserActivity.1
                        private boolean mWrittenOnce = false;

                        @Override // com.android.internal.widget.ResolverDrawerLayout.OnCollapsedChangedListener
                        public void onCollapsedChanged(boolean z2) {
                            if (!z2 && !this.mWrittenOnce) {
                                com.android.internal.app.ChooserActivity.this.incrementNumSheetExpansions();
                                this.mWrittenOnce = true;
                            }
                            com.android.internal.app.ChooserActivity.this.getChooserActivityLogger().logSharesheetExpansionChanged(z2);
                        }
                    });
                }
                android.util.Log.d(TAG, "System Time Cost is " + j);
                getChooserActivityLogger().logShareStarted(259, getReferrerPackageName(), intent.getType(), this.mCallerChooserTargets != null ? 0 : this.mCallerChooserTargets.length, intentArr != null ? 0 : intentArr.length, isWorkProfile(), findPreferredContentPreview(getTargetIntent(), getContentResolver()), intent.getAction());
                this.mDirectShareShortcutInfoCache = new java.util.HashMap();
                setEnterSharedElementCallback(new android.app.SharedElementCallback() { // from class: com.android.internal.app.ChooserActivity.2
                    @Override // android.app.SharedElementCallback
                    public void onMapSharedElements(java.util.List<java.lang.String> list, java.util.Map<java.lang.String, android.view.View> map) {
                        if (com.android.internal.app.ChooserActivity.this.mRemoveSharedElements) {
                            list.remove("screenshot_preview_image");
                            map.remove("screenshot_preview_image");
                        }
                        super.onMapSharedElements(list, map);
                        com.android.internal.app.ChooserActivity.this.mRemoveSharedElements = false;
                    }
                });
                this.mEnterTransitionAnimationDelegate.postponeTransition();
            }
            android.util.Log.w(TAG, "Ignoring intent's EXTRA_TITLE, deprecated in P. You may wish to set a preview title by using EXTRA_TITLE property of the wrapped EXTRA_INTENT.");
        }
        charSequence = null;
        if (charSequence == null) {
        }
        parcelableArrayExtra = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_INITIAL_INTENTS);
        if (parcelableArrayExtra == null) {
        }
        this.mReferrerFillInIntent = new android.content.Intent().putExtra(android.content.Intent.EXTRA_REFERRER, getReferrer());
        this.mChosenComponentSender = (android.content.IntentSender) intent2.getParcelableExtra(android.content.Intent.EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, android.content.IntentSender.class);
        this.mRefinementIntentSender = (android.content.IntentSender) intent2.getParcelableExtra(android.content.Intent.EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER, android.content.IntentSender.class);
        setSafeForwardingMode(true);
        this.mPinnedSharedPrefs = getPinnedSharedPrefs(this);
        parcelableArrayExtra2 = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_EXCLUDE_COMPONENTS);
        android.content.ComponentName nearbySharingComponent2 = getNearbySharingComponent();
        if (shouldNearbyShareBeFirstInRankedRow()) {
        }
        if (parcelableArrayExtra2 == null) {
        }
        parcelableArrayExtra3 = intent2.getParcelableArrayExtra(android.content.Intent.EXTRA_CHOOSER_TARGETS);
        if (parcelableArrayExtra3 != null) {
        }
        this.mMaxTargetsPerRow = getResources().getInteger(com.android.internal.R.integer.config_chooser_max_targets_per_row);
        this.mShouldDisplayLandscape = shouldDisplayLandscape(getResources().getConfiguration().orientation);
        setRetainInOnStop(intent2.getBooleanExtra(EXTRA_PRIVATE_RETAIN_IN_ON_STOP, false));
        super.onCreate(bundle, intent, charSequence, i, intentArr, null, false);
        this.mChooserShownTime = java.lang.System.currentTimeMillis();
        long j2 = this.mChooserShownTime - currentTimeMillis;
        getMetricsLogger().write(new android.metrics.LogMaker(214).setSubtype(isWorkProfile() ? i2 : 1).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_SHARESHEET_MIMETYPE, intent.getType()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_TIME_TO_APP_TARGETS, java.lang.Long.valueOf(j2)));
        if (this.mResolverDrawerLayout != null) {
        }
        android.util.Log.d(TAG, "System Time Cost is " + j2);
        getChooserActivityLogger().logShareStarted(259, getReferrerPackageName(), intent.getType(), this.mCallerChooserTargets != null ? 0 : this.mCallerChooserTargets.length, intentArr != null ? 0 : intentArr.length, isWorkProfile(), findPreferredContentPreview(getTargetIntent(), getContentResolver()), intent.getAction());
        this.mDirectShareShortcutInfoCache = new java.util.HashMap();
        setEnterSharedElementCallback(new android.app.SharedElementCallback() { // from class: com.android.internal.app.ChooserActivity.2
            @Override // android.app.SharedElementCallback
            public void onMapSharedElements(java.util.List<java.lang.String> list, java.util.Map<java.lang.String, android.view.View> map) {
                if (com.android.internal.app.ChooserActivity.this.mRemoveSharedElements) {
                    list.remove("screenshot_preview_image");
                    map.remove("screenshot_preview_image");
                }
                super.onMapSharedElements(list, map);
                com.android.internal.app.ChooserActivity.this.mRemoveSharedElements = false;
            }
        });
        this.mEnterTransitionAnimationDelegate.postponeTransition();
    }

    @Override // com.android.internal.app.ResolverActivity
    protected int appliedThemeResId() {
        return com.android.internal.R.style.Theme_DeviceDefault_Chooser;
    }

    private android.app.prediction.AppPredictor setupAppPredictorForUser(android.os.UserHandle userHandle, android.app.prediction.AppPredictor.Callback callback) {
        android.app.prediction.AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(userHandle);
        if (appPredictorForDirectShareIfEnabled == null) {
            return null;
        }
        this.mDirectShareAppTargetCache = new java.util.HashMap();
        appPredictorForDirectShareIfEnabled.registerPredictionUpdates(getMainExecutor(), callback);
        return appPredictorForDirectShareIfEnabled;
    }

    private com.android.internal.app.ResolverAppPredictorCallback createAppPredictorCallback(final com.android.internal.app.ChooserListAdapter chooserListAdapter) {
        return new com.android.internal.app.ResolverAppPredictorCallback(new java.util.function.Consumer() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.app.ChooserActivity.this.lambda$createAppPredictorCallback$0(chooserListAdapter, (java.util.List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createAppPredictorCallback$0(com.android.internal.app.ChooserListAdapter chooserListAdapter, java.util.List list) {
        if (isFinishing() || isDestroyed() || chooserListAdapter.getCount() == 0) {
            return;
        }
        if (list.isEmpty() && shouldQueryShortcutManager(chooserListAdapter.getUserHandle())) {
            queryDirectShareTargets(chooserListAdapter, true);
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            android.app.prediction.AppTarget appTarget = (android.app.prediction.AppTarget) it.next();
            if (appTarget.getShortcutInfo() != null) {
                arrayList2.add(appTarget);
            }
        }
        for (android.app.prediction.AppTarget appTarget2 : arrayList2) {
            arrayList.add(new android.content.pm.ShortcutManager.ShareShortcutInfo(appTarget2.getShortcutInfo(), new android.content.ComponentName(appTarget2.getPackageName(), appTarget2.getClassName())));
        }
        sendShareShortcutInfoList(arrayList, chooserListAdapter, arrayList2, chooserListAdapter.getUserHandle());
    }

    static android.content.SharedPreferences getPinnedSharedPrefs(android.content.Context context) {
        return context.getSharedPreferences(new java.io.File(new java.io.File(android.os.Environment.getDataUserCePackageDirectory(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, context.getUserId(), context.getPackageName()), "shared_prefs"), "chooser_pin_settings.xml"), 0);
    }

    @Override // com.android.internal.app.ResolverActivity
    protected com.android.internal.app.AbstractMultiProfilePagerAdapter createMultiProfilePagerAdapter(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        if (shouldShowTabs()) {
            this.mChooserMultiProfilePagerAdapter = createChooserMultiProfilePagerAdapterForTwoProfiles(intentArr, list, z);
        } else {
            this.mChooserMultiProfilePagerAdapter = createChooserMultiProfilePagerAdapterForOneProfile(intentArr, list, z);
        }
        return this.mChooserMultiProfilePagerAdapter;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        boolean isSendAction = isSendAction(getTargetIntent());
        return new com.android.internal.app.NoCrossProfileEmptyStateProvider(getPersonalProfileUserHandle(), new com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, com.android.internal.R.string.resolver_cross_profile_blocked, isSendAction ? android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_SHARE_WITH_PERSONAL : android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_PERSONAL, isSendAction ? 17041548 : 17041546, 158, "intent_chooser"), new com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, com.android.internal.R.string.resolver_cross_profile_blocked, isSendAction ? android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_SHARE_WITH_WORK : android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_WORK, isSendAction ? 17041549 : 17041547, 159, "intent_chooser"), createCrossProfileIntentsChecker(), getTabOwnerUserHandleForLaunch());
    }

    private com.android.internal.app.ChooserMultiProfilePagerAdapter createChooserMultiProfilePagerAdapterForOneProfile(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        return new com.android.internal.app.ChooserMultiProfilePagerAdapter(this, createChooserGridAdapter(this, this.mIntents, intentArr, list, z, getPersonalProfileUserHandle()), createEmptyStateProvider(null), this.mQuietModeManager, null, getCloneProfileUserHandle(), this.mMaxTargetsPerRow);
    }

    private com.android.internal.app.ChooserMultiProfilePagerAdapter createChooserMultiProfilePagerAdapterForTwoProfiles(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        int findSelectedProfile = findSelectedProfile();
        return new com.android.internal.app.ChooserMultiProfilePagerAdapter(this, createChooserGridAdapter(this, this.mIntents, findSelectedProfile == 0 ? intentArr : null, list, z, getPersonalProfileUserHandle()), createChooserGridAdapter(this, this.mIntents, findSelectedProfile == 1 ? intentArr : null, list, z, getWorkProfileUserHandle()), createEmptyStateProvider(getWorkProfileUserHandle()), this.mQuietModeManager, findSelectedProfile, getWorkProfileUserHandle(), getCloneProfileUserHandle(), this.mMaxTargetsPerRow);
    }

    private int findSelectedProfile() {
        int selectedProfileExtra = getSelectedProfileExtra();
        if (selectedProfileExtra == -1) {
            return getProfileForUser(getTabOwnerUserHandleForLaunch());
        }
        return selectedProfileExtra;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected boolean postRebuildList(boolean z) {
        updateStickyContentPreview();
        if (shouldShowStickyContentPreview() || this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().getSystemRowCount() != 0) {
            logActionShareWithPreview();
        }
        return postRebuildListInternal(z);
    }

    private boolean isAppPredictionServiceAvailable() {
        return getPackageManager().getAppPredictionServicePackageName() != null;
    }

    protected boolean isWorkProfile() {
        return ((android.os.UserManager) getSystemService(android.os.UserManager.class)).getUserInfo(android.os.UserHandle.myUserId()).isManagedProfile();
    }

    @Override // com.android.internal.app.ResolverActivity
    protected com.android.internal.content.PackageMonitor createPackageMonitor(final com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        return new com.android.internal.content.PackageMonitor() { // from class: com.android.internal.app.ChooserActivity.3
            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                com.android.internal.app.ChooserActivity.this.handlePackagesChanged(resolverListAdapter);
            }
        };
    }

    public void handlePackagesChanged() {
        handlePackagesChanged(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackagesChanged(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        this.mPinnedSharedPrefs = getPinnedSharedPrefs(this);
        if (resolverListAdapter == null) {
            this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
            if (this.mChooserMultiProfilePagerAdapter.getCount() > 1) {
                this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter().handlePackagesChanged();
            }
        } else {
            resolverListAdapter.handlePackagesChanged();
        }
        updateProfileViewButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCopyButtonClicked(android.view.View view) {
        android.content.ClipData clipData;
        android.content.Intent targetIntent = getTargetIntent();
        if (targetIntent == null) {
            finish();
            return;
        }
        java.lang.String action = targetIntent.getAction();
        if (android.content.Intent.ACTION_SEND.equals(action)) {
            java.lang.String stringExtra = targetIntent.getStringExtra(android.content.Intent.EXTRA_TEXT);
            android.net.Uri uri = (android.net.Uri) targetIntent.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
            if (stringExtra != null) {
                clipData = android.content.ClipData.newPlainText(null, stringExtra);
            } else if (uri != null) {
                clipData = android.content.ClipData.newUri(getContentResolver(), null, uri);
            } else {
                android.util.Log.w(TAG, "No data available to copy to clipboard");
                return;
            }
        } else if (android.content.Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            java.util.ArrayList parcelableArrayListExtra = targetIntent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
            android.content.ClipData newUri = android.content.ClipData.newUri(getContentResolver(), null, (android.net.Uri) parcelableArrayListExtra.get(0));
            for (int i = 1; i < parcelableArrayListExtra.size(); i++) {
                newUri.addItem(getContentResolver(), new android.content.ClipData.Item((android.net.Uri) parcelableArrayListExtra.get(i)));
            }
            clipData = newUri;
        } else {
            android.util.Log.w(TAG, "Action (" + action + ") not supported for copying to clipboard");
            return;
        }
        ((android.content.ClipboardManager) getSystemService("clipboard")).setPrimaryClipAsPackage(clipData, getReferrerPackageName());
        getMetricsLogger().write(new android.metrics.LogMaker(1749).setSubtype(1));
        getChooserActivityLogger().logShareTargetSelected(4, "", -1, false);
        setResult(-1);
        finish();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        android.util.Log.d(TAG, "onResume: " + getComponentName().flattenToShortString());
        maybeCancelFinishAnimation();
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        com.android.internal.widget.ViewPager viewPager = (com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.profile_pager);
        if (viewPager.isLayoutRtl()) {
            this.mMultiProfilePagerAdapter.setupViewPager(viewPager);
        }
        this.mShouldDisplayLandscape = shouldDisplayLandscape(configuration.orientation);
        this.mMaxTargetsPerRow = getResources().getInteger(com.android.internal.R.integer.config_chooser_max_targets_per_row);
        this.mChooserMultiProfilePagerAdapter.setMaxTargetsPerRow(this.mMaxTargetsPerRow);
        adjustPreviewWidth(configuration.orientation, null);
        updateStickyContentPreview();
        updateTabPadding();
    }

    private boolean shouldDisplayLandscape(int i) {
        return i == 2 && !isInMultiWindowMode();
    }

    private void adjustPreviewWidth(int i, android.view.View view) {
        int i2;
        if (!this.mShouldDisplayLandscape) {
            i2 = -1;
        } else {
            i2 = getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_preview_width);
        }
        if (view == null) {
            view = getWindow().getDecorView();
        }
        updateLayoutWidth(com.android.internal.R.id.content_preview_text_layout, i2, view);
        updateLayoutWidth(com.android.internal.R.id.content_preview_title_layout, i2, view);
        updateLayoutWidth(com.android.internal.R.id.content_preview_file_layout, i2, view);
    }

    private void updateTabPadding() {
        if (shouldShowTabs()) {
            android.view.View findViewById = findViewById(16908307);
            int width = (int) ((((findViewById.getWidth() - (this.mMaxTargetsPerRow * getResources().getDimension(com.android.internal.R.dimen.chooser_icon_size))) / this.mMaxTargetsPerRow) / 2.0f) - getResources().getDimension(com.android.internal.R.dimen.resolver_profile_tab_margin));
            findViewById.setPadding(width, 0, width, 0);
        }
    }

    private void updateLayoutWidth(int i, int i2, android.view.View view) {
        android.view.View findViewById = view.findViewById(i);
        if (findViewById != null && findViewById.getLayoutParams() != null) {
            android.view.ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.width = i2;
            findViewById.setLayoutParams(layoutParams);
        }
    }

    protected android.view.ViewGroup createContentPreviewView(android.view.ViewGroup viewGroup) {
        android.content.Intent targetIntent = getTargetIntent();
        return displayContentPreview(findPreferredContentPreview(targetIntent, getContentResolver()), targetIntent, getLayoutInflater(), viewGroup);
    }

    protected android.content.ComponentName getNearbySharingComponent() {
        java.lang.String string = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.NEARBY_SHARING_COMPONENT);
        if (android.text.TextUtils.isEmpty(string)) {
            string = getString(com.android.internal.R.string.config_defaultNearbySharingComponent);
        }
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(string);
    }

    protected android.content.ComponentName getEditSharingComponent() {
        java.lang.String string = getApplicationContext().getString(com.android.internal.R.string.config_systemImageEditor);
        if (string == null || android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(string);
    }

    protected com.android.internal.app.chooser.TargetInfo getEditSharingTarget(android.content.Intent intent) {
        android.net.Uri uri;
        android.content.ComponentName editSharingComponent = getEditSharingComponent();
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setFlags(intent.getFlags() & 195);
        intent2.setComponent(editSharingComponent);
        intent2.setAction(android.content.Intent.ACTION_EDIT);
        java.lang.String action = intent.getAction();
        if (android.content.Intent.ACTION_SEND.equals(action)) {
            if (intent2.getData() == null && (uri = (android.net.Uri) intent2.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class)) != null) {
                intent2.setDataAndType(uri, getContentResolver().getType(uri));
            }
            android.content.pm.ResolveInfo resolveActivity = getPackageManager().resolveActivity(intent2, 128);
            if (resolveActivity == null || resolveActivity.activityInfo == null) {
                android.util.Log.e(TAG, "Device-specified image edit component (" + editSharingComponent + ") not available");
                return null;
            }
            com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = new com.android.internal.app.chooser.DisplayResolveInfo(intent, resolveActivity, getString(com.android.internal.R.string.screenshot_edit), "", intent2, null);
            displayResolveInfo.setDisplayIcon(getDrawable(com.android.internal.R.drawable.ic_screenshot_edit));
            return displayResolveInfo;
        }
        android.util.Log.e(TAG, action + " is not supported.");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected com.android.internal.app.chooser.TargetInfo getNearbySharingTarget(android.content.Intent intent) {
        java.lang.String str;
        android.graphics.drawable.Drawable drawable;
        android.content.res.Resources resourcesForActivity;
        java.lang.CharSequence charSequence;
        android.content.ComponentName nearbySharingComponent = getNearbySharingComponent();
        android.graphics.drawable.Drawable drawable2 = null;
        drawable2 = null;
        java.lang.String str2 = null;
        if (nearbySharingComponent == null) {
            return null;
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setComponent(nearbySharingComponent);
        android.content.pm.ResolveInfo resolveActivity = getPackageManager().resolveActivity(intent2, 128);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            android.util.Log.e(TAG, "Device-specified nearby sharing component (" + nearbySharingComponent + ") not available");
            return null;
        }
        android.os.Bundle bundle = resolveActivity.activityInfo.metaData;
        if (bundle == null) {
            drawable = null;
        } else {
            try {
                resourcesForActivity = getPackageManager().getResourcesForActivity(nearbySharingComponent);
                str = resourcesForActivity.getString(bundle.getInt(CHIP_LABEL_METADATA_KEY));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                str = null;
            } catch (android.content.res.Resources.NotFoundException e2) {
                str = null;
            }
            try {
                drawable2 = resourcesForActivity.getDrawable(bundle.getInt(CHIP_ICON_METADATA_KEY));
            } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                drawable = null;
                str2 = str;
                if (android.text.TextUtils.isEmpty(str2)) {
                }
                if (drawable == null) {
                }
                com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = new com.android.internal.app.chooser.DisplayResolveInfo(intent, resolveActivity, charSequence, "", intent2, null);
                displayResolveInfo.setDisplayIcon(drawable);
                return displayResolveInfo;
            } catch (android.content.res.Resources.NotFoundException e4) {
            }
            drawable = drawable2;
            str2 = str;
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            charSequence = str2;
        } else {
            charSequence = resolveActivity.loadLabel(getPackageManager());
        }
        if (drawable == null) {
            drawable = resolveActivity.loadIcon(getPackageManager());
        }
        com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo2 = new com.android.internal.app.chooser.DisplayResolveInfo(intent, resolveActivity, charSequence, "", intent2, null);
        displayResolveInfo2.setDisplayIcon(drawable);
        return displayResolveInfo2;
    }

    private android.widget.Button createActionButton(android.graphics.drawable.Drawable drawable, java.lang.CharSequence charSequence, android.view.View.OnClickListener onClickListener) {
        android.widget.Button button = (android.widget.Button) android.view.LayoutInflater.from(this).inflate(com.android.internal.R.layout.chooser_action_button, (android.view.ViewGroup) null);
        if (drawable != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_action_button_icon_size);
            drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            button.setCompoundDrawablesRelative(drawable, null, null, null);
        }
        button.lambda$setTextAsync$0(charSequence);
        button.setOnClickListener(onClickListener);
        return button;
    }

    private android.widget.Button createCopyButton() {
        android.widget.Button createActionButton = createActionButton(getDrawable(com.android.internal.R.drawable.ic_menu_copy_material), getString(17039361), new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.ChooserActivity.this.onCopyButtonClicked(view);
            }
        });
        createActionButton.setId(com.android.internal.R.id.chooser_copy_button);
        return createActionButton;
    }

    private android.widget.Button createNearbyButton(android.content.Intent intent) {
        final com.android.internal.app.chooser.TargetInfo nearbySharingTarget = getNearbySharingTarget(intent);
        if (nearbySharingTarget == null) {
            return null;
        }
        android.widget.Button createActionButton = createActionButton(nearbySharingTarget.getDisplayIcon(this), nearbySharingTarget.getDisplayLabel(), new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.ChooserActivity.this.lambda$createNearbyButton$1(nearbySharingTarget, view);
            }
        });
        createActionButton.setId(com.android.internal.R.id.chooser_nearby_button);
        return createActionButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNearbyButton$1(com.android.internal.app.chooser.TargetInfo targetInfo, android.view.View view) {
        getChooserActivityLogger().logShareTargetSelected(5, "", -1, false);
        safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle());
        finish();
    }

    private android.widget.Button createEditButton(android.content.Intent intent) {
        final com.android.internal.app.chooser.TargetInfo editSharingTarget = getEditSharingTarget(intent);
        if (editSharingTarget == null) {
            return null;
        }
        android.widget.Button createActionButton = createActionButton(editSharingTarget.getDisplayIcon(this), editSharingTarget.getDisplayLabel(), new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.ChooserActivity.this.lambda$createEditButton$2(editSharingTarget, view);
            }
        });
        createActionButton.setId(com.android.internal.R.id.chooser_edit_button);
        return createActionButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEditButton$2(com.android.internal.app.chooser.TargetInfo targetInfo, android.view.View view) {
        getChooserActivityLogger().logShareTargetSelected(6, "", -1, false);
        android.view.View firstVisibleImgPreviewView = getFirstVisibleImgPreviewView();
        if (firstVisibleImgPreviewView == null) {
            safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle());
            finish();
        } else {
            safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle(), android.app.ActivityOptions.makeSceneTransitionAnimation(this, firstVisibleImgPreviewView, "screenshot_preview_image").toBundle());
            startFinishAnimation();
        }
    }

    private android.view.View getFirstVisibleImgPreviewView() {
        android.view.View findViewById = findViewById(com.android.internal.R.id.content_preview_image_1_large);
        if (findViewById == null || !findViewById.isVisibleToUser()) {
            return null;
        }
        return findViewById;
    }

    private void addActionButton(android.view.ViewGroup viewGroup, android.widget.Button button) {
        if (button == null) {
            return;
        }
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = new android.view.ViewGroup.MarginLayoutParams(-2, -2);
        int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_icon_margin) / 2;
        marginLayoutParams.setMarginsRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        viewGroup.addView(button, marginLayoutParams);
    }

    private android.view.ViewGroup displayContentPreview(int i, android.content.Intent intent, android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup) {
        android.view.ViewGroup displayImageContentPreview;
        switch (i) {
            case 1:
                displayImageContentPreview = displayImageContentPreview(intent, layoutInflater, viewGroup);
                break;
            case 2:
                displayImageContentPreview = displayFileContentPreview(intent, layoutInflater, viewGroup);
                break;
            case 3:
                displayImageContentPreview = displayTextContentPreview(intent, layoutInflater, viewGroup);
                break;
            default:
                android.util.Log.e(TAG, "Unexpected content preview type: " + i);
                displayImageContentPreview = null;
                break;
        }
        if (displayImageContentPreview != null) {
            adjustPreviewWidth(getResources().getConfiguration().orientation, displayImageContentPreview);
        }
        if (i != 1) {
            this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
        }
        return displayImageContentPreview;
    }

    private android.view.ViewGroup displayTextContentPreview(android.content.Intent intent, android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup) {
        android.net.Uri uri;
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) layoutInflater.inflate(com.android.internal.R.layout.chooser_grid_preview_text, viewGroup, false);
        android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) viewGroup2.findViewById(com.android.internal.R.id.chooser_action_row);
        addActionButton(viewGroup3, createCopyButton());
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup3, createNearbyButton(intent));
        }
        java.lang.CharSequence charSequenceExtra = intent.getCharSequenceExtra(android.content.Intent.EXTRA_TEXT);
        if (charSequenceExtra == null) {
            viewGroup2.findViewById(com.android.internal.R.id.content_preview_text_layout).setVisibility(8);
        } else {
            ((android.widget.TextView) viewGroup2.findViewById(com.android.internal.R.id.content_preview_text)).lambda$setTextAsync$0(charSequenceExtra);
        }
        java.lang.String stringExtra = intent.getStringExtra(android.content.Intent.EXTRA_TITLE);
        if (android.text.TextUtils.isEmpty(stringExtra)) {
            viewGroup2.findViewById(com.android.internal.R.id.content_preview_title_layout).setVisibility(8);
        } else {
            ((android.widget.TextView) viewGroup2.findViewById(com.android.internal.R.id.content_preview_title)).lambda$setTextAsync$0(stringExtra);
            android.content.ClipData clipData = intent.getClipData();
            if (clipData != null && clipData.getItemCount() > 0) {
                uri = clipData.getItemAt(0).getUri();
            } else {
                uri = null;
            }
            android.widget.ImageView imageView = (android.widget.ImageView) viewGroup2.findViewById(com.android.internal.R.id.content_preview_thumbnail);
            if (!validForContentPreview(uri)) {
                imageView.setVisibility(8);
            } else {
                this.mPreviewCoord = new com.android.internal.app.ChooserActivity.ContentPreviewCoordinator(viewGroup2, false);
                this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_thumbnail, uri, 0);
            }
        }
        return viewGroup2;
    }

    private android.view.ViewGroup displayImageContentPreview(android.content.Intent intent, android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup) {
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) layoutInflater.inflate(com.android.internal.R.layout.chooser_grid_preview_image, viewGroup, false);
        android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) viewGroup2.findViewById(com.android.internal.R.id.content_preview_image_area);
        android.view.ViewGroup viewGroup4 = (android.view.ViewGroup) viewGroup2.findViewById(com.android.internal.R.id.chooser_action_row);
        java.lang.String action = intent.getAction();
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup4, createNearbyButton(intent));
        }
        if (!android.content.Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            addActionButton(viewGroup4, createEditButton(intent));
        }
        this.mPreviewCoord = new com.android.internal.app.ChooserActivity.ContentPreviewCoordinator(viewGroup2, false);
        if (android.content.Intent.ACTION_SEND.equals(action)) {
            android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
            if (!validForContentPreview(uri)) {
                viewGroup3.setVisibility(8);
                return viewGroup2;
            }
            viewGroup3.findViewById(com.android.internal.R.id.content_preview_image_1_large).setTransitionName("screenshot_preview_image");
            this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_image_1_large, uri, 0);
        } else {
            android.content.ContentResolver contentResolver = getContentResolver();
            java.util.ArrayList<android.net.Uri> parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.net.Uri uri2 : parcelableArrayListExtra) {
                if (validForContentPreview(uri2) && isImageType(contentResolver.getType(uri2))) {
                    arrayList.add(uri2);
                }
            }
            if (arrayList.size() == 0) {
                android.util.Log.i(TAG, "Attempted to display image preview area with zero available images detected in EXTRA_STREAM list");
                viewGroup3.setVisibility(8);
                return viewGroup2;
            }
            viewGroup3.findViewById(com.android.internal.R.id.content_preview_image_1_large).setTransitionName("screenshot_preview_image");
            this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_image_1_large, (android.net.Uri) arrayList.get(0), 0);
            if (arrayList.size() == 2) {
                this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_image_2_large, (android.net.Uri) arrayList.get(1), 0);
            } else if (arrayList.size() > 2) {
                this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_image_2_small, (android.net.Uri) arrayList.get(1), 0);
                this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_image_3_small, (android.net.Uri) arrayList.get(2), arrayList.size() - 3);
            }
        }
        return viewGroup2;
    }

    private static class FileInfo {
        public final boolean hasThumbnail;
        public final java.lang.String name;

        FileInfo(java.lang.String str, boolean z) {
            this.name = str;
            this.hasThumbnail = z;
        }
    }

    public android.database.Cursor queryResolver(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        return contentResolver.query(uri, null, null, null, null);
    }

    private com.android.internal.app.ChooserActivity.FileInfo extractFileInfo(android.net.Uri uri, android.content.ContentResolver contentResolver) {
        int lastIndexOf;
        java.lang.String str = null;
        boolean z = false;
        try {
            android.database.Cursor queryResolver = queryResolver(contentResolver, uri);
            if (queryResolver != null) {
                try {
                    if (queryResolver.getCount() > 0) {
                        int columnIndex = queryResolver.getColumnIndex("_display_name");
                        int columnIndex2 = queryResolver.getColumnIndex("title");
                        int columnIndex3 = queryResolver.getColumnIndex("flags");
                        queryResolver.moveToFirst();
                        if (columnIndex != -1) {
                            str = queryResolver.getString(columnIndex);
                        } else if (columnIndex2 != -1) {
                            str = queryResolver.getString(columnIndex2);
                        }
                        if (columnIndex3 != -1) {
                            if ((queryResolver.getInt(columnIndex3) & 1) != 0) {
                                z = true;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    if (queryResolver != null) {
                        try {
                            queryResolver.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (queryResolver != null) {
                queryResolver.close();
            }
        } catch (java.lang.NullPointerException | java.lang.SecurityException e) {
            logContentPreviewWarning(uri);
        }
        if (android.text.TextUtils.isEmpty(str) && (lastIndexOf = (str = uri.getPath()).lastIndexOf(47)) != -1) {
            str = str.substring(lastIndexOf + 1);
        }
        return new com.android.internal.app.ChooserActivity.FileInfo(str, z);
    }

    private void logContentPreviewWarning(android.net.Uri uri) {
        android.util.Log.w(TAG, "Could not load (" + uri.toString() + ") thumbnail/name for preview. If desired, consider using Intent#createChooser to launch the ChooserActivity, and set your Intent's clipData and flags in accordance with that method's documentation");
    }

    private android.view.ViewGroup displayFileContentPreview(android.content.Intent intent, android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup) {
        android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) layoutInflater.inflate(com.android.internal.R.layout.chooser_grid_preview_file, viewGroup, false);
        android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) viewGroup2.findViewById(com.android.internal.R.id.chooser_action_row);
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup3, createNearbyButton(intent));
        }
        if (android.content.Intent.ACTION_SEND.equals(intent.getAction())) {
            android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
            if (!validForContentPreview(uri)) {
                viewGroup2.setVisibility(8);
                return viewGroup2;
            }
            loadFileUriIntoView(uri, viewGroup2);
        } else {
            java.util.List list = (java.util.List) intent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class).stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean validForContentPreview;
                    validForContentPreview = com.android.internal.app.ChooserActivity.validForContentPreview((android.net.Uri) obj);
                    return validForContentPreview;
                }
            }).collect(java.util.stream.Collectors.toList());
            int size = list.size();
            if (size == 0) {
                viewGroup2.setVisibility(8);
                android.util.Log.i(TAG, "Appears to be no uris available in EXTRA_STREAM, removing preview area");
                return viewGroup2;
            }
            if (size == 1) {
                loadFileUriIntoView((android.net.Uri) list.get(0), viewGroup2);
            } else {
                com.android.internal.app.ChooserActivity.FileInfo extractFileInfo = extractFileInfo((android.net.Uri) list.get(0), getContentResolver());
                java.util.HashMap hashMap = new java.util.HashMap();
                hashMap.put(PLURALS_COUNT, java.lang.Integer.valueOf(size - 1));
                hashMap.put(PLURALS_FILE_NAME, extractFileInfo.name);
                ((android.widget.TextView) viewGroup2.findViewById(com.android.internal.R.id.content_preview_filename)).lambda$setTextAsync$0(android.util.PluralsMessageFormatter.format(getResources(), hashMap, com.android.internal.R.string.file_count));
                viewGroup2.findViewById(com.android.internal.R.id.content_preview_file_thumbnail).setVisibility(8);
                android.widget.ImageView imageView = (android.widget.ImageView) viewGroup2.findViewById(com.android.internal.R.id.content_preview_file_icon);
                imageView.setVisibility(0);
                imageView.setImageResource(com.android.internal.R.drawable.ic_file_copy);
            }
        }
        return viewGroup2;
    }

    private void loadFileUriIntoView(android.net.Uri uri, android.view.View view) {
        com.android.internal.app.ChooserActivity.FileInfo extractFileInfo = extractFileInfo(uri, getContentResolver());
        ((android.widget.TextView) view.findViewById(com.android.internal.R.id.content_preview_filename)).lambda$setTextAsync$0(extractFileInfo.name);
        if (extractFileInfo.hasThumbnail) {
            this.mPreviewCoord = new com.android.internal.app.ChooserActivity.ContentPreviewCoordinator(view, false);
            this.mPreviewCoord.loadUriIntoView(com.android.internal.R.id.content_preview_file_thumbnail, uri, 0);
        } else {
            view.findViewById(com.android.internal.R.id.content_preview_file_thumbnail).setVisibility(8);
            android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.content_preview_file_icon);
            imageView.setVisibility(0);
            imageView.setImageResource(com.android.internal.R.drawable.chooser_file_generic);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean validForContentPreview(android.net.Uri uri) throws java.lang.SecurityException {
        if (uri == null) {
            return false;
        }
        int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, -2);
        if (userIdFromUri != -2 && userIdFromUri != android.os.UserHandle.myUserId()) {
            android.util.Log.e(TAG, "dropped invalid content URI belonging to user " + userIdFromUri);
            return false;
        }
        return true;
    }

    protected boolean isImageType(java.lang.String str) {
        return str != null && str.startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    private int findPreferredContentPreview(android.net.Uri uri, android.content.ContentResolver contentResolver) {
        if (uri == null) {
            return 3;
        }
        return isImageType(contentResolver.getType(uri)) ? 1 : 2;
    }

    private int findPreferredContentPreview(android.content.Intent intent, android.content.ContentResolver contentResolver) {
        java.util.ArrayList parcelableArrayListExtra;
        java.lang.String action = intent.getAction();
        if (android.content.Intent.ACTION_SEND.equals(action)) {
            return findPreferredContentPreview((android.net.Uri) intent.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class), contentResolver);
        }
        if (!android.content.Intent.ACTION_SEND_MULTIPLE.equals(action) || (parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class)) == null || parcelableArrayListExtra.isEmpty()) {
            return 3;
        }
        java.util.Iterator it = parcelableArrayListExtra.iterator();
        while (it.hasNext()) {
            if (findPreferredContentPreview((android.net.Uri) it.next(), contentResolver) == 2) {
                return 2;
            }
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNumSheetExpansions() {
        return getPreferences(0).getInt(PREF_NUM_SHEET_EXPANSIONS, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void incrementNumSheetExpansions() {
        getPreferences(0).edit().putInt(PREF_NUM_SHEET_EXPANSIONS, getNumSheetExpansions() + 1).apply();
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        if (maybeCancelFinishAnimation()) {
            finish();
        }
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            this.mLatencyTracker.lambda$onActionStart$1(16);
        }
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        this.mChooserHandler.removeAllMessages();
        if (this.mPreviewCoord != null) {
            this.mPreviewCoord.cancelLoads();
        }
        this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().destroyAppPredictor();
        if (this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter() != null) {
            this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter().destroyAppPredictor();
        }
        this.mPersonalAppPredictor = null;
        this.mWorkAppPredictor = null;
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public android.content.Intent getReplacementIntent(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent) {
        android.os.Bundle bundle;
        if (this.mReplacementExtras != null && (bundle = this.mReplacementExtras.getBundle(activityInfo.packageName)) != null) {
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.putExtras(bundle);
            intent = intent2;
        }
        if (activityInfo.name.equals(com.android.internal.app.IntentForwarderActivity.FORWARD_INTENT_TO_PARENT) || activityInfo.name.equals(com.android.internal.app.IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            android.content.Intent createChooser = android.content.Intent.createChooser(intent, getIntent().getCharSequenceExtra(android.content.Intent.EXTRA_TITLE));
            createChooser.putExtra(android.content.Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, false);
            return createChooser;
        }
        return intent;
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onActivityStarted(com.android.internal.app.chooser.TargetInfo targetInfo) {
        android.content.ComponentName resolvedComponentName;
        if (this.mChosenComponentSender != null && (resolvedComponentName = targetInfo.getResolvedComponentName()) != null) {
            try {
                this.mChosenComponentSender.sendIntent(this, -1, new android.content.Intent().putExtra(android.content.Intent.EXTRA_CHOSEN_COMPONENT, resolvedComponentName), null, null);
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Slog.e(TAG, "Unable to launch supplied IntentSender to report the chosen component: " + e);
            }
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    public void addUseDifferentAppLabelIfNecessary(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (this.mCallerChooserTargets != null && this.mCallerChooserTargets.length > 0) {
            this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().addServiceResults(null, com.google.android.collect.Lists.newArrayList(this.mCallerChooserTargets), 0, null);
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    public int getLayoutResource() {
        return com.android.internal.R.layout.chooser_grid;
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean shouldGetActivityMetadata() {
        return true;
    }

    @Override // com.android.internal.app.ResolverActivity
    public boolean shouldAutoLaunchSingleChoice(com.android.internal.app.chooser.TargetInfo targetInfo) {
        if (!super.shouldAutoLaunchSingleChoice(targetInfo)) {
            return false;
        }
        return getIntent().getBooleanExtra(android.content.Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTargetDetails(com.android.internal.app.chooser.TargetInfo targetInfo) {
        java.util.ArrayList<com.android.internal.app.chooser.DisplayResolveInfo> arrayList;
        if (targetInfo == null) {
            return;
        }
        com.android.internal.app.ChooserTargetActionsDialogFragment chooserTargetActionsDialogFragment = new com.android.internal.app.ChooserTargetActionsDialogFragment();
        android.os.Bundle bundle = new android.os.Bundle();
        if (targetInfo instanceof com.android.internal.app.chooser.SelectableTargetInfo) {
            com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo = (com.android.internal.app.chooser.SelectableTargetInfo) targetInfo;
            if (selectableTargetInfo.getDisplayResolveInfo() == null || selectableTargetInfo.getChooserTarget() == null) {
                android.util.Log.e(TAG, "displayResolveInfo or chooserTarget in selectableTargetInfo are null");
                return;
            }
            arrayList = new java.util.ArrayList<>();
            arrayList.add(selectableTargetInfo.getDisplayResolveInfo());
            bundle.putString(com.android.internal.app.ChooserTargetActionsDialogFragment.SHORTCUT_ID_KEY, selectableTargetInfo.getChooserTarget().getIntentExtras().getString(android.content.Intent.EXTRA_SHORTCUT_ID));
            bundle.putBoolean(com.android.internal.app.ChooserTargetActionsDialogFragment.IS_SHORTCUT_PINNED_KEY, selectableTargetInfo.isPinned());
            bundle.putParcelable("intent_filter", getTargetIntentFilter());
            if (selectableTargetInfo.getDisplayLabel() != null) {
                bundle.putString(com.android.internal.app.ChooserTargetActionsDialogFragment.SHORTCUT_TITLE_KEY, selectableTargetInfo.getDisplayLabel().toString());
            }
        } else if (targetInfo instanceof com.android.internal.app.chooser.MultiDisplayResolveInfo) {
            arrayList = ((com.android.internal.app.chooser.MultiDisplayResolveInfo) targetInfo).getTargets();
        } else {
            arrayList = new java.util.ArrayList<>();
            arrayList.add((com.android.internal.app.chooser.DisplayResolveInfo) targetInfo);
        }
        bundle.putParcelable("user_handle", getResolveInfoUserHandle(targetInfo.getResolveInfo(), this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle()));
        bundle.putParcelableArrayList(com.android.internal.app.ChooserTargetActionsDialogFragment.TARGET_INFOS_KEY, arrayList);
        chooserTargetActionsDialogFragment.setArguments(bundle);
        chooserTargetActionsDialogFragment.show(getFragmentManager(), TARGET_DETAILS_FRAGMENT_TAG);
    }

    private void modifyTargetIntent(android.content.Intent intent) {
        if (isSendAction(intent)) {
            intent.addFlags(134742016);
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected boolean onTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo, boolean z) {
        if (this.mRefinementIntentSender != null) {
            android.content.Intent intent = new android.content.Intent();
            java.util.List<android.content.Intent> allSourceIntents = targetInfo.getAllSourceIntents();
            if (!allSourceIntents.isEmpty()) {
                intent.putExtra(android.content.Intent.EXTRA_INTENT, allSourceIntents.get(0));
                if (allSourceIntents.size() > 1) {
                    android.content.Intent[] intentArr = new android.content.Intent[allSourceIntents.size() - 1];
                    int size = allSourceIntents.size();
                    for (int i = 1; i < size; i++) {
                        intentArr[i - 1] = allSourceIntents.get(i);
                    }
                    intent.putExtra(android.content.Intent.EXTRA_ALTERNATE_INTENTS, intentArr);
                }
                if (this.mRefinementResultReceiver != null) {
                    this.mRefinementResultReceiver.destroy();
                }
                this.mRefinementResultReceiver = new com.android.internal.app.ChooserActivity.RefinementResultReceiver(this, targetInfo, null);
                intent.putExtra(android.content.Intent.EXTRA_RESULT_RECEIVER, this.mRefinementResultReceiver);
                try {
                    this.mRefinementIntentSender.sendIntent(this, 0, intent, null, null);
                    return false;
                } catch (android.content.IntentSender.SendIntentException e) {
                    android.util.Log.e(TAG, "Refinement IntentSender failed to send", e);
                }
            }
        }
        updateModelAndChooserCounts(targetInfo);
        return super.onTargetSelected(targetInfo, z);
    }

    @Override // com.android.internal.app.ResolverActivity
    public void startSelected(int i, boolean z, boolean z2) {
        int i2;
        android.util.HashedStringCache.HashResult hashResult;
        com.android.internal.app.ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
        com.android.internal.app.chooser.TargetInfo targetInfoForPosition = activeListAdapter.targetInfoForPosition(i, z2);
        if (targetInfoForPosition != null && (targetInfoForPosition instanceof com.android.internal.app.chooser.NotSelectableTargetInfo)) {
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mChooserShownTime;
        if (targetInfoForPosition instanceof com.android.internal.app.chooser.MultiDisplayResolveInfo) {
            com.android.internal.app.chooser.MultiDisplayResolveInfo multiDisplayResolveInfo = (com.android.internal.app.chooser.MultiDisplayResolveInfo) targetInfoForPosition;
            if (!multiDisplayResolveInfo.hasSelected()) {
                com.android.internal.app.ChooserStackedAppDialogFragment chooserStackedAppDialogFragment = new com.android.internal.app.ChooserStackedAppDialogFragment();
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable("user_handle", getResolveInfoUserHandle(targetInfoForPosition.getResolveInfo(), this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle()));
                bundle.putObject("multi_dri_key", multiDisplayResolveInfo);
                bundle.putInt("which_key", i);
                chooserStackedAppDialogFragment.setArguments(bundle);
                chooserStackedAppDialogFragment.show(getFragmentManager(), TARGET_DETAILS_FRAGMENT_TAG);
                return;
            }
        }
        super.startSelected(i, z, z2);
        if (activeListAdapter.getCount() > 0) {
            int i3 = -1;
            switch (activeListAdapter.getPositionTargetType(i)) {
                case 0:
                case 2:
                    i -= activeListAdapter.getSurfacedTargetInfo().size();
                    int callerTargetCount = activeListAdapter.getCallerTargetCount();
                    getChooserActivityLogger().logShareTargetSelected(2, targetInfoForPosition.getResolveInfo().activityInfo.processName, i, targetInfoForPosition.isPinned());
                    i2 = callerTargetCount;
                    r6 = 215;
                    hashResult = null;
                    break;
                case 1:
                    android.service.chooser.ChooserTarget chooserTargetForValue = activeListAdapter.getChooserTargetForValue(i);
                    hashResult = android.util.HashedStringCache.getInstance().hashString(this, TAG, chooserTargetForValue.getComponentName().getPackageName() + chooserTargetForValue.getTitle().toString(), this.mMaxHashSaltDays);
                    com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo = (com.android.internal.app.chooser.SelectableTargetInfo) targetInfoForPosition;
                    int rankedPosition = getRankedPosition(selectableTargetInfo);
                    r6 = this.mCallerChooserTargets != null ? this.mCallerChooserTargets.length : 0;
                    getChooserActivityLogger().logShareTargetSelected(1, targetInfoForPosition.getResolveInfo().activityInfo.processName, i, selectableTargetInfo.isPinned());
                    int i4 = r6;
                    r6 = 216;
                    i3 = rankedPosition;
                    i2 = i4;
                    break;
                case 3:
                    getChooserActivityLogger().logShareTargetSelected(3, targetInfoForPosition.getResolveInfo().activityInfo.processName, -1, false);
                    hashResult = null;
                    i2 = 0;
                    r6 = 217;
                    i = -1;
                    break;
                default:
                    hashResult = null;
                    i2 = 0;
                    break;
            }
            if (r6 != 0) {
                android.metrics.LogMaker subtype = new android.metrics.LogMaker(r6).setSubtype(i);
                if (hashResult != null) {
                    subtype.addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_NAME, hashResult.hashedString);
                    subtype.addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_SALT_GEN, java.lang.Integer.valueOf(hashResult.saltGeneration));
                    subtype.addTaggedData(1087, java.lang.Integer.valueOf(i3));
                }
                subtype.addTaggedData(1086, java.lang.Integer.valueOf(i2));
                getMetricsLogger().write(subtype);
            }
            if (this.mIsSuccessfullySelected) {
                android.util.Log.d(TAG, "User Selection Time Cost is " + currentTimeMillis);
                android.util.Log.d(TAG, "position of selected app/service/caller is " + java.lang.Integer.toString(i));
                com.android.internal.logging.MetricsLogger.histogram(null, "user_selection_cost_for_smart_sharing", (int) currentTimeMillis);
                com.android.internal.logging.MetricsLogger.histogram(null, "app_position_for_smart_sharing", i);
            }
        }
    }

    private int getRankedPosition(com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo) {
        java.lang.String packageName = selectableTargetInfo.getChooserTarget().getComponentName().getPackageName();
        com.android.internal.app.ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
        int min = java.lang.Math.min(activeListAdapter.mDisplayList.size(), 12);
        for (int i = 0; i < min; i++) {
            if (activeListAdapter.mDisplayList.get(i).getResolveInfo().activityInfo.packageName.equals(packageName)) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected boolean shouldAddFooterView() {
        return true;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void applyFooterView(int i) {
        int itemCount = this.mChooserMultiProfilePagerAdapter.getItemCount();
        for (int i2 = 0; i2 < itemCount; i2++) {
            this.mChooserMultiProfilePagerAdapter.getAdapterForIndex(i2).setFooterHeight(i);
        }
    }

    private android.content.IntentFilter getTargetIntentFilter() {
        try {
            android.content.Intent targetIntent = getTargetIntent();
            java.lang.String dataString = targetIntent.getDataString();
            if (targetIntent.getType() == null) {
                if (android.text.TextUtils.isEmpty(dataString)) {
                    android.util.Log.e(TAG, "Failed to get target intent filter: intent data and type are null");
                    return null;
                }
                return new android.content.IntentFilter(targetIntent.getAction(), dataString);
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter(targetIntent.getAction(), targetIntent.getType());
            java.util.ArrayList<android.net.Uri> arrayList = new java.util.ArrayList();
            if (android.content.Intent.ACTION_SEND.equals(targetIntent.getAction())) {
                android.net.Uri uri = (android.net.Uri) targetIntent.getParcelableExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
                if (uri != null) {
                    arrayList.add(uri);
                }
            } else {
                java.util.ArrayList parcelableArrayListExtra = targetIntent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, android.net.Uri.class);
                if (parcelableArrayListExtra != null) {
                    arrayList.addAll(parcelableArrayListExtra);
                }
            }
            for (android.net.Uri uri2 : arrayList) {
                intentFilter.addDataScheme(uri2.getScheme());
                intentFilter.addDataAuthority(uri2.getAuthority(), null);
                intentFilter.addDataPath(uri2.getPath(), 0);
            }
            return intentFilter;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to get target intent filter", e);
            return null;
        }
    }

    protected void queryDirectShareTargets(final com.android.internal.app.ChooserListAdapter chooserListAdapter, boolean z) {
        android.app.prediction.AppPredictor appPredictorForDirectShareIfEnabled;
        this.mQueriedSharingShortcutsTimeMs = java.lang.System.currentTimeMillis();
        final android.os.UserHandle userHandle = chooserListAdapter.getUserHandle();
        if (!z && (appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(userHandle)) != null) {
            appPredictorForDirectShareIfEnabled.requestPredictionUpdate();
            return;
        }
        final android.content.IntentFilter targetIntentFilter = getTargetIntentFilter();
        if (targetIntentFilter == null) {
            return;
        }
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.app.ChooserActivity.this.lambda$queryDirectShareTargets$3(userHandle, targetIntentFilter, chooserListAdapter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDirectShareTargets$3(android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, com.android.internal.app.ChooserListAdapter chooserListAdapter) {
        sendShareShortcutInfoList(((android.content.pm.ShortcutManager) createContextAsUser(userHandle, 0).getSystemService("shortcut")).getShareTargets(intentFilter), chooserListAdapter, null, userHandle);
    }

    private boolean shouldQueryShortcutManager(android.os.UserHandle userHandle) {
        if (shouldShowTabs() && getWorkProfileUserHandle().equals(userHandle)) {
            return isUserRunning(userHandle) && isUserUnlocked(userHandle) && !isQuietModeEnabled(userHandle);
        }
        return true;
    }

    private void sendShareShortcutInfoList(java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> list, com.android.internal.app.ChooserListAdapter chooserListAdapter, java.util.List<android.app.prediction.AppTarget> list2, android.os.UserHandle userHandle) {
        if (list2 != null && list2.size() != list.size()) {
            throw new java.lang.RuntimeException("resultList and appTargets must have the same size. resultList.size()=" + list.size() + " appTargets.size()=" + list2.size());
        }
        android.content.Context createContextAsUser = createContextAsUser(userHandle, 0);
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!isPackageEnabled(createContextAsUser, list.get(size).getTargetComponent().getPackageName())) {
                list.remove(size);
                if (list2 != null) {
                    list2.remove(size);
                }
            }
        }
        int i = list2 == null ? 2 : 3;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < chooserListAdapter.getDisplayResolveInfoCount(); i2++) {
            com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = chooserListAdapter.getDisplayResolveInfo(i2);
            java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> filterShortcutsByTargetComponentName = filterShortcutsByTargetComponentName(list, displayResolveInfo.getResolvedComponentName());
            if (!filterShortcutsByTargetComponentName.isEmpty()) {
                arrayList.add(new com.android.internal.app.ChooserActivity.ServiceResultInfo(displayResolveInfo, convertToChooserTarget(filterShortcutsByTargetComponentName, list, list2, i), userHandle));
            }
        }
        sendShortcutManagerShareTargetResults(i, (com.android.internal.app.ChooserActivity.ServiceResultInfo[]) arrayList.toArray(new com.android.internal.app.ChooserActivity.ServiceResultInfo[0]));
    }

    private java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> filterShortcutsByTargetComponentName(java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> list, android.content.ComponentName componentName) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.ShortcutManager.ShareShortcutInfo shareShortcutInfo : list) {
            if (componentName.equals(shareShortcutInfo.getTargetComponent())) {
                arrayList.add(shareShortcutInfo);
            }
        }
        return arrayList;
    }

    protected void sendShortcutManagerShareTargetResults(int i, com.android.internal.app.ChooserActivity.ServiceResultInfo[] serviceResultInfoArr) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 7;
        obtain.obj = serviceResultInfoArr;
        obtain.arg1 = i;
        this.mChooserHandler.sendMessage(obtain);
    }

    private boolean isPackageEnabled(android.content.Context context, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            return applicationInfo != null && applicationInfo.enabled && (applicationInfo.flags & 1073741824) == 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public java.util.List<android.service.chooser.ChooserTarget> convertToChooserTarget(java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> list, java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> list2, java.util.List<android.app.prediction.AppTarget> list3, int i) {
        float max;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i == 2) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int rank = list.get(i2).getShortcutInfo().getRank();
                if (!arrayList.contains(java.lang.Integer.valueOf(rank))) {
                    arrayList.add(java.lang.Integer.valueOf(rank));
                }
            }
            java.util.Collections.sort(arrayList);
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList(list.size());
        for (int i3 = 0; i3 < list.size(); i3++) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(i3).getShortcutInfo();
            int indexOf = list2.indexOf(list.get(i3));
            if (i == 3) {
                max = java.lang.Math.max(1.0f - (indexOf * 0.01f), 0.0f);
            } else {
                max = java.lang.Math.max(1.0f - (arrayList.indexOf(java.lang.Integer.valueOf(shortcutInfo.getRank())) * 0.01f), 0.0f);
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.content.Intent.EXTRA_SHORTCUT_ID, shortcutInfo.getId());
            android.service.chooser.ChooserTarget chooserTarget = new android.service.chooser.ChooserTarget(shortcutInfo.getLabel(), null, max, list.get(i3).getTargetComponent().m778clone(), bundle);
            arrayList2.add(chooserTarget);
            if (this.mDirectShareAppTargetCache != null && list3 != null) {
                this.mDirectShareAppTargetCache.put(chooserTarget, list3.get(indexOf));
            }
            if (this.mDirectShareShortcutInfoCache != null) {
                this.mDirectShareShortcutInfoCache.put(chooserTarget, shortcutInfo);
            }
        }
        java.util.Collections.sort(arrayList2, new java.util.Comparator() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda6
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                return com.android.internal.app.ChooserActivity.lambda$convertToChooserTarget$4((android.service.chooser.ChooserTarget) obj, (android.service.chooser.ChooserTarget) obj2);
            }
        });
        return arrayList2;
    }

    static /* synthetic */ int lambda$convertToChooserTarget$4(android.service.chooser.ChooserTarget chooserTarget, android.service.chooser.ChooserTarget chooserTarget2) {
        return -java.lang.Float.compare(chooserTarget.getScore(), chooserTarget2.getScore());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logDirectShareTargetReceived(int i) {
        getMetricsLogger().write(new android.metrics.LogMaker(i).setSubtype((int) (java.lang.System.currentTimeMillis() - this.mQueriedSharingShortcutsTimeMs)));
    }

    void updateModelAndChooserCounts(com.android.internal.app.chooser.TargetInfo targetInfo) {
        if (targetInfo != null && (targetInfo instanceof com.android.internal.app.chooser.MultiDisplayResolveInfo)) {
            targetInfo = ((com.android.internal.app.chooser.MultiDisplayResolveInfo) targetInfo).getSelectedTarget();
        }
        if (targetInfo != null) {
            sendClickToAppPredictor(targetInfo);
            android.content.pm.ResolveInfo resolveInfo = targetInfo.getResolveInfo();
            android.content.Intent targetIntent = getTargetIntent();
            if (resolveInfo == null || resolveInfo.activityInfo == null || targetIntent == null) {
                android.util.Log.d(TAG, "Can not log Chooser Counts of null ResovleInfo");
            } else {
                com.android.internal.app.ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
                if (activeListAdapter != null) {
                    sendImpressionToAppPredictor(targetInfo, activeListAdapter);
                    activeListAdapter.updateModel(targetInfo);
                    activeListAdapter.updateChooserCounts(resolveInfo.activityInfo.packageName, targetIntent.getAction(), resolveInfo.userHandle);
                }
                android.util.Log.d(TAG, "ResolveInfo Package is " + resolveInfo.activityInfo.packageName);
                android.util.Log.d(TAG, "Action to be updated is " + targetIntent.getAction());
            }
        }
        this.mIsSuccessfullySelected = true;
    }

    private void sendImpressionToAppPredictor(com.android.internal.app.chooser.TargetInfo targetInfo, com.android.internal.app.ChooserListAdapter chooserListAdapter) {
        android.app.prediction.AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle());
        if (appPredictorForDirectShareIfEnabled == null || (targetInfo instanceof com.android.internal.app.chooser.ChooserTargetInfo)) {
            return;
        }
        java.util.List<com.android.internal.app.chooser.ChooserTargetInfo> surfacedTargetInfo = chooserListAdapter.getSurfacedTargetInfo();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.internal.app.chooser.ChooserTargetInfo> it = surfacedTargetInfo.iterator();
        while (it.hasNext()) {
            android.service.chooser.ChooserTarget chooserTarget = it.next().getChooserTarget();
            android.content.ComponentName componentName = chooserTarget.getComponentName();
            if (this.mDirectShareShortcutInfoCache.containsKey(chooserTarget)) {
                arrayList.add(new android.app.prediction.AppTargetId(java.lang.String.format("%s/%s/%s", this.mDirectShareShortcutInfoCache.get(chooserTarget).getId(), componentName.flattenToString(), SHORTCUT_TARGET)));
            }
        }
        appPredictorForDirectShareIfEnabled.notifyLaunchLocationShown(LAUNCH_LOCATION_DIRECT_SHARE, arrayList);
    }

    private void sendClickToAppPredictor(com.android.internal.app.chooser.TargetInfo targetInfo) {
        android.app.prediction.AppTarget appTarget;
        android.app.prediction.AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle());
        if (appPredictorForDirectShareIfEnabled == null || !(targetInfo instanceof com.android.internal.app.chooser.ChooserTargetInfo)) {
            return;
        }
        android.service.chooser.ChooserTarget chooserTarget = ((com.android.internal.app.chooser.ChooserTargetInfo) targetInfo).getChooserTarget();
        if (this.mDirectShareAppTargetCache == null) {
            appTarget = null;
        } else {
            appTarget = this.mDirectShareAppTargetCache.get(chooserTarget);
        }
        if (appTarget != null) {
            appPredictorForDirectShareIfEnabled.notifyAppTargetEvent(new android.app.prediction.AppTargetEvent.Builder(appTarget, 1).setLaunchLocation(LAUNCH_LOCATION_DIRECT_SHARE).build());
        }
    }

    private android.app.prediction.AppPredictor createAppPredictor(android.os.UserHandle userHandle) {
        if (!this.mIsAppPredictorComponentAvailable) {
            return null;
        }
        if (getPersonalProfileUserHandle().equals(userHandle)) {
            if (this.mPersonalAppPredictor != null) {
                return this.mPersonalAppPredictor;
            }
        } else if (this.mWorkAppPredictor != null) {
            return this.mWorkAppPredictor;
        }
        android.content.Context createContextAsUser = createContextAsUser(userHandle, 0);
        android.content.IntentFilter targetIntentFilter = getTargetIntentFilter();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("intent_filter", targetIntentFilter);
        populateTextContent(bundle);
        android.app.prediction.AppPredictor createAppPredictionSession = ((android.app.prediction.AppPredictionManager) createContextAsUser.getSystemService(android.app.prediction.AppPredictionManager.class)).createAppPredictionSession(new android.app.prediction.AppPredictionContext.Builder(createContextAsUser).setUiSurface(APP_PREDICTION_SHARE_UI_SURFACE).setPredictedTargetCount(20).setExtras(bundle).build());
        if (getPersonalProfileUserHandle().equals(userHandle)) {
            this.mPersonalAppPredictor = createAppPredictionSession;
        } else {
            this.mWorkAppPredictor = createAppPredictionSession;
        }
        return createAppPredictionSession;
    }

    private void populateTextContent(android.os.Bundle bundle) {
        bundle.putString(SHARED_TEXT_KEY, getTargetIntent().getStringExtra(android.content.Intent.EXTRA_TEXT));
    }

    private android.app.prediction.AppPredictor getAppPredictorForDirectShareIfEnabled(android.os.UserHandle userHandle) {
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            return null;
        }
        return createAppPredictor(userHandle);
    }

    private android.app.prediction.AppPredictor getAppPredictorForShareActivitiesIfEnabled(android.os.UserHandle userHandle) {
        if (getCloneProfileUserHandle() == null) {
            return createAppPredictor(userHandle);
        }
        return null;
    }

    void onRefinementResult(com.android.internal.app.chooser.TargetInfo targetInfo, android.content.Intent intent) {
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        if (targetInfo == null) {
            android.util.Log.e(TAG, "Refinement result intent did not match any known targets; canceling");
        } else if (!checkTargetSourceIntent(targetInfo, intent)) {
            android.util.Log.e(TAG, "onRefinementResult: Selected target " + targetInfo + " cannot match refined source intent " + intent);
        } else {
            com.android.internal.app.chooser.TargetInfo cloneFilledIn = targetInfo.cloneFilledIn(intent, 0);
            if (super.onTargetSelected(cloneFilledIn, false)) {
                updateModelAndChooserCounts(cloneFilledIn);
                finish();
                return;
            }
        }
        onRefinementCanceled();
    }

    void onRefinementCanceled() {
        if (this.mRefinementResultReceiver != null) {
            this.mRefinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        finish();
    }

    boolean checkTargetSourceIntent(com.android.internal.app.chooser.TargetInfo targetInfo, android.content.Intent intent) {
        java.util.List<android.content.Intent> allSourceIntents = targetInfo.getAllSourceIntents();
        int size = allSourceIntents.size();
        for (int i = 0; i < size; i++) {
            if (allSourceIntents.get(i).filterEquals(intent)) {
                return true;
            }
        }
        return false;
    }

    static class AzInfoComparator implements java.util.Comparator<com.android.internal.app.chooser.DisplayResolveInfo> {
        java.util.Comparator<com.android.internal.app.chooser.DisplayResolveInfo> mComparator;

        AzInfoComparator(android.content.Context context) {
            this.mComparator = java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.internal.app.ChooserActivity$AzInfoComparator$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((com.android.internal.app.chooser.DisplayResolveInfo) obj).getDisplayLabel();
                }
            }, java.text.Collator.getInstance(context.getResources().getConfiguration().locale)).thenComparingInt(new java.util.function.ToIntFunction() { // from class: com.android.internal.app.ChooserActivity$AzInfoComparator$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int identifier;
                    identifier = com.android.internal.app.ResolverActivity.getResolveInfoUserHandle(((com.android.internal.app.chooser.DisplayResolveInfo) obj).getResolveInfo(), android.os.UserHandle.SYSTEM).getIdentifier();
                    return identifier;
                }
            });
        }

        @Override // java.util.Comparator
        public int compare(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo2) {
            return this.mComparator.compare(displayResolveInfo, displayResolveInfo2);
        }
    }

    protected com.android.internal.logging.MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    protected com.android.internal.app.ChooserActivityLogger getChooserActivityLogger() {
        if (this.mChooserActivityLogger == null) {
            this.mChooserActivityLogger = new com.android.internal.app.ChooserActivityLoggerImpl();
        }
        return this.mChooserActivityLogger;
    }

    public class ChooserListController extends com.android.internal.app.ResolverListController {
        public ChooserListController(android.content.Context context, android.content.pm.PackageManager packageManager, android.content.Intent intent, java.lang.String str, int i, android.os.UserHandle userHandle, com.android.internal.app.AbstractResolverComparator abstractResolverComparator, android.os.UserHandle userHandle2) {
            super(context, packageManager, intent, str, i, userHandle, abstractResolverComparator, userHandle2);
        }

        @Override // com.android.internal.app.ResolverListController
        boolean isComponentFiltered(android.content.ComponentName componentName) {
            if (com.android.internal.app.ChooserActivity.this.mFilteredComponentNames == null) {
                return false;
            }
            for (android.content.ComponentName componentName2 : com.android.internal.app.ChooserActivity.this.mFilteredComponentNames) {
                if (componentName.equals(componentName2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.internal.app.ResolverListController
        public boolean isComponentPinned(android.content.ComponentName componentName) {
            if (android.service.chooser.Flags.legacyChooserPinningRemoval()) {
                return false;
            }
            return com.android.internal.app.ChooserActivity.this.mPinnedSharedPrefs.getBoolean(componentName.flattenToString(), false);
        }

        @Override // com.android.internal.app.ResolverListController
        public boolean isFixedAtTop(android.content.ComponentName componentName) {
            return componentName != null && componentName.equals(com.android.internal.app.ChooserActivity.this.getNearbySharingComponent()) && com.android.internal.app.ChooserActivity.this.shouldNearbyShareBeFirstInRankedRow();
        }
    }

    public com.android.internal.app.ChooserActivity.ChooserGridAdapter createChooserGridAdapter(android.content.Context context, java.util.List<android.content.Intent> list, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list2, boolean z, android.os.UserHandle userHandle) {
        com.android.internal.app.ChooserListAdapter createChooserListAdapter = createChooserListAdapter(context, list, intentArr, list2, z, userHandle);
        com.android.internal.app.ResolverAppPredictorCallback createAppPredictorCallback = createAppPredictorCallback(createChooserListAdapter);
        android.app.prediction.AppPredictor.Callback asCallback = createAppPredictorCallback.asCallback();
        createChooserListAdapter.setAppPredictor(setupAppPredictorForUser(userHandle, asCallback));
        createChooserListAdapter.setAppPredictorCallback(asCallback, createAppPredictorCallback);
        return new com.android.internal.app.ChooserActivity.ChooserGridAdapter(createChooserListAdapter);
    }

    public com.android.internal.app.ChooserListAdapter createChooserListAdapter(android.content.Context context, java.util.List<android.content.Intent> list, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list2, boolean z, android.os.UserHandle userHandle) {
        android.os.UserHandle userHandle2;
        if (!isLaunchedAsCloneProfile() || !userHandle.equals(getPersonalProfileUserHandle())) {
            userHandle2 = userHandle;
        } else {
            userHandle2 = getCloneProfileUserHandle();
        }
        return new com.android.internal.app.ChooserListAdapter(context, list, intentArr, list2, z, createListController(userHandle), this, this, context.getPackageManager(), getChooserActivityLogger(), userHandle2);
    }

    @Override // com.android.internal.app.ResolverActivity
    protected com.android.internal.app.ResolverListController createListController(android.os.UserHandle userHandle) {
        com.android.internal.app.AbstractResolverComparator resolverRankerServiceResolverComparator;
        android.app.prediction.AppPredictor appPredictorForShareActivitiesIfEnabled = getAppPredictorForShareActivitiesIfEnabled(userHandle);
        if (appPredictorForShareActivitiesIfEnabled != null) {
            resolverRankerServiceResolverComparator = new com.android.internal.app.AppPredictionServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), appPredictorForShareActivitiesIfEnabled, userHandle, getChooserActivityLogger());
        } else {
            resolverRankerServiceResolverComparator = new com.android.internal.app.ResolverRankerServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), (com.android.internal.app.AbstractResolverComparator.AfterCompute) null, getChooserActivityLogger(), getResolverRankerServiceUserHandleList(userHandle));
        }
        android.os.UserHandle queryIntentsUser = getQueryIntentsUser(userHandle);
        return new com.android.internal.app.ChooserActivity.ChooserListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid, userHandle, resolverRankerServiceResolverComparator, queryIntentsUser == null ? userHandle : queryIntentsUser);
    }

    protected android.graphics.Bitmap loadThumbnail(android.net.Uri uri, android.util.Size size) {
        if (uri == null || size == null) {
            return null;
        }
        try {
            return getContentResolver().loadThumbnail(uri, size, null);
        } catch (java.io.IOException | java.lang.NullPointerException | java.lang.SecurityException e) {
            logContentPreviewWarning(uri);
            return null;
        }
    }

    static final class PlaceHolderTargetInfo extends com.android.internal.app.chooser.NotSelectableTargetInfo {
        PlaceHolderTargetInfo() {
        }

        @Override // com.android.internal.app.chooser.TargetInfo
        public android.graphics.drawable.Drawable getDisplayIcon(android.content.Context context) {
            android.graphics.drawable.AnimatedVectorDrawable animatedVectorDrawable = (android.graphics.drawable.AnimatedVectorDrawable) context.getDrawable(com.android.internal.R.drawable.chooser_direct_share_icon_placeholder);
            animatedVectorDrawable.start();
            return animatedVectorDrawable;
        }
    }

    protected static final class EmptyTargetInfo extends com.android.internal.app.chooser.NotSelectableTargetInfo {
        @Override // com.android.internal.app.chooser.TargetInfo
        public android.graphics.drawable.Drawable getDisplayIcon(android.content.Context context) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScroll(android.view.View view, int i, int i2, int i3, int i4) {
        if (this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter() != null) {
            this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().handleScroll(view, i2, i4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLayoutChange(android.view.View view, int i, final int i2, int i3, final int i4, int i5, int i6, int i7, int i8) {
        if (this.mChooserMultiProfilePagerAdapter == null) {
            return;
        }
        final com.android.internal.widget.RecyclerView activeAdapterView = this.mChooserMultiProfilePagerAdapter.getActiveAdapterView();
        final com.android.internal.app.ChooserActivity.ChooserGridAdapter currentRootAdapter = this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter();
        if (currentRootAdapter == null || activeAdapterView == null || activeAdapterView.computeVerticalScrollOffset() != 0) {
            return;
        }
        int paddingLeft = ((i3 - i) - view.getPaddingLeft()) - view.getPaddingRight();
        boolean z = currentRootAdapter.consumeLayoutRequest() || currentRootAdapter.calculateChooserTargetWidth(paddingLeft) || activeAdapterView.getAdapter() == null || paddingLeft != this.mCurrAvailableWidth;
        boolean equals = true ^ java.util.Objects.equals(this.mLastAppliedInsets, this.mSystemWindowInsets);
        if (z || equals || this.mLastNumberOfChildren != activeAdapterView.getChildCount()) {
            this.mCurrAvailableWidth = paddingLeft;
            if (z) {
                activeAdapterView.setAdapter(currentRootAdapter);
                ((com.android.internal.widget.GridLayoutManager) activeAdapterView.getLayoutManager()).setSpanCount(this.mMaxTargetsPerRow);
                updateTabPadding();
            }
            if (getProfileForUser(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle()) != findSelectedProfile()) {
                return;
            }
            if (this.mLastNumberOfChildren == activeAdapterView.getChildCount() && !equals) {
                return;
            }
            getMainThreadHandler().post(new java.lang.Runnable() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.app.ChooserActivity.this.lambda$handleLayoutChange$5(currentRootAdapter, i2, i4, activeAdapterView);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLayoutChange$5(com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter, int i, int i2, com.android.internal.widget.RecyclerView recyclerView) {
        if (this.mResolverDrawerLayout == null || chooserGridAdapter == null) {
            return;
        }
        this.mResolverDrawerLayout.setCollapsibleHeightReserved(calculateDrawerOffset(i, i2, recyclerView, chooserGridAdapter));
        this.mEnterTransitionAnimationDelegate.markOffsetCalculated();
        this.mLastAppliedInsets = this.mSystemWindowInsets;
    }

    private int calculateDrawerOffset(int i, int i2, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter) {
        int i3;
        int i4 = this.mSystemWindowInsets != null ? this.mSystemWindowInsets.bottom : 0;
        int systemRowCount = chooserGridAdapter.getSystemRowCount() + chooserGridAdapter.getProfileRowCount() + chooserGridAdapter.getServiceTargetRowCount() + chooserGridAdapter.getCallerAndRankedTargetRowCount();
        if (systemRowCount == 0) {
            systemRowCount = chooserGridAdapter.getRowCount();
        }
        if (systemRowCount == 0 && !shouldShowStickyContentPreview()) {
            return i4 + getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_max_collapsed_height);
        }
        android.view.View findViewById = findViewById(com.android.internal.R.id.content_preview_container);
        if (shouldShowStickyContentPreview() && isStickyContentPreviewShowing()) {
            i3 = findViewById.getHeight() + i4;
        } else {
            i3 = i4;
        }
        if (shouldShowTabs()) {
            i3 += findViewById(16908307).getHeight();
        }
        if (recyclerView.getVisibility() == 0) {
            int min = java.lang.Math.min(4, systemRowCount);
            boolean shouldShowExtraRow = shouldShowExtraRow(min);
            this.mLastNumberOfChildren = recyclerView.getChildCount();
            int childCount = recyclerView.getChildCount();
            int i5 = 0;
            int i6 = 0;
            while (true) {
                if (i5 >= childCount || min <= 0) {
                    break;
                }
                android.view.View childAt = recyclerView.getChildAt(i5);
                if (((com.android.internal.widget.GridLayoutManager.LayoutParams) childAt.getLayoutParams()).getSpanIndex() == 0) {
                    int height = childAt.getHeight();
                    i3 += height;
                    if (shouldShowExtraRow) {
                        i3 += height;
                    }
                    if (chooserGridAdapter.getTargetType(recyclerView.getChildAdapterPosition(childAt)) == 1) {
                        i6 = height;
                    }
                    min--;
                }
                i5++;
            }
            boolean z = getResources().getConfiguration().orientation == 1 && !isInMultiWindowMode();
            if (i6 != 0 && shouldShowContentPreview() && z) {
                i3 = java.lang.Math.min(i3, ((((i2 - i) - this.mResolverDrawerLayout.getAlwaysShowHeight()) - ((int) (i6 / DIRECT_SHARE_EXPANSION_RATE))) - (this.mSystemWindowInsets != null ? this.mSystemWindowInsets.top : 0)) - i4);
            }
        } else {
            android.view.ViewGroup activeEmptyStateView = getActiveEmptyStateView();
            if (activeEmptyStateView.getVisibility() == 0) {
                i3 += activeEmptyStateView.getHeight();
            }
        }
        return java.lang.Math.min(i3, i2 - i);
    }

    private boolean shouldShowExtraRow(int i) {
        return shouldShowTabs() && i == 1 && this.mChooserMultiProfilePagerAdapter.shouldShowEmptyStateScreen(this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter());
    }

    private int getProfileForUser(android.os.UserHandle userHandle) {
        if (userHandle.equals(getWorkProfileUserHandle())) {
            return 1;
        }
        return 0;
    }

    private android.view.ViewGroup getActiveEmptyStateView() {
        return this.mChooserMultiProfilePagerAdapter.getItem(this.mChooserMultiProfilePagerAdapter.getCurrentPage()).getEmptyStateView();
    }

    static class BaseChooserTargetComparator implements java.util.Comparator<android.service.chooser.ChooserTarget> {
        BaseChooserTargetComparator() {
        }

        @Override // java.util.Comparator
        public int compare(android.service.chooser.ChooserTarget chooserTarget, android.service.chooser.ChooserTarget chooserTarget2) {
            return (int) java.lang.Math.signum(chooserTarget2.getScore() - chooserTarget.getScore());
        }
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void onHandlePackagesChanged(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().notifyDataSetChanged();
        super.onHandlePackagesChanged(resolverListAdapter);
    }

    @Override // com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator
    public com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter makePresentationGetter(android.content.pm.ActivityInfo activityInfo) {
        return this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().makePresentationGetter(activityInfo);
    }

    @Override // com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator
    public android.content.Intent getReferrerFillInIntent() {
        return this.mReferrerFillInIntent;
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public int getMaxRankedTargets() {
        return this.mMaxTargetsPerRow * 2;
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public void sendListViewUpdateMessage(android.os.UserHandle userHandle) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 6;
        obtain.obj = userHandle;
        this.mChooserHandler.sendMessageDelayed(obtain, this.mListViewUpdateDelayMs);
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onListRebuilt(com.android.internal.app.ResolverListAdapter resolverListAdapter, boolean z) {
        setupScrollListener();
        com.android.internal.app.ChooserListAdapter chooserListAdapter = (com.android.internal.app.ChooserListAdapter) resolverListAdapter;
        if (chooserListAdapter.getUserHandle().equals(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle())) {
            this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().setAdapter(this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter());
            this.mChooserMultiProfilePagerAdapter.setupListAdapter(this.mChooserMultiProfilePagerAdapter.getCurrentPage());
        }
        if (chooserListAdapter.mDisplayList == null || chooserListAdapter.mDisplayList.isEmpty()) {
            chooserListAdapter.notifyDataSetChanged();
        } else {
            chooserListAdapter.updateAlphabeticalList();
        }
        if (z) {
            getChooserActivityLogger().logSharesheetAppLoadComplete();
            maybeQueryAdditionalPostProcessingTargets(chooserListAdapter);
            this.mLatencyTracker.onActionEnd(16);
        }
    }

    private void maybeQueryAdditionalPostProcessingTargets(com.android.internal.app.ChooserListAdapter chooserListAdapter) {
        if (android.app.ActivityManager.isLowRamDeviceStatic() || !shouldQueryShortcutManager(chooserListAdapter.getUserHandle())) {
            return;
        }
        android.util.Log.d(TAG, "querying direct share targets from ShortcutManager");
        queryDirectShareTargets(chooserListAdapter, false);
    }

    protected boolean isUserRunning(android.os.UserHandle userHandle) {
        return ((android.os.UserManager) getSystemService(android.os.UserManager.class)).isUserRunning(userHandle);
    }

    protected boolean isUserUnlocked(android.os.UserHandle userHandle) {
        return ((android.os.UserManager) getSystemService(android.os.UserManager.class)).isUserUnlocked(userHandle);
    }

    protected boolean isQuietModeEnabled(android.os.UserHandle userHandle) {
        return ((android.os.UserManager) getSystemService(android.os.UserManager.class)).isQuietModeEnabled(userHandle);
    }

    private void setupScrollListener() {
        if (this.mResolverDrawerLayout == null) {
            return;
        }
        final android.view.View findViewById = this.mResolverDrawerLayout.findViewById(shouldShowTabs() ? 16908307 : com.android.internal.R.id.chooser_header);
        final float elevation = findViewById.getElevation();
        final float dimensionPixelSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_header_scroll_elevation);
        this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().addOnScrollListener(new com.android.internal.widget.RecyclerView.OnScrollListener() { // from class: com.android.internal.app.ChooserActivity.4
            @Override // com.android.internal.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(com.android.internal.widget.RecyclerView recyclerView, int i) {
                if (i == 0) {
                    if (com.android.internal.app.ChooserActivity.this.mScrollStatus == 1) {
                        com.android.internal.app.ChooserActivity.this.mScrollStatus = 0;
                        com.android.internal.app.ChooserActivity.this.setHorizontalScrollingEnabled(true);
                        return;
                    }
                    return;
                }
                if (i == 1 && com.android.internal.app.ChooserActivity.this.mScrollStatus == 0) {
                    com.android.internal.app.ChooserActivity.this.mScrollStatus = 1;
                    com.android.internal.app.ChooserActivity.this.setHorizontalScrollingEnabled(false);
                }
            }

            @Override // com.android.internal.widget.RecyclerView.OnScrollListener
            public void onScrolled(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
                android.view.View findViewByPosition;
                if (recyclerView.getChildCount() > 0 && ((findViewByPosition = recyclerView.getLayoutManager().findViewByPosition(0)) == null || findViewByPosition.getTop() < 0)) {
                    findViewById.setElevation(dimensionPixelSize);
                } else {
                    findViewById.setElevation(elevation);
                }
            }
        });
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean isSendAction(android.content.Intent intent) {
        java.lang.String action;
        if (intent == null || (action = intent.getAction()) == null) {
            return false;
        }
        if (!android.content.Intent.ACTION_SEND.equals(action) && !android.content.Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            return false;
        }
        return true;
    }

    private boolean shouldShowStickyContentPreview() {
        return shouldShowStickyContentPreviewNoOrientationCheck() && !getResources().getBoolean(com.android.internal.R.bool.resolver_landscape_phone);
    }

    private boolean shouldShowStickyContentPreviewNoOrientationCheck() {
        return shouldShowTabs() && (this.mMultiProfilePagerAdapter.getListAdapterForUserHandle(android.os.UserHandle.of(android.os.UserHandle.myUserId())).getCount() > 0 || shouldShowStickyContentPreviewWhenEmpty()) && shouldShowContentPreview();
    }

    protected boolean shouldShowStickyContentPreviewWhenEmpty() {
        return false;
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean shouldShowContentPreview() {
        return isSendAction(getTargetIntent());
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean shouldShowServiceTargets() {
        return shouldShowContentPreview() && !android.app.ActivityManager.isLowRamDeviceStatic();
    }

    private void updateStickyContentPreview() {
        if (shouldShowStickyContentPreviewNoOrientationCheck()) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) findViewById(com.android.internal.R.id.content_preview_container);
            if (viewGroup.getChildCount() == 0) {
                viewGroup.addView(createContentPreviewView(viewGroup));
            }
        }
        if (shouldShowStickyContentPreview()) {
            showStickyContentPreview();
        } else {
            hideStickyContentPreview();
        }
    }

    private void showStickyContentPreview() {
        if (isStickyContentPreviewShowing()) {
            return;
        }
        ((android.view.ViewGroup) findViewById(com.android.internal.R.id.content_preview_container)).setVisibility(0);
    }

    private boolean isStickyContentPreviewShowing() {
        return ((android.view.ViewGroup) findViewById(com.android.internal.R.id.content_preview_container)).getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideStickyContentPreview() {
        if (!isStickyContentPreviewShowing()) {
            return;
        }
        ((android.view.ViewGroup) findViewById(com.android.internal.R.id.content_preview_container)).setVisibility(8);
    }

    private void logActionShareWithPreview() {
        getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_SHARE_WITH_PREVIEW).setSubtype(findPreferredContentPreview(getTargetIntent(), getContentResolver())));
    }

    private void startFinishAnimation() {
        android.view.View findRootView = findRootView();
        if (findRootView != null) {
            findRootView.startAnimation(new com.android.internal.app.ChooserActivity.FinishAnimation(this, findRootView));
        }
    }

    private boolean maybeCancelFinishAnimation() {
        android.view.View findRootView = findRootView();
        android.view.animation.Animation animation = findRootView == null ? null : findRootView.getAnimation();
        if (animation instanceof com.android.internal.app.ChooserActivity.FinishAnimation) {
            boolean hasEnded = animation.hasEnded();
            animation.cancel();
            findRootView.clearAnimation();
            return !hasEnded;
        }
        return false;
    }

    private android.view.View findRootView() {
        if (this.mContentView == null) {
            this.mContentView = findViewById(16908290);
        }
        return this.mContentView;
    }

    static abstract class ViewHolderBase extends com.android.internal.widget.RecyclerView.ViewHolder {
        private int mViewType;

        ViewHolderBase(android.view.View view, int i) {
            super(view);
            this.mViewType = i;
        }

        int getViewType() {
            return this.mViewType;
        }
    }

    final class ItemViewHolder extends com.android.internal.app.ChooserActivity.ViewHolderBase {
        int mListPosition;
        com.android.internal.app.ResolverListAdapter.ViewHolder mWrappedViewHolder;

        ItemViewHolder(android.view.View view, boolean z, int i) {
            super(view, i);
            this.mListPosition = -1;
            this.mWrappedViewHolder = new com.android.internal.app.ResolverListAdapter.ViewHolder(view);
            if (z) {
                view.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$ItemViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(android.view.View view2) {
                        com.android.internal.app.ChooserActivity.ItemViewHolder.this.lambda$new$0(view2);
                    }
                });
                view.setOnLongClickListener(new android.view.View.OnLongClickListener() { // from class: com.android.internal.app.ChooserActivity$ItemViewHolder$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(android.view.View view2) {
                        boolean lambda$new$1;
                        lambda$new$1 = com.android.internal.app.ChooserActivity.ItemViewHolder.this.lambda$new$1(view2);
                        return lambda$new$1;
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(android.view.View view) {
            com.android.internal.app.ChooserActivity.this.startSelected(this.mListPosition, false, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$1(android.view.View view) {
            com.android.internal.app.chooser.TargetInfo targetInfoForPosition = com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(this.mListPosition, true);
            if ((targetInfoForPosition instanceof com.android.internal.app.chooser.DisplayResolveInfo) && com.android.internal.app.ChooserActivity.this.shouldShowTargetDetails(targetInfoForPosition)) {
                com.android.internal.app.ChooserActivity.this.showTargetDetails((com.android.internal.app.chooser.DisplayResolveInfo) targetInfoForPosition);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowTargetDetails(com.android.internal.app.chooser.TargetInfo targetInfo) {
        if (android.service.chooser.Flags.legacyChooserPinningRemoval()) {
            return false;
        }
        android.content.ComponentName nearbySharingComponent = getNearbySharingComponent();
        return (targetInfo instanceof com.android.internal.app.chooser.SelectableTargetInfo) || ((targetInfo instanceof com.android.internal.app.chooser.DisplayResolveInfo) && !(nearbySharingComponent != null && nearbySharingComponent.equals(targetInfo.getResolvedComponentName()) && shouldNearbyShareBeFirstInRankedRow()));
    }

    static final class FooterViewHolder extends com.android.internal.app.ChooserActivity.ViewHolderBase {
        FooterViewHolder(android.view.View view, int i) {
            super(view, i);
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onButtonClick(android.view.View view) {
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void resetButtonBar() {
    }

    @Override // com.android.internal.app.ResolverActivity
    protected java.lang.String getMetricsCategory() {
        return "intent_chooser";
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void onProfileTabSelected() {
        this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().updateDirectShareExpansion();
        setVerticalScrollEnabled(true);
        if (this.mResolverDrawerLayout != null) {
            this.mResolverDrawerLayout.scrollNestedScrollableChildBackToTop();
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
        if (shouldShowTabs()) {
            this.mChooserMultiProfilePagerAdapter.setEmptyStateBottomOffset(windowInsets.getSystemWindowInsetBottom());
            this.mChooserMultiProfilePagerAdapter.setupContainerPadding(getActiveEmptyStateView().findViewById(com.android.internal.R.id.resolver_empty_state_container));
        }
        android.view.WindowInsets onApplyWindowInsets = super.onApplyWindowInsets(view, windowInsets);
        if (this.mResolverDrawerLayout != null) {
            this.mResolverDrawerLayout.requestLayout();
        }
        return onApplyWindowInsets;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHorizontalScrollingEnabled(boolean z) {
        ((com.android.internal.app.ResolverViewPager) findViewById(com.android.internal.R.id.profile_pager)).setSwipingEnabled(z);
    }

    private void setVerticalScrollEnabled(boolean z) {
        ((com.android.internal.app.ChooserGridLayoutManager) this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().getLayoutManager()).setVerticalScrollEnabled(z);
    }

    @Override // com.android.internal.app.ResolverActivity
    void onHorizontalSwipeStateChanged(int i) {
        if (i == 1) {
            if (this.mScrollStatus == 0) {
                this.mScrollStatus = 2;
                setVerticalScrollEnabled(false);
                return;
            }
            return;
        }
        if (i == 0 && this.mScrollStatus == 2) {
            this.mScrollStatus = 0;
            setVerticalScrollEnabled(true);
        }
    }

    public final class ChooserGridAdapter extends com.android.internal.widget.RecyclerView.Adapter<com.android.internal.widget.RecyclerView.ViewHolder> {
        private static final int NUM_EXPANSIONS_TO_HIDE_AZ_LABEL = 20;
        private static final int VIEW_TYPE_AZ_LABEL = 4;
        private static final int VIEW_TYPE_CALLER_AND_RANK = 5;
        private static final int VIEW_TYPE_CONTENT_PREVIEW = 2;
        private static final int VIEW_TYPE_DIRECT_SHARE = 0;
        private static final int VIEW_TYPE_FOOTER = 6;
        private static final int VIEW_TYPE_NORMAL = 1;
        private static final int VIEW_TYPE_PROFILE = 3;
        private com.android.internal.app.ChooserListAdapter mChooserListAdapter;
        private com.android.internal.app.ChooserActivity.DirectShareViewHolder mDirectShareViewHolder;
        private final android.view.LayoutInflater mLayoutInflater;
        private boolean mShowAzLabelIfPoss;
        private int mChooserTargetWidth = 0;
        private boolean mLayoutRequested = false;
        private int mFooterHeight = 0;

        ChooserGridAdapter(com.android.internal.app.ChooserListAdapter chooserListAdapter) {
            this.mChooserListAdapter = chooserListAdapter;
            this.mLayoutInflater = android.view.LayoutInflater.from(com.android.internal.app.ChooserActivity.this);
            this.mShowAzLabelIfPoss = com.android.internal.app.ChooserActivity.this.getNumSheetExpansions() < 20;
            chooserListAdapter.registerDataSetObserver(new android.database.DataSetObserver() { // from class: com.android.internal.app.ChooserActivity.ChooserGridAdapter.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    super.onChanged();
                    com.android.internal.app.ChooserActivity.ChooserGridAdapter.this.notifyDataSetChanged();
                }

                @Override // android.database.DataSetObserver
                public void onInvalidated() {
                    super.onInvalidated();
                    com.android.internal.app.ChooserActivity.ChooserGridAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setFooterHeight(int i) {
            this.mFooterHeight = i;
        }

        public boolean calculateChooserTargetWidth(int i) {
            int min;
            if (i == 0 || (min = java.lang.Math.min(com.android.internal.app.ChooserActivity.this.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_width), i) / com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow) == this.mChooserTargetWidth) {
                return false;
            }
            this.mChooserTargetWidth = min;
            return true;
        }

        public void hideContentPreview() {
            this.mLayoutRequested = true;
            notifyDataSetChanged();
        }

        public boolean consumeLayoutRequest() {
            boolean z = this.mLayoutRequested;
            this.mLayoutRequested = false;
            return z;
        }

        public int getRowCount() {
            return (int) (getSystemRowCount() + getProfileRowCount() + getServiceTargetRowCount() + getCallerAndRankedTargetRowCount() + getAzLabelRowCount() + java.lang.Math.ceil(this.mChooserListAdapter.getAlphaTargetCount() / com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow));
        }

        public int getSystemRowCount() {
            return (com.android.internal.app.ChooserActivity.this.shouldShowTabs() || !com.android.internal.app.ChooserActivity.this.shouldShowContentPreview() || this.mChooserListAdapter == null || this.mChooserListAdapter.getCount() == 0) ? 0 : 1;
        }

        public int getProfileRowCount() {
            return (com.android.internal.app.ChooserActivity.this.shouldShowTabs() || this.mChooserListAdapter.getOtherProfile() == null) ? 0 : 1;
        }

        public int getFooterRowCount() {
            return 1;
        }

        public int getCallerAndRankedTargetRowCount() {
            return (int) java.lang.Math.ceil((this.mChooserListAdapter.getCallerTargetCount() + this.mChooserListAdapter.getRankedTargetCount()) / com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow);
        }

        public int getServiceTargetRowCount() {
            return com.android.internal.app.ChooserActivity.this.shouldShowServiceTargets() ? 1 : 0;
        }

        public int getAzLabelRowCount() {
            return (!this.mShowAzLabelIfPoss || this.mChooserListAdapter.getAlphaTargetCount() <= 0) ? 0 : 1;
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemCount() {
            return getSystemRowCount() + getProfileRowCount() + getServiceTargetRowCount() + getCallerAndRankedTargetRowCount() + getAzLabelRowCount() + this.mChooserListAdapter.getAlphaTargetCount() + getFooterRowCount();
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public com.android.internal.widget.RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup viewGroup, int i) {
            switch (i) {
                case 0:
                case 5:
                    return createItemGroupViewHolder(i, viewGroup);
                case 1:
                    return com.android.internal.app.ChooserActivity.this.new ItemViewHolder(this.mChooserListAdapter.createView(viewGroup), true, i);
                case 2:
                    return com.android.internal.app.ChooserActivity.this.new ItemViewHolder(com.android.internal.app.ChooserActivity.this.createContentPreviewView(viewGroup), false, i);
                case 3:
                    return com.android.internal.app.ChooserActivity.this.new ItemViewHolder(createProfileView(viewGroup), false, i);
                case 4:
                    return com.android.internal.app.ChooserActivity.this.new ItemViewHolder(createAzLabelView(viewGroup), false, i);
                case 6:
                    android.widget.Space space = new android.widget.Space(viewGroup.getContext());
                    space.setLayoutParams(new com.android.internal.widget.RecyclerView.LayoutParams(-1, this.mFooterHeight));
                    return new com.android.internal.app.ChooserActivity.FooterViewHolder(space, i);
                default:
                    return null;
            }
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public void onBindViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
            switch (((com.android.internal.app.ChooserActivity.ViewHolderBase) viewHolder).getViewType()) {
                case 0:
                case 5:
                    bindItemGroupViewHolder(i, (com.android.internal.app.ChooserActivity.ItemGroupViewHolder) viewHolder);
                    break;
                case 1:
                    bindItemViewHolder(i, (com.android.internal.app.ChooserActivity.ItemViewHolder) viewHolder);
                    break;
            }
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            int systemRowCount = getSystemRowCount();
            if (systemRowCount > 0 && i < systemRowCount) {
                return 2;
            }
            int profileRowCount = getProfileRowCount();
            int i2 = systemRowCount + profileRowCount;
            if (profileRowCount > 0 && i < i2) {
                return 3;
            }
            int serviceTargetRowCount = getServiceTargetRowCount();
            int i3 = i2 + serviceTargetRowCount;
            if (serviceTargetRowCount > 0 && i < i3) {
                return 0;
            }
            int callerAndRankedTargetRowCount = getCallerAndRankedTargetRowCount();
            int i4 = i3 + callerAndRankedTargetRowCount;
            if (callerAndRankedTargetRowCount > 0 && i < i4) {
                return 5;
            }
            int azLabelRowCount = getAzLabelRowCount();
            int i5 = i4 + azLabelRowCount;
            if (azLabelRowCount <= 0 || i >= i5) {
                return i == getItemCount() - 1 ? 6 : 1;
            }
            return 4;
        }

        public int getTargetType(int i) {
            return this.mChooserListAdapter.getPositionTargetType(getListPosition(i));
        }

        private android.view.View createProfileView(android.view.ViewGroup viewGroup) {
            android.view.View inflate = this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_profile_row, viewGroup, false);
            com.android.internal.app.ChooserActivity.this.mProfileView = inflate.findViewById(com.android.internal.R.id.profile_button);
            android.view.View view = com.android.internal.app.ChooserActivity.this.mProfileView;
            final com.android.internal.app.ChooserActivity chooserActivity = com.android.internal.app.ChooserActivity.this;
            view.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view2) {
                    com.android.internal.app.ChooserActivity.this.onProfileClick(view2);
                }
            });
            com.android.internal.app.ChooserActivity.this.updateProfileViewButton();
            return inflate;
        }

        private android.view.View createAzLabelView(android.view.ViewGroup viewGroup) {
            return this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_az_label_row, viewGroup, false);
        }

        private com.android.internal.app.ChooserActivity.ItemGroupViewHolder loadViewsIntoGroup(final com.android.internal.app.ChooserActivity.ItemGroupViewHolder itemGroupViewHolder) {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(this.mChooserTargetWidth, 1073741824);
            int columnCount = itemGroupViewHolder.getColumnCount();
            boolean z = itemGroupViewHolder instanceof com.android.internal.app.ChooserActivity.DirectShareViewHolder;
            for (final int i = 0; i < columnCount; i++) {
                android.view.View createView = this.mChooserListAdapter.createView(itemGroupViewHolder.getRowByIndex(i));
                createView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity.ChooserGridAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(android.view.View view) {
                        com.android.internal.app.ChooserActivity.this.startSelected(itemGroupViewHolder.getItemIndex(i), false, true);
                    }
                });
                createView.setOnLongClickListener(new android.view.View.OnLongClickListener() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(android.view.View view) {
                        boolean lambda$loadViewsIntoGroup$0;
                        lambda$loadViewsIntoGroup$0 = com.android.internal.app.ChooserActivity.ChooserGridAdapter.this.lambda$loadViewsIntoGroup$0(itemGroupViewHolder, i, view);
                        return lambda$loadViewsIntoGroup$0;
                    }
                });
                itemGroupViewHolder.addView(i, createView);
                if (z) {
                    com.android.internal.app.ResolverListAdapter.ViewHolder viewHolder = (com.android.internal.app.ResolverListAdapter.ViewHolder) createView.getTag();
                    viewHolder.text.setLines(2);
                    viewHolder.text.setHorizontallyScrolling(false);
                    viewHolder.text2.setVisibility(8);
                }
                createView.measure(makeMeasureSpec2, makeMeasureSpec);
                setViewBounds(createView, createView.getMeasuredWidth(), createView.getMeasuredHeight());
            }
            android.view.ViewGroup viewGroup = itemGroupViewHolder.getViewGroup();
            itemGroupViewHolder.measure();
            setViewBounds(viewGroup, -1, itemGroupViewHolder.getMeasuredRowHeight());
            if (z) {
                com.android.internal.app.ChooserActivity.DirectShareViewHolder directShareViewHolder = (com.android.internal.app.ChooserActivity.DirectShareViewHolder) itemGroupViewHolder;
                setViewBounds(directShareViewHolder.getRow(0), -1, directShareViewHolder.getMinRowHeight());
                setViewBounds(directShareViewHolder.getRow(1), -1, directShareViewHolder.getMinRowHeight());
            }
            viewGroup.setTag(itemGroupViewHolder);
            return itemGroupViewHolder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$loadViewsIntoGroup$0(com.android.internal.app.ChooserActivity.ItemGroupViewHolder itemGroupViewHolder, int i, android.view.View view) {
            com.android.internal.app.chooser.TargetInfo targetInfoForPosition = this.mChooserListAdapter.targetInfoForPosition(itemGroupViewHolder.getItemIndex(i), true);
            if (com.android.internal.app.ChooserActivity.this.shouldShowTargetDetails(targetInfoForPosition)) {
                com.android.internal.app.ChooserActivity.this.showTargetDetails(targetInfoForPosition);
            }
            return true;
        }

        private void setViewBounds(android.view.View view, int i, int i2) {
            android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                view.setLayoutParams(new android.view.ViewGroup.LayoutParams(i, i2));
            } else {
                layoutParams.height = i2;
                layoutParams.width = i;
            }
        }

        com.android.internal.app.ChooserActivity.ItemGroupViewHolder createItemGroupViewHolder(int i, android.view.ViewGroup viewGroup) {
            if (i == 0) {
                android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_row_direct_share, viewGroup, false);
                android.view.ViewGroup viewGroup3 = (android.view.ViewGroup) this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_row, viewGroup2, false);
                android.view.ViewGroup viewGroup4 = (android.view.ViewGroup) this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_row, viewGroup2, false);
                viewGroup2.addView(viewGroup3);
                viewGroup2.addView(viewGroup4);
                java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList(viewGroup3, viewGroup4);
                int i2 = com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow;
                final com.android.internal.app.ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter = com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter;
                java.util.Objects.requireNonNull(chooserMultiProfilePagerAdapter);
                this.mDirectShareViewHolder = new com.android.internal.app.ChooserActivity.DirectShareViewHolder(viewGroup2, newArrayList, i2, i, new java.util.function.Supplier() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        return com.android.internal.app.ChooserMultiProfilePagerAdapter.this.getActiveListAdapter();
                    }
                });
                loadViewsIntoGroup(this.mDirectShareViewHolder);
                return this.mDirectShareViewHolder;
            }
            com.android.internal.app.ChooserActivity.SingleRowViewHolder singleRowViewHolder = new com.android.internal.app.ChooserActivity.SingleRowViewHolder((android.view.ViewGroup) this.mLayoutInflater.inflate(com.android.internal.R.layout.chooser_row, viewGroup, false), com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow, i);
            loadViewsIntoGroup(singleRowViewHolder);
            return singleRowViewHolder;
        }

        int getRowType(int i) {
            int positionTargetType = this.mChooserListAdapter.getPositionTargetType(i);
            if (positionTargetType == 0) {
                return 2;
            }
            if (getAzLabelRowCount() > 0 && positionTargetType == 3) {
                return 2;
            }
            return positionTargetType;
        }

        void bindItemViewHolder(int i, com.android.internal.app.ChooserActivity.ItemViewHolder itemViewHolder) {
            android.view.View view = itemViewHolder.itemView;
            int listPosition = getListPosition(i);
            itemViewHolder.mListPosition = listPosition;
            this.mChooserListAdapter.bindView(listPosition, view);
        }

        void bindItemGroupViewHolder(int i, com.android.internal.app.ChooserActivity.ItemGroupViewHolder itemGroupViewHolder) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) itemGroupViewHolder.itemView;
            int listPosition = getListPosition(i);
            int rowType = getRowType(listPosition);
            int columnCount = itemGroupViewHolder.getColumnCount();
            int i2 = (listPosition + columnCount) - 1;
            while (getRowType(i2) != rowType && i2 >= listPosition) {
                i2--;
            }
            if (i2 == listPosition && (this.mChooserListAdapter.getItem(listPosition) instanceof com.android.internal.app.ChooserActivity.EmptyTargetInfo)) {
                android.widget.TextView textView = (android.widget.TextView) viewGroup.findViewById(com.android.internal.R.id.chooser_row_text_option);
                if (textView.getVisibility() != 0) {
                    textView.setAlpha(0.0f);
                    textView.setVisibility(0);
                    textView.setText(com.android.internal.R.string.chooser_no_direct_share_targets);
                    android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
                    ofFloat.setInterpolator(new android.view.animation.DecelerateInterpolator(1.0f));
                    textView.setTranslationY(com.android.internal.app.ChooserActivity.this.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_row_text_option_translate));
                    android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(textView, "translationY", 0.0f);
                    ofFloat2.setInterpolator(new android.view.animation.DecelerateInterpolator(1.0f));
                    android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                    animatorSet.setDuration(200L);
                    animatorSet.setStartDelay(200L);
                    animatorSet.playTogether(ofFloat, ofFloat2);
                    animatorSet.start();
                }
            }
            for (int i3 = 0; i3 < columnCount; i3++) {
                android.view.View view = itemGroupViewHolder.getView(i3);
                int i4 = listPosition + i3;
                if (i4 <= i2) {
                    itemGroupViewHolder.setViewVisibility(i3, 0);
                    itemGroupViewHolder.setItemIndex(i3, i4);
                    this.mChooserListAdapter.bindView(itemGroupViewHolder.getItemIndex(i3), view);
                } else {
                    itemGroupViewHolder.setViewVisibility(i3, 4);
                }
            }
        }

        int getListPosition(int i) {
            int systemRowCount = i - (getSystemRowCount() + getProfileRowCount());
            int serviceTargetCount = this.mChooserListAdapter.getServiceTargetCount();
            int ceil = (int) java.lang.Math.ceil(serviceTargetCount / com.android.internal.app.ChooserActivity.this.getMaxRankedTargets());
            if (systemRowCount < ceil) {
                return systemRowCount * com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow;
            }
            int i2 = systemRowCount - ceil;
            int callerTargetCount = this.mChooserListAdapter.getCallerTargetCount() + this.mChooserListAdapter.getRankedTargetCount();
            int callerAndRankedTargetRowCount = getCallerAndRankedTargetRowCount();
            if (i2 < callerAndRankedTargetRowCount) {
                return serviceTargetCount + (i2 * com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow);
            }
            return callerTargetCount + serviceTargetCount + (i2 - (getAzLabelRowCount() + callerAndRankedTargetRowCount));
        }

        public void handleScroll(android.view.View view, int i, int i2) {
            boolean canExpandDirectShare = canExpandDirectShare();
            if (this.mDirectShareViewHolder != null && canExpandDirectShare) {
                this.mDirectShareViewHolder.handleScroll(com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveAdapterView(), i, i2, com.android.internal.app.ChooserActivity.this.mMaxTargetsPerRow);
            }
        }

        private boolean canExpandDirectShare() {
            return false;
        }

        public com.android.internal.app.ChooserListAdapter getListAdapter() {
            return this.mChooserListAdapter;
        }

        boolean shouldCellSpan(int i) {
            return getItemViewType(i) == 1;
        }

        void updateDirectShareExpansion() {
            if (this.mDirectShareViewHolder == null || !canExpandDirectShare()) {
                return;
            }
            com.android.internal.widget.RecyclerView activeAdapterView = com.android.internal.app.ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveAdapterView();
            if (com.android.internal.app.ChooserActivity.this.mResolverDrawerLayout.isCollapsed()) {
                this.mDirectShareViewHolder.collapse(activeAdapterView);
            } else {
                this.mDirectShareViewHolder.expand(activeAdapterView);
            }
        }
    }

    static abstract class ItemGroupViewHolder extends com.android.internal.app.ChooserActivity.ViewHolderBase {
        protected final android.view.View[] mCells;
        private final int mColumnCount;
        private int[] mItemIndices;
        protected int mMeasuredRowHeight;

        abstract android.view.ViewGroup addView(int i, android.view.View view);

        abstract android.view.ViewGroup getRow(int i);

        abstract android.view.ViewGroup getRowByIndex(int i);

        abstract android.view.ViewGroup getViewGroup();

        abstract void setViewVisibility(int i, int i2);

        ItemGroupViewHolder(int i, android.view.View view, int i2) {
            super(view, i2);
            this.mCells = new android.view.View[i];
            this.mItemIndices = new int[i];
            this.mColumnCount = i;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public void measure() {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            getViewGroup().measure(makeMeasureSpec, makeMeasureSpec);
            this.mMeasuredRowHeight = getViewGroup().getMeasuredHeight();
        }

        public int getMeasuredRowHeight() {
            return this.mMeasuredRowHeight;
        }

        public void setItemIndex(int i, int i2) {
            this.mItemIndices[i] = i2;
        }

        public int getItemIndex(int i) {
            return this.mItemIndices[i];
        }

        public android.view.View getView(int i) {
            return this.mCells[i];
        }
    }

    static class SingleRowViewHolder extends com.android.internal.app.ChooserActivity.ItemGroupViewHolder {
        private final android.view.ViewGroup mRow;

        SingleRowViewHolder(android.view.ViewGroup viewGroup, int i, int i2) {
            super(i, viewGroup, i2);
            this.mRow = viewGroup;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getViewGroup() {
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getRowByIndex(int i) {
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getRow(int i) {
            if (i == 0) {
                return this.mRow;
            }
            return null;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup addView(int i, android.view.View view) {
            this.mRow.addView(view);
            this.mCells[i] = view;
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void setViewVisibility(int i, int i2) {
            getView(i).setVisibility(i2);
        }
    }

    static class DirectShareViewHolder extends com.android.internal.app.ChooserActivity.ItemGroupViewHolder {
        private int mCellCountPerRow;
        private final boolean[] mCellVisibility;
        private int mDirectShareCurrHeight;
        private int mDirectShareMaxHeight;
        private int mDirectShareMinHeight;
        private boolean mHideDirectShareExpansion;
        private final java.util.function.Supplier<com.android.internal.app.ChooserListAdapter> mListAdapterSupplier;
        private final android.view.ViewGroup mParent;
        private final java.util.List<android.view.ViewGroup> mRows;

        DirectShareViewHolder(android.view.ViewGroup viewGroup, java.util.List<android.view.ViewGroup> list, int i, int i2, java.util.function.Supplier<com.android.internal.app.ChooserListAdapter> supplier) {
            super(list.size() * i, viewGroup, i2);
            this.mHideDirectShareExpansion = false;
            this.mDirectShareMinHeight = 0;
            this.mDirectShareCurrHeight = 0;
            this.mDirectShareMaxHeight = 0;
            this.mParent = viewGroup;
            this.mRows = list;
            this.mCellCountPerRow = i;
            this.mCellVisibility = new boolean[list.size() * i];
            java.util.Arrays.fill(this.mCellVisibility, true);
            this.mListAdapterSupplier = supplier;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup addView(int i, android.view.View view) {
            android.view.ViewGroup rowByIndex = getRowByIndex(i);
            rowByIndex.addView(view);
            this.mCells[i] = view;
            return rowByIndex;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getViewGroup() {
            return this.mParent;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getRowByIndex(int i) {
            return this.mRows.get(i / this.mCellCountPerRow);
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public android.view.ViewGroup getRow(int i) {
            return this.mRows.get(i);
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void measure() {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            getRow(0).measure(makeMeasureSpec, makeMeasureSpec);
            getRow(1).measure(makeMeasureSpec, makeMeasureSpec);
            this.mDirectShareMinHeight = getRow(0).getMeasuredHeight();
            this.mDirectShareCurrHeight = this.mDirectShareCurrHeight > 0 ? this.mDirectShareCurrHeight : this.mDirectShareMinHeight;
            this.mDirectShareMaxHeight = this.mDirectShareMinHeight * 2;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public int getMeasuredRowHeight() {
            return this.mDirectShareCurrHeight;
        }

        public int getMinRowHeight() {
            return this.mDirectShareMinHeight;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void setViewVisibility(int i, int i2) {
            final android.view.View view = getView(i);
            if (i2 == 0) {
                this.mCellVisibility[i] = true;
                view.setVisibility(i2);
                view.setAlpha(1.0f);
            } else if (i2 == 4 && this.mCellVisibility[i]) {
                this.mCellVisibility[i] = false;
                android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
                ofFloat.setDuration(200L);
                ofFloat.setInterpolator(new android.view.animation.AccelerateInterpolator(1.0f));
                ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.app.ChooserActivity.DirectShareViewHolder.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        view.setVisibility(4);
                    }
                });
                ofFloat.start();
            }
        }

        public void handleScroll(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, int i3) {
            if (this.mDirectShareCurrHeight == this.mDirectShareMinHeight) {
                if (this.mHideDirectShareExpansion) {
                    return;
                }
                if (this.mListAdapterSupplier.get().getSelectableServiceTargetCount() <= i3) {
                    this.mHideDirectShareExpansion = true;
                    return;
                }
            }
            int i4 = (int) ((i2 - i) * com.android.internal.app.ChooserActivity.DIRECT_SHARE_EXPANSION_RATE);
            int i5 = this.mDirectShareCurrHeight;
            int max = java.lang.Math.max(java.lang.Math.min(i4 + i5, this.mDirectShareMaxHeight), this.mDirectShareMinHeight);
            updateDirectShareRowHeight(recyclerView, max - i5, max);
        }

        void expand(com.android.internal.widget.RecyclerView recyclerView) {
            updateDirectShareRowHeight(recyclerView, this.mDirectShareMaxHeight - this.mDirectShareCurrHeight, this.mDirectShareMaxHeight);
        }

        void collapse(com.android.internal.widget.RecyclerView recyclerView) {
            updateDirectShareRowHeight(recyclerView, this.mDirectShareMinHeight - this.mDirectShareCurrHeight, this.mDirectShareMinHeight);
        }

        private void updateDirectShareRowHeight(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
            if (recyclerView == null || recyclerView.getChildCount() == 0 || i == 0) {
                return;
            }
            boolean z = false;
            for (int i3 = 0; i3 < recyclerView.getChildCount(); i3++) {
                android.view.View childAt = recyclerView.getChildAt(i3);
                if (z) {
                    childAt.offsetTopAndBottom(i);
                } else if (childAt.getTag() != null && (childAt.getTag() instanceof com.android.internal.app.ChooserActivity.DirectShareViewHolder)) {
                    childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(childAt.getWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
                    childAt.getLayoutParams().height = childAt.getMeasuredHeight();
                    childAt.layout(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getTop() + childAt.getMeasuredHeight());
                    z = true;
                }
            }
            if (z) {
                this.mDirectShareCurrHeight = i2;
            }
        }
    }

    public static class ServiceResultInfo {
        public final com.android.internal.app.chooser.DisplayResolveInfo originalTarget;
        public final java.util.List<android.service.chooser.ChooserTarget> resultTargets;
        public final android.os.UserHandle userHandle;

        public ServiceResultInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, java.util.List<android.service.chooser.ChooserTarget> list, android.os.UserHandle userHandle) {
            this.originalTarget = displayResolveInfo;
            this.resultTargets = list;
            this.userHandle = userHandle;
        }
    }

    static class ChooserTargetRankingInfo {
        public final java.util.List<android.app.prediction.AppTarget> scores;
        public final android.os.UserHandle userHandle;

        ChooserTargetRankingInfo(java.util.List<android.app.prediction.AppTarget> list, android.os.UserHandle userHandle) {
            this.scores = list;
            this.userHandle = userHandle;
        }
    }

    static class RefinementResultReceiver extends android.os.ResultReceiver {
        private com.android.internal.app.ChooserActivity mChooserActivity;
        private com.android.internal.app.chooser.TargetInfo mSelectedTarget;

        public RefinementResultReceiver(com.android.internal.app.ChooserActivity chooserActivity, com.android.internal.app.chooser.TargetInfo targetInfo, android.os.Handler handler) {
            super(handler);
            this.mChooserActivity = chooserActivity;
            this.mSelectedTarget = targetInfo;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            if (this.mChooserActivity == null) {
                android.util.Log.e(com.android.internal.app.ChooserActivity.TAG, "Destroyed RefinementResultReceiver received a result");
            }
            if (bundle == null) {
                android.util.Log.e(com.android.internal.app.ChooserActivity.TAG, "RefinementResultReceiver received null resultData");
                return;
            }
            switch (i) {
                case -1:
                    android.os.Parcelable parcelable = bundle.getParcelable(android.content.Intent.EXTRA_INTENT);
                    if (parcelable instanceof android.content.Intent) {
                        this.mChooserActivity.onRefinementResult(this.mSelectedTarget, (android.content.Intent) parcelable);
                        break;
                    } else {
                        android.util.Log.e(com.android.internal.app.ChooserActivity.TAG, "RefinementResultReceiver received RESULT_OK but no Intent in resultData with key Intent.EXTRA_INTENT");
                        break;
                    }
                case 0:
                    this.mChooserActivity.onRefinementCanceled();
                    break;
                default:
                    android.util.Log.w(com.android.internal.app.ChooserActivity.TAG, "Unknown result code " + i + " sent to RefinementResultReceiver");
                    break;
            }
        }

        public void destroy() {
            this.mChooserActivity = null;
            this.mSelectedTarget = null;
        }
    }

    public static class RoundedRectImageView extends android.widget.ImageView {
        private java.lang.String mExtraImageCount;
        private android.graphics.Paint mOverlayPaint;
        private android.graphics.Path mPath;
        private int mRadius;
        private android.graphics.Paint mRoundRectPaint;
        private android.graphics.Paint mTextPaint;

        public RoundedRectImageView(android.content.Context context) {
            super(context);
            this.mRadius = 0;
            this.mPath = new android.graphics.Path();
            this.mOverlayPaint = new android.graphics.Paint(0);
            this.mRoundRectPaint = new android.graphics.Paint(0);
            this.mTextPaint = new android.graphics.Paint(1);
            this.mExtraImageCount = null;
        }

        public RoundedRectImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public RoundedRectImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public RoundedRectImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mRadius = 0;
            this.mPath = new android.graphics.Path();
            this.mOverlayPaint = new android.graphics.Paint(0);
            this.mRoundRectPaint = new android.graphics.Paint(0);
            this.mTextPaint = new android.graphics.Paint(1);
            this.mExtraImageCount = null;
            this.mRadius = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_corner_radius);
            this.mOverlayPaint.setColor(-1728053248);
            this.mOverlayPaint.setStyle(android.graphics.Paint.Style.FILL);
            this.mRoundRectPaint.setColor(context.getResources().getColor(com.android.internal.R.color.chooser_row_divider));
            this.mRoundRectPaint.setStyle(android.graphics.Paint.Style.STROKE);
            this.mRoundRectPaint.setStrokeWidth(context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_preview_image_border));
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTextSize(context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.chooser_preview_image_font_size));
            this.mTextPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        }

        private void updatePath(int i, int i2) {
            this.mPath.reset();
            this.mPath.addRoundRect(getPaddingLeft(), getPaddingTop(), (i - getPaddingRight()) - getPaddingLeft(), (i2 - getPaddingBottom()) - getPaddingTop(), this.mRadius, this.mRadius, android.graphics.Path.Direction.CW);
        }

        public void setRadius(int i) {
            this.mRadius = i;
            updatePath(getWidth(), getHeight());
        }

        public void setExtraImageCount(int i) {
            if (i > 0) {
                this.mExtraImageCount = "+" + i;
            } else {
                this.mExtraImageCount = null;
            }
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            updatePath(i, i2);
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(android.graphics.Canvas canvas) {
            if (this.mRadius != 0) {
                canvas.clipPath(this.mPath);
            }
            super.onDraw(canvas);
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            if (this.mExtraImageCount != null) {
                canvas.drawRect(paddingLeft, paddingRight, width, height, this.mOverlayPaint);
                canvas.drawText(this.mExtraImageCount, canvas.getWidth() / 2, (int) ((canvas.getHeight() / 2.0f) - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f)), this.mTextPaint);
            }
            canvas.drawRoundRect(paddingLeft, paddingRight, width, height, this.mRadius, this.mRadius, this.mRoundRectPaint);
        }
    }

    private class EnterTransitionAnimationDelegate implements android.view.View.OnLayoutChangeListener {
        private boolean mOffsetCalculated;
        private boolean mPreviewReady;

        private EnterTransitionAnimationDelegate() {
            this.mPreviewReady = false;
            this.mOffsetCalculated = false;
        }

        void postponeTransition() {
            com.android.internal.app.ChooserActivity.this.postponeEnterTransition();
        }

        void markImagePreviewReady() {
            if (!this.mPreviewReady) {
                this.mPreviewReady = true;
                maybeStartListenForLayout();
            }
        }

        void markOffsetCalculated() {
            if (!this.mOffsetCalculated) {
                this.mOffsetCalculated = true;
                maybeStartListenForLayout();
            }
        }

        private void maybeStartListenForLayout() {
            if (this.mPreviewReady && this.mOffsetCalculated && com.android.internal.app.ChooserActivity.this.mResolverDrawerLayout != null) {
                if (com.android.internal.app.ChooserActivity.this.mResolverDrawerLayout.isInLayout()) {
                    com.android.internal.app.ChooserActivity.this.startPostponedEnterTransition();
                } else {
                    com.android.internal.app.ChooserActivity.this.mResolverDrawerLayout.addOnLayoutChangeListener(this);
                    com.android.internal.app.ChooserActivity.this.mResolverDrawerLayout.requestLayout();
                }
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            view.removeOnLayoutChangeListener(this);
            com.android.internal.app.ChooserActivity.this.startPostponedEnterTransition();
        }
    }

    private static class FinishAnimation extends android.view.animation.AlphaAnimation implements android.view.animation.Animation.AnimationListener {
        private android.app.Activity mActivity;
        private final float mFromAlpha;
        private android.view.View mRootView;

        FinishAnimation(android.app.Activity activity, android.view.View view) {
            super(view.getAlpha(), 0.0f);
            this.mActivity = activity;
            this.mRootView = view;
            this.mFromAlpha = view.getAlpha();
            setInterpolator(new android.view.animation.LinearInterpolator());
            long transitionBackgroundFadeDuration = activity.getWindow().getTransitionBackgroundFadeDuration();
            setDuration(transitionBackgroundFadeDuration);
            setStartOffset(transitionBackgroundFadeDuration);
            super.setAnimationListener(this);
        }

        @Override // android.view.animation.Animation
        public void setAnimationListener(android.view.animation.Animation.AnimationListener animationListener) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // android.view.animation.Animation
        public void cancel() {
            if (this.mRootView != null) {
                this.mRootView.setAlpha(this.mFromAlpha);
            }
            cleanup();
            super.cancel();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(android.view.animation.Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(android.view.animation.Animation animation) {
            android.app.Activity activity = this.mActivity;
            cleanup();
            if (activity != null) {
                activity.finish();
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(android.view.animation.Animation animation) {
        }

        private void cleanup() {
            this.mActivity = null;
            this.mRootView = null;
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void maybeLogProfileChange() {
        getChooserActivityLogger().logShareheetProfileChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldNearbyShareBeFirstInRankedRow() {
        return android.app.ActivityManager.isLowRamDeviceStatic() && this.mIsNearbyShareFirstTargetInRankedApp;
    }

    private boolean shouldNearbyShareBeIncludedAsActionButton() {
        return !shouldNearbyShareBeFirstInRankedRow();
    }
}
