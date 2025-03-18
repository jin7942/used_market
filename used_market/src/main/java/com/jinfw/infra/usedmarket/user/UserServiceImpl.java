package com.jinfw.infra.usedmarket.user;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;

}
