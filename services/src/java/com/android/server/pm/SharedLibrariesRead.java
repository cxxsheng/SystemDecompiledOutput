package com.android.server.pm;

/* loaded from: classes2.dex */
public interface SharedLibrariesRead {
    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState);

    void dumpProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream);

    @android.annotation.NonNull
    com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> getAll();

    @android.annotation.Nullable
    android.content.pm.SharedLibraryInfo getSharedLibraryInfo(@android.annotation.NonNull java.lang.String str, long j);

    @android.annotation.Nullable
    com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> getSharedLibraryInfos(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> getStaticLibraryInfos(@android.annotation.NonNull java.lang.String str);
}
