package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface IHwBinder {

    public interface DeathRecipient {
        void serviceDied(long j);
    }

    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j);

    android.os.IHwInterface queryLocalInterface(java.lang.String str);

    void transact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException;

    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient);
}
