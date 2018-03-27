package vn.javis.tourde.ViewTutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import vn.javis.tourde.R;
import vn.javis.tourde.ViewTutorial.helpers.InputValidation;
import vn.javis.tourde.ViewTutorial.model.User;
import vn.javis.tourde.ViewTutorial.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity Activity = RegisterActivity.this;
    private NestedScrollView mNestedScrollView;
    private TextInputLayout mTextInputLayoutName;
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputLayout mTextInputLayoutConfirmPassword;
    private TextInputEditText mTextInputEditTextName;
    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditTextPassword;
    private TextInputEditText mTextInputEditTextConfirmPassword;
    private AppCompatButton mAppCompatButtonRegister;
    private AppCompatTextView mAppCompatTextViewLoginLink;
    private InputValidation mInputValidation;
    private DatabaseHelper mDatabaseHelper;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_register);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews() {
        mNestedScrollView = (NestedScrollView) findViewById( R.id.nestedScrollView);
        mTextInputLayoutName = (TextInputLayout) findViewById( R.id.textInputLayoutName);
        mTextInputLayoutEmail = (TextInputLayout) findViewById( R.id.textInputLayoutEmail);
        mTextInputLayoutPassword = (TextInputLayout) findViewById( R.id.textInputLayoutPassword);
        mTextInputLayoutConfirmPassword = (TextInputLayout) findViewById( R.id.textInputLayoutConfirmPassword);
        mTextInputEditTextName = (TextInputEditText) findViewById( R.id.textInputEditTextName);
        mTextInputEditTextEmail = (TextInputEditText) findViewById( R.id.textInputEditTextEmail);
        mTextInputEditTextPassword = (TextInputEditText) findViewById( R.id.textInputEditTextPassword);
        mTextInputEditTextConfirmPassword = (TextInputEditText) findViewById( R.id.textInputEditTextConfirmPassword);
        mAppCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        mAppCompatTextViewLoginLink = (AppCompatTextView) findViewById( R.id.appCompatTextViewLoginLink);
    }
    private void initListeners() {
        mAppCompatButtonRegister.setOnClickListener(this);
        mAppCompatTextViewLoginLink.setOnClickListener(this);
    }
    private void initObjects() {
        mInputValidation = new InputValidation(Activity);
        mDatabaseHelper = new DatabaseHelper(Activity);
        user = new User();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }
    private void postDataToSQLite() {
        if (!mInputValidation.isInputEditTextFilled(mTextInputEditTextName, mTextInputLayoutName, getString( R.string.error_message_name))) {
            return;
        }
        if (!mInputValidation.isInputEditTextFilled(mTextInputEditTextEmail, mTextInputLayoutEmail, getString( R.string.error_message_email))) {
            return;
        }
        if (!mInputValidation.isInputEditTextEmail(mTextInputEditTextEmail, mTextInputLayoutEmail, getString( R.string.error_message_email))) {
            return;
        }
        if (!mInputValidation.isInputEditTextFilled(mTextInputEditTextPassword, mTextInputLayoutPassword, getString( R.string.error_message_password))) {
            return;
        }
        if (!mInputValidation.isInputEditTextMatches(mTextInputEditTextPassword, mTextInputEditTextConfirmPassword,
                mTextInputLayoutConfirmPassword, getString( R.string.error_password_match))) {
            return;
        }
        if (!mDatabaseHelper.checkUser(mTextInputEditTextEmail.getText().toString().trim())) {
            user.setName(mTextInputEditTextName.getText().toString().trim());
            user.setEmail(mTextInputEditTextEmail.getText().toString().trim());
            user.setPassword(mTextInputEditTextPassword.getText().toString().trim());
            mDatabaseHelper.addUser(user);
            Snackbar.make(mNestedScrollView, getString( R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            Snackbar.make(mNestedScrollView, getString( R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        mTextInputEditTextName.setText(null);
        mTextInputEditTextEmail.setText(null);
        mTextInputEditTextPassword.setText(null);
        mTextInputEditTextConfirmPassword.setText(null);
    }
}
