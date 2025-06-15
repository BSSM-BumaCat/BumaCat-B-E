package com.example.bumacat.domain.market.controller;

import com.example.bumacat.domain.market.dto.request.MarketRequest;
import com.example.bumacat.domain.market.dto.response.MarketResponse;
import com.example.bumacat.domain.market.service.MarketService;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.annotation.CurrentUser;
import com.example.bumacat.global.dto.CursorPage;
import com.example.bumacat.global.dto.ResponseDto;
import com.example.bumacat.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {
  private final MarketService marketService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<ResponseDto<Void>> openMarket(@Valid @RequestBody MarketRequest marketRequest, @CurrentUser User user) {
    marketService.open(marketRequest, user);
    ResponseDto<Void> responseDto = HttpUtil.success("market opened");
    return ResponseEntity.ok(responseDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{market-id}")
  public ResponseEntity<ResponseDto<Void>> modifyMarket(@Valid @RequestBody MarketRequest marketRequest, @CurrentUser User user, @PathVariable("market-id") Long marketId) {
    marketService.modify(marketRequest, user, marketId);
    ResponseDto<Void> responseDto = HttpUtil.success("market modified");
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<MarketResponse>> findNextUpcomingMarket() {
    MarketResponse marketResponse = marketService.findNextUpcomingMarket();
    ResponseDto<MarketResponse> responseDto = HttpUtil.success("not opened old market found", marketResponse);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/history")
  public ResponseEntity<ResponseDto<CursorPage<MarketResponse>>> findHistoryMarket(@RequestParam(value = "cursor-id", required = false) Long cursorId, @RequestParam(value = "size", defaultValue = "10")  Integer size) {
    CursorPage<MarketResponse> marketResponseCursorPage = marketService.findHistory(cursorId, size);
    ResponseDto<CursorPage<MarketResponse>> responseDto = HttpUtil.success("market history found", marketResponseCursorPage);
    return ResponseEntity.ok(responseDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{market-id}")
  public ResponseEntity<ResponseDto<Void>> deleteMarket(@PathVariable("market-id") Long marketId) {
    marketService.delete(marketId);
    ResponseDto<Void> responseDto = HttpUtil.success("market deleted");
    return ResponseEntity.ok(responseDto);
  }
}
