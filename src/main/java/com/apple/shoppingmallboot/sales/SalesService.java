package com.apple.shoppingmallboot.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;


    public void saveSales(Sales sales) {
        salesRepository.save(sales);
    }
}
