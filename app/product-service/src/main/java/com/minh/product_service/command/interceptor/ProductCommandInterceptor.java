package com.minh.product_service.command.interceptor;

import com.minh.product_service.command.commands.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
  @Override
  public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
    return ((index, command) -> {
      if (command.getPayload() instanceof CreateProductCommand) {

      }
      return command;
    });
  }
}
