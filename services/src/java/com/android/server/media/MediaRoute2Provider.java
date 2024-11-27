package com.android.server.media;

/* loaded from: classes2.dex */
abstract class MediaRoute2Provider {
    com.android.server.media.MediaRoute2Provider.Callback mCallback;
    final android.content.ComponentName mComponentName;
    boolean mIsSystemRouteProvider;
    private volatile android.media.MediaRoute2ProviderInfo mProviderInfo;
    final java.lang.String mUniqueId;
    final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.List<android.media.RoutingSessionInfo> mSessionInfos = new java.util.ArrayList();

    public interface Callback {
        void onProviderStateChanged(@android.annotation.Nullable com.android.server.media.MediaRoute2Provider mediaRoute2Provider);

        void onRequestFailed(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, int i);

        void onSessionCreated(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, @android.annotation.Nullable android.media.RoutingSessionInfo routingSessionInfo);

        void onSessionReleased(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo);

        void onSessionUpdated(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo);
    }

    public abstract void deselectRoute(long j, java.lang.String str, java.lang.String str2);

    protected abstract java.lang.String getDebugString();

    public abstract void prepareReleaseSession(@android.annotation.NonNull java.lang.String str);

    public abstract void releaseSession(long j, java.lang.String str);

    public abstract void requestCreateSession(long j, java.lang.String str, java.lang.String str2, @android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str3);

    public abstract void selectRoute(long j, java.lang.String str, java.lang.String str2);

    public abstract void setRouteVolume(long j, java.lang.String str, int i);

    public abstract void setSessionVolume(long j, java.lang.String str, int i);

    public abstract void transferToRoute(long j, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, java.lang.String str2, java.lang.String str3, int i);

    public abstract void updateDiscoveryPreference(java.util.Set<java.lang.String> set, android.media.RouteDiscoveryPreference routeDiscoveryPreference);

    MediaRoute2Provider(@android.annotation.NonNull android.content.ComponentName componentName) {
        java.util.Objects.requireNonNull(componentName, "Component name must not be null.");
        this.mComponentName = componentName;
        this.mUniqueId = componentName.flattenToShortString();
    }

    public void setCallback(com.android.server.media.MediaRoute2Provider.Callback callback) {
        this.mCallback = callback;
    }

    @android.annotation.NonNull
    public java.lang.String getUniqueId() {
        return this.mUniqueId;
    }

    @android.annotation.Nullable
    public android.media.MediaRoute2ProviderInfo getProviderInfo() {
        return this.mProviderInfo;
    }

    @android.annotation.NonNull
    public java.util.List<android.media.RoutingSessionInfo> getSessionInfos() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mSessionInfos);
        }
        return arrayList;
    }

    void setProviderState(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        if (mediaRoute2ProviderInfo == null) {
            this.mProviderInfo = null;
        } else {
            this.mProviderInfo = new android.media.MediaRoute2ProviderInfo.Builder(mediaRoute2ProviderInfo).setUniqueId(this.mComponentName.getPackageName(), this.mUniqueId).setSystemRouteProvider(this.mIsSystemRouteProvider).build();
        }
    }

    void notifyProviderState() {
        if (this.mCallback != null) {
            this.mCallback.onProviderStateChanged(this);
        }
    }

    void setAndNotifyProviderState(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        setProviderState(mediaRoute2ProviderInfo);
        notifyProviderState();
    }

    public boolean hasComponentName(java.lang.String str, java.lang.String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + getDebugString());
        java.lang.String str2 = str + "  ";
        if (this.mProviderInfo == null) {
            printWriter.println(str2 + "<provider info not received, yet>");
        } else if (this.mProviderInfo.getRoutes().isEmpty()) {
            printWriter.println(str2 + "<provider info has no routes>");
        } else {
            for (android.media.MediaRoute2Info mediaRoute2Info : this.mProviderInfo.getRoutes()) {
                printWriter.printf("%s%s | %s\n", str2, mediaRoute2Info.getId(), mediaRoute2Info.getName());
            }
        }
        printWriter.println(str2 + "Active routing sessions:");
        synchronized (this.mLock) {
            try {
                if (this.mSessionInfos.isEmpty()) {
                    printWriter.println(str2 + "  <no active routing sessions>");
                } else {
                    java.util.Iterator<android.media.RoutingSessionInfo> it = this.mSessionInfos.iterator();
                    while (it.hasNext()) {
                        it.next().dump(printWriter, str2 + "  ");
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.lang.String toString() {
        return getDebugString();
    }
}
