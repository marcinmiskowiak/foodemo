package pl.mm.foodemo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
@Getter
public class PairDTO<T> {
    T element1;
    T element2;
}