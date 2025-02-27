package com.food.basic.kakaopay;

import lombok.Data;

/**
 * Created by kakaopay
 */

@Data
public class ReadyResponse {
    private String tid;
    private String created_at;
    private String next_redirect_pc_url;


}
