package com.example.qltc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtDu;
    SearchView searchView;
    ListView lstView;
    Button btnAdd;
    ArrayList<ThuChi> arrayList;
    Adapter adapter;

    SQLite mysql = new SQLite(this, "QLTC", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert();

        txtDu = findViewById(R.id.txtDu);
        searchView = findViewById(R.id.edtSearch);
        lstView = findViewById(R.id.lstContact);
        btnAdd = findViewById(R.id.btnAdd);

//        mysql.addContact(new ThuChi(1,"Luong", "11/12/2023",100000,1));
//        mysql.addContact(new ThuChi(2,"Mua sam", "23/04/2023",10000,0));
//        mysql.addContact(new ThuChi(3,"An uong", "11/02/2023",10000,0));

        arrayList = mysql.getAllContact();
        adapter = new Adapter(this, arrayList);
        lstView.setAdapter(adapter);

        int soDu=0;
        for(ThuChi thuChi: arrayList){
            if(thuChi.isThuChi()==1)
                soDu+= thuChi.getCost();
            else
                soDu-= thuChi.getCost();
        }
        txtDu.setText("Số dư: "+String.valueOf(soDu));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayList = mysql.getContactBySearch(newText);
                adapter = new Adapter(MainActivity.this, arrayList);
                lstView.setAdapter(adapter);
                return false;
            }
        });

    }
    private boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private void alert(){
        if(isOnline(MainActivity.this) == false){
            Toast.makeText(MainActivity.this, "Mất Kết Nối, Không Thể Đồng Bộ Dữ Liệu Lên Cloud!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Sẵn Sàng Đồng Bộ Dữ Liệu Lên Cloud!", Toast.LENGTH_LONG).show();
        }
    }
}