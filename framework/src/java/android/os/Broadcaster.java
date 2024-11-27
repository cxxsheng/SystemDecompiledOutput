package android.os;

/* loaded from: classes3.dex */
public class Broadcaster {
    private android.os.Broadcaster.Registration mReg;

    public void request(int i, android.os.Handler handler, int i2) {
        synchronized (this) {
            int i3 = 0;
            if (this.mReg == null) {
                android.os.Broadcaster.Registration registration = new android.os.Broadcaster.Registration();
                registration.senderWhat = i;
                registration.targets = new android.os.Handler[1];
                registration.targetWhats = new int[1];
                registration.targets[0] = handler;
                registration.targetWhats[0] = i2;
                this.mReg = registration;
                registration.next = registration;
                registration.prev = registration;
            } else {
                android.os.Broadcaster.Registration registration2 = this.mReg;
                android.os.Broadcaster.Registration registration3 = registration2;
                while (registration3.senderWhat < i && (registration3 = registration3.next) != registration2) {
                }
                if (registration3.senderWhat != i) {
                    android.os.Broadcaster.Registration registration4 = new android.os.Broadcaster.Registration();
                    registration4.senderWhat = i;
                    registration4.targets = new android.os.Handler[1];
                    registration4.targetWhats = new int[1];
                    registration4.next = registration3;
                    registration4.prev = registration3.prev;
                    registration3.prev.next = registration4;
                    registration3.prev = registration4;
                    if (registration3 == this.mReg && registration3.senderWhat > registration4.senderWhat) {
                        this.mReg = registration4;
                    }
                    registration3 = registration4;
                } else {
                    int length = registration3.targets.length;
                    android.os.Handler[] handlerArr = registration3.targets;
                    int[] iArr = registration3.targetWhats;
                    for (int i4 = 0; i4 < length; i4++) {
                        if (handlerArr[i4] == handler && iArr[i4] == i2) {
                            return;
                        }
                    }
                    int i5 = length + 1;
                    registration3.targets = new android.os.Handler[i5];
                    java.lang.System.arraycopy(handlerArr, 0, registration3.targets, 0, length);
                    registration3.targetWhats = new int[i5];
                    java.lang.System.arraycopy(iArr, 0, registration3.targetWhats, 0, length);
                    i3 = length;
                }
                registration3.targets[i3] = handler;
                registration3.targetWhats[i3] = i2;
            }
        }
    }

    public void cancelRequest(int i, android.os.Handler handler, int i2) {
        synchronized (this) {
            android.os.Broadcaster.Registration registration = this.mReg;
            if (registration == null) {
                return;
            }
            android.os.Broadcaster.Registration registration2 = registration;
            while (registration2.senderWhat < i && (registration2 = registration2.next) != registration) {
            }
            if (registration2.senderWhat == i) {
                android.os.Handler[] handlerArr = registration2.targets;
                int[] iArr = registration2.targetWhats;
                int length = handlerArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    if (handlerArr[i3] != handler || iArr[i3] != i2) {
                        i3++;
                    } else {
                        int i4 = length - 1;
                        registration2.targets = new android.os.Handler[i4];
                        registration2.targetWhats = new int[i4];
                        if (i3 > 0) {
                            java.lang.System.arraycopy(handlerArr, 0, registration2.targets, 0, i3);
                            java.lang.System.arraycopy(iArr, 0, registration2.targetWhats, 0, i3);
                        }
                        int i5 = (length - i3) - 1;
                        if (i5 != 0) {
                            int i6 = i3 + 1;
                            java.lang.System.arraycopy(handlerArr, i6, registration2.targets, i3, i5);
                            java.lang.System.arraycopy(iArr, i6, registration2.targetWhats, i3, i5);
                        }
                    }
                }
            }
        }
    }

    public void dumpRegistrations() {
        synchronized (this) {
            android.os.Broadcaster.Registration registration = this.mReg;
            java.lang.System.out.println("Broadcaster " + this + " {");
            if (registration != null) {
                android.os.Broadcaster.Registration registration2 = registration;
                do {
                    java.lang.System.out.println("    senderWhat=" + registration2.senderWhat);
                    int length = registration2.targets.length;
                    for (int i = 0; i < length; i++) {
                        java.lang.System.out.println("        [" + registration2.targetWhats[i] + "] " + registration2.targets[i]);
                    }
                    registration2 = registration2.next;
                } while (registration2 != registration);
            }
            java.lang.System.out.println("}");
        }
    }

    public void broadcast(android.os.Message message) {
        synchronized (this) {
            if (this.mReg == null) {
                return;
            }
            int i = message.what;
            android.os.Broadcaster.Registration registration = this.mReg;
            android.os.Broadcaster.Registration registration2 = registration;
            while (registration2.senderWhat < i && (registration2 = registration2.next) != registration) {
            }
            if (registration2.senderWhat == i) {
                android.os.Handler[] handlerArr = registration2.targets;
                int[] iArr = registration2.targetWhats;
                int length = handlerArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    android.os.Handler handler = handlerArr[i2];
                    android.os.Message obtain = android.os.Message.obtain();
                    obtain.copyFrom(message);
                    obtain.what = iArr[i2];
                    handler.sendMessage(obtain);
                }
            }
        }
    }

    private class Registration {
        android.os.Broadcaster.Registration next;
        android.os.Broadcaster.Registration prev;
        int senderWhat;
        int[] targetWhats;
        android.os.Handler[] targets;

        private Registration() {
        }
    }
}
