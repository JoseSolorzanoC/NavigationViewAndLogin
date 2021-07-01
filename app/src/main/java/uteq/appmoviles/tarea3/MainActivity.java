package uteq.appmoviles.tarea3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button btnRegistrar;
    Button btnAcceder;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }

    private void showAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMenu(FirebaseUser user){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        menuIntent.putExtra("user", user);
        startActivity(menuIntent);
    }

    public void setup(){
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEmail.getText().toString().isEmpty()
                        && !txtPassword.getText().toString().isEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            txtEmail.getText().toString(), txtPassword.getText().toString())
                            .addOnCompleteListener(command -> {
                                if (command.isSuccessful()){
                                    showMenu(FirebaseAuth.getInstance().getCurrentUser());
                                }else{
                                    showAlert(command.getException().getMessage().toString());
                                }
                            });
                }
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEmail.getText().toString().isEmpty()
                    && !txtPassword.getText().toString().isEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            txtEmail.getText().toString(), txtPassword.getText().toString())
                            .addOnCompleteListener(command -> {
                                if (command.isSuccessful()){
                                    showMenu(FirebaseAuth.getInstance().getCurrentUser());
                                }else{
                                    showAlert(command.getException().getMessage().toString());
                                }
                            });
                }
            }
        });
    }
}