package com.food.basic.admin.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdminUserService {
	
	private final AdminUserMapper adminUserMapper;
}
