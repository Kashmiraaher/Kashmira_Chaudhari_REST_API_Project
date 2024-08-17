package com.trialstudio.customstudio.repository;



import com.trialstudio.customstudio.model.OrderProduct;
import com.trialstudio.customstudio.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends  MongoRepository<OrderProduct, String>{

}