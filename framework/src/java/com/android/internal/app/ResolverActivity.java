package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverActivity extends android.app.Activity implements com.android.internal.app.ResolverListAdapter.ResolverListCommunicator {
    private static final boolean DEBUG = false;
    public static boolean ENABLE_TABBED_VIEW = true;
    static final java.lang.String EXTRA_CALLING_USER = "com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER";
    private static final java.lang.String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    public static final java.lang.String EXTRA_IS_AUDIO_CAPTURE_DEVICE = "is_audio_capture_device";
    protected static final java.lang.String EXTRA_SELECTED_PROFILE = "com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE";
    private static final java.lang.String EXTRA_SHOW_FRAGMENT_ARGS = ":settings:show_fragment_args";
    private static final java.lang.String LAST_SHOWN_TAB_KEY = "last_shown_tab_key";
    protected static final java.lang.String METRICS_CATEGORY_CHOOSER = "intent_chooser";
    protected static final java.lang.String METRICS_CATEGORY_RESOLVER = "intent_resolver";
    private static final java.lang.String OPEN_LINKS_COMPONENT_KEY = "app_link_state";
    protected static final int PROFILE_PERSONAL = 0;
    protected static final int PROFILE_WORK = 1;
    private static final java.lang.String TAB_TAG_PERSONAL = "personal";
    private static final java.lang.String TAB_TAG_WORK = "work";
    private static final java.lang.String TAG = "ResolverActivity";
    private android.widget.Button mAlwaysButton;
    private android.os.UserHandle mCloneProfileUserHandle;
    private int mDefaultTitleResId;
    private android.widget.Space mFooterSpacer;
    private android.os.UserHandle mHeaderCreatorUser;
    protected final java.util.ArrayList<android.content.Intent> mIntents;
    private final boolean mIsIntentPicker;
    private int mLastSelected;
    protected final com.android.internal.util.LatencyTracker mLatencyTracker;
    protected int mLaunchedFromUid;
    private android.os.UserHandle mLaunchedFromUserHandle;
    private int mLayoutId;
    protected com.android.internal.app.AbstractMultiProfilePagerAdapter mMultiProfilePagerAdapter;
    private com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener mOnSwitchOnWorkSelectedListener;
    private android.widget.Button mOnceButton;
    private com.android.internal.content.PackageMonitor mPersonalPackageMonitor;
    private android.os.UserHandle mPersonalProfileUserHandle;
    private com.android.internal.app.ResolverActivity.PickTargetOptionRequest mPickOptionRequest;
    protected android.content.pm.PackageManager mPm;
    private android.os.UserHandle mPrivateProfileUserHandle;
    private java.lang.String mProfileSwitchMessage;
    protected android.view.View mProfileView;
    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager mQuietModeManager;
    private java.lang.String mReferrerPackage;
    private boolean mRegistered;
    protected com.android.internal.widget.ResolverDrawerLayout mResolverDrawerLayout;
    private boolean mResolvingHome;
    private boolean mRetainInOnStop;
    private boolean mSafeForwardingMode;
    protected boolean mSupportsAlwaysUseOption;
    protected android.graphics.Insets mSystemWindowInsets;
    private android.os.UserHandle mTabOwnerUserHandleForLaunch;
    private java.lang.CharSequence mTitle;
    private com.android.internal.content.PackageMonitor mWorkPackageMonitor;
    private boolean mWorkProfileHasBeenEnabled;
    private android.content.BroadcastReceiver mWorkProfileStateReceiver;
    private android.os.UserHandle mWorkProfileUserHandle;

    public ResolverActivity() {
        this.mLastSelected = -1;
        this.mResolvingHome = false;
        this.mIntents = new java.util.ArrayList<>();
        this.mSystemWindowInsets = null;
        this.mFooterSpacer = null;
        this.mWorkProfileHasBeenEnabled = false;
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = getClass().equals(com.android.internal.app.ResolverActivity.class);
    }

    protected ResolverActivity(boolean z) {
        this.mLastSelected = -1;
        this.mResolvingHome = false;
        this.mIntents = new java.util.ArrayList<>();
        this.mSystemWindowInsets = null;
        this.mFooterSpacer = null;
        this.mWorkProfileHasBeenEnabled = false;
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = z;
    }

    private com.android.internal.util.LatencyTracker getLatencyTracker() {
        return com.android.internal.util.LatencyTracker.getInstance(this);
    }

    public static int getLabelRes(java.lang.String str) {
        return com.android.internal.app.ResolverActivity.ActionTitle.forAction(str).labelRes;
    }

    private enum ActionTitle {
        VIEW("android.intent.action.VIEW", com.android.internal.R.string.whichViewApplication, com.android.internal.R.string.whichViewApplicationNamed, com.android.internal.R.string.whichViewApplicationLabel),
        EDIT(android.content.Intent.ACTION_EDIT, com.android.internal.R.string.whichEditApplication, com.android.internal.R.string.whichEditApplicationNamed, com.android.internal.R.string.whichEditApplicationLabel),
        SEND(android.content.Intent.ACTION_SEND, com.android.internal.R.string.whichSendApplication, com.android.internal.R.string.whichSendApplicationNamed, com.android.internal.R.string.whichSendApplicationLabel),
        SENDTO(android.content.Intent.ACTION_SENDTO, com.android.internal.R.string.whichSendToApplication, com.android.internal.R.string.whichSendToApplicationNamed, com.android.internal.R.string.whichSendToApplicationLabel),
        SEND_MULTIPLE(android.content.Intent.ACTION_SEND_MULTIPLE, com.android.internal.R.string.whichSendApplication, com.android.internal.R.string.whichSendApplicationNamed, com.android.internal.R.string.whichSendApplicationLabel),
        CAPTURE_IMAGE("android.media.action.IMAGE_CAPTURE", com.android.internal.R.string.whichImageCaptureApplication, com.android.internal.R.string.whichImageCaptureApplicationNamed, com.android.internal.R.string.whichImageCaptureApplicationLabel),
        DEFAULT(null, com.android.internal.R.string.whichApplication, com.android.internal.R.string.whichApplicationNamed, com.android.internal.R.string.whichApplicationLabel),
        HOME(android.content.Intent.ACTION_MAIN, com.android.internal.R.string.whichHomeApplication, com.android.internal.R.string.whichHomeApplicationNamed, com.android.internal.R.string.whichHomeApplicationLabel);

        public static final int BROWSABLE_APP_TITLE_RES = 17041930;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17041928;
        public static final int BROWSABLE_HOST_TITLE_RES = 17041927;
        public static final int BROWSABLE_TITLE_RES = 17041929;
        public final java.lang.String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        ActionTitle(java.lang.String str, int i, int i2, int i3) {
            this.action = str;
            this.titleRes = i;
            this.namedTitleRes = i2;
            this.labelRes = i3;
        }

        public static com.android.internal.app.ResolverActivity.ActionTitle forAction(java.lang.String str) {
            for (com.android.internal.app.ResolverActivity.ActionTitle actionTitle : values()) {
                if (actionTitle != HOME && str != null && str.equals(actionTitle.action)) {
                    return actionTitle;
                }
            }
            return DEFAULT;
        }
    }

    protected com.android.internal.content.PackageMonitor createPackageMonitor(final com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        return new com.android.internal.content.PackageMonitor() { // from class: com.android.internal.app.ResolverActivity.1
            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                resolverListAdapter.handlePackagesChanged();
                com.android.internal.app.ResolverActivity.this.updateProfileViewButton();
            }

            @Override // com.android.internal.content.PackageMonitor
            public boolean onPackageChanged(java.lang.String str, int i, java.lang.String[] strArr) {
                return true;
            }
        };
    }

    private android.content.Intent makeMyIntent() {
        android.content.Intent intent = new android.content.Intent(getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & (-8388609));
        if ((intent.getFlags() & 4096) != 0) {
            intent.setFlags(intent.getFlags() & (-4097));
        }
        return intent;
    }

    protected void super_onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        android.content.Intent makeMyIntent = makeMyIntent();
        java.util.Set<java.lang.String> categories = makeMyIntent.getCategories();
        if (android.content.Intent.ACTION_MAIN.equals(makeMyIntent.getAction()) && categories != null && categories.size() == 1 && categories.contains(android.content.Intent.CATEGORY_HOME)) {
            this.mResolvingHome = true;
        }
        setSafeForwardingMode(true);
        onCreate(bundle, makeMyIntent, null, 0, null, null, true);
    }

    protected void onCreate(android.os.Bundle bundle, android.content.Intent intent, java.lang.CharSequence charSequence, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        onCreate(bundle, intent, charSequence, 0, intentArr, list, z);
    }

    protected void onCreate(android.os.Bundle bundle, android.content.Intent intent, java.lang.CharSequence charSequence, int i, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        int i2;
        setTheme(appliedThemeResId());
        super.onCreate(bundle);
        this.mQuietModeManager = createQuietModeManager();
        setProfileSwitchMessage(intent.getContentUserHint());
        this.mLaunchedFromUid = getLaunchedFromUid();
        this.mLaunchedFromUserHandle = android.os.UserHandle.getUserHandleForUid(this.mLaunchedFromUid);
        if (this.mLaunchedFromUid < 0 || android.os.UserHandle.isIsolated(this.mLaunchedFromUid)) {
            finish();
            return;
        }
        this.mPm = getPackageManager();
        this.mReferrerPackage = getReferrerPackageName();
        this.mIntents.add(0, new android.content.Intent(intent));
        this.mTitle = charSequence;
        this.mDefaultTitleResId = i;
        this.mSupportsAlwaysUseOption = z;
        this.mPersonalProfileUserHandle = fetchPersonalProfileUserHandle();
        this.mWorkProfileUserHandle = fetchWorkProfileUserProfile();
        this.mCloneProfileUserHandle = fetchCloneProfileUserHandle();
        this.mPrivateProfileUserHandle = fetchPrivateProfileUserHandle();
        this.mTabOwnerUserHandleForLaunch = fetchTabOwnerUserHandleForLaunch();
        this.mMultiProfilePagerAdapter = createMultiProfilePagerAdapter(intentArr, list, (!this.mSupportsAlwaysUseOption || isVoiceInteraction() || shouldShowTabs() || hasCloneProfile()) ? false : true);
        if (configureContentView()) {
            return;
        }
        this.mPersonalPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getPersonalListAdapter());
        this.mPersonalPackageMonitor.register(this, getMainLooper(), getPersonalProfileUserHandle(), false);
        if (shouldShowTabs()) {
            this.mWorkPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getWorkListAdapter());
            this.mWorkPackageMonitor.register(this, getMainLooper(), getWorkProfileUserHandle(), false);
        }
        this.mRegistered = true;
        com.android.internal.widget.ResolverDrawerLayout resolverDrawerLayout = (com.android.internal.widget.ResolverDrawerLayout) findViewById(com.android.internal.R.id.contentPanel);
        if (resolverDrawerLayout != null) {
            resolverDrawerLayout.setOnDismissedListener(new com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.app.ResolverActivity.2
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public void onDismissed() {
                    com.android.internal.app.ResolverActivity.this.finish();
                }
            });
            boolean hasSystemFeature = getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_TOUCHSCREEN);
            if (isVoiceInteraction() || !hasSystemFeature) {
                resolverDrawerLayout.setCollapsed(false);
            }
            resolverDrawerLayout.setSystemUiVisibility(768);
            resolverDrawerLayout.setOnApplyWindowInsetsListener(new android.view.View.OnApplyWindowInsetsListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda13
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
                    return com.android.internal.app.ResolverActivity.this.onApplyWindowInsets(view, windowInsets);
                }
            });
            this.mResolverDrawerLayout = resolverDrawerLayout;
            int count = this.mMultiProfilePagerAdapter.getCount();
            for (int i3 = 0; i3 < count; i3++) {
                android.view.View findViewById = this.mMultiProfilePagerAdapter.getItem(i3).rootView.findViewById(com.android.internal.R.id.resolver_list);
                if (findViewById != null) {
                    findViewById.setAccessibilityDelegate(new com.android.internal.app.ResolverActivity.AppListAccessibilityDelegate(resolverDrawerLayout));
                }
            }
        }
        this.mProfileView = findViewById(com.android.internal.R.id.profile_button);
        if (this.mProfileView != null) {
            this.mProfileView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda14
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.app.ResolverActivity.this.onProfileClick(view);
                }
            });
            updateProfileViewButton();
        }
        java.util.Set<java.lang.String> categories = intent.getCategories();
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) {
            i2 = 451;
        } else {
            i2 = 453;
        }
        com.android.internal.logging.MetricsLogger.action(this, i2, intent.getAction() + ":" + intent.getType() + ":" + (categories != null ? java.util.Arrays.toString(categories.toArray()) : ""));
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter createMultiProfilePagerAdapter(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        if (shouldShowTabs()) {
            return createResolverMultiProfilePagerAdapterForTwoProfiles(intentArr, list, z);
        }
        return createResolverMultiProfilePagerAdapterForOneProfile(intentArr, list, z);
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.MyUserIdProvider createMyUserIdProvider() {
        return new com.android.internal.app.AbstractMultiProfilePagerAdapter.MyUserIdProvider();
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker createCrossProfileIntentsChecker() {
        return new com.android.internal.app.AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker(getContentResolver());
    }

    /* renamed from: com.android.internal.app.ResolverActivity$3, reason: invalid class name */
    class AnonymousClass3 implements com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager {
        private boolean mIsWaitingToEnableWorkProfile = false;
        final /* synthetic */ android.os.UserManager val$userManager;

        AnonymousClass3(android.os.UserManager userManager) {
            this.val$userManager = userManager;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public boolean isQuietModeEnabled(android.os.UserHandle userHandle) {
            return this.val$userManager.isQuietModeEnabled(userHandle);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public void requestQuietModeEnabled(final boolean z, final android.os.UserHandle userHandle) {
            java.util.concurrent.Executor executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;
            final android.os.UserManager userManager = this.val$userManager;
            executor.execute(new java.lang.Runnable() { // from class: com.android.internal.app.ResolverActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.UserManager.this.requestQuietModeEnabled(z, userHandle);
                }
            });
            this.mIsWaitingToEnableWorkProfile = true;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public void markWorkProfileEnabledBroadcastReceived() {
            this.mIsWaitingToEnableWorkProfile = false;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public boolean isWaitingToEnableWorkProfile() {
            return this.mIsWaitingToEnableWorkProfile;
        }
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager createQuietModeManager() {
        return new com.android.internal.app.ResolverActivity.AnonymousClass3((android.os.UserManager) getSystemService(android.os.UserManager.class));
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        if (!getUser().equals(getIntentUser())) {
            return new com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider() { // from class: com.android.internal.app.ResolverActivity.4
            };
        }
        return new com.android.internal.app.NoCrossProfileEmptyStateProvider(getPersonalProfileUserHandle(), new com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, com.android.internal.R.string.resolver_cross_profile_blocked, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_PERSONAL, com.android.internal.R.string.resolver_cant_access_personal_apps_explanation, 158, METRICS_CATEGORY_RESOLVER), new com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, com.android.internal.R.string.resolver_cross_profile_blocked, android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_WORK, com.android.internal.R.string.resolver_cant_access_work_apps_explanation, 159, METRICS_CATEGORY_RESOLVER), createCrossProfileIntentsChecker(), getTabOwnerUserHandleForLaunch());
    }

    protected com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider createEmptyStateProvider(android.os.UserHandle userHandle) {
        return new com.android.internal.app.AbstractMultiProfilePagerAdapter.CompositeEmptyStateProvider(createBlockerEmptyStateProvider(), new com.android.internal.app.WorkProfilePausedEmptyStateProvider(this, userHandle, this.mQuietModeManager, new com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda12
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                com.android.internal.app.ResolverActivity.this.lambda$createEmptyStateProvider$0();
            }
        }, getMetricsCategory()), new com.android.internal.app.NoAppsAvailableEmptyStateProvider(this, userHandle, getPersonalProfileUserHandle(), getMetricsCategory(), getTabOwnerUserHandleForLaunch()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEmptyStateProvider$0() {
        if (this.mOnSwitchOnWorkSelectedListener != null) {
            this.mOnSwitchOnWorkSelectedListener.onSwitchOnWorkSelected();
        }
    }

    private com.android.internal.app.ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForOneProfile(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        return new com.android.internal.app.ResolverMultiProfilePagerAdapter(this, createResolverListAdapter(this, this.mIntents, intentArr, list, z, getPersonalProfileUserHandle()), createEmptyStateProvider(null), createQuietModeManager(), null, getCloneProfileUserHandle());
    }

    private android.os.UserHandle getIntentUser() {
        if (getIntent().hasExtra(EXTRA_CALLING_USER)) {
            return (android.os.UserHandle) getIntent().getParcelableExtra(EXTRA_CALLING_USER, android.os.UserHandle.class);
        }
        return getUser();
    }

    private com.android.internal.app.ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForTwoProfiles(android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        int i;
        int currentProfile = getCurrentProfile();
        android.os.UserHandle intentUser = getIntentUser();
        if (!getTabOwnerUserHandleForLaunch().equals(intentUser)) {
            if (getPersonalProfileUserHandle().equals(intentUser)) {
                i = 0;
            } else {
                if (getWorkProfileUserHandle().equals(intentUser)) {
                    i = 1;
                }
                i = currentProfile;
            }
        } else {
            int selectedProfileExtra = getSelectedProfileExtra();
            if (selectedProfileExtra != -1) {
                i = selectedProfileExtra;
            }
            i = currentProfile;
        }
        com.android.internal.app.ResolverListAdapter createResolverListAdapter = createResolverListAdapter(this, this.mIntents, i == 0 ? intentArr : null, list, z && android.os.UserHandle.myUserId() == getPersonalProfileUserHandle().getIdentifier(), getPersonalProfileUserHandle());
        android.os.UserHandle workProfileUserHandle = getWorkProfileUserHandle();
        return new com.android.internal.app.ResolverMultiProfilePagerAdapter(this, createResolverListAdapter, createResolverListAdapter(this, this.mIntents, i == 1 ? intentArr : null, list, z && android.os.UserHandle.myUserId() == workProfileUserHandle.getIdentifier(), workProfileUserHandle), createEmptyStateProvider(getWorkProfileUserHandle()), createQuietModeManager(), i, getWorkProfileUserHandle(), getCloneProfileUserHandle());
    }

    protected int appliedThemeResId() {
        return com.android.internal.R.style.Theme_DeviceDefault_Resolver;
    }

    int getSelectedProfileExtra() {
        int i = -1;
        if (getIntent().hasExtra(EXTRA_SELECTED_PROFILE) && (i = getIntent().getIntExtra(EXTRA_SELECTED_PROFILE, -1)) != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE has invalid value " + i + ". Must be either ResolverActivity.PROFILE_PERSONAL or ResolverActivity.PROFILE_WORK.");
        }
        return i;
    }

    protected int getCurrentProfile() {
        return android.os.UserHandle.myUserId() == getPersonalProfileUserHandle().getIdentifier() ? 0 : 1;
    }

    protected android.os.UserHandle getPersonalProfileUserHandle() {
        if (privateSpaceEnabled() && isLaunchedAsPrivateProfile()) {
            return this.mPrivateProfileUserHandle;
        }
        return this.mPersonalProfileUserHandle;
    }

    protected android.os.UserHandle getWorkProfileUserHandle() {
        return this.mWorkProfileUserHandle;
    }

    protected android.os.UserHandle getCloneProfileUserHandle() {
        return this.mCloneProfileUserHandle;
    }

    protected android.os.UserHandle getTabOwnerUserHandleForLaunch() {
        return this.mTabOwnerUserHandleForLaunch;
    }

    protected android.os.UserHandle getPrivateProfileUserHandle() {
        return this.mPrivateProfileUserHandle;
    }

    protected android.os.UserHandle fetchPersonalProfileUserHandle() {
        this.mPersonalProfileUserHandle = android.os.UserHandle.of(android.app.ActivityManager.getCurrentUser());
        return this.mPersonalProfileUserHandle;
    }

    protected android.os.UserHandle fetchWorkProfileUserProfile() {
        this.mWorkProfileUserHandle = null;
        for (android.content.pm.UserInfo userInfo : ((android.os.UserManager) getSystemService(android.os.UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isManagedProfile()) {
                this.mWorkProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mWorkProfileUserHandle;
    }

    protected android.os.UserHandle fetchCloneProfileUserHandle() {
        this.mCloneProfileUserHandle = null;
        for (android.content.pm.UserInfo userInfo : ((android.os.UserManager) getSystemService(android.os.UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isCloneProfile()) {
                this.mCloneProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mCloneProfileUserHandle;
    }

    protected android.os.UserHandle fetchPrivateProfileUserHandle() {
        this.mPrivateProfileUserHandle = null;
        java.util.Iterator<android.content.pm.UserInfo> it = ((android.os.UserManager) getSystemService(android.os.UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            android.content.pm.UserInfo next = it.next();
            if (next.isPrivateProfile()) {
                this.mPrivateProfileUserHandle = next.getUserHandle();
                break;
            }
        }
        return this.mPrivateProfileUserHandle;
    }

    private android.os.UserHandle fetchTabOwnerUserHandleForLaunch() {
        if (android.os.UserHandle.of(android.os.UserHandle.myUserId()).equals(getWorkProfileUserHandle())) {
            return getWorkProfileUserHandle();
        }
        if (privateSpaceEnabled() && isLaunchedAsPrivateProfile()) {
            return getPrivateProfileUserHandle();
        }
        return getPersonalProfileUserHandle();
    }

    private boolean hasWorkProfile() {
        return getWorkProfileUserHandle() != null;
    }

    private boolean hasCloneProfile() {
        return getCloneProfileUserHandle() != null;
    }

    protected final boolean isLaunchedAsCloneProfile() {
        return hasCloneProfile() && android.os.UserHandle.myUserId() == getCloneProfileUserHandle().getIdentifier();
    }

    protected final boolean isLaunchedAsPrivateProfile() {
        return getPrivateProfileUserHandle() != null && android.os.UserHandle.myUserId() == getPrivateProfileUserHandle().getIdentifier();
    }

    protected boolean shouldShowTabs() {
        return !(privateSpaceEnabled() && isLaunchedAsPrivateProfile()) && hasWorkProfile() && ENABLE_TABBED_VIEW;
    }

    protected void onProfileClick(android.view.View view) {
        com.android.internal.app.chooser.DisplayResolveInfo otherProfile = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (otherProfile == null) {
            return;
        }
        this.mProfileSwitchMessage = null;
        onTargetSelected(otherProfile, false);
        finish();
    }

    protected boolean shouldAddFooterView() {
        android.view.View findViewById;
        return useLayoutWithDefault() || (findViewById = findViewById(com.android.internal.R.id.button_bar)) == null || findViewById.getVisibility() == 8;
    }

    protected void applyFooterView(int i) {
        if (this.mFooterSpacer == null) {
            this.mFooterSpacer = new android.widget.Space(getApplicationContext());
        } else {
            ((com.android.internal.app.ResolverMultiProfilePagerAdapter) this.mMultiProfilePagerAdapter).getActiveAdapterView().removeFooterView(this.mFooterSpacer);
        }
        this.mFooterSpacer.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, this.mSystemWindowInsets.bottom));
        ((com.android.internal.app.ResolverMultiProfilePagerAdapter) this.mMultiProfilePagerAdapter).getActiveAdapterView().addFooterView(this.mFooterSpacer);
    }

    protected android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
        this.mSystemWindowInsets = windowInsets.getSystemWindowInsets();
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        resetButtonBar();
        if (shouldUseMiniResolver()) {
            findViewById(com.android.internal.R.id.button_bar_container).setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + getResources().getDimensionPixelOffset(com.android.internal.R.dimen.resolver_button_bar_spacing));
        }
        if (shouldAddFooterView()) {
            applyFooterView(this.mSystemWindowInsets.bottom);
        }
        return windowInsets.consumeSystemWindowInsets();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
        if (this.mIsIntentPicker && shouldShowTabs() && !useLayoutWithDefault() && !shouldUseMiniResolver()) {
            updateIntentPickerPaddings();
        }
        if (this.mSystemWindowInsets != null) {
            this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        }
    }

    private void updateIntentPickerPaddings() {
        android.view.View findViewById = findViewById(com.android.internal.R.id.title_container);
        findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingTop(), findViewById.getPaddingRight(), getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_title_padding_bottom));
        android.view.View findViewById2 = findViewById(com.android.internal.R.id.button_bar);
        findViewById2.setPadding(findViewById2.getPaddingLeft(), getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_button_bar_spacing), findViewById2.getPaddingRight(), getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_button_bar_spacing));
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void sendVoiceChoicesIfNeeded() {
        if (!isVoiceInteraction()) {
            return;
        }
        int count = this.mMultiProfilePagerAdapter.getActiveListAdapter().getCount();
        android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr = new android.app.VoiceInteractor.PickOptionRequest.Option[count];
        for (int i = 0; i < count; i++) {
            com.android.internal.app.chooser.TargetInfo item = this.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(i);
            if (item == null) {
                return;
            }
            optionArr[i] = optionForChooserTarget(item, i);
        }
        this.mPickOptionRequest = new com.android.internal.app.ResolverActivity.PickTargetOptionRequest(new android.app.VoiceInteractor.Prompt(getTitle()), optionArr, null);
        getVoiceInteractor().submitRequest(this.mPickOptionRequest);
    }

    android.app.VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(com.android.internal.app.chooser.TargetInfo targetInfo, int i) {
        return new android.app.VoiceInteractor.PickOptionRequest.Option(targetInfo.getDisplayLabel(), i);
    }

    protected final void setAdditionalTargets(android.content.Intent[] intentArr) {
        if (intentArr != null) {
            for (android.content.Intent intent : intentArr) {
                this.mIntents.add(intent);
            }
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public android.content.Intent getTargetIntent() {
        if (this.mIntents.isEmpty()) {
            return null;
        }
        return this.mIntents.get(0);
    }

    protected java.lang.String getReferrerPackageName() {
        android.net.Uri referrer = getReferrer();
        if (referrer != null && "android-app".equals(referrer.getScheme())) {
            return referrer.getHost();
        }
        return null;
    }

    public int getLayoutResource() {
        return com.android.internal.R.layout.resolver_list;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void updateProfileViewButton() {
        if (this.mProfileView == null) {
            return;
        }
        com.android.internal.app.chooser.DisplayResolveInfo otherProfile = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (otherProfile != null && !shouldShowTabs()) {
            this.mProfileView.setVisibility(0);
            android.view.View findViewById = this.mProfileView.findViewById(com.android.internal.R.id.profile_button);
            if (!(findViewById instanceof android.widget.TextView)) {
                findViewById = this.mProfileView.findViewById(16908308);
            }
            ((android.widget.TextView) findViewById).lambda$setTextAsync$0(otherProfile.getDisplayLabel());
            return;
        }
        this.mProfileView.setVisibility(8);
    }

    private void setProfileSwitchMessage(int i) {
        if (i != -2 && i != android.os.UserHandle.myUserId()) {
            android.os.UserManager userManager = (android.os.UserManager) getSystemService("user");
            android.content.pm.UserInfo userInfo = userManager.getUserInfo(i);
            boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
            boolean isManagedProfile2 = userManager.isManagedProfile();
            if (isManagedProfile && !isManagedProfile2) {
                this.mProfileSwitchMessage = getForwardToPersonalMsg();
            } else if (!isManagedProfile && isManagedProfile2) {
                this.mProfileSwitchMessage = getForwardToWorkMsg();
            }
        }
    }

    private java.lang.String getForwardToPersonalMsg() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_PERSONAL, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getForwardToPersonalMsg$1;
                lambda$getForwardToPersonalMsg$1 = com.android.internal.app.ResolverActivity.this.lambda$getForwardToPersonalMsg$1();
                return lambda$getForwardToPersonalMsg$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getForwardToPersonalMsg$1() {
        return getString(com.android.internal.R.string.forward_intent_to_owner);
    }

    private java.lang.String getForwardToWorkMsg() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getForwardToWorkMsg$2;
                lambda$getForwardToWorkMsg$2 = com.android.internal.app.ResolverActivity.this.lambda$getForwardToWorkMsg$2();
                return lambda$getForwardToWorkMsg$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getForwardToWorkMsg$2() {
        return getString(com.android.internal.R.string.forward_intent_to_work);
    }

    public void setSafeForwardingMode(boolean z) {
        this.mSafeForwardingMode = z;
    }

    protected java.lang.CharSequence getTitleForAction(android.content.Intent intent, int i) {
        com.android.internal.app.ResolverActivity.ActionTitle forAction;
        if (this.mResolvingHome) {
            forAction = com.android.internal.app.ResolverActivity.ActionTitle.HOME;
        } else {
            forAction = com.android.internal.app.ResolverActivity.ActionTitle.forAction(intent.getAction());
        }
        boolean z = this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition() >= 0;
        if (forAction == com.android.internal.app.ResolverActivity.ActionTitle.DEFAULT && i != 0) {
            return getString(i);
        }
        if (z) {
            return getString(forAction.namedTitleRes, this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredItem().getDisplayLabel());
        }
        return getString(forAction.titleRes);
    }

    void dismiss() {
        if (!isFinishing()) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mPersonalPackageMonitor.register(this, getMainLooper(), getPersonalProfileUserHandle(), false);
            if (shouldShowTabs()) {
                if (this.mWorkPackageMonitor == null) {
                    this.mWorkPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getWorkListAdapter());
                }
                this.mWorkPackageMonitor.register(this, getMainLooper(), getWorkProfileUserHandle(), false);
            }
            this.mRegistered = true;
        }
        if (shouldShowTabs() && this.mQuietModeManager.isWaitingToEnableWorkProfile() && this.mQuietModeManager.isQuietModeEnabled(getWorkProfileUserHandle())) {
            this.mQuietModeManager.markWorkProfileEnabledBroadcastReceived();
        }
        this.mMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
        updateProfileViewButton();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        getWindow().addSystemFlags(524288);
        if (shouldShowTabs()) {
            this.mWorkProfileStateReceiver = createWorkProfileStateReceiver();
            registerWorkProfileStateReceiver();
            this.mWorkProfileHasBeenEnabled = isWorkProfileEnabled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWorkProfileEnabled() {
        android.os.UserHandle workProfileUserHandle = getWorkProfileUserHandle();
        android.os.UserManager userManager = (android.os.UserManager) getSystemService(android.os.UserManager.class);
        return !userManager.isQuietModeEnabled(workProfileUserHandle) && userManager.isUserUnlocked(workProfileUserHandle);
    }

    private void registerWorkProfileStateReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.content.Intent.ACTION_USER_UNLOCKED);
        intentFilter.addAction(android.content.Intent.ACTION_MANAGED_PROFILE_AVAILABLE);
        intentFilter.addAction(android.content.Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE);
        registerReceiverAsUser(this.mWorkProfileStateReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        android.view.Window window = getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags &= -524289;
        window.setAttributes(attributes);
        if (this.mRegistered) {
            this.mPersonalPackageMonitor.unregister();
            if (this.mWorkPackageMonitor != null) {
                this.mWorkPackageMonitor.unregister();
            }
            this.mRegistered = false;
        }
        if ((getIntent().getFlags() & 268435456) != 0 && !isVoiceInteraction() && !this.mResolvingHome && !this.mRetainInOnStop && !isChangingConfigurations()) {
            finish();
        }
        if (this.mWorkPackageMonitor != null) {
            unregisterReceiver(this.mWorkProfileStateReceiver);
            this.mWorkPackageMonitor = null;
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations() && this.mPickOptionRequest != null) {
            this.mPickOptionRequest.cancel();
        }
        if (this.mMultiProfilePagerAdapter != null && this.mMultiProfilePagerAdapter.getActiveListAdapter() != null) {
            this.mMultiProfilePagerAdapter.getActiveListAdapter().onDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        com.android.internal.widget.ViewPager viewPager = (com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.profile_pager);
        if (viewPager != null) {
            bundle.putInt(LAST_SHOWN_TAB_KEY, viewPager.getCurrentItem());
        }
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        resetButtonBar();
        com.android.internal.widget.ViewPager viewPager = (com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.profile_pager);
        if (viewPager != null) {
            viewPager.setCurrentItem(bundle.getInt(LAST_SHOWN_TAB_KEY));
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    private boolean hasManagedProfile() {
        android.os.UserManager userManager = (android.os.UserManager) getSystemService("user");
        if (userManager == null) {
            return false;
        }
        try {
            for (android.content.pm.UserInfo userInfo : userManager.getProfiles(getUserId())) {
                if (userInfo != null && userInfo.isManagedProfile()) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    private boolean supportsManagedProfiles(android.content.pm.ResolveInfo resolveInfo) {
        try {
            return getPackageManager().getApplicationInfo(resolveInfo.activityInfo.packageName, 0).targetSdkVersion >= 21;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlwaysButtonEnabled(boolean z, int i, boolean z2) {
        android.content.pm.ResolveInfo resolveInfo;
        boolean z3;
        if (!this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(getUser())) {
            this.mAlwaysButton.setEnabled(false);
            return;
        }
        if (hasCloneProfile() && !this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(this.mWorkProfileUserHandle)) {
            this.mAlwaysButton.setEnabled(false);
            return;
        }
        if (!z) {
            resolveInfo = null;
            z3 = false;
        } else {
            resolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, z2);
            if (resolveInfo == null) {
                android.util.Log.e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            } else if (resolveInfo.targetUserId != -2) {
                android.util.Log.e(TAG, "Attempted to set selection to resolve info for another user");
                return;
            } else {
                this.mAlwaysButton.lambda$setTextAsync$0(getResources().getString(com.android.internal.R.string.activity_resolver_use_always));
                z3 = true;
            }
        }
        if (resolveInfo != null) {
            if (!(this.mPm.checkPermission(android.Manifest.permission.RECORD_AUDIO, resolveInfo.activityInfo.packageName) == 0)) {
                z3 = !getIntent().getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false);
            }
        }
        this.mAlwaysButton.setEnabled(z3);
    }

    public void onButtonClick(android.view.View view) {
        int checkedItemPosition;
        int id = view.getId();
        android.widget.ListView listView = (android.widget.ListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
        com.android.internal.app.ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (activeListAdapter.hasFilteredItem()) {
            checkedItemPosition = activeListAdapter.getFilteredPosition();
        } else {
            checkedItemPosition = listView.getCheckedItemPosition();
        }
        startSelected(checkedItemPosition, id == 16908852, !activeListAdapter.hasFilteredItem());
    }

    public void startSelected(int i, boolean z, boolean z2) {
        int i2;
        if (isFinishing()) {
            return;
        }
        android.content.pm.ResolveInfo resolveInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, z2);
        if (this.mResolvingHome && hasManagedProfile() && !supportsManagedProfiles(resolveInfoForPosition)) {
            android.widget.Toast.makeText(this, getWorkProfileNotSupportedMsg(resolveInfoForPosition.activityInfo.loadLabel(getPackageManager()).toString()), 1).show();
            return;
        }
        com.android.internal.app.chooser.TargetInfo targetInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
        if (targetInfoForPosition != null && onTargetSelected(targetInfoForPosition, z)) {
            if (z && this.mSupportsAlwaysUseOption) {
                com.android.internal.logging.MetricsLogger.action(this, 455);
            } else if (this.mSupportsAlwaysUseOption) {
                com.android.internal.logging.MetricsLogger.action(this, 456);
            } else {
                com.android.internal.logging.MetricsLogger.action(this, 457);
            }
            if (this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) {
                i2 = 452;
            } else {
                i2 = 454;
            }
            com.android.internal.logging.MetricsLogger.action(this, i2);
            finish();
        }
    }

    private java.lang.String getWorkProfileNotSupportedMsg(final java.lang.String str) {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_WORK_PROFILE_NOT_SUPPORTED, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getWorkProfileNotSupportedMsg$3;
                lambda$getWorkProfileNotSupportedMsg$3 = com.android.internal.app.ResolverActivity.this.lambda$getWorkProfileNotSupportedMsg$3(str);
                return lambda$getWorkProfileNotSupportedMsg$3;
            }
        }, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getWorkProfileNotSupportedMsg$3(java.lang.String str) {
        return getString(com.android.internal.R.string.activity_resolver_work_profiles_support, str);
    }

    public android.content.Intent getReplacementIntent(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent) {
        return intent;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public final void onPostListReady(com.android.internal.app.ResolverListAdapter resolverListAdapter, boolean z, boolean z2) {
        if (isDestroyed() || isAutolaunching()) {
            return;
        }
        if (this.mIsIntentPicker) {
            ((com.android.internal.app.ResolverMultiProfilePagerAdapter) this.mMultiProfilePagerAdapter).setUseLayoutWithDefault(useLayoutWithDefault());
        }
        if (this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(resolverListAdapter)) {
            this.mMultiProfilePagerAdapter.showEmptyResolverListEmptyState(resolverListAdapter);
        } else {
            this.mMultiProfilePagerAdapter.showListView(resolverListAdapter);
        }
        if ((!z2 || !maybeAutolaunchActivity()) && z) {
            maybeCreateHeader(resolverListAdapter);
            resetButtonBar();
            onListRebuilt(resolverListAdapter, z2);
        }
    }

    protected void onListRebuilt(com.android.internal.app.ResolverListAdapter resolverListAdapter, boolean z) {
        com.android.internal.widget.ResolverDrawerLayout resolverDrawerLayout;
        int i;
        setupAdapterListView((android.widget.ListView) this.mMultiProfilePagerAdapter.getActiveAdapterView(), new com.android.internal.app.ResolverActivity.ItemClickListener());
        if (shouldShowTabs() && this.mIsIntentPicker && (resolverDrawerLayout = (com.android.internal.widget.ResolverDrawerLayout) findViewById(com.android.internal.R.id.contentPanel)) != null) {
            android.content.res.Resources resources = getResources();
            if (useLayoutWithDefault()) {
                i = com.android.internal.R.dimen.resolver_max_collapsed_height_with_default_with_tabs;
            } else {
                i = com.android.internal.R.dimen.resolver_max_collapsed_height_with_tabs;
            }
            resolverDrawerLayout.setMaxCollapsedHeight(resources.getDimensionPixelSize(i));
        }
    }

    protected boolean onTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo, boolean z) {
        android.content.Intent intent;
        android.content.ComponentName[] componentNameArr;
        java.lang.String resolveType;
        android.content.pm.ResolveInfo resolveInfo = targetInfo.getResolveInfo();
        android.content.Intent resolvedIntent = targetInfo != null ? targetInfo.getResolvedIntent() : null;
        if (resolvedIntent != null && ((this.mSupportsAlwaysUseOption || this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) && this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList() != null)) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            if (resolvedIntent.getSelector() != null) {
                intent = resolvedIntent.getSelector();
            } else {
                intent = resolvedIntent;
            }
            java.lang.String action = intent.getAction();
            if (action != null) {
                intentFilter.addAction(action);
            }
            java.util.Set<java.lang.String> categories = intent.getCategories();
            if (categories != null) {
                java.util.Iterator<java.lang.String> it = categories.iterator();
                while (it.hasNext()) {
                    intentFilter.addCategory(it.next());
                }
            }
            intentFilter.addCategory(android.content.Intent.CATEGORY_DEFAULT);
            int i = resolveInfo.match & android.content.IntentFilter.MATCH_CATEGORY_MASK;
            android.net.Uri data = intent.getData();
            if (i == 6291456 && (resolveType = intent.resolveType(this)) != null) {
                try {
                    intentFilter.addDataType(resolveType);
                } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                    android.util.Log.w(TAG, e);
                    intentFilter = null;
                }
            }
            if (data != null && data.getScheme() != null && (i != 6291456 || (!"file".equals(data.getScheme()) && !"content".equals(data.getScheme())))) {
                intentFilter.addDataScheme(data.getScheme());
                java.util.Iterator<android.os.PatternMatcher> schemeSpecificPartsIterator = resolveInfo.filter.schemeSpecificPartsIterator();
                if (schemeSpecificPartsIterator != null) {
                    java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
                    while (true) {
                        if (schemeSpecificPart == null || !schemeSpecificPartsIterator.hasNext()) {
                            break;
                        }
                        android.os.PatternMatcher next = schemeSpecificPartsIterator.next();
                        if (next.match(schemeSpecificPart)) {
                            intentFilter.addDataSchemeSpecificPart(next.getPath(), next.getType());
                            break;
                        }
                    }
                }
                java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator = resolveInfo.filter.authoritiesIterator();
                if (authoritiesIterator != null) {
                    while (true) {
                        if (!authoritiesIterator.hasNext()) {
                            break;
                        }
                        android.content.IntentFilter.AuthorityEntry next2 = authoritiesIterator.next();
                        if (next2.match(data) >= 0) {
                            int port = next2.getPort();
                            intentFilter.addDataAuthority(next2.getHost(), port >= 0 ? java.lang.Integer.toString(port) : null);
                        }
                    }
                }
                java.util.Iterator<android.os.PatternMatcher> pathsIterator = resolveInfo.filter.pathsIterator();
                if (pathsIterator != null) {
                    java.lang.String path = data.getPath();
                    while (true) {
                        if (path == null || !pathsIterator.hasNext()) {
                            break;
                        }
                        android.os.PatternMatcher next3 = pathsIterator.next();
                        if (next3.match(path)) {
                            intentFilter.addDataPath(next3.getPath(), next3.getType());
                            break;
                        }
                    }
                }
            }
            if (intentFilter != null) {
                int size = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList().size();
                boolean z2 = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile() != null;
                if (!z2) {
                    componentNameArr = new android.content.ComponentName[size];
                } else {
                    componentNameArr = new android.content.ComponentName[size + 1];
                }
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    android.content.pm.ResolveInfo resolveInfoAt = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList().get(i3).getResolveInfoAt(0);
                    componentNameArr[i3] = new android.content.ComponentName(resolveInfoAt.activityInfo.packageName, resolveInfoAt.activityInfo.name);
                    if (resolveInfoAt.match > i2) {
                        i2 = resolveInfoAt.match;
                    }
                }
                if (z2) {
                    componentNameArr[size] = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile().getResolvedComponentName();
                    int i4 = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile().getResolveInfo().match;
                    if (i4 > i2) {
                        i2 = i4;
                    }
                }
                if (z) {
                    int userId = getUserId();
                    android.content.pm.PackageManager packageManager = getPackageManager();
                    packageManager.addUniquePreferredActivity(intentFilter, i2, componentNameArr, resolvedIntent.getComponent());
                    if (resolveInfo.handleAllWebDataURI && android.text.TextUtils.isEmpty(packageManager.getDefaultBrowserPackageNameAsUser(userId))) {
                        packageManager.setDefaultBrowserPackageNameAsUser(resolveInfo.activityInfo.packageName, userId);
                    }
                } else {
                    try {
                        this.mMultiProfilePagerAdapter.getActiveListAdapter().mResolverListController.setLastChosen(resolvedIntent, intentFilter, i2);
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.d(TAG, "Error calling setLastChosenActivity\n" + e2);
                    }
                }
            }
        }
        if (targetInfo != null) {
            safelyStartActivity(targetInfo);
            if (targetInfo.isSuspended()) {
                return false;
            }
        }
        return true;
    }

    public final void safelyStartActivity(com.android.internal.app.chooser.TargetInfo targetInfo) {
        safelyStartActivityAsUser(targetInfo, getResolveInfoUserHandle(targetInfo.getResolveInfo(), this.mMultiProfilePagerAdapter.getCurrentUserHandle()), null);
    }

    public final void safelyStartActivityAsUser(com.android.internal.app.chooser.TargetInfo targetInfo, android.os.UserHandle userHandle) {
        safelyStartActivityAsUser(targetInfo, userHandle, null);
    }

    protected final void safelyStartActivityAsUser(com.android.internal.app.chooser.TargetInfo targetInfo, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        android.os.StrictMode.disableDeathOnFileUriExposure();
        try {
            safelyStartActivityInternal(targetInfo, userHandle, bundle);
        } finally {
            android.os.StrictMode.enableDeathOnFileUriExposure();
        }
    }

    protected void safelyStartActivityInternal(com.android.internal.app.chooser.TargetInfo targetInfo, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        if (!targetInfo.isSuspended() && this.mRegistered) {
            if (this.mPersonalPackageMonitor != null) {
                this.mPersonalPackageMonitor.unregister();
            }
            if (this.mWorkPackageMonitor != null) {
                this.mWorkPackageMonitor.unregister();
            }
            this.mRegistered = false;
        }
        if (this.mProfileSwitchMessage != null) {
            android.widget.Toast.makeText(this, this.mProfileSwitchMessage, 1).show();
        }
        if (!this.mSafeForwardingMode) {
            if (targetInfo.startAsUser(this, bundle, userHandle)) {
                onActivityStarted(targetInfo);
                maybeLogCrossProfileTargetLaunch(targetInfo, userHandle);
                return;
            }
            return;
        }
        try {
            if (targetInfo.startAsCaller(this, bundle, userHandle.getIdentifier())) {
                onActivityStarted(targetInfo);
                maybeLogCrossProfileTargetLaunch(targetInfo, userHandle);
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.wtf(TAG, "Unable to launch as uid " + this.mLaunchedFromUid + " package " + getLaunchedFromPackage() + ", while running in " + android.app.ActivityThread.currentProcessName(), e);
        }
    }

    private void maybeLogCrossProfileTargetLaunch(com.android.internal.app.chooser.TargetInfo targetInfo, android.os.UserHandle userHandle) {
        if (!hasWorkProfile() || userHandle.equals(getUser())) {
            return;
        }
        android.app.admin.DevicePolicyEventLogger.createEvent(155).setBoolean(userHandle.equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory(), targetInfo instanceof com.android.internal.app.chooser.ChooserTargetInfo ? com.android.internal.app.ChooserActivity.LAUNCH_LOCATION_DIRECT_SHARE : "other_target").write();
    }

    public void onActivityStarted(com.android.internal.app.chooser.TargetInfo targetInfo) {
    }

    public boolean shouldGetActivityMetadata() {
        return false;
    }

    public boolean shouldAutoLaunchSingleChoice(com.android.internal.app.chooser.TargetInfo targetInfo) {
        return !targetInfo.isSuspended();
    }

    void showTargetDetails(android.content.pm.ResolveInfo resolveInfo) {
        startActivityAsUser(new android.content.Intent().setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(android.net.Uri.fromParts("package", resolveInfo.activityInfo.packageName, null)).addFlags(524288), this.mMultiProfilePagerAdapter.getCurrentUserHandle());
    }

    protected com.android.internal.app.ResolverListAdapter createResolverListAdapter(android.content.Context context, java.util.List<android.content.Intent> list, android.content.Intent[] intentArr, java.util.List<android.content.pm.ResolveInfo> list2, boolean z, android.os.UserHandle userHandle) {
        android.os.UserHandle userHandle2;
        boolean booleanExtra = getIntent().getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false);
        if (!isLaunchedAsCloneProfile() || !userHandle.equals(getPersonalProfileUserHandle())) {
            userHandle2 = userHandle;
        } else {
            userHandle2 = getCloneProfileUserHandle();
        }
        return new com.android.internal.app.ResolverListAdapter(context, list, intentArr, list2, z, createListController(userHandle), this, booleanExtra, userHandle2);
    }

    protected com.android.internal.app.ResolverListController createListController(android.os.UserHandle userHandle) {
        android.os.UserHandle queryIntentsUser = getQueryIntentsUser(userHandle);
        return new com.android.internal.app.ResolverListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid, userHandle, new com.android.internal.app.ResolverRankerServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), (com.android.internal.app.AbstractResolverComparator.AfterCompute) null, (com.android.internal.app.ChooserActivityLogger) null, getResolverRankerServiceUserHandleList(userHandle)), queryIntentsUser);
    }

    private boolean configureContentView() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter() == null) {
            throw new java.lang.IllegalStateException("mMultiProfilePagerAdapter.getCurrentListAdapter() cannot be null.");
        }
        android.os.Trace.beginSection("configureContentView");
        boolean z = this.mMultiProfilePagerAdapter.rebuildActiveTab(true) || this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded();
        if (shouldShowTabs()) {
            z = z && (this.mMultiProfilePagerAdapter.rebuildInactiveTab(false) || this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded());
        }
        if (shouldUseMiniResolver()) {
            configureMiniResolverContent();
            android.os.Trace.endSection();
            return false;
        }
        if (useLayoutWithDefault()) {
            this.mLayoutId = com.android.internal.R.layout.resolver_list_with_default;
        } else {
            this.mLayoutId = getLayoutResource();
        }
        setContentView(this.mLayoutId);
        this.mMultiProfilePagerAdapter.setupViewPager((com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.profile_pager));
        boolean postRebuildList = postRebuildList(z);
        android.os.Trace.endSection();
        return postRebuildList;
    }

    private void configureMiniResolverContent() {
        this.mLayoutId = com.android.internal.R.layout.miniresolver;
        setContentView(this.mLayoutId);
        final com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList.get(0);
        boolean z = getCurrentProfile() == 1;
        final com.android.internal.app.ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        final com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo2 = inactiveListAdapter.mDisplayList.get(0);
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(16908294);
        java.util.Objects.requireNonNull(inactiveListAdapter);
        new com.android.internal.app.ResolverListAdapter.LoadIconTask(inactiveListAdapter, displayResolveInfo2, displayResolveInfo2, imageView) { // from class: com.android.internal.app.ResolverActivity.5
            final /* synthetic */ android.widget.ImageView val$icon;
            final /* synthetic */ com.android.internal.app.chooser.DisplayResolveInfo val$otherProfileResolveInfo;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(displayResolveInfo2);
                this.val$otherProfileResolveInfo = displayResolveInfo2;
                this.val$icon = imageView;
                java.util.Objects.requireNonNull(inactiveListAdapter);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.app.ResolverListAdapter.LoadIconTask, android.os.AsyncTask
            public void onPostExecute(android.graphics.drawable.Drawable drawable) {
                if (!com.android.internal.app.ResolverActivity.this.isDestroyed()) {
                    this.val$otherProfileResolveInfo.setDisplayIcon(drawable);
                    new com.android.internal.app.ResolverListAdapter.ViewHolder(this.val$icon).bindIcon(this.val$otherProfileResolveInfo);
                }
            }
        }.execute(new java.lang.Void[0]);
        final java.lang.CharSequence displayLabel = displayResolveInfo2.getDisplayLabel();
        android.app.admin.DevicePolicyResourcesManager resources = ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources();
        if (z) {
            ((android.widget.TextView) findViewById(com.android.internal.R.id.open_cross_profile)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$configureMiniResolverContent$4;
                    lambda$configureMiniResolverContent$4 = com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$4(displayLabel);
                    return lambda$configureMiniResolverContent$4;
                }
            }, displayLabel));
            ((android.widget.Button) findViewById(com.android.internal.R.id.use_same_profile_browser)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$configureMiniResolverContent$5;
                    lambda$configureMiniResolverContent$5 = com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$5();
                    return lambda$configureMiniResolverContent$5;
                }
            }));
        } else {
            ((android.widget.TextView) findViewById(com.android.internal.R.id.open_cross_profile)).lambda$setTextAsync$0(resources.getString(android.app.admin.DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_IN_WORK, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$configureMiniResolverContent$6;
                    lambda$configureMiniResolverContent$6 = com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$6(displayLabel);
                    return lambda$configureMiniResolverContent$6;
                }
            }, displayLabel));
            ((android.widget.Button) findViewById(com.android.internal.R.id.use_same_profile_browser)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$configureMiniResolverContent$7;
                    lambda$configureMiniResolverContent$7 = com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$7();
                    return lambda$configureMiniResolverContent$7;
                }
            }));
        }
        findViewById(com.android.internal.R.id.use_same_profile_browser).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$8(displayResolveInfo, view);
            }
        });
        findViewById(com.android.internal.R.id.button_open).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.app.ResolverActivity.this.lambda$configureMiniResolverContent$9(displayResolveInfo2, inactiveListAdapter, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$configureMiniResolverContent$4(java.lang.CharSequence charSequence) {
        return getString(com.android.internal.R.string.miniresolver_open_in_personal, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$configureMiniResolverContent$5() {
        return getString(com.android.internal.R.string.miniresolver_use_work_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$configureMiniResolverContent$6(java.lang.CharSequence charSequence) {
        return getString(com.android.internal.R.string.miniresolver_open_in_work, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$configureMiniResolverContent$7() {
        return getString(com.android.internal.R.string.miniresolver_use_personal_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$8(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, android.view.View view) {
        safelyStartActivity(displayResolveInfo);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$9(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, com.android.internal.app.ResolverListAdapter resolverListAdapter, android.view.View view) {
        displayResolveInfo.getResolvedIntent();
        safelyStartActivityAsUser(displayResolveInfo, resolverListAdapter.mResolverListController.getUserHandle());
        finish();
    }

    private boolean shouldUseMiniResolver() {
        if (!this.mIsIntentPicker || this.mMultiProfilePagerAdapter.getActiveListAdapter() == null || this.mMultiProfilePagerAdapter.getInactiveListAdapter() == null) {
            return false;
        }
        java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> list = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList;
        java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> list2 = this.mMultiProfilePagerAdapter.getInactiveListAdapter().mDisplayList;
        if (list.isEmpty()) {
            android.util.Log.d(TAG, "No targets in the current profile");
            return false;
        }
        if (list2.size() != 1) {
            android.util.Log.d(TAG, "Found " + list2.size() + " resolvers in the other profile");
            return false;
        }
        if (list2.get(0).getResolveInfo().handleAllWebDataURI) {
            android.util.Log.d(TAG, "Other profile is a web browser");
            return false;
        }
        java.util.Iterator<com.android.internal.app.chooser.DisplayResolveInfo> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().getResolveInfo().handleAllWebDataURI) {
                android.util.Log.d(TAG, "Non-browser found in this profile");
                return false;
            }
        }
        return true;
    }

    protected boolean postRebuildList(boolean z) {
        return postRebuildListInternal(z);
    }

    final boolean postRebuildListInternal(boolean z) {
        this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount();
        if (z && maybeAutolaunchActivity()) {
            return true;
        }
        setupViewVisibilities();
        if (shouldShowTabs()) {
            setupProfileTabs();
            return false;
        }
        return false;
    }

    private int isPermissionGranted(java.lang.String str, int i) {
        return android.app.ActivityManager.checkComponentPermission(str, i, -1, true);
    }

    private boolean maybeAutolaunchActivity() {
        int itemCount = this.mMultiProfilePagerAdapter.getItemCount();
        if (itemCount == 1 && maybeAutolaunchIfSingleTarget()) {
            return true;
        }
        if (itemCount == 2 && this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded() && this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded()) {
            if (maybeAutolaunchIfNoAppsOnInactiveTab() || maybeAutolaunchIfCrossProfileSupported()) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean maybeAutolaunchIfSingleTarget() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount() != 1 || this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile() != null) {
            return false;
        }
        com.android.internal.app.chooser.TargetInfo targetInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false);
        if (!shouldAutoLaunchSingleChoice(targetInfoForPosition)) {
            return false;
        }
        safelyStartActivity(targetInfoForPosition);
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfNoAppsOnInactiveTab() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount() != 1 || this.mMultiProfilePagerAdapter.getInactiveListAdapter().getUnfilteredCount() != 0) {
            return false;
        }
        safelyStartActivity(this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false));
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfCrossProfileSupported() {
        com.android.internal.app.ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (activeListAdapter.getUnfilteredCount() != 1) {
            return false;
        }
        com.android.internal.app.ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        if (inactiveListAdapter.getUnfilteredCount() != 1) {
            return false;
        }
        com.android.internal.app.chooser.TargetInfo targetInfoForPosition = activeListAdapter.targetInfoForPosition(0, false);
        if (!java.util.Objects.equals(targetInfoForPosition.getResolvedComponentName(), inactiveListAdapter.targetInfoForPosition(0, false).getResolvedComponentName()) || !shouldAutoLaunchSingleChoice(targetInfoForPosition) || !canAppInteractCrossProfiles(targetInfoForPosition.getResolvedComponentName().getPackageName())) {
            return false;
        }
        android.app.admin.DevicePolicyEventLogger.createEvent(161).setBoolean(activeListAdapter.getUserHandle().equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory()).write();
        safelyStartActivity(targetInfoForPosition);
        finish();
        return true;
    }

    private boolean canAppInteractCrossProfiles(java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 0);
            if (!applicationInfo.crossProfile) {
                return false;
            }
            int i = applicationInfo.uid;
            return isPermissionGranted(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL, i) == 0 || isPermissionGranted(android.Manifest.permission.INTERACT_ACROSS_USERS, i) == 0 || android.content.PermissionChecker.checkPermissionForPreflight(this, android.Manifest.permission.INTERACT_ACROSS_PROFILES, -1, i, str) == 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Package " + str + " does not exist on current user.");
            return false;
        }
    }

    private boolean isAutolaunching() {
        return !this.mRegistered && isFinishing();
    }

    private void setupProfileTabs() {
        maybeHideDivider();
        final android.widget.TabHost tabHost = (android.widget.TabHost) findViewById(com.android.internal.R.id.profile_tabhost);
        tabHost.setup();
        final com.android.internal.widget.ViewPager viewPager = (com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.profile_pager);
        viewPager.setSaveEnabled(false);
        android.widget.Button button = (android.widget.Button) getLayoutInflater().inflate(com.android.internal.R.layout.resolver_profile_tab_button, (android.view.ViewGroup) tabHost.getTabWidget(), false);
        button.lambda$setTextAsync$0(getPersonalTabLabel());
        button.setContentDescription(getPersonalTabAccessibilityLabel());
        tabHost.addTab(tabHost.newTabSpec("personal").setContent(com.android.internal.R.id.profile_pager).setIndicator(button));
        android.widget.Button button2 = (android.widget.Button) getLayoutInflater().inflate(com.android.internal.R.layout.resolver_profile_tab_button, (android.view.ViewGroup) tabHost.getTabWidget(), false);
        button2.lambda$setTextAsync$0(getWorkTabLabel());
        button2.setContentDescription(getWorkTabAccessibilityLabel());
        tabHost.addTab(tabHost.newTabSpec(TAB_TAG_WORK).setContent(com.android.internal.R.id.profile_pager).setIndicator(button2));
        tabHost.getTabWidget().setVisibility(0);
        updateActiveTabStyle(tabHost);
        tabHost.setOnTabChangedListener(new android.widget.TabHost.OnTabChangeListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda16
            @Override // android.widget.TabHost.OnTabChangeListener
            public final void onTabChanged(java.lang.String str) {
                com.android.internal.app.ResolverActivity.this.lambda$setupProfileTabs$10(tabHost, viewPager, str);
            }
        });
        viewPager.setVisibility(0);
        tabHost.setCurrentTab(this.mMultiProfilePagerAdapter.getCurrentPage());
        this.mMultiProfilePagerAdapter.setOnProfileSelectedListener(new com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener() { // from class: com.android.internal.app.ResolverActivity.6
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfileSelected(int i) {
                tabHost.setCurrentTab(i);
                com.android.internal.app.ResolverActivity.this.resetButtonBar();
                com.android.internal.app.ResolverActivity.this.resetCheckedItem();
            }

            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfilePageStateChanged(int i) {
                com.android.internal.app.ResolverActivity.this.onHorizontalSwipeStateChanged(i);
            }
        });
        this.mOnSwitchOnWorkSelectedListener = new com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda17
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                com.android.internal.app.ResolverActivity.lambda$setupProfileTabs$11(android.widget.TabHost.this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupProfileTabs$10(android.widget.TabHost tabHost, com.android.internal.widget.ViewPager viewPager, java.lang.String str) {
        updateActiveTabStyle(tabHost);
        if ("personal".equals(str)) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
        setupViewVisibilities();
        maybeLogProfileChange();
        onProfileTabSelected();
        android.app.admin.DevicePolicyEventLogger.createEvent(156).setInt(viewPager.getCurrentItem()).setStrings(getMetricsCategory()).write();
    }

    static /* synthetic */ void lambda$setupProfileTabs$11(android.widget.TabHost tabHost) {
        android.view.View childAt = tabHost.getTabWidget().getChildAt(1);
        childAt.setFocusable(true);
        childAt.setFocusableInTouchMode(true);
        childAt.requestFocus();
    }

    private java.lang.String getPersonalTabLabel() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_PERSONAL_TAB, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getPersonalTabLabel$12;
                lambda$getPersonalTabLabel$12 = com.android.internal.app.ResolverActivity.this.lambda$getPersonalTabLabel$12();
                return lambda$getPersonalTabLabel$12;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getPersonalTabLabel$12() {
        return getString(com.android.internal.R.string.resolver_personal_tab);
    }

    private java.lang.String getWorkTabLabel() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getWorkTabLabel$13;
                lambda$getWorkTabLabel$13 = com.android.internal.app.ResolverActivity.this.lambda$getWorkTabLabel$13();
                return lambda$getWorkTabLabel$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getWorkTabLabel$13() {
        return getString(com.android.internal.R.string.resolver_work_tab);
    }

    void onHorizontalSwipeStateChanged(int i) {
    }

    private void maybeHideDivider() {
        android.view.View findViewById;
        if (!this.mIsIntentPicker || (findViewById = findViewById(com.android.internal.R.id.divider)) == null) {
            return;
        }
        findViewById.setVisibility(8);
    }

    protected void onProfileTabSelected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCheckedItem() {
        if (!this.mIsIntentPicker) {
            return;
        }
        this.mLastSelected = -1;
        android.widget.ListView listView = (android.widget.ListView) this.mMultiProfilePagerAdapter.getInactiveAdapterView();
        if (listView.getCheckedItemCount() > 0) {
            listView.setItemChecked(listView.getCheckedItemPosition(), false);
        }
    }

    private java.lang.String getPersonalTabAccessibilityLabel() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_PERSONAL_TAB_ACCESSIBILITY, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getPersonalTabAccessibilityLabel$14;
                lambda$getPersonalTabAccessibilityLabel$14 = com.android.internal.app.ResolverActivity.this.lambda$getPersonalTabAccessibilityLabel$14();
                return lambda$getPersonalTabAccessibilityLabel$14;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getPersonalTabAccessibilityLabel$14() {
        return getString(com.android.internal.R.string.resolver_personal_tab_accessibility);
    }

    private java.lang.String getWorkTabAccessibilityLabel() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB_ACCESSIBILITY, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getWorkTabAccessibilityLabel$15;
                lambda$getWorkTabAccessibilityLabel$15 = com.android.internal.app.ResolverActivity.this.lambda$getWorkTabAccessibilityLabel$15();
                return lambda$getWorkTabAccessibilityLabel$15;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getWorkTabAccessibilityLabel$15() {
        return getString(com.android.internal.R.string.resolver_work_tab_accessibility);
    }

    private static int getAttrColor(android.content.Context context, int i) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    private void updateActiveTabStyle(android.widget.TabHost tabHost) {
        int currentTab = tabHost.getCurrentTab();
        android.widget.TextView textView = (android.widget.TextView) tabHost.getTabWidget().getChildAt(currentTab);
        android.widget.TextView textView2 = (android.widget.TextView) tabHost.getTabWidget().getChildAt(1 - currentTab);
        textView.setSelected(true);
        textView2.setSelected(false);
    }

    private void setupViewVisibilities() {
        com.android.internal.app.ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (!this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter)) {
            addUseDifferentAppLabelIfNecessary(activeListAdapter);
        }
    }

    public void addUseDifferentAppLabelIfNecessary(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter.hasFilteredItem()) {
            android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.stub);
            frameLayout.setVisibility(0);
            android.widget.TextView textView = (android.widget.TextView) android.view.LayoutInflater.from(this).inflate(com.android.internal.R.layout.resolver_different_item_header, (android.view.ViewGroup) null, false);
            if (shouldShowTabs()) {
                textView.setGravity(17);
            }
            frameLayout.addView(textView);
        }
    }

    private void setupAdapterListView(android.widget.ListView listView, com.android.internal.app.ResolverActivity.ItemClickListener itemClickListener) {
        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemClickListener);
        if (this.mSupportsAlwaysUseOption) {
            listView.setChoiceMode(1);
        }
    }

    private void maybeCreateHeader(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        java.lang.CharSequence titleForAction;
        android.widget.TextView textView;
        if (this.mHeaderCreatorUser != null && !resolverListAdapter.getUserHandle().equals(this.mHeaderCreatorUser)) {
            return;
        }
        if (!shouldShowTabs() && resolverListAdapter.getCount() == 0 && resolverListAdapter.getPlaceholderCount() == 0 && (textView = (android.widget.TextView) findViewById(16908310)) != null) {
            textView.setVisibility(8);
        }
        if (this.mTitle != null) {
            titleForAction = this.mTitle;
        } else {
            titleForAction = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
        }
        if (!android.text.TextUtils.isEmpty(titleForAction)) {
            android.widget.TextView textView2 = (android.widget.TextView) findViewById(16908310);
            if (textView2 != null) {
                textView2.lambda$setTextAsync$0(titleForAction);
            }
            setTitle(titleForAction);
        }
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(16908294);
        if (imageView != null) {
            resolverListAdapter.loadFilteredItemIconTaskAsync(imageView);
        }
        this.mHeaderCreatorUser = resolverListAdapter.getUserHandle();
    }

    protected void resetButtonBar() {
        if (!this.mSupportsAlwaysUseOption) {
            return;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) findViewById(com.android.internal.R.id.button_bar);
        if (viewGroup == null) {
            android.util.Log.e(TAG, "Layout unexpectedly does not have a button bar");
            return;
        }
        com.android.internal.app.ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        android.view.View findViewById = findViewById(com.android.internal.R.id.resolver_button_bar_divider);
        if (!useLayoutWithDefault()) {
            viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_button_bar_spacing) + (this.mSystemWindowInsets != null ? this.mSystemWindowInsets.bottom : 0));
        }
        if (activeListAdapter.isTabLoaded() && this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter) && !useLayoutWithDefault()) {
            viewGroup.setVisibility(4);
            if (findViewById != null) {
                findViewById.setVisibility(4);
            }
            setButtonBarIgnoreOffset(false);
            return;
        }
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        viewGroup.setVisibility(0);
        setButtonBarIgnoreOffset(true);
        this.mOnceButton = (android.widget.Button) viewGroup.findViewById(com.android.internal.R.id.button_once);
        this.mAlwaysButton = (android.widget.Button) viewGroup.findViewById(com.android.internal.R.id.button_always);
        resetAlwaysOrOnceButtonBar();
    }

    private void setButtonBarIgnoreOffset(boolean z) {
        android.view.View findViewById = findViewById(com.android.internal.R.id.button_bar_container);
        if (findViewById != null) {
            com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams = (com.android.internal.widget.ResolverDrawerLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.ignoreOffset = z;
            findViewById.setLayoutParams(layoutParams);
        }
    }

    private void resetAlwaysOrOnceButtonBar() {
        setAlwaysButtonEnabled(false, -1, false);
        this.mOnceButton.setEnabled(false);
        int filteredPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition();
        if (useLayoutWithDefault() && filteredPosition != -1) {
            setAlwaysButtonEnabled(true, filteredPosition, false);
            this.mOnceButton.setEnabled(true);
            this.mOnceButton.requestFocus();
        } else {
            android.widget.ListView listView = (android.widget.ListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
            if (listView != null && listView.getCheckedItemPosition() != -1) {
                setAlwaysButtonEnabled(true, listView.getCheckedItemPosition(), true);
                this.mOnceButton.setEnabled(true);
            }
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean useLayoutWithDefault() {
        return this.mSupportsAlwaysUseOption && this.mMultiProfilePagerAdapter.getListAdapterForUserHandle(getTabOwnerUserHandleForLaunch()).hasFilteredItem();
    }

    protected void setRetainInOnStop(boolean z) {
        this.mRetainInOnStop = z;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean resolveInfoMatch(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
        return resolveInfo == null ? resolveInfo2 == null : resolveInfo.activityInfo == null ? resolveInfo2.activityInfo == null : java.util.Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name) && java.util.Objects.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName) && java.util.Objects.equals(getResolveInfoUserHandle(resolveInfo, this.mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle()), getResolveInfoUserHandle(resolveInfo2, this.mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle()));
    }

    protected java.lang.String getMetricsCategory() {
        return METRICS_CATEGORY_RESOLVER;
    }

    public void onHandlePackagesChanged(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter == this.mMultiProfilePagerAdapter.getActiveListAdapter()) {
            if ((!resolverListAdapter.getUserHandle().equals(getWorkProfileUserHandle()) || !this.mQuietModeManager.isWaitingToEnableWorkProfile()) && this.mMultiProfilePagerAdapter.rebuildActiveTab(true)) {
                com.android.internal.app.ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
                activeListAdapter.notifyDataSetChanged();
                if (activeListAdapter.getCount() == 0 && !inactiveListAdapterHasItems()) {
                    finish();
                    return;
                }
                return;
            }
            return;
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    private boolean inactiveListAdapterHasItems() {
        return shouldShowTabs() && this.mMultiProfilePagerAdapter.getInactiveListAdapter().getCount() > 0;
    }

    private android.content.BroadcastReceiver createWorkProfileStateReceiver() {
        return new android.content.BroadcastReceiver() { // from class: com.android.internal.app.ResolverActivity.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if ((!android.text.TextUtils.equals(action, android.content.Intent.ACTION_USER_UNLOCKED) && !android.text.TextUtils.equals(action, android.content.Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE) && !android.text.TextUtils.equals(action, android.content.Intent.ACTION_MANAGED_PROFILE_AVAILABLE)) || intent.getIntExtra(android.content.Intent.EXTRA_USER_HANDLE, -1) != com.android.internal.app.ResolverActivity.this.getWorkProfileUserHandle().getIdentifier()) {
                    return;
                }
                if (com.android.internal.app.ResolverActivity.this.isWorkProfileEnabled()) {
                    if (com.android.internal.app.ResolverActivity.this.mWorkProfileHasBeenEnabled) {
                        return;
                    }
                    com.android.internal.app.ResolverActivity.this.mWorkProfileHasBeenEnabled = true;
                    com.android.internal.app.ResolverActivity.this.mQuietModeManager.markWorkProfileEnabledBroadcastReceived();
                } else {
                    com.android.internal.app.ResolverActivity.this.mWorkProfileHasBeenEnabled = false;
                }
                if (com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(com.android.internal.app.ResolverActivity.this.getWorkProfileUserHandle())) {
                    com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.rebuildActiveTab(true);
                } else {
                    com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
                }
            }
        };
    }

    public static final class ResolvedComponentInfo {
        private boolean mFixedAtTop;
        private boolean mPinned;
        public final android.content.ComponentName name;
        private final java.util.List<android.content.Intent> mIntents = new java.util.ArrayList();
        private final java.util.List<android.content.pm.ResolveInfo> mResolveInfos = new java.util.ArrayList();

        public ResolvedComponentInfo(android.content.ComponentName componentName, android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo) {
            this.name = componentName;
            add(intent, resolveInfo);
        }

        public void add(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo) {
            this.mIntents.add(intent);
            this.mResolveInfos.add(resolveInfo);
        }

        public int getCount() {
            return this.mIntents.size();
        }

        public android.content.Intent getIntentAt(int i) {
            if (i >= 0) {
                return this.mIntents.get(i);
            }
            return null;
        }

        public android.content.pm.ResolveInfo getResolveInfoAt(int i) {
            if (i >= 0) {
                return this.mResolveInfos.get(i);
            }
            return null;
        }

        public int findIntent(android.content.Intent intent) {
            int size = this.mIntents.size();
            for (int i = 0; i < size; i++) {
                if (intent.equals(this.mIntents.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int findResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
            int size = this.mResolveInfos.size();
            for (int i = 0; i < size; i++) {
                if (resolveInfo.equals(this.mResolveInfos.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public boolean isPinned() {
            return this.mPinned;
        }

        public void setPinned(boolean z) {
            this.mPinned = z;
        }

        public boolean isFixedAtTop() {
            return this.mFixedAtTop;
        }

        public void setFixedAtTop(boolean z) {
            this.mFixedAtTop = z;
        }
    }

    class ItemClickListener implements android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.widget.ListView listView = adapterView instanceof android.widget.ListView ? (android.widget.ListView) adapterView : null;
            if (listView != null) {
                i -= listView.getHeaderViewsCount();
            }
            if (i < 0 || com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, true) == null) {
                return;
            }
            android.widget.ListView listView2 = (android.widget.ListView) com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.getActiveAdapterView();
            int checkedItemPosition = listView2.getCheckedItemPosition();
            boolean z = checkedItemPosition != -1;
            if (com.android.internal.app.ResolverActivity.this.useLayoutWithDefault() || ((z && com.android.internal.app.ResolverActivity.this.mLastSelected == checkedItemPosition) || com.android.internal.app.ResolverActivity.this.mAlwaysButton == null)) {
                com.android.internal.app.ResolverActivity.this.startSelected(i, false, true);
                return;
            }
            com.android.internal.app.ResolverActivity.this.setAlwaysButtonEnabled(z, checkedItemPosition, true);
            com.android.internal.app.ResolverActivity.this.mOnceButton.setEnabled(z);
            if (z) {
                listView2.smoothScrollToPosition(checkedItemPosition);
                com.android.internal.app.ResolverActivity.this.mOnceButton.requestFocus();
            }
            com.android.internal.app.ResolverActivity.this.mLastSelected = checkedItemPosition;
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.widget.ListView listView = adapterView instanceof android.widget.ListView ? (android.widget.ListView) adapterView : null;
            if (listView != null) {
                i -= listView.getHeaderViewsCount();
            }
            if (i < 0) {
                return false;
            }
            com.android.internal.app.ResolverActivity.this.showTargetDetails(com.android.internal.app.ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, true));
            return true;
        }
    }

    static final boolean isSpecificUriMatch(int i) {
        int i2 = i & android.content.IntentFilter.MATCH_CATEGORY_MASK;
        return i2 >= 3145728 && i2 <= 5242880;
    }

    static class PickTargetOptionRequest extends android.app.VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            super(prompt, optionArr, bundle);
        }

        @Override // android.app.VoiceInteractor.Request
        public void onCancel() {
            super.onCancel();
            com.android.internal.app.ResolverActivity resolverActivity = (com.android.internal.app.ResolverActivity) getActivity();
            if (resolverActivity != null) {
                resolverActivity.mPickOptionRequest = null;
                resolverActivity.finish();
            }
        }

        @Override // android.app.VoiceInteractor.PickOptionRequest
        public void onPickOptionResult(boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            com.android.internal.app.ResolverActivity resolverActivity;
            super.onPickOptionResult(z, optionArr, bundle);
            if (optionArr.length == 1 && (resolverActivity = (com.android.internal.app.ResolverActivity) getActivity()) != null && resolverActivity.onTargetSelected(resolverActivity.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(optionArr[0].getIndex()), false)) {
                resolverActivity.mPickOptionRequest = null;
                resolverActivity.finish();
            }
        }
    }

    protected void maybeLogProfileChange() {
    }

    protected final android.os.UserHandle getQueryIntentsUser(android.os.UserHandle userHandle) {
        if (isLaunchedAsCloneProfile() && userHandle.equals(getPersonalProfileUserHandle())) {
            return getCloneProfileUserHandle();
        }
        return userHandle;
    }

    public final java.util.List<android.os.UserHandle> getResolverRankerServiceUserHandleList(android.os.UserHandle userHandle) {
        return getResolverRankerServiceUserHandleListInternal(userHandle);
    }

    protected java.util.List<android.os.UserHandle> getResolverRankerServiceUserHandleListInternal(android.os.UserHandle userHandle) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(userHandle);
        if (userHandle.equals(getPersonalProfileUserHandle()) && getCloneProfileUserHandle() != null) {
            arrayList.add(getCloneProfileUserHandle());
        }
        return arrayList;
    }

    public static android.os.UserHandle getResolveInfoUserHandle(android.content.pm.ResolveInfo resolveInfo, android.os.UserHandle userHandle) {
        if (resolveInfo.userHandle == null) {
            android.util.Log.e(TAG, "ResolveInfo with null UserHandle found: " + resolveInfo);
        }
        return resolveInfo.userHandle;
    }

    private boolean privateSpaceEnabled() {
        return this.mIsIntentPicker && android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.allowResolverSheetForPrivateSpace();
    }

    public static class AppListAccessibilityDelegate extends android.view.View.AccessibilityDelegate {
        private final android.view.View mBottomBar;
        private final com.android.internal.widget.ResolverDrawerLayout mDrawer;
        private final android.graphics.Rect mRect = new android.graphics.Rect();

        public AppListAccessibilityDelegate(com.android.internal.widget.ResolverDrawerLayout resolverDrawerLayout) {
            this.mDrawer = resolverDrawerLayout;
            this.mBottomBar = this.mDrawer.findViewById(com.android.internal.R.id.button_bar_container);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            boolean onRequestSendAccessibilityEvent = super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            if (onRequestSendAccessibilityEvent && accessibilityEvent.getEventType() == 32768 && this.mDrawer.isCollapsed()) {
                view.getBoundsOnScreen(this.mRect);
                int i = this.mRect.top;
                int i2 = this.mRect.bottom;
                this.mDrawer.getBoundsOnScreen(this.mRect, true);
                int height = this.mBottomBar == null ? 0 : this.mBottomBar.getHeight();
                int i3 = this.mRect.top;
                int i4 = this.mRect.bottom - height;
                if (i3 > i || i2 > i4) {
                    this.mDrawer.setCollapsed(false);
                }
            }
            return onRequestSendAccessibilityEvent;
        }
    }
}
