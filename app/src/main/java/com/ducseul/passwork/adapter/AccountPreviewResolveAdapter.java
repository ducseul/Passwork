package com.ducseul.passwork.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ducseul.passwork.R;
import com.ducseul.passwork.entity.AccountRecord;
import com.ducseul.passwork.helper.KEY;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.SecureRandom;

public class AccountPreviewResolveAdapter {
    private BottomSheetDialog bottomSheetDialog;
    private View view;
    private AccountRecord accountRecord;
    private ClipboardManager clipboardManager;
    private MainRVAdapter mainRVAdapter;
    private Handler handler;

    private RelativeLayout preview_account_layout;
    private ImageButton btn_close;
    private TextView tv_title;
    private Button btn_save_change;
    private EditText et_username;
    private ImageButton btnCopyUsername;
    private EditText et_password;
    private ImageButton btn_auto_password;
    private ImageButton btn_edit_password;
    private ImageButton btn_copy_password;
    private AutoCompleteTextView multiAutoCompleteTextView;
    private TextView last_modify_stamp;

    //TODO: Detect when user name and description change => change button delete to save (currently only work with autogenerate password)
    //TODO: Generate password not currently have length regulated

    public AccountPreviewResolveAdapter(View view, AccountRecord accountRecord, MainRVAdapter mainRVAdapter, BottomSheetDialog bottomSheetDialog) {
        this.view = view;
        this.accountRecord = accountRecord;
        this.bottomSheetDialog = bottomSheetDialog;
        this.mainRVAdapter = mainRVAdapter;
        clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        handler = new Handler();

        preview_account_layout = view.findViewById(R.id.preview_account_layout);
        btn_close = view.findViewById(R.id.btn_close);
        tv_title = view.findViewById(R.id.tv_title);
        btn_save_change = view.findViewById(R.id.btn_save_change);
        et_username = view.findViewById(R.id.et_username);
        btnCopyUsername = view.findViewById(R.id.btnCopyUsername);
        et_password = view.findViewById(R.id.et_password);
        btn_auto_password = view.findViewById(R.id.btn_auto_password); //auto generate password
        btn_edit_password = view.findViewById(R.id.btn_edit_password);
        btn_copy_password = view.findViewById(R.id.btn_copy_password);
        multiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView);
        last_modify_stamp = view.findViewById(R.id.last_modify_stamp);
        doFillData();
        doInitEvent();
    }

    private void doFillData() {
        tv_title.setText(accountRecord.getAccountTitle());
        et_username.setText(accountRecord.getAccountUsername());
        String passsw = "";
        for (int i = 0; i < accountRecord.getAccountPassword().length(); i++) {
            passsw += "*";
        }
        et_password.setText(passsw);
        multiAutoCompleteTextView.setText(accountRecord.getAccountDescription());
        last_modify_stamp.setText(accountRecord.getAccountCreateDate().toString());
    }

    private void doInitEvent() {
        btn_save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_save_change.getText().toString().toUpperCase().equals("SAVE")) {
                    TransitionManager.beginDelayedTransition(preview_account_layout, new AutoTransition());
                    btn_save_change.setText("DELETE");
                    et_password.setVisibility(View.GONE);
                    btn_auto_password.setVisibility(View.GONE);
                    btn_edit_password.setVisibility(View.VISIBLE);
                    btn_copy_password.setVisibility(View.VISIBLE);

                    int index = KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().indexOf(accountRecord);
                    KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().get(index)
                            .setAccountUsername(et_username.getText().toString());
                    KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().get(index)
                            .setAccountPassword(et_password.getText().toString());
                    KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().get(index)
                            .setAccountDescription(multiAutoCompleteTextView.getText().toString());
                    KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().get(index)
                            .generateCreateDate();
                    KEY.CACHE_DATA_OBJECT_MANAGER.saveChangeUpdate(view.getContext());
                    mainRVAdapter.setDataObjects(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData());
                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().remove(accountRecord);
                                    KEY.CACHE_DATA_OBJECT_MANAGER.saveChangeUpdate(view.getContext());
                                    bottomSheetDialog.dismiss();
                                    mainRVAdapter.setDataObjects(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure to delete account ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
        btnCopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtcopy = et_username.getText().toString();
                copy2Clipboard(txtcopy);
            }
        });
        btn_auto_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                int length = 12;
                SecureRandom rnd = new SecureRandom();
                StringBuilder sb = new StringBuilder(length);
                for (int i = 0; i < length; i++)
                    sb.append(AB.charAt(rnd.nextInt(AB.length())));
                et_password.setText(sb.toString());

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(preview_account_layout, new AutoTransition());
                        et_password.setVisibility(View.GONE);
                        btn_auto_password.setVisibility(View.GONE);
                        btn_edit_password.setVisibility(View.VISIBLE);
                        btn_copy_password.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }
        });
        btn_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(preview_account_layout, new AutoTransition());
                et_password.setVisibility(View.VISIBLE);
                btn_auto_password.setVisibility(View.VISIBLE);
                btn_edit_password.setVisibility(View.GONE);
                btn_copy_password.setVisibility(View.GONE);
                btn_save_change.setText("SAVE");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(preview_account_layout, new AutoTransition());
                        et_password.setVisibility(View.GONE);
                        btn_auto_password.setVisibility(View.GONE);
                        btn_edit_password.setVisibility(View.VISIBLE);
                        btn_copy_password.setVisibility(View.VISIBLE);
                    }
                }, 5000);
            }
        });
        btn_copy_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtcopy = accountRecord.getAccountPassword();
                copy2Clipboard(txtcopy);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        initChangeDetectedEvent();
    }

    private void initChangeDetectedEvent() {
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valueChanged = s.toString();
                if (!accountRecord.getAccountUsername().equalsIgnoreCase(valueChanged)) {
                    if (btn_save_change.getText().toString().equalsIgnoreCase("DELETE"))
                        fipBtnValue();
                } else {
                    if (btn_save_change.getText().toString().equalsIgnoreCase("SAVE"))
                        fipBtnValue();
                }
            }
        });
    }

    private void copy2Clipboard(String txtcopy) {
        ClipData text = ClipData.newPlainText("text", txtcopy);
        clipboardManager.setPrimaryClip(text);
        Toast.makeText(view.getContext(), "Copy success", Toast.LENGTH_SHORT).show();
    }

    private void fipBtnValue() {
        if (btn_save_change.getText().toString().toUpperCase().equals("DELETE")) {
            btn_save_change.setText("SAVE");
        } else {
            btn_save_change.setText("DELETE");
        }
    }
}
