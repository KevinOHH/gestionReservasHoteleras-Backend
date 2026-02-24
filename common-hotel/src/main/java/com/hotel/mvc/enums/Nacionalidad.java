package com.hotel.mvc.enums;


public enum Nacionalidad {
    MEXICANA,
    ESTADOUNIDENSE,
    CANADIENSE,
    ARGENTINA,
    ESPAÑOLA,
    COLOMBIANA;
	
	public static Nacionalidad fromString (String value) {
		if(value == null || value.trim().isEmpty()) {
		     throw new IllegalArgumentException("La nacionalidad no puede ser nula o vacía");
        }

        for (Nacionalidad n : Nacionalidad.values()) {
            if (n.name().equalsIgnoreCase(value.trim())) {
                return n;
            }
        }

        throw new IllegalArgumentException("Nacionalidad inválida: " + value);
    }
	
}
