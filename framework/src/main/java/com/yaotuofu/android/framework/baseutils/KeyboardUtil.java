package com.yaotuofu.android.framework.baseutils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class KeyboardUtil {

    private KeyboardUtil() {
    }

    public static void openKeyboard(final Context context, final EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        //        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        //imm.showSoftInput(editText, 0);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

    }

    public static void openInputMethod(final Context context, final EditText editText) {
        editText.requestFocus();
        final InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        new Timer().schedule(new TimerTask() {
            public void run() {
                im.showSoftInput(editText, 0);
            }
        }, 100);
    }

    public static void focusAndOpenKeyboard(final Context context, final EditText editText) {
        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openKeyboard(context, editText);
            }
        }, 50);
    }

    public static void closeKeyboard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isKeyboardshow(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive(editText);
    }
}
