package com.app.cashfree.Interface;

import com.app.cashfree.Model.AddBenDetail;
import com.app.cashfree.Model.AddBenModel;
import com.app.cashfree.Model.AddCartModel;
import com.app.cashfree.Model.AddPinModel;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.CheckBalanceModel;
import com.app.cashfree.Model.CheckBalanceModelNew;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.Model.GetBenModel;
import com.app.cashfree.Model.SignModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.Model.TranferModel;
import com.app.cashfree.Model.TransModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("v1/authorize")
    Call<AutorizationModel> AutorizationApi();

    @GET("v1/getBalance")
    Call<CheckBalanceModel> ChcekBalance();

    @POST("v1/requestTransfer")
    Call<TranferModel> TranferBalance(@Body JsonObject transfer);


    @POST("v1/addBeneficiary")
    Call<JsonObject> addBen(@Body JsonObject location);

    @GET("getBeneficiary/{input}")
    Call<GetBenModel> getBen(@Path("input") String input);


    @FormUrlEncoded
    @POST("userRegister")
    Call<SignModel> signUp(@Field("firstName") String firstName,
                           @Field("lastName") String lastName,
                           @Field("password") String password,
                           @Field("phone") String phone,
                           @Field("email") String email);

    @FormUrlEncoded
    @POST("userLogin")
    Call<SignModel> Login(@Field("email") String email,
                          @Field("password") String password,
                          @Field("deviceId") String deviceId,
                          @Field("deviceType") String deviceType);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<GenricModel> forgot(@Field("email") String email);

    @FormUrlEncoded
    @POST("recoverPassword")
    Call<GenricModel> recover_password(@Field("email") String email,
                                       @Field("otp") String otp,
                                       @Field("password") String password,
                                       @Field("confirmPassword") String confirm_password);


    @FormUrlEncoded
    @POST("addWalletAmount")
    Call<GenricModel> addWallet(@Field("userId") String userId,
                                @Field("wallet_amount") String wallet_amount,
                                @Field("walletId") String walletId);

    @FormUrlEncoded
    @POST("save_credit_card_info")
    Call<AddCartModel> saveCard(@Field("userId") String userId,
                                @Field("cardHolderName") String cardHolderName,
                                @Field("cardNo") String cardNo,
                                @Field("cardCvvNo") String cardCvvNo,
                                @Field("cardExpiryDate") String cardExpiryDate,
                                @Field("ifscCode") String ifscCode,
                                @Field("account_no") String account_no);

    @FormUrlEncoded
    @POST("getWalletMoney")
    Call<CheckBalanceModelNew> getWallet(@Field("userId") String userId);


    @GET("getSignature")
    Call<SignatureModel> signature();

    @FormUrlEncoded
    @POST("transactionForOtherAccount")
    Call<GenricModel> tranfer_money(@Field("loginId") String loginId,
                                    @Field("senderWallletId") String senderWallletId,
                                    @Field("receiverWalletId") String receiverWalletId,
                                    @Field("amount") String amount,
                                    @Field("transferType") String transferType,
                                    @Field("recevierName") String recevierName);




    @FormUrlEncoded
    @POST("userLogout")
    Call<GenricModel> logout(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("transactionAccountHistory")
    Call<TransModel> transaction_history(@Field("wallletId") String wallletId);


    @FormUrlEncoded
    @POST("updBeneficiaryId")
    Call<AddBenDetail> AddBeneficiary(@Field("beneficiaryId") String beneficiaryId,
                                      @Field("userId") String userId,
                                      @Field("beneficiaryName") String beneficiaryName);



    @FormUrlEncoded
    @POST("userVarify")
    Call<GenricModel> userVarify(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("genratePinNumber")
    Call<AddPinModel> genratePinNumber(@Field("userId") String userId,
                                       @Field("pinNumber") String pinNumber);




}
