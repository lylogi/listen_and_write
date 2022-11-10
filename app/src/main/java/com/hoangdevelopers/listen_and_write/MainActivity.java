package com.hoangdevelopers.listen_and_write;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rbddevs.splashy.Splashy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_about)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        setSplashy();
    }

    private void setSplashy(){
        new Splashy(this)         // For JAVA : new Splashy(this)
                .setLogo(R.mipmap.ic_launcher_foreground)
                .setTitle("Listen and Write")
                .setTitleColor("#FFFFFF")
                .setSubTitleColor(R.color.white)
                .setSubTitle("Practise your listening skills")
                .setProgressColor(R.color.white)
                .setBackgroundResource(R.color.colorPrimary)
                .setFullScreen(true)
                .setTime(5000)
                .show();
    }

}
