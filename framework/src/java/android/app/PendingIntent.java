package android.app;

/* loaded from: classes.dex */
public final class PendingIntent implements android.os.Parcelable {
    public static final long BLOCK_MUTABLE_IMPLICIT_PENDING_INTENT = 236704164;
    public static final int FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT = 16777216;
    public static final int FLAG_CANCEL_CURRENT = 268435456;
    public static final int FLAG_IMMUTABLE = 67108864;
    public static final int FLAG_MUTABLE = 33554432;

    @java.lang.Deprecated
    public static final int FLAG_MUTABLE_UNAUDITED = 33554432;
    public static final int FLAG_NO_CREATE = 536870912;
    public static final int FLAG_ONE_SHOT = 1073741824;
    public static final int FLAG_UPDATE_CURRENT = 134217728;
    static final long PENDING_INTENT_EXPLICIT_MUTABILITY_REQUIRED = 160794467;
    public static final long PENDING_INTENT_OPTIONS_CHECK = 320664730;
    private static final java.lang.String TAG = "PendingIntent";
    private android.app.ActivityManager.PendingIntentInfo mCachedInfo;
    private android.app.PendingIntent.CancelListerInfo mCancelListerInfo;
    private final android.content.IIntentSender mTarget;
    private android.os.IBinder mWhitelistToken;
    private static final java.lang.ThreadLocal<java.util.List<android.app.PendingIntent.OnMarshaledListener>> sOnMarshaledListener = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.app.PendingIntent$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return new java.util.ArrayList();
        }
    });
    public static final android.os.Parcelable.Creator<android.app.PendingIntent> CREATOR = new android.os.Parcelable.Creator<android.app.PendingIntent>() { // from class: android.app.PendingIntent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PendingIntent createFromParcel(android.os.Parcel parcel) {
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new android.app.PendingIntent(readStrongBinder, parcel.getClassCookie(android.app.PendingIntent.class));
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PendingIntent[] newArray(int i) {
            return new android.app.PendingIntent[i];
        }
    };

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public interface CancelListener {
        void onCanceled(android.app.PendingIntent pendingIntent);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public interface OnFinished {
        void onSendFinished(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle);
    }

    public interface OnMarshaledListener {
        void onMarshaled(android.app.PendingIntent pendingIntent, android.os.Parcel parcel, int i);
    }

    private final class CancelListerInfo extends com.android.internal.os.IResultReceiver.Stub {
        private final android.util.ArraySet<android.util.Pair<java.util.concurrent.Executor, android.app.PendingIntent.CancelListener>> mCancelListeners;
        private boolean mCanceled;

        private CancelListerInfo() {
            this.mCancelListeners = new android.util.ArraySet<>();
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            android.app.PendingIntent.this.notifyCancelListeners();
        }
    }

    public static class CanceledException extends android.util.AndroidException {
        public CanceledException() {
        }

        public CanceledException(java.lang.String str) {
            super(str);
        }

        public CanceledException(java.lang.Exception exc) {
            super(exc);
        }
    }

    private static class FinishedDispatcher extends android.content.IIntentReceiver.Stub implements java.lang.Runnable {
        private static android.os.Handler sDefaultSystemHandler;
        private final android.os.Handler mHandler;
        private android.content.Intent mIntent;
        private final android.app.PendingIntent mPendingIntent;
        private int mResultCode;
        private java.lang.String mResultData;
        private android.os.Bundle mResultExtras;
        private final android.app.PendingIntent.OnFinished mWho;

        FinishedDispatcher(android.app.PendingIntent pendingIntent, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler) {
            this.mPendingIntent = pendingIntent;
            this.mWho = onFinished;
            if (handler == null && android.app.ActivityThread.isSystem()) {
                if (sDefaultSystemHandler == null) {
                    sDefaultSystemHandler = new android.os.Handler(android.os.Looper.getMainLooper());
                }
                this.mHandler = sDefaultSystemHandler;
                return;
            }
            this.mHandler = handler;
        }

        @Override // android.content.IIntentReceiver
        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            this.mIntent = intent;
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            if (this.mHandler == null) {
                run();
            } else {
                this.mHandler.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mWho.onSendFinished(this.mPendingIntent, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    public static void setOnMarshaledListener(android.app.PendingIntent.OnMarshaledListener onMarshaledListener) {
        java.util.List<android.app.PendingIntent.OnMarshaledListener> list = sOnMarshaledListener.get();
        list.clear();
        if (onMarshaledListener != null) {
            list.add(onMarshaledListener);
        }
    }

    static void addOnMarshaledListener(android.app.PendingIntent.OnMarshaledListener onMarshaledListener) {
        sOnMarshaledListener.get().add(onMarshaledListener);
    }

    static void removeOnMarshaledListener(android.app.PendingIntent.OnMarshaledListener onMarshaledListener) {
        sOnMarshaledListener.get().remove(onMarshaledListener);
    }

    private static void checkPendingIntent(int i, android.content.Intent intent, android.content.Context context, boolean z) {
        boolean z2 = (67108864 & i) != 0;
        boolean z3 = (33554432 & i) != 0;
        java.lang.String packageName = context.getPackageName();
        if (z2 && z3) {
            throw new java.lang.IllegalArgumentException("Cannot set both FLAG_IMMUTABLE and FLAG_MUTABLE for PendingIntent");
        }
        if (android.compat.Compatibility.isChangeEnabled(PENDING_INTENT_EXPLICIT_MUTABILITY_REQUIRED) && !z2 && !z3) {
            throw new java.lang.IllegalArgumentException(packageName + ": Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.\nStrongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE if some functionality depends on the PendingIntent being mutable, e.g. if it needs to be used with inline replies or bubbles.");
        }
        if (isNewMutableDisallowedImplicitPendingIntent(i, intent, z) && !android.compat.Compatibility.isChangeEnabled(BLOCK_MUTABLE_IMPLICIT_PENDING_INTENT)) {
            android.util.Log.w(TAG, new android.app.StackTrace("New mutable implicit PendingIntent: pkg=" + packageName + ", action=" + intent.getAction() + ", featureId=" + context.getAttributionTag() + ". This will be blocked once the app targets U+ for security reasons."));
        }
    }

    public static boolean isNewMutableDisallowedImplicitPendingIntent(int i, android.content.Intent intent, boolean z) {
        if (z) {
            return false;
        }
        return !((536870912 & i) != 0) && ((33554432 & i) != 0) && (intent.getComponent() == null && intent.getPackage() == null) && !((i & 16777216) != 0);
    }

    public static android.app.PendingIntent getActivity(android.content.Context context, int i, android.content.Intent intent, int i2) {
        return getActivity(context, i, intent, i2, null);
    }

    public static android.app.PendingIntent getActivity(android.content.Context context, int i, android.content.Intent intent, int i2, android.os.Bundle bundle) {
        android.os.UserHandle user = context.getUser();
        if (user == null) {
            user = android.os.UserHandle.of(context.getUserId());
        }
        return getActivityAsUser(context, i, intent, i2, bundle, user);
    }

    public static android.app.PendingIntent getActivityAsUser(android.content.Context context, int i, android.content.Intent intent, int i2, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        java.lang.String packageName = context.getPackageName();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            android.content.IIntentSender intentSenderWithFeature = android.app.ActivityManager.getService().getIntentSenderWithFeature(2, packageName, context.getAttributionTag(), null, null, i, new android.content.Intent[]{intent}, resolveTypeIfNeeded != null ? new java.lang.String[]{resolveTypeIfNeeded} : null, i2, bundle, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new android.app.PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.app.PendingIntent getActivities(android.content.Context context, int i, android.content.Intent[] intentArr, int i2) {
        return getActivities(context, i, intentArr, i2, null);
    }

    public static android.app.PendingIntent getActivities(android.content.Context context, int i, android.content.Intent[] intentArr, int i2, android.os.Bundle bundle) {
        android.os.UserHandle user = context.getUser();
        if (user == null) {
            user = android.os.UserHandle.of(context.getUserId());
        }
        return getActivitiesAsUser(context, i, intentArr, i2, bundle, user);
    }

    public static android.app.PendingIntent getActivitiesAsUser(android.content.Context context, int i, android.content.Intent[] intentArr, int i2, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        java.lang.String packageName = context.getPackageName();
        java.lang.String[] strArr = new java.lang.String[intentArr.length];
        for (int i3 = 0; i3 < intentArr.length; i3++) {
            intentArr[i3].migrateExtraStreamToClipData(context);
            intentArr[i3].prepareToLeaveProcess(context);
            strArr[i3] = intentArr[i3].resolveTypeIfNeeded(context.getContentResolver());
            checkPendingIntent(i2, intentArr[i3], context, false);
        }
        try {
            android.content.IIntentSender intentSenderWithFeature = android.app.ActivityManager.getService().getIntentSenderWithFeature(2, packageName, context.getAttributionTag(), null, null, i, intentArr, strArr, i2, bundle, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new android.app.PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.app.PendingIntent getBroadcast(android.content.Context context, int i, android.content.Intent intent, int i2) {
        return getBroadcastAsUser(context, i, intent, i2, context.getUser());
    }

    public static android.app.PendingIntent getBroadcastAsUser(android.content.Context context, int i, android.content.Intent intent, int i2, android.os.UserHandle userHandle) {
        java.lang.String packageName = context.getPackageName();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.prepareToLeaveProcess(context);
            android.content.IIntentSender intentSenderWithFeature = android.app.ActivityManager.getService().getIntentSenderWithFeature(1, packageName, context.getAttributionTag(), null, null, i, new android.content.Intent[]{intent}, resolveTypeIfNeeded != null ? new java.lang.String[]{resolveTypeIfNeeded} : null, i2, null, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new android.app.PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.app.PendingIntent getService(android.content.Context context, int i, android.content.Intent intent, int i2) {
        return buildServicePendingIntent(context, i, intent, i2, 4);
    }

    public static android.app.PendingIntent getForegroundService(android.content.Context context, int i, android.content.Intent intent, int i2) {
        return buildServicePendingIntent(context, i, intent, i2, 5);
    }

    private static android.app.PendingIntent buildServicePendingIntent(android.content.Context context, int i, android.content.Intent intent, int i2, int i3) {
        java.lang.String packageName = context.getPackageName();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.prepareToLeaveProcess(context);
            android.content.IIntentSender intentSenderWithFeature = android.app.ActivityManager.getService().getIntentSenderWithFeature(i3, packageName, context.getAttributionTag(), null, null, i, new android.content.Intent[]{intent}, resolveTypeIfNeeded != null ? new java.lang.String[]{resolveTypeIfNeeded} : null, i2, null, context.getUserId());
            if (intentSenderWithFeature != null) {
                return new android.app.PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.IntentSender getIntentSender() {
        return new android.content.IntentSender(this.mTarget, this.mWhitelistToken);
    }

    public void cancel() {
        try {
            android.app.ActivityManager.getService().cancelIntentSender(this.mTarget);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void send() throws android.app.PendingIntent.CanceledException {
        send(null, 0, null, null, null, null, null);
    }

    public void send(int i) throws android.app.PendingIntent.CanceledException {
        send(null, i, null, null, null, null, null);
    }

    public void send(android.content.Context context, int i, android.content.Intent intent) throws android.app.PendingIntent.CanceledException {
        send(context, i, intent, null, null, null, null);
    }

    public void send(android.os.Bundle bundle) throws android.app.PendingIntent.CanceledException {
        send(null, 0, null, null, null, null, bundle);
    }

    public void send(int i, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler) throws android.app.PendingIntent.CanceledException {
        send(null, i, null, onFinished, handler, null, null);
    }

    public void send(android.content.Context context, int i, android.content.Intent intent, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler) throws android.app.PendingIntent.CanceledException {
        send(context, i, intent, onFinished, handler, null, null);
    }

    public void send(android.content.Context context, int i, android.content.Intent intent, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler, java.lang.String str) throws android.app.PendingIntent.CanceledException {
        send(context, i, intent, onFinished, handler, str, null);
    }

    public void send(android.content.Context context, int i, android.content.Intent intent, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler, java.lang.String str, android.os.Bundle bundle) throws android.app.PendingIntent.CanceledException {
        if (sendAndReturnResult(context, i, intent, onFinished, handler, str, bundle) < 0) {
            throw new android.app.PendingIntent.CanceledException();
        }
    }

    public int sendAndReturnResult(android.content.Context context, int i, android.content.Intent intent, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler, java.lang.String str, android.os.Bundle bundle) throws android.app.PendingIntent.CanceledException {
        java.lang.String resolveTypeIfNeeded;
        android.os.Bundle bundle2;
        android.app.PendingIntent.FinishedDispatcher finishedDispatcher;
        if (intent != null) {
            try {
                resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
            } catch (android.os.RemoteException e) {
                throw new android.app.PendingIntent.CanceledException(e);
            }
        } else {
            resolveTypeIfNeeded = null;
        }
        if (context != null && isActivity()) {
            android.app.ActivityOptions activityOptions = bundle != null ? new android.app.ActivityOptions(bundle) : android.app.ActivityOptions.makeBasic();
            activityOptions.setCallerDisplayId(context.getDisplayId());
            bundle2 = activityOptions.toBundle();
        } else {
            bundle2 = bundle;
        }
        android.app.ActivityThread.ApplicationThread applicationThread = android.app.ActivityThread.currentActivityThread().getApplicationThread();
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        android.content.IIntentSender iIntentSender = this.mTarget;
        android.os.IBinder iBinder = this.mWhitelistToken;
        if (onFinished != null) {
            finishedDispatcher = new android.app.PendingIntent.FinishedDispatcher(this, onFinished, handler);
        } else {
            finishedDispatcher = null;
        }
        return service.sendIntentSender(applicationThread, iIntentSender, iBinder, i, intent, resolveTypeIfNeeded, finishedDispatcher, str, bundle2);
    }

    @java.lang.Deprecated
    public java.lang.String getTargetPackage() {
        return getCreatorPackage();
    }

    public java.lang.String getCreatorPackage() {
        return getCachedInfo().getCreatorPackage();
    }

    public int getCreatorUid() {
        return getCachedInfo().getCreatorUid();
    }

    @java.lang.Deprecated
    public void registerCancelListener(android.app.PendingIntent.CancelListener cancelListener) {
        if (!addCancelListener(new android.app.PendingIntent$$ExternalSyntheticLambda0(), cancelListener)) {
            cancelListener.onCanceled(this);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean addCancelListener(java.util.concurrent.Executor executor, android.app.PendingIntent.CancelListener cancelListener) {
        synchronized (this.mTarget) {
            if (this.mCancelListerInfo != null && this.mCancelListerInfo.mCanceled) {
                return false;
            }
            if (this.mCancelListerInfo == null) {
                this.mCancelListerInfo = new android.app.PendingIntent.CancelListerInfo();
            }
            android.app.PendingIntent.CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            boolean isEmpty = cancelListerInfo.mCancelListeners.isEmpty();
            cancelListerInfo.mCancelListeners.add(android.util.Pair.create(executor, cancelListener));
            if (isEmpty) {
                try {
                    boolean registerIntentSenderCancelListenerEx = android.app.ActivityManager.getService().registerIntentSenderCancelListenerEx(this.mTarget, cancelListerInfo);
                    if (!registerIntentSenderCancelListenerEx) {
                        cancelListerInfo.mCanceled = true;
                    }
                    return registerIntentSenderCancelListenerEx;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return cancelListerInfo.mCanceled ? false : true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCancelListeners() {
        android.util.ArraySet arraySet;
        synchronized (this.mTarget) {
            android.app.PendingIntent.CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            cancelListerInfo.mCanceled = true;
            arraySet = new android.util.ArraySet(cancelListerInfo.mCancelListeners);
            cancelListerInfo.mCancelListeners.clear();
        }
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            final android.util.Pair pair = (android.util.Pair) arraySet.valueAt(i);
            ((java.util.concurrent.Executor) pair.first).execute(new java.lang.Runnable() { // from class: android.app.PendingIntent$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.PendingIntent.this.lambda$notifyCancelListeners$0(pair);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyCancelListeners$0(android.util.Pair pair) {
        ((android.app.PendingIntent.CancelListener) pair.second).onCanceled(this);
    }

    @java.lang.Deprecated
    public void unregisterCancelListener(android.app.PendingIntent.CancelListener cancelListener) {
        removeCancelListener(cancelListener);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void removeCancelListener(android.app.PendingIntent.CancelListener cancelListener) {
        synchronized (this.mTarget) {
            android.app.PendingIntent.CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            if (cancelListerInfo != null && cancelListerInfo.mCancelListeners.size() != 0) {
                for (int size = cancelListerInfo.mCancelListeners.size() - 1; size >= 0; size--) {
                    if (((android.util.Pair) cancelListerInfo.mCancelListeners.valueAt(size)).second == cancelListener) {
                        cancelListerInfo.mCancelListeners.removeAt(size);
                    }
                }
                if (cancelListerInfo.mCancelListeners.isEmpty()) {
                    try {
                        android.app.ActivityManager.getService().unregisterIntentSenderCancelListener(this.mTarget, cancelListerInfo);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public android.os.UserHandle getCreatorUserHandle() {
        return android.os.UserHandle.getUserHandleForUid(getCachedInfo().getCreatorUid());
    }

    public boolean isTargetedToPackage() {
        try {
            return android.app.ActivityManager.getService().isIntentSenderTargetedToPackage(this.mTarget);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isImmutable() {
        return getCachedInfo().isImmutable();
    }

    public boolean isActivity() {
        return getCachedInfo().getIntentSenderType() == 2;
    }

    public boolean isForegroundService() {
        return getCachedInfo().getIntentSenderType() == 5;
    }

    public boolean isService() {
        return getCachedInfo().getIntentSenderType() == 4;
    }

    public boolean isBroadcast() {
        return getCachedInfo().getIntentSenderType() == 1;
    }

    public android.content.Intent getIntent() {
        try {
            return android.app.ActivityManager.getService().getIntentForIntentSender(this.mTarget);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getTag(java.lang.String str) {
        try {
            return android.app.ActivityManager.getService().getTagForIntentSender(this.mTarget, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.content.pm.ResolveInfo> queryIntentComponents(int i) {
        try {
            android.content.pm.ParceledListSlice queryIntentComponentsForIntentSender = android.app.ActivityManager.getService().queryIntentComponentsForIntentSender(this.mTarget, i);
            if (queryIntentComponentsForIntentSender == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentComponentsForIntentSender.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean intentFilterEquals(android.app.PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return false;
        }
        try {
            return android.app.ActivityManager.getService().getIntentForIntentSender(pendingIntent.mTarget).filterEquals(getIntent());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.app.PendingIntent) {
            return this.mTarget.asBinder().equals(((android.app.PendingIntent) obj).mTarget.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("PendingIntent{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.mTarget.asBinder());
        sb.append('}');
        return sb.toString();
    }

    public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        android.content.Intent intent;
        if (android.app.Flags.visitRiskyUris() && (intent = (android.content.Intent) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: android.app.PendingIntent$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingSupplier
            public final java.lang.Object getOrThrow() {
                return android.app.PendingIntent.this.getIntent();
            }
        })) != null) {
            intent.visitUris(consumer);
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mTarget.asBinder().toString());
        protoOutputStream.end(start);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
        java.util.List<android.app.PendingIntent.OnMarshaledListener> list = sOnMarshaledListener.get();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            list.get(i2).onMarshaled(this, parcel, i);
        }
    }

    public static void writePendingIntentOrNullToParcel(android.app.PendingIntent pendingIntent, android.os.Parcel parcel) {
        parcel.writeStrongBinder(pendingIntent != null ? pendingIntent.mTarget.asBinder() : null);
        if (pendingIntent != null) {
            java.util.List<android.app.PendingIntent.OnMarshaledListener> list = sOnMarshaledListener.get();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).onMarshaled(pendingIntent, parcel, 0);
            }
        }
    }

    public static android.app.PendingIntent readPendingIntentOrNullFromParcel(android.os.Parcel parcel) {
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder != null) {
            return new android.app.PendingIntent(readStrongBinder, parcel.getClassCookie(android.app.PendingIntent.class));
        }
        return null;
    }

    public PendingIntent(android.content.IIntentSender iIntentSender) {
        this.mTarget = (android.content.IIntentSender) java.util.Objects.requireNonNull(iIntentSender);
    }

    PendingIntent(android.os.IBinder iBinder, java.lang.Object obj) {
        this.mTarget = (android.content.IIntentSender) java.util.Objects.requireNonNull(android.content.IIntentSender.Stub.asInterface(iBinder));
        if (obj != null) {
            this.mWhitelistToken = (android.os.IBinder) obj;
        }
    }

    public android.content.IIntentSender getTarget() {
        return this.mTarget;
    }

    public android.os.IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    private android.app.ActivityManager.PendingIntentInfo getCachedInfo() {
        if (this.mCachedInfo == null) {
            try {
                this.mCachedInfo = android.app.ActivityManager.getService().getInfoForIntentSender(this.mTarget);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mCachedInfo;
    }
}
