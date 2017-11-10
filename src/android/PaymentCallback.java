
import com.interswitchng.sdk.util.RandomString;
import com.interswitchng.sdk.util.StringUtils;
import com.interswitchng.sdk.payment.android.util.Util;
import com.interswitchng.sdk.payment.model.PurchaseResponse;
import com.interswitchng.sdk.payment.IswCallback;
import org.apache.cordova.CallbackContext;

public class PaymentCallback extends IswCallback<PurchaseResponse> {
    private transient CallbackContext callbackContext;

    public PaymentCallback(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    @Override
    public void onError(Exception error) {
        callbackContext.error(error.getMessage());
    }

    @Override
    public void onSuccess(PurchaseResponse response) {
        PluginUtils.getPluginResult(callbackContext, response);
    }

}
