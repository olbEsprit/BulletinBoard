package kampus.bulletinboard;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Павел on 12.12.2015.
 */
public class BulletinDetails extends ActionBarActivity implements android.view.View.OnClickListener {
    Button btnSave, btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    private int _Student_Id = 0;
    RestService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_bulletin_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Student_Id = 0;
        Intent intent = getIntent();
        _Student_Id = intent.getIntExtra("student_Id", 0);
        if (_Student_Id > 0) {
            restService.getService().getBulletinById(_Student_Id, new Callback<Bulletin>() {
                @Override
                public void success(Bulletin bulletin, Response response) {

                    editTextName.setText(bulletin.text);
                    editTextEmail.setText(bulletin.title);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(BulletinDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            restService.getService().deleteBulletinById(_Student_Id, new Callback<Bulletin>() {
                @Override
                public void success(Bulletin bulletin, Response response) {
                    Toast.makeText(BulletinDetails.this, "Student Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(BulletinDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                }

            });

            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            Bulletin bulletin = new Bulletin(editTextEmail.getText().toString(), editTextName.getText().toString(), _Student_Id);
            Integer status = 0;
            //student.Email= editTextEmail.getText().toString();
            //student.Name=editTextName.getText().toString();
            //student.Age= Integer.parseInt(editTextAge.getText().toString());
            //student.Id = _Student_Id;

            if (_Student_Id == 0) {
                restService.getService().addBulletin(bulletin, new Callback<Bulletin>() {
                    @Override
                    public void success(Bulletin bulletin1, Response response) {
                        Toast.makeText(BulletinDetails.this, "New Student Inserted.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(BulletinDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });
            } else {
                restService.getService().updateBulletinById(_Student_Id, bulletin, new Callback<Bulletin>() {
                    @Override
                    public void success(Bulletin bulletin1, Response response) {
                        Toast.makeText(BulletinDetails.this, "Student Record updated.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(BulletinDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });
            }


        }
    }
}