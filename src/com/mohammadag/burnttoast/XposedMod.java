package com.mohammadag.burnttoast;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XResources;
import android.view.Gravity;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class XposedMod implements IXposedHookZygoteInit {

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		XC_LayoutInflated hook = new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				LinearLayout layout = (LinearLayout) liparam.view;
				Context context = layout.getContext();
				TextView view = (TextView) liparam.view.findViewById(android.R.id.message);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;

				view.setLayoutParams(params);

				PackageManager pm = context.getPackageManager();

				ImageView imageView = new ImageView(context);
				imageView.setMaxHeight(view.getHeight() + 128);
				imageView.setMaxWidth(view.getHeight() + 128);
				imageView.setAdjustViewBounds(true);
				imageView.setImageDrawable(pm.getApplicationIcon(context.getPackageName()));
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params1.gravity = Gravity.CENTER;
				params1.rightMargin = 10;
				imageView.setLayoutParams(params1);

				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.addView(imageView, 0);
			}
		};

		XResources.hookSystemWideLayout("android", "layout", "transient_notification", hook);
		try {
			XResources.hookSystemWideLayout("android", "layout", "tw_transient_notification", hook);
		} catch (Resources.NotFoundException e) {

		} catch (Throwable t) {

		}
	}

}
