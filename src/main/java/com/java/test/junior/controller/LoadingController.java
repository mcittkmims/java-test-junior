package com.java.test.junior.controller;

import com.java.test.junior.model.loading.FileSourceDTO;
import com.java.test.junior.model.user.UserPrincipal;
import com.java.test.junior.service.loading.LoadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/loading")
@RestController
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
