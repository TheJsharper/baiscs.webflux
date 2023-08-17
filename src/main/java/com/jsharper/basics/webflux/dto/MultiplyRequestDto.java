package com.jsharper.basics.webflux.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
public class MultiplyRequestDto {

	private int first;
	private int second;
}
