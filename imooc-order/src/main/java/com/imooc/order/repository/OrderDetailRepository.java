package com.imooc.order.repository;

import com.imooc.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mx
 * 2019-03-23 16:12
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

}
