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

import vn.javis.tourde.helpers.InputValidation;
import vn.javis.tourde.Activity_main2;
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

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    //Face
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

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById( vn.javis.tourde.R.id.nestedScrollView );

        textInputLayoutEmail = (TextInputLayout) findViewById( vn.javis.tourde.R.id.textInputLayoutEmail );
        textInputLayoutPassword = (TextInputLayout) findViewById( vn.javis.tourde.R.id.textInputLayoutPassword );

        textInputEditTextEmail = (TextInputEditText) findViewById( vn.javis.tourde.R.id.textInputEditTextEmail );
        textInputEditTextPassword = (TextInputEditText) findViewById( vn.javis.tourde.R.id.textInputEditTextPassword );

        appCompatButtonLogin = (AppCompatButton) findViewById( vn.javis.tourde.R.id.appCompatButtonLogin );

        textViewLinkRegister = (AppCompatTextView) findViewById( vn.javis.tourde.R.id.textViewLinkRegister );

    }


    private void initListeners() {
        appCompatButtonLogin.setOnClickListener( this );
        textViewLinkRegister.setOnClickListener( this );
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper( activity );
        inputValidation = new InputValidation( activity );

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
        if (!inputValidation.isInputEditTextFilled( textInputEditTextEmail, textInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail( textInputEditTextEmail, textInputLayoutEmail, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled( textInputEditTextPassword, textInputLayoutPassword, getString( vn.javis.tourde.R.string.error_message_email ) )) {
            return;
        }

        if (databaseHelper.checkUser( textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim() )) {


            //  Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            Intent accountsIntent = new Intent( activity, Activity_main2.class );
            //  accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            //   emptyInputEditText();
            startActivity( accountsIntent );


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make( nestedScrollView, getString( vn.javis.tourde.R.string.error_valid_email_password ), Snackbar.LENGTH_LONG ).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText( null );
        textInputEditTextPassword.setText( null );
    }

}


