package vn.javis.tourde.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.javis.tourde.R;

public class ProcessDialog {

    public Context context;
    ProgressDialog dialog1;
    private static ProgressBar spinner;
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
    public static void showDialogConfirm(final Context context,String title, String content, final  OnActionDialogClickOk action) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
       // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);
        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnCancel = (Button) dialog.findViewById(R.id.cancel_button);
        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              action.onOkClick();
                dialog.hide();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }
    public static void showDialogConfirm(final Context context,int viewLayout, String title, String content, final  OnActionDialogClickOk action) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(viewLayout);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);
        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnCancel = (Button) dialog.findViewById(R.id.cancel_button);
        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onOkClick();
                dialog.hide();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }
    public static void showDialogConfirm(final Context context,int viewLayout,int colorTitle,int colorMessage, String title, String content, final  OnActionDialogClickOk action) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(viewLayout);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        tvTitle.setTextColor(colorTitle);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setTextColor(colorMessage);
        tvMessage.setText(content);

        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnCancel = (Button) dialog.findViewById(R.id.cancel_button);
        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);
//asdgynsdghisdmi
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onOkClick();
                dialog.hide();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }
    public static void showDialogOk(final Context context,String title, String content) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_ok);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);
        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnCancel = (Button) dialog.findViewById(R.id.btn_ok);




        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();


            }
        });

        dialog.show();
    }
    public static void showDialogLogin(final Context context,String title, String content, final  OnActionDialogClickOk action) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_ok);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);

        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //  showloading( context );
                

                action.onOkClick();
                dialog.hide();
            }
        });

        dialog.show();
    }
    public static void showDialogcheckbox(final Context context, String title, String content, final OnActionDialogClickOk onActionDialogClickOk) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialogcheckbox);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title_text_view);
        // tvTitle.setVisibility(View.GONE);
        tvTitle.setText(title);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.message_text_view);
        tvMessage.setText(content);
        //tuanpd
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final Button btnCancel = (Button) dialog.findViewById(R.id.cancel_button);


        final Button btnOk = (Button) dialog.findViewById(R.id.ok_button);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionDialogClickOk.onOkClick();
                dialog.hide();
               // SharedPreferencesUtils.getInstance(context).setStringValue("Checkbox", "1");
            }
        });
        dialog.show();

        CheckBox checkBox;
        checkBox = dialog.findViewById( R.id.chkbox );
        checkBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox;
                checkBox = dialog.findViewById( R.id.chkbox );
                if(checkBox.isChecked()){
                    //xu ly click check box

                    Log.d("dm","checkbox");
                   // SharedPreferencesUtils.getInstance(context).setStringValue("Checkbox", "1");
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();

                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onActionDialogClickOk.onOkClick();
                            dialog.hide();
                            SharedPreferencesUtils.getInstance(context).setStringValue("Checkbox", "1");
                        }
                    });

                }
            }
        } );


    }
    public static void showloading(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.loading);
        spinner=dialog.findViewById( R.id.pBar );
        spinner.setVisibility( View.VISIBLE );
        dialog.show();
       // dialog.dismiss();
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }
    public interface OnActionDialogClickOk{
         void onOkClick();
    }

}
