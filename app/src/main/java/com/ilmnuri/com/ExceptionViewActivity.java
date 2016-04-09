package com.ilmnuri.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ExceptionViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = (TextView)findViewById(R.id.tv_exception_view);

        String exception =  "Uh, ah bunisini kutmagandik! Odatda, INTERNET yo'q bo'lsa yuklanmagan " +
                "darslikni yuklamoqchi bo'lsangiz shu xatni o'qiysiz. Endi orqaga qaytishingiz mumkin. " +
                "Biroq, internet bor bo'lib shunday bo'lgan bo'lsa bizga xabar qiling ilmnuri@ilmnuri.com. " +
                "Nosozliklar uchun oldindan uzur so'raymiz.\n\n -- Team ilmnuri";
        textView.setText(exception);
    }

    //required for back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    //required for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_play, menu);
        return true;
    }
}
