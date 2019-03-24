package com.shyfay.order.repository;

import com.shyfay.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mx
 * 2019-03-23 16:12
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

}
