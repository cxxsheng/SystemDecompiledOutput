package android.os;

/* loaded from: classes3.dex */
public final class Message implements android.os.Parcelable {
    static final int FLAGS_TO_CLEAR_ON_COPY_FROM = 1;
    static final int FLAG_ASYNCHRONOUS = 2;
    static final int FLAG_IN_USE = 1;
    private static final int MAX_POOL_SIZE = 50;
    public static final int UID_NONE = -1;
    private static android.os.Message sPool;
    public int arg1;
    public int arg2;
    java.lang.Runnable callback;
    android.os.Bundle data;
    int flags;
    android.os.Message next;
    public java.lang.Object obj;
    public android.os.Messenger replyTo;
    android.os.Handler target;
    public int what;
    public long when;
    public static final java.lang.Object sPoolSync = new java.lang.Object();
    private static int sPoolSize = 0;
    private static boolean gCheckRecycle = true;
    public static final android.os.Parcelable.Creator<android.os.Message> CREATOR = new android.os.Parcelable.Creator<android.os.Message>() { // from class: android.os.Message.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Message createFromParcel(android.os.Parcel parcel) {
            android.os.Message obtain = android.os.Message.obtain();
            obtain.readFromParcel(parcel);
            return obtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Message[] newArray(int i) {
            return new android.os.Message[i];
        }
    };
    public int sendingUid = -1;
    public int workSourceUid = -1;

    public static android.os.Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                android.os.Message message = sPool;
                sPool = message.next;
                message.next = null;
                message.flags = 0;
                sPoolSize--;
                return message;
            }
            return new android.os.Message();
        }
    }

    public static android.os.Message obtain(android.os.Message message) {
        android.os.Message obtain = obtain();
        obtain.what = message.what;
        obtain.arg1 = message.arg1;
        obtain.arg2 = message.arg2;
        obtain.obj = message.obj;
        obtain.replyTo = message.replyTo;
        obtain.sendingUid = message.sendingUid;
        obtain.workSourceUid = message.workSourceUid;
        if (message.data != null) {
            obtain.data = new android.os.Bundle(message.data);
        }
        obtain.target = message.target;
        obtain.callback = message.callback;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler, java.lang.Runnable runnable) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        obtain.callback = runnable;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler, int i) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        obtain.what = i;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler, int i, java.lang.Object obj) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        obtain.what = i;
        obtain.obj = obj;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler, int i, int i2, int i3) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        return obtain;
    }

    public static android.os.Message obtain(android.os.Handler handler, int i, int i2, int i3, java.lang.Object obj) {
        android.os.Message obtain = obtain();
        obtain.target = handler;
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        return obtain;
    }

    public static void updateCheckRecycle(int i) {
        if (i < 21) {
            gCheckRecycle = false;
        }
    }

    public void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new java.lang.IllegalStateException("This message cannot be recycled because it is still in use.");
            }
        } else {
            recycleUnchecked();
        }
    }

    void recycleUnchecked() {
        this.flags = 1;
        this.what = 0;
        this.arg1 = 0;
        this.arg2 = 0;
        this.obj = null;
        this.replyTo = null;
        this.sendingUid = -1;
        this.workSourceUid = -1;
        this.when = 0L;
        this.target = null;
        this.callback = null;
        this.data = null;
        synchronized (sPoolSync) {
            if (sPoolSize < 50) {
                this.next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    public void copyFrom(android.os.Message message) {
        this.flags = message.flags & (-2);
        this.what = message.what;
        this.arg1 = message.arg1;
        this.arg2 = message.arg2;
        this.obj = message.obj;
        this.replyTo = message.replyTo;
        this.sendingUid = message.sendingUid;
        this.workSourceUid = message.workSourceUid;
        if (message.data != null) {
            this.data = (android.os.Bundle) message.data.clone();
        } else {
            this.data = null;
        }
    }

    public long getWhen() {
        return this.when;
    }

    public void setTarget(android.os.Handler handler) {
        this.target = handler;
    }

    public android.os.Handler getTarget() {
        return this.target;
    }

    public java.lang.Runnable getCallback() {
        return this.callback;
    }

    public android.os.Message setCallback(java.lang.Runnable runnable) {
        this.callback = runnable;
        return this;
    }

    public android.os.Bundle getData() {
        if (this.data == null) {
            this.data = new android.os.Bundle();
        }
        return this.data;
    }

    public android.os.Bundle peekData() {
        return this.data;
    }

    public void setData(android.os.Bundle bundle) {
        this.data = bundle;
    }

    public android.os.Message setWhat(int i) {
        this.what = i;
        return this;
    }

    public void sendToTarget() {
        this.target.sendMessage(this);
    }

    public boolean isAsynchronous() {
        return (this.flags & 2) != 0;
    }

    public void setAsynchronous(boolean z) {
        if (z) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    boolean isInUse() {
        return (this.flags & 1) == 1;
    }

    void markInUse() {
        this.flags |= 1;
    }

    public java.lang.String toString() {
        return toString(android.os.SystemClock.uptimeMillis());
    }

    java.lang.String toString(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{ when=");
        android.util.TimeUtils.formatDuration(this.when - j, sb);
        if (this.target != null) {
            if (this.callback != null) {
                sb.append(" callback=");
                sb.append(this.callback.getClass().getName());
            } else {
                sb.append(" what=");
                sb.append(this.what);
            }
            if (this.arg1 != 0) {
                sb.append(" arg1=");
                sb.append(this.arg1);
            }
            if (this.arg2 != 0) {
                sb.append(" arg2=");
                sb.append(this.arg2);
            }
            if (this.obj != null) {
                sb.append(" obj=");
                sb.append(this.obj);
            }
            sb.append(" target=");
            sb.append(this.target.getClass().getName());
        } else {
            sb.append(" barrier=");
            sb.append(this.arg1);
        }
        sb.append(" }");
        return sb.toString();
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, this.when);
        if (this.target != null) {
            if (this.callback != null) {
                protoOutputStream.write(1138166333442L, this.callback.getClass().getName());
            } else {
                protoOutputStream.write(1120986464259L, this.what);
            }
            if (this.arg1 != 0) {
                protoOutputStream.write(1120986464260L, this.arg1);
            }
            if (this.arg2 != 0) {
                protoOutputStream.write(1120986464261L, this.arg2);
            }
            if (this.obj != null) {
                protoOutputStream.write(1138166333446L, this.obj.toString());
            }
            protoOutputStream.write(1138166333447L, this.target.getClass().getName());
        } else {
            protoOutputStream.write(1120986464264L, this.arg1);
        }
        protoOutputStream.end(start);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.callback != null) {
            throw new java.lang.RuntimeException("Can't marshal callbacks across processes.");
        }
        parcel.writeInt(this.what);
        parcel.writeInt(this.arg1);
        parcel.writeInt(this.arg2);
        if (this.obj != null) {
            try {
                android.os.Parcelable parcelable = (android.os.Parcelable) this.obj;
                parcel.writeInt(1);
                parcel.writeParcelable(parcelable, i);
            } catch (java.lang.ClassCastException e) {
                throw new java.lang.RuntimeException("Can't marshal non-Parcelable objects across processes.");
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.when);
        parcel.writeBundle(this.data);
        android.os.Messenger.writeMessengerOrNullToParcel(this.replyTo, parcel);
        parcel.writeInt(this.sendingUid);
        parcel.writeInt(this.workSourceUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readFromParcel(android.os.Parcel parcel) {
        this.what = parcel.readInt();
        this.arg1 = parcel.readInt();
        this.arg2 = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.obj = parcel.readParcelable(getClass().getClassLoader(), java.lang.Object.class);
        }
        this.when = parcel.readLong();
        this.data = parcel.readBundle();
        this.replyTo = android.os.Messenger.readMessengerOrNullFromParcel(parcel);
        this.sendingUid = parcel.readInt();
        this.workSourceUid = parcel.readInt();
    }
}
