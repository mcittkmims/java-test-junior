package com.java.test.junior.controller;

import com.java.test.junior.model.loading.FileSourceDTO;
import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.user.UserPrincipal;
import com.java.test.junior.service.loading.LoadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/loading")
@Controller
@AllArgsConstructor
public class LoadingController {

    private LoadingService loadingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void loadingProducts(@RequestBody FileSourceDTO fileSourceDTO, @AuthenticationPrincipal UserPrincipal userPrincipal){
        loadingService.loadProducts(fileSourceDTO, userPrincipal);
    }

}
