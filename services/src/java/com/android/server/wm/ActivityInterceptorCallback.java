package com.android.server.wm;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public interface ActivityInterceptorCallback {
    public static final int DREAM_MANAGER_ORDERED_ID = 4;
    public static final int MAINLINE_FIRST_ORDERED_ID = 1000;
    public static final int MAINLINE_LAST_ORDERED_ID = 1001;
    public static final int MAINLINE_SDK_SANDBOX_ORDER_ID = 1001;
    public static final int PERMISSION_POLICY_ORDERED_ID = 1;
    public static final int PRODUCT_ORDERED_ID = 5;
    public static final int SYSTEM_FIRST_ORDERED_ID = 0;
    public static final int SYSTEM_LAST_ORDERED_ID = 5;
    public static final int VIRTUAL_DEVICE_SERVICE_ORDERED_ID = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OrderedId {
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(@android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo);

    default void onActivityLaunched(@android.annotation.NonNull android.app.TaskInfo taskInfo, @android.annotation.NonNull android.content.pm.ActivityInfo activityInfo, @android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
    }

    static boolean isValidOrderId(int i) {
        return isValidMainlineOrderId(i) || (i >= 0 && i <= 5);
    }

    static boolean isValidMainlineOrderId(int i) {
        return i >= 1000 && i <= 1001;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static final class ActivityInterceptorInfo {

        @android.annotation.NonNull
        private final android.content.pm.ActivityInfo mActivityInfo;

        @android.annotation.Nullable
        private final java.lang.String mCallingFeatureId;

        @android.annotation.Nullable
        private final java.lang.String mCallingPackage;
        private final int mCallingPid;
        private final int mCallingUid;

        @android.annotation.Nullable
        private final android.app.ActivityOptions mCheckedOptions;

        @android.annotation.Nullable
        private final java.lang.Runnable mClearOptionsAnimation;
        private final android.content.Intent mIntent;
        private final int mRealCallingPid;
        private final int mRealCallingUid;

        @android.annotation.NonNull
        private final android.content.pm.ResolveInfo mResolveInfo;

        @android.annotation.Nullable
        private final java.lang.String mResolvedType;
        private final int mUserId;

        public ActivityInterceptorInfo(com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder builder) {
            this.mCallingUid = builder.mCallingUid;
            this.mCallingPid = builder.mCallingPid;
            this.mRealCallingUid = builder.mRealCallingUid;
            this.mRealCallingPid = builder.mRealCallingPid;
            this.mUserId = builder.mUserId;
            this.mIntent = builder.mIntent;
            this.mResolveInfo = builder.mResolveInfo;
            this.mActivityInfo = builder.mActivityInfo;
            this.mResolvedType = builder.mResolvedType;
            this.mCallingPackage = builder.mCallingPackage;
            this.mCallingFeatureId = builder.mCallingFeatureId;
            this.mCheckedOptions = builder.mCheckedOptions;
            this.mClearOptionsAnimation = builder.mClearOptionsAnimation;
        }

        public static final class Builder {

            @android.annotation.NonNull
            private final android.content.pm.ActivityInfo mActivityInfo;
            private final int mCallingPid;
            private final int mCallingUid;
            private final android.content.Intent mIntent;
            private final int mRealCallingPid;
            private final int mRealCallingUid;

            @android.annotation.NonNull
            private final android.content.pm.ResolveInfo mResolveInfo;

            @android.annotation.Nullable
            private java.lang.String mResolvedType;
            private final int mUserId;

            @android.annotation.Nullable
            private java.lang.String mCallingPackage = null;

            @android.annotation.Nullable
            private java.lang.String mCallingFeatureId = null;

            @android.annotation.Nullable
            private android.app.ActivityOptions mCheckedOptions = null;

            @android.annotation.Nullable
            private java.lang.Runnable mClearOptionsAnimation = null;

            public Builder(int i, int i2, int i3, int i4, int i5, @android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo, @android.annotation.NonNull android.content.pm.ActivityInfo activityInfo) {
                this.mCallingUid = i;
                this.mCallingPid = i2;
                this.mRealCallingUid = i3;
                this.mRealCallingPid = i4;
                this.mUserId = i5;
                this.mIntent = intent;
                this.mResolveInfo = resolveInfo;
                this.mActivityInfo = activityInfo;
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo build() {
                return new com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo(this);
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder setResolvedType(@android.annotation.Nullable java.lang.String str) {
                this.mResolvedType = str;
                return this;
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder setCallingPackage(@android.annotation.Nullable java.lang.String str) {
                this.mCallingPackage = str;
                return this;
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder setCallingFeatureId(@android.annotation.Nullable java.lang.String str) {
                this.mCallingFeatureId = str;
                return this;
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder setCheckedOptions(@android.annotation.Nullable android.app.ActivityOptions activityOptions) {
                this.mCheckedOptions = activityOptions;
                return this;
            }

            @android.annotation.NonNull
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder setClearOptionsAnimationRunnable(@android.annotation.Nullable java.lang.Runnable runnable) {
                this.mClearOptionsAnimation = runnable;
                return this;
            }
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public int getCallingPid() {
            return this.mCallingPid;
        }

        public int getRealCallingUid() {
            return this.mRealCallingUid;
        }

        public int getRealCallingPid() {
            return this.mRealCallingPid;
        }

        public int getUserId() {
            return this.mUserId;
        }

        @android.annotation.NonNull
        public android.content.Intent getIntent() {
            return this.mIntent;
        }

        @android.annotation.NonNull
        public android.content.pm.ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        @android.annotation.NonNull
        public android.content.pm.ActivityInfo getActivityInfo() {
            return this.mActivityInfo;
        }

        @android.annotation.Nullable
        public java.lang.String getResolvedType() {
            return this.mResolvedType;
        }

        @android.annotation.Nullable
        public java.lang.String getCallingPackage() {
            return this.mCallingPackage;
        }

        @android.annotation.Nullable
        public java.lang.String getCallingFeatureId() {
            return this.mCallingFeatureId;
        }

        @android.annotation.Nullable
        public android.app.ActivityOptions getCheckedOptions() {
            return this.mCheckedOptions;
        }

        @android.annotation.Nullable
        public java.lang.Runnable getClearOptionsAnimationRunnable() {
            return this.mClearOptionsAnimation;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static final class ActivityInterceptResult {

        @android.annotation.NonNull
        private final android.app.ActivityOptions mActivityOptions;
        private final boolean mActivityResolved;

        @android.annotation.NonNull
        private final android.content.Intent mIntent;

        public ActivityInterceptResult(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.app.ActivityOptions activityOptions) {
            this(intent, activityOptions, false);
        }

        public ActivityInterceptResult(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.app.ActivityOptions activityOptions, boolean z) {
            this.mIntent = intent;
            this.mActivityOptions = activityOptions;
            this.mActivityResolved = z;
        }

        @android.annotation.NonNull
        public android.content.Intent getIntent() {
            return this.mIntent;
        }

        @android.annotation.NonNull
        public android.app.ActivityOptions getActivityOptions() {
            return this.mActivityOptions;
        }

        public boolean isActivityResolved() {
            return this.mActivityResolved;
        }
    }
}
