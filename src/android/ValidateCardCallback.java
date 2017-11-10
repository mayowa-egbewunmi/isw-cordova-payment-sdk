
import com.interswitchng.sdk.payment.IswCallback;
import com.interswitchng.sdk.payment.model.AuthorizeCardResponse;

import org.apache.cordova.CallbackContext;

/**
 * Created by Babajide.Apata on 11/10/2017.
 */

public class ValidateCardCallback extends IswCallback<AuthorizeCardResponse> {
    private transient CallbackContext callbackContext;

    public ValidateCardCallback(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    @Override
    public void onError(Exception error) {
        callbackContext.error(error.getMessage());
    }

    @Override
    public void onSuccess(AuthorizeCardResponse authorizeCardResponse) {
        PluginUtils.getPluginResult(callbackContext, authorizeCardResponse);
    }


}
