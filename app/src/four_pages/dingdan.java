package four_pages;

import com.hzgzsoft.logisticsmanage.BasePager;
import com.hzgzsoft.logisticsmanage.R;


import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;



public class dingdan extends BasePager {

	public dingdan(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		System.out.println("下单页初始化啦...");

		// 要给帧布局填充布局对象
		TextView view = new TextView(mActivity);
		view.setText("订单页");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);

		
		flContent.addView(view);




	}

}
