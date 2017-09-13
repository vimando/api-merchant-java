package com.vimando.api.merchant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vimando.api.merchant.model.*;
import com.vimando.api.merchant.service.ApiException;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;


/**
 * User: Michael Hässig
 * Email: michael.haessig (at) gmail (dot) com
 * Date: 21.07.17
 * Time: 16:56
 */
public class VimandoMerchantApiTest {

    private VimandoMerchantApi getMerchantApi() {
        /* create VimandoMerchantApi instance */
        VimandoMerchantApi vimandoMerchantApi = new VimandoMerchantApi();
		/* set auth information */
        vimandoMerchantApi.authenticate("https://shop.vimando.com", "apikey", "username", "password");
		/* debug mode for request/response logging */
        vimandoMerchantApi.setDebugMode(true);

        return vimandoMerchantApi;
    }

    @Test
    public void importOrderTest() throws Exception {

        VimandoMerchantApi vimandoMerchantApi = getMerchantApi();

        try {
            /* your order id */
            String merchantOrderId = "1000";

            /* customer information */
            Customer customer = new Customer();
            customer.setCountry(Customer.CountryEnum.DE);
            customer.setEmail("customer@gmail.com");
            customer.setSalutation(Customer.SalutationEnum._1);
            customer.setFirstname("Customer");
            customer.setLastname("Customer");
            customer.setStreet("Customerstreet");
            customer.setStreetNumber("3");
            customer.setStreetAdditional("");
            customer.setZip("80000");
            customer.setCity("Berlin");

            /* order position */
            Position position = new Position();
            position.setSku("WP10004939-0-387");
            position.setCount(1);

            /* format parameter */
            FormatDimension formatDimension = new FormatDimension();
            formatDimension.setWidth(260d);
            formatDimension.setHeight(390d);

            position.addParametersItem(formatDimension);

            /* order object */
            Order order = new Order();
            order.setId(merchantOrderId);
            order.setCountry("DE");
            order.setLocale("de");
            order.setCurrency("EUR");
            order.setCustomer(customer);

            /* order position list */
            order.addPositionsItem(position);

            /* call importOrder API */
            OrderImport orderImport = vimandoMerchantApi.importOrder(order);

            /* print returned data */
            System.out.print(orderImport);
        } catch (ApiException apiException) {
            /* get error data */
            ErrorResponse errorResponse = vimandoMerchantApi.getErrorResponse(apiException);
            System.out.println(errorResponse.getCode());
        }

    }

    @Test
    public void listOrderImportsTest() throws Exception {

        VimandoMerchantApi vimandoMerchantApi = getMerchantApi();

        try {

            OrderImportList orderImportList = vimandoMerchantApi.listOrderImports(1, 50);

            System.out.println("OrderImport count : " + orderImportList.getCount());

            for (OrderImport orderImport : orderImportList.getOrderImports()) {
				/* print orderImport */
                System.out.println(orderImport);

				/* check state of orderImport */
                if (VimandoMerchantApi.Codes.OrderImport.IMPORTED.equals(orderImport.getStateCode())) {
                    System.out.println("Import successfull " + orderImport.getIdentifier());
                }
            }
        } catch (ApiException apiException) {

            ErrorResponse errorResponse = vimandoMerchantApi.getErrorResponse(apiException);
            System.out.println(errorResponse.getCode());
        }

    }

    @Test
    public void orderJsonTest() throws Exception {

        VimandoMerchantApi vimandoMerchantApi = getMerchantApi();

        ObjectMapper mapper = new ObjectMapper();

        InputStream resourceAsStream = VimandoMerchantApiTest.class.getClassLoader().getResourceAsStream("order.json");
        Order order = mapper.readValue(resourceAsStream, Order.class);

        OrderImport orderImport = vimandoMerchantApi.importOrder(order);

        System.out.println("Imported Order : ");

        System.out.println(orderImport);
    }

    @Test
    public void uploadTest() throws Exception {

        VimandoMerchantApi vimandoMerchantApi = getMerchantApi();

        try {

            UploadedFile uploadedFile = vimandoMerchantApi.upload(new File("./mountain.jpg.jpg"));

            System.out.println("Uploaded File : ");

            System.out.println(uploadedFile);

            String fileId = uploadedFile.getId();

            System.out.println("File ID: " + fileId);

        }catch(ApiException apiException){

            ErrorResponse errorResponse = vimandoMerchantApi.getErrorResponse(apiException);
            System.out.println(errorResponse);
            System.out.println(errorResponse.getCode());
            System.out.println(errorResponse.getMessage());

        }

    }

}
