package com.math;

import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class FragmentPlay extends Fragment {
	private TextView tv_soA;
	private TextView tv_soB;
	private TextView tv_ketqua;
	private TextView tv_score;
	private TextView tv_hightscore;
	private ImageButton bt1;
	private ImageButton bt2;
	private Boolean ok = true;
	private Boolean ok2 = false;
	private int diem = 0;
	private SeekBar mSeeBar;
	private int time = 1500;

	private Boolean math() {
		Random random = new Random();
		int a = random.nextInt(10);
		int b = random.nextInt(10);
		int c = a + b - 1 + random.nextInt(2);
		tv_soA.setText(String.valueOf(a));
		tv_soB.setText(String.valueOf(b));
		tv_ketqua.setText(String.valueOf(c));
		if (c == a + b)
			return true;
		else
			return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View view = inflater.inflate(R.layout.play, null);
		tv_soA = (TextView) view.findViewById(R.id.textView_soA);
		tv_soB = (TextView) view.findViewById(R.id.textView_soB);
		tv_ketqua = (TextView) view.findViewById(R.id.textView_ketqua);
		bt1 = (ImageButton) view.findViewById(R.id.button_dung);
		bt2 = (ImageButton) view.findViewById(R.id.button_sai);
		tv_score = (TextView) view.findViewById(R.id.textView_diem);
		tv_hightscore = (TextView) view.findViewById(R.id.textView_hightScore);
		mSeeBar = (SeekBar) view.findViewById(R.id.seekBar);
		mSeeBar.setEnabled(false);
		ok2 = math();
		SharedPreferences pre = this.getActivity().getSharedPreferences(
				"hight_score", Context.MODE_PRIVATE);
		String hight_score = pre.getString("hight_score", "0");
		tv_hightscore.setText(hight_score);
		final CountDownTimer timer = new CountDownTimer(time, 1) {
			Boolean check = true;
			@Override
			public void onTick(final long millisUntilFinished) {
				if (ok) {
					mSeeBar.setProgress((int) ((time - millisUntilFinished) / (time / 100)));
				}
				else check = false;
				bt1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!ok2) {
							ok = false;
							check = true;
							onFinish();
						} else {
							diem++;
							tv_score.setText(String.valueOf(diem));
							ok2 = math();
							check = false;
							onFinish();
							start();
						}
					}
				});
				bt2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ok2) {
							ok = false;
							check = true;
							onFinish();
						} else {
							diem++;
							tv_score.setText(String.valueOf(diem));
							ok2 = math();
							check = false;
							onFinish();
							start();
						}
					}
				});
			}

			@Override
			public void onFinish() {
				if (mSeeBar.getProgress() == 99)
					ok = false;
				if(!check) ok = true;
				if (!ok) {
					bt1.setEnabled(false);
					bt2.setEnabled(false);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Game Over @@");
					builder.setMessage("Your Score: "
							+ tv_score.getText().toString());
					builder.setNegativeButton("Exit",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									getActivity().finish();
								}
							});
					builder.setNeutralButton("Again",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									FragmentManager fm = getActivity()
											.getSupportFragmentManager();
									FragmentTransaction ft = fm
											.beginTransaction();
									FragmentPlay fragment = new FragmentPlay();
									ft.replace(R.id.container, fragment);
									ft.commit();
								}
							});
					AlertDialog dialog = builder.create();
					dialog.setCancelable(false);
					dialog.setIcon(R.drawable.face_troll2);
					dialog.show();
				}
			}
		}.start();

		return view;
	}

	@Override
	public void onPause() {
		if (Integer.parseInt(tv_score.getText().toString()) > Integer
				.parseInt(tv_hightscore.getText().toString())) {
			SharedPreferences pre = this.getActivity().getSharedPreferences(
					"hight_score", Context.MODE_PRIVATE);
			SharedPreferences.Editor edit = pre.edit();
			String hight_score = tv_score.getText().toString();
			edit.putString("hight_score", hight_score);
			edit.commit();
		}
		super.onPause();
	}
}
