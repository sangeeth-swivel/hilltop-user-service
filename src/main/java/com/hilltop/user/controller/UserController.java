package com.hilltop.user.controller;

import com.hilltop.user.domain.request.UserRequestDto;
import com.hilltop.user.domain.response.ResponseWrapper;
import com.hilltop.user.enums.ErrorMessage;
import com.hilltop.user.enums.SuccessMessage;
import com.hilltop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> registerUser(@RequestBody UserRequestDto userRequestDto) {
        if (!userRequestDto.isRequiredFieldsAvailable()) {
            log.debug("Required fields missing. data: {}", userRequestDto.toLogJson());
            return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS, HttpStatus.BAD_REQUEST);
        }
        if (!userRequestDto.isValidMobileNo())
            return getBadRequestErrorResponse(ErrorMessage.INVALID_MOBILE_NO, HttpStatus.BAD_REQUEST);
        userService.addUser(userRequestDto);
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_ADDED, null, HttpStatus.CREATED);

    }
}
