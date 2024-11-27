package android.os;

/* loaded from: classes3.dex */
public interface IBinder {
    public static final int DUMP_TRANSACTION = 1598311760;
    public static final int FIRST_CALL_TRANSACTION = 1;
    public static final int FLAG_CLEAR_BUF = 32;
    public static final int FLAG_COLLECT_NOTED_APP_OPS = 2;
    public static final int FLAG_ONEWAY = 1;
    public static final int INTERFACE_TRANSACTION = 1598968902;
    public static final int LAST_CALL_TRANSACTION = 16777215;
    public static final int LIKE_TRANSACTION = 1598835019;
    public static final int MAX_IPC_SIZE = 65536;
    public static final int PING_TRANSACTION = 1599098439;
    public static final int SHELL_COMMAND_TRANSACTION = 1598246212;
    public static final int SYSPROPS_TRANSACTION = 1599295570;
    public static final int TWEET_TRANSACTION = 1599362900;

    void dump(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException;

    void dumpAsync(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException;

    java.lang.String getInterfaceDescriptor() throws android.os.RemoteException;

    boolean isBinderAlive();

    void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) throws android.os.RemoteException;

    boolean pingBinder();

    android.os.IInterface queryLocalInterface(java.lang.String str);

    void shellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    boolean transact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException;

    boolean unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i);

    static int getSuggestedMaxIpcSizeBytes() {
        return 65536;
    }

    default android.os.IBinder getExtension() throws android.os.RemoteException {
        throw new java.lang.IllegalStateException("Method is not implemented");
    }

    public interface DeathRecipient {
        void binderDied();

        default void binderDied(android.os.IBinder iBinder) {
            binderDied();
        }
    }
}
