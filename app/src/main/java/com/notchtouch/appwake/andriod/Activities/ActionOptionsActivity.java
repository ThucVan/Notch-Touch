package com.notchtouch.appwake.andriod.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.notchtouch.appwake.andriod.R;
import com.notchtouch.appwake.andriod.Utils.Functions;
import com.notchtouch.appwake.andriod.databinding.ActivityActionOptionsBinding;

public class ActionOptionsActivity extends AppCompatActivity {

    ActivityActionOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.lightBackgroundStatusBarDesign(this);
        binding= ActivityActionOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}