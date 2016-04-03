package com.ilmnuri.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ExceptionViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_view);
        TextView textView = (TextView)findViewById(R.id.tv_exception_view);
        String exception =  "Uh, ah bunisini kutmagandik! Odatda, INTERNET yo'q bo'lsa yuklanmagan " +
                "ma'ruzani yuklamoqchi bo'lsangiz shu xatni o'qiysiz. Agarda internet bo'lsa ham " +
                "shunday bo'lgan bo'lsa, appni qayta o'chirib yondiring iltimos. Unda ham " +
                "bo'lmasa bizga habar qiling ilmnuri@ilmnuri.com. " +
                "Nosozliklar uchun oldindan uzur so'raymiz. -- Team ilmnuri";
        textView.setText(exception);
    }

}
