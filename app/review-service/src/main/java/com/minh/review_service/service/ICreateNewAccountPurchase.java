package com.minh.review_service.service;

import com.minh.review_service.DTOs.RetrievedProductInfoDTO;

public interface ICreateNewAccountPurchase {
    /**
     * Tạo một bản ghi mua hàng mới cho tài khoản.
     *
     * @param retrievedProductInfoDTO Thông tin sản phẩm đã được lấy từ product service
     * @return true nếu tạo thành công, false nếu không thành công
     */
    boolean createNewAccountPurchase(RetrievedProductInfoDTO retrievedProductInfoDTO);
}
