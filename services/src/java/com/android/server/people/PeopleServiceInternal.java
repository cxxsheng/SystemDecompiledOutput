package com.android.server.people;

/* loaded from: classes2.dex */
public abstract class PeopleServiceInternal extends android.service.appprediction.IPredictionService.Stub {
    @android.annotation.Nullable
    public abstract byte[] getBackupPayload(int i);

    public abstract void pruneDataForUser(int i, @android.annotation.NonNull android.os.CancellationSignal cancellationSignal);

    public abstract void restore(int i, @android.annotation.NonNull byte[] bArr);
}
