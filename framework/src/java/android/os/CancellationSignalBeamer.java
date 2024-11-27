package android.os;

/* loaded from: classes3.dex */
public class CancellationSignalBeamer {
    static final java.lang.ref.Cleaner sCleaner = android.system.SystemCleaner.cleaner();

    public static abstract class Sender {
        private static final java.lang.ThreadLocal<android.util.Pair<android.os.CancellationSignalBeamer.Sender, java.util.ArrayList<android.os.CancellationSignalBeamer.Sender.CloseableToken>>> sScope = new java.lang.ThreadLocal<>();

        public interface CloseableToken extends android.os.IBinder, android.os.CancellationSignalBeamer.Sender.MustClose {
            @Override // android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
            void close();
        }

        public interface MustClose extends java.lang.AutoCloseable {
            @Override // java.lang.AutoCloseable
            void close();
        }

        public abstract void onCancel(android.os.IBinder iBinder);

        public abstract void onForget(android.os.IBinder iBinder);

        public android.os.CancellationSignalBeamer.Sender.CloseableToken beam(android.os.CancellationSignal cancellationSignal) {
            if (cancellationSignal == null) {
                return null;
            }
            return new android.os.CancellationSignalBeamer.Sender.Token(cancellationSignal);
        }

        public android.os.CancellationSignalBeamer.Sender.MustClose beamScopeIfNeeded(android.view.inputmethod.HandwritingGesture handwritingGesture) {
            if (!(handwritingGesture instanceof android.view.inputmethod.CancellableHandwritingGesture)) {
                return null;
            }
            sScope.set(android.util.Pair.create(this, new java.util.ArrayList()));
            return new android.os.CancellationSignalBeamer.Sender.MustClose() { // from class: android.os.CancellationSignalBeamer$Sender$$ExternalSyntheticLambda0
                @Override // android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
                public final void close() {
                    android.os.CancellationSignalBeamer.Sender.lambda$beamScopeIfNeeded$0();
                }
            };
        }

        static /* synthetic */ void lambda$beamScopeIfNeeded$0() {
            java.util.ArrayList<android.os.CancellationSignalBeamer.Sender.CloseableToken> arrayList = sScope.get().second;
            sScope.remove();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) != null) {
                    arrayList.get(size).close();
                }
            }
        }

        public static android.os.IBinder beamFromScope(android.os.CancellationSignal cancellationSignal) {
            android.util.Pair<android.os.CancellationSignalBeamer.Sender, java.util.ArrayList<android.os.CancellationSignalBeamer.Sender.CloseableToken>> pair = sScope.get();
            if (pair != null) {
                android.os.CancellationSignalBeamer.Sender.CloseableToken beam = pair.first.beam(cancellationSignal);
                pair.second.add(beam);
                return beam;
            }
            return null;
        }

        private static class Token extends android.os.Binder implements android.os.CancellationSignalBeamer.Sender.CloseableToken, java.lang.Runnable {
            private android.os.CancellationSignalBeamer.Sender.Token.Preparer mPreparer;
            private final android.os.CancellationSignalBeamer.Sender mSender;

            private Token(android.os.CancellationSignalBeamer.Sender sender, android.os.CancellationSignal cancellationSignal) {
                this.mSender = sender;
                this.mPreparer = new android.os.CancellationSignalBeamer.Sender.Token.Preparer(sender, cancellationSignal, this);
            }

            @Override // android.os.CancellationSignalBeamer.Sender.CloseableToken, android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
            public void close() {
                android.os.CancellationSignalBeamer.Sender.Token.Preparer preparer = this.mPreparer;
                this.mPreparer = null;
                if (preparer != null) {
                    preparer.setup();
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mSender.onForget(this);
            }

            private static class Preparer implements android.os.CancellationSignal.OnCancelListener {
                private final android.os.CancellationSignalBeamer.Sender mSender;
                private final android.os.CancellationSignal mSignal;
                private final android.os.CancellationSignalBeamer.Sender.Token mToken;

                private Preparer(android.os.CancellationSignalBeamer.Sender sender, android.os.CancellationSignal cancellationSignal, android.os.CancellationSignalBeamer.Sender.Token token) {
                    this.mSender = sender;
                    this.mSignal = cancellationSignal;
                    this.mToken = token;
                }

                void setup() {
                    android.os.CancellationSignalBeamer.sCleaner.register(this, this.mToken);
                    this.mSignal.setOnCancelListener(this);
                }

                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    try {
                        this.mSender.onCancel(this.mToken);
                    } finally {
                        java.lang.ref.Reference.reachabilityFence(this);
                    }
                }
            }
        }
    }

    public static class Receiver implements android.os.IBinder.DeathRecipient {
        private final boolean mCancelOnSenderDeath;
        private final java.util.HashMap<android.os.IBinder, android.os.CancellationSignal> mTokenMap = new java.util.HashMap<>();

        public Receiver(boolean z) {
            this.mCancelOnSenderDeath = z;
        }

        public android.os.CancellationSignal unbeam(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            synchronized (this) {
                android.os.CancellationSignal cancellationSignal = this.mTokenMap.get(iBinder);
                if (cancellationSignal != null) {
                    return cancellationSignal;
                }
                android.os.CancellationSignal cancellationSignal2 = new android.os.CancellationSignal();
                this.mTokenMap.put(iBinder, cancellationSignal2);
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    dead(iBinder);
                }
                return cancellationSignal2;
            }
        }

        public void forget(android.os.IBinder iBinder) {
            synchronized (this) {
                if (this.mTokenMap.remove(iBinder) != null) {
                    iBinder.unlinkToDeath(this, 0);
                }
            }
        }

        public void cancel(android.os.IBinder iBinder) {
            synchronized (this) {
                android.os.CancellationSignal cancellationSignal = this.mTokenMap.get(iBinder);
                if (cancellationSignal != null) {
                    forget(iBinder);
                    cancellationSignal.cancel();
                }
            }
        }

        private void dead(android.os.IBinder iBinder) {
            if (this.mCancelOnSenderDeath) {
                cancel(iBinder);
            } else {
                forget(iBinder);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(android.os.IBinder iBinder) {
            dead(iBinder);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            throw new java.lang.RuntimeException("unreachable");
        }
    }
}
