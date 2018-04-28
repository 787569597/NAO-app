package com.example.nao.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nao.R;
import com.example.nao.Util.ToastUtils;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 爱冒险的小鸡仔 on 2018/4/18.
 */

public class SignUpActivity extends Activity implements OnClickListener{
    private static final String TAG = "SignupActivity";

    //界面控件
    private EditText userNameEdit;
    private EditText UserPasswordEdit;
    private EditText CheckUserPasswordEdit;
    private Button createAccountButton;

    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        initViews();
    }
    /**
     * 通过findViewById,减少重复的类型转换
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id){
        try{
            return (E) findViewById(id);
        }catch (ClassCastException ex){
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    private void initViews(){

        createAccountButton = getView(R.id.btn_login);
        createAccountButton.setOnClickListener(this);
        userNameEdit = getView(R.id.et_userNameEdit);
        /* editText.setImeOptions(EditorInfo.IME_ACTION_XXX)为设置输入法软键盘右下角回车键的内容 */
        userNameEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT); //下一步
        UserPasswordEdit = getView(R.id.et_UserPasswordEdit);
        UserPasswordEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        CheckUserPasswordEdit = getView(R.id.et_CheckUserPasswordEdit);
        CheckUserPasswordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        CheckUserPasswordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);

        CheckUserPasswordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // 点击软键盘中的done
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO){
                    try{
                        commit();
                    }catch (IOException | JSONException e1){
//                        命令行打印错误信息位置及原因
                        e1.printStackTrace();
                    }
                }
                return false;
                /*
                Log.e("点击CheckUserPasswordEdit", "没有响应");
                text.setText("Editing CheckUserPasswordEdit");
                return false;
                 */
            }
        });
    }
    private void commit()throws IOException, JSONException{
        String userName =  userNameEdit.getText().toString().trim();
        String UserPassword = UserPasswordEdit.getText().toString().trim();
        String CheckUserPassword = CheckUserPasswordEdit.getText().toString().trim();

        if (checkInput(userName, UserPassword, CheckUserPassword)){
            //请求服务端注册账号
            createAccountButton.setOnClickListener(new OnClickListener(){

                @Override
                        public void onClick(View arg0){
                    //android4.0后的新的特性，网络数据请求时不能放在主线程中。
                    //使用线程执行访问服务器，获取返回信息后通知主线程更新UI或者提示信息。
                    final Handler handler = new Handler(){
                        @Override
                        public  void handleMessage(Message msg){
                            if (msg.what == 1){
                                //提示读取结果
                                Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
                                if(result.contains("成")){
                                    Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
                                    ToastUtils.showShort(SignUpActivity.this, "注册成功");
                                }
                                else{
                                    final Intent it = new Intent(SignUpActivity.this, LoginActivity.class);//你要转向的Activity
                                    Timer timer = new Timer();
                                    TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            startActivity(it); //执行
                                        }
                                    };
                                    timer.schedule(task, 1000); //1秒后
                                }
                            }
                        }
                    };
                    //启动线程来执行任务
                    new Thread(){
                        public void run(){
                            //请求网络
                            try{
                                Register(userNameEdit.getText().toString(), UserPasswordEdit.getText().toString(), CheckUserPasswordEdit.getText().toString());
                            }catch (IOException | JSONException e){
                                e.printStackTrace();
                            }
                            Message m = new Message();
                            m.what = 1;
                            //发送消息到Handler
                            handler.sendMessage(m);
                        }
                    }.start();
                }
            });
        }
    }
    private boolean checkInput(String userName, String UserPassword,String CheckUserPassword){
        if (TextUtils.isEmpty(userName) || userName.length() > 10){//用户名格式
            ToastUtils.showShort(this, R.string.tip_userName_please_input_in_10);
        }
        else{
            if (UserPassword.length()<8 ||UserPassword.length()>16 || TextUtils.isEmpty(UserPassword)){
                ToastUtils.showShort(this, R.string.tip_UserPassword_please_input_8_16);
            }else if(UserPassword != CheckUserPassword){
                ToastUtils.showShort(this, R.string.tip_CheckUserPassword_different);
            }else{
                return true;
            }
        }
        return true;
    }

    public Boolean Register(String userName, String UserPassword,String CheckUserPassword) throws IOException, JSONException{
        try {
            String httpUrl ="";
            URL url = new URL(httpUrl); //创建一个URL
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//通过该url获得与服务器的连接
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");//设置请求方式为post
            connection.setConnectTimeout(3000); //设置超时为3秒
            //设置传送类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            //提交数据
            String data = "&userName=" + URLEncoder.encode(userName,"UTF-8") + "&userpassword="+ UserPassword +"&checkuserpassword=" + CheckUserPassword;//传递的数据
            connection.setRequestProperty("Content-Length",String.valueOf(data.getBytes().length));
            ToastUtils.showShort(this, "数据提交成功");

            //获取输出流
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            //获取相应输出流对象
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(is);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            //读取服务器返回信息
            while ((line = bufferedReader.readLine())!= null){
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            is.close();
            connection.disconnect();
        }catch (Exception e){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
