package com.common;

public class TTT {

	public static void main(String[] args) {
		for (int i = -5; i <= 5; i++) {
			// Math.abs(i),变化规律 先减少后增加 步长 1
			for (int j = 0; j < Math.abs(i)+10; j++) {
				System.out.print(" ");
			}
			// 11-Math.abs(i)*2 ,先增加后减少 步长 2
			for (int k = 0; k < 11 - Math.abs(i) * 2; k++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
