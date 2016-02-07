package modele;

import lombok.Data;

@Data
public class Type {
	private int idType;
	private String libelleType;

	public Type(String libelleType) {
		super();
		this.libelleType = libelleType;
	}

	public Type() {
		super();
	}


}
