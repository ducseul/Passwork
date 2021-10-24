package com.ducseul.passwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ducseul.passwork.R;
import com.ducseul.passwork.adapter.AccountCreateResolveAdapter;
import com.ducseul.passwork.adapter.AccountPreviewResolveAdapter;
import com.ducseul.passwork.adapter.MainRVAdapter;
import com.ducseul.passwork.entity.AccountRecord;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout main_layout;

    private EditText et_search_bar;
    private RecyclerView all_info;

    private ImageButton btn_quick_filter;
    private LinearLayout sub_menu;
    private FloatingActionButton fab;

    private CardView fab_add_note_wrapper;
    private FloatingActionButton fab_addNote;

    private CardView fab_add_account_wrapper;
    private FloatingActionButton fab_addAccount;

    private CardView cv_add_auth2o_account;
    private FloatingActionButton fab_addAuth2OAccount;

    private CardView fab_more_setting_wrapper;
    private FloatingActionButton fab_more_setting;

    private Spinner sort;
    private Spinner type;

    private MainRVAdapter mainRVAdapter;
    private boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doInitWidget();
        doInitEvent();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        System.out.println("FUCK");
    }

    private void doInitWidget() {
        main_layout = findViewById(R.id.main_layout);
        et_search_bar = findViewById(R.id.et_search_bar);
        et_search_bar.clearFocus();

        fab = findViewById(R.id.fab);
        fab_addNote = findViewById(R.id.fab_addNote);
        fab_addAccount = findViewById(R.id.fab_addAccount);

        sub_menu = findViewById(R.id.sub_menu);
        sub_menu.setVisibility(View.INVISIBLE);

        all_info = findViewById(R.id.all_info);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        all_info.setLayoutManager(linearLayoutManager);
        mainRVAdapter = new MainRVAdapter(this);
        all_info.setAdapter(mainRVAdapter);

        fab_add_account_wrapper = findViewById(R.id.cv_add_account);
        btn_quick_filter = findViewById(R.id.btn_quick_filter);

        fab_add_note_wrapper = findViewById(R.id.fab_add_note_wrapper);
        fab_addNote = findViewById(R.id.fab_addNote);

        fab_more_setting_wrapper = findViewById(R.id.fab_more_setting_wrapper);
        fab_more_setting = findViewById(R.id.fab_more_setting);

        cv_add_auth2o_account = findViewById(R.id.cv_add_auth2o_account);
        fab_addAuth2OAccount = findViewById(R.id.fab_addAuth2OAccount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search_bar.clearFocus();
        main_layout.requestFocus();
    }

    private void doInitEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fab_add_account_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAddAccountPopup();
                closeFABMenu();
            }
        });

        fab_addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAddAccountPopup();
                closeFABMenu();
            }
        });

        fab_add_note_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "NOT SUPPORTED YET", Toast.LENGTH_SHORT).show();
                closeFABMenu();
            }
        });

        fab_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "NOT SUPPORTED YET", Toast.LENGTH_SHORT).show();
                closeFABMenu();
            }
        });

        fab_more_setting_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdvanceSettingActivity.class);
                closeFABMenu();
                startActivity(intent);
            }
        });

        fab_more_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdvanceSettingActivity.class);
                closeFABMenu();
                startActivity(intent);
            }
        });

        cv_add_auth2o_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "NOT SUPPORTED YET", Toast.LENGTH_SHORT).show();
                closeFABMenu();
            }
        });
        fab_addAuth2OAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "NOT SUPPORTED YET", Toast.LENGTH_SHORT).show();
                closeFABMenu();
            }
        });

        et_search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mainRVAdapter.getFilter().filter(s.toString());
            }
        });
    }

    private void showFABMenu() {
        isFABOpen = true;
        sub_menu.animate().translationY(-180);
        sub_menu.setVisibility(View.VISIBLE);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        sub_menu.animate().translationY(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sub_menu.setVisibility(View.INVISIBLE);
            }
        }, 100);
    }

    public void doViewAccountShowPopup(AccountRecord accountRecord) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.AppBottomSheetDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.layout_preview_account, null);
        new AccountPreviewResolveAdapter(view, accountRecord, mainRVAdapter, bottomSheetDialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    public void doAddAccountPopup() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.AppBottomSheetDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.layout_create_account, null);
        new AccountCreateResolveAdapter(view, mainRVAdapter, bottomSheetDialog); //attach view adapter to resolve
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}