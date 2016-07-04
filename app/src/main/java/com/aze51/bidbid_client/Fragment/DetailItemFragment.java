package com.aze51.bidbid_client.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.Network.Auction;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailItemFragment extends Fragment {
    View rootViewBasic;
    //ImageView backimage;
    TextView detail_price;
    TextView detail_time;
    TextView detail_title;
    Button detail_bid;
    TextView detail_bidPrice;
    String get_img;
    ImageView detail_img;
    List<Product> products;
    int position;
    String tmpMessage;
    NetworkService networkService;
    Auction auction;
    //static String user_id = "lkh034";
    Product tmpProduct;
    public DetailItemFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        initNetworkService();
        rootViewBasic = inflater.inflate(R.layout.detail_item_cardview,container,false);
        products = ApplicationController.getInstance().getProduct();
        position = ApplicationController.getPosition();
        initView();
        //detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        //detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        //detail_price.setText("120000");
        String tmpName = products.get(position).product_name;
        int tmpPrice = products.get(position).register_minprice;
        String tmpImg = products.get(position).product_img;
        final int tmpRegisterId = products.get(position).register_id; //
        /*getDetailContent(tmpRegisterId);
        String detailName = tmpProduct.product_name;
        String detailImg = tmpProduct.product_img;
        int detailPrice = tmpProduct.register_minprice;*/
        //String tmpPrice = (products.get(position).register_minprice);
        detail_title.setText(tmpName);
        detail_price.setText(Integer.toString(tmpPrice));
        //detail_bidPrice.setText(detail_bidPrice.getText());
        //detail_time.setText("3:45");
        Glide.with(this).load(tmpImg).into(detail_img);
       /* detail_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detail_bidPrice.setText(detail_bidPrice.getText());
                auction = new Auction();
                auction.deal_price = Integer.parseInt(detail_bidPrice.getText().toString());
                auction.register_id = tmpRegisterId;
                auction.user_id = ApplicationController.getUserId();
                ApplicationController.setAuction(auction);
                //postBidResult(auction);
            }
        });*/
        return rootViewBasic;
    }

    protected void initView(){
        detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        detail_title = (TextView)rootViewBasic.findViewById(R.id.detail_item_name);
        detail_img = (ImageView)rootViewBasic.findViewById(R.id.detail_ImageView);
        detail_bid = (Button)rootViewBasic.findViewById(R.id.bidbtn);
        detail_bidPrice = (TextView)rootViewBasic.findViewById(R.id.inputPrice);

    }
    public void postBidResult(Auction auction){
        Call<Auction> auctionCall = networkService.finishbid(auction);
        auctionCall.enqueue(new Callback<Auction>() {
            @Override
            public void onResponse(Call<Auction> call, Response<Auction> response) {
                if(response.isSuccessful()){
                    tmpMessage = "입찰 성공";
                    Toast.makeText(getContext(), tmpMessage, Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Auction> call, Throwable t) {

            }
        });
    } // BottomMenuFragment

    private void initNetworkService() {
        // TODO: 13. ApplicationConoller 객체를 이용하여 NetworkService 가져오기
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    public void getDetailContent(int id){
        Call<Product> callProduct = networkService.getContent(id);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    tmpProduct = new Product();
                    tmpProduct = response.body();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    } // DetailItemFragment
}
