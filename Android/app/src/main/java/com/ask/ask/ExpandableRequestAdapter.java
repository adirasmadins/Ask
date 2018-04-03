package com.ask.ask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexander on 3/20/2018.
 */
public class ExpandableRequestAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeaders;
    private HashMap<String, List<String>> listHashMap;

    public ExpandableRequestAdapter(Context context, List<String> listHeaders, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listHeaders = listHeaders;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listHashMap.get(listHeaders.get(groupPosition)) == null) {
            return 0; //this Request has no Offers
        } else {
            return listHashMap.get(listHeaders.get(groupPosition)).size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View view, ViewGroup parent) {
        String headerStr = (String) getGroup(position);
        String[] headerArr = headerStr.split("#");
        String numOffersForCurrentRequest = headerArr[1];
        String requestId = headerArr[2];
        String status = headerArr[3];
        int temp = status.indexOf(":");
        int statusInt = Integer.parseInt(status.substring(temp + 1).trim());
        int color = Integer.parseInt(headerArr[4]);
        int imageIcon = Integer.parseInt(headerArr[5]);



        if (statusInt == LocalData.REQUEST_WITH_PENDING_OFFERS) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_request_header_pending, null);
            }
            view.setBackgroundColor(view.getResources().getColor(color));
            ImageView imageViewHeader = (ImageView) view.findViewById(R.id.imageViewItemImage);
            imageViewHeader.setImageResource(imageIcon);

            TextView textViewNumOffersForRequest = (TextView) view.findViewById(R.id.textViewNumOffersForRequest);
            textViewNumOffersForRequest.setText(numOffersForCurrentRequest);

            TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
            textViewStatus.setText(status);

            TextView textViewRequestId = (TextView) view.findViewById(R.id.textViewRequestId);
            textViewRequestId.setText(requestId);

        } else if (statusInt == LocalData.REQUEST_WITH_OFFER_SELECTED) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_request_header_accepted, null);
            }
            view.setBackgroundColor(view.getResources().getColor(color));
            ImageView imageViewHeader = (ImageView) view.findViewById(R.id.imageViewItemImage);
            imageViewHeader.setImageResource(imageIcon);

            TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
            textViewStatus.setText(status);

            TextView textViewProviderName = (TextView) view.findViewById(R.id.textViewProviderName);
            textViewProviderName.setText("Provider Name"); //TODO: need to get provider name and put here

            Button buttonMessage = (Button) view.findViewById(R.id.buttonMessage);
            buttonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: switch to messaging screen



                    Toast.makeText(v.getContext(), "Go to Message Screen.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_request_item_offers, null);
        }

        String elementStr = (String) getChild(groupPosition, childPosition);
        String[] elementsArr = elementStr.split("#");
        String name = elementsArr[0];
        String price = elementsArr[1];
        int color = Integer.parseInt(elementsArr[2]);
        final String request_id = elementsArr[3];
        final String provider_id = elementsArr[4];

        view.setBackgroundColor(view.getResources().getColor(color));

        TextView textViewProviderName = (TextView) view.findViewById(R.id.textViewProviderName);
        textViewProviderName.setText(name);

        TextView textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
        textViewPrice.setText(price);

        Button buttonAcceptOffer = (Button) view.findViewById(R.id.buttonAcceptOffer);
        buttonAcceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOffer(v, request_id, provider_id);
                //TODO: go to messaging page

                Toast.makeText(v.getContext(), "Offer Accepted.", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonDenyOffer = (Button) view.findViewById(R.id.buttonDenyOffer);
        buttonDenyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: send update to database


                //TODO: send OFFER_DENIED to database with the offer_id


                Toast.makeText(v.getContext(), "Offer Denied.", Toast.LENGTH_SHORT).show();
            }
        });

//        SwipeMenuListView swipeMenuOfferControl = (SwipeMenuListView) view.findViewById(R.id.swipeMenuOfferControl);
//
//        ArrayList<String> listView = new ArrayList<>();
//
//        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1);
//        swipeMenuOfferControl.setAdapter(adapter);
//
//        final View v = view;
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(v.getContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(v.getContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//
//        listView.setMenuCreator(creator);






        return view;
    }

    private void sendOffer(View view, String request_id, String provider_id) {
        final String url = "https://ask-capa.herokuapp.com/api/offers/accept/" + request_id;

        final View v = view;

        POSTData postData = new POSTData();
        postData.postAcceptOffer(url, request_id, provider_id, "", v.getContext(), new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Toast.makeText(v.getContext(), "Offer Accepted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(v.getContext(), "Error Accepting Offer.", Toast.LENGTH_SHORT).show();
                // handle failure on accepting offer
            }

        });

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}