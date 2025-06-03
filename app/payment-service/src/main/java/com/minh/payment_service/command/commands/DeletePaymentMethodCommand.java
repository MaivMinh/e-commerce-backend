package com.minh.payment_service.command.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePaymentMethodCommand {
    private String paymentMethodId;
    private String errorMsg;
}
