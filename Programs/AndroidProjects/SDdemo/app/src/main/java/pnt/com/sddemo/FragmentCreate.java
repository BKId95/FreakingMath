package pnt.com.sddemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by pnt on 9/20/15.
 */
public class FragmentCreate extends Fragment {

    private EditText ed_mail;
    private EditText ed_pass;
    private EditText ed_repass;
    private Button button_create;
    NetWork netWork = new NetWork();
    private int check = 0;
    String mail = null;
    String pass = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account,null);
        ed_mail = (EditText) view.findViewById(R.id.editText_cre_mail);
        ed_pass = (EditText) view.findViewById(R.id.editText_cre_pass);
        ed_repass = (EditText) view.findViewById(R.id.editText_cre_repass);
        button_create = (Button) view.findViewById(R.id.but_cre_creaccount);

        button_create.setEnabled(false);
        ed_mail.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (ed_mail.getText().toString().equals("") == false
                        && ed_pass.getText().toString().equals("") == false
                        && ed_repass.getText().toString().equals("") == false) {
                    button_create.setEnabled(true);
                    button_create.setBackgroundResource(R.drawable.demo2);
                } else {
                    button_create.setEnabled(false);
                    button_create.setBackgroundResource(R.drawable.demo);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        ed_pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (ed_mail.getText().toString().equals("") == false
                        && ed_pass.getText().toString().equals("") == false
                        && ed_repass.getText().toString().equals("") == false) {
                    button_create.setEnabled(true);
                    button_create.setBackgroundResource(R.drawable.demo2);
                } else {
                    button_create.setEnabled(false);
                    button_create.setBackgroundResource(R.drawable.demo);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        ed_repass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (ed_mail.getText().toString().equals("") == false
                        && ed_pass.getText().toString().equals("") == false
                        && ed_repass.getText().toString().equals("") == false) {
                    button_create.setEnabled(true);
                    button_create.setBackgroundResource(R.drawable.demo2);
                } else {
                    button_create.setEnabled(false);
                    button_create.setBackgroundResource(R.drawable.demo);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        ed_pass.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                ed_pass.setText("");
                return false;
            }
        });
        ed_repass.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                ed_repass.setText("");
                return false;
            }
        });
        button_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mail = ed_mail.getText().toString();
                pass = ed_pass.getText().toString();
                String repass = ed_repass.getText().toString();
                netWork.setMail(mail);
                netWork.setPass(pass);
                if (!isValidEmail(mail)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setTitle("Fail");
                    builder.setMessage("Mail incorrect @@");
                    builder.setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCancelable(false);
                } else if (netWork.checkInternetConnect(getActivity())) {
                    check = 0;
                    if(pass.equals(repass)==true) {
                        NetWorkAsyncTask nw = (NetWorkAsyncTask) new NetWorkAsyncTask().execute("http://thachpn.name.vn/account/create_account.php");
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Error");
                        builder.setMessage("Có nhập lại mật khẩu mà cũng sai @@");
                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Internet");
                    builder.setMessage("Có đéo mạng đâu @@");
                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            button_create.callOnClick();
                        }
                    });
                    builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCancelable(false);
                }
            }
        });
        return  view;
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }

    }
    public class NetWorkAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog pb;
        @Override
        protected void onPreExecute() {
            pb = new ProgressDialog(getActivity());
            pb.setMessage("Creating...");
            pb.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if(pb!=null){
                pb.dismiss();
            }
            if(s!=null){
                check = netWork.checkAccount(s);
                if (check == 1) {
                    SharedPreferences pre = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pre.edit();
                    editor.putString("mail", mail);
                    editor.putString("pass", pass);
                    editor.commit();
                    getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentLogin()).commit();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Fail");
                    builder.setMessage("Account existed");
                    builder.setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            HttpResponse response = null;
            try{
                response = netWork.makeRquest(url);
            }catch (IOException e){
                return  null;
            }
            if(response!=null){
                String content = null;
                try{
                    content = netWork.processHTTPResponce(response);
                    return content;
                }catch (IOException e){
                    return  null;
                } catch (ParseException e) {
                    return  null;
                }
            }
            return null;

        }
    }
}
