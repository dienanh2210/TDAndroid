package vn.javis.tourde.ViewTutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.Callable;
import vn.javis.tourde.R;
import vn.javis.tourde.ScreenMain.ScreenMain;
import vn.javis.tourde.ViewTutorial.sql.DatabaseHelper;
import vn.javis.tourde.ViewTutorial.helpers.InputValidation;

public class LoginActivity extends Fragment implements View.OnClickListener {

    private NestedScrollView mNestedScrollView;
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditTextPassword;
    private AppCompatButton mAppCompatButtonLogin;
    private AppCompatTextView mTextViewLinkRegister;
    private InputValidation mInputValidation;
    private DatabaseHelper mDatabaseHelper;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_login, null );
        this.view = view;
        initViews();
        initListeners();
        initObjects();
        return view;
    }
    private void initViews() {
        mNestedScrollView = (NestedScrollView) view.findViewById( vn.javis.tourde.R.id.nestedScrollView );
        mTextInputLayoutEmail = (TextInputLayout) view. findViewById( vn.javis.tourde.R.id.textInputLayoutEmail );
        mTextInputLayoutPassword = (TextInputLayout)  view.findViewById( vn.javis.tourde.R.id.textInputLayoutPassword );
        mTextInputEditTextEmail = (TextInputEditText)  view.findViewById( vn.javis.tourde.R.id.textInputEditTextEmail );
        mTextInputEditTextPassword = (TextInputEditText)  view.findViewById( vn.javis.tourde.R.id.textInputEditTextPassword );
        mAppCompatButtonLogin = (AppCompatButton)  view.findViewById( vn.javis.tourde.R.id.appCompatButtonLogin );
        mTextViewLinkRegister = (AppCompatTextView)  view.findViewById( vn.javis.tourde.R.id.textViewLinkRegister );
    }
    private void initListeners() {
        mAppCompatButtonLogin.setOnClickListener( this );
        mTextViewLinkRegister.setOnClickListener( this );
    }
    private void initObjects() {
        mDatabaseHelper = new DatabaseHelper( getActivity() );
        mInputValidation = new InputValidation( getActivity() );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case vn.javis.tourde.R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case vn.javis.tourde.R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent( getContext(), RegisterActivity.class );
                startActivity( intentRegister );
                break;
        }
    }
    private void verifyFromSQLite() {
        if (!mInputValidation.isInputEditTextFilled( mTextInputEditTextEmail, mTextInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!mInputValidation.isInputEditTextEmail( mTextInputEditTextEmail, mTextInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!mInputValidation.isInputEditTextFilled( mTextInputEditTextPassword, mTextInputLayoutPassword, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (mDatabaseHelper.checkUser( mTextInputEditTextEmail.getText().toString().trim()
                , mTextInputEditTextPassword.getText().toString().trim() )) {
            Intent accountsIntent = new Intent( getActivity(),ScreenMain.class );
            startActivity( accountsIntent );

        } else {
            Snackbar.make( mNestedScrollView, getString( vn.javis.tourde.R.string.error_valid_email_password ), Snackbar.LENGTH_LONG ).show();
        }
    }
    private void emptyInputEditText() {
        mTextInputEditTextEmail.setText( null );
        mTextInputEditTextPassword.setText( null );
    }


}
