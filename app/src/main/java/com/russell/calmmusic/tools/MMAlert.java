package com.russell.calmmusic.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.russell.calmmusic.R;

import java.util.List;

public final class MMAlert {

	public interface OnAlertSelectId {
		void onClick(int whichButton);
	}

	private MMAlert() {

	}

	public static AlertDialog showAlert(final Context context, final String msg, final String title) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final int msgId, final int titleId) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final int msgId, final int titleId, final DialogInterface.OnClickListener l) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.app_ok, l);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String msg, final String title, final DialogInterface.OnClickListener l) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.app_ok, l);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final int msgId, final int titleId, final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.app_ok, lOk);
		builder.setNegativeButton(R.string.app_cancel, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final int msg, final int title, final int yes, final int no, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(yes, lOk);
		builder.setNegativeButton(no, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String msg, final String title, final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.app_ok, lOk);
		builder.setNegativeButton(R.string.app_cancel, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String msg, final String title, final String yes, final String no, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(yes, lOk);
		builder.setNegativeButton(no, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String title, final View view, final DialogInterface.OnClickListener lOk) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(R.string.app_ok, lOk);
		// builder.setCancelable(true);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String title, final View view, final String ok, final String cancel, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(ok, lOk);
		builder.setNegativeButton(cancel, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String title, final String ok, final View view, final DialogInterface.OnClickListener lOk) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(ok, lOk);
		// builder.setCancelable(true);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String title, final String msg, final View view, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setView(view);
		builder.setPositiveButton(R.string.app_ok, lOk);
		builder.setNegativeButton(R.string.app_cancel, lCancel);
		// builder.setCancelable(true);
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (lCancel != null) {
					lCancel.onClick(dialog, 0);
				}
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static AlertDialog showAlert(final Context context, final String title, final View view, final OnCancelListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		// builder.setCancelable(true);
		builder.setOnCancelListener(lCancel);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}


}

class AlertAdapter extends BaseAdapter {
	// private static final String TAG = "AlertAdapter";
	public static final int TYPE_BUTTON = 0;
	public static final int TYPE_TITLE = 1;
	public static final int TYPE_EXIT = 2;
	public static final int TYPE_CANCEL = 3;
	private List<String> items;
	private int[] types;
	// private boolean isSpecial = false;
	private boolean isTitle = false;
	// private boolean isExit = false;
	private Context context;

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean isEnabled(int position) {
		if (position == 0 && isTitle) {
			return false;
		} else {
			return super.isEnabled(position);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String textString = (String) getItem(position);
		ViewHolder holder;
		int type = types[position];
		if (convertView == null || ((ViewHolder) convertView.getTag()).type != type) {
			holder = new ViewHolder();
			if (type == TYPE_CANCEL) {
				convertView = View.inflate(context, R.layout.alert_dialog_menu_list_layout_cancel, null);
			} else if (type == TYPE_BUTTON) {
				convertView = View.inflate(context, R.layout.alert_dialog_menu_list_layout, null);
			} else if (type == TYPE_TITLE) {
				convertView = View.inflate(context, R.layout.alert_dialog_menu_list_layout_title, null);
			} else if (type == TYPE_EXIT) {
				convertView = View.inflate(context, R.layout.alert_dialog_menu_list_layout_special, null);
			}

			// holder.view = (LinearLayout) convertView.findViewById(R.id.popup_layout);
			holder.text = (TextView) convertView.findViewById(R.id.popup_text);
			holder.type = type;

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(textString);
		return convertView;
	}

	static class ViewHolder {
		// LinearLayout view;
		TextView text;
		int type;
	}
}
