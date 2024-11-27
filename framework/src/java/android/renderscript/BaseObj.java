package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class BaseObj {
    final dalvik.system.CloseGuard guard = dalvik.system.CloseGuard.get();
    private boolean mDestroyed;
    private long mID;
    private java.lang.String mName;
    android.renderscript.RenderScript mRS;

    BaseObj(long j, android.renderscript.RenderScript renderScript) {
        renderScript.validate();
        this.mRS = renderScript;
        this.mID = j;
        this.mDestroyed = false;
    }

    void setID(long j) {
        if (this.mID != 0) {
            throw new android.renderscript.RSRuntimeException("Internal Error, reset of object ID.");
        }
        this.mID = j;
    }

    long getID(android.renderscript.RenderScript renderScript) {
        this.mRS.validate();
        if (this.mDestroyed) {
            throw new android.renderscript.RSInvalidStateException("using a destroyed object.");
        }
        if (this.mID == 0) {
            throw new android.renderscript.RSRuntimeException("Internal error: Object id 0.");
        }
        if (renderScript != null && renderScript != this.mRS) {
            throw new android.renderscript.RSInvalidStateException("using object with mismatched context.");
        }
        return this.mID;
    }

    void checkValid() {
        if (this.mID == 0) {
            throw new android.renderscript.RSIllegalArgumentException("Invalid object.");
        }
    }

    public void setName(java.lang.String str) {
        if (str == null) {
            throw new android.renderscript.RSIllegalArgumentException("setName requires a string of non-zero length.");
        }
        if (str.length() < 1) {
            throw new android.renderscript.RSIllegalArgumentException("setName does not accept a zero length string.");
        }
        if (this.mName != null) {
            throw new android.renderscript.RSIllegalArgumentException("setName object already has a name.");
        }
        try {
            this.mRS.nAssignName(this.mID, str.getBytes(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8));
            this.mName = str;
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public java.lang.String getName() {
        return this.mName;
    }

    private void helpDestroy() {
        boolean z;
        synchronized (this) {
            if (this.mDestroyed) {
                z = false;
            } else {
                z = true;
                this.mDestroyed = true;
            }
        }
        if (z) {
            this.guard.close();
            java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock readLock = this.mRS.mRWLock.readLock();
            readLock.lock();
            if (this.mRS.isAlive() && this.mID != 0) {
                this.mRS.nObjDestroy(this.mID);
            }
            readLock.unlock();
            this.mRS = null;
            this.mID = 0L;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            helpDestroy();
        } finally {
            super.finalize();
        }
    }

    public void destroy() {
        if (this.mDestroyed) {
            throw new android.renderscript.RSInvalidStateException("Object already destroyed.");
        }
        helpDestroy();
    }

    void updateFromNative() {
        this.mRS.validate();
        this.mName = this.mRS.nGetName(getID(this.mRS));
    }

    public int hashCode() {
        return (int) ((this.mID & 268435455) ^ (this.mID >> 32));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mID == ((android.renderscript.BaseObj) obj).mID) {
            return true;
        }
        return false;
    }
}
