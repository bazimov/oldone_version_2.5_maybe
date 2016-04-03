package com.ilmnuri.com.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.ilmnuri.com.ExceptionViewActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler implements
        Thread.UncaughtExceptionHandler {
    private final Activity myContext;

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        Intent intent = new Intent(myContext, ExceptionViewActivity.class);
        String LINE_SEPARATOR = "\n";
        intent.putExtra("error", "************ CAUSE OF ERROR ************\n\n" + stackTrace.toString() + "\n************ DEVICE INFORMATION ***********\n" + "Brand: " + Build.BRAND + LINE_SEPARATOR + "Device: " + Build.DEVICE + LINE_SEPARATOR + "Model: " + Build.MODEL + LINE_SEPARATOR + "Id: " + Build.ID + LINE_SEPARATOR + "Product: " + Build.PRODUCT + LINE_SEPARATOR + "\n************ FIRMWARE ************\n" + LINE_SEPARATOR + "Release: " + Build.VERSION.RELEASE + LINE_SEPARATOR + "Incremental: " + Build.VERSION.INCREMENTAL + LINE_SEPARATOR);
        myContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}