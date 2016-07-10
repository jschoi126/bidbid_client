package com.aze51.bidbid_client.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;

import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

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
    ImageView detail_view;
    TextView detail_price;
    TextView detail_time, detail_prices, detail_menu, detail_phone, detail_closed, detail_park, detail_address, detail_year;
    TextView detail_title, detail1, detail2, detail3, detail4;
    TextView detail_time_hour, detail_time_min, detail_time_sec, detailCount, detailFavorite, detail_deal, detail_people;
    TextView detail_stime, detail_ftime;
    Button detail_bid;
    TextView detail_bidPrice;
    String get_img;
    ImageView detail_img;
    List<Product> products, tmp_Product;
    int registerID;
    int dealPrice;
    int dealCount;
    int product_minprice;
    int product_maxprice;
    String stimes, ftimes;
    CountDownTimer timer;

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MyTag", "ondetach");
        if(timer!=null) {
            timer.cancel();
        }
    }

    int favoriteCheck, bidCheck;
    long rHour, rMin, rSec;
    int tmpRegisterId, tmpPage, tmpPage2;
    Favorite f;
    //facebookshare
    ImageView shareImage, favoriteImage, bidSucceed, background;
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
            int pageState =((MainActivity) ctx).getFromState();
            if (pageState== 6) { //from search on clicked  detail
                products = ApplicationController.getInstance().getProducts(5);
                ApplicationController.getInstance().setRegisterId(products.get(pos).register_id);
            } else if (pageState== 2) { //from favorite detail
                products = ApplicationController.getInstance().getProducts(3);
                ApplicationController.getInstance().setRegisterId(products.get(pos).register_id);
            } else if (pageState == 3) { //from mypage detail
                products = ApplicationController.getInstance().getProducts(4);
                ApplicationController.getInstance().setRegisterId(products.get(pos).register_id);
            } else {//from view pager.
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
                        .build();
                        ShareApi.share(content,null);

                ShareApi.share(content,null);*/


                String temp;
                if(tmpProduct!=null&&tmpProduct.product_img!=null&&tmpProduct.product_img.length()!=0){
                    temp = tmpProduct.product_img;
                }
                else{
                    temp = "https://s3.ap-northeast-2.amazonaws.com/bidbid/FoodPic_2.jpg";
                }

                /*ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("Shared from nearbyme application")
                        .setContentDescription("This is a wonderful place")
                        .setContentUrl(Uri.parse("http://www.villathena.com/images/nearby/thumbs/le-bus-bleu-private-tours.jpg"))
                        .setImageUrl(Uri.parse("http://www.villathena.com/images/nearby/thumbs/le-bus-bleu-private-tours.jpg"))
                        .build();
                shareDialog.show(content);*/

                /*
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "books.book")
                        .putString("og:title", "Bid Bid")
                        .putString("og:description", "경매형 오픈 마케팅 플랫폼")
                        .putString("og:image",temp)
                        .putString("og:site:name","https://www.facebook.com/bidbid7979/?skip_nax_wizard=true")
                        .putString("fb:app_id",String.valueOf(R.string.facebook_app_id))
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
                */
                //ShareDialog.show(getActivity(),content);
                //ShareApi.share(content,null);
                /*
                if(ApplicationController.getInstance().GetIsFacebook()==true) {
                  //  Context ctx = ApplicationController.getInstance().getMainActivityContext();
                   // Intent intent = new Intent(ctx, SharingActivity.class);
                   // startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "공유하려면 페이스북 로그인 해 주세요.", Toast.LENGTH_SHORT).show();
                }

                */

                String urlToShare = "https://www.facebook.com/bidbid7979/?skip_nax_wizard=true";
                Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setType("text/plain");
// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
                //intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
             intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"bidbid");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "bidbid" +
                    " 짱");
/*// See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }
// As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }*/
                //intent.putExtra(Intent.EXTRA_TEXT, "비드비드 사랑해요");
                //intent.setType("image/png");
                //try {
                //    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), temp);
                //}
                /*
                Glide.with(ctx)
                        .load(temp)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Uri bmpUri = Uri.parse(resource);
                                //final Intent emailIntent1 = new Intent(     android.content.Intent.ACTION_SEND);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                intent.setType("image/png");

                                //holder.mImageView.setImageBitmap(resource);
                            }
                        });*/

                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_STREAM, String.valueOf(uri));
                intent.putExtra(Intent.EXTRA_STREAM, temp);
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            }
        });

        favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoriteCheck == 1){
                    favoriteImage.setImageResource(R.mipmap.favorite);
                    CheckOutFavorite();
                    favoriteCheck = 0;
                    favoriteFlag = false;
                }
                else{
                    favoriteImage.setImageResource(R.mipmap.favorite_click);
                    CheckInFavorite();
                    favoriteCheck = 1;
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
        //detail = (TextView)rootViewBasic.findViewById(R.id.detail_bid);
        detailCount = (TextView)rootViewBasic.findViewById(R.id.detail_current_count);
        detail_deal = (TextView)rootViewBasic.findViewById(R.id.detail_bid);
        shareImage = (ImageView)rootViewBasic.findViewById(R.id.detail_share_image);
        favoriteImage = (ImageView)rootViewBasic.findViewById(R.id.detail_favorite_image);
        detail_ftime = (TextView)rootViewBasic.findViewById(R.id.detail_ftime);
        detail_stime = (TextView)rootViewBasic.findViewById(R.id.detail_stime);
        detail_people = (TextView)rootViewBasic.findViewById(R.id.detail_people_su);
        detail1 = (TextView)rootViewBasic.findViewById(R.id.detail_connect);
        detail2 = (TextView)rootViewBasic.findViewById(R.id.detail_checking);
        detail3 = (TextView)rootViewBasic.findViewById(R.id.detail_people_su_connect);
        detail4 = (TextView)rootViewBasic.findViewById(R.id.detail_text);
        detail_time = (TextView)rootViewBasic.findViewById(R.id.time);
        detail_prices = (TextView)rootViewBasic.findViewById(R.id.store_price);
        detail_menu = (TextView)rootViewBasic.findViewById(R.id.memus);
        detail_phone = (TextView)rootViewBasic.findViewById(R.id.store_phone);
        detail_closed = (TextView)rootViewBasic.findViewById(R.id.closed_day);
        detail_park = (TextView)rootViewBasic.findViewById(R.id.park);
        detail_address = (TextView)rootViewBasic.findViewById(R.id.address);
        detail_view = (ImageView)rootViewBasic.findViewById(R.id.detail_inform_image);


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
                    tmp_Product = response.body();//detail list 에 한 개의 아이템 만 있음
                    tmpProduct = tmp_Product.get(0);//detail의 첫번째가 원하는 아이템

                    ApplicationController.getInstance().SetDetailProduct(tmpProduct);

                    registerID = tmpProduct.register_id;
                    detail_title.setText(tmpProduct.product_name);
                    Glide.with(getContext())
                            .load(tmpProduct.product_img)
                            .into(detail_img);

                    //detail_img.setImageResource(R.drawable.foodppp);
                    Glide.with(getContext()).load(tmpProduct.product_img).into(detail_img);
                    detail_price.setText(Integer.toString(tmpProduct.register_minprice));
                    tmp_time = tmpProduct.rtime;
                    dealPrice = tmpProduct.deal_price;
                    dealCount = tmpProduct.deal_count;
                    product_minprice = tmpProduct.register_minprice;
                    product_maxprice = tmpProduct.register_maxprice;

                    TextView maxprice = (TextView)rootViewBasic.findViewById(R.id.detail_max_price);
                    TextView minprice = (TextView)rootViewBasic.findViewById(R.id.detail_min_price);
                    maxprice.setText(String.valueOf(product_maxprice) + "원");
                    minprice.setText(String.valueOf(product_minprice) + "원");

                    detail_deal.setText(Integer.toString(dealPrice));
                    detailCount.setText(Integer.toString(dealCount));

                    //TextView deal_count = (TextView)rootViewBasic.findViewById(R.id.detail_current_count);
                    //deal_count.setText(Integer.toString(tmpProduct.deal_count));
                    stimes = changeString(tmpProduct.register_stime);
                    ftimes = changeString(tmpProduct.register_ftime);

                    detail_stime.setText(stimes);
                    detail_ftime.setText(ftimes);
                    detail_people.setText(Integer.toString(tmpProduct.register_numpeople));
                   // detail_stime.setText(t);
                    favoriteCheck = tmpProduct.favorite;

                    if(favoriteCheck == 1){
                        favoriteImage.setImageResource(R.mipmap.favorite_click);
                    }
                    else{
                        favoriteImage.setImageResource(R.mipmap.favorite);
                    }

                    Glide.with(getContext()).load(tmpProduct.store_img).error(R.drawable.food).into(detail_view);
                    detail_time.setText(tmpProduct.store_time);
                    detail_prices.setText(tmpProduct.store_price);
                    detail_phone.setText(tmpProduct.store_number);
                    detail_closed.setText(tmpProduct.store_year);
                    detail_menu.setText(tmpProduct.store_menu);
                    detail_address.setText(tmpProduct.store_address);
                    detail_park.setText(tmpProduct.store_parking);
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
        timer = new CountDownTimer(tmp_time* 1000, 1000) {

            //int counter = 3 * 60;
            @Override
            public void onTick(long millisUntilFinished) {
                tmpPage = ((MainActivity) ctx).getPageState();
                tmpPage2 = ((MainActivity) ctx).getFromState();
                /*if(tmpPage == tmpPage2){
                    cancel();
                }*/
                tmp_time= millisUntilFinished/1000;
                rSec--;
                if(rSec == -1){
                    rMin--;
                    rSec = 59;
                }if(rMin == -1){
                    rMin = 59;
                    rHour--;
                }
                detail_time_sec.setText(Long.toString(rSec));
                detail_time_hour.setText(Long.toString(rHour));
                detail_time_min.setText(Long.toString(rMin));
            }
            @Override
            public void onFinish() {
                if(rHour == 0) {
                    Toast.makeText(getContext(), "경매 마감", Toast.LENGTH_SHORT).show();
                    detail_people.setText("마감된 경매");
                    detail_stime.setText("마감된 경매");
                    detail_ftime.setText("");
                    detail_price.setText("마감된 경매");
                    detail_deal.setText("마감된 경매");
                    detail1.setText("");  detail2.setText("");  detail3.setText("");  detail4.setText("");
                }
                clearTime();
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
    private void clearTime() {
        rHour = 0;
        rMin = 0;
        rSec = 0;
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        ApplicationController.getInstance().getDataFromServer();
        super.onViewStateRestored(savedInstanceState);
    }
    private String changeString(String str1){
        StringTokenizer tokenizer = new StringTokenizer(str1);
        String dates = tokenizer.nextToken("T");
        String tmp = tokenizer.nextToken(".");
        String times = tmp.substring(1, 6);
        String sumdates = dates +" "+ times;
        return sumdates;
    }


}
