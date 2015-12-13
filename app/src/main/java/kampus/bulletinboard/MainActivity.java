package kampus.bulletinboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    RestService restService;
    TextView bulletin_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_main);

        //ArrayList<String> myDataset = getDataSet();
        Bulletin myDataset = getDataSetNew();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onResume() {

        super.onResume();
        //refreshScreen();
    }

    @Override
    public void onClick(View v) {
        if (v== findViewById(R.id.fab)){

            Intent intent = new Intent(this,BulletinDetails.class);
            intent.putExtra("student_Id",0);
            startActivity(intent);

        }else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }


    /*
    private void refreshScreen(){

        //Call to server to grab list of student records. this is a asyn
        restService.getService().getStudent(new Callback<List<Student>>() {
            @Override
            public void success(List<Student> students, Response response) {
                ListView lv = (ListView) findViewById(R.id.listView);

                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, R.layout.view_student_entry, students);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        student_Id = (TextView) view.findViewById(R.id.student_Id);
                        String studentId = student_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), StudentDetail.class);
                        objIndent.putExtra("student_Id", Integer.parseInt(studentId));
                        startActivity(objIndent);
                    }
                });
                lv.setAdapter(customAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
    */

    private void refreshScreen_SimpleWay(){

        restService.getService().getBulletin(new Callback<List<Bulletin>>() {
            @Override
            public void success(List<Bulletin> bulletins, Response response) {
                //ListView lv = (ListView) findViewById(R.id.listView);


                ArrayList<HashMap<String, String>> bulletinList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < bulletins.size(); i++) {
                    HashMap<String, String> bulletin = new HashMap<String, String>();
                    bulletin.put("id", String.valueOf(bulletins.get(i).id));
                    bulletin.put("text", String.valueOf(bulletins.get(i).text));
                    bulletin.put("title", String.valueOf(bulletins.get(i).title));
                    bulletinList.add(bulletin);
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bulletin_Id = (TextView) view.findViewById(R.id.bulletin_Id);
                        String bulletinId = bulletin_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), BulletinDetails.class);
                        objIndent.putExtra("bulletin_Id", Integer.parseInt(bulletinId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(MainActivity.this, bulletinList, R.layout.view_student_entry, new String[]{"id", "name"}, new int[]{R.id.bulletin_Id, R.id.bulletin_text});
                lv.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }




    private Bulletin getDataSetNew(){
        Bulletin B = new Bulletin("Title", "Text", 1);

        //for (int i = 0; i < B.length; i++) {
        //    B[i].text = "IdleText";
        //    B[i].title = "Lorem Ipsum";
        //}
        return B;
    }


    private ArrayList<String> getDataSet() {

        ArrayList<String> mDataSet = new ArrayList();

        for (int i = 0; i < 100; i++) {
            mDataSet.add(i, "Lorem Ipsum " + i);
        }
        mDataSet.add(mDataSet.size(), "пункт" + mDataSet.size() + 1);
        return mDataSet;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_actual:
            {
                Intent nav_actual = new Intent(this, MainActivity.class);
                startActivity(nav_actual);
                break;
            }
            case R.id.nav_all:
            {
                Intent nav_all = new Intent(this, MainActivity.class);
                startActivity(nav_all);
                break;
            }
            case R.id.nav_archieve:
            {
                Intent nav_archieve = new Intent(this, MainActivity.class);
                startActivity(nav_archieve);
                break;
            }
            case R.id.nav_settings:
            {
                Intent nav_settings = new Intent(this, SettingsActivity.class);
                startActivity(nav_settings);
                break;
            }
            case R.id.nav_about:
            {
                Intent nav_about = new Intent(this, About.class);
                startActivity(nav_about);
                break;
            }
            case R.id.nav_logout:
            {
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
