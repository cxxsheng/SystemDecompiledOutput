package android.telephony.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class EuiccCardManager {
    public static final int CANCEL_REASON_END_USER_REJECTED = 0;
    public static final int CANCEL_REASON_POSTPONED = 1;
    public static final int CANCEL_REASON_PPR_NOT_ALLOWED = 3;
    public static final int CANCEL_REASON_TIMEOUT = 2;
    public static final int RESET_OPTION_DELETE_FIELD_LOADED_TEST_PROFILES = 2;
    public static final int RESET_OPTION_DELETE_OPERATIONAL_PROFILES = 1;
    public static final int RESET_OPTION_RESET_DEFAULT_SMDP_ADDRESS = 4;
    public static final int RESULT_CALLER_NOT_ALLOWED = -3;
    public static final int RESULT_EUICC_NOT_FOUND = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_PROFILE_DOES_NOT_EXIST = -4;
    public static final int RESULT_PROFILE_NOT_FOUND = 1;
    public static final int RESULT_UNKNOWN_ERROR = -1;
    private static final java.lang.String TAG = "EuiccCardManager";
    private final android.content.Context mContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CancelReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResetOption {
    }

    public interface ResultCallback<T> {
        void onComplete(int i, T t);
    }

    public EuiccCardManager(android.content.Context context) {
        this.mContext = context;
    }

    private com.android.internal.telephony.euicc.IEuiccCardController getIEuiccCardController() {
        return com.android.internal.telephony.euicc.IEuiccCardController.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getEuiccCardControllerServiceRegisterer().get());
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.euicc.IGetAllProfilesCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
        public void onComplete(final int i, final android.service.euicc.EuiccProfileInfo[] euiccProfileInfoArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccProfileInfoArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestAllProfiles(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.service.euicc.EuiccProfileInfo[]> resultCallback) {
        try {
            getIEuiccCardController().getAllProfiles(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass1(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getAllProfiles", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.internal.telephony.euicc.IGetProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass2(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetProfileCallback
        public void onComplete(final int i, final android.service.euicc.EuiccProfileInfo euiccProfileInfo) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccProfileInfo);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestProfile(java.lang.String str, java.lang.String str2, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.service.euicc.EuiccProfileInfo> resultCallback) {
        try {
            getIEuiccCardController().getProfile(this.mContext.getOpPackageName(), str, str2, new android.telephony.euicc.EuiccCardManager.AnonymousClass2(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestEnabledProfileForPort(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.service.euicc.EuiccProfileInfo> resultCallback) {
        try {
            getIEuiccCardController().getEnabledProfile(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass3(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling requestEnabledProfileForPort", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$3, reason: invalid class name */
    class AnonymousClass3 extends com.android.internal.telephony.euicc.IGetProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass3(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetProfileCallback
        public void onComplete(final int i, final android.service.euicc.EuiccProfileInfo euiccProfileInfo) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccProfileInfo);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$4, reason: invalid class name */
    class AnonymousClass4 extends com.android.internal.telephony.euicc.IDisableProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass4(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IDisableProfileCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void disableProfile(java.lang.String str, java.lang.String str2, boolean z, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().disableProfile(this.mContext.getOpPackageName(), str, str2, z, new android.telephony.euicc.EuiccCardManager.AnonymousClass4(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling disableProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void switchToProfile(java.lang.String str, java.lang.String str2, boolean z, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.service.euicc.EuiccProfileInfo> resultCallback) {
        try {
            getIEuiccCardController().switchToProfile(this.mContext.getOpPackageName(), str, str2, 0, z, new android.telephony.euicc.EuiccCardManager.AnonymousClass5(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling switchToProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$5, reason: invalid class name */
    class AnonymousClass5 extends com.android.internal.telephony.euicc.ISwitchToProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass5(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
        public void onComplete(final int i, final android.service.euicc.EuiccProfileInfo euiccProfileInfo) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccProfileInfo);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$6, reason: invalid class name */
    class AnonymousClass6 extends com.android.internal.telephony.euicc.ISwitchToProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass6(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
        public void onComplete(final int i, final android.service.euicc.EuiccProfileInfo euiccProfileInfo) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccProfileInfo);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void switchToProfile(java.lang.String str, java.lang.String str2, int i, boolean z, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.service.euicc.EuiccProfileInfo> resultCallback) {
        try {
            getIEuiccCardController().switchToProfile(this.mContext.getOpPackageName(), str, str2, i, z, new android.telephony.euicc.EuiccCardManager.AnonymousClass6(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling switchToProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$7, reason: invalid class name */
    class AnonymousClass7 extends com.android.internal.telephony.euicc.ISetNicknameCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass7(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISetNicknameCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$7$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void setNickname(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().setNickname(this.mContext.getOpPackageName(), str, str2, str3, new android.telephony.euicc.EuiccCardManager.AnonymousClass7(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setNickname", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$8, reason: invalid class name */
    class AnonymousClass8 extends com.android.internal.telephony.euicc.IDeleteProfileCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass8(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IDeleteProfileCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void deleteProfile(java.lang.String str, java.lang.String str2, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().deleteProfile(this.mContext.getOpPackageName(), str, str2, new android.telephony.euicc.EuiccCardManager.AnonymousClass8(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling deleteProfile", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$9, reason: invalid class name */
    class AnonymousClass9 extends com.android.internal.telephony.euicc.IResetMemoryCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass9(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IResetMemoryCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$9$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void resetMemory(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().resetMemory(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass9(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling resetMemory", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$10, reason: invalid class name */
    class AnonymousClass10 extends com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass10(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback
        public void onComplete(final int i, final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$10$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestDefaultSmdpAddress(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.String> resultCallback) {
        try {
            getIEuiccCardController().getDefaultSmdpAddress(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass10(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getDefaultSmdpAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$11, reason: invalid class name */
    class AnonymousClass11 extends com.android.internal.telephony.euicc.IGetSmdsAddressCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass11(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetSmdsAddressCallback
        public void onComplete(final int i, final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$11$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestSmdsAddress(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.String> resultCallback) {
        try {
            getIEuiccCardController().getSmdsAddress(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass11(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getSmdsAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDefaultSmdpAddress(java.lang.String str, java.lang.String str2, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().setDefaultSmdpAddress(this.mContext.getOpPackageName(), str, str2, new android.telephony.euicc.EuiccCardManager.AnonymousClass12(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setDefaultSmdpAddress", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$12, reason: invalid class name */
    class AnonymousClass12 extends com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass12(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$12$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$13, reason: invalid class name */
    class AnonymousClass13 extends com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass13(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
        public void onComplete(final int i, final android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$13$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccRulesAuthTable);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestRulesAuthTable(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.telephony.euicc.EuiccRulesAuthTable> resultCallback) {
        try {
            getIEuiccCardController().getRulesAuthTable(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass13(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getRulesAuthTable", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$14, reason: invalid class name */
    class AnonymousClass14 extends com.android.internal.telephony.euicc.IGetEuiccChallengeCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass14(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccChallengeCallback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$14$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestEuiccChallenge(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().getEuiccChallenge(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass14(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getEuiccChallenge", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$15, reason: invalid class name */
    class AnonymousClass15 extends com.android.internal.telephony.euicc.IGetEuiccInfo1Callback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass15(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo1Callback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$15$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestEuiccInfo1(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().getEuiccInfo1(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass15(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getEuiccInfo1", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$16, reason: invalid class name */
    class AnonymousClass16 extends com.android.internal.telephony.euicc.IGetEuiccInfo2Callback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass16(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo2Callback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$16$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void requestEuiccInfo2(java.lang.String str, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().getEuiccInfo2(this.mContext.getOpPackageName(), str, new android.telephony.euicc.EuiccCardManager.AnonymousClass16(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getEuiccInfo2", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void authenticateServer(java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().authenticateServer(this.mContext.getOpPackageName(), str, str2, bArr, bArr2, bArr3, bArr4, new android.telephony.euicc.EuiccCardManager.AnonymousClass17(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling authenticateServer", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$17, reason: invalid class name */
    class AnonymousClass17 extends com.android.internal.telephony.euicc.IAuthenticateServerCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass17(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$17$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void prepareDownload(java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().prepareDownload(this.mContext.getOpPackageName(), str, bArr, bArr2, bArr3, bArr4, new android.telephony.euicc.EuiccCardManager.AnonymousClass18(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling prepareDownload", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$18, reason: invalid class name */
    class AnonymousClass18 extends com.android.internal.telephony.euicc.IPrepareDownloadCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass18(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IPrepareDownloadCallback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$18$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void loadBoundProfilePackage(java.lang.String str, byte[] bArr, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().loadBoundProfilePackage(this.mContext.getOpPackageName(), str, bArr, new android.telephony.euicc.EuiccCardManager.AnonymousClass19(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling loadBoundProfilePackage", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$19, reason: invalid class name */
    class AnonymousClass19 extends com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass19(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$19$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void cancelSession(java.lang.String str, byte[] bArr, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<byte[]> resultCallback) {
        try {
            getIEuiccCardController().cancelSession(this.mContext.getOpPackageName(), str, bArr, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass20(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling cancelSession", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$20, reason: invalid class name */
    class AnonymousClass20 extends com.android.internal.telephony.euicc.ICancelSessionCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass20(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.ICancelSessionCallback
        public void onComplete(final int i, final byte[] bArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$20$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, bArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$21, reason: invalid class name */
    class AnonymousClass21 extends com.android.internal.telephony.euicc.IListNotificationsCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass21(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
        public void onComplete(final int i, final android.telephony.euicc.EuiccNotification[] euiccNotificationArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$21$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccNotificationArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void listNotifications(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.telephony.euicc.EuiccNotification[]> resultCallback) {
        try {
            getIEuiccCardController().listNotifications(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass21(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling listNotifications", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$22, reason: invalid class name */
    class AnonymousClass22 extends com.android.internal.telephony.euicc.IRetrieveNotificationListCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass22(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRetrieveNotificationListCallback
        public void onComplete(final int i, final android.telephony.euicc.EuiccNotification[] euiccNotificationArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$22$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccNotificationArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void retrieveNotificationList(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.telephony.euicc.EuiccNotification[]> resultCallback) {
        try {
            getIEuiccCardController().retrieveNotificationList(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass22(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling retrieveNotificationList", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$23, reason: invalid class name */
    class AnonymousClass23 extends com.android.internal.telephony.euicc.IRetrieveNotificationCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass23(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRetrieveNotificationCallback
        public void onComplete(final int i, final android.telephony.euicc.EuiccNotification euiccNotification) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$23$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, euiccNotification);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void retrieveNotification(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<android.telephony.euicc.EuiccNotification> resultCallback) {
        try {
            getIEuiccCardController().retrieveNotification(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass23(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling retrieveNotification", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeNotificationFromList(java.lang.String str, int i, java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback<java.lang.Void> resultCallback) {
        try {
            getIEuiccCardController().removeNotificationFromList(this.mContext.getOpPackageName(), str, i, new android.telephony.euicc.EuiccCardManager.AnonymousClass24(executor, resultCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling removeNotificationFromList", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.euicc.EuiccCardManager$24, reason: invalid class name */
    class AnonymousClass24 extends com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback.Stub {
        final /* synthetic */ android.telephony.euicc.EuiccCardManager.ResultCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass24(java.util.concurrent.Executor executor, android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback) {
            this.val$executor = executor;
            this.val$callback = resultCallback;
        }

        @Override // com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback
        public void onComplete(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.euicc.EuiccCardManager.ResultCallback resultCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.euicc.EuiccCardManager$24$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.euicc.EuiccCardManager.ResultCallback.this.onComplete(i, null);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
