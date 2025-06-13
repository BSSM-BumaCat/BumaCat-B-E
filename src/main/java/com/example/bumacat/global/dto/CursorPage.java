package com.example.bumacat.global.dto;

import java.util.List;

public record CursorPage<T> (
        boolean hasNext,
        List<T> data
) {
}
