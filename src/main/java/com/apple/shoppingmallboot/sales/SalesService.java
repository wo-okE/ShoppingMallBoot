package com.apple.shoppingmallboot.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;


    public List<Sales> getAllSales(){ return salesRepository.findAll(); }

    public void saveSales(Sales sales) {
        salesRepository.save(sales);
    }

    public List<SalesDto> customFindAll() {
        List<Sales> result = salesRepository.customFindAll();
        List<SalesDto> resultList = new ArrayList<>();
        SalesDto salesDto;

        for (int i = 0; i < result.size(); i++){
            salesDto = new SalesDto();
            salesDto.setPrice(result.get(i).getPrice());
            salesDto.setItemName(result.get(i).getItemName());
            salesDto.setUserName(result.get(i).getMember().getUsername());
            salesDto.setDisplayName(result.get(i).getMember().getDisplayName());
            salesDto.setCreated(result.get(i).getCreated());

            resultList.add(salesDto);
        }

        return resultList;
    }
}
