package com.actvc.client;

public enum Gender {
	F, M, U;

	public static Gender getGender(String genderStr) {
		if (!genderStr.isEmpty()) {
			genderStr = genderStr.trim().toUpperCase();
			if (genderStr.equals(Gender.F.toString()) || genderStr.equals("F")) {
				return Gender.F;
			} else if (genderStr.equals(Gender.M.toString())
					|| genderStr.equals("M")) {
				return Gender.M;
			}
		}
		return Gender.U;
	}
}
