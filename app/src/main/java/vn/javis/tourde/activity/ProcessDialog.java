package vn.javis.tourde.activity;

import android.app.ProgressDialog;
import android.content.Context;

public class ProcessDialog {
    public Context context;

    ProgressDialog dialog1;

    public ProcessDialog(final Context context) {
        this.context = context;
        dialog1 = new ProgressDialog(context);
    }

    public void showDialog() {
        showDialog("Loading...", false);
    }

    public void showDialog(String message, boolean cancelable) {
        if (dialog1 != null) {
            dialog1.setMessage(message);
            dialog1.setIndeterminate(false);
            dialog1.setCancelable(cancelable);
            dialog1.show();
        }
    }

    public void close() {
        if (dialog1 != null) {
            dialog1.dismiss();
        }
    }
}
