package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.Auction;
import com.aze51.bidbid_client.Network.Favorite;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.SharingActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailItemFragment extends Fragment {
    View rootViewBasic;
    //ImageView backimage;
    TextView detail_price;
    TextView detail_time;
    TextView detail_title;
    TextView detail_time_hour, detail_time_min, detail_time_sec;
    Button detail_bid;
    TextView detail_bidPrice;
    String get_img;
    ImageView detail_img;
    List<Product> products, tmp_Product;
    int registerID, dealPrice;
    long rHour, rMin, rSec;
    int tmpRegisterId;
    Favorite f;
    //facebookshare
    ImageView shareImage, favoriteImage;
    Bitmap image;
    boolean favoriteFlag = false;
    int position, pos;
    long tmp_time;
    String tmpMessage;
    NetworkService networkService;
    Auction auction;
    Context ctx;
    //static String user_id = "lkh034";
    Product tmpProduct;
    private TextView textViewKeyTimer;
    private boolean isKeyExpired = true;

    public DetailItemFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
            initNetworkService();
            pos = ApplicationController.getInstance().getPos();
            position = ApplicationController.getInstance().getPosition();
            rootViewBasic = inflater.inflate(R.layout.detail_item_cardview, container, false);
            ctx = ApplicationController.getInstance().getMainActivityContext();
            if (((MainActivity) ctx).getFromState() == 6) { //from search on clicked  detail
                products = ApplicationController.getInstance().getProducts(5);
                ApplicationController.getInstance().setRegisterId(products.get(pos).register_id);
            } else if (((MainActivity) ctx).getFromState() == 2) { //from favorite detail
                products = ApplicationController.getInstance().getProducts(3);
                ApplicationController.getInstance().setRegisterId(products.get(position).register_id);
            } else if (((MainActivity) ctx).getFromState() == 3) { //from mypage detail
                products = ApplicationController.getInstance().getProducts(4);
                ApplicationController.getInstance().setRegisterId(products.get(position).register_id);
            } else {
                products = ApplicationController.getInstance().getProducts(position);
                ApplicationController.getInstance().setRegisterId(products.get(pos).register_id);
            }

            initView();
            f = new Favorite();
            registerID = ApplicationController.getInstance().getRegisterId();
            f.user_id = ApplicationController.getInstance().getUserId();
            f.register_id = registerID;
            getDetailContent(registerID, f.user_id);
       // image = BitmapFactory.decodeResource(getResources(),R.drawable.food);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "share clicked");
                /*
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "fitness.course")
                        .putString("og:title", "Sample Course")
                        .putString("og:description", "This is a sample course.")
                        .putInt("fitness:duration:value", 100)
                        .putString("fitness:duration:units", "s")
                        .putInt("fitness:distance:value", 12)
                        .putString("fitness:distance:units", "km")
                        .putInt("fitness:speed:value", 5)
                        .putString("fitness:speed:units", "m/s")
                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("fitness.runs")
                        .putObject("fitness:course", object)
                        .build();
                ShareOpenGraphContent content2 = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("fitness:course")
                        .setAction(action)
                        .build();*/
                /*
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "books.book")
                        .putString("og:title", "Bid Bid")
                        .putString("og:description", "경매형 마케팅 플랫폼")
                        .putString("books:isbn", "0-553-57340-3")
                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("books.reads")
                        .putObject("book", object)
                        .build();
                // Create the content
                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("book")
                        .setAction(action)
                        .build();
                //ShareApi.share(content, null);
                //Context ctx = ApplicationController.getInstance().getMainActivityContext()
                ShareDialog.show(getActivity(), content);*/
                Context ctx = ApplicationController.getInstance().getMainActivityContext();
                Intent intent = new Intent(ctx, SharingActivity.class);
                startActivity(intent);
            }
        });
        favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteFlag == true){
                    favoriteImage.setImageResource(R.mipmap.favorite);
                    CheckOutFavorite();
                    favoriteFlag = false;
                }
                else{
                    favoriteImage.setImageResource(R.mipmap.favorite_click);
                    CheckInFavorite();
                    favoriteFlag = true;
                }
            }
        });
        return rootViewBasic;
    }

    protected void initView(){
        detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        //detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        detail_title = (TextView)rootViewBasic.findViewById(R.id.detail_item_name);
        detail_img = (ImageView)rootViewBasic.findViewById(R.id.detail_ImageView);
        detail_time_hour = (TextView)rootViewBasic.findViewById(R.id.detail_time_hour);
        detail_time_min = (TextView)rootViewBasic.findViewById(R.id.detail_time_min);
        detail_time_sec = (TextView)rootViewBasic.findViewById(R.id.detail_time_sec);

        shareImage = (ImageView)rootViewBasic.findViewById(R.id.detail_share_image);
        favoriteImage = (ImageView)rootViewBasic.findViewById(R.id.detail_favorite_image);
    }
    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    public void getDetailContent(int id, String user_id){
        Call<List<Product>> callProduct = networkService.getContent(id, user_id);
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    tmp_Product = response.body();
                    tmpProduct = tmp_Product.get(0);
                    registerID = tmpProduct.register_id;
                    detail_title.setText(tmpProduct.product_name);
                    Glide.with(getContext()).load(tmpProduct.product_img).into(detail_img);
                    detail_price.setText(Integer.toString(tmpProduct.register_minprice));
                    tmp_time = tmpProduct.rtime;
                    dealPrice = tmpProduct.deal_price;
                    clearTime();
                    getTime();
                    startRemainingTimeCount();
                    ApplicationController.getInstance().sets(1);

                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    } // DetailItemFragment
    private void CheckInFavorite(){

        Call<Favorite> pushFavoriteCall = networkService.registerFavorite(f);
        pushFavoriteCall.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Response<Favorite> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(getContext(), "즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void CheckOutFavorite() {
        Call<Void> checkoutCall = networkService.deleteFavorite(f.user_id, f.register_id);
        checkoutCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Toast.makeText(getContext(),"즐겨찾기에 해제되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void startRemainingTimeCount() {
        isKeyExpired = false;
        CountDownTimer timer = new CountDownTimer(tmp_time* 1000, 1000) {

            //int counter = 3 * 60;
            @Override
            public void onTick(long millisUntilFinished) {
                tmp_time= millisUntilFinished/1000;
                rSec--;
                if(rSec == -1){
                    rMin--;
                    rSec = 59;
                }
                else if(rMin == -1){
                    rMin = 59;
                    rHour--;
                }
                detail_time_sec.setText(Long.toString(rSec));
                detail_time_hour.setText(Long.toString(rHour));
                detail_time_min.setText(Long.toString(rMin));
            }

            @Override
            public void onFinish() {
                if(rHour == 0)
                    Toast.makeText(getContext(),"경매 마감", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }
    private void getTime(){
        rHour = (tmp_time/3600);
        double tmp2 = ((tmp_time/3600.0)-rHour)*60.0;
        rMin = ((int)tmp2);
        double tmp3 = (tmp2-rMin)*60;
        rSec = ((int)tmp3);
        dealPrice = tmpProduct.deal_price;
    }
    private void clearTime(){
        rHour = 0;
        rMin = 0;
        rSec = 0;
    }
}
