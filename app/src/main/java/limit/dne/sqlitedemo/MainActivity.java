package limit.dne.sqlitedemo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dh;
    EditText name;
    EditText email;
    EditText password;
    EditText id;
    Button add;
    Button view;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        id = (EditText) findViewById(R.id.editTextID);
        add = (Button) findViewById(R.id.buttonAdd);
        delete = (Button) findViewById(R.id.buttonDelete);
        update = (Button) findViewById(R.id.buttonUpdate);
        view = (Button) findViewById(R.id.buttonView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });
    }

    public void addData(){
        boolean success = dh.insertData(name.getText().toString(), email.getText().toString(), password.getText().toString());
        if (success){
            Toast.makeText(this, "Data Insertion Successfull", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data Insertion Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void dialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void viewData(){
        Cursor data = dh.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        while (data.moveToNext()){
            stringBuffer.append("\nName: " + data.getString(0));
            stringBuffer.append("\nEmail: " + data.getString(1));
            stringBuffer.append("\nPassword: " + data.getString(2) + "\n");
        }
        dialog("Data", stringBuffer.toString());
    }

    public void deleteData(){
        Integer success = dh.deleteData(id.getText().toString());
            Toast.makeText(this, "Deletion Successfull", Toast.LENGTH_LONG).show();
    }

    public void updateData(){
        dh.updateData(name.getText().toString(), email.getText().toString(), password.getText().toString());
        Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
    }
}
