package com.android.server.wm;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ActivitySnapshotController$$ExternalSyntheticLambda1 implements com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver {
    @Override // com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver
    public final java.io.File getSystemDirectoryForUser(int i) {
        return android.os.Environment.getDataSystemCeDirectory(i);
    }
}
