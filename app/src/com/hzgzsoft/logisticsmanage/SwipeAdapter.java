package com.hzgzsoft.logisticsmanage;

/**
 * 作者    楼雄伟
 * 时间    16/2/1 上午12:13
 * 文件    LogisticsManage
 * 描述
 */

import java.util.List;

import JavaBeen.CustomerAddressResult.AddressData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;

public class SwipeAdapter extends ArrayAdapter<AddressData>
{
    private LayoutInflater mInflater ;
    private List<AddressData> objects ;
    private SwipeListView mSwipeListView ;
    private Context context;
    private  String phone;

    public SwipeAdapter(String phone,Context context, int textViewResourceId,List<AddressData> objects, SwipeListView mSwipeListView)
    {
        super(context, textViewResourceId, objects);
        this.phone=phone;
        this.objects = objects ;
        this.mSwipeListView = mSwipeListView ;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null ;
        if(convertView == null)
        {
//			convertView = mInflater.inflate(R.layout.package_row, parent, false);
            convertView=View.inflate(context,R.layout.list_item,null);

            //设置后台按钮的宽度
            android.view.ViewGroup.LayoutParams BtnPara = convertView.findViewById(R.id.bt_select).getLayoutParams();
            BtnPara.width = convertView.getResources().getDisplayMetrics().widthPixels*2/9;
            convertView.findViewById(R.id.bt_select).setLayoutParams(BtnPara);
            convertView.findViewById(R.id.bt_edit).setLayoutParams(BtnPara);
            convertView.findViewById(R.id.bt_delete).setLayoutParams(BtnPara);

            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mPostNumber = (TextView) convertView.findViewById(R.id.tv_postnumber);
            holder.mAddress = (TextView) convertView.findViewById(R.id.tv_address);

            holder.mSelect = (Button) convertView.findViewById(R.id.bt_select);
            holder.mEdit = (Button) convertView.findViewById(R.id.bt_edit);
            holder.mDelete = (Button) convertView.findViewById(R.id.bt_delete);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {//*******************************************************

                System.out.println("ssssssssssssssssssssssssssssssssssssssss");
                //关闭动画
                mSwipeListView.closeAnimate(position);
                
                mSwipeListView.dismiss(position);

            }
        });
        


        //真正开始赋值，让其显示出来
        AddressData item = getItem(position);
        holder.mName.setText(item.Name);
        holder.mPostNumber.setText(item.Postcode);
        holder.mAddress.setText(item.Province+item.Area+item.Town+item.Village);

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mSwipeListView.closeAnimate(position);
               mSwipeListView.edit(position,phone,getItem(position).Name,getItem(position).Province,getItem(position).Area,getItem(position).County,getItem(position)
.Town,getItem(position).Village ,getItem(position).Postcode);
            }
        });
        holder.mSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSwipeListView.closeAnimate(position);
			String address=	getItem(position).Province+getItem(position).Area+getItem(position)
	            		   .Town+getItem(position).Village;
	              // mSwipeListView.select(position,phone,getItem(position).Name,address,getItem(position).Postcode);
			mSwipeListView.select(position, phone, getItem(position).Name, address, getItem(position).Postcode);
			}
		});

        return convertView;
    }


   static class ViewHolder
    {
        TextView mName,mPostNumber,mAddress ;
        Button mEdit,mDelete,mSelect ;
    }
}

