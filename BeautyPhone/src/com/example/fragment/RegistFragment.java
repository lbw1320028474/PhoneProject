package com.example.fragment;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;

import com.example.activity.UserLoginUtil;
import com.example.activity.UserLoginUtil.loginListener;
import com.example.beautyphone.R;
import com.example.beautyphone.UrlManager;
import com.example.beautyphone.network.HttpUtil;
import com.example.beautyphone.network.Bean.IdentIsCanRegistRequestBean;
import com.example.beautyphone.network.Bean.IdentIsCanRegistResponseBean;
import com.example.beautyphone.network.Bean.RegistRequestBean;
import com.example.beautyphone.network.Bean.RegistResponseBean;
import com.example.myDialog.LoadingDialog;
import com.example.myDialog.TipDialog;
import com.example.myDialog.TipDialog.tipDialotButtonListener;
import com.example.util.IdentyPhoneIsNumberUtil;
import com.example.util.MD5Util;
import com.example.util.NetWorkUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistFragment extends Fragment implements OnClickListener{
	private EditText phoneNumber = null;
	private EditText identyCode = null;
	private EditText password = null;
	private Button registButton = null;
	private TextView getIdenty = null;
	private String number = "";
	private String passWord = "";
	private String identy = "";
	private TipDialog tipDialog = null;
	private LoadingDialog loadingDialog = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.regist_fragment_layout, container, false);
		initView(view);
		initData();
		return view;
	}
	private void initData() {
		// TODO Auto-generated method stub
		loadingDialog = new LoadingDialog(getActivity());
		tipDialog = new TipDialog(getActivity());
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		phoneNumber = (EditText)view.findViewById(R.id.loginactivity_account_edittext);
		identyCode = (EditText)view.findViewById(R.id.loginactivity_password_edittext);
		password = (EditText)view.findViewById(R.id.loginactivity_idendity_edittext);
		getIdenty = (TextView)view.findViewById(R.id.regist_getidentcode);
		registButton = (Button)view.findViewById(R.id.loginactivity_regist_button);
		getIdenty.setOnClickListener(this);
		registButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regist_getidentcode:
			number = phoneNumber.getText().toString();
			if(IdentyPhoneIsNumberUtil.isMobile(number)){
				//Toast.makeText(getActivity(), "��ʼ��֤", 0).show();
				identIsCanRegist(number);
			}else{
				phoneNumber.setError("��������ȷ���ֻ�����");
				//Toast.makeText(getActivity(), "��������ȷ���ֻ�����", 0).show();
			}
			break;
		case R.id.loginactivity_regist_button:
			identy = identyCode.getText().toString();
			if(!identy.isEmpty() && identy.length() == 6){
				String ps = password.getText().toString();
				if(ps.isEmpty()){
					password.setError("����������");
					return;
				}else if(ps.length() < 6){
					password.setError("����6λ��");
					return;
				}
				toRegist();
			}else{
				identyCode.setError("��֤���ʽ����ȷ");
				//Toast.makeText(getActivity(), "��֤���ʽ����ȷ", 0).show();
			}
			break;
		}
	}
	private void toRegist() {
		// TODO Auto-generated method stub
		if(!NetWorkUtil.checkNetworkAvailable(getActivity())){
			Toast.makeText(getActivity(), "��������", 0).show();
			return;
		}
		loadingDialog.showDialog();
		BmobSMS.verifySmsCode(getActivity(), number, identy, new VerifySMSCodeListener() {
			@Override
			public void done(BmobException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){//������֤������֤�ɹ�
					Log.i("bmob", "��֤ͨ��");
					beginToRegist();
					//Toast.makeText(getActivity(), "��֤ͨ��", 0).show();
				}else{
					loadingDialog.closeDialog();
					identyCode.setError("��֤�����");
					//Toast.makeText(getActivity(), "��֤ʧ�ܣ�code ="+arg0.getErrorCode()+",msg = "+arg0.getLocalizedMessage(), 0).show();
					Log.i("bmob", "��֤ʧ�ܣ�code ="+arg0.getErrorCode()+",msg = "+arg0.getLocalizedMessage());
				}
			}


		});
	}
	private void beginToRegist() {
		// TODO Auto-generated method stub
		if(!NetWorkUtil.checkNetworkAvailable(getActivity())){
			loadingDialog.closeDialog();
			Toast.makeText(getActivity(), "��������", 0).show();
			return;
		}
		passWord = password.getText().toString();
		if(passWord.isEmpty()){
			loadingDialog.closeDialog();
			password.setError("����������");
		}else if(passWord.length() >= 6){
			RegistRequestBean requestBean = new RegistRequestBean();
			requestBean.setUserNumber(number);
			requestBean.setUserPassword(MD5Util.md5(passWord));
			HttpUtil.getInstance().okHttpDoPost(UrlManager.REGIST_URL, requestBean, registhandler);
		}else{
			loadingDialog.closeDialog();
			password.setError("����6λ����");
		}
	}

	private Handler registhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			loadingDialog.closeDialog();
			if(msg != null && msg.getData() != null && msg.getData().getByteArray("byte") != null){
				byte[] registbyte = msg.getData().getByteArray("byte");
				RegistResponseBean responseBean = TLVCodeUtil.decode(registbyte, RegistResponseBean.class);
				if(responseBean != null && responseBean.getResultCode().equals("1")){
					//Toast.makeText(getActivity(), responseBean.get, duration)
					tipDialog.showDialog("ע��ɹ�,ֱ�ӵ�¼��", new tipDialotButtonListener() {

						@Override
						public void click(boolean isOk) {
							// TODO Auto-generated method stub
							if(isOk){
								if(!NetWorkUtil.checkNetworkAvailable(getActivity())){
									Toast.makeText(getActivity(), "��������", 0).show();
									return;
								}
								loadingDialog.showDialog();
								UserLoginUtil.getInstance(getActivity()).login(number, MD5Util.md5(passWord), identy, new loginListener() {

									@Override
									public void loginState(int i, String message) {
										// TODO Auto-generated method stub
										loadingDialog.closeDialog();
										if(i == 1){
											Toast.makeText(getActivity(), "��¼�ɹ�", 0).show();
											Timer timer = new Timer();
											timer.schedule(new TimerTask() {

												@Override
												public void run() {
													// TODO Auto-generated method stub
													getActivity().finish();
												}
											}, 1500);
										}else{
											Toast.makeText(getActivity(), message, 0).show();
										}
									}
								});
							}else{
								tipDialog.closeDialog();
							}
						}
					});
					//Toast.makeText(getActivity(), responseBean.getRegistResult(), Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), responseBean.getRegistResult(), Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getActivity(), "ע��ʧ��,����ϵ�ͷ�", 0);
			}
		};
	};

	private void identIsCanRegist(String number) {
		// TODO Auto-generated method stub
		if(!NetWorkUtil.checkNetworkAvailable(getActivity())){
			Toast.makeText(getActivity(), "����δ����,��������", 0).show();
			return;
		}
		IdentIsCanRegistRequestBean identIsCanRegistRequestBean = new IdentIsCanRegistRequestBean();
		identIsCanRegistRequestBean.setNumber(number);
		loadingDialog.showDialog();
		HttpUtil.getInstance().okHttpDoPost(UrlManager.IDENT_IS_CANREGIST, identIsCanRegistRequestBean, identHandler);
	}

	private Handler identHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg != null && msg.getData() != null && msg.getData().getByteArray("byte") != null){
				Bundle bundle = msg.getData();
				byte[] b = bundle.getByteArray("byte");
				IdentIsCanRegistResponseBean responseBean = TLVCodeUtil.decode(b, IdentIsCanRegistResponseBean.class);
				if(responseBean != null && !responseBean.getIsCanRegist().isEmpty()){
					if(responseBean.getIsCanRegist().equals("1")){
						beginGetIdenCode(number);
						//Toast.makeText(getActivity(), responseBean.getIsCanRegist() + "", Toast.LENGTH_LONG).show();
					}else if(responseBean.getIsCanRegist().equals("0")){
						loadingDialog.closeDialog();
						phoneNumber.setError("���ֻ�����ע��");
					}else{
						loadingDialog.closeDialog();
						Toast.makeText(getActivity(), "��֤���ȡʧ��", 0).show();
					}
					//Toast.makeText(getActivity(), responseBean.getIsCanRegist(), 0).show();
				}else if(responseBean != null){
					loadingDialog.closeDialog();
					Toast.makeText(getActivity(), responseBean.getIsCanRegist(), 0).show();
				}else{
					loadingDialog.closeDialog();
					Toast.makeText(getActivity(), "����ʧ��", 0).show();
				}
			}else{
				loadingDialog.closeDialog();
				Toast.makeText(getActivity(), "����ʧ��", 0).show();
			}
		}


	};
	private void beginGetIdenCode(String number) {
		// TODO Auto-generated method stub
		BmobSMS.requestSMSCode(getActivity(), number, "����绰ע����֤��",new RequestSMSCodeListener() {
			@Override
			public void done(Integer arg0, BmobException arg1) {
				// TODO Auto-generated method stub
				loadingDialog.closeDialog();
				if(arg1==null){//��֤�뷢�ͳɹ�
					Toast.makeText(getActivity(), "��֤�뷢�ͳɹ�,��ע�������֤��", 0).show();
				}else{
					Toast.makeText(getActivity(), "��֤�뷢��ʧ��", 0).show();
				}
			}
		});
	};
}
