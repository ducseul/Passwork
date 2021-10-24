package com.ducseul.passwork.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.ducseul.passwork.R;
import com.ducseul.passwork.entity.AccountRecord;
import com.ducseul.passwork.helper.KEY;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;

import java.security.SecureRandom;

public class AccountCreateResolveAdapter {

    private BottomSheetDialog bottomSheetDialog;
    private MainRVAdapter mainRVAdapter;
    private ClipboardManager clipboardManager;
    private View view;

    private RelativeLayout create_account_layout;
    private Button btn_save_change;
    private AutoCompleteTextView et_title;
    private ImageButton btnPasteTitle;
    private EditText et_website;
    private ImageButton btnPasteWebsite;
    private EditText et_username;
    private ImageButton btnPasteUsername;
    private EditText et_password;
    private ImageButton btnGenerate;
    private ImageButton btnPastePassword;
    private Slider rangeslider;
    private EditText et_descriptio;

    private Handler handler;

    public AccountCreateResolveAdapter(View view, MainRVAdapter mainRVAdapter, BottomSheetDialog bottomSheetDialog) {
        this.view = view;
        this.mainRVAdapter = mainRVAdapter;
        this.bottomSheetDialog = bottomSheetDialog;

        clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        handler = new Handler();

        create_account_layout = view.findViewById(R.id.create_account_layout);
        btn_save_change = view.findViewById(R.id.btn_save_change);
        et_title = view.findViewById(R.id.et_title);
        btnPasteTitle = view.findViewById(R.id.btnPasteTitle);
        et_website = view.findViewById(R.id.et_website);
        btnPasteWebsite = view.findViewById(R.id.btnPasteWebsite);
        et_username = view.findViewById(R.id.et_username);
        btnPasteUsername = view.findViewById(R.id.btnPasteUsername);
        et_password = view.findViewById(R.id.et_password);
        btnGenerate = view.findViewById(R.id.btnGenerate);
        btnPastePassword = view.findViewById(R.id.btnPastePassword);
        rangeslider = view.findViewById(R.id.rangeslider);
        et_descriptio = view.findViewById(R.id.et_description);


        ArrayAdapter adapterCountries
                = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_1,
                KEY.CACHE_MANAGER.AUTO_COMPLETE_DICT.keySet().toArray());
        et_title.setAdapter(adapterCountries);

        doInitEvent();
    }

    private void doInitEvent() {
        btn_save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickBtnSaveChange(v);
            }
        });
        btnPasteTitle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (et_title.getText().toString().length() == 0) {
                    onClickBtnPasteTitle(v);
                    btnPasteTitle.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    et_title.setText("");
                    btnPasteTitle.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });
        btnPasteWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_website.getText().toString().length() == 0) {
                    onClickBtnPasteWebsite(v);
                    btnPasteWebsite.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    et_website.setText("");
                    btnPasteWebsite.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });
        btnPasteUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().length() == 0) {
                    onClickBtnPasteUsername(v);
                    btnPasteUsername.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    et_username.setText("");
                    btnPasteUsername.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });
        btnPastePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_password.getText().toString().length() == 0) {
                    onClickBtnPastePassword(v);
                    btnPastePassword.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    et_password.setText("");
                    btnPastePassword.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float value = rangeslider.getValue();
                TransitionManager.beginDelayedTransition(create_account_layout, new AutoTransition());
                rangeslider.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(create_account_layout, new AutoTransition());
                        rangeslider.setVisibility(View.GONE);
                    }
                }, 3000);
                onClickBtnGeneratePassword(v, Integer.parseInt((value + "").subSequence(0, (value + "").length() - 2).toString() + ""));
            }
        });
        rangeslider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                String s = (value + "").subSequence(0, (value + "").length() - 2).toString();
                onClickBtnGeneratePassword(null, Integer.parseInt(s));
                //Auto hide range after while
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(create_account_layout, new AutoTransition());
                        rangeslider.setVisibility(View.GONE);
                    }
                }, 4000);
            }
        });

        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String url = KEY.CACHE_MANAGER.AUTO_COMPLETE_DICT.get(s.toString());
                    if(url != null){
                        et_website.setText(url);
                    }
                    btnPasteTitle.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    btnPasteTitle.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });

        et_website.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btnPasteWebsite.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    btnPasteWebsite.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btnPasteUsername.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    btnPasteUsername.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btnPastePassword.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_delete_24));
                } else {
                    btnPastePassword.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_content_paste_24));
                }
            }
        });
    }

    private void onClickBtnGeneratePassword(View v, int length) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        et_password.setText(sb.toString());
    }

    private void onClickBtnPastePassword(View v) {
        String txtClipboard = getClipboard();
        et_password.setText(txtClipboard);
    }

    @NonNull
    private String getClipboard() {
        ClipData pData = clipboardManager.getPrimaryClip();
        ClipData.Item item = pData.getItemAt(0);
        return item.getText().toString();
    }

    private void onClickBtnPasteUsername(View v) {
        String txtClipboard = getClipboard();
        et_username.setText(txtClipboard);
    }

    private void onClickBtnPasteWebsite(View v) {
        String txtClipboard = getClipboard();
        et_website.setText(txtClipboard);
    }

    private void onClickBtnPasteTitle(View v) {
        String txtClipboard = getClipboard();
        et_title.setText(txtClipboard);
    }

    private void onCLickBtnSaveChange(View v) {
        if (verify()) {
            AccountRecord accountRecord = new AccountRecord()
                    .builder()
                    .accountTitle(et_title.getText().toString())
                    .websiteUrl(et_website.getText().toString())
                    .accountUsername(et_username.getText().toString())
                    .accountPassword(et_password.getText().toString())
                    .accountDescription(et_descriptio.getText().toString())
                    .build();
            accountRecord.generateCreateDate();
            accountRecord.generateUUID();
            KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData().add(accountRecord);
            mainRVAdapter.setDataObjects(KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData());
            KEY.CACHE_DATA_OBJECT_MANAGER.saveChangeUpdate(view.getContext());
            bottomSheetDialog.dismiss();
        }
    }

    private boolean verify() {
        //TODO: add filter before save
        return true;
    }
}
