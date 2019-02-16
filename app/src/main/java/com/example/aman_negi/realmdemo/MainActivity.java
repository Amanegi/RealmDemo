package com.example.aman_negi.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtPass;
    private Button btnSave, btnView, btnDelete, btnUpdate;
    private TextView text;
    Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPsswd);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        text = findViewById(R.id.text);

        btnSave.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        //initialize Realm
        Realm.init(this);
        //get a realm instance for this thread
        myRealm = Realm.getDefaultInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show();
                myRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Person person = realm.createObject(Person.class);
                        person.setName(edtName.getText().toString());
                        person.setPassword(edtPass.getText().toString());
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btnView:
                Toast.makeText(this, "VIEW", Toast.LENGTH_SHORT).show();
                RealmResults<Person> results = myRealm.where(Person.class).findAll();
                StringBuffer stringBuffer = new StringBuffer();
                for (Person p : results) {
                    stringBuffer.append(p.getName() + "\t" + p.getPassword() + "\n");
                }
                text.setText(stringBuffer.toString());
                break;
            case R.id.btnDelete:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
                myRealm.beginTransaction();
                RealmResults<Person> results1 = myRealm.where(Person.class).equalTo("name", edtName.getText().toString()).findAll();
                results1.deleteAllFromRealm();
                myRealm.commitTransaction();
                break;
            case R.id.btnUpdate:
                Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
                myRealm.beginTransaction();
                RealmResults<Person> results2 = myRealm.where(Person.class).equalTo("name", edtName.getText().toString()).findAll();
                if (results2.size() > 0) {
                    results2.get(0).setPassword("Tested OK");
                }else{
                    Toast.makeText(this, "Entry not present", Toast.LENGTH_SHORT).show();
                }
                myRealm.commitTransaction();
                break;
        }
    }
}