package com.pureproduce.ecommerce_oil.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.exception.OrderException;

import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.Order;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.OrderRepository;
import com.pureproduce.ecommerce_oil.response.ApiResponse;
// import com.pureproduce.ecommerce_oil.repository.OrderRepository;
// import com.pureproduce.ecommerce_oil.response.ApiResponse;
import com.pureproduce.ecommerce_oil.response.PaymentLinkResponse;
import com.pureproduce.ecommerce_oil.service.OrderService;
import com.pureproduce.ecommerce_oil.service.UserService;
import com.pureproduce.ecommerce_oil.user.domain.OrderStatus;
import com.pureproduce.ecommerce_oil.user.domain.PaymentStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    

    @Autowired
    private OrderRepository orderRepository;

    public PaymentController(OrderService orderService, UserService userService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }   




    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException, RazorpayException{

    Order order = orderService.findOrderById(orderId);
    User user = userService.findUserProfileByJwt(jwt);
    try {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", order.getTotalDiscountPrice()*100);
        paymentLinkRequest.put("currency","INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFirstName());
        customer.put("email",user.getEmail());
        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+orderId);
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        String paymentLinkId = payment.get("id");
        String paymentLinkUrl = payment.get("short_url");

        PaymentLinkResponse res = new PaymentLinkResponse();
        res.setPayment_link_id(paymentLinkId);
        res.setPayment_link_url(paymentLinkUrl);

        return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);


    } catch (Exception e) {
        throw new RazorpayException(e.getMessage());
    }

    }

    @GetMapping("/payments")
    private ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id")String paymentId, @RequestHeader("Authorization") String jwt, @RequestParam(name="order_id")Long orderId) throws RazorpayException,UserException, OrderException{
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        try {
            Payment payment = razorpayClient.payments.fetch(paymentId);
             if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
                orderRepository.save(order);
             }

             ApiResponse res = new ApiResponse();
             res.setMessage("Your Order Has Been Placed");
             res.setStatus(true);

             return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
        } catch (RazorpayException e) {
            throw new RazorpayException(e.getMessage());
        }

    }
}




















