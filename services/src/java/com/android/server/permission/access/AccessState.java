package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public abstract class AccessState implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.MutableAccessState> {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.ExternalState, com.android.server.permission.access.MutableExternalState> externalStateReference;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.SystemState, com.android.server.permission.access.MutableSystemState> systemStateReference;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>> userStatesReference;

    public /* synthetic */ AccessState(com.android.server.permission.access.immutable.MutableReference mutableReference, com.android.server.permission.access.immutable.MutableReference mutableReference2, com.android.server.permission.access.immutable.MutableReference mutableReference3, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableReference, mutableReference2, mutableReference3);
    }

    private AccessState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.ExternalState, com.android.server.permission.access.MutableExternalState> mutableReference, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.SystemState, com.android.server.permission.access.MutableSystemState> mutableReference2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>> mutableReference3) {
        this.externalStateReference = mutableReference;
        this.systemStateReference = mutableReference2;
        this.userStatesReference = mutableReference3;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.ExternalState, com.android.server.permission.access.MutableExternalState> getExternalStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.externalStateReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.SystemState, com.android.server.permission.access.MutableSystemState> getSystemStateReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.systemStateReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState>> getUserStatesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.userStatesReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.ExternalState getExternalState() {
        return this.externalStateReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.SystemState getSystemState() {
        return this.systemStateReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.UserState, com.android.server.permission.access.MutableUserState> getUserStates() {
        return this.userStatesReference.get();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.MutableAccessState toMutable() {
        return new com.android.server.permission.access.MutableAccessState(this);
    }
}
