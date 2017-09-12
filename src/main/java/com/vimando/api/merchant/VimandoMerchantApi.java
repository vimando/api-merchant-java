package com.vimando.api.merchant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimando.api.merchant.api.MerchantApi;
import com.vimando.api.merchant.model.*;
import com.vimando.api.merchant.service.ApiClient;
import com.vimando.api.merchant.service.ApiException;
import com.vimando.api.merchant.service.auth.ApiKeyAuth;
import com.vimando.api.merchant.service.auth.HttpBasicAuth;

import java.io.File;

/**
 * User: Michael Hässig
 * Email: michael.haessig (at) gmail (dot) com
 * Date: 21.07.17
 * Time: 16:56
 */
public class VimandoMerchantApi {

	private final MerchantApi merchantApi = new MerchantApi();

	public void authenticate(String url,String apiKey,String username,String password){
		// merchantApi & uploadApi have the same client instance
		ApiClient apiClient = merchantApi.getApiClient();

		apiClient.setBasePath(url);

		HttpBasicAuth basicAuth = (HttpBasicAuth) apiClient.getAuthentication("basicAuth");
		basicAuth.setUsername(username);
		basicAuth.setPassword(password);

		ApiKeyAuth apiKeyAuth = (ApiKeyAuth) apiClient.getAuthentication("MERCHANT-KEY");
		apiKeyAuth.setApiKey(apiKey);
	}

	public void setDebugMode(boolean debugMode){
		// merchantApi & uploadApi have the same client instance
		merchantApi.getApiClient().setDebugging(debugMode);
	}

	/**
	 * getOrderImport
	 *
	 * @param id id (required)
	 * @return OrderImport
	 * @throws ApiException if fails to make API call
	 */
	public OrderImport getOrderImport(String id) throws ApiException {

		OrderImport orderImport = merchantApi.getOrderImport(id);

		return orderImport;
	}

	/**
	 * listOrderImports
	 *
	 * @return OrderImportList
	 * @throws ApiException if fails to make API call
	 */
	public OrderImportList listOrderImports() throws ApiException {

		OrderImportList orderImportList = merchantApi.listOrderImports(1,20);

		return orderImportList;
	}

	/**
	 * listOrderImports
	 *
	 * @param page Results page you want to retrieve (1..N) (optional)
	 * @param size Number of records per page. (optional)
	 * @return OrderImportList
	 * @throws ApiException if fails to make API call
	 */
	public OrderImportList listOrderImports(Integer page,Integer size) throws ApiException {

		OrderImportList orderImportList = merchantApi.listOrderImports(page,size);

		return orderImportList;
	}

	/**
	 * importOrder
	 *
	 * @param order order (required)
	 * @return OrderImport
	 * @throws ApiException if fails to make API call
	 */
	public OrderImport importOrder(Order order) throws ApiException {

		OrderImport orderImport = merchantApi.importOrder(order);

		return orderImport;
	}

	/**
	 * getOrderState
	 *
	 * @param id id (required)
	 * @return OrderState
	 * @throws ApiException if fails to make API call
	 */
	public OrderState getOrderState(String id) throws ApiException {

		OrderState orderState = merchantApi.getOrderState(id);

		return orderState;
	}

	/**
	 * upload
	 *
	 * @param file file (required)
	 * @return UploadedFile
	 * @throws ApiException if fails to make API call
	 */
	public UploadedFile upload(File file) throws ApiException {

		UploadedFile uploadedFile = merchantApi.upload(file);

		return uploadedFile;
	}

	/**
	 * getErrorResponse
	 *
	 * @param apiException ApiException (required)
	 * @return ErrorResponse
	 */
	public ErrorResponse getErrorResponse(ApiException apiException) {

		try {
			ObjectMapper objectMapper = merchantApi.getApiClient().getObjectMapper();

			ErrorResponse errorResponse = objectMapper.readValue(apiException.getResponseBody(), ErrorResponse.class);

			return errorResponse;
		}catch(Exception e){
			return new ErrorResponse().code(Codes.ErrorResponse.ERROR).message(e.getMessage());
		}

	}

	/* ------------------ Return Codes Constants ------------------ */

	public static class Codes {
		public static class ErrorResponse {
            public static final String ERROR                 = "api-error";
            public static final String RESOURCE_NOT_FOUND    = "api-resource-not-found";
            public static final String KEY_INVALID           = "api-key-invalid";
            public static final String KEY_NOT_FOUND         = "api-key-not-found";
            public static final String OPERATION_NOT_ALLOWED = "api-operation-not-allowed";
		}

		public static class OrderImport {
            public static final String IMPORTING = "1";
            public static final String IMPORTED  = "2";
            public static final String DELETED   = "3";
            public static final String FAILED    = "4";
		}

		public static class OrderState {
            public static final String ORDER_RECIVED                  = "1";
            public static final String WAITING_FOR_PAYMENT_ADVANCE    = "2";
            public static final String WAITING_FOR_PAYMENT            = "3";
            public static final String WAITING_FOR_PAYMENT_REMINDER   = "4";
            public static final String WAITING_FOR_PAYMENT_DUNNING_1  = "5";
            public static final String WAITING_FOR_PAYMENT_DUNNING_2  = "6";
            public static final String WAITING_FOR_PAYMENT_OUTSOURCED = "7";
            public static final String ORDER_COMPLETED                = "8";
            public static final String ORDER_INPRODUCTION             = "9";
		}

		public static class OrderDelivery {
            public static final String ORDER_RECIVED          = "1";
            public static final String ORDER_INPRODUCTION     = "2";
            public static final String ORDER_READYFORSHIPPING = "3";
            public static final String ORDER_SHIPPED          = "4";
		}
	}

}

