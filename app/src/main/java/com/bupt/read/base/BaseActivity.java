package com.bupt.read.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bupt.read.R;

/**
 * Created by xs on 2016/5/28.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    //布局文件Id
    protected abstract int getLayoutId();
    //布局中的Fragment的id
    protected abstract int getFragmentContentId();

    protected Dialog dialog;

   // protected Context context;
    
    public FragmentManager fgManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fgManager = getSupportFragmentManager();
       // dialog = createLoadingDialog("正在加载",true);
       // context = getApplicationContext();
        setContentView(getLayoutId());
        initViews();
        setListener();

    }

    protected void setListener() {
    }


    //初始化View
    public void initViews() {

    }


    //添加Fragment
    protected void addFragment(BaseFragment fragment){
        if(fragment != null){
            fgManager.beginTransaction().replace(getFragmentContentId(),fragment,fragment.getClass()
                    .getSimpleName()).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }


    
    //移除Fragment
    protected void removeFragment(){
        if(fgManager.getBackStackEntryCount() > 1){
            fgManager.popBackStack();
        }else{
            finish();
        }
    }

    //返回键返回事件


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(fgManager.getBackStackEntryCount() == 1){
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public Dialog createLoadingDialog(Context context,String msg,boolean cancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.customprogressdialog, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局
        TextView tip_text = (TextView) v.findViewById(R.id.tipTextView);
        tip_text.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(cancelable);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }
}
