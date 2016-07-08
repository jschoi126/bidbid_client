package com.aze51.bidbid_client;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aze51.bidbid_client.Network.Product;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

public class SharingActivity extends AppCompatActivity{

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sharing);
            /*
            String temp;

            Product tmpProduct = ApplicationController.getInstance().GetProduct();
            if(tmpProduct!=null&&tmpProduct.product_img!=null&&tmpProduct.product_img.length()!=0){
                temp = tmpProduct.product_img;
            }
            else{
                temp = "https://s3.ap-northeast-2.amazonaws.com/bidbid/FoodPic_2.jpg";
            }
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

            //ShareApi.share(content, null);
            //Context ctx = ApplicationController.getInstance().getMainActivityContext()
            ShareDialog.show(this,content);
            */

            sharePhotoToFacebook();
            /*
            FacebookSdk.sdkInitialize(getApplicationContext());

            callbackManager = CallbackManager.Factory.create();

            List<String> permissionNeeds = Arrays.asList("publish_actions");

            //this loginManager helps you eliminate adding a LoginButton to your UI
            loginManager = LoginManager.getInstance();
            loginManager.logInWithPublishPermissions(this, permissionNeeds);
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    sharePhotoToFacebook();
                }
                @Override
                public void onCancel()
                {
                    System.out.println("onCancel");
                }
                @Override
                public void onError(FacebookException exception)
                {
                    System.out.println("onError");
                }
            });*/
        }

    private void sharePhotoToFacebook(){
       /* ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("Shared from nearbyme application")
                .setContentDescription("This is a wonderful place")
                .setContentUrl(Uri.parse("http://www.villathena.com/images/nearby/thumbs/le-bus-bleu-private-tours.jpg"))
                .setImageUrl(Uri.parse("http://www.villathena.com/images/nearby/thumbs/le-bus-bleu-private-tours.jpg"))
                .build();
        ShareDialog.show(linkcontent);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        ShareApi.share(content,null);*/

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.food);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("비드비드 짱짱!")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:title", "Bid Bid")
                .putString("og:description", "경매형 마케팅 플랫폼")
                .putString("books:isbn", "0-553-57340-3")
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .build();
         //Create the content
        //ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
         //       .setPreviewPropertyName("book")
         //       .setAction(action)
         //       .build();
        content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareApi.share(content, null);
    }
}
