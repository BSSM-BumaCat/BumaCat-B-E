package com.example.bumacat.global.argument;

import com.example.bumacat.domain.user.facade.UserFacade;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class HandlerUserArgumentResolver implements HandlerMethodArgumentResolver {
  private final UserFacade userFacade;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUser.class)
            && parameter.getParameterType() == User.class;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    User user = userFacade.getUser();
    return user;
  }
}
