package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    Button botonLogin,botonRegistro,botonGoogle,botonPago;

    EditText emailText, passText;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    List<String> emailsCocina = Arrays.asList("admin1@gmail.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);





        emailText = findViewById(R.id.cajaEmail);
        passText = findViewById(R.id.cajaPass);

        //Boton para iniciar sesión con correo y contraseña
        botonLogin = findViewById(R.id.login);
        botonLogin.setOnClickListener(view -> {

            //Iniciar sesion en Firebase
            String email = emailText.getText().toString();
            String pass = passText.getText().toString();
            String regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
            String regexPass = "^.{6,}$";

            if(email.isEmpty()) {
                emailText.setError("Campo vacio");
            }else if(!email.matches(regexEmail)){
                emailText.setError("Introduce un email válido");
            }else if(pass.isEmpty()){
                passText.setError("Campo vacio");
            }else if(!email.matches(regexPass)){
                emailText.setError("Introduce una contraseña válida");
            }else {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userEmail = user.getEmail();

                            Intent intent;
                            if (emailsCocina.contains(userEmail)) {
                                intent = new Intent(Login.this, Cocina.class);
                            } else {
                                intent = new Intent(Login.this, Carta.class);
                            }

                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        });

        //Boton para registrarte con correo y contraseña
        botonRegistro = findViewById(R.id.registro);
        botonRegistro.setOnClickListener(view -> {

                String email = emailText.getText().toString();
                String pass = passText.getText().toString();
                String regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
                String regexPass = "^.{6,}$";

                if(email.isEmpty()) {
                    emailText.setError("Campo vacio");
                }else if(!email.matches(regexEmail)){
                    emailText.setError("Introduce un email válido");
                }else if(pass.isEmpty()){
                    passText.setError("Campo vacio");
                }else if(!email.matches(regexPass)){
                    emailText.setError("Introduce una contraseña válida");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userEmail = user.getEmail();

                                Intent intent;
                                if (emailsCocina.contains(userEmail)) {
                                    intent = new Intent(Login.this, Cocina.class);
                                } else {
                                    intent = new Intent(Login.this, Carta.class);
                                }

                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
        });

        //Boton para iniciar sesion con google
        botonGoogle = findViewById(R.id.google);
        botonGoogle.setOnClickListener(view -> {
            signIn();
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            goNext();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        user = mAuth.getCurrentUser();
        if (user != null){
            goNext();
        }
    }

    private void goNext() {

        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();

        Intent intent;
        if (emailsCocina.contains(userEmail)) {
            intent = new Intent(Login.this, Cocina.class);
        } else {
            intent = new Intent(Login.this, Carta.class);
        }

        startActivity(intent);
    }

}