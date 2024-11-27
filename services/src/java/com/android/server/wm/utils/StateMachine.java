package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class StateMachine {
    private static final java.lang.String TAG = "StateMachine";
    private final java.util.Queue<com.android.server.wm.utils.StateMachine.Command> mCommands;
    private int mLastRequestedState;
    private int mState;
    private final android.util.SparseArray<com.android.server.wm.utils.StateMachine.Handler> mStateHandlers;
    private final android.util.IntArray mTmp;

    public interface Handler {
        default void enter() {
        }

        default void exit() {
        }

        default boolean handle(int i, @android.annotation.Nullable java.lang.Object obj) {
            return false;
        }
    }

    protected static class Command {
        static final int COMMIT = 1;
        static final int ENTER = 2;
        static final int EXIT = 3;
        final int mState;
        final int mType;

        private Command(int i, int i2) {
            this.mType = i;
            com.android.internal.util.AnnotationValidations.validate(android.annotation.IntRange.class, (android.annotation.IntRange) null, i2, "from", 0L);
            this.mState = i2;
        }

        static com.android.server.wm.utils.StateMachine.Command newCommit(int i) {
            return new com.android.server.wm.utils.StateMachine.Command(1, i);
        }

        static com.android.server.wm.utils.StateMachine.Command newEnter(int i) {
            return new com.android.server.wm.utils.StateMachine.Command(2, i);
        }

        static com.android.server.wm.utils.StateMachine.Command newExit(int i) {
            return new com.android.server.wm.utils.StateMachine.Command(3, i);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Command{ type: ");
            switch (this.mType) {
                case 1:
                    sb.append("commit");
                    break;
                case 2:
                    sb.append("enter");
                    break;
                case 3:
                    sb.append("exit");
                    break;
                default:
                    sb.append("UNKNOWN(");
                    sb.append(this.mType);
                    sb.append(")");
                    break;
            }
            sb.append(" state: ");
            sb.append(java.lang.Integer.toHexString(this.mState));
            sb.append(" }");
            return sb.toString();
        }
    }

    public StateMachine() {
        this(0);
    }

    public StateMachine(int i) {
        this.mTmp = new android.util.IntArray();
        this.mStateHandlers = new android.util.SparseArray<>();
        this.mCommands = new java.util.ArrayDeque();
        this.mState = i;
        com.android.internal.util.AnnotationValidations.validate(android.annotation.IntRange.class, (android.annotation.IntRange) null, i, "from", 0L);
        this.mLastRequestedState = i;
    }

    public int getState() {
        return this.mLastRequestedState;
    }

    protected int getCurrentState() {
        return this.mState;
    }

    protected com.android.server.wm.utils.StateMachine.Command[] getCommands() {
        com.android.server.wm.utils.StateMachine.Command[] commandArr = new com.android.server.wm.utils.StateMachine.Command[this.mCommands.size()];
        this.mCommands.toArray(commandArr);
        return commandArr;
    }

    @android.annotation.Nullable
    public com.android.server.wm.utils.StateMachine.Handler addStateHandler(int i, @android.annotation.Nullable com.android.server.wm.utils.StateMachine.Handler handler) {
        com.android.server.wm.utils.StateMachine.Handler handler2 = this.mStateHandlers.get(i);
        this.mStateHandlers.put(i, handler);
        return handler2;
    }

    public void handle(int i, @android.annotation.Nullable java.lang.Object obj) {
        int i2 = this.mState;
        while (true) {
            com.android.server.wm.utils.StateMachine.Handler handler = this.mStateHandlers.get(i2);
            if ((handler == null || !handler.handle(i, obj)) && i2 != 0) {
                i2 >>= 4;
            } else {
                return;
            }
        }
    }

    protected void enter(int i) {
        com.android.internal.util.AnnotationValidations.validate(android.annotation.IntRange.class, (android.annotation.IntRange) null, i, "from", 0L);
        com.android.server.wm.utils.StateMachine.Handler handler = this.mStateHandlers.get(i);
        if (handler != null) {
            handler.enter();
        }
    }

    protected void exit(int i) {
        com.android.internal.util.AnnotationValidations.validate(android.annotation.IntRange.class, (android.annotation.IntRange) null, i, "from", 0L);
        com.android.server.wm.utils.StateMachine.Handler handler = this.mStateHandlers.get(i);
        if (handler != null) {
            handler.exit();
        }
    }

    public static boolean isIn(int i, int i2) {
        while (i > i2) {
            i >>= 4;
        }
        return i == i2;
    }

    public boolean isIn(int i) {
        return isIn(this.mLastRequestedState, i);
    }

    public void transit(int i) {
        com.android.internal.util.AnnotationValidations.validate(android.annotation.IntRange.class, (android.annotation.IntRange) null, i, "from", 0L);
        this.mCommands.add(com.android.server.wm.utils.StateMachine.Command.newCommit(i));
        if (this.mLastRequestedState == i) {
            this.mCommands.add(com.android.server.wm.utils.StateMachine.Command.newExit(i));
            this.mCommands.add(com.android.server.wm.utils.StateMachine.Command.newEnter(i));
        } else {
            for (int i2 = this.mLastRequestedState; !isIn(i, i2); i2 >>= 4) {
                this.mCommands.add(com.android.server.wm.utils.StateMachine.Command.newExit(i2));
            }
            this.mTmp.clear();
            for (int i3 = i; !isIn(this.mLastRequestedState, i3); i3 >>= 4) {
                this.mTmp.add(i3);
            }
            for (int size = this.mTmp.size() - 1; size >= 0; size--) {
                this.mCommands.add(com.android.server.wm.utils.StateMachine.Command.newEnter(this.mTmp.get(size)));
            }
        }
        this.mLastRequestedState = i;
        while (!this.mCommands.isEmpty()) {
            com.android.server.wm.utils.StateMachine.Command remove = this.mCommands.remove();
            switch (remove.mType) {
                case 1:
                    this.mState = remove.mState;
                    break;
                case 2:
                    enter(remove.mState);
                    break;
                case 3:
                    exit(remove.mState);
                    break;
                default:
                    android.util.Slog.e(TAG, "Unknown command type: " + remove.mType);
                    break;
            }
        }
    }
}
