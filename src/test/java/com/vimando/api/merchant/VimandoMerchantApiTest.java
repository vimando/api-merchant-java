package com.vimando.api.merchant;

/**
 * User: Michael Hässig
 * Email: michael.haessig (at) gmail (dot) com
 * Date: 21.07.17
 * Time: 16:56
 */
public class VimandoMerchantApiTest {

	private VimandoMerchantApi getMerchantApi(){
		/* create VimandoMerchantApi instance */
		VimandoMerchantApi vimandoMerchantApi = new VimandoMerchantApi();
		/* set auth information */
		vimandoMerchantApi.authenticate("https://shop.vimando.com","apikey","username","password");
		/* debug mode for request/response logging */
		vimandoMerchantApi.setDebugMode(true);

		return vimandoMerchantApi;
	}

}
