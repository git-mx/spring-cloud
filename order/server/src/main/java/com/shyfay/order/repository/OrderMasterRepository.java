package com.shyfay.order.repository;

import com.shyfay.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mx
 * 2019-03-23 16:11
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
