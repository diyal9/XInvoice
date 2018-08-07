package com.diyal.xinvoice.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.diyal.xinvoice.R;

public class MyDialog extends Dialog {
	private Context mContext;
	private String title;
	private String msg;

	private TextView tv_title;

	private TextView tv_msg;

	private Button btn_sure;

	private ImageButton btn_close_button;

	public MyDialog(Context context, String title, String msg) {
		super(context);
		this.mContext = context;
		this.title = title;
		this.msg = msg;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setCancelable(true);
		setCanceledOnTouchOutside(false);

		setContentView(R.layout.myialog);

		tv_title = (TextView) findViewById(R.id.dialogTitle);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		btn_close_button = (ImageButton) findViewById(R.id.btn_close_button);

		btn_close_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	@Override
	public void show() {
		super.show();
	}

}
