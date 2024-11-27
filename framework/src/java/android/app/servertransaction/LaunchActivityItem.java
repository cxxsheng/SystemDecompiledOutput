package android.app.servertransaction;

/* loaded from: classes.dex */
public class LaunchActivityItem extends android.app.servertransaction.ClientTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.LaunchActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.LaunchActivityItem>() { // from class: android.app.servertransaction.LaunchActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.LaunchActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.LaunchActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.LaunchActivityItem[] newArray(int i) {
            return new android.app.servertransaction.LaunchActivityItem[i];
        }
    };
    private android.app.IActivityClientController mActivityClientController;
    private android.os.IBinder mActivityToken;
    private android.window.ActivityWindowInfo mActivityWindowInfo;
    private android.os.IBinder mAssistToken;
    private android.content.res.Configuration mCurConfig;
    private int mDeviceId;
    private int mIdent;
    private android.content.pm.ActivityInfo mInfo;
    private android.os.IBinder mInitialCallerInfoAccessToken;
    private android.content.Intent mIntent;
    private boolean mIsForward;
    private boolean mLaunchedFromBubble;
    private android.content.res.Configuration mOverrideConfig;
    private java.util.List<com.android.internal.content.ReferrerIntent> mPendingNewIntents;
    private java.util.List<android.app.ResultInfo> mPendingResults;
    private android.os.PersistableBundle mPersistentState;
    private int mProcState;
    private android.app.ProfilerInfo mProfilerInfo;
    private java.lang.String mReferrer;
    private android.app.ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private android.os.IBinder mShareableActivityToken;
    private android.os.Bundle mState;
    private android.os.IBinder mTaskFragmentToken;
    private com.android.internal.app.IVoiceInteractor mVoiceInteractor;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        clientTransactionHandler.countLaunchingActivities(1);
        clientTransactionHandler.updateProcessState(this.mProcState, false);
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mCurConfig);
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mOverrideConfig);
        clientTransactionHandler.updatePendingConfiguration(this.mCurConfig);
        if (this.mActivityClientController != null) {
            android.app.ActivityClient.setActivityClientController(this.mActivityClientController);
        }
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityStart");
        clientTransactionHandler.handleLaunchActivity(new android.app.ActivityThread.ActivityClientRecord(this.mActivityToken, this.mIntent, this.mIdent, this.mInfo, this.mOverrideConfig, this.mReferrer, this.mVoiceInteractor, this.mState, this.mPersistentState, this.mPendingResults, this.mPendingNewIntents, this.mSceneTransitionInfo, this.mIsForward, this.mProfilerInfo, clientTransactionHandler, this.mAssistToken, this.mShareableActivityToken, this.mLaunchedFromBubble, this.mTaskFragmentToken, this.mInitialCallerInfoAccessToken, this.mActivityWindowInfo), pendingTransactionActions, this.mDeviceId, null);
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.countLaunchingActivities(-1);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return android.app.ActivityThread.currentApplication();
    }

    private LaunchActivityItem() {
    }

    public static android.app.servertransaction.LaunchActivityItem obtain(android.os.IBinder iBinder, android.content.Intent intent, int i, android.content.pm.ActivityInfo activityInfo, android.content.res.Configuration configuration, android.content.res.Configuration configuration2, int i2, java.lang.String str, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, android.app.ProfilerInfo profilerInfo, android.os.IBinder iBinder2, android.app.IActivityClientController iActivityClientController, android.os.IBinder iBinder3, boolean z2, android.os.IBinder iBinder4, android.os.IBinder iBinder5, android.window.ActivityWindowInfo activityWindowInfo) {
        android.os.Bundle bundle2;
        android.app.servertransaction.LaunchActivityItem launchActivityItem = (android.app.servertransaction.LaunchActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.LaunchActivityItem.class);
        if (launchActivityItem == null) {
            launchActivityItem = new android.app.servertransaction.LaunchActivityItem();
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        android.content.pm.ActivityInfo activityInfo2 = new android.content.pm.ActivityInfo(activityInfo);
        android.content.res.Configuration configuration3 = new android.content.res.Configuration(configuration);
        android.content.res.Configuration configuration4 = new android.content.res.Configuration(configuration2);
        if (bundle != null) {
            bundle2 = new android.os.Bundle(bundle);
        } else {
            bundle2 = null;
        }
        setValues(launchActivityItem, iBinder, intent2, i, activityInfo2, configuration3, configuration4, i2, str, iVoiceInteractor, i3, bundle2, persistableBundle != null ? new android.os.PersistableBundle(persistableBundle) : null, list != null ? new java.util.ArrayList(list) : null, list2 != null ? new java.util.ArrayList(list2) : null, sceneTransitionInfo, z, profilerInfo != null ? new android.app.ProfilerInfo(profilerInfo) : null, iBinder2, iActivityClientController, iBinder3, z2, iBinder4, iBinder5, new android.window.ActivityWindowInfo(activityWindowInfo));
        return launchActivityItem;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.os.IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        setValues(this, null, null, 0, null, null, null, 0, null, null, 0, null, null, null, null, null, false, null, null, null, null, false, null, null, null);
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mActivityToken);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeInt(this.mIdent);
        parcel.writeTypedObject(this.mInfo, i);
        parcel.writeTypedObject(this.mCurConfig, i);
        parcel.writeTypedObject(this.mOverrideConfig, i);
        parcel.writeInt(this.mDeviceId);
        parcel.writeString(this.mReferrer);
        parcel.writeStrongInterface(this.mVoiceInteractor);
        parcel.writeInt(this.mProcState);
        parcel.writeBundle(this.mState);
        parcel.writePersistableBundle(this.mPersistentState);
        parcel.writeTypedList(this.mPendingResults, i);
        parcel.writeTypedList(this.mPendingNewIntents, i);
        parcel.writeTypedObject(this.mSceneTransitionInfo, i);
        parcel.writeBoolean(this.mIsForward);
        parcel.writeTypedObject(this.mProfilerInfo, i);
        parcel.writeStrongBinder(this.mAssistToken);
        parcel.writeStrongInterface(this.mActivityClientController);
        parcel.writeStrongBinder(this.mShareableActivityToken);
        parcel.writeBoolean(this.mLaunchedFromBubble);
        parcel.writeStrongBinder(this.mTaskFragmentToken);
        parcel.writeStrongBinder(this.mInitialCallerInfoAccessToken);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private LaunchActivityItem(android.os.Parcel parcel) {
        setValues(this, parcel.readStrongBinder(), (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR), parcel.readInt(), (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR), (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR), (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR), parcel.readInt(), parcel.readString(), com.android.internal.app.IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readBundle(getClass().getClassLoader()), parcel.readPersistableBundle(getClass().getClassLoader()), parcel.createTypedArrayList(android.app.ResultInfo.CREATOR), parcel.createTypedArrayList(com.android.internal.content.ReferrerIntent.CREATOR), (android.app.ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(android.app.ActivityOptions.SceneTransitionInfo.CREATOR), parcel.readBoolean(), (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR), parcel.readStrongBinder(), android.app.IActivityClientController.Stub.asInterface(parcel.readStrongBinder()), parcel.readStrongBinder(), parcel.readBoolean(), parcel.readStrongBinder(), parcel.readStrongBinder(), (android.window.ActivityWindowInfo) parcel.readTypedObject(android.window.ActivityWindowInfo.CREATOR));
    }

    public boolean equals(java.lang.Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.servertransaction.LaunchActivityItem launchActivityItem = (android.app.servertransaction.LaunchActivityItem) obj;
        if ((this.mIntent == null && launchActivityItem.mIntent == null) || (this.mIntent != null && this.mIntent.filterEquals(launchActivityItem.mIntent))) {
            z = true;
        } else {
            z = false;
        }
        if (z && java.util.Objects.equals(this.mActivityToken, launchActivityItem.mActivityToken) && this.mIdent == launchActivityItem.mIdent && activityInfoEqual(launchActivityItem.mInfo) && java.util.Objects.equals(this.mCurConfig, launchActivityItem.mCurConfig) && java.util.Objects.equals(this.mOverrideConfig, launchActivityItem.mOverrideConfig) && this.mDeviceId == launchActivityItem.mDeviceId && java.util.Objects.equals(this.mReferrer, launchActivityItem.mReferrer) && this.mProcState == launchActivityItem.mProcState && areBundlesEqualRoughly(this.mState, launchActivityItem.mState) && areBundlesEqualRoughly(this.mPersistentState, launchActivityItem.mPersistentState) && java.util.Objects.equals(this.mPendingResults, launchActivityItem.mPendingResults) && java.util.Objects.equals(this.mPendingNewIntents, launchActivityItem.mPendingNewIntents)) {
            if (this.mSceneTransitionInfo == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (launchActivityItem.mSceneTransitionInfo == null) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z2 == z3 && this.mIsForward == launchActivityItem.mIsForward && java.util.Objects.equals(this.mProfilerInfo, launchActivityItem.mProfilerInfo) && java.util.Objects.equals(this.mAssistToken, launchActivityItem.mAssistToken) && java.util.Objects.equals(this.mShareableActivityToken, launchActivityItem.mShareableActivityToken) && java.util.Objects.equals(this.mTaskFragmentToken, launchActivityItem.mTaskFragmentToken) && java.util.Objects.equals(this.mInitialCallerInfoAccessToken, launchActivityItem.mInitialCallerInfoAccessToken) && java.util.Objects.equals(this.mActivityWindowInfo, launchActivityItem.mActivityWindowInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((527 + java.util.Objects.hashCode(this.mActivityToken)) * 31) + this.mIntent.filterHashCode()) * 31) + this.mIdent) * 31) + java.util.Objects.hashCode(this.mCurConfig)) * 31) + java.util.Objects.hashCode(this.mOverrideConfig)) * 31) + this.mDeviceId) * 31) + java.util.Objects.hashCode(this.mReferrer)) * 31) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mProcState))) * 31) + getRoughBundleHashCode(this.mState)) * 31) + getRoughBundleHashCode(this.mPersistentState)) * 31) + java.util.Objects.hashCode(this.mPendingResults)) * 31) + java.util.Objects.hashCode(this.mPendingNewIntents)) * 31) + (this.mSceneTransitionInfo != null ? 1 : 0)) * 31) + (this.mIsForward ? 1 : 0)) * 31) + java.util.Objects.hashCode(this.mProfilerInfo)) * 31) + java.util.Objects.hashCode(this.mAssistToken)) * 31) + java.util.Objects.hashCode(this.mShareableActivityToken)) * 31) + java.util.Objects.hashCode(this.mTaskFragmentToken)) * 31) + java.util.Objects.hashCode(this.mInitialCallerInfoAccessToken)) * 31) + java.util.Objects.hashCode(this.mActivityWindowInfo);
    }

    private boolean activityInfoEqual(android.content.pm.ActivityInfo activityInfo) {
        return this.mInfo == null ? activityInfo == null : activityInfo != null && this.mInfo.flags == activityInfo.flags && this.mInfo.getMaxAspectRatio() == activityInfo.getMaxAspectRatio() && java.util.Objects.equals(this.mInfo.launchToken, activityInfo.launchToken) && java.util.Objects.equals(this.mInfo.getComponentName(), activityInfo.getComponentName());
    }

    private static int getRoughBundleHashCode(android.os.BaseBundle baseBundle) {
        return (baseBundle == null || baseBundle.isDefinitelyEmpty()) ? 0 : 1;
    }

    private static boolean areBundlesEqualRoughly(android.os.BaseBundle baseBundle, android.os.BaseBundle baseBundle2) {
        return getRoughBundleHashCode(baseBundle) == getRoughBundleHashCode(baseBundle2);
    }

    public java.lang.String toString() {
        return "LaunchActivityItem{activityToken=" + this.mActivityToken + ",intent=" + this.mIntent + ",ident=" + this.mIdent + ",info=" + this.mInfo + ",curConfig=" + this.mCurConfig + ",overrideConfig=" + this.mOverrideConfig + ",deviceId=" + this.mDeviceId + ",referrer=" + this.mReferrer + ",procState=" + this.mProcState + ",state=" + this.mState + ",persistentState=" + this.mPersistentState + ",pendingResults=" + this.mPendingResults + ",pendingNewIntents=" + this.mPendingNewIntents + ",sceneTransitionInfo=" + this.mSceneTransitionInfo + ",profilerInfo=" + this.mProfilerInfo + ",assistToken=" + this.mAssistToken + ",shareableActivityToken=" + this.mShareableActivityToken + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }

    private static void setValues(android.app.servertransaction.LaunchActivityItem launchActivityItem, android.os.IBinder iBinder, android.content.Intent intent, int i, android.content.pm.ActivityInfo activityInfo, android.content.res.Configuration configuration, android.content.res.Configuration configuration2, int i2, java.lang.String str, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, android.app.ProfilerInfo profilerInfo, android.os.IBinder iBinder2, android.app.IActivityClientController iActivityClientController, android.os.IBinder iBinder3, boolean z2, android.os.IBinder iBinder4, android.os.IBinder iBinder5, android.window.ActivityWindowInfo activityWindowInfo) {
        launchActivityItem.mActivityToken = iBinder;
        launchActivityItem.mIntent = intent;
        launchActivityItem.mIdent = i;
        launchActivityItem.mInfo = activityInfo;
        launchActivityItem.mCurConfig = configuration;
        launchActivityItem.mOverrideConfig = configuration2;
        launchActivityItem.mDeviceId = i2;
        launchActivityItem.mReferrer = str;
        launchActivityItem.mVoiceInteractor = iVoiceInteractor;
        launchActivityItem.mProcState = i3;
        launchActivityItem.mState = bundle;
        launchActivityItem.mPersistentState = persistableBundle;
        launchActivityItem.mPendingResults = list;
        launchActivityItem.mPendingNewIntents = list2;
        launchActivityItem.mSceneTransitionInfo = sceneTransitionInfo;
        launchActivityItem.mIsForward = z;
        launchActivityItem.mProfilerInfo = profilerInfo;
        launchActivityItem.mAssistToken = iBinder2;
        launchActivityItem.mActivityClientController = iActivityClientController;
        launchActivityItem.mShareableActivityToken = iBinder3;
        launchActivityItem.mLaunchedFromBubble = z2;
        launchActivityItem.mTaskFragmentToken = iBinder4;
        launchActivityItem.mInitialCallerInfoAccessToken = iBinder5;
        launchActivityItem.mActivityWindowInfo = activityWindowInfo;
    }
}
