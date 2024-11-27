package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
public abstract class GrammaticalInflectionManagerInternal {
    public abstract boolean canGetSystemGrammaticalGender(int i, @android.annotation.Nullable java.lang.String str);

    @android.annotation.Nullable
    public abstract byte[] getBackupPayload(int i);

    public abstract int getSystemGrammaticalGender(int i);

    public abstract int retrieveSystemGrammaticalGender(@android.annotation.NonNull android.content.res.Configuration configuration);

    public abstract void stageAndApplyRestoredPayload(byte[] bArr, int i);
}
