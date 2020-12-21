package com.bonfire.az.bonfireaz.model.response;

import com.bonfire.az.bonfireaz.controller.OperationName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationModel {

	private String result;
	private String name;

	public OperationModel(OperationStatus status, OperationName name) {
		this.result = status.name();
		this.name = name.name();
	}
}
