import android.app.Activity;
import android.content.Context;

import com.interswitchng.sdk.model.RequestOptions;
import com.interswitchng.sdk.payment.IswCallback;
import com.interswitchng.sdk.payment.android.inapp.Pay;
import com.interswitchng.sdk.payment.android.inapp.PayWithCard;
import com.interswitchng.sdk.payment.android.inapp.PayWithToken;
import com.interswitchng.sdk.payment.android.inapp.PayWithWallet;
import com.interswitchng.sdk.payment.android.inapp.ValidateCard;
import com.interswitchng.sdk.payment.model.AuthorizeCardResponse;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PayWithUI extends CordovaPlugin{
    private String clientId;
    private String clientSecret;
    private static RequestOptions options;
    private Activity activity;
    private Context context;

    public PayWithUI(Activity activity, String clientId, String clientSecret ){
        this.activity = activity;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        activity =  cordova.getActivity();
    }

    public void pay(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    options = RequestOptions.builder().setClientId(clientId).setClientSecret(clientSecret).build();
                    JSONObject params = args.getJSONObject(0);
                    String customerId = params.getString("customerId");
                    String currency = params.getString("currency");
                    String description = params.getString("description");
                    String amount = params.getString("amount");
                    String transactionRef = params.getString("transactionRef");
                    final PaymentCallback paymentCallback = new PaymentCallback(callbackContext);
                    Pay pay = new Pay(activity, customerId, description, amount, currency,transactionRef, options,paymentCallback);
                    pay.start();
                } catch (Exception ex) {
                    callbackContext.error(ex.toString());
                }
            }
        });
    }
    public void payWithCard(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    options = RequestOptions.builder().setClientId(clientId).setClientSecret(clientSecret).build();
                    JSONObject params = args.getJSONObject(0);
                    String customerId = params.getString("customerId");
                    String currency = params.getString("currency");
                    String description = params.getString("description");
                    String amount = params.getString("amount");
                    boolean isRecurrent = false;
                    if (params.has("isRecurrent")){
                        isRecurrent = params.getBoolean("isRecurrent");
                    }
                    final PaymentCallback paymentCallback = new PaymentCallback(callbackContext);
                    PayWithCard pay;
                    if (isRecurrent){
                        pay = new PayWithCard(activity, customerId, description, amount, currency, options, paymentCallback, true);
                    }
                    else {
                        pay = new PayWithCard(activity, customerId, description, amount, currency, options, paymentCallback);
                    }
                    pay.start();
                } catch (Exception ex) {
                    callbackContext.error(ex.toString());
                }
            }
        });
    }
    public void payWithWallet(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    options = RequestOptions.builder().setClientId(clientId).setClientSecret(clientSecret).build();
                    JSONObject params = args.getJSONObject(0);
                    String customerId = params.getString("customerId");
                    String currency = params.getString("currency");
                    String description = params.getString("description");
                    String amount = params.getString("amount");
                    final PaymentCallback paymentCallback = new PaymentCallback(callbackContext);
                    PayWithWallet payWithWallet = new PayWithWallet(activity, customerId, description, amount, currency, options,paymentCallback);
                    payWithWallet.start();
                } catch (Exception ex) {
                    callbackContext.error(ex.toString());
                }
            }
        });
    }
    public void payWithToken(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    options = RequestOptions.builder().setClientId(clientId).setClientSecret(clientSecret).build();
                    JSONObject params = args.getJSONObject(0);
                    String currency = params.getString("currency");
                    String token = params.getString("pan");
                    String amount = params.getString("amount");
                    String cardType = params.getString("cardtype");
                    String panLast4Digits = params.getString("panLast4Digits");
                    String expiryDate = params.getString("expiryDate");
                    String customerId = params.getString("customerId");
                    String description = params.getString("description");
                    final PaymentCallback paymentCallback = new PaymentCallback(callbackContext);
                    PayWithToken payWithToken = new PayWithToken(activity, customerId, amount, token, expiryDate, currency, cardType, panLast4Digits, description, options,paymentCallback);
                    payWithToken.start();
                } catch (Exception ex) {
                    callbackContext.error(ex.toString());
                }
            }
        });
    }
    public void validatePaymentCard(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    JSONObject params = args.getJSONObject(0);
                    String customerId = params.getString("customerId");
                    options = RequestOptions.builder().setClientId(clientId).setClientSecret(clientSecret).build();
                    boolean isRecurrent = false;
                    if (params.has("isRecurrent")){
                        isRecurrent = params.getBoolean("isRecurrent");
                    }
                    ValidateCard validateCard;
                    final ValidateCardCallback validateCardCallback = new ValidateCardCallback(callbackContext);
                    if (isRecurrent){
                        validateCard = new ValidateCard(activity, customerId, options, validateCardCallback,true);
                    }else {
                        validateCard = new ValidateCard(activity, customerId, options, validateCardCallback);
                    }
                    validateCard.start();
                } catch (Exception error) {
                    callbackContext.error(error.toString());
                }
            }
        });
    }
}