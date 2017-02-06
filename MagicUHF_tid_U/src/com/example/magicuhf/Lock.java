package com.example.magicuhf;

import android.app.Activity;
import android.hardware.uhf.magic.reader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Lock extends Activity {
    Button m_btnLock,m_btnKill,m_readepc;
    EditText m_editLPSW,m_editKPSW;
    Spinner m_spMem,m_spAction;
    TextView m_result,m_textepc;
    String m_strresult="";

    ArrayAdapter<String> m_adapter;
    private Handler mHandler = new MainHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock);
        m_readepc = (Button) findViewById(R.id.readepc);
        m_textepc = (TextView) findViewById(R.id.textEPC);
        m_btnLock=(Button)findViewById(R.id.BtnLock);
        m_btnKill=(Button)findViewById(R.id.BtnKill);
        m_editLPSW=(EditText)findViewById(R.id.Lockpassword);
        m_editKPSW=(EditText)findViewById(R.id.killpassword);
        m_spMem=(Spinner)findViewById(R.id.spinnerMembank);
        m_spAction=(Spinner)findViewById(R.id.spinnerAction);
        m_result=(TextView)findViewById(R.id.resultView);
        reader.m_handler=mHandler;
        //开放，锁定，永久开放，永久锁定:
        String []str={"OPEN","PWD R/W","Perma Open","Perma not R/W"};
        m_adapter=new ArrayAdapter<String>(Lock.this,android.R.layout.simple_spinner_item,str);
        m_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        m_spAction.setAdapter(m_adapter);
        String []strmem={"Kill PassWord","Access PassWord","EPC","TID","USER"};
        m_adapter=new ArrayAdapter<String>(Lock.this,android.R.layout.simple_spinner_item,strmem);
        m_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        m_spMem.setAdapter(m_adapter);
        if(null==reader.m_strPCEPC||reader.m_strPCEPC.length()<=0)
        {
            m_result.setText("Please select Lables first");
        }
        /**
         * @author Administrator (1)读取到标签EPC，读写只是针对单个标签操作： （2）选定标签
         * **/
        m_readepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                android.hardware.uhf.magic.reader.InventoryLables();
            }
        });
        /**
         * 锁定标签：
         */
        m_btnLock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String mimaStr = m_editLPSW.getText().toString().trim();
                if (mimaStr == null || mimaStr.equals("")) {
                    m_strresult += "Please enter your 8 - digit password!!\n";
                    m_result.setText(m_strresult);
                    return;
                }
                if(null==reader.m_strPCEPC||reader.m_strPCEPC.length()<=0)
                {
                    m_result.setText("Please select Lables first");
                    return;
                }
                byte[] passw = reader.stringToBytes(mimaStr);
                int LD=reader.GetLockPayLoad((byte)m_spMem.getSelectedItemPosition(),
                        (byte)m_spAction.getSelectedItemPosition());
                //int LD1=0x020080;//（ Mask+Action）
                byte[]epc=reader.stringToBytes(reader.m_strPCEPC);
                reader.LockLables(passw, epc.length,epc,LD);
            }
        });
        m_btnKill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String mimaStr = m_editKPSW.getText().toString().trim();
                Log.e("mimaStr=", mimaStr);
                if (mimaStr == null || mimaStr.equals("")) {
                    m_strresult += "Please enter your 8 - digit password!!\n";
                    m_result.setText(m_strresult);
                    return;
                }
                byte[] passw = reader.stringToBytes(mimaStr);
                byte[]epc=reader.stringToBytes(reader.m_strPCEPC);
                reader.KillLables(passw,epc.length, epc);
            }
        });
    }
    private class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==reader.locklable||msg.what==reader.killlable)
            {
                //if(m_strresult.indexOf((String)msg.obj)<0)
                {
                    Log.e("8888888888",(String)msg.obj+"\r\n");
                    m_strresult =(String)msg.obj;
                    m_strresult+="\r\n";
                    m_result.setText(m_strresult);
                }
            }
            if (msg.what==reader.msgreadepc) {
                //读取到标签EPC：
                String readerdata = (String)msg.obj;
                m_textepc.setText(readerdata);
                byte[] epc = reader.stringToBytes(readerdata);
                reader.Select((byte)0x01,16,(byte)((epc.length)*8),(byte)0,epc);
                reader.m_strPCEPC = readerdata;
            }
        }
    };
}
