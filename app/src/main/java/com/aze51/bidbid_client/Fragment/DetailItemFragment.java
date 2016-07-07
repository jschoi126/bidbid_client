package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
    int registerID;
    int tmpRegisterId;
    Favorite f;
    //facebookshare
    ImageView shareImage, shareImage2;
    Bitmap image;
    boolean favoriteFlag = false;
    int position, pos;
    int tmp_time;
    String tmpMessage;
    NetworkService networkService;
    Auction auction;
    Context ctx;
    //static String user_id = "lkh034";
    Product tmpProduct;
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
        /*String tmpName = products.get(position).product_name;
        int tmpPrice = products.get(position).register_minprice;
        String tmpImg = products.get(position).product_img;
        tmpRegisterId = products.get(position).register_id; //

        detail_title.setText(tmpName);
        detail_price.setText(Integer.toString(tmpPrice));
        Glide.with(this).load(tmpImg).into(detail_img);*/
            getDetailContent(registerID);
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
        shareImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteFlag == true){
                    CheckOutFavorite();
                    favoriteFlag = false;
                }
                else{
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
        //detail_bid = (Button)rootViewBasic.findViewById(R.id.bidbtn);
        //detail_bidPrice = (TextView)rootViewBasic.findViewById(R.id.inputPrice);
        detail_time_hour = (TextView)rootViewBasic.findViewById(R.id.detail_time_hour);
        detail_time_min = (TextView)rootViewBasic.findViewById(R.id.detail_time_min);
        detail_time_sec = (TextView)rootViewBasic.findViewById(R.id.detail_time_sec);

        shareImage = (ImageView)rootViewBasic.findViewById(R.id.detail_share_image);
        shareImage2 = (ImageView)rootViewBasic.findViewById(R.id.detail_favorite_image);
    }
    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    public void getDetailContent(int id){
        Call<List<Product>> callProduct = networkService.getContent(id);
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    tmp_Product = response.body();
                    tmpProduct = tmp_Product.get(0);
                    registerID = tmpProduct.register_id;
                    detail_title.setText(tmpProduct.product_name);
                    Glide
                            .with(getContext())
                            .load(tmpProduct.product_img)
                            .into(detail_img);
                    detail_price.setText(Integer.toString(tmpProduct.register_minprice));
                    //tmp_time = tmpProduct.rtime;
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
                    Toast.makeText(getContext(),"즐겨찾기에 등록되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void CheckOutFavorite(){
        //String userid = ApplicationController.getInstance().getUserId();
        Call<Void> checkoutCall = networkService.deleteFavorite(f.user_id, f.register_id);
        checkoutCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(getContext(),"즐겨찾기에 해제되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void setDetail(List<Product>detail ){
        tmpProduct = detail.get(0);
        registerID = tmpProduct.register_id;
        detail_title.setText(tmpProduct.product_name);
        Glide
                .with(getContext())
                .load(tmpProduct.product_img)
                .into(detail_img);

        detail_price.setText(tmpProduct.register_minprice);
        //tmp_time = tmpProduct.rtime;
    }
}
