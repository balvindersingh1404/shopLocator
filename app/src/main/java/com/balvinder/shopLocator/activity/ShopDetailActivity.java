package com.balvinder.shopLocator.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balvinder.shopLocator.util.ApiInterface;
import com.balvinder.shopLocator.util.MyApplication;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.balvinder.shopLocator.model.ShopDetails;
import com.balvinder.shopLocator.model.ShopDetailsResponse;
import com.balvinder.shopLocator.R;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDetailActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private String placeId;
    private static final int MY_PERMISSION_FOR_CALLING = 200;
    ShopDetails shopDetails;
    private LatLng currentLatLng;
    private LatLng destinationLatLng;
    private String phone;
    private String website;

    @BindView(R.id.shop_name) TextView shopName;
    @BindView(R.id.shop_address) TextView shopAddress;
    @BindView(R.id.shopImage) ImageView shopImage;
    @BindView(R.id.call_button) ImageButton callButton;
    @BindView(R.id.website_button) ImageButton websiteButton;
    @BindView(R.id.direction_button) ImageButton directionButton;
    @BindView(R.id.shop_rating)
    RatingBar shopRating;

    @BindView(R.id.shop_timing) TextView shopTimingTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ((MyApplication)getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            placeId = extras.getString("placeIdKey");
            currentLatLng = new LatLng(extras.getDouble("currentLat"),
                    extras.getDouble("currentLong"));
        }

        try{
            getDetails(placeId);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void getDetails(String placeId){
        ApiInterface apiService =
                retrofit.create(ApiInterface.class);
        String Api_Key = getResources().getString(R.string.API_KEY);
        Call<ShopDetailsResponse> call = apiService.getShopDetails(placeId,Api_Key);
        call.enqueue(new Callback<ShopDetailsResponse>() {

            @Override
            public void onResponse(Call<ShopDetailsResponse> call, Response<ShopDetailsResponse> response) {

                shopDetails=response.body().getResult();
                shopName.setText(shopDetails.getName());
                destinationLatLng = new LatLng(shopDetails.getGeometry().getLoc().getLat(),
                        shopDetails.getGeometry().getLoc().getLng());
                shopAddress.setText(shopDetails.getVicinity());
                try{
                    phone = shopDetails.getFormattedPhoneNumber();

                }catch (NullPointerException ex){
                    phone = "";
                }

                try{
                    website = shopDetails.getWebsite();
                }catch (NullPointerException ex){

                    website = "";
                }

                try{
                    shopRating.setRating(shopDetails.getRating().floatValue());
                    shopRating.setEnabled(false);
                }catch (NullPointerException ex){
                    shopRating.setRating(0);
                    shopRating.setEnabled(false);
                }
                if(shopDetails.getOpeningHours()!=null){
                    if(shopDetails.getOpeningHours().getWeekdayText()!=null){
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_WEEK);

                        switch (day) {
                            case Calendar.MONDAY:
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(0));
                                break;
                            case Calendar.TUESDAY:
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(1));
                                break;
                            case Calendar.WEDNESDAY:
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(2));
                                break;
                            case Calendar.THURSDAY:
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(3));
                                break;
                            case Calendar.FRIDAY :
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(4));
                                break;
                            case Calendar.SATURDAY :
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(5));
                                break;
                            case Calendar.SUNDAY :
                                shopTimingTextview.setText(shopDetails.getOpeningHours().getWeekdayText().get(6));
                                break;
                        }
                    }
                }else {
                    shopTimingTextview.setText("Timing Unknown");
                }
                Picasso.with(ShopDetailActivity.this).load(shopDetails.getIcon()).into(shopImage);
            }

            @Override
            public void onFailure(Call<ShopDetailsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.direction_button)
    public void showDirections(ImageButton imageButton){

        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("currentLat",currentLatLng.latitude);
        intent.putExtra("currentLong",currentLatLng.longitude);
        intent.putExtra("destinationLat",destinationLatLng.latitude);
        intent.putExtra("destinationLng",destinationLatLng.longitude);
        startActivity(intent);

    }

    @OnClick(R.id.call_button)
    public void callTheShop(ImageButton imageButton){
        if(phone == null){
            Toast.makeText(ShopDetailActivity.this,"Not having a valid Phone Number!!",Toast.LENGTH_LONG).show();
        }else{

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        ShopDetailActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSION_FOR_CALLING);
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phone));
            startActivity(callIntent);

        }

    }
    @OnClick(R.id.website_button)
    public void goToWebsite(ImageButton imageButton){
        if(website == null){
            Toast.makeText(ShopDetailActivity.this,"Not having a valid Website!!",Toast.LENGTH_LONG).show();
        }else{

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(browserIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_FOR_CALLING:
                callTheShop((ImageButton)findViewById(R.id.call_button));
        }
    }
}
