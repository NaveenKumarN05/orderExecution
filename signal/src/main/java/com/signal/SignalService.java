
package com.signal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignalService {

    @Autowired
    private SignalRepository repository;

    @Transactional
    public String processSignal(String type, SignalRequest request) {
        Signal signal = new Signal();
        signal.setType(type);
        signal.setSymbol(request.getSymbol());
        signal.setPrice(request.getPrice());
        signal.setTimestamp(System.currentTimeMillis());
        repository.save(signal);
        return type + " signal saved for: " + request.getSymbol() + " at price: " + request.getPrice();
    }
}