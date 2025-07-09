package com.signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signals")
public class SignalController {

//    @Autowired
//    private SignalService signalService;
//
//    @Autowired
//    private KiteAuthService kiteService;

//    @PostMapping("/buy")
//    public String handleBuySignal(@RequestBody SignalRequest request) {
////        String dbResponse = signalService.processSignal("BUY", request);
////        String kiteResponse = kiteService.placeTestOrder(request.getSymbol(), 1, request.getPrice());
//        return null;
//    }
//
//    @PostMapping("/sell")
//    public String handleSellSignal(@RequestBody SignalRequest request) {
////        String dbResponse = signalService.processSignal("SELL", request);
////        String kiteResponse = kiteService.placeTestOrder(request.getSymbol(), 1, request.getPrice());
//        return null;
//    }
}