package vn.javis.tourde.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;

import vn.javis.tourde.MainActivity;
import vn.javis.tourde.helpers.InputValidation;
import vn.javis.tourde.MainActivity;
import vn.javis.tourde.sql.DatabaseHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.Callable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView mnestedScrollView;
    private TextInputLayout mtextInputLayoutEmail;
    private TextInputLayout mtextInputLayoutPassword;
    private TextInputEditText mtextInputEditTextEmail;
    private TextInputEditText mtextInputEditTextPassword;
    private AppCompatButton mappCompatButtonLogin;
    private AppCompatTextView mtextViewLinkRegister;
    private InputValidation minputValidation;
    private DatabaseHelper mdatabaseHelper;
    //Facebook
    private LoginButton mFacebookSignInButton;
    private CallbackManager mFacebookCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //login face
        FacebookSdk.sdkInitialize( getApplicationContext() );
        setContentView( vn.javis.tourde.R.layout.activity_login );
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookSignInButton = (LoginButton) findViewById( vn.javis.tourde.R.id.login_button );
        mFacebookSignInButton.registerCallback( mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        //TODO: Use the Profile class to get information about the current user.
                        handleSignInResult( new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                LoginManager.getInstance().logOut();
                                return null;
                            }
                        } );
                    }
                    @Override
                    public void onCancel() {
                        handleSignInResult( null );
                    }

                    private void handleSignInResult(Object o) {
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Log.d( LoginActivity.class.getCanonicalName(), error.getMessage() );
                        handleSignInResult( null );
                    }
                }
        );
//end login face
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews() {
        mnestedScrollView = (NestedScrollView) findViewById( vn.javis.tourde.R.id.nestedScrollView );
        mtextInputLayoutEmail = (TextInputLayout) findViewById( vn.javis.tourde.R.id.textInputLayoutEmail );
        mtextInputLayoutPassword = (TextInputLayout) findViewById( vn.javis.tourde.R.id.textInputLayoutPassword );
        mtextInputEditTextEmail = (TextInputEditText) findViewById( vn.javis.tourde.R.id.textInputEditTextEmail );
        mtextInputEditTextPassword = (TextInputEditText) findViewById( vn.javis.tourde.R.id.textInputEditTextPassword );
        mappCompatButtonLogin = (AppCompatButton) findViewById( vn.javis.tourde.R.id.appCompatButtonLogin );
        mtextViewLinkRegister = (AppCompatTextView) findViewById( vn.javis.tourde.R.id.textViewLinkRegister );
    }
    private void initListeners() {
        mappCompatButtonLogin.setOnClickListener( this );
        mtextViewLinkRegister.setOnClickListener( this );
    }
    private void initObjects() {
        mdatabaseHelper = new DatabaseHelper( activity );
        minputValidation = new InputValidation( activity );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case vn.javis.tourde.R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case vn.javis.tourde.R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent( getApplicationContext(), RegisterActivity.class );
                startActivity( intentRegister );
                break;
        }
    }
    private void verifyFromSQLite() {
        if (!minputValidation.isInputEditTextFilled( mtextInputEditTextEmail, mtextInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!minputValidation.isInputEditTextEmail( mtextInputEditTextEmail, mtextInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!minputValidation.isInputEditTextFilled( mtextInputEditTextPassword, mtextInputLayoutPassword, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (mdatabaseHelper.checkUser( mtextInputEditTextEmail.getText().toString().trim()
                , mtextInputEditTextPassword.getText().toString().trim() )) {
            //  Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            Intent accountsIntent = new Intent( activity, MainActivity.class );
            //  accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            //   emptyInputEditText();
            startActivity( accountsIntent );

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make( mnestedScrollView, getString( vn.javis.tourde.R.string.error_valid_email_password ), Snackbar.LENGTH_LONG ).show();
        }
    }
    private void emptyInputEditText() {
        mtextInputEditTextEmail.setText( null );
        mtextInputEditTextPassword.setText( null );
    }

}


