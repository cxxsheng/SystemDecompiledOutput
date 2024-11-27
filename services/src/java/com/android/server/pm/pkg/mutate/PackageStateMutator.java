package com.android.server.pm.pkg.mutate;

/* loaded from: classes2.dex */
public class PackageStateMutator {
    private static final java.util.concurrent.atomic.AtomicLong sStateChangeSequence = new java.util.concurrent.atomic.AtomicLong();
    private final java.util.function.Function<java.lang.String, com.android.server.pm.PackageSetting> mActiveStateFunction;
    private final java.util.function.Function<java.lang.String, com.android.server.pm.PackageSetting> mDisabledStateFunction;
    private final com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper mStateWrite = new com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper();
    private final android.util.ArraySet<com.android.server.pm.PackageSetting> mChangedStates = new android.util.ArraySet<>();

    public PackageStateMutator(@android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.PackageSetting> function, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.PackageSetting> function2) {
        this.mActiveStateFunction = function;
        this.mDisabledStateFunction = function2;
    }

    public static void onPackageStateChanged() {
        sStateChangeSequence.incrementAndGet();
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateWrite forPackage(@android.annotation.NonNull java.lang.String str) {
        return setState(this.mActiveStateFunction.apply(str));
    }

    @android.annotation.Nullable
    public com.android.server.pm.pkg.mutate.PackageStateWrite forPackageNullable(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageSetting apply = this.mActiveStateFunction.apply(str);
        setState(apply);
        if (apply == null) {
            return null;
        }
        return setState(apply);
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateWrite forDisabledSystemPackage(@android.annotation.NonNull java.lang.String str) {
        return setState(this.mDisabledStateFunction.apply(str));
    }

    @android.annotation.Nullable
    public com.android.server.pm.pkg.mutate.PackageStateWrite forDisabledSystemPackageNullable(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageSetting apply = this.mDisabledStateFunction.apply(str);
        if (apply == null) {
            return null;
        }
        return setState(apply);
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState(int i) {
        return new com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState(i, sStateChangeSequence.get());
    }

    @android.annotation.Nullable
    public com.android.server.pm.pkg.mutate.PackageStateMutator.Result generateResult(@android.annotation.Nullable com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, int i) {
        if (initialState == null) {
            return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS;
        }
        boolean z = i != initialState.mPackageSequence;
        boolean z2 = sStateChangeSequence.get() != initialState.mStateSequence;
        if (z && z2) {
            return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.PACKAGES_AND_STATE_CHANGED;
        }
        if (z) {
            return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.PACKAGES_CHANGED;
        }
        if (z2) {
            return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.STATE_CHANGED;
        }
        return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS;
    }

    public void onFinished() {
        for (int i = 0; i < this.mChangedStates.size(); i++) {
            this.mChangedStates.valueAt(i).onChanged();
        }
    }

    @android.annotation.NonNull
    private com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper setState(@android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting) {
        if (packageSetting != null) {
            this.mChangedStates.add(packageSetting);
        }
        return this.mStateWrite.setState(packageSetting);
    }

    public static class InitialState {
        private final int mPackageSequence;
        private final long mStateSequence;

        public InitialState(int i, long j) {
            this.mPackageSequence = i;
            this.mStateSequence = j;
        }
    }

    public static class Result {
        private final boolean mCommitted;
        private final boolean mPackagesChanged;
        private final boolean mSpecificPackageNull;
        private final boolean mStateChanged;
        public static final com.android.server.pm.pkg.mutate.PackageStateMutator.Result SUCCESS = new com.android.server.pm.pkg.mutate.PackageStateMutator.Result(true, false, false, false);
        public static final com.android.server.pm.pkg.mutate.PackageStateMutator.Result PACKAGES_CHANGED = new com.android.server.pm.pkg.mutate.PackageStateMutator.Result(false, true, false, false);
        public static final com.android.server.pm.pkg.mutate.PackageStateMutator.Result STATE_CHANGED = new com.android.server.pm.pkg.mutate.PackageStateMutator.Result(false, false, true, false);
        public static final com.android.server.pm.pkg.mutate.PackageStateMutator.Result PACKAGES_AND_STATE_CHANGED = new com.android.server.pm.pkg.mutate.PackageStateMutator.Result(false, true, true, false);
        public static final com.android.server.pm.pkg.mutate.PackageStateMutator.Result SPECIFIC_PACKAGE_NULL = new com.android.server.pm.pkg.mutate.PackageStateMutator.Result(false, false, true, true);

        public Result(boolean z, boolean z2, boolean z3, boolean z4) {
            this.mCommitted = z;
            this.mPackagesChanged = z2;
            this.mStateChanged = z3;
            this.mSpecificPackageNull = z4;
        }

        public boolean isCommitted() {
            return this.mCommitted;
        }

        public boolean isPackagesChanged() {
            return this.mPackagesChanged;
        }

        public boolean isStateChanged() {
            return this.mStateChanged;
        }

        public boolean isSpecificPackageNull() {
            return this.mSpecificPackageNull;
        }
    }

    private static class StateWriteWrapper implements com.android.server.pm.pkg.mutate.PackageStateWrite {

        @android.annotation.NonNull
        private com.android.server.pm.PackageSetting mState;
        private final com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper mUserStateWrite;

        private StateWriteWrapper() {
            this.mUserStateWrite = new com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper();
        }

        public com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper setState(com.android.server.pm.PackageSetting packageSetting) {
            this.mState = packageSetting;
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageUserStateWrite userState(int i) {
            com.android.server.pm.pkg.PackageUserStateImpl orCreateUserState = this.mState == null ? null : this.mState.getOrCreateUserState(i);
            if (orCreateUserState != null) {
                orCreateUserState.setWatchable(this.mState);
            }
            return this.mUserStateWrite.setStates(orCreateUserState);
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        public void onChanged() {
            if (this.mState != null) {
                this.mState.onChanged();
            }
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        public com.android.server.pm.pkg.mutate.PackageStateWrite setLastPackageUsageTime(int i, long j) {
            if (this.mState != null) {
                this.mState.getTransientState().setLastPackageUsageTimeInMills(i, j);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        public com.android.server.pm.pkg.mutate.PackageStateWrite setHiddenUntilInstalled(boolean z) {
            if (this.mState != null) {
                this.mState.getTransientState().setHiddenUntilInstalled(z);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setRequiredForSystemUser(boolean z) {
            if (this.mState != null) {
                if (z) {
                    this.mState.setPrivateFlags(this.mState.getPrivateFlags() | 512);
                } else {
                    this.mState.setPrivateFlags(this.mState.getPrivateFlags() & (-513));
                }
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setMimeGroup(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
            if (this.mState != null) {
                this.mState.setMimeGroup(str, arraySet);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setCategoryOverride(int i) {
            if (this.mState != null) {
                this.mState.setCategoryOverride(i);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setUpdateAvailable(boolean z) {
            if (this.mState != null) {
                this.mState.setUpdateAvailable(z);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setLoadingProgress(float f) {
            if (this.mState != null) {
                this.mState.setLoadingProgress(f);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setLoadingCompletedTime(long j) {
            if (this.mState != null) {
                this.mState.setLoadingCompletedTime(j);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setOverrideSeInfo(@android.annotation.Nullable java.lang.String str) {
            if (this.mState != null) {
                this.mState.getTransientState().setOverrideSeInfo(str);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setInstaller(@android.annotation.Nullable java.lang.String str, int i) {
            if (this.mState != null) {
                this.mState.setInstallerPackage(str, i);
            }
            return this;
        }

        @Override // com.android.server.pm.pkg.mutate.PackageStateWrite
        @android.annotation.NonNull
        public com.android.server.pm.pkg.mutate.PackageStateWrite setUpdateOwner(@android.annotation.NonNull java.lang.String str) {
            if (this.mState != null) {
                this.mState.setUpdateOwnerPackage(str);
            }
            return this;
        }

        private static class UserStateWriteWrapper implements com.android.server.pm.pkg.mutate.PackageUserStateWrite {

            @android.annotation.Nullable
            private com.android.server.pm.pkg.PackageUserStateImpl mUserState;

            private UserStateWriteWrapper() {
            }

            public com.android.server.pm.pkg.mutate.PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper setStates(@android.annotation.Nullable com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl) {
                this.mUserState = packageUserStateImpl;
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setInstalled(boolean z) {
                if (this.mUserState != null) {
                    this.mUserState.setInstalled(z);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setUninstallReason(int i) {
                if (this.mUserState != null) {
                    this.mUserState.setUninstallReason(i);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setDistractionFlags(int i) {
                if (this.mUserState != null) {
                    this.mUserState.setDistractionFlags(i);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite putSuspendParams(@android.annotation.NonNull android.content.pm.UserPackage userPackage, @android.annotation.Nullable com.android.server.pm.pkg.SuspendParams suspendParams) {
                if (this.mUserState != null) {
                    this.mUserState.putSuspendParams(userPackage, suspendParams);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite removeSuspension(@android.annotation.NonNull android.content.pm.UserPackage userPackage) {
                if (this.mUserState != null) {
                    this.mUserState.removeSuspension(userPackage);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setHidden(boolean z) {
                if (this.mUserState != null) {
                    this.mUserState.setHidden(z);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setStopped(boolean z) {
                if (this.mUserState != null) {
                    this.mUserState.setStopped(z);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setNotLaunched(boolean z) {
                if (this.mUserState != null) {
                    this.mUserState.setNotLaunched(z);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setOverlayPaths(@android.annotation.NonNull android.content.pm.overlay.OverlayPaths overlayPaths) {
                if (this.mUserState != null) {
                    this.mUserState.setOverlayPaths(overlayPaths);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setOverlayPathsForLibrary(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.pm.overlay.OverlayPaths overlayPaths) {
                if (this.mUserState != null) {
                    this.mUserState.setSharedLibraryOverlayPaths(str, overlayPaths);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setHarmfulAppWarning(@android.annotation.Nullable java.lang.String str) {
                if (this.mUserState != null) {
                    this.mUserState.setHarmfulAppWarning(str);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setSplashScreenTheme(@android.annotation.Nullable java.lang.String str) {
                if (this.mUserState != null) {
                    this.mUserState.setSplashScreenTheme(str);
                }
                return this;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setComponentLabelIcon(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) {
                if (this.mUserState != null) {
                    this.mUserState.overrideLabelAndIcon(componentName, str, num);
                    return null;
                }
                return null;
            }

            @Override // com.android.server.pm.pkg.mutate.PackageUserStateWrite
            @android.annotation.NonNull
            public com.android.server.pm.pkg.mutate.PackageUserStateWrite setMinAspectRatio(int i) {
                if (this.mUserState != null) {
                    this.mUserState.setMinAspectRatio(i);
                }
                return this;
            }
        }
    }
}
