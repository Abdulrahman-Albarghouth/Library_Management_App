package com.example.library_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private TextView fullname;
    ImageView IMGV;
    private int ID;
    private MySQLiteOpenHelper helper;
    private UserDataSource uds;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        try {
            helper = new MySQLiteOpenHelper(this,"Library_DB",null,1);
            uds = new UserDataSource(helper);
            drawerLayout = findViewById(R.id.drawer);
            navigationView = findViewById(R.id.drawer_nav);
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) AdminHomeActivity.this);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }

            ID = getIntent().getIntExtra("ID",0);
            User user = uds.getUserById(ID);
            View header = navigationView.getHeaderView(0);
            IMGV = header.findViewById(R.id.img_view);
            fullname = header.findViewById(R.id.tfullname);
            fullname.setText(user.getFirstName().toUpperCase()+" "+user.getLastName().toUpperCase());
            bundle = new Bundle();
            bundle.putInt("ID",ID);
            profileFragment profileFragment = new profileFragment();
            profileFragment.setArguments(bundle);


        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                profileFragment profilefragment = new profileFragment();
                profilefragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profilefragment).addToBackStack(null).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new usersFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_author:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new authorsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new booksFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new categoryFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                onCreateDialog();
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openProfile(View view) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ID);
            profileFragment profilefragment = new profileFragment();
            profilefragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profilefragment).addToBackStack(null).commit();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    public Dialog onCreateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
        builder.setTitle(R.string.question);
        builder.setMessage(R.string.logoutmessage);
        builder.setIcon(R.drawable.ic_baseline_help_24);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no,null);
        builder.create();
        return builder.show();
    }
}