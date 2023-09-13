package com.eg.customviewgroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eg.customviewgroup.databinding.ActivityMainBinding;

/*
 main_line:
 1.reqs/Qs => flow & apply API(input)=> output;
 2.copy&deubug details ignore details;
 3.specific eg => concept // active recall
 4.sample code clx as Unit: clsX => clsXTmp

Q: viewBinding XML => objViewX
A:
 1. viewBinding{ enabled = true}  // XML => clsX
 2. ActivityMainBinding bindingX = ActivityMainBinding.inflate(getLayoutInflater());
    a.XML & ctx => bindingX(objViewX)
    b.LayoutInflater.from(ctx)
    c.(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 3. R.layout.resId ?
    a.bindingX.getRoot();
 4. buttonX ?
    a.bindingX.buttonIdX

* */

/*

ActivityMainBinding
		.inflate(getLayoutInflater()); => activityMainBinding

activityMainBinding
		.getRoot()  // setContentView()
		.tst1  // buttonX

activity_main.XML => activityMainBinding[obj]
* * */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(activityMainBinding.getRoot());  // object => id

		activityMainBinding.tst2.setOnClickListener(this);  // getView
		activityMainBinding.tst3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId(); //getId

		if (id == R.id.tst2)
			startActivity(new Intent(this, MainActivity2.class));
		else if (id == R.id.tst3)
			startActivity(new Intent(this, MainActivity3.class));
	}
}
