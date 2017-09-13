import com.vimando.api.merchant.VimandoMerchantApi;
import com.vimando.api.merchant.model.ErrorResponse;
import com.vimando.api.merchant.model.OrderImport;
import com.vimando.api.merchant.model.OrderImportList;
import com.vimando.api.merchant.service.ApiException;

/**
 * User: Michael Hässig
 * Email: michael.haessig (at) gmail (dot) com
 * Date: 12.09.17
 * Time: 13:00
 */
public class Client {

    public static void main(String args[]){
       /* create VimandoMerchantApi instance */
        VimandoMerchantApi vimandoMerchantApi = new VimandoMerchantApi();
        /* set auth information */
        vimandoMerchantApi.authenticate("https://shop.vimando.com","apikey","username","password");
        /* output request/response to stdout */
        vimandoMerchantApi.setDebugMode(true);

        try {
            /* call listOrderImports API */
            OrderImportList orderImportList = vimandoMerchantApi.listOrderImports();
            /* print total count */
            System.out.println("OrderImport count : " + orderImportList.getCount());
            /* iterate over results */
            for (OrderImport orderImport : orderImportList.getOrderImports()) {
            /* print orderImport */
                System.out.println(orderImport);
            }
        } catch (ApiException apiException) {
            /* get error data */
            ErrorResponse errorResponse = vimandoMerchantApi.getErrorResponse(apiException);
            System.out.println(errorResponse.getCode());
        }
    }
}
